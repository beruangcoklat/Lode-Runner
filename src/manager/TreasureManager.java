package manager;

import java.awt.image.BufferedImage;

import lib.Animation;
import lib.ObjectManager;
import utility.Asset;

public class TreasureManager extends ObjectManager {

	private Animation anim;

	private TreasureManager() {
		anim = new Animation();
		anim.addAnim(0, Asset.getInstance().getTreasure(), 300);
	}

	private static TreasureManager instance = new TreasureManager();

	public static TreasureManager getInstance() {
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
