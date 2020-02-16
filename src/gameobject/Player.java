package gameobject;

import java.awt.event.KeyEvent;

import lib.Animation;
import lib.CharacterBehavior2;
import lib.GameObject;
import main.Main;
import manager.BrickManager;
import manager.EnemyManager;
import manager.LadderManager;
import manager.PoleManager;
import manager.RockManager;
import manager.TreasureManager;
import scene.GameOverScene;
import scene.GameScene;
import utility.Asset;
import utility.Time;
import utility.Util;
import utility.Variable;

public class Player extends CharacterBehavior2 {

	public void reset() {
		isDead = false;
		startDeadAnim = false;
		anim.setAnimState(Variable.STATE_IDLE);
		lookAt = Variable.RIGHT;
	}

	private Player() {
		anim = new Animation();
		for (int i = 0; i < 6; i++) {
			int duration = 100;
			if (i == Variable.STATE_IDLE)
				duration = 1200;
			else if (i == Variable.STATE_DEAD)
				duration = 500;
			anim.addAnim(i, Asset.getInstance().getPlayer(i), duration);
		}
	}

	@Override
	public void update() {
		if (isDead) {
			Main.getInstance().changeScene(new GameOverScene(false));
			return;
		}

		if (TreasureManager.getInstance().getItems().size() == 0) {
			Main.getInstance().changeScene(new GameOverScene(true));
			return;
		}

		super.update();
	}

	public void move(int code) {
		if (GameScene.isEndGame())
			return;

		if (code == KeyEvent.VK_RIGHT) {
			veloX = 1;
			lookAt = Variable.RIGHT;
		} else if (code == KeyEvent.VK_LEFT) {
			veloX = -1;
			lookAt = Variable.LEFT;
		} else if (code == KeyEvent.VK_UP && targetLadder != null && !onTopLadder && !isTop) {
			lookAt = Variable.UP;
			veloY = -1;
			x = targetLadder.getX();
			anim.setAnimState(Variable.STATE_CLIMB_LADDER);
		} else if (code == KeyEvent.VK_DOWN && (targetLadder != null || targetPole != null)) {
			lookAt = Variable.DOWN;
			veloY = 1;
			if (targetLadder != null)
				x = targetLadder.getX();
			else if (targetLadder == null && targetPole != null) {
				fallFromPole = true;
				this.x = repositionX();
			}
			anim.setAnimState(Variable.STATE_CLIMB_LADDER);
		} else if (code == KeyEvent.VK_D || code == KeyEvent.VK_F) {
			open(code == KeyEvent.VK_F);
		}
	}

	public void idle(int code) {
		veloX = 0;
		veloY = 0;
	}

	private void open(boolean isRight) {
		int cx = (int) (repositionX() / Variable.TILE_SIZE);
		int cy = (int) (repositionY() / Variable.TILE_SIZE);
		int tx = cx + (isRight ? 1 : -1);
		int ty = cy + 1;

		String targetKey = Util.getKey(tx, ty);
		String targetUpKey = Util.getKey(tx, ty - 1);

		Brick targetBrick = (Brick) BrickManager.getInstance().getItem(targetKey);
		if (targetBrick == null)
			return;
		if (LadderManager.getInstance().getItem(targetUpKey) != null)
			return;
		if (PoleManager.getInstance().getItem(targetUpKey) != null)
			return;
		if (RockManager.getInstance().getItem(targetUpKey) != null)
			return;

		this.x = repositionX();
		targetBrick.destroy();
	}

	public void checkCollision() {
		int cx = (int) (repositionX() / Variable.TILE_SIZE);
		int cy = (int) (repositionY() / Variable.TILE_SIZE);

		// ambil treasure
		Treasure treasure = (Treasure) TreasureManager.getInstance().getItem(Util.getKey(cx, cy));
		if (treasure != null) {
			TreasureManager.getInstance().getItems().remove(treasure);
		}

		super.checkCollision();
		if (onGround)
			return;

		// bisa injek enemy
		Brick brick = (Brick) BrickManager.getInstance().getItem(Util.getKey(cx, cy + 1));
		if (brick != null && brick.isEmpty() && EnemyManager.getInstance().findEnemy(cx, cy + 1))
			onGround = true;
	}

	protected boolean getDead() {
		if (super.getDead())
			return true;

		for (GameObject enemy : EnemyManager.getInstance().getItems()) {
			if (collideVertical(enemy) && collideHorizontal(enemy)) {
				double v = getCollideVertical(enemy);
				double h = getCollideHorizontal(enemy);
				double t = Util.getTolerance() * 1.5;
				if (v < t || h < t)
					continue;

				GameScene.endGame();
				return true;
			}
		}

		return false;
	}

	protected void updateX(double velocity) {
		x += velocity * Time.deltaTime * 0.1;
	}

	protected void updateY(double velocity) {
		y += velocity * Time.deltaTime * 0.05;
	}

	public void setCoordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}

	private static Player instance = new Player();

	public static Player getInstance() {
		return instance;
	}

}
