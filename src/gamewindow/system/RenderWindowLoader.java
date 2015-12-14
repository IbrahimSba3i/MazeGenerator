package gamewindow.system;

import java.io.IOException;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;

public class RenderWindowLoader {
	
	public static RenderWindow getRenderWindow() {
		String windowName = "Game";
		VideoMode mode = VideoMode.getDesktopMode();
		RenderWindow window = new RenderWindow(mode, windowName);
		
		try {
			IniFileParser parser = new IniFileParser("main.ini");			
			windowName = parser.getString("window", "name", windowName);
			if(parser.containsSection("videomode")) {
				int width = Integer.parseInt(parser.getString("videomode", "width", "-1"));
				int height = Integer.parseInt(parser.getString("videomode", "height", "-1"));
				if(width != -1 && height != -1) {
					mode = new VideoMode(width, height);
				}				
			}

			if(parser.containsSection("style")) {
				String state = parser.getString("style", "state", "not_found");
				
				int hasCloseButton = (Boolean.parseBoolean(parser.getString("style", "close", "false"))? RenderWindow.CLOSE : 0);
				int hasTitleBar = (Boolean.parseBoolean(parser.getString("style", "titlebar", "false"))? RenderWindow.TITLEBAR : 0);
				int resizable = (Boolean.parseBoolean(parser.getString("style", "resize", "false"))? RenderWindow.RESIZE : 0);
				
				int total = hasCloseButton | hasTitleBar | resizable;
				if(state == "not_found") {
					if(total == 0) {
						total = RenderWindow.DEFAULT;
					}
				}
				else if(state == "default") {
					total = RenderWindow.DEFAULT;
				}
				else if(state == "fullscreen") {
					total = RenderWindow.FULLSCREEN;
				}
				
				window.create(mode, windowName, total);
			}
			else {
				window.create(mode, windowName);
			}
		} catch(IOException e) {
			window.create(mode, windowName);
		}
		
		return window;
	}
}
