package tests;

import dev.mudkip.mojava.Profile;
import dev.mudkip.mojava.SkinModel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ProfileTest {
	private static final UUID NOTCH_UUID = UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf5");
	private static final SkinModel NOTCH_SKIN_MODEL = SkinModel.CLASSIC;
	
	@Test
	public void testProfile() {
		Profile profile = Profile.fromUsername("Notch");
		assert profile.getUUID().equals(NOTCH_UUID);
		assert profile.getSkinModel() == NOTCH_SKIN_MODEL;
	}
}
