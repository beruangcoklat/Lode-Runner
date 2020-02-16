package lib;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import utility.Util;
import utility.Variable;

public abstract class ObjectManager {

	protected Vector<GameObject> items;
	protected HashMap<String, GameObject> hashItems;

	public ObjectManager() {
		hashItems = new HashMap<>();
		items = new Vector<>();
	}

	public Vector<GameObject> getItems() {
		return items;
	}

	public GameObject getItem(String key) {
		return hashItems.get(key);
	}

	public void addItem(GameObject obj) {
		items.add(obj);
		int tx = (int) (obj.getX() / Variable.TILE_SIZE);
		int ty = (int) (obj.getY() / Variable.TILE_SIZE);
		hashItems.put(Util.getKey(tx, ty), obj);
	}

	public void update() {
		try {
			Iterator<GameObject> cursor = items.iterator();
			while (cursor.hasNext()) {
				cursor.next().update();
			}
		} catch (Exception e) {

		}
	}

	public void render(Graphics2D g) {
		try {
			Iterator<GameObject> cursor = items.iterator();
			while (cursor.hasNext()) {
				cursor.next().render(g);
			}
		} catch (Exception e) {
		}
	}

}
