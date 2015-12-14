package gamewindow.movingobject.tweens;

import org.jsfml.system.Vector2f;

public class UniformAccelerationMotionTween extends Tween {
	protected Vector2f d;
	protected Vector2f a;
	protected Vector2f v;
	protected Vector2f vf;
	
	protected UniformAccelerationMotionTween(int frames, Vector2f d, Vector2f a) {
		super(frames);
		this.d = d;
		this.a = a;
	}
	
	public static UniformAccelerationMotionTween getTweenByDisplacement(int frames, Vector2f d, Vector2f a) {
		return new UniformAccelerationMotionTween(frames, d, a);
	}
	
	public static UniformAccelerationMotionTween getTweenByFinalVelocity(int frames, Vector2f vf, Vector2f a) {
		float dx = vf.x * frames - a.x * frames * frames / 2.f;
		float dy = vf.y * frames - a.y * frames * frames / 2.f;
		return UniformAccelerationMotionTween.getTweenByDisplacement(frames, new Vector2f(dx, dy), a);
	}

	public static UniformAccelerationMotionTween getTweenByInitialVelocity(int frames, Vector2f vi, Vector2f a) {
		float vfx = vi.x + a.x * frames;
		float vfy = vi.y + a.y * frames;
		return getTweenByFinalVelocity(frames, new Vector2f(vfx, vfy), a);
	}
	
	@Override
	protected void initialize() {
		float vfx = (d.x + a.x * frames * frames / 2.f) / frames;
		float vfy = (d.y + a.y * frames * frames / 2.f) / frames;
		float vx = vfx - a.x * frames;
		float vy = vfy - a.y * frames;
		vf = new Vector2f(vfx, vfy);
		v = new Vector2f(vx, vy);
	}
	
	@Override
	public void updateMovement() {
		source.move(v.x, v.y);
		v = new Vector2f(v.x + a.x, v.y + a.y);
	}

	@Override
	public void stop() {
		super.stop();
		updateMovement();
	}

}
