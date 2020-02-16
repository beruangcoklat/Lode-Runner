package manager;

import lib.ObjectManager;

public class BrickManager extends ObjectManager {

	private BrickManager() {

	}

	private static BrickManager instance = new BrickManager();

	public static BrickManager getInstance() {
		return instance;
	}

}
