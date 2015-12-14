package mazegame;

import gamewindow.system.GameWindow;
import mazegame.menus.MainMenu;



public class Main {
	public static void main(String[] args) {
		GameWindow window = new MainMenu();
		window.open();
	}
}
