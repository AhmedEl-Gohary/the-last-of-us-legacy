package model.collectibles;

import model.characters.Hero;

public class Supply implements Collectible {
	
	public Supply() {}

	// Adds the supply picked by the hero to his corresponding ArrayList
	@Override
	public void pickUp(Hero hero) {
		hero.getSupplyInventory().add(this);
	}

	// Removes the used supply by the hero from corresponding ArrayList
	@Override
	public void use(Hero hero) {
		hero.getSupplyInventory().remove(this);
	}
	
	
}
