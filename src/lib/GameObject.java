package lib;

import java.awt.Graphics2D;

import utility.Variable;

public abstract class GameObject {

	protected double x, y, width, height;

	public GameObject() {
		width = height = Variable.TILE_SIZE;
	}

	public abstract void update();

	public abstract void render(Graphics2D g);

	protected boolean collideVertical(GameObject other) {
		GameObject up = y < other.y ? this : other;
		GameObject down = y > other.y ? this : other;
		return up.y + up.height > down.y;
	}

	protected boolean collideHorizontal(GameObject other) {
		GameObject left = x < other.x ? this : other;
		GameObject right = x > other.x ? this : other;
		return left.x + left.width > right.x;
	}

	protected double getCollideVertical(GameObject other) {
		GameObject up = y < other.y ? this : other;
		GameObject down = y > other.y ? this : other;
		return up.y + up.height - down.y;
	}

	protected double getCollideHorizontal(GameObject other) {
		GameObject left = x < other.x ? this : other;
		GameObject right = x > other.x ? this : other;
		return left.x + left.width - right.x;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

}
