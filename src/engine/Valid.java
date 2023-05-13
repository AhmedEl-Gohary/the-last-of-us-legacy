package engine;

import java.awt.*;

import exceptions.*;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public class Valid {

	public static boolean isLocationValid(Point p) {
		return p.x >= 0 && p.x < 15 && p.y >= 0 && p.y < 15;
	}

	public static boolean isActionValid(Hero hero) {
		return hero.getActionsAvailable() != 0;
	}

	private static int distance(int x, int y) {
		return Math.abs(x - y);
	}

	public static boolean isTargetValid(Hero hero) {
		if (hero.getTarget() == null)
			return false;
		if (distance(hero.getTarget().getLocation().x, hero.getLocation().x) == 1
				&& (distance(hero.getTarget().getLocation().y, hero.getLocation().y) == 1)) {
			return true;

		}
		if (distance(hero.getTarget().getLocation().x, hero.getLocation().x)
				+ distance(hero.getTarget().getLocation().y, hero.getLocation().y) == 1) {
			return true;
		}
		return false;
	}

	public static void checkMoveValid(Hero hero, Point p) throws MovementException {
		if (!isActionValid(hero) || !isLocationValid(p) || !(isEmptyCell(cellAt(p))))
			throw new MovementException();
	}

	public static void checkCureValid(Hero hero) throws GameActionException {
		if (hero.getVaccineInventory().size() == 0)
			throw new NoAvailableResourcesException();
		if (!isActionValid(hero))
			throw new NotEnoughActionsException();
		if (!isTargetValid(hero) || !(hero.getTarget() instanceof Zombie))
			throw new InvalidTargetException();
	}

	public static void checkAttackValid(Hero hero) throws GameActionException {
		if (!isTargetValid(hero) || !(hero.getTarget() instanceof Zombie))
			throw new InvalidTargetException();
		if (hero instanceof Fighter && hero.isSpecialAction())
			return;
		if (!isActionValid(hero))
			throw new NotEnoughActionsException();
	}

	public static void checkCollectValid(Hero hero) throws GameActionException {
		if (!isActionValid(hero))
			throw new NotEnoughActionsException();
	}

	public static void checkSpecialValid(Hero hero) throws GameActionException {
		if (hero.getSupplyInventory().size() == 0)
			throw new NoAvailableResourcesException();
		if (hero.isSpecialAction())
			throw new NotEnoughActionsException();
	}

	public static Cell cellAt(Point p) {
		return Game.map[p.x][p.y];
	}

	public static boolean isEmptyCell(Cell cell) {
		return cell == null;
	}

	public static boolean isCharacterCell(Cell cell) {
		return !isEmptyCell(cell) && cell instanceof CharacterCell;
	}

	public static boolean isHeroCell(Cell cell) {
		return isCharacterCell(cell) && ((CharacterCell) cell).getCharacter() instanceof Hero;
	}

	public static boolean isZombieCell(Cell cell) {
		return isCharacterCell(cell) && ((CharacterCell) cell).getCharacter() instanceof Zombie;
	}

	public static boolean isTrapCell(Cell cell) {
		return !isEmptyCell(cell) && cell instanceof TrapCell;
	}

	public static boolean isCollectableCell(Cell cell) {
		return !isEmptyCell(cell) && cell instanceof CollectibleCell;
	}

}
