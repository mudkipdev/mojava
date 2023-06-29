package dev.mudkip.mojava;

public enum SkinModel {
	CLASSIC, SLIM;
	
	public static SkinModel fromString(String string) {
		return string.equals("slim") ? SLIM : CLASSIC;
	}
}
