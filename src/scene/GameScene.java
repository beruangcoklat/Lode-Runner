package scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import gameobject.Player;
import lib.Scene;
import manager.BrickManager;
import manager.EnemyManager;
import manager.LadderManager;
import manager.PoleManager;
import manager.RockManager;
import manager.TreasureManager;
import utility.Camera;
import utility.Time;
import utility.Variable;

@SuppressWarnings("serial")
public class GameScene extends Scene {

	private boolean startGame;
	private static boolean endGame;

	public GameScene() {
		startGame = false;
		endGame = false;
		Camera.getInstance().init();
	}

	@Override
	public void update() {
		if (!startGame) {
			startGame = Camera.getInstance().preGame();
			return;
		}

		Player.getInstance().update();
		BrickManager.getInstance().update();
		LadderManager.getInstance().update();
		Camera.getInstance().update();
		EnemyManager.getInstance().update();
		TreasureManager.getInstance().update();
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Variable.REAL_WIDTH, Variable.REAL_HEIGHT);

		PoleManager.getInstance().render(g);
		BrickManager.getInstance().render(g);
		LadderManager.getInstance().render(g);
		RockManager.getInstance().render(g);
		TreasureManager.getInstance().render(g);
		EnemyManager.getInstance().render(g);
		Player.getInstance().render(g);

		if (!startGame)
			return;
		g.setColor(Color.GREEN);
		g.setFont(new Font("Arial", Font.PLAIN, 15));
		g.drawString("FPS: " + Time.fps, 0, 15);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		render((Graphics2D) g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Player.getInstance().move(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		Player.getInstance().idle(e.getKeyCode());
	}

	public void keyTyped(KeyEvent e) {
	}

	public static boolean isEndGame() {
		return endGame;
	}

	public static void endGame() {
		endGame = true;
	}

}
