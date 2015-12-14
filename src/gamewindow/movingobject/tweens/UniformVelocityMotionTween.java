package gamewindow.movingobject.tweens;

import org.jsfml.system.Vector2f;

public class UniformVelocityMotionTween extends Tween {
	protected Vector2f v;
	protected Vector2f d;
	
	public UniformVelocityMotionTween(int frames, Vector2f d) {
		super(frames);
		this.d = d;
	}

	public UniformVelocityMotionTween(int frames, boolean repeat, Vector2f d) {
		super(frames, repeat);
		this.d = d;
	}
	
	public void initialize() {
		float vx = d.x / frames;
		float vy = d.y / frames;
		v = new Vector2f(vx, vy);
	}
	
	@Override
	public void updateMovement() {
		source.move(v);
	}
}
