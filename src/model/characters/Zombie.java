package model.characters;

import engine.Game;

public class Zombie extends Character {

	// Read-write static variable
	private static int ZOMBIES_COUNT = 0;

	public Zombie() {
		super("Zombie " + (++ZOMBIES_COUNT), 40, 10);
	}

	public void onCharacterDeath() {
		super.onCharacterDeath();
		Game.removeZombie(this);
	}
}
