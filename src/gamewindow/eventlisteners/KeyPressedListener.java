package gamewindow.eventlisteners;

import org.jsfml.window.Keyboard.Key;

public interface KeyPressedListener {

	void onKeyPressed(Key key, boolean alt, boolean control, boolean shift, boolean system);

}
