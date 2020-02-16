package lib;

import java.awt.Graphics2D;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class Scene extends JPanel implements KeyListener {

	public abstract void update();
	public abstract void render(Graphics2D g);
	
	public Scene() {
		addKeyListener(this);
	}
	
}
