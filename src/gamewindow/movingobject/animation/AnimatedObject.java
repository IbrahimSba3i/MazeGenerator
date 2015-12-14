package gamewindow.movingobject.animation;

import gamewindow.movingobject.MovingObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsfml.graphics.Sprite;

public class AnimatedObject extends MovingObject {
	protected List<Animation> animationList;
	protected Map<String, Animation> animationMap;
	protected int currentlyPlaying;
	
	public AnimatedObject(Sprite target) {
		super(target);
		animationList = new ArrayList<Animation>();
		animationMap = new HashMap<String, Animation>();
		currentlyPlaying = -1;
	}
	
	public void addAnimation(String filename) {
        Animation animation = new Animation();
        animation.loadAnimationFromFile(filename);
        animation.setSourceObject((Sprite) target);
        animationList.add(animation);
        animationMap.put(animation.getName(), animation);
	}
	
	public void addAnimation(Animation animation) {
        animation.setSourceObject((Sprite) target);
        animationList.add(animation);
        animationMap.put(animation.getName(), animation);		
	}
	
    public Animation getAnimation() {
    	return getAnimation(currentlyPlaying);
    }
    
    public Animation getAnimation(int index) {
    	return animationList.get(index);
    }
    
    public Animation getAnimation(String name) {
    	return animationMap.get(name);
    }

    public int getAnimationsCount() {
        return animationList.size();
    }
    
    public boolean isAnimationPlaying() {
    	boolean result;
        if (currentlyPlaying < 0) {
        	result = false;
        }
        else {
        	result = getAnimation().isPlaying();
        }
        return result;
    }
    
    public void playAnimation(int index) {
    	if(isAnimationPlaying()) {
    		stopAnimation();
    	}
    	currentlyPlaying = index;
        getAnimation(index).start();
    }
    
    public void playAnimation(String index) {
    	if(isAnimationPlaying()) {
    		stopAnimation();
    	}
    	for(int i = 0; i<animationList.size(); i++) {
    		if(animationList.get(i).getName().equals(index))
    			currentlyPlaying = i;
    	}
    	getAnimation().start();
    }
    
    public void stopAnimation() {
        getAnimation().stop();
        currentlyPlaying = -1;
    }
    
    public void pauseAnimation() {
        getAnimation().pause();
    }
        
    public void resumeAnimation() {
        getAnimation().resume();
    }
    
    @Override
    public void update() {
        super.update();
        if(isAnimationPlaying()) {
            if(!getAnimation().isPlaying()) {
                currentlyPlaying = -1;
            }
            else {
            	getAnimation().update();
            }
        }
    }
}
