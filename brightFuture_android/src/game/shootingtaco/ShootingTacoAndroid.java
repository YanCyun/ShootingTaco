
package game.shootingtaco;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import game.shootingtaco.ShootingTaco;

public class ShootingTacoAndroid extends AndroidApplication {
	/** Called when the activity is first created. */
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize(new ShootingTaco(), false);
	}
}
