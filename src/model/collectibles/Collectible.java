package model.collectibles;

import model.characters.Hero;

public interface Collectible {
	
	// Adds the collectible picked by the hero to his corresponding ArrayList
	void pickUp(Hero hero);
	
	// Removes the used collectible by the hero from corresponding ArrayList
	void use(Hero hero);
	
}
