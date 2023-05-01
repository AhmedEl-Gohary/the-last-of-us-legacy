package model.characters;

public class Medic extends Hero {
	
	public Medic(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}
	
	public void useSpecial(Hero hero) {
		super.useSpecial();
		heal(hero);
	}
	
	private void heal(Hero hero) {
		hero.setCurrentHp(getMaxHp());
	}
}
