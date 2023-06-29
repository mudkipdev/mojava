package dev.mudkip.mojava;

import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@ApiStatus.Internal
public class Cache {
	public static final long INVALIDATION_PERIOD = 600000;
	private static final Map<UUID, Profile> uuidToProfileMap = new HashMap<>();
	private static final Map<String, Profile> usernameToProfileMap = new HashMap<>();
	// TODO: Cache properties
	
	public static Optional<Profile> getProfile(UUID uuid) {
		Optional<Profile> profile = Optional.ofNullable(uuidToProfileMap.get(uuid));
		boolean isOutdated = profile.isPresent() && (System.currentTimeMillis() - INVALIDATION_PERIOD > profile.get().getRequestedAt());
		return isOutdated ? Optional.empty() : profile;
	}
	
	public static Optional<Profile> getProfile(String username) {
		Optional<Profile> profile = Optional.ofNullable(usernameToProfileMap.get(username));
		boolean isOutdated = profile.isPresent() && (System.currentTimeMillis() - INVALIDATION_PERIOD > profile.get().getRequestedAt());
		return isOutdated ? Optional.empty() : profile;
	}
	
	public static void addProfile(Profile profile) {
		if (!uuidToProfileMap.containsKey(profile.getUUID())) {
			uuidToProfileMap.put(profile.getUUID(), profile);
		}
		
		if (!usernameToProfileMap.containsKey(profile.getUsername())) {
			usernameToProfileMap.put(profile.getUsername(), profile);
		}
	}
	
	public static void invalidate() {
		uuidToProfileMap.clear();
		usernameToProfileMap.clear();
	}
}
