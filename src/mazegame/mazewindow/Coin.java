package mazegame.mazewindow;

import gamewindow.movingobject.animation.AnimatedObject;
import mazegame.DirectoryManager;

import org.jsfml.graphics.Sprite;

public class Coin extends AnimatedObject {

	public Coin() {
		super(new Sprite());
	}

	@Override
	public void initialize() {
		super.initialize();
		addAnimation(DirectoryManager.getAnimationsPath() + "coin_rotation.animation");
		playAnimation("Rotate Coin");
		getAnimation().setLoopingEnabled(true);
	}
}
