package gameobject;

import java.awt.Graphics2D;

import lib.Inanimate;
import manager.LadderManager;

public class Ladder extends Inanimate {

	public Ladder(double x, double y) {
		super(x, y);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);
		g.drawImage(LadderManager.getInstance().getImage(), af, null);
	}

}
