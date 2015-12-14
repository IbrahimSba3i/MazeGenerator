package gamewindow.test;

import gamewindow.eventlisteners.KeyPressedListener;
import gamewindow.movingobject.MovingObject;
import gamewindow.movingobject.tweens.Tween;
import gamewindow.movingobject.tweens.TweenFinishedListener;
import gamewindow.movingobject.tweens.UniformAccelerationMotionTween;
import gamewindow.movingobject.tweens.UniformVelocityMotionTween;
import gamewindow.system.GameWindow;

import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard.Key;

public class CircleProjectileWindow extends GameWindow {

	private MovingObject circle;
	private final float acceleration = 1.0f;
	private final int time = 25;
	private final float radius = 30.f;
	private final float initialX = 0.f;
	private final float initialY = getWindowHeight() - radius * 2;
	private final float finalX = getWindowWidth() - radius * 2;
	private final float deltaX = finalX - initialX;
	
	private Drawable getCircle() {
		CircleShape c = new CircleShape();
		c.setFillColor(Color.RED);
		c.setRadius(radius);
		c.setPosition(new Vector2f(initialX, initialY));
		return new MovingObject(c);
	}

	@Override
	protected void createWindowContent() {
		circle = (MovingObject) getCircle();
		addElement(circle);
		
		setKeyPressedListener(new KeyPressedListener() {

			@Override
			public void onKeyPressed(Key key, boolean alt, boolean control, boolean shift, boolean system) {

				if(key == Key.R) {
					circle.setPosition(new Vector2f(initialX, initialY));
					
					final Tween constant = new UniformVelocityMotionTween(time * 2, new Vector2f(deltaX, 0));
					final Tween t1 = UniformAccelerationMotionTween.getTweenByFinalVelocity(time, new Vector2f(0, 0), new Vector2f(0, acceleration));
					final Tween t2 = UniformAccelerationMotionTween.getTweenByInitialVelocity(time, new Vector2f(0, 0), new Vector2f(0, acceleration));

					circle.addTween(constant);
					circle.addTween(t1);
					t1.setOnTweenFinishedListener(new TweenFinishedListener() {
						@Override
						public void onTweenFinished() {
							circle.addTween(t2);
						}
					});
				}
			}
		});
	}

	@Override
	protected void update() {

	}

}
