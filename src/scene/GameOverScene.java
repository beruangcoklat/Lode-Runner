package scene;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import gameobject.Player;
import lib.Animation;
import lib.Scene;
import main.Main;
import utility.Asset;
import utility.Camera;
import utility.Map;
import utility.Variable;

@SuppressWarnings("serial")
public class GameOverScene extends Scene {

	private static BufferedImage bg;
	private static Animation anim;

	static {
		bg = Asset.getInstance().getGameover();
		anim = new Animation();
		anim.addAnim(Variable.WIN, Asset.getInstance().getPlayer(Variable.STATE_RUN), 100);
		anim.addAnim(Variable.LOSE, Asset.getInstance().getPlayer(Variable.STATE_DEAD), 100);
	}

	public GameOverScene(boolean isWin) {
		anim.setAnimState(isWin ? Variable.WIN : Variable.LOSE);
		Map.getInstance().reset();
		Player.getInstance().reset();
	}

	@Override
	public void update() {
		anim.updateAnim();
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(bg, 0, 0, Camera.getInstance().getWidth(), Camera.getInstance().getHeight(), null);
		g.drawImage(anim.getImage(), 0, 0, 100, 100, null);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			Main.getInstance().changeScene(new TitleScene());
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		render((Graphics2D) g);
	}

}
