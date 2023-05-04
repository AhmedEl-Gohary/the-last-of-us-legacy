package model.characters;

import exceptions.InvalidTargetException;

public class Medic extends Hero {
	
	public Medic(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}
	
	public void useSpecial() throws InvalidTargetException {
		if (!(getTarget() instanceof Hero)) throw new InvalidTargetException();
		super.useSpecial();
		heal((Hero)getTarget());
		setSpecialAction(getSupplyInventory().size() > 0);
	}
	
	private void heal(Hero hero) {
		hero.setCurrentHp(getMaxHp());
	}
}
