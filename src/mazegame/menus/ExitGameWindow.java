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

public class ExitGameWindow extends GameWindow implements Menu {

	public ExitGameWindow(GameWindow parent) {
		super(parent);
	}

	public ExitGameWindow() {
		super();
	}

	public ExitGameWindow(RenderWindow renderWindow) {
		super(renderWindow);
	}

	private List<Text> menuItems;
	private String[] menuStrings = {
			"Are you sure you want to exit?",
			"Yes",
			"No"
	};
	private Sprite backgroundImage;
	private int currentItem;
	
	
	
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
		
		currentItem = 1;
		setFormatting();
		
		setKeyPressedListener(new KeyPressedListener() {
			@Override
			public void onKeyPressed(Key key, boolean alt, boolean control, boolean shift, boolean system) {
				if(key == Key.UP) {
					moveToPrevious();
				} else if(key == Key.DOWN) {
					moveToNext();
				} else if(key == Key.RETURN) {
					if(getSelected() == 1) renderWindow.close();
					else close();
				} else if(key == Key.BACKSPACE) {
					close();
				}
			}
		});
	}
	
	@Override
	protected void update() {
		
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
		currentItem = 3-currentItem ;
		setFormatting();
	}

	@Override
	public void moveToPrevious() {
		clearFormatting();
		currentItem = 3-currentItem ;
		setFormatting();
	}

	@Override
	public int getSelected() {
		return currentItem;
	}


}
