package mazegame.mazewindow;

import gamewindow.eventlisteners.KeyPressedListener;
import gamewindow.system.GameWindow;

import java.util.ArrayList;
import java.util.List;

import mazegame.Globals;
import mazegame.GameManager;
import mazegame.TexturesManager;
import mazegame.generator.MazeInformation;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

public class MazeWindow extends GameWindow {
	private Sprite[][] board;
	
	private Player player;
	private Coin coin;
	
	private Vector2i playerPosition;
	private Vector2i targetPosition;

	private Text text;
	private RectangleShape shade;
	private boolean gameFinished;
	
	private MazeInformation mazeInformation;
	
	private RectangleShape console;
	private Text stats;
	
	private List<RectangleShape> path;
	
	private boolean pathShown;
	private boolean consoleOpened;

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

		gameFinished = false;
		pathShown = false;
		consoleOpened = false;
		
		player = new Player();
		player.setPosition(new Vector2f(Globals.TILE_WIDTH * playerPosition.x, Globals.TILE_HEIGHT * playerPosition.y - (Globals.PAYER_HEIGHT - Globals.TILE_HEIGHT)));
		addElement(player);

		coin = new Coin();
		coin.setPosition(new Vector2f(Globals.TILE_WIDTH * targetPosition.x + (Globals.TILE_WIDTH - Globals.COIN_WIDTH) / 2, Globals.TILE_HEIGHT * targetPosition.y + (Globals.TILE_HEIGHT - Globals.COIN_HEIGHT) / 2));
		addElement(coin);
		
		text = new Text();
		text.setCharacterSize(40);
		text.setColor(Color.WHITE);
		text.setString("Level Complete");
		text.setFont(GameManager.getFont());
		text.setPosition((getWindowWidth() - text.getGlobalBounds().width)/2 , (getWindowHeight() - text.getGlobalBounds().height)/2);

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

		if(!gameFinished) {
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
			
			if(playerPosition.x == targetPosition.x && playerPosition.y == targetPosition.y) {
				gameFinished = true;
				removeElement(coin);
				addElement(shade);
				addElement(text);
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
