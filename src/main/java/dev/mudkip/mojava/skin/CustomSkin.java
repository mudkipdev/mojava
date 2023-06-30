package dev.mudkip.mojava.skin;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustomSkin implements Skin {
	private final URL url;
	private final Model model;
	
	public CustomSkin(final URL url, final Model model) {
		this.url = url;
		this.model = model;
	}
	
	@Override
	public byte[] readData() throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		InputStream inputStream = connection.getInputStream();
		byte[] buffer = new byte[inputStream.available()];
		inputStream.read(buffer);
		return buffer;
	}
	
	@Override
	public Skin.Model getModel() {
		return this.model;
	}
	
	public URL getURL() {
		return this.url;
	}
}
