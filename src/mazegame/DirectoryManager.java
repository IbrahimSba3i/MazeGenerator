package mazegame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DirectoryManager {
	
	public static String getSpritesPath() {
		return "res/images/sprites/";
	}
	
	public static String getAnimationsPath() {
		return "res/animation/";
	}
	
	public static String getLevelsPath() {
		return "res/levels/";
	}

	public static String getFontsPath() {
		return "res/fonts/";
	}
	
	public static String getNewLevelFileName() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(getAnimationsPath() + "maptracker"));
			int firstAvailable = Integer.parseInt(reader.readLine());
			reader.close();
			firstAvailable++;
			String result = "map" + firstAvailable + ".lvl";
			BufferedWriter writer = new BufferedWriter(new FileWriter(getAnimationsPath() + "maptracker"));
			writer.write(firstAvailable);
			writer.close();
			return result;
		} catch(IOException e) {
			System.out.println(e.toString());
			return "map.lvl";
		}
	}

	public static String getBackgroundsPath() {
		return "res/images/background/";
	}
}
