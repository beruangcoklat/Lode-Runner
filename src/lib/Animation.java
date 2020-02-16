package lib;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Vector;

import utility.Time;
import utility.Variable;

public class Animation {

	private int animState, animIndex;
	private double animDuration;
	private HashMap<Integer, Pair> anim;

	public Animation() {
		animIndex = 0;
		animDuration = 0;
		anim = new HashMap<>();
	}

	public void updateAnim() {
		animDuration += Time.deltaTime;
		if (animDuration >= anim.get(animState).animDuration) {
			animDuration = 0;
			animIndex++;
			if (animIndex >= anim.get(animState).animSprites.size())
				animIndex = 0;
		}
	}

	public BufferedImage getImage() {
		Vector<BufferedImage> vec = anim.get(animState).animSprites;
		if (animIndex >= vec.size())
			animIndex = 0;
		BufferedImage img = vec.get(animIndex);
		return img;
	}

	public void addAnim(int state, Vector<BufferedImage> vec, double animDuration) {
		anim.put(state, new Pair(vec, animDuration));
	}

	public void setAnimState(int animState) {
		if (animState == Variable.STATE_DEAD)
			animIndex = 0;
		this.animState = animState;
	}

	public int getAnimState() {
		return animState;
	}

	public boolean isAnimLastIndex() {
		return anim.get(animState).animSprites.size() - 1 == animIndex;
	}

	private class Pair {
		public Vector<BufferedImage> animSprites;
		public double animDuration;

		public Pair(Vector<BufferedImage> vecAnim, double animDuration) {
			this.animSprites = vecAnim;
			this.animDuration = animDuration;
		}
	}

}
