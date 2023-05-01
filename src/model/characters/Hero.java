package model.characters;

import java.awt.Point;
import java.util.ArrayList;

import engine.Game;
import model.collectibles.Supply;
import model.collectibles.Vaccine;

public abstract class Hero extends Character {

	// Read-only instance variables
	private int maxActions;
	private ArrayList<Vaccine> vaccineInventory;
	private ArrayList<Supply> supplyInventory;

	// Read-write instance variables;
	private int actionsAvailable;
	private boolean specialAction;

	public Hero(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg);
		this.maxActions = maxActions;
		this.actionsAvailable = maxActions;
		this.specialAction = false;
		vaccineInventory = new ArrayList<>();
		supplyInventory = new ArrayList<>();
	}

	public int getActionsAvailable() {
		return actionsAvailable;
	}

	public void setActionsAvailable(int actionsAvailable) {
		this.actionsAvailable = actionsAvailable;
	}

	public boolean isSpecialAction() {
		return specialAction;
	}

	public void setSpecialAction(boolean specialAction) {
		this.specialAction = specialAction;
	}

	public int getMaxActions() {
		return maxActions;
	}

	public ArrayList<Vaccine> getVaccineInventory() {
		return vaccineInventory;
	}

	public ArrayList<Supply> getSupplyInventory() {
		return supplyInventory;
	}

	@Override
	public void attack() {
		// check if tou can attack
		super.attack();

		// The target is always a zombie
		if (getTarget().getCurrentHp() <= 0) {
			getTarget().onCharacterDeath();
		}
		if (!(this instanceof Fighter)) {
			actionsAvailable--;
		}
	}

	public void onCharacterDeath() {
		Game.removeHero(this);
	}

	public void move(Direction direction) {
		int x = getLocation().x;
		int y = getLocation().y;
		removeVisibleCells(x, y);
		switch (direction) {
		case UP:
			y++;
			break;
		case DOWN:
			y--;
			break;
		case RIGHT:
			x++;
			break;
		case LEFT:
			x--;
			break;
		}
		// Handle out of bounds exception
		
		setLocation(new Point(x, y));
		Game.map[x][y].setVisible(true);
		setVisibleCells(x, y);
		actionsAvailable--;
	}
	
	private void setVisibleCells(int x, int y) {
		int[] dx = {1, -1, 0, 0, 1, -1, 1, -1};
		int[] dy = {1, -1, 1, -1, 0 , 0, -1, 1};
		for (int i = 0; i < 8; i++) {
			Game.map[x + dx[i]][y + dy[i]].setVisible(true);
		}
	}
	
	private void removeVisibleCells(int x, int y) {
		int[] dx = {1, -1, 0, 0, 1, -1, 1, -1};
		int[] dy = {1, -1, 1, -1, 0 , 0, -1, 1};
		for (int i = 0; i < 8; i++) {
			Game.map[x + dx[i]][y + dy[i]].setVisible(false);
		}
	}
	
	public void useSpecial() {
		actionsAvailable--;
		supplyInventory.get(0).use(this);
		specialAction  = true;
	}
	
	
	

}
