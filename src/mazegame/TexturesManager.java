package mazegame;

import java.io.IOException;
import java.nio.file.FileSystems;

import org.jsfml.graphics.Texture;

public class TexturesManager {
	private static TexturesManager instance;
	private Texture[] tileTextures;
	private String[] tileNames = {
			"wall0.png",
			"wall1.png",
			"wall2.png",
			"wall3.png",
			"wall4.png",
			"wall5.png",
			"wall6.png",
			"wall7.png",
			"wall8.png",
			"wall9.png",
			"wall10.png",
			"wall11.png",
			"wall12.png",
			"wall13.png",
			"wall14.png",
			"wall15.png",
			"ground.png",
			"ground.png",
	};
	private Texture endPointTexture;
	
	private TexturesManager() {
		tileTextures = new Texture[tileNames.length];
		
		try {
			for(int i =0; i<tileTextures.length; i++) {
				tileTextures[i] = new Texture();
				tileTextures[i].loadFromFile(FileSystems.getDefault().getPath(DirectoryManager.getSpritesPath(), tileNames[i]));
			}
		} catch (IOException e) {
			System.out.println("Could not load texture files.");
		}
	}
	
	public static TexturesManager getInstance() {
		if(instance == null) {
			instance = new TexturesManager();
		}
		return instance;
	}
	
	public Texture getTileTexture(int index) {
		return tileTextures[index];
	}

	public Texture getEndTexture() {
		if(endPointTexture == null) {
			try {
				endPointTexture = new Texture();
				endPointTexture.loadFromFile(FileSystems.getDefault().getPath(DirectoryManager.getSpritesPath(), "point0.png"));
			} catch (IOException e) {
				System.out.println("Couldn't load texture");
			}
		}
		return endPointTexture;
	}
}
