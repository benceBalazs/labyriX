import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.Gdx;
@RunWith(GdxTestRunner.class)
public class AssetExistsExampleTest {

	@Test
	public void badlogicLogoFileExists() {
		assertTrue("This test will only pass when the badlogic.jpg file coming with a new project setup has not been deleted.", Gdx.files
				.internal("../android/assets/badlogic.jpg").exists());
	}
}
