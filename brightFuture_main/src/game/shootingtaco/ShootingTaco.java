/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package game.shootingtaco;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class ShootingTaco extends Game{
	boolean firstTimeCreate = true;

	@Override
	public void create () {
		LevelSetting.load();
		setScreen(new LoadingScreen(this));
		/*Asset.load();
		Asset.music.play();*/

	}

	/**
		* {@link Game#dispose()} only calls {@link Screen#hide()} so you need to override
		* {@link Game#dispose()} in order to call {@link Screen#dispose()} on each of your
		* screens which still need to dispose of their resources.
		* SuperJumper doesn't actually have such resources so this is only to complete the
		* example.
		*/
	@Override
	public void dispose () {
		super.dispose();
		Asset.choice.stop();
		Asset.bg.stop();
		Asset.bg2.stop();
		Gdx.app.exit();
		getScreen().dispose();
	}
}
