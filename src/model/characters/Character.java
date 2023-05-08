package model.characters;

import java.awt.Point;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.CharacterCell;

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
		if (currentHp <= 0) {
			onCharacterDeath();
		}
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
	
	public boolean isAdjacent() {
		if (distance(target.location.x, location.x) == 1
				&& (distance(target.location.y, location.y) == 1)) {
			return true;

		}
		if (distance(target.location.x, location.x) + distance(target.location.y, location.y) == 1) {
			return true;
		}
		return false;
	}

	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		if (target == null || !isAdjacent()) {
			throw new InvalidTargetException();
		}
		target.setCurrentHp(target.getCurrentHp() - attackDmg);
		target.defend(this);
	}
	
	public void defend(Character character) {
		character.setCurrentHp(character.getCurrentHp() - attackDmg / 2);
	}
	
	public void onCharacterDeath() {
		Game.map[location.x][location.y] = new CharacterCell(null);
	}

}
