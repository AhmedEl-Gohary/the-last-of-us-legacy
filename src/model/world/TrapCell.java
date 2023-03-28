package model.world;

import java.util.Random;

public class TrapCell extends Cell {
	
	// Read-only instance variables
	private int trapDamage;
	
	public TrapCell() {
		Random random = new Random();
		int[] damageOptions = {10, 20, 30};
		int index = random.nextInt(damageOptions.length);
		trapDamage = damageOptions[index];
	}

	public int getTrapDamage() {
		return trapDamage;
	}
	
}
