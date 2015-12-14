package mazegame.generator;

import geneticalgorithms.FitnessFunction;
import geneticalgorithms.chromosome.Chromosome;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import mazegame.Globals;
import mazegame.Difficulty;
import mazegame.GameManager;

import org.jsfml.system.Vector2i;

public class MazeFitnessFunction implements FitnessFunction<Boolean> {
	protected int[][] shortestPath;
	protected Vector2i[][] pathVia;
	protected MazeInformation mazeInfo;
	protected Difficulty difficulty;
	protected ErrorRecorder errorRecorder;
	
	public MazeFitnessFunction(Difficulty difficulty) {
		shortestPath = new int[Globals.TILES_COLS][Globals.TILES_ROWS];
		pathVia = new Vector2i[Globals.TILES_COLS][Globals.TILES_ROWS];
		this.difficulty = difficulty;
		errorRecorder = null;
	}
	
	protected void clearShortestPath() {
		for(int x = 0; x < shortestPath.length; x++) {
			for(int y = 0; y < shortestPath[x].length; y++) {
				shortestPath[x][y] = -1;
			}
		}
		
		for(int x = 0; x < pathVia.length; x++) {
			for(int y = 0; y < pathVia[x].length; y++) {
				pathVia[x][y] = new Vector2i(-1, -1);
			}
		}
	}
	
	protected void traverseGrid() {
		int[] dx = {0, -1, 1, 0};
		int[] dy = {-1, 0, 0, 1};
		mazeInfo.openedPointsCount = 0;
		
		Queue<DistancePositionEntry> queue = new LinkedList<>();
		queue.add(new DistancePositionEntry(Globals.PLAYER_X, Globals.PLAYER_Y, 0));
		shortestPath[queue.peek().x][queue.peek().y] = 0;
		
		while(!queue.isEmpty()) {
			DistancePositionEntry current = queue.remove();
			for(int i = 0; i < dx.length; i++) {
				int x = current.x + dx[i];
				int y = current.y + dy[i];
				int distance = current.distance + 1;
				if(GameManager.withinBounds(x, y)) {
					if(shortestPath[x][y] == -1 && GameManager.getCell(mazeInfo.chromosome, x, y) == false) {
						shortestPath[x][y] = distance;
						pathVia[x][y] = new Vector2i(current.x, current.y);
						queue.add(new DistancePositionEntry(x, y, distance));
					}
				} else {
					mazeInfo.openedPointsCount++;
				}
			}
		}
		
		savePathToList(new Vector2i(Globals.TARGET_X, Globals.TARGET_Y), mazeInfo.shortestPath);
	}
	
	protected void savePathToList(Vector2i point, List<Vector2i> positions) {
		if(point.x != -1 && point.y != -1) {
			savePathToList(pathVia[point.x][point.y], positions);
			positions.add(point);
		}
	}
	
	protected double getValidityError() {
		mazeInfo.isReachable = (shortestPath[Globals.TARGET_X][Globals.TARGET_Y] != -1);
		mazeInfo.reachabilityError = mazeInfo.isReachable? 0:Globals.ERROR_REACHABILITY;
		mazeInfo.openedPointsError = mazeInfo.openedPointsCount * Globals.ERROR_OPENEDSPOT;
		mazeInfo.characterCollides = GameManager.getCell(mazeInfo.chromosome, Globals.PLAYER_X, Globals.PLAYER_Y);
		mazeInfo.targetCollides = GameManager.getCell(mazeInfo.chromosome, Globals.TARGET_X, Globals.TARGET_Y);
		mazeInfo.collisionError = (mazeInfo.characterCollides? 25.0 : 0.0) + (mazeInfo.targetCollides? Globals.ERROR_COLLISION : 0.0);
		mazeInfo.validityError = mazeInfo.openedPointsError + mazeInfo.reachabilityError + mazeInfo.collisionError;
		return mazeInfo.validityError;
	}
	
	protected double getShortestPathError() {
		mazeInfo.targetShortestPathLength = difficulty.getTargetShortestPath();
		mazeInfo.shortestPathLength = shortestPath[Globals.TARGET_X][Globals.TARGET_Y];
		mazeInfo.shortestPathError = Globals.ERROR_SHORTEST_PATH * Math.abs(mazeInfo.targetShortestPathLength - mazeInfo.shortestPathLength);
		return mazeInfo.shortestPathError;
	}
	
	protected double getWallsRatioError() {
		mazeInfo.wallsCount = 0;
		for(int i = 0; i <mazeInfo.chromosome.size(); i++) {
			if(mazeInfo.chromosome.get(i).booleanValue())
				mazeInfo.wallsCount++;
		}
		mazeInfo.wallsRatio = (double)mazeInfo.wallsCount / (double)mazeInfo.chromosome.size();
		mazeInfo.targetWallsRatio = difficulty.getTargetWallsRatio();
		mazeInfo.wallsRatioError = Math.abs(mazeInfo.wallsRatio - mazeInfo.targetWallsRatio);
		return mazeInfo.wallsRatioError;
	}
	
	protected double getWallsCollisionError() {
		mazeInfo.wallSquaresCount = 0;
		for(int i = 0; i < Globals.TILES_COLS - 1; i++) {
			for(int j = 0; j < Globals.TILES_ROWS - 1; j++) {
				if( GameManager.getCell(mazeInfo.chromosome, i, j) && 
					GameManager.getCell(mazeInfo.chromosome, i+1, j) && 
					GameManager.getCell(mazeInfo.chromosome, i, j+1) && 
					GameManager.getCell(mazeInfo.chromosome, i+1, j+1)) {
					mazeInfo.wallSquaresCount++;
				}
			}
		}
		mazeInfo.wallSquaresError = Globals.ERROR_WALL_SQUARES * mazeInfo.wallSquaresCount;
		return mazeInfo.wallSquaresError;
	}
	
	protected double getCheckerboardError() {
		mazeInfo.checkerboardCount = 0;
		for(int i = 0; i < Globals.TILES_COLS - 1; i++) {
			for(int j = 0; j < Globals.TILES_ROWS - 1; j++) {
				if(( GameManager.getCell(mazeInfo.chromosome, i, j) && 
					!GameManager.getCell(mazeInfo.chromosome, i+1, j) && 
					!GameManager.getCell(mazeInfo.chromosome, i, j+1) && 
					GameManager.getCell(mazeInfo.chromosome, i+1, j+1))
					||
					( !GameManager.getCell(mazeInfo.chromosome, i, j) && 
							GameManager.getCell(mazeInfo.chromosome, i+1, j) && 
							GameManager.getCell(mazeInfo.chromosome, i, j+1) && 
							!GameManager.getCell(mazeInfo.chromosome, i+1, j+1))
					) {
					mazeInfo.checkerboardCount++;
				}
			}
		}
		mazeInfo.checkerboardError = Globals.ERROR_CHECKERBOARD * mazeInfo.checkerboardCount;
		return mazeInfo.checkerboardError;
	}
	
	protected double getTotalError() {
		double validityError = getValidityError();
		double shortestPathError = getShortestPathError();
		double wallsRatioError = getWallsRatioError();
		double wallsCollisionError = getWallsCollisionError();
		double checkerboardError = getCheckerboardError();
		mazeInfo.totalError = validityError + shortestPathError + wallsRatioError + wallsCollisionError + checkerboardError;
		return mazeInfo.totalError;
	}
	
	@Override
	public double getFitness(List<Boolean> chromosome) {
		mazeInfo = new MazeInformation();
		mazeInfo.chromosome = new Chromosome<Boolean>((Chromosome<Boolean>)chromosome);
		clearShortestPath();
		traverseGrid();
		mazeInfo.fitness = -getTotalError();
		if(errorRecorder != null) {
			errorRecorder.onErrorMeasure(mazeInfo);
		}
		return mazeInfo.fitness;
	}

	public ErrorRecorder getErrorRecorder() {
		return errorRecorder;
	}

	public void setErrorRecorder(ErrorRecorder errorRecorder) {
		this.errorRecorder = errorRecorder;
	}

}
