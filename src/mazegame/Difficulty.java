package mazegame;

public enum Difficulty {
	EASY(Globals.EASY_SHORTEST_PATH, Globals.EASY_WALLS_RATIO),
	MEDIUM(Globals.MEDIUM_SHORTEST_PATH, Globals.MEDIUM_WALLS_RATIO),
	HARD(Globals.HARD_ShORTEST_PATH, Globals.HARD_WALLS_RATIO);
	
	private int targetShortestPath;
	private double targetWallsRatio;

	private Difficulty(int targetShortestPath, double targetWallsRatio) {
		this.targetShortestPath = targetShortestPath;
		this.targetWallsRatio = targetWallsRatio;
	}
	
	public int getTargetShortestPath() {
		return targetShortestPath;
	}
	
	public double getTargetWallsRatio() {
		return targetWallsRatio;
	}
}
