package gamewindow.movingobject;

import gamewindow.movingobject.tweens.Tween;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Transform;
import org.jsfml.graphics.Transformable;
import org.jsfml.system.Vector2f;

public class MovingObject implements UpdatableDrawable, Transformable {
	protected Transformable target;
	protected List<Tween> tweens;
	
	public MovingObject(Transformable target) {
		tweens = new ArrayList<Tween>();
		this.target = target;
	}
	
	public void addTween(Tween tween) {
		if(tween != null) {
			tween.setSource(this);
			tween.start();
			tweens.add(tween);
		}
	}
	
	public boolean isMoving() {
		return (tweens.size() != 0);
	}
	
	public void stopMoving() {
		for(int i = tweens.size()-1; i >= 0; i--) {
			tweens.get(i).stop();
		}
		tweens.clear();
	}
	
	public void removeTween(Tween tween) {
		for(int i=0; i<tweens.size(); i++) {
			if(tweens.get(i) == tween) {
				tweens.get(i).stop();
				tweens.remove(i--);
			}
		}
	}
	
	@Override
	public void initialize() {
		
	}

	@Override
	public void update() {
		for(int i=0; i < tweens.size(); i++) {
			tweens.get(i).update();
			if(!tweens.get(i).isPlaying()) {
				tweens.remove(i);
				i--;
			}
		}
	}

	@Override
	public void draw(RenderTarget arg0, RenderStates arg1) {
		if(target instanceof Drawable) {
			arg0.draw((Drawable) target, arg1);
		}
	}

	@Override
	public Vector2f getPosition() {
		return target.getPosition();
	}

	@Override
	public float getRotation() {
		return target.getRotation();
	}

	@Override
	public Vector2f getScale() {
		return target.getScale();
	}

	@Override
	public void move(Vector2f arg0) {
		target.move(arg0);
	}

	@Override
	public void move(float arg0, float arg1) {
		target.move(arg0, arg1);
	}

	@Override
	public void rotate(float arg0) {
		target.rotate(arg0);
	}

	@Override
	public void scale(Vector2f arg0) {
		target.scale(arg0);
	}

	@Override
	public void scale(float arg0, float arg1) {
		target.scale(arg0, arg1);
	}

	@Override
	public void setOrigin(Vector2f arg0) {
		target.setOrigin(arg0);
	}

	@Override
	public void setOrigin(float arg0, float arg1) {
		target.setOrigin(arg0, arg1);
	}

	@Override
	public void setPosition(Vector2f arg0) {
		target.setPosition(arg0);
	}

	@Override
	public void setPosition(float arg0, float arg1) {
		target.setPosition(arg0, arg1);
	}

	@Override
	public void setRotation(float arg0) {
		target.setRotation(arg0);
	}

	@Override
	public void setScale(Vector2f arg0) {
		target.setScale(arg0);
	}

	@Override
	public void setScale(float arg0, float arg1) {
		target.setScale(arg0, arg1);
	}

	@Override
	public Transform getInverseTransform() {
		return target.getInverseTransform();
	}

	@Override
	public Vector2f getOrigin() {
		return target.getOrigin();
	}

	@Override
	public Transform getTransform() {
		return target.getTransform();
	}
}
