package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public class Game {
	// Read-write static variable
	// finishes without testing
	public static ArrayList<Hero> availableHeroes = new ArrayList<>();
	public static ArrayList<Hero> heroes = new ArrayList<>();
	public static ArrayList<Zombie> zombies = new ArrayList<>();
	public static Cell[][] map;
	public static int[][] check= new int[15][15];
	
	public static void loadHeroes(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line;
		while ((line = br.readLine()) != null) {
			String[] hero = line.split(",");
			String name = hero[0];
			String type = hero[1];
			int maxHp = Integer.parseInt(hero[2]);
			int maxActions = Integer.parseInt(hero[3]);
			int attackDmg = Integer.parseInt(hero[4]);
			if (type.equals("FIGH"))
				availableHeroes.add(new Fighter(name, maxHp, attackDmg, maxActions));
			else if (type.equals("EXP"))
				availableHeroes.add(new Explorer(name, maxHp, attackDmg, maxActions));
			else if (type.equals("MED"))
				availableHeroes.add(new Medic(name, maxHp, attackDmg, maxActions));
		}
	}
	
	public static void removeHero(Hero hero) {
		heroes.remove(hero);
	}
	
	public static void removeZombie(Zombie zombie) {
		addZombie();
		zombies.remove(zombie);
	}
	
	public static void setMap(boolean flag) {
		for(int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (map[i][j] != null) {
					map[i][j].setVisible(flag);
				}
			}
		}
	}
	
	/*
	 * public static void startGame(Hero h): This method is called to handle the initial game
	   setup, as such, it should spawn the necessary collectibles (5 Vaccines, 5 Supplies), spawn 5 traps
       randomly around the map, spawn 10 zombies randomly around the map, add the hero to the
       controllable heroes pool and removing from the availableHeroes, and finally allocating the hero to
       the bottom left corner of the map.
	 */
	
	
	private static boolean isFree(Point p) {
		if (map[p.x][p.y] instanceof CharacterCell) {
			return ((CharacterCell) map[p.x][p.y]).getCharacter() == null && check[p.x][p.y] == 0;
		} 
		if (map[p.x][p.y] instanceof CollectibleCell) {
			return ((CollectibleCell) map[p.x][p.y]).getCollectible() == null && check[p.x][p.y] == 0;
		}
		if (map[p.x][p.y] instanceof TrapCell) {
			return ((TrapCell) map[p.x][p.y]) == null && check[p.x][p.y] == 0;
		}
		return true;
		
	}
	
	
	private static Point getRandomPos() {
		Random random = new Random();
		int row, column;
		do {
			row = random.nextInt(15);
			column = random.nextInt(15);
		} while (!isFree(new Point(row, column)) && (row > 0 || column > 0));
		
		return new Point(row, column);
	}
	
	
	private static void spawnCollectibles() {
		for (int i = 0; i < 5; i++) {
			Point p = getRandomPos();
			map[p.x][p.y] = new CollectibleCell(new Vaccine());
			p = getRandomPos();
			map[p.x][p.y] = new CollectibleCell(new Supply());
		}
	}
	
	private static void spawnTraps() {
		for (int i = 0; i < 5; i++) {
			Point p = getRandomPos();
			map[p.x][p.y] = new TrapCell();
		}
	}
	
	private static void spawnHero(Hero hero) {
		availableHeroes.remove(hero);
		heroes.add(hero);
		map[0][0] = new CharacterCell(hero);
		hero.setLocation(new Point(0, 0));
		hero.setVisibleCells(0, 0);
	}
	
	public static void addZombie() {
		Point p = getRandomPos();
		Zombie zombie = new Zombie();
		map[p.x][p.y] = new CharacterCell(zombie);
		zombie.setLocation(p);
		zombies.add(zombie);
	}
	
	private static void spawnZombies() {
		for (int i = 0; i < 10; i++) {
			addZombie();
		}
	}
	
	
	
	public static void startGame(Hero hero) {
		// Initialize map
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				map[i][j] = new CharacterCell(null);
			}
		}
		spawnCollectibles();
		spawnTraps();
		spawnZombies();
		spawnHero(hero);
	}
	
	// now we will continue after start game
	
	/*
	 * public static void endTurn(): This method is called when the player decides to end the turn.
The method should allow all zombies to attempt to attack an adjacent hero (if exists), as well as
reset each hero’s actions, target, and special, update the map visibility in the game such that only
cells adjacent to heroes are visible, and finally spawn a zombie randomly on the map.
	 */
	
	public static void endTurn() {
		setMap(false);
		zombies.forEach(zombie -> { 
			zombie.attack();
			zombie.setTarget(null);
		});
		addZombie();

		heroes.forEach(hero -> {
			hero.setVisibleCells(hero.getLocation().x, hero.getLocation().y);
			hero.setActionsAvailable(hero.getMaxActions());
			hero.setSpecialAction(false);
			hero.setTarget(null);
		});
		for (int i = 0;i<15;i++) {
			for (int j = 0;j<15;j++) check[i][j] = 0;
		}
	}
	
	public static boolean checkWin() {
		return heroes.size() >= 5 && noVaccines() && allVaccinesUsed();
	}
	
	public static boolean checkGameOver() {
		return (allVaccinesUsed() && noVaccines()) || heroes.size() == 0;
	}
	
	private static boolean noVaccines() {
		int vaccinesCnt = 0;
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (map[i][j] != null && map[i][j] instanceof CollectibleCell &&
						((CollectibleCell) map[i][j]).getCollectible() instanceof Vaccine) {
					vaccinesCnt++;
				}
			}
		}
		return vaccinesCnt == 0;
	}
	
	private static boolean allVaccinesUsed() {
		for (Hero hero : heroes) {
			if (!hero.getVaccineInventory().isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
}
