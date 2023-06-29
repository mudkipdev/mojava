package dev.mudkip.mojava;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class Utility {
	public static String removeDashesFromUUID(String uuid) {
		return uuid.replace("-", "");
	}
	
	public static String addDashesToUUID(String uuid) {
		return new StringBuilder(uuid)
				.insert(20, "-")
				.insert(16, "-")
				.insert(12, "-")
				.insert(8, "-")
				.toString();
	}
}
