package gamewindow.movingobject.tweens;

import org.jsfml.graphics.Transformable;

public abstract class Tween {
	protected Transformable source;
	protected int frames;
	protected int currentFrames;
	protected boolean playing;
	protected boolean repeat ;
	protected TweenFinishedListener tweenFinishedListener;
	
	public Tween(int frames, boolean repeat) {
		this.frames = frames;
		this.repeat = repeat;
		initializeTween();
	}

	public Tween(int frames) {
		this.frames = frames;
		this.repeat = false;
		initializeTween();
	}

	protected void initializeTween() {
		currentFrames = 0;
		playing = false;
		source = null;
		tweenFinishedListener = null;
	}
	
	protected abstract void initialize();
	
	public void setSource(Transformable source) {
		this.source = source;
	}
	
	public boolean isPlaying() {
		return playing;
	}
	
	public void start() {
		if(!isPlaying()) {
			initialize();
			playing = true;
		}
	}
	
	public void setLoop(boolean loop) {
		this.repeat = loop;
	}
	
	public boolean isLoopEnabled() {
		return repeat;
	}
	
	public void setOnTweenFinishedListener(TweenFinishedListener listener) {
		this.tweenFinishedListener = listener;
	}
	
	public abstract void updateMovement();
	
	public void update() {
		if(isPlaying()) {
			currentFrames++;
			updateMovement();
			if(currentFrames == frames) {
				stop();
				if(repeat) {
					start();
				} else {
					if(tweenFinishedListener != null) {
						tweenFinishedListener.onTweenFinished();
					}
				}
			}
		}
	}

	public void stop() {
		playing = false;
		currentFrames = 0;
	}
}
