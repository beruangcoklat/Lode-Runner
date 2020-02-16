package main;

import javax.swing.JFrame;

import lib.Scene;
import scene.GameScene;
import utility.Camera;
import utility.Map;
import utility.Time;
import utility.Util;
import utility.Variable;

@SuppressWarnings("serial")
public class Main extends JFrame implements Runnable {

	private Scene activeScene;
	private boolean isClose;
	private static Main instance;

	public static void main(String[] args) {
		instance = new Main();
	}

	private Main() {
		Map.getInstance();
		activeScene = new GameScene();
		
		setSize(Camera.getInstance().getWidth() + 6, Camera.getInstance().getHeight() + 29);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(activeScene);
		setVisible(true);
		activeScene.setFocusable(true);
		new Thread(this).start();
	}

	@Override
	public void run() {
		long lastTime = System.currentTimeMillis();
		long targetTime = 1000 / Variable.LIMIT_FPS;
		while (!isClose) {
			long now = System.currentTimeMillis();
			Time.deltaTime = (now - lastTime);

			long sleep_time = targetTime - Time.deltaTime;
			sleep_time = sleep_time > 0 ? sleep_time : 0;
			Util.sleep(sleep_time);
			Time.deltaTime += sleep_time;

			lastTime = now;
			activeScene.update();
			activeScene.repaint();

			Time.fps = (int) (1000 / (Time.deltaTime + sleep_time));
		}
	}

	public void changeScene(Scene newScene) {
		remove(activeScene);
		activeScene = newScene;
		add(activeScene);
		revalidate();
		activeScene.setFocusable(true);
	}

	public static Main getInstance() {
		return instance;
	}

	public void dispose() {
		isClose = true;
		super.dispose();
	}

	public boolean isClose() {
		return isClose;
	}

}
