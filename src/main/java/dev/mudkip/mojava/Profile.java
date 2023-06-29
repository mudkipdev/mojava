package dev.mudkip.mojava;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Profile {
	private final UUID uuid;
	private final String username;
	private final long requestedAt;
	private Map<String, String> properties;
	
	protected Profile(UUID uuid, String username) {
		this.uuid = uuid;
		this.username = username;
		this.requestedAt = System.currentTimeMillis();
	}
	
	public static Profile fromUsername(String username) {
		Optional<Profile> cachedProfile = Cache.getProfile(username);
		return cachedProfile.orElseGet(() -> {
			try {
				UUID uuid = API.getUUIDFromUsername(username);
				Profile profile = new Profile(uuid, username);
				Cache.addProfile(profile);
				return profile;
			} catch (IOException | APIException e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	public UUID getUUID() {
		return this.uuid;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public Optional<String> getSkinURL() {
		JsonObject texturesJSON = this.getPropertyAsJSON("textures")
				.orElseThrow(RuntimeException::new)
				.getAsJsonObject("textures");
		
		if (!texturesJSON.has("SKIN")) {
			return Optional.empty();
		} else {
			JsonObject skinJSON = texturesJSON.getAsJsonObject("SKIN");
			return Optional.of(skinJSON.get("url").getAsString());
		}
	}
	
	public SkinModel getSkinModel() {
		JsonObject texturesJSON = this.getPropertyAsJSON("textures")
				.orElseThrow(RuntimeException::new)
				.getAsJsonObject("textures");
		
		if (!texturesJSON.has("SKIN")) {
			return SkinModel.CLASSIC;
		} else {
			JsonObject skinJSON = texturesJSON.getAsJsonObject("SKIN");
			
			if (!skinJSON.has("metadata")) {
				return SkinModel.CLASSIC;
			} else {
				return SkinModel.fromString(
						skinJSON
						.getAsJsonObject("metadata")
						.get("model")
						.getAsString()
				);
			}
		}
	}
	
	public Optional<String> getCapeURL() {
		JsonObject texturesJSON = this.getPropertyAsJSON("textures")
				.orElseThrow(RuntimeException::new)
				.getAsJsonObject("textures");
		
		if (!texturesJSON.has("CAPE")) {
			return Optional.empty();
		} else {
			JsonObject capeJSON = texturesJSON.getAsJsonObject("CAPE");
			return Optional.of(capeJSON.get("url").getAsString());
		}
	}
	
	public Optional<String> getProperty(String key) {
		if (this.properties == null) {
			try {
				this.properties = API.getPropertiesFromUUID(this.uuid);
			} catch (IOException | APIException e) {
				throw new RuntimeException(e);
			}
		}
		
		return Optional.ofNullable(properties.get(key));
	}
	
	public Optional<JsonObject> getPropertyAsJSON(String key) {
		if (!this.getProperty(key).isPresent()) {
			return Optional.empty();
		}
		
		return Optional.ofNullable(new Gson().fromJson(
				this.getProperty(key).get(),
				JsonObject.class
		));
	}
	
	protected long getRequestedAt() {
		return this.requestedAt;
	}
}
