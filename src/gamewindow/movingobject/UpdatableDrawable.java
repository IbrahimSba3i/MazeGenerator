package gamewindow.movingobject;

import org.jsfml.graphics.Drawable;

public interface UpdatableDrawable extends Drawable {
	public void initialize();
	public void update();
}
