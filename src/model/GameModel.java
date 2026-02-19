package model;

import java.io.File;

public class GameModel {
	public final static int tileSize = 40;
	
	public File getlevel(int num) {
		if (num == 0) {
			File file = new File("levelblank.txt");
		}
		if (num == 1) {
			File file =  new File("level1.txt");
			return file;
		}
		File file =  new File("level2.txt");
		return file;
	}

}
