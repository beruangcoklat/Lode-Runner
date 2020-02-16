package lib;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import gameobject.Brick;
import manager.BrickManager;
import manager.LadderManager;
import manager.PoleManager;
import manager.RockManager;
import utility.Camera;
import utility.Util;
import utility.Variable;

public abstract class CharacterBehavior2 extends GameObject {

	protected Animation anim;
	protected double veloX, veloY;
	protected int lookAt;
	protected GameObject targetLadder, targetPole;
	protected boolean onGround, onTopLadder, fallFromPole, isDead, startDeadAnim, isTop;

	protected abstract void updateX(double velocity);

	protected abstract void updateY(double velocity);

	@Override
	public void render(Graphics2D g) {
		AffineTransform af = new AffineTransform();
		if (lookAt == Variable.RIGHT) {
			af.translate(x + width - Camera.getInstance().getX(), y - Camera.getInstance().getY());
			af.scale(width / 16 * -1, height / 16);
		} else {
			af.translate(x - Camera.getInstance().getX(), y - Camera.getInstance().getY());
			af.scale(width / 16, height / 16);
		}
		g.drawImage(anim.getImage(), af, null);
	}

	@Override
	public void update() {
		if (startDeadAnim) {
			anim.updateAnim();
			if (anim.isAnimLastIndex()) {
				isDead = true;
				return;
			}
			return;
		}

		if (getDead()) {
			startDeadAnim = true;
			anim.setAnimState(Variable.STATE_DEAD);
			return;
		}

		controlAnim();
		updateLadder();
		updatePole();
		checkCollision();

		if (targetLadder != null) {
			collideLadder();
		} else if (targetPole != null) {
			collidePole();
		} else {
			if (onGround) {
				onGround();
			} else {
				onAir();
			}
		}
	}

	protected void collideLadder() {
		if (fallFromPole) {
			fallFromPole = false;
			veloY = 0;
			this.y = targetLadder.getY();
		}

		updateX(veloX);
		updateY(veloY);

		if (onTopLadder && lookAt == Variable.UP) {
			anim.setAnimState(Variable.STATE_IDLE);
			veloY = 0;
		} else if (veloY == 0 && onGround) {
			anim.setAnimState(veloX == 0 ? Variable.STATE_IDLE : Variable.STATE_RUN);
			anim.updateAnim();
		} else if (anim.getAnimState() == Variable.STATE_CLIMB_POLE) {
			anim.setAnimState(Variable.STATE_CLIMB_LADDER);
		}
	}

	protected void collidePole() {
		if (fallFromPole) {
			veloY = 1;
			updateY(veloY);
			anim.setAnimState(Variable.STATE_SKYDIVE);
		} else {
			updateY(veloY);
			updateX(veloX);
			anim.setAnimState(Variable.STATE_CLIMB_POLE);
		}
	}

	protected void onGround() {
		fallFromPole = false;
		updateY(veloY);
		updateX(veloX);
		if (anim.getAnimState() == Variable.STATE_SKYDIVE)
			anim.setAnimState(veloX == 0 ? Variable.STATE_IDLE : Variable.STATE_RUN);
		else if (veloX != 0)
			anim.setAnimState(Variable.STATE_RUN);
		else
			anim.setAnimState(Variable.STATE_IDLE);
	}

	protected void onAir() {
		this.x = repositionX();
		veloY = 1;
		updateY(veloY);
		anim.setAnimState(Variable.STATE_SKYDIVE);
	}

	public double repositionX() {
		int start = (int) this.x / Variable.TILE_SIZE;
		int plus = this.x % Variable.TILE_SIZE < 15 ? 0 : 1;
		double newX = (start + plus) * Variable.TILE_SIZE;
		return newX;
	}

	public double repositionY() {
		int start = (int) this.y / Variable.TILE_SIZE;
		int plus = this.y % Variable.TILE_SIZE < 15 ? 0 : 1;
		double newY = (start + plus) * Variable.TILE_SIZE;
		return newY;
	}

	protected boolean getDead() {
		int tx = (int) (repositionX() / Variable.TILE_SIZE);
		int ty = (int) (repositionY() / Variable.TILE_SIZE);
		Brick brick = (Brick) BrickManager.getInstance().getItem(Util.getKey(tx, ty));
		if (brick != null && !brick.isEmpty()) {
			return true;
		}
		return false;
	}

	protected void checkCollision() {
		if (veloY == 1)
			isTop = false;

		onGround = false;

		for (GameObject b : BrickManager.getInstance().getItems()) {
			if (((Brick) b).isEmpty())
				continue;
			collideDown(b);
			isTop = collideTop(b) ? true : isTop;
			collideRight(b);
			collideLeft(b);
		}

		for (GameObject b : RockManager.getInstance().getItems()) {
			collideDown(b);
			isTop = collideTop(b) ? true : isTop;
			collideRight(b);
			collideLeft(b);
		}
	}

	// hidden

	private void updateLadder() {
		targetLadder = null;
		onTopLadder = false;
		double max = Double.MIN_VALUE;
		for (GameObject ladder : LadderManager.getInstance().getItems()) {
			if (collideHorizontal(ladder) && collideVertical(ladder)) {
				double curr = getCollideVertical(ladder);
				if (curr > max) {
					max = curr;
					targetLadder = ladder;
				}
			}
		}
		if (targetLadder != null && max < Util.getTolerance())
			onTopLadder = true;
	}

	private void updatePole() {
		targetPole = null;

		// nentuin targetPole
		double maxHorizontal = Double.MIN_VALUE;
		// sesuain posisi pole
		double maxVertical = Double.MIN_VALUE;

		for (GameObject pole : PoleManager.getInstance().getItems()) {
			if (collideHorizontal(pole) && collideVertical(pole)) {
				double curr = getCollideHorizontal(pole);
				if (curr > maxHorizontal) {
					targetPole = pole;
					maxHorizontal = curr;
				}

				curr = getCollideVertical(pole);
				if (curr > maxVertical)
					maxVertical = curr;
			}
		}

		if (targetPole == null) {
			fallFromPole = false;
			return;
		}

		if (fallFromPole)
			return;

		if (targetPole != null && maxVertical > (Variable.TILE_SIZE - Util.getTolerance())) {
			this.y = targetPole.getY();
		} else if (targetPole != null && maxVertical < Util.getTolerance()) {
			fallFromPole = false;
			veloY = 0;
			this.y = targetPole.getY();
		} else {
			fallFromPole = true;
			veloY = 1;
		}
	}

	private void controlAnim() {
		if (targetLadder != null && veloY == 0)
			return;
		if (targetPole != null && veloX == 0)
			return;
		anim.updateAnim();
	}

	private boolean collideTop(GameObject other) {
		if (collideHorizontal(other) && other.getY() + other.getHeight() > y && y > other.getY()) {
			if (getCollideVertical(other) > Util.getTolerance()) {
				y = repositionY();
				veloY = 0;
				return true;
			}
		}
		return false;
	}

	private boolean collideDown(GameObject other) {
		if (collideHorizontal(other) && y + height >= other.getY() && y < other.getY()) {
			if (getCollideHorizontal(other) > Util.getTolerance()) {
				y = repositionY();
				onGround = true;
				return true;
			}
		}
		return false;
	}

	private void collideRight(GameObject other) {
		if (collideVertical(other) && x + width >= other.getX() && x < other.getX() && veloX == 1) {
			if (getCollideVertical(other) > Util.getTolerance()) {
				veloX = 0;
				x = repositionX();
			}
		}
	}

	private void collideLeft(GameObject other) {
		if (collideVertical(other) && x <= other.getX() + other.getWidth() && x > other.getX() && veloX == -1) {
			if (getCollideVertical(other) > Util.getTolerance()) {
				veloX = 0;
				x = repositionX();
			}
		}
	}

}
