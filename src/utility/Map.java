package utility;

import gameobject.Brick;
import gameobject.Enemy;
import gameobject.Ladder;
import gameobject.Player;
import gameobject.Pole;
import gameobject.Rock;
import gameobject.Treasure;
import manager.BrickManager;
import manager.EnemyManager;
import manager.LadderManager;
import manager.PoleManager;
import manager.RockManager;
import manager.TreasureManager;

public class Map {

	// 0 empty
	// 1 brick
	// 2 rock
	// 3 ladder
	// 4 pole
	// 5 treasure
	// 6 player
	// 7 enemy
	private int map[][];
	private int mapMaster[][] = new int[][] { { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
			{ 2, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 3, 0, 0, 2 },
			{ 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 3, 0, 0, 2 },
			{ 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 2 },
			{ 2, 0, 0, 0, 0, 0, 0, 3, 4, 4, 4, 4, 0, 0, 3, 0, 0, 2 },
			{ 2, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 3, 0, 0, 2 },
			{ 2, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 7, 0, 3, 0, 0, 2 },
			{ 2, 0, 0, 0, 0, 0, 0, 3, 4, 4, 4, 4, 0, 0, 3, 0, 0, 2 },
			{ 2, 0, 0, 0, 3, 1, 1, 1, 0, 0, 0, 0, 0, 0, 3, 0, 0, 2 },
			{ 2, 1, 1, 1, 3, 0, 0, 0, 0, 3, 0, 0, 0, 0, 3, 0, 0, 2 },
			{ 2, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 2 },
			{ 2, 6, 0, 0, 3, 7, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 2 },
			{ 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2 },
			{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 }, };

	private Map() {
		map = new int[mapMaster.length][mapMaster[0].length];
		initMap();
		initObject();
		Variable.REAL_WIDTH = map[0].length * Variable.TILE_SIZE;
		Variable.REAL_HEIGHT = map.length * Variable.TILE_SIZE;
	}

	public void reset() {
		initMap();
		initObject();
	}

	private void initMap() {
		for (int i = 0; i < mapMaster.length; i++) {
			for (int j = 0; j < mapMaster[i].length; j++) {
				map[i][j] = mapMaster[i][j];
			}
		}
	}

	public final int[][] getMap() {
		return map;
	}

	private void initObject() {
		BrickManager.getInstance().getItems().clear();
		RockManager.getInstance().getItems().clear();
		LadderManager.getInstance().getItems().clear();
		PoleManager.getInstance().getItems().clear();
		TreasureManager.getInstance().getItems().clear();
		EnemyManager.getInstance().getItems().clear();
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				int curr = map[i][j];
				if (curr == Variable.MAP_BRICK)
					BrickManager.getInstance().addItem(new Brick(j * Variable.TILE_SIZE, i * Variable.TILE_SIZE));
				else if (curr == Variable.MAP_ROCK)
					RockManager.getInstance().addItem(new Rock(j * Variable.TILE_SIZE, i * Variable.TILE_SIZE));
				else if (curr == Variable.MAP_LADDER)
					LadderManager.getInstance().addItem(new Ladder(j * Variable.TILE_SIZE, i * Variable.TILE_SIZE));
				else if (curr == Variable.MAP_POLE)
					PoleManager.getInstance().addItem(new Pole(j * Variable.TILE_SIZE, i * Variable.TILE_SIZE));
				else if (curr == Variable.MAP_TREASURE) {
					TreasureManager.getInstance().addItem(new Treasure(j * Variable.TILE_SIZE, i * Variable.TILE_SIZE));
					map[i][j] = 0;
				} else if (curr == Variable.MAP_PLAYER) {
					Player.getInstance().setCoordinate(j * Variable.TILE_SIZE, i * Variable.TILE_SIZE);
					map[i][j] = 0;
				} else if (curr == Variable.MAP_ENEMY) {
					EnemyManager.getInstance().addItem(new Enemy(j * Variable.TILE_SIZE, i * Variable.TILE_SIZE));
					map[i][j] = 0;
				}
			}
		}
	}

	private static Map instance = new Map();

	public static Map getInstance() {
		return instance;
	}

}
