package mazegame.generator;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.system.Vector2i;

public class MazeInformation implements Comparable<MazeInformation> {
	protected boolean boardMatrixInitialized;
	protected int[][] boardValues;
	
	public List<Boolean> chromosome;
	
	public List<Vector2i> coinsPositions;
	
	public double totalError;
	public double fitness;
	
	public double collisionError;
	public boolean characterCollides;
	public boolean targetCollides;
	
	public double reachabilityError;
	public boolean isReachable;
	
	public int openedPointsCount;
	public double openedPointsError;
	
	public double validityError;
	
	public int shortestPathLength;
	public int targetShortestPathLength;
	public List<Vector2i> shortestPath;
	public List<Vector2i> reachablePoints;
	public double shortestPathError;
	
	public int wallsCount;
	public double wallsRatio;
	public double targetWallsRatio;
	public double wallsRatioError;

	public int checkerboardCount;
	public double checkerboardError;

	public int wallSquaresCount;
	public double wallSquaresError;
	
	public MazeInformation() {
		boardMatrixInitialized = false;
		shortestPath = new ArrayList<>();
		reachablePoints = new ArrayList<>();
	}
	
	public void createBoard() {
		boardMatrixInitialized = true;
		BoardCreator generator = new BoardCreator();
		boardValues = generator.getBoardFromList(chromosome);
		coinsPositions = generator.getCoinsList();
		reachablePoints = generator.getReachabilityList();
	}
		
	public int getCell(int x, int y) {
		if(!boardMatrixInitialized) {
			createBoard();
		}
		return boardValues[x][y];
	}

	@Override
	public int compareTo(MazeInformation o) {
        if(fitness < o.fitness)
            return -1;
        else if(fitness > o.fitness) {
            return 1;
        }
        return 0;
	}

	public String toConsoleString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Maze Information ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" 
				+ "\n        reachable:                    " + isReachable 
				+ "\n        reachability error:           " + reachabilityError 
				+ "\n        opened spots count:           " + openedPointsCount
				+ "\n        opened spots error:           " + openedPointsError
				+ "\n        player position collides:     " + characterCollides
				+ "\n        target position collides:     " + targetCollides
				+ "\n        collision error:              " + collisionError
				+ "\n        total validity error:         " + validityError 
				+ "\n"
				+ "\n        shortest path length:         " + shortestPathLength 
				+ "\n        target shortest path length:  " + targetShortestPathLength 
				+ "\n        shortest path error:          " + shortestPathError 
				+ "\n"
				+ "\n        walls count:                  " + wallsCount 
				+ "\n        walls ratio:                  " + wallsRatio
				+ "\n        target walls ratio:           " + targetWallsRatio
				+ "\n        walls ratio error:            " + wallsRatioError
				+ "\n"
				+ "\n        wall squares count:           " + wallSquaresCount
				+ "\n        wall squares error:           " + wallSquaresError 
				+ "\n"
				+ "\n        checkerboard count:           " + checkerboardCount
				+ "\n        checkerboard error:           " + checkerboardError 
				+ "\n"
				+ "\n        total error:                  " + totalError 
				+ "\n        fitness:                      " + fitness
				+ "\n");
		
		return builder.toString();

	}
	
	@Override
	public String toString() {
		return toConsoleString();
	}
}
