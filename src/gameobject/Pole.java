package gameobject;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import lib.Inanimate;
import utility.Asset;

public class Pole extends Inanimate {

	private static BufferedImage image;

	static {
		image = Asset.getInstance().getPole();
	}

	public Pole(double x, double y) {
		super(x, y);
	}

	@Override
	public void update() {

	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);
		g.drawImage(image, af, null);
	}

}
