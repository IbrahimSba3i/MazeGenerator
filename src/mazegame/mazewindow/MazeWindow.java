package mazegame.mazewindow;

import gamewindow.eventlisteners.KeyPressedListener;
import gamewindow.system.GameWindow;

import java.util.ArrayList;
import java.util.List;

import mazegame.GameManager;
import mazegame.Globals;
import mazegame.TexturesManager;
import mazegame.generator.MazeInformation;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

public class MazeWindow extends GameWindow {
	private Sprite[][] board;
	
	private Player player;
	private List<Coin> coins;
	
	private Vector2i playerPosition;
	private Vector2i targetPosition;

	private Text youWonText;
	private Text youLostText;

	private RectangleShape shade;
	private boolean gameWon;
	private boolean gameLost;
	
	private Sprite endPoint;
	
	private MazeInformation mazeInformation;
	
	private RectangleShape console;
	private Text stats;
	
	private List<RectangleShape> path;
	private List<RectangleShape> reach;
	
	private boolean pathShown;
	private boolean consoleOpened;
	private boolean reachabilityShown;
	
	private Clock clk;
	private Text timer;
	private RectangleShape bgPart;
	
	private Text score;
	private int scoreCounter;
	
	private int counter;
	
	public MazeWindow(GameWindow parent, MazeInformation mazeInformation) {
		super(parent);
		this.mazeInformation = mazeInformation;
		this.playerPosition = new Vector2i(Globals.PLAYER_X, Globals.PLAYER_Y);
		this.targetPosition = new Vector2i(Globals.TARGET_X, Globals.TARGET_Y);
	}

	protected void createBoard() {
		TexturesManager textures = TexturesManager.getInstance();
		board = new Sprite[Globals.TILES_COLS][];
		for(int i=0; i<board.length; i++) {
			board[i] = new Sprite[Globals.TILES_ROWS];
			for(int j = 0; j<board[i].length; j++) {
				board[i][j] = new Sprite();
				board[i][j].setTexture(textures.getTileTexture(mazeInformation.getCell(i, j)));
				board[i][j].setPosition(i * Globals.TILE_WIDTH, j * Globals.TILE_HEIGHT);
			}
		}
		System.out.println(mazeInformation);
	}

	@Override
	protected void createWindowContent() {
		createBoard();
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++) {
				addElement(board[i][j]);
			}
		}
		
		
		
		clk = new Clock();
		
		counter = (int) (Globals.MAX_TIME - clk.getElapsedTime().asSeconds());
		
		timer = new Text();
		timer.setCharacterSize(40);
		timer.setFont(GameManager.getFont());
		timer.setColor(GameManager.getBlueColor());
		timer.setString(String.valueOf(counter));
		timer.setOrigin(timer.getGlobalBounds().width / 2, timer.getGlobalBounds().height / 2);
		timer.setPosition(Globals.WINDOW_WIDTH + (getWindowWidth() - Globals.WINDOW_WIDTH) / 2, Globals.TIMER_POSITION_WAY);
		
		scoreCounter = 0;
		score = new Text();
		score.setCharacterSize(40);
		score.setFont(GameManager.getFont());
		score.setColor(GameManager.getBlueColor());
		score.setString(String.valueOf(scoreCounter));
		score.setOrigin(score.getGlobalBounds().width / 2, score.getGlobalBounds().height / 2);
		score.setPosition(Globals.WINDOW_WIDTH + (getWindowWidth() - Globals.WINDOW_WIDTH) / 2, Globals.SCORE_POSITION_Y);
		
		
		bgPart = new RectangleShape();
		bgPart.setSize(new Vector2f(getWindowWidth() - Globals.WINDOW_WIDTH, getWindowHeight()));
		bgPart.setPosition(Globals.WINDOW_WIDTH, 0);
		bgPart.setFillColor(GameManager.getGreyColor());
		
		addElement(bgPart);
		addElement(timer);
		addElement(score);
		
		gameWon = false;
		pathShown = false;
		reachabilityShown = false;
		consoleOpened = false;
		
		player = new Player();
		player.setPosition(new Vector2f(Globals.TILE_WIDTH * playerPosition.x, Globals.TILE_HEIGHT * playerPosition.y - (Globals.PAYER_HEIGHT - Globals.TILE_HEIGHT)));
		addElement(player);
		
		coins = new ArrayList<Coin>();
		for(int i = 0; i <Globals.COINS_COUNT; i++) {
			Coin coin = new Coin();
			coin.setPosition(new Vector2f(	Globals.TILE_WIDTH * mazeInformation.coinsPositions.get(i).x + (Globals.TILE_WIDTH - Globals.COIN_WIDTH) / 2,
											Globals.TILE_HEIGHT * mazeInformation.coinsPositions.get(i).y + (Globals.TILE_HEIGHT - Globals.COIN_HEIGHT) / 2));
			coins.add(coin);
			addElement(coin);
		}
		
		endPoint = new Sprite();
		endPoint.setTexture(TexturesManager.getInstance().getEndTexture());
		endPoint.setPosition(targetPosition.x * Globals.TILE_WIDTH, targetPosition.y * Globals.TILE_HEIGHT);
		addElement(endPoint);
		
		youWonText = new Text();
		youWonText.setCharacterSize(40);
		youWonText.setColor(Color.WHITE);
		youWonText.setString("You Won");
		youWonText.setFont(GameManager.getFont());
		youWonText.setPosition((getWindowWidth() - youWonText.getGlobalBounds().width)/2 , (getWindowHeight() - youWonText.getGlobalBounds().height)/2);

		youLostText = new Text();
		youLostText.setCharacterSize(40);
		youLostText.setColor(Color.WHITE);
		youLostText.setString("You Lost");
		youLostText.setFont(GameManager.getFont());
		youLostText.setPosition((getWindowWidth() - youLostText.getGlobalBounds().width)/2 , (getWindowHeight() - youLostText.getGlobalBounds().height)/2);

		shade = new RectangleShape();
		shade.setSize(new Vector2f(getWindowWidth(), getWindowHeight()));
		shade.setFillColor(new Color(0, 0, 0, 128));
		
		path = new ArrayList<RectangleShape>();
		for(int i = 0; i < mazeInformation.shortestPath.size(); i++) {
			Vector2i point = mazeInformation.shortestPath.get(i);
			RectangleShape sp = new RectangleShape();
			sp.setPosition(point.x * Globals.TILE_WIDTH, point.y * Globals.TILE_HEIGHT);
			sp.setFillColor(new Color(255, 0, 0, 70));
			sp.setSize(new Vector2f(Globals.TILE_WIDTH, Globals.TILE_HEIGHT));
			path.add(sp);
		}
		
		reach = new ArrayList<RectangleShape>();
		for(int i = 0; i < mazeInformation.reachablePoints.size(); i++) {
			Vector2i point = mazeInformation.reachablePoints.get(i);
			RectangleShape sp = new RectangleShape();
			sp.setPosition(point.x * Globals.TILE_WIDTH, point.y * Globals.TILE_HEIGHT);
			sp.setFillColor(new Color(255, 255, 0, 70));
			sp.setSize(new Vector2f(Globals.TILE_WIDTH, Globals.TILE_HEIGHT));
			reach.add(sp);
		}
		
		
		
		console = new RectangleShape();
		console.setSize(new Vector2f(getWindowWidth(), getWindowHeight()));
		console.setFillColor(new Color(90, 0, 90, 100));
		
		stats = new Text();
		stats.setCharacterSize(18);
		stats.setColor(Color.WHITE);
		stats.setFont(GameManager.getConsoleFont());
		stats.setString(mazeInformation.toConsoleString());
		
		setKeyPressedListener(new KeyPressedListener() {
			@Override
			public void onKeyPressed(Key key, boolean alt, boolean control,
					boolean shift, boolean system) {
				if(key == Key.P) {
					if(!pathShown) {
						for(RectangleShape shape : path) {
							addElement(shape);
						}
						pathShown = true;
					} else {
						for(RectangleShape shape : path) {
							removeElement(shape);
						}
						pathShown = false;
					}
				} else if(key == Key.R) {
					if(!reachabilityShown) {
						for(RectangleShape shape : reach) {
							addElement(shape);
						}
						reachabilityShown = true;
					} else {
						for(RectangleShape shape : reach) {
							removeElement(shape);
						}
						reachabilityShown = false;
					}
				} else if(key == Key.C) {
					if(!consoleOpened) {
						addElement(console);
						addElement(stats);
						consoleOpened = true;
					} else {
						removeElement(stats);
						removeElement(console);
						consoleOpened = false;
					}
				} else if(key == Key.BACKSPACE) {
					close();
				}
			}
		});
	}

	@Override
	protected void update() {
		
		if(!gameWon && !gameLost) {
			
			if(Keyboard.isKeyPressed(Key.UP)) {
				if(!player.isAnimationPlaying()) {
					if(playerPosition.y > 0 && mazeInformation.getCell(playerPosition.x, playerPosition.y - 1) == Globals.GROUND) {
						player.moveUp();
						playerPosition = new Vector2i(playerPosition.x, playerPosition.y - 1);
					} else {
						player.lookUp();
					}
				}
			}

			if(Keyboard.isKeyPressed(Key.DOWN)) {
				if(!player.isAnimationPlaying()) {
					if(playerPosition.y < Globals.TILES_ROWS - 1 &&  mazeInformation.getCell(playerPosition.x, playerPosition.y + 1) == Globals.GROUND) {
						player.moveDown();
						playerPosition = new Vector2i(playerPosition.x, playerPosition.y + 1);
					} else {
						player.lookDown();
					}
				}
			}

			if(Keyboard.isKeyPressed(Key.LEFT)) {
				if(!player.isAnimationPlaying()) {
					if(playerPosition.x > 0 &&  mazeInformation.getCell(playerPosition.x - 1, playerPosition.y) == Globals.GROUND) {
						player.moveLeft();
						playerPosition = new Vector2i(playerPosition.x - 1, playerPosition.y);
					} else {
						player.lookLeft();
					}
				}
			}

			if(Keyboard.isKeyPressed(Key.RIGHT)) {
				if(!player.isAnimationPlaying()) {
					if(playerPosition.x < Globals.TILES_COLS - 1 && mazeInformation.getCell(playerPosition.x + 1, playerPosition.y) == Globals.GROUND) {
						player.moveRight();
						playerPosition = new Vector2i(playerPosition.x + 1, playerPosition.y);
					} else {
						player.lookRight();
					}
				}
			}
			
			for(int i = 0; i < mazeInformation.coinsPositions.size(); i++) {
				if(mazeInformation.coinsPositions.get(i).x == playerPosition.x && mazeInformation.coinsPositions.get(i).y == playerPosition.y) {
					removeElement(coins.get(i));
					mazeInformation.coinsPositions.remove(i);
					coins.remove(i);
					scoreCounter +=Globals.COIN_SCORE;
					score.setString(String.valueOf(scoreCounter));
					score.setOrigin(score.getGlobalBounds().width / 2, score.getGlobalBounds().height / 2);
					break;
				}
			}
			
			if(playerPosition.x == targetPosition.x && playerPosition.y == targetPosition.y) {
				gameWon = true;
				removeElement(endPoint);
				addElement(shade);
				addElement(youWonText);
				setKeyPressedListener(new KeyPressedListener() {
					@Override
					public void onKeyPressed(Key key, boolean alt, boolean control, boolean shift, boolean system) {
						close();
					}
				});
			}
		}
		
		if(!gameLost && !gameWon) {
			counter = (int) (Globals.MAX_TIME - clk.getElapsedTime().asSeconds());
			timer.setString(String.valueOf(counter));
			timer.setOrigin(timer.getGlobalBounds().width / 2, timer.getGlobalBounds().height / 2);
			
			if(counter == 0) {
				gameLost = true;
				addElement(shade);
				addElement(youLostText);
				setKeyPressedListener(new KeyPressedListener() {
					@Override
					public void onKeyPressed(Key key, boolean alt, boolean control, boolean shift, boolean system) {
						close();
					}
				});

			}
		}

	}

}
