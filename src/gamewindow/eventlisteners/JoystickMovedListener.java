package gamewindow.eventlisteners;

import org.jsfml.window.Joystick.Axis;

public interface JoystickMovedListener {

	void onJoystickMoved(int joystickId, Axis joyAxis, float position);

}
