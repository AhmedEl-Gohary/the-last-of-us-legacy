package model.characters;

import engine.Game;

public class Zombie extends Character {

	// Read-write static variable
	private static int ZOMBIES_COUNT = 0;

	public Zombie() {
		super("Zombie " + (++ZOMBIES_COUNT), 40, 10);
	}

	@Override
	public void attack() {
		super.attack();

		// The target is always a zombie
		if (getTarget().getCurrentHp() <= 0) {
			getTarget().onCharacterDeath();
		}
	}

	public void onCharacterDeath() {
		if (getCurrentHp() <= 0) {
			Game.removeZombie(this);
			ZOMBIES_COUNT--;
		}
	}
}
