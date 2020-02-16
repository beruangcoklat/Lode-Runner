package manager;

import gameobject.Enemy;
import lib.GameObject;
import lib.ObjectManager;
import scene.GameScene;
import utility.Variable;

public class EnemyManager extends ObjectManager {

	private EnemyManager() {
		
	}

	public void update() {
		if (GameScene.isEndGame())
			return;

		super.update();
	}

	public boolean findEnemy(int x, int y) {
		for (GameObject item : items) {
			Enemy enemy = (Enemy) item;
			if (enemy.repositionX() / Variable.TILE_SIZE == x && enemy.repositionY() / Variable.TILE_SIZE == y) {
				return true;
			}
		}
		return false;
	}

	private static EnemyManager instance = new EnemyManager();

	public static EnemyManager getInstance() {
		return instance;
	}

}
