package model.characters;

import java.awt.Point;

public abstract class Character {

	// Read-only instance variables
	private String name;
	private int maxHp;
	private int attackDmg;

	// Read-write instance variables
	private Point location;
	private int currentHp;
	private Character target;

	public Character(String name, int maxHp, int attackDmg) {
		this.name = name;
		this.maxHp = maxHp;
		this.currentHp = maxHp;
		this.attackDmg = attackDmg;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		if (currentHp < 0)
			this.currentHp = 0;
		else
			this.currentHp = Math.min(maxHp, currentHp);
	}

	public Character getTarget() {
		return target;
	}

	public void setTarget(Character target) {
		this.target = target;
	}

	public String getName() {
		return name;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getAttackDmg() {
		return attackDmg;
	}

	private int distance(int x, int y) {
		return Math.abs(x - y);
	}
	
	private boolean isAdjacent() {
		if (distance(target.location.x, location.x) == 1
				&& (distance(target.location.y, location.y) == 1)) {
			return true;

		}
		if (distance(target.location.x, location.x) + distance(target.location.y, location.y) == 1) {
			return true;
		}
		return false;
	}

	public void attack() {
		if (isAdjacent()) {
			target.setCurrentHp(Math.max(0, target.getCurrentHp() - attackDmg));
			target.defend(this);
			// If currHp of target reaches zero call onCharacterDeath
		}
	}
	
	public void defend(Character character) {
		character.setCurrentHp(Math.max(0, character.getCurrentHp() - attackDmg / 2));
		// If currHp of target reaches zero call onCharacterDeath
	}
	
	public abstract void onCharacterDeath();
	
	

}
