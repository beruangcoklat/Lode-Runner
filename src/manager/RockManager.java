package manager;

import lib.ObjectManager;

public class RockManager extends ObjectManager {

	private RockManager() {

	}

	private static RockManager instance = new RockManager();

	public static RockManager getInstance() {
		return instance;
	}

}
