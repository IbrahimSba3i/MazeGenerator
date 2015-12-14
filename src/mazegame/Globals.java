package mazegame;

import geneticalgorithms.Utility;

public abstract class Globals {
	
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 640;
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;
	public static final int TILES_COLS = WINDOW_WIDTH / TILE_WIDTH;
	public static final int TILES_ROWS = WINDOW_HEIGHT / TILE_HEIGHT;
	
	public static final int PLAYER_WIDTH = 32;
	public static final int PAYER_HEIGHT = 41;	
	
	public static final int COIN_WIDTH = 27;
	public static final int COIN_HEIGHT = 27;
	
	public static final int GROUND = 16;
	public static final int BLANK = 17;
	
	public static final int MAX_EPOCHS = 100;
	
	public static final double ERROR_REACHABILITY = 20.0;
	public static final double ERROR_OPENEDSPOT = 2.0;
	public static final double ERROR_COLLISION = 25.0;
	public static final double ERROR_SHORTEST_PATH = 0.025;
	public static final double ERROR_WALL_SQUARES = 0.01;
	public static final double ERROR_CHECKERBOARD = 0.00;
	
	public static final boolean REMOVE_USELESS_BLOCKS = false;

	public static int PLAYER_X = 1;
	public static int PLAYER_Y = 1;
	
	public static int TARGET_X = 23;
	public static int TARGET_Y = 17;
	
	public static int EASY_SHORTEST_PATH = 40;
	public static int MEDIUM_SHORTEST_PATH = 60;
	public static int HARD_ShORTEST_PATH = 80;
	
	public static double EASY_WALLS_RATIO = 0.45;
	public static double MEDIUM_WALLS_RATIO = 0.45;
	public static double HARD_WALLS_RATIO = 0.45;
	

	public static void resetPositins() {
		do {
			PLAYER_X = Utility.rand().nextInt(TILES_COLS - 1) + 1;
			TARGET_X = Utility.rand().nextInt(TILES_COLS - 1) + 1;
			PLAYER_Y = Utility.rand().nextInt(TILES_ROWS - 1) + 1;
			TARGET_Y = Utility.rand().nextInt(TILES_ROWS - 1) + 1;
		} while(Math.abs(PLAYER_X - TARGET_X) + Math.abs(PLAYER_Y - TARGET_Y) < Math.min(TILES_COLS, TILES_ROWS));
	}
}
