package mazegame.generator;

import geneticalgorithms.Utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import mazegame.DirectoryManager;
import mazegame.GameManager;
import mazegame.Globals;

import org.jsfml.system.Vector2i;

public class BoardCreator {
	protected int[][] board = new int[Globals.TILES_COLS][Globals.TILES_ROWS];
	protected List<Vector2i> coinsList;
	protected int[][] shortestPath;
	
	protected void loadBoardFromFile(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(DirectoryManager.getLevelsPath() + fileName));
			String line;
			int y = 0;
			while((line = reader.readLine()) != null) {
				if(line.length() == 0) {
					line = null;
				} else {
					for(int x =0; x<line.length(); x++) {
						board[x][y] = (line.charAt(x) == '.')? 0 : (line.charAt(x) == '#')? 1 : 2;
					}
					y++;
				}
			}
			reader.close();
		} catch(IOException e) {
			System.out.println("Couldn't load level file");
		}
	}
	
	public int[][] getBoardFromFile(String fileName) {
		loadBoardFromFile(fileName);
		adjustWallsForDisplay();
		return board;
	}
	
	protected void generateBoardFromList(List<Boolean> list) {
		for(int x = 0; x < Globals.TILES_COLS; x++) {
			for(int y = 0; y < Globals.TILES_ROWS; y++) {
				board[x][y] = GameManager.getCell(list, x, y)? 1 : 0;
			}
		}
		
		for(int y = 0; y < Globals.TILES_ROWS; y++) {
			for(int x = 0; x < Globals.TILES_COLS; x++) {
				System.out.print(board[x][y] + " ");
			}
			System.out.print("\n");;
		}
	}
	
	protected void removeUselessBlocks(int[][] shortestPath) {
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				if(shortestPath[i][j] == -1) {
					board[i][j] = 2;
				}
			}
		}
	}
	
	protected int[][] bfs() {
		
		int[] dx = null;
		int[] dy = null;
		
		if(Globals.DIAGONALS_BFS) {
			dx = new int[]{0, -1, 1, 0, -1, -1, 1, 1};
			dy = new int[]{-1, 0, 0, 1, -1, 1, 1, -1};
		} else {
			dx = new int[]{0, -1, 1, 0};
			dy = new int[]{-1, 0, 0, 1};
		}
		
		shortestPath = new int[Globals.TILES_COLS][Globals.TILES_ROWS];
		for(int x = 0; x < shortestPath.length; x++) {
			for(int y = 0; y < shortestPath[x].length; y++) {
				shortestPath[x][y] = -1;
			}
		}
		
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
					if(shortestPath[x][y] == -1) {
						if(board[x][y] == 0) {
							shortestPath[x][y] = distance;
							queue.add(new DistancePositionEntry(x, y, distance));
						} else {
							shortestPath[x][y] = -2;
						}
					}
				}
			}
		}
		return shortestPath;
	}
	
	public int[][] getBoardFromList(List<Boolean> list) {
		coinsList = new ArrayList<Vector2i>();
		generateBoardFromList(list);
		int[][] shortestPath = bfs();
		if(GameManager.removeUselessBlock()) {
			removeUselessBlocks(shortestPath);
		}
		
		createCoinsList(shortestPath);
		
		adjustWallsForDisplay();
		return board;
	}
	
	protected void createCoinsList(int[][] shortestPath) {
		for(int i = 0; i < Globals.COINS_COUNT; i++) {
			int coinX, coinY;
			do {
				coinX = Utility.rand().nextInt(Globals.TILES_COLS);
				coinY = Utility.rand().nextInt(Globals.TILES_ROWS);
				
			} while((coinX == Globals.PLAYER_X && coinY == Globals.PLAYER_Y) || 
					(coinX == Globals.TARGET_X && coinY == Globals.TARGET_Y) ||
					(shortestPath[coinX][coinY] < 0) ||
					(coinsList.contains(new Vector2i(coinX, coinY))));
			coinsList.add(new Vector2i(coinX, coinY));
		}
	}

	protected int getNumber(int x, int y) {
		int left = (x < 1)? 0 : (board[x-1][y] & 1) << 2;
		int right = (x >= board.length - 1)? 0 : (board[x+1][y] & 1) << 1;
		int up = (y < 1)? 0 : (board[x][y-1] & 1) << 3;
		int down = (y >= board[x].length - 1)? 0 : (board[x][y+1] & 1);
		
		return up | left | right | down;
	}
	
	protected void adjustWallsForDisplay() {
		int[][] A = new int[board.length][board[0].length];
		for(int i = 0; i<board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				if(board[i][j] == 0) {
					A[i][j] = Globals.GROUND;
				} else if(board[i][j] == 2) {
					A[i][j] = Globals.BLANK;
				} else {
					A[i][j] = getNumber(i, j);
				}
			}
		}
		board = A;
	}

	public List<Vector2i> getCoinsList() {
		return coinsList;
	}

	public List<Vector2i> getReachabilityList() {
		List<Vector2i> reachabilityList = new ArrayList<>();
		for(int i = 0; i < shortestPath.length; i++) {
			for(int j = 0; j < shortestPath[i].length; j++) {
				if(shortestPath[i][j] >= 0) {
					reachabilityList.add(new Vector2i(i, j));
				}
			}
		}
		return reachabilityList;
	}
}
