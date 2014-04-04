package game.shootingtaco;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MenuScreen implements Screen{

	private SpriteBatch spriteBatch;
	private OrthographicCamera menuCam;
	private Rectangle play;
	private Rectangle howToPlay;
	private Vector3 touchPoint;
	Game game;
	
	
	
	public MenuScreen(Game game){
		
		this.game = game;
		
		menuCam = new OrthographicCamera(1280,800);
		menuCam.position.set(640.0f,400.0f,0.0f);
		
		spriteBatch = new SpriteBatch();
		
		
		touchPoint = new Vector3();
		
		play = new Rectangle(555,202,167,73);
		howToPlay = new Rectangle(524,113,232,61);
		Gdx.input.setCatchBackKey(true);
		
	}
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		menuCam.update();
		spriteBatch.setProjectionMatrix(menuCam.combined);
		spriteBatch.enableBlending();
		spriteBatch.begin();
			spriteBatch.draw(Asset.menuPage,0,0,1280,800);
			if(LevelSetting.voice)
				spriteBatch.draw(Asset.soundon, 10, 20,128,128);
			else
				spriteBatch.draw(Asset.soundoff, 10, 20,128,128);
		spriteBatch.end();
		
		if(Gdx.input.justTouched()){
			touchMenu();
		}
		if(Gdx.input.isKeyPressed(Keys.BACK)){
			Gdx.app.exit();
		}
		
	}
	
	public void touchMenu(){
		
		menuCam.unproject(touchPoint.set(Gdx.input.getX(0), Gdx.input.getY(0), 0));
		
		Rectangle soundBonus = new Rectangle(menuCam.position.x-640+10,menuCam.position.y-400+20,128,128);
		
		if(OverlapTester.pointInRectangle(play, touchPoint.x, touchPoint.y)){
			System.out.println("Touch Play!!");
			game.setScreen(new ChoiceScreen(game));
			Asset.playSound(Asset.button);
		}
		if(OverlapTester.pointInRectangle(howToPlay,touchPoint.x,touchPoint.y)){
			Asset.playSound(Asset.button);
			game.setScreen(new HowToPlayScreen(game));
		}
		if(OverlapTester.pointInRectangle(soundBonus, touchPoint.x, touchPoint.y)){
			if(LevelSetting.voice) LevelSetting.voice = false;
			else LevelSetting.voice = true;
			LevelSetting.save();
			Asset.playSound(Asset.choice);
			if(LevelSetting.voice == false) Asset.choice.pause();
		} 
		
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		Asset.choice.pause();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		Asset.load();
		Asset.playSound(Asset.choice);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		Gdx.app.exit();
	}

}
