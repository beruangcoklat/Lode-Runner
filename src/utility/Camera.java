package utility;

import gameobject.Player;

public class Camera {

	private final double SPEED = 0.3, WAIT_TIME = 1000;
	private int width, height;
	private double x, y, timer;
	private int state;
	private boolean wait;

	private Camera() {
		init();
	}
	
	public void init(){
		width = 12 * Variable.TILE_SIZE;
		height = 10 * Variable.TILE_SIZE;
		x = 0;
		y = Variable.REAL_HEIGHT - height;
		state = Variable.RIGHT;
		wait = true;
	}

	public boolean preGame() {
		if (wait) {
			timer += Time.deltaTime;
			if (timer >= WAIT_TIME) {
				timer = 0;
				wait = false;
			}
			return false;
		}

		if (state == Variable.RIGHT)
			x += Time.deltaTime * SPEED;
		else if (state == Variable.LEFT)
			x += Time.deltaTime * -SPEED;
		else if (state == Variable.UP)
			y += Time.deltaTime * -SPEED;
		else if (state == Variable.DOWN)
			y += Time.deltaTime * SPEED;

		if (state == Variable.RIGHT && x + width >= Variable.REAL_WIDTH) {
			x = Variable.REAL_WIDTH - width;
			state = Variable.UP;
			wait = true;
		} else if (state == Variable.UP && y <= 0) {
			y = 0;
			state = Variable.LEFT;
			wait = true;
		} else if (state == Variable.LEFT && x <= 0) {
			x = 0;
			state = Variable.DOWN;
			wait = true;
		} else if (state == Variable.DOWN && y + height >= Variable.REAL_HEIGHT) {
			y = Variable.REAL_HEIGHT - height;
			return true;
		}
		return false;
	}

	public void update() {
		x = Player.getInstance().getX() - 100;
		y = Player.getInstance().getY() - 100;

		if (x < 0)
			x = 0;
		else if (x + width > Variable.REAL_WIDTH)
			x = Variable.REAL_WIDTH - width;

		if (y < 0)
			y = 0;
		else if (y + height > Variable.REAL_HEIGHT)
			y = Variable.REAL_HEIGHT - height;

	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		init();
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private static Camera instance = new Camera();

	public static Camera getInstance() {
		return instance;
	}

}
