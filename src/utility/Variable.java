package utility;

public class Variable {
	
	public static int REAL_WIDTH, REAL_HEIGHT;
	
	public static final int LIMIT_FPS = 60,
							TILE_SIZE = 50,
							WIN = 0,
							LOSE = 1;
	
	public static final int STATE_RUN = 0,
							STATE_SKYDIVE = 1,
							STATE_CLIMB_LADDER = 2,
							STATE_CLIMB_POLE = 3,
							STATE_DEAD = 4,
							STATE_IDLE = 5;	
	
	public static final int BRICK_IDLE_STATE = 0,
							BRICK_DESTROY_STATE = 1,
							BRICK_APPEAR_STATE = 2;
	
	public static final int RIGHT = 0,
							LEFT = 1,
							UP = 2,
							DOWN = 3;
	
	public static final int MAP_EMPTY = 0,
							MAP_BRICK = 1,
							MAP_ROCK = 2,
							MAP_LADDER = 3,
							MAP_POLE = 4,
							MAP_TREASURE = 5,
							MAP_PLAYER = 6,
							MAP_ENEMY = 7;
	
}
