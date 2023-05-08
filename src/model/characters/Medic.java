package model.characters;

import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;

public class Medic extends Hero {
	
	public Medic(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}
	
	public void useSpecial() throws InvalidTargetException, NoAvailableResourcesException {
		if (!(getTarget() instanceof Hero) || !isAdjacent()) {
			throw new InvalidTargetException();
		}
		super.useSpecial();
		heal((Hero) getTarget());
		setSpecialAction(getSupplyInventory().size() > 0);
	}
	
	private void heal(Hero hero) {
		hero.setCurrentHp(hero.getMaxHp());
	}
}
