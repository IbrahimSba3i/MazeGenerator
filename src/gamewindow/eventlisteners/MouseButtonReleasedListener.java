package gamewindow.eventlisteners;

import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse.Button;

public interface MouseButtonReleasedListener {

	void onMouseButtonReleased(Button button, Vector2i position);

}
