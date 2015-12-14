package mazegame.menus;

import gamewindow.eventlisteners.KeyPressedListener;
import gamewindow.system.GameWindow;
import gamewindow.system.Menu;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import mazegame.DirectoryManager;
import mazegame.GameManager;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard.Key;

public class MainMenu extends GameWindow implements Menu {

	private List<Text> menuItems;
	private String[] menuStrings = {
			"Start Game",
			"About",
			"Exit"
	};
	private Sprite backgroundImage;
	private int currentItem;
	
	public MainMenu() {
		super();
	}
	
	public MainMenu(GameWindow parent) {
		super(parent);
	}

	public MainMenu(RenderWindow renderWindow) {
		super(renderWindow);
	}

	protected void clearFormatting() {
		menuItems.get(currentItem).setScale(new Vector2f(1.0f, 1.0f));
	}
	
	protected void setFormatting() {
		menuItems.get(currentItem).setScale(new Vector2f(1.2f, 1.2f));		
	}
	
	@Override
	public void moveToNext() {
		clearFormatting();
		currentItem = (currentItem + 1) % menuItems.size();
		setFormatting();
	}

	@Override
	public void moveToPrevious() {
		clearFormatting();
		currentItem = (currentItem - 1 + menuItems.size()) % menuItems.size();
		setFormatting();
	}

	@Override
	public int getSelected() {
		return currentItem;
	}

	@Override
	protected void createWindowContent() {
		Texture texture = new Texture();
		try {
			texture.loadFromFile(FileSystems.getDefault().getPath(DirectoryManager.getBackgroundsPath(), "plain.png"));
		} catch(IOException e) {
			System.out.println("Couldn't load background image!");
		}
		backgroundImage = new Sprite();
		backgroundImage.setTexture(texture);
		addElement(backgroundImage);
		
		menuItems = new ArrayList<Text>();
		
		for(int i = 0; i < menuStrings.length; i++) {
			Text text = new Text();
			text.setCharacterSize(30);
			text.setFont(GameManager.getFont());
			text.setColor(GameManager.getBlueColor());
			text.setString(menuStrings[i]);
			text.setOrigin(text.getLocalBounds().width / 2, text.getLocalBounds().height / 2);
			text.setPosition(new Vector2f(getWindowWidth()/2, (i + 1) * getWindowHeight()/(menuStrings.length + 1)));
			menuItems.add(text);
			addElement(text);
		}
		
		currentItem = 0;
		setFormatting();
		
		setKeyPressedListener(new KeyPressedListener() {
			@Override
			public void onKeyPressed(Key key, boolean alt, boolean control, boolean shift, boolean system) {
				if(key == Key.UP) {
					moveToPrevious();
				} else if(key == Key.DOWN) {
					moveToNext();
				} else if(key == Key.RETURN) {
					if(getSelected() == 0) { // new game
						DifficultyWindow window = new DifficultyWindow(getCurrentWindow());
						window.open();
					} else if(getSelected() == 1) { // about
						
					} else if(getSelected() == 2){ // exit game
						ExitGameWindow window = new ExitGameWindow(getCurrentWindow());
						window.open();
					}
				}
			}
		});

	}

	@Override
	protected void update() {
		
	}

}
