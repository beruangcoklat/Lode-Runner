package manager;

import java.awt.image.BufferedImage;

import lib.Animation;
import lib.ObjectManager;
import utility.Asset;

public class LadderManager extends ObjectManager {

	private Animation anim;

	private LadderManager() {
		anim = new Animation();
		anim.addAnim(0, Asset.getInstance().getLadder(), 300);
	}

	private static LadderManager instance = new LadderManager();

	public static LadderManager getInstance() {
		return instance;
	}

	public void update() {
		super.update();
		anim.updateAnim();
	}

	public BufferedImage getImage() {
		return anim.getImage();
	}

}
