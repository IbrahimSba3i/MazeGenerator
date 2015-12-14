package mazegame.mazewindow;

import mazegame.Globals;
import mazegame.DirectoryManager;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

import gamewindow.movingobject.animation.AnimatedObject;
import gamewindow.movingobject.animation.AnimationStoppedListener;
import gamewindow.movingobject.tweens.UniformVelocityMotionTween;

public class Player extends AnimatedObject {
	protected int lastPlayed;
	
	public Player() {
		super(new Sprite());		
	}
	
	@Override
	public void initialize() {
		super.initialize();
		lastPlayed = 0;
		initializeAnimation();
		playAnimation("Look Right");
	}
	
	public void initializeAnimation() {
		String[] animationFileNames = {
				"look_down.animation",
				"look_left.animation",
				"look_right.animation",
				"look_up.animation",
				"move_down_1.animation",
				"move_down_2.animation",
				"move_left_1.animation",
				"move_left_2.animation",
				"move_right_1.animation",
				"move_right_2.animation",
				"move_up_1.animation",
				"move_up_2.animation"		
		};
		
		for(int i = 0; i< animationFileNames.length; i++) {
			addAnimation(DirectoryManager.getAnimationsPath() + animationFileNames[i]);
		}
	}
	
	public void moveLeft() {
		if(!isAnimationPlaying()) {
			playAnimation("Move Left " + (lastPlayed + 1));
			getAnimation().setAnimationStoppedListener(new AnimationStoppedListener() {
				@Override
				public void onAnimationStopped() {
					playAnimation("Look Left");
				}
			});
			addTween(new UniformVelocityMotionTween(getAnimation().getFramesCount(), new Vector2f(-Globals.TILE_WIDTH, 0)));
			lastPlayed = 1 - lastPlayed;
		}
	}
	
	public void moveRight() {
		if(!isAnimationPlaying()) {
			playAnimation("Move Right " + (lastPlayed + 1));
			getAnimation().setAnimationStoppedListener(new AnimationStoppedListener() {
				@Override
				public void onAnimationStopped() {
					playAnimation("Look Right");
				}
			});
			addTween(new UniformVelocityMotionTween(getAnimation().getFramesCount(), new Vector2f(Globals.TILE_WIDTH, 0)));
			lastPlayed = 1 - lastPlayed;
		}		
	}
	
	public void moveUp() {
		if(!isAnimationPlaying()) {
			playAnimation("Move Up " + (lastPlayed + 1));
			getAnimation().setAnimationStoppedListener(new AnimationStoppedListener() {
				@Override
				public void onAnimationStopped() {
					playAnimation("Look Up");					
				}
			});
			addTween(new UniformVelocityMotionTween(getAnimation().getFramesCount(), new Vector2f(0, -Globals.TILE_HEIGHT)));
			lastPlayed = 1 - lastPlayed;
		}
	}
	
	public void moveDown() {
		if(!isAnimationPlaying()) {
			playAnimation("Move Down " + (lastPlayed + 1));
			getAnimation().setAnimationStoppedListener(new AnimationStoppedListener() {
				@Override
				public void onAnimationStopped() {
					playAnimation("Look Down");					
				}
			});
			addTween(new UniformVelocityMotionTween(getAnimation().getFramesCount(), new Vector2f(0, Globals.TILE_HEIGHT)));
			lastPlayed = 1 - lastPlayed;
		}
	}

	public void lookUp() {
		if(!isAnimationPlaying()) {
			playAnimation("Look Up");
		}
	}
	
	public void lookDown() {
		if(!isAnimationPlaying()) {
			playAnimation("Look Down");
		}
	}
	
	public void lookLeft() {
		if(!isAnimationPlaying()) {
			playAnimation("Look Left");
		}
	}
	
	public void lookRight() {
		if(!isAnimationPlaying()) {
			playAnimation("Look Right");
		}
	}

}
