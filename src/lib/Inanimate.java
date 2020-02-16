package lib;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import utility.Camera;
import utility.Variable;

public abstract class Inanimate extends GameObject {

	protected AffineTransform af;

	public Inanimate(double x, double y) {
		this.x = x;
		this.y = y;
		width = height = Variable.TILE_SIZE;
	}

	public void render(Graphics2D g) {
		af = new AffineTransform();
		af.translate(x - Camera.getInstance().getX(), y - Camera.getInstance().getY());
		af.scale(width / 16, height / 16);
	}

}
