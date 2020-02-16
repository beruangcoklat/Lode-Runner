package gameobject;

import java.awt.Graphics2D;

import lib.Inanimate;
import manager.TreasureManager;

public class Treasure extends Inanimate {

	public Treasure(double x, double y) {
		super(x, y);
		
	}

	@Override
	public void update() {
		
	}
	
	@Override
	public void render(Graphics2D g) {
		super.render(g);
		g.drawImage(TreasureManager.getInstance().getImage(), af, null);
	}

}
