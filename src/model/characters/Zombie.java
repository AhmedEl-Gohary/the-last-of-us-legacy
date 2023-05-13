package model.characters;

import java.awt.Point;

import engine.Game;
import engine.Valid;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.Cell;
import model.world.CharacterCell;

public class Zombie extends Character {

	// Read-write static variable
	private static int ZOMBIES_COUNT = 0;

	public Zombie() {
		super("Zombie "+ (++ZOMBIES_COUNT), 40, 10);
	}

	public void onCharacterDeath() {
		super.onCharacterDeath();
		Game.removeZombie(this);
	}
	
	public void attack() {
		attackAdjacentHero();
	}
	
	public void attackAdjacentHero() {
		int[] dx = {1, -1, 0, 0, 1, -1, 1, -1};
		int[] dy = {1, -1, 1, -1, 0 , 0, -1, 1};
		for (int i = 0; i < 8; i++) {
			int row = getLocation().x + dx[i];
			int column = getLocation().y + dy[i];
			if (Valid.isLocationValid(new Point(row, column))) {
				Cell cell = Game.map[row][column];
				if (cell instanceof CharacterCell) {
					Character character = ((CharacterCell) cell).getCharacter();
					if (character != null && character instanceof Hero) {
						setTarget(character);
						try {
							super.attack();
						} catch (InvalidTargetException | NotEnoughActionsException e) {

						}
						return;
					}
				}
			}
		}
	}
}
