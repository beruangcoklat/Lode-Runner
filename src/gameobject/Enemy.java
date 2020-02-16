package gameobject;

import java.util.Vector;

import lib.Animation;
import lib.CharacterBehavior2;
import manager.BrickManager;
import manager.EnemyManager;
import utility.Asset;
import utility.Map;
import utility.Time;
import utility.Util;
import utility.Variable;

public class Enemy extends CharacterBehavior2 {

	private static final int dirX[] = { 0, 0, 1, -1 };
	private static final int dirY[] = { -1, 1, 0, 0 };
	private static final double waitTime = 4000;

	private Node map[][];
	private int timer;
	private boolean isComeback, isImmune;
	private double upY, distance;

	public Enemy(double x, double y) {
		this.x = x;
		this.y = y;
		anim = new Animation();
		anim.setAnimState(Variable.STATE_RUN);
		for (int i = 0; i < 6; i++) {
			int duration = 100;
			if (i == Variable.STATE_DEAD)
				duration = 1000;
			anim.addAnim(i, Asset.getInstance().getEnemy(i), duration);
		}
	}

	@Override
	public void update() {
		if (isDead) {
			EnemyManager.getInstance().getItems().remove(this);
			return;
		}

		if (distance >= Variable.TILE_SIZE) {
			distance = 0;
			isImmune = false;
		}

		int move = getMove();
		printMove(move - 100);
		if (isComeback) {
			comeback(move);
			return;
		}

		if (move == -1)
			trap();
		else
			controlMove(move);

		super.update();
	}

	protected void onAir() {
		if (lookAt == Variable.LEFT || lookAt == Variable.RIGHT) {
			updateX(veloX);
			anim.setAnimState(Variable.STATE_RUN);
		} else {
			this.x = repositionX();
		}

		int cx = (int) (repositionX() / Variable.TILE_SIZE);
		int cy = (int) (repositionY() / Variable.TILE_SIZE);
		Brick brick = (Brick) BrickManager.getInstance().getItem(Util.getKey(cx, cy));
		if (brick != null && brick.isEmpty()) {
			veloY = 0;
			y = repositionY();
			trap();
			return;
		}

		veloY = 1;
		updateY(veloY);
		anim.setAnimState(Variable.STATE_SKYDIVE);
	}

	private void printMove(int move) {
		if (move == Variable.RIGHT)
			System.out.println("kanan");
		if (move == Variable.LEFT)
			System.out.println("kiri");
		if (move == Variable.UP)
			System.out.println("atas");
		if (move == Variable.DOWN)
			System.out.println("bawah");
		if (move == -1)
			System.out.println("no");
	}

	private void trap() {
		timer += Time.deltaTime;
		if (timer >= waitTime) {
			timer = 0;
			isComeback = true;
			upY = ((repositionY() / Variable.TILE_SIZE) - 1) * Variable.TILE_SIZE;
		}
	}

	private void comeback(int move) {
		if (startDeadAnim) {
			isImmune = false;
			isComeback = false;
			return;
		}
		updateY(-0.5);
		anim.updateAnim();

		if (Math.abs(y - upY) < Util.getTolerance()) {
			this.y = upY;
			isComeback = false;
			isImmune = true;
			distance = 0;
		}
	}

	private void controlMove(int move) {
		lookAt = move;

		if (!onGround && targetLadder == null && targetPole == null) {
			return;
		}

		if (move == Variable.DOWN || move == Variable.UP) {
			anim.setAnimState(Variable.STATE_CLIMB_LADDER);
			veloY = move == Variable.DOWN ? 1 : -1;
			veloX = 0;
			this.x = repositionX();
			fallFromPole = move == Variable.DOWN;
		} else if (move == Variable.RIGHT || move == Variable.LEFT) {
			anim.setAnimState(Variable.STATE_CLIMB_LADDER);
			veloX = move == Variable.RIGHT ? 1 : -1;
			veloY = 0;
			this.y = repositionY();
		}
	}

	protected void checkCollision() {
		super.checkCollision();
		if (isImmune)
			onGround = true;
	}

	private int getMove() {
		resetMap();
		int cx = (int) (repositionX() / Variable.TILE_SIZE);
		int cy = (int) (repositionY() / Variable.TILE_SIZE);
		int px = (int) (Player.getInstance().repositionX() / Variable.TILE_SIZE);
		int py = (int) (Player.getInstance().repositionY() / Variable.TILE_SIZE);
		Node target = map[py][px];
		Node currentPos = map[cy][cx];
		boolean found = false;

		if (currentPos.key == 0 && map[currentPos.y + 1][currentPos.x].key == 0) {
			return Variable.DOWN;
		}

		Vector<Node> openList = new Vector<>();
		openList.add(map[cy][cx]);
		// GameScene.pts.clear();
		while (openList.size() > 0) {
			Node curr = openList.get(0);
			if (curr.x == px && curr.y == py) {
				found = true;
				break;
			}

			openList.remove(0);
			curr.visited = true;

			for (int i = 0; i < dirX.length; i++) {
				int ck = curr.key;
				// gabisa naik
				if (ck != Variable.MAP_LADDER && i == 0)
					continue;

				// gabisa ke kanan kiri, karena dibawahnya kosong
				if (curr.key == 0 && map[curr.y + 1][curr.x].key == 0 && i >= 2)
					continue;

				int nx = curr.x + dirX[i];
				int ny = curr.y + dirY[i];
				if (nx < 0 || nx >= map[0].length || ny < 0 || ny >= map.length)
					continue;

				Node neighbor = map[ny][nx];
				int nk = neighbor.key;
				if (neighbor.visited || nk == Variable.MAP_ROCK || nk == Variable.MAP_BRICK)
					continue;

				neighbor.parent = curr;
				openList.add(neighbor);
			}
		}

		if (!found)
			return -1;

		while (target.parent != currentPos)
			target = target.parent;

		if (target.x < currentPos.x)
			return Variable.LEFT;
		else if (target.x > currentPos.x)
			return Variable.RIGHT;
		else if (target.y < currentPos.y)
			return Variable.UP;
		else if (target.y > currentPos.y)
			return Variable.DOWN;
		return -1;
	}

	private void initMap() {
		map = new Node[Map.getInstance().getMap().length][Map.getInstance().getMap()[0].length];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new Node(j, i, Map.getInstance().getMap()[i][j]);
			}
		}
	}

	private void resetMap() {
		if (map == null)
			initMap();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j].visited = false;
			}
		}
	}

	protected void updateX(double velocity) {
		double v = 0.03;
		distance += Math.abs(velocity * Time.deltaTime * v);
		x += velocity * Time.deltaTime * v;
	}

	protected void updateY(double velocity) {
		distance += Math.abs(velocity * Time.deltaTime * 0.05);
		y += velocity * Time.deltaTime * 0.05;
	}

	private class Node {
		int x, y, key;
		boolean visited;
		Node parent;

		Node(int x, int y, int key) {
			this.x = x;
			this.y = y;
			this.key = key;
			visited = false;
			parent = null;
		}
	}

}
