package mazegame.generator;

import gamewindow.system.GameWindow;
import geneticalgorithms.FinishedObserver;
import geneticalgorithms.ProgressObserver;

import java.io.IOException;
import java.nio.file.FileSystems;

import mazegame.Globals;
import mazegame.Difficulty;
import mazegame.DirectoryManager;
import mazegame.GameManager;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class MazeGenerationLoadingWindow extends GameWindow implements ProgressObserver, FinishedObserver {
	protected GeneticAlgorithmsMazeGenerator generator;
	protected RectangleShape plainBackground;
	protected Text generatingMapText;
	protected Text progressPercent;
	protected volatile RectangleShape loadingRectangle;
	protected Sprite backgroundImage;
	protected Thread thread;
	protected float maxLoadingBarSize = 450.0f;
	protected volatile boolean finished;
	protected MazeInformation mazeInfo;

	
	public MazeGenerationLoadingWindow(Difficulty difficulty) {
		generator = new GeneticAlgorithmsMazeGenerator(this, this, difficulty);
		finished = false;
	}

	public MazeGenerationLoadingWindow(RenderWindow window, Difficulty difficulty) {
		super(window);
		generator = new GeneticAlgorithmsMazeGenerator(this, this, difficulty);
		finished = false;
	}

	
	public MazeGenerationLoadingWindow(GameWindow parent, Difficulty difficulty) {
		super(parent);
		generator = new GeneticAlgorithmsMazeGenerator(this, this, difficulty);
		finished = false;
	}

	@Override
	protected void createWindowContent() {
		
		
		Texture texture = new Texture();
		try {
			texture.loadFromFile(FileSystems.getDefault().getPath(DirectoryManager.getBackgroundsPath(), "loading.png"));
		} catch (IOException e) {
			System.out.println("Couldn't load loading texture");
		}
		
		Vector2f scaleFactor = new Vector2f((float)getWindowWidth() / texture.getSize().x,  (float) getWindowHeight() / texture.getSize().y);
		
		plainBackground = new RectangleShape();
		plainBackground.setSize(new Vector2f(getWindowWidth(), getWindowHeight()));
		plainBackground.setFillColor(GameManager.getGreyColor());
		plainBackground.setScale(scaleFactor);
		addElement(plainBackground);
		
		loadingRectangle = new RectangleShape();
		loadingRectangle.setFillColor(GameManager.getBlueColor());
		loadingRectangle.setPosition((getWindowWidth() - maxLoadingBarSize * scaleFactor.x) / 2, 436);
		loadingRectangle.setSize(new Vector2f(0, 65));
		loadingRectangle.setScale(scaleFactor);
		addElement(loadingRectangle);

		
		backgroundImage = new Sprite();
		backgroundImage.setTexture(texture);
		backgroundImage.setScale(scaleFactor);
		addElement(backgroundImage);
		
		
		progressPercent = new Text();
		progressPercent.setCharacterSize(50);
		progressPercent.setColor(GameManager.getBlueColor());
		progressPercent.setFont(GameManager.getFont());
		progressPercent.setString("0%");
		progressPercent.setPosition((getWindowWidth() - progressPercent.getGlobalBounds().width)/2, 290);
		addElement(progressPercent);
		
		generatingMapText = new Text();
		generatingMapText.setCharacterSize(30);
		generatingMapText.setColor(GameManager.getBlueColor());
		generatingMapText.setString("Generating Level...");
		generatingMapText.setFont(GameManager.getFont());
		generatingMapText.setPosition((getWindowWidth() - generatingMapText.getGlobalBounds().width)/2, 526);
		addElement(generatingMapText);
		
		thread = new Thread(new Runnable() {			
			@Override
			public void run() {
				generator.start();
			}
		});
		thread.start();
	}

	@Override
	protected void update() {
		if(finished) {
			try {
				// thread.wait();
				mazeInfo = generator.getBestMazeInfo();
				close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error: " + e.toString());
			}
		}
	}

	@Override
	public void onUpdate(int progress) {
		progress = progress * 100 / Globals.MAX_EPOCHS;
		loadingRectangle.setSize(new Vector2f(progress * (maxLoadingBarSize / 100.0f), 65));
		progressPercent.setString(progress + "%");
		progressPercent.setPosition((getWindowWidth() - progressPercent.getGlobalBounds().width)/2, 290);
	}

	@Override
	public void onFinished() {
		loadingRectangle.setSize(new Vector2f(maxLoadingBarSize, 65));
		progressPercent.setString("100%");
		progressPercent.setPosition((getWindowWidth() - progressPercent.getGlobalBounds().width)/2, 290);

		finished = true;
	}
	
	public MazeInformation getBestMazeInfo() {
		return this.mazeInfo;
	}

}
