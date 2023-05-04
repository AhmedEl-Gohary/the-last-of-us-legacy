package model.characters;

import engine.Game;
import exceptions.InvalidTargetException;

public class Explorer extends Hero {
	
	public Explorer(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}
	
	@Override
	public void useSpecial() throws InvalidTargetException {
		super.useSpecial();
		Game.setMap(false);
	}
}
