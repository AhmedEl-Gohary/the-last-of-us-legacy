package model.characters;

import java.awt.Point;
import java.util.ArrayList;

import engine.Game;
import engine.Valid;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NotEnoughActionsException;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

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

		// The target
		if (this instanceof Fighter && specialAction) {
			return;
		}
		actionsAvailable--;
	}

	public void onCharacterDeath() {
		super.onCharacterDeath();
		Game.removeHero(this);
	}

	private Point dirToPoint(Direction direction) {
		int x = getLocation().x;
		int y = getLocation().y;
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
		return new Point(x, y);
	}
	
	public void move(Direction direction) throws MovementException, NotEnoughActionsException {
		Point p = dirToPoint(direction);
		if (!Valid.isLocationValid(p)) {
			throw new MovementException();
		}
		if (getActionsAvailable() <= 0) {
			throw new NotEnoughActionsException();
		}
		if (Game.map[p.x][p.y] instanceof CharacterCell && ((CharacterCell) Game.map[p.x][p.y]).getCharacter() != null) {
			throw new MovementException();
		}
		
		Cell cell = Game.map[p.x][p.y];
		if (cell instanceof CollectibleCell) {
			 ((CollectibleCell) cell).getCollectible().pickUp(this);
		} else if (cell instanceof TrapCell) {
			setCurrentHp(getCurrentHp() - ((TrapCell) cell).getTrapDamage());
		}
		
		Game.map[getLocation().x][getLocation().y] = new CharacterCell(null);
		
		setLocation(p);
		Game.map[p.x][p.y] = new CharacterCell(this);
		setVisibleCells(p.x, p.y);
		actionsAvailable--;
	}
	
	private void setVisibleCells(int x, int y) {
		int[] dx = {1, -1, 0, 0, 1, -1, 1, -1, 0};
		int[] dy = {1, -1, 1, -1, 0 , 0, -1, 1, 0};
		for (int i = 0; i < 8; i++) {
			int row = x + dx[i];
			int column = y + dy[i];
			if (Valid.isLocationValid(new Point(row, column))) {
				Game.map[row][column].setVisible(true);
			}
		}
	}
	
	public void useSpecial() throws InvalidTargetException {
		actionsAvailable--;
		supplyInventory.get(0).use(this);
		specialAction  = true;
	}
}
