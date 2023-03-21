package model.world;

import java.util.Random;

public class TrapCell extends Cell {
	
	// Read-only instance variables
	private int trapDamage;
	
	public TrapCell() {
		Random r = new Random();
		int damage = r.nextInt(3) + 1;
		trapDamage = damage * 10;
	}

	public int getTrapDamage() {
		return trapDamage;
	}
	
}
