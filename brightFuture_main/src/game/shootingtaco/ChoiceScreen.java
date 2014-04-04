package game.shootingtaco;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class ChoiceScreen implements Screen,InputProcessor{

	Game game;
	
	SpriteBatch spriteBatch;
	Sprite choice_bg;
	Sprite choice;
	Sprite back;
	OrthographicCamera choiceCam;
	Rectangle[] level;
	Rectangle backBonus;
	Vector3 touchPoint;
	int levelCount;
	
	public ChoiceScreen(Game game){
		this.game = game;
		
		choiceCam = new OrthographicCamera(1280,800);
		choiceCam.position.set(640,400,0);
		
		spriteBatch = new SpriteBatch();
		choice_bg = new Sprite(Asset.choice_bg);
		choice = new Sprite(Asset.choicePart);
		back = new Sprite(Asset.back);
		touchPoint = new Vector3();
		
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(this);
		
		levelCount = 2;
		level = new Rectangle[levelCount];

	}
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		choiceCam.update();
		spriteBatch.setProjectionMatrix(choiceCam.combined);
		choice.setPosition(195, 213);
		back.setPosition(10, 20);
		spriteBatch.begin();
			choice_bg.draw(spriteBatch);
			choice.draw(spriteBatch);
			back.draw(spriteBatch);
			for(int i = 0 ; i < level.length ; i++){
				if(LevelSetting.levelLock[i*5] != 1){
					spriteBatch.draw(Asset.partLock,195+(i*521),213,380,380);
				}
			}
		spriteBatch.end();
		
		if(Gdx.input.justTouched()){
			touchChoice();
		}
		if(Gdx.input.isKeyPressed(Keys.BACK)){
			//game.setScreen(new MenuScreen(game));
		}
	}
	public void touchChoice(){
		
		choiceCam.unproject(touchPoint.set(Gdx.input.getX(0),Gdx.input.getY(0),0));
		
		//checkTouchBack
		backBonus = new Rectangle(choiceCam.position.x-640+10,choiceCam.position.y-400+20,128,128);
		if(OverlapTester.pointInRectangle(backBonus, touchPoint.x, touchPoint.y)){
			game.setScreen(new MenuScreen(game));
			Asset.playSound(Asset.button);
		}
		//checkTouchChoice
		for(int i = 0 ; i < level.length ; i++){
			if(LevelSetting.levelLock[i*5] == 1)
				level[i] = new Rectangle(195+(i*521),213,376,378);
			else
				continue;

			if(OverlapTester.pointInRectangle(level[i], touchPoint.x, touchPoint.y)){
				System.out.println(i);
				game.setScreen(new LevelScreen(game,i+1));
				Asset.playSound(Asset.button);
			}
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
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
