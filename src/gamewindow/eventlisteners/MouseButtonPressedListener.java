package gamewindow.eventlisteners;

import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse.Button;

public interface MouseButtonPressedListener {

	void onMouseButtonPressed(Button button, Vector2i position);

}
