package gameobject;

import java.awt.Graphics2D;

import lib.Animation;
import lib.Inanimate;
import utility.Asset;
import utility.Time;
import utility.Variable;

public class Brick extends Inanimate {

	private static final double waitTime = 8000;
	private Animation anim;
	private double timer;
	
	public Brick(double x, double y) {
		super(x, y);
		anim = new Animation();
		anim.setAnimState(Variable.BRICK_IDLE_STATE);
		for (int i = 0; i < 3; i++) {
			anim.addAnim(i, Asset.getInstance().getBrick(i), 100);
		}
	}

	public void destroy() {
		anim.setAnimState(Variable.BRICK_DESTROY_STATE);
		timer = 0;
	}

	public boolean isEmpty() {
		return anim.getAnimState() != Variable.BRICK_IDLE_STATE;
	}

	@Override
	public void update() {
		if (anim.getAnimState() == Variable.BRICK_DESTROY_STATE && anim.isAnimLastIndex()) {
			timer += Time.deltaTime;
			if (timer > waitTime) {
				anim.setAnimState(Variable.BRICK_APPEAR_STATE);
				timer = 0;
			}
			return;
		}

		if (anim.getAnimState() == Variable.BRICK_APPEAR_STATE && anim.isAnimLastIndex()) {
			anim.setAnimState(Variable.BRICK_IDLE_STATE);	
			return;
		}

		anim.updateAnim();
	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);
		g.drawImage(anim.getImage(), af, null);
	}

}
