package model.world;

public abstract class Cell {

	private boolean isVisible;

	public Cell() {
		this.isVisible = false;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}
