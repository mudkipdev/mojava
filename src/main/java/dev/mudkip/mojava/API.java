package dev.mudkip.mojava;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@ApiStatus.Internal
public class API {
	public static UUID getUUIDFromUsername(String username) throws IOException, APIException {
		JsonObject json = getJSON(Endpoint.USERNAME_TO_UUID.getURL(username));
		
		if (json.has("errorMessage")) {
			throw new APIException(json.get("errorMessage").getAsString());
		} else {
			return UUID.fromString(Utility.addDashesToUUID(json.get("id").getAsString()));
		}
	}
	
	public static Map<String, String> getPropertiesFromUUID(UUID uuid) throws IOException, APIException {
		JsonObject json = getJSON(Endpoint.UUID_TO_PROPERTIES.getURL(uuid));
		Map<String, String> properties = new HashMap<>();
		
		if (json.has("errorMessage")) {
			throw new APIException(json.get("errorMessage").getAsString());
		} else {
			JsonArray propertyArray = json.get("properties").getAsJsonArray();
			
			for (JsonElement propertyElement : propertyArray.asList()) {
				JsonObject propertyObject = (JsonObject) propertyElement;
				String name = propertyObject.get("name").getAsString();
				String value = new String(Base64.getDecoder().decode(propertyObject.get("value").getAsString()));
				properties.put(name, value);
			}
		}
		
		return properties;
	}
	
	private static JsonObject getJSON(URL url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		InputStream inputStream = connection.getInputStream();
		Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		return new Gson().fromJson(reader, JsonObject.class);
	}
	
	private enum Endpoint {
		USERNAME_TO_UUID("https://api.mojang.com/users/profiles/minecraft/%s"),
		UUID_TO_PROPERTIES("https://sessionserver.mojang.com/session/minecraft/profile/%s");
		
		private final String endpoint;
		
		Endpoint(final String endpoint) {
			this.endpoint = endpoint;
		}
		
		public URL getURL(Object... arguments) throws IOException {
			return new URL(String.format(this.endpoint, arguments));
		}
	}
}
