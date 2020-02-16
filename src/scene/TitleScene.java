package scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import lib.Scene;
import main.Main;
import utility.Camera;
import utility.Time;
import utility.Util;

@SuppressWarnings("serial")
public class TitleScene extends Scene {

	private String menus[] = { "Play", "Exit" };
	private int x_menu, x_title, curr_menu;
	private Color color;
	private double timer;

	public TitleScene() {
		color = Color.WHITE;
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Camera.getInstance().getWidth(), Camera.getInstance().getHeight());

		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 50));
		g.drawString("YI Runner", x_title, 100);

		g.setFont(new Font("Arial", Font.BOLD, 20));
		for (int i = 0; i < menus.length; i++) {
			Color c = curr_menu == i ? Color.RED : Color.WHITE;
			g.setColor(c);
			g.drawString(menus[i], x_menu, 150 + (i * 30));
		}
	}

	@Override
	public void update() {
		x_title = Camera.getInstance().getWidth() / 2 - 100;
		x_menu = Camera.getInstance().getWidth() / 2 - 50;

		timer += Time.deltaTime;
		if (timer > 500) {
			timer = 0;
			color = color == Color.WHITE ? Color.YELLOW : Color.WHITE;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN) {
			curr_menu += (code == KeyEvent.VK_UP ? -1 : 1);
			curr_menu = Util.minMax(curr_menu, 0, menus.length - 1);
		} else if (code == KeyEvent.VK_ENTER) {
			if (menus[curr_menu].equals("Play"))
				Main.getInstance().changeScene(new GameScene());
			else
				Main.getInstance().dispose();
		}
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		render((Graphics2D) g);
	}


}
