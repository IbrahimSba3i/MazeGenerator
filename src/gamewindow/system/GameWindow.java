package gamewindow.system;

import gamewindow.eventlisteners.ClosedListener;
import gamewindow.eventlisteners.GainedFocusListener;
import gamewindow.eventlisteners.JoystickButtonPressedListener;
import gamewindow.eventlisteners.JoystickButtonReleasedListener;
import gamewindow.eventlisteners.JoystickConnecetedListener;
import gamewindow.eventlisteners.JoystickDisconnectedListener;
import gamewindow.eventlisteners.JoystickMovedListener;
import gamewindow.eventlisteners.KeyPressedListener;
import gamewindow.eventlisteners.KeyReleasedListener;
import gamewindow.eventlisteners.LostFocusListener;
import gamewindow.eventlisteners.MouseButtonPressedListener;
import gamewindow.eventlisteners.MouseButtonReleasedListener;
import gamewindow.eventlisteners.MouseEnteredListener;
import gamewindow.eventlisteners.MouseLeftListener;
import gamewindow.eventlisteners.MouseMovedListener;
import gamewindow.eventlisteners.MouseWheelMovedListener;
import gamewindow.eventlisteners.ResizedListener;
import gamewindow.eventlisteners.TextEnteredListener;
import gamewindow.movingobject.UpdatableDrawable;

import java.util.ArrayList;
import java.util.List;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.window.event.Event;


public abstract class GameWindow {
	protected RenderWindow renderWindow = null;
	protected Clock clock = new Clock();	
	protected boolean windowOpened = false;	
	protected List<Drawable> drawables = new ArrayList<Drawable>();
	protected int framesPerSecond = 30;
	
	protected ClosedListener closedListener = null;
	protected GainedFocusListener gainedFocusListener = null;
	protected TextEnteredListener textEnteredListener = null;
	protected JoystickButtonPressedListener joystickButtonPressedListener = null;
	protected JoystickButtonReleasedListener joystickButtonReleasedListener = null;
	protected JoystickConnecetedListener joystickConnecetedListener = null;
	protected JoystickDisconnectedListener joystickDisconnectedListener = null;
	protected JoystickMovedListener joystickMovedListener = null;
	protected KeyPressedListener keyPressedListener = null;
	protected KeyReleasedListener keyReleasedListener = null;
	protected LostFocusListener lostFocusListener = null;
	protected MouseButtonPressedListener mouseButtonPressedListener = null;
	protected MouseButtonReleasedListener mouseButtonReleasedListener = null;
	protected MouseEnteredListener mouseEnteredListener = null;
	protected MouseLeftListener mouseLeftListener = null;
	protected MouseMovedListener mouseMovedListener = null;
	protected MouseWheelMovedListener mouseWheelMovedListener = null;
	protected ResizedListener resizedListener = null;
	
	public GameWindow getCurrentWindow() {
		return this;
	}
	
	public GameWindow(GameWindow parent) {
		this.renderWindow = parent.renderWindow;
	}
	
	public GameWindow() {
		this.renderWindow = RenderWindowLoader.getRenderWindow();
	}
	
	public GameWindow(RenderWindow renderWindow) {
		this.renderWindow = renderWindow;
	}
	
	public void addElement(Drawable d) {
		drawables.add(d);
	}
	
	public void close() {
		windowOpened = false;
	}
	
	public boolean containsElement(Drawable d) {
		return drawables.contains(d);
	}
	
	protected void gameLoop() {
		long frameTime = 1000 / framesPerSecond;
		while(windowOpened && renderWindow.isOpen()) {
			if(clock.getElapsedTime().asMilliseconds() >= frameTime) {
				handleEvents();
				updateCurrentContent();
				update();
				render();
				clock.restart();
			}
		}
	}
	
	protected void updateCurrentContent() {
		for(int i = 0; i< drawables.size(); i++) {
			if(drawables.get(i) instanceof UpdatableDrawable) {
				((UpdatableDrawable) drawables.get(i)).update();
			}
		}
	}

	public int getFPS() {
		return framesPerSecond;
	}
	
	public ClosedListener getOnClosedListener() {
		return closedListener;
	}

	public GainedFocusListener getOnGainedFocusListener() {
		return gainedFocusListener;
	}

	public TextEnteredListener getOnTextEnteredListener() {
		return textEnteredListener;
	}

	public JoystickButtonPressedListener getOnJoystickButtonPressedListener() {
		return joystickButtonPressedListener;
	}

	public JoystickButtonReleasedListener getOnJoystickButtonReleasedListener() {
		return joystickButtonReleasedListener;
	}

	public JoystickConnecetedListener getOnJoystickConnecetedListener() {
		return joystickConnecetedListener;
	}

	public JoystickDisconnectedListener getOnJoystickDisconnectedListener() {
		return joystickDisconnectedListener;
	}

	public JoystickMovedListener getOnJoystickMovedListener() {
		return joystickMovedListener;
	}

	public KeyPressedListener getOnKeyPressedListener() {
		return keyPressedListener;
	}

	public KeyReleasedListener getOnKeyReleasedListener() {
		return keyReleasedListener;
	}

	public LostFocusListener getOnLostFocusListener() {
		return lostFocusListener;
	}

	public MouseButtonPressedListener getOnMouseButtonPressedListener() {
		return mouseButtonPressedListener;
	}

	public MouseButtonReleasedListener getOnMouseButtonReleasedListener() {
		return mouseButtonReleasedListener;
	}

	public MouseEnteredListener getOnMouseEnteredListener() {
		return mouseEnteredListener;
	}

	public MouseLeftListener getOnMouseLeftListener() {
		return mouseLeftListener;
	}

	public MouseMovedListener getOnMouseMovedListener() {
		return mouseMovedListener;
	}

	public MouseWheelMovedListener getOnMouseWheelMovedListener() {
		return mouseWheelMovedListener;
	}

	public ResizedListener getOnResizedListener() {
		return resizedListener;
	}
	
	public int getWindowHeight() {
		return renderWindow.getSize().y;
	}
	
	public int getWindowWidth() {
		return renderWindow.getSize().x;
	}
	
	protected void handleEvents() {
		Event e;
		while((e = renderWindow.pollEvent()) != null) {
			switch(e.type) {
			case CLOSED:
				if(closedListener != null) {
					closedListener.onClosed();
				}
				break;

			case GAINED_FOCUS:
				if(gainedFocusListener != null) {
					gainedFocusListener.onGainedFocus();
				}
				break;

			case TEXT_ENTERED:
				if(textEnteredListener != null) {
					textEnteredListener.onTextEntered(e.asTextEvent().character, e.asTextEvent().unicode);
				}
				break;

			case JOYSTICK_BUTTON_PRESSED:
				if(joystickButtonPressedListener != null) {
					joystickButtonPressedListener.onJoystickButtonPressed(e.asJoystickButtonEvent().joystickId, e.asJoystickButtonEvent().button);
				}
				break;

			case JOYSTICK_BUTTON_RELEASED:
				if(joystickButtonReleasedListener != null) {
					joystickButtonReleasedListener.onJoystickButtonReleased(e.asJoystickButtonEvent().joystickId, e.asJoystickButtonEvent().button);
				}
				break;

			case JOYSTICK_CONNECETED:
				if(joystickConnecetedListener != null) {
					joystickConnecetedListener.onJoystickConneceted(e.asJoystickEvent().joystickId);
				}
				break;

			case JOYSTICK_DISCONNECTED:
				if(joystickDisconnectedListener != null) {
					joystickDisconnectedListener.onJoystickDisconnected(e.asJoystickEvent().joystickId);
				}
				break;

			case JOYSTICK_MOVED:
				if(joystickMovedListener != null) {
					joystickMovedListener.onJoystickMoved(e.asJoystickMoveEvent().joystickId, e.asJoystickMoveEvent().joyAxis, e.asJoystickMoveEvent().position);
				}
				break;

			case KEY_PRESSED:
				if(keyPressedListener != null) {
					keyPressedListener.onKeyPressed(e.asKeyEvent().key, e.asKeyEvent().alt, e.asKeyEvent().control, e.asKeyEvent().shift, e.asKeyEvent().system);
				}
				break;

			case KEY_RELEASED:
				if(keyReleasedListener != null) {
					keyReleasedListener.onKeyReleased(e.asKeyEvent().key, e.asKeyEvent().alt, e.asKeyEvent().control, e.asKeyEvent().shift, e.asKeyEvent().system);
				}
				break;

			case LOST_FOCUS:
				if(lostFocusListener != null) {
					lostFocusListener.onLostFocus();
				}
				break;

			case MOUSE_BUTTON_PRESSED:
				if(mouseButtonPressedListener != null) {
					mouseButtonPressedListener.onMouseButtonPressed(e.asMouseButtonEvent().button, e.asMouseButtonEvent().position);
				}
				break;

			case MOUSE_BUTTON_RELEASED:
				if(mouseButtonReleasedListener != null) {
					mouseButtonReleasedListener.onMouseButtonReleased(e.asMouseButtonEvent().button, e.asMouseButtonEvent().position);
				}
				break;

			case MOUSE_ENTERED:
				if(mouseEnteredListener != null) {
					mouseEnteredListener.onMouseEntered(e.asMouseEvent().position);
				}
				break;

			case MOUSE_LEFT:
				if(mouseLeftListener != null) {
					mouseLeftListener.onMouseLeft(e.asMouseEvent().position);
				}
				break;

			case MOUSE_MOVED:
				if(mouseMovedListener != null) {
					mouseMovedListener.onMouseMoved(e.asMouseEvent().position);
				}
				break;

			case MOUSE_WHEEL_MOVED:
				if(mouseWheelMovedListener != null) {
					mouseWheelMovedListener.onMouseWheelMoved(e.asMouseWheelEvent().delta, e.asMouseWheelEvent().position);
				}
				break;

			case RESIZED:
				if(resizedListener != null) {
					resizedListener.onResized();
				}
				break;
			default:
				break;
			}
		}
	}
	
	protected abstract void createWindowContent();
	public void open() {
		windowOpened = true;
		setClosedListener(new ClosedListener() {
			
			@Override
			public void onClosed() {
				close();
			}
		});
		createWindowContent();
		initializeElements();
		gameLoop();
	}
	
	protected void initializeElements() {
		for(Drawable d : drawables) {
			if(d instanceof UpdatableDrawable) {
				((UpdatableDrawable) d).initialize();
			}
		}
	}

	public void removeElement(Drawable d) {
		drawables.remove(d);
	}
	
	protected void render() {
		renderWindow.clear(Color.WHITE);
		for(Drawable d : drawables){
			renderWindow.draw(d);
		}
		renderWindow.display();
	}
	
	public void setFPS(int n) {
		framesPerSecond = n;
	}
	
	public void setClosedListener(ClosedListener closedListener) {
		this.closedListener = closedListener;
	}

	public void setGainedFocusListener(GainedFocusListener gainedFocusListener) {
		this.gainedFocusListener = gainedFocusListener;
	}

	public void setTextEnteredListener(TextEnteredListener textEnteredListener) {
		this.textEnteredListener = textEnteredListener;
	}

	public void setJoystickButtonPressedListener(JoystickButtonPressedListener joystickButtonPressedListener) {
		this.joystickButtonPressedListener = joystickButtonPressedListener;
	}

	public void setJoystickButtonReleasedListener(JoystickButtonReleasedListener joystickButtonReleasedListener) {
		this.joystickButtonReleasedListener = joystickButtonReleasedListener;
	}

	public void setJoystickConnecetedListener(JoystickConnecetedListener joystickConnecetedListener) {
		this.joystickConnecetedListener = joystickConnecetedListener;
	}

	public void setJoystickDisconnectedListener(JoystickDisconnectedListener joystickDisconnectedListener) {
		this.joystickDisconnectedListener = joystickDisconnectedListener;
	}

	public void setJoystickMovedListener(JoystickMovedListener joystickMovedListener) {
		this.joystickMovedListener = joystickMovedListener;
	}

	public void setKeyPressedListener(KeyPressedListener keyPressedListener) {
		this.keyPressedListener = keyPressedListener;
	}

	public void setKeyReleasedListener(KeyReleasedListener keyReleasedListener) {
		this.keyReleasedListener = keyReleasedListener;
	}

	public void setLostFocusListener(LostFocusListener lostFocusListener) {
		this.lostFocusListener = lostFocusListener;
	}

	public void setMouseButtonPressedListener(MouseButtonPressedListener mouseButtonPressedListener) {
		this.mouseButtonPressedListener = mouseButtonPressedListener;
	}

	public void setMouseButtonReleasedListener(MouseButtonReleasedListener mouseButtonReleasedListener) {
		this.mouseButtonReleasedListener = mouseButtonReleasedListener;
	}

	public void setMouseEnteredListener(MouseEnteredListener mouseEnteredListener) {
		this.mouseEnteredListener = mouseEnteredListener;
	}

	public void setMouseLeftListener(MouseLeftListener mouseLeftListener) {
		this.mouseLeftListener = mouseLeftListener;
	}

	public void setMouseMovedListener(MouseMovedListener mouseMovedListener) {
		this.mouseMovedListener = mouseMovedListener;
	}

	public void setMouseWheelMovedListener(MouseWheelMovedListener mouseWheelMovedListener) {
		this.mouseWheelMovedListener = mouseWheelMovedListener;
	}

	public void setResizedListener(ResizedListener resizedListener) {
		this.resizedListener = resizedListener;
	}

	protected abstract void update();
}
