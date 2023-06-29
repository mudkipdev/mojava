package dev.mudkip.mojava;

public class Skin {
	public enum Model {
		CLASSIC, SLIM;
		
		public static Model fromString(String string) {
			return string.equals("slim") ? SLIM : CLASSIC;
		}
	}
}
