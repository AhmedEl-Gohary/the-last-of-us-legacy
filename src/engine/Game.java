package engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.world.Cell;

public class Game {
	
	// Read-write static variables
	public static ArrayList<Hero> availableHeroes;
	public static ArrayList<Hero> heroes;
	public static ArrayList<Zombie> zombies;
	public static Cell[][] map;
	
	public static void loadHeroes(String filePath) throws Exception {
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
	
	
}
