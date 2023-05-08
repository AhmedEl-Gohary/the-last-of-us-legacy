package model.collectibles;

import java.awt.Point;
import java.util.Random;

import engine.Game;
import model.characters.Hero;
import model.world.CharacterCell;

public class Vaccine implements Collectible {

	public static int vaccineCount = 5;
	
	public Vaccine() {}
	
	
	// Adds the vaccine picked by the hero to his corresponding ArrayList
	@Override
	public void pickUp(Hero hero) {
		hero.getVaccineInventory().add(this);
	}
	
	// Removes the used vaccine by the hero from corresponding ArrayList
	@Override
	public void use(Hero hero) {
		hero.getVaccineInventory().remove(this);

		Point p = hero.getTarget().getLocation();
		
		// Get random hero
		Random random = new Random();
		int idx = random.nextInt(Game.availableHeroes.size());
		Hero newHero = Game.availableHeroes.remove(idx);
		
		// Remove zombie
		Game.zombies.remove(hero.getTarget());
		
		// Update the map
		Game.map[p.x][p.y] = new CharacterCell(newHero);	
		
		// Add new hero
		Game.heroes.add(newHero);
		
		// Set location and visible cells of the new hero
		newHero.setLocation(p);
		newHero.setVisibleCells(p.x, p.y);
		vaccineCount--;
	}
	
}
