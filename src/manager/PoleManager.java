package manager;

import lib.ObjectManager;

public class PoleManager extends ObjectManager {

	private PoleManager() {

	}

	private static PoleManager instance = new PoleManager();

	public static PoleManager getInstance() {
		return instance;
	}

}
