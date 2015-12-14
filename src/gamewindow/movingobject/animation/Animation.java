package gamewindow.movingobject.animation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;

public class Animation {
	protected int framesPerImage;
	protected Texture[][] spriteSheet;
	protected int framesCounter;
	protected List<Vector2i> sequence;
	protected boolean playing;
	protected Sprite source;
	protected int currentlyDrawing;
	protected boolean loopingEnabled;
	protected String name;
	protected AnimationStoppedListener animationStoppedListener = null;
	
	public AnimationStoppedListener getAnimationStoppedListener() {
		return animationStoppedListener;
	}

	public void setAnimationStoppedListener(AnimationStoppedListener animationStoppedListener) {
		this.animationStoppedListener = animationStoppedListener;
	}

	public Animation() {
		framesPerImage = 0;
		framesCounter = 0;
		sequence = new ArrayList<Vector2i>();
		playing = false;
		source = null;
		currentlyDrawing = 0;
		loopingEnabled = false;
		name = "No Name";
	}
	
	public boolean isPlaying() {
        if(source != null)
            return playing;
        else return false;
	}
    
    public void resume() {
        playing = true;
        framesCounter = 0;
    }
    
    public void start() {
        playing = true;
        currentlyDrawing = 0;
        framesCounter = 0;
    }
    
    public void stop() {
        playing = false;
        currentlyDrawing = 0;
        if(animationStoppedListener != null) {
        	animationStoppedListener.onAnimationStopped();
        }
    }
    
    public void pause() {
        playing = false;
    }
    
    public int getFramesPerImage() {
        return framesPerImage;
    }
    
    public void setFramesPerImage(int frames) {
        framesPerImage = frames;
    }

    public void loadSpriteSheet(String filename, int spriteWidth, int spriteHeight) {
        this.spriteSheet = SpriteSheetManager.getInstance().getSpriteSheet(filename, spriteWidth, spriteHeight);
    }

    public void loadAnimationFromFile(String filename) {
    	try {
    		BufferedReader reader = new BufferedReader(new FileReader(filename));
    		String line = "";
    		Pattern sourceR = Pattern.compile("source = \"(.+)\"");
    		Pattern nameR = Pattern.compile("name = \"(.+)\"");
    		Pattern sequenceR = Pattern.compile("sequence = \\{(.+)\\}");
    		Pattern spriteWidthR = Pattern.compile("sprite_width = (\\d+)");
    		Pattern spriteHeightR = Pattern.compile("sprite_height = (\\d+)");
    		Pattern framesPerImageR = Pattern.compile("frames_per_image = (\\d+)");
    		String spritesheetPath = "";
    		int spriteWidth = 0;
    		int spriteHeight = 0;
    		
    		while((line = reader.readLine()) != null) {
    			Matcher matcher;
    			
    			matcher = sourceR.matcher(line);
    			if(matcher.find()) {
    				String token = matcher.group(1);
    				spritesheetPath = token;
    			}
    			matcher = nameR.matcher(line);
    			if(matcher.find()) {
    				String token = matcher.group(1);
    				name = token;
    			}
    			matcher = sequenceR.matcher(line);
    			if(matcher.find()) {
    				String token = matcher.group(1);
    				String[] sequenceString = token.split(",");
    				for(int i =0; i<sequenceString.length; i++) {
    					sequenceString[i] = sequenceString[i].trim();
    					String[] coordinates = sequenceString[i].substring(1, sequenceString[i].length() - 1).split(" ");
    					sequence.add(new Vector2i(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
    				}
    			}
    			matcher = framesPerImageR.matcher(line);
    			if(matcher.find()) {
    				String token = matcher.group(1);
    				framesPerImage = Integer.parseInt(token);
    			}
    			matcher = spriteWidthR.matcher(line);
    			if(matcher.find()) {
    				String token = matcher.group(1);
    				spriteWidth = Integer.parseInt(token);
    			}
    			matcher = spriteHeightR.matcher(line);
    			if(matcher.find()) {
    				String token = matcher.group(1);
    				spriteHeight = Integer.parseInt(token);
    			}
    		}
    		reader.close();
    		loadSpriteSheet(spritesheetPath, spriteWidth, spriteHeight);
    	} catch(IOException e) {
    		System.out.println("Couldn't load animation file");
    	}
    }
    
    public void setLoopingEnabled(boolean loop) {
        loopingEnabled = loop;
    }
    
    public boolean isLoopingEnabled() {
        return loopingEnabled;
    }

    public int getFramesCount() {
        return sequence.size();
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setSourceObject(Sprite source) {
        this.source = source;
    }
    
    public Sprite getSourceObject() {
    	return this.source;
	}
    
    public void update() {
        if(isPlaying()) {
            if(source != null) {
                if(framesCounter + 1 >= framesPerImage) {
                    Texture currentSprite = spriteSheet[sequence.get(currentlyDrawing).y][sequence.get(currentlyDrawing).x];
                    source.setTexture(currentSprite);
                    currentlyDrawing = currentlyDrawing + 1;
                    framesCounter = -1;
                }
                framesCounter = framesCounter + 1;
            }
            if(currentlyDrawing == getFramesCount()) {               
                if(isLoopingEnabled())
                    currentlyDrawing = 0;
                else
                    stop();
            }
        }
    }
}
