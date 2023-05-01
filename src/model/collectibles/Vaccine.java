package model.collectibles;

import model.characters.Hero;

public class Vaccine implements Collectible {

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
	}
	
}
