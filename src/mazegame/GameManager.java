package mazegame;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.List;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;

public class GameManager {
	private static Font font;
	private static Font consoleFont;
	private static Color grey = new Color(56, 56, 56);
	private static Color blue = new Color(30, 166, 188);
	
	public static Color getBlueColor() {
		return blue;
	}
	
	public static Color getGreyColor() {
		return grey;
	}
	
	public static Font getFont() {
		if(font == null) {
			font = new Font();
			try{
				font.loadFromFile(FileSystems.getDefault().getPath(DirectoryManager.getFontsPath(), "moonhouse.ttf"));
			}catch(IOException e) {
				System.out.println(e.toString());
			}
		}
		return font;
	}
	
	public static Font getConsoleFont() {
		if(consoleFont == null) {
			consoleFont = new Font();
			try{
				consoleFont.loadFromFile(FileSystems.getDefault().getPath(DirectoryManager.getFontsPath(), "consola.ttf"));
			}catch(IOException e) {
				System.out.println(e.toString());
			}
		}
		return consoleFont;
	}
	
	public static int getIndex(int x, int y) {
		return x * Globals.TILES_ROWS + y;
	}
	
	public static boolean withinBounds(int x, int y) {
		return (x >= 0 && x < Globals.TILES_COLS && y >= 0 && y < Globals.TILES_ROWS);
	}
	
	public static boolean getCell(List<Boolean> list, int x, int y) {
		return list.get(getIndex(x, y));
	}

	public static boolean removeUselessBlock() {
		return Globals.REMOVE_USELESS_BLOCKS;
	}

}
