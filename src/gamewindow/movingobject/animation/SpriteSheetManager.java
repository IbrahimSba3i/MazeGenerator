package gamewindow.movingobject.animation;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;

import org.jsfml.graphics.Image;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;

public class SpriteSheetManager {
	private static SpriteSheetManager instance = null;
	private Map<String, Texture[][]> loadedSpriteSheets;
	
	private SpriteSheetManager() {
		loadedSpriteSheets = new HashMap<String, Texture[][]>();
	}
	
	public Texture[][] getSpriteSheet(String filename, int spriteWidth, int spriteHeight) {
		if(loadedSpriteSheets.containsKey(filename)) {
			return loadedSpriteSheets.get(filename);
		} else {
			Image image = new Image();
			try {
				image.loadFromFile(FileSystems.getDefault().getPath(filename));
				Image sprite = new Image();
				sprite.create(spriteWidth, spriteHeight);
				Texture[][] spriteSheet = new Texture[image.getSize().x / spriteWidth][image.getSize().y / spriteHeight];
				for(int i =0; i<spriteSheet.length; i++) {
					for(int j=0; j<spriteSheet[i].length; j++) {
						for(int x = 0; x < spriteWidth; x++) {
							for(int y = 0; y < spriteHeight; y++) {
								sprite.setPixel(x, y, image.getPixel(i * spriteWidth + x,  j * spriteHeight + y));
							}
						}
						spriteSheet[i][j] = new Texture();
						spriteSheet[i][j].loadFromImage(sprite);
					}
				}
				loadedSpriteSheets.put(filename, spriteSheet);
				return spriteSheet;
				
			} catch (IOException e) {
				System.out.println("Could not load image file: " + filename);
				return null;
			} catch(TextureCreationException e) {
				System.out.println(e.toString());
				return null;
			}
		}
	}
	
	public static SpriteSheetManager getInstance() {
		if(instance == null) {
			instance = new SpriteSheetManager();
		}
		return instance;
	}
	
}
