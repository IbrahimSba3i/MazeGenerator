package gamewindow.eventlisteners;

import org.jsfml.window.Keyboard.Key;

public interface KeyReleasedListener {

	void onKeyReleased(Key key, boolean alt, boolean control, boolean shift, boolean system);

}
