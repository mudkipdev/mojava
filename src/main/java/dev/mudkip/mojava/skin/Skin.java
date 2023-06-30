package dev.mudkip.mojava.skin;

import java.io.IOException;

public interface Skin {
	byte[] readData() throws IOException;
	
	Model getModel();
	
	enum Model {
		CLASSIC, SLIM;
		
		public static Model fromString(String string) {
			return string.equals("slim") ? SLIM : CLASSIC;
		}
	}
}
