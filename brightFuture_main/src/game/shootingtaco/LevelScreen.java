package game.shootingtaco;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class LevelScreen implements Screen{
	
	Game game;
	
	SpriteBatch spriteBatch;
	Sprite selectLevel;
	Sprite back;
	OrthographicCamera levelCam;
	Rectangle[] level;
	Rectangle backBonus;
	Vector3 touchPoint;
	int levelCount;
	int part;
	
	public LevelScreen(Game game,int part){
		this.game = game;
		this.part = part;
		
		levelCam = new OrthographicCamera(1280,800);
		levelCam.position.set(640,400,0);
		
		spriteBatch = new SpriteBatch();
		selectLevel = new Sprite(Asset.selectLevel);
		back = new Sprite(Asset.back);
		touchPoint = new Vector3();
		
		Gdx.input.setCatchBackKey(true);
		
		levelCount = 5;
		level = new Rectangle[levelCount];
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		levelCam.update();
		spriteBatch.setProjectionMatrix(levelCam.combined);
		back.setPosition(10, 20);
		spriteBatch.begin();
			selectLevel.draw(spriteBatch);
			back.draw(spriteBatch);
			drawStar();
			for(int i = 0 ; i < level.length ; i++){
				if(LevelSetting.levelLock[(part-1)*5+i] != 1){
					spriteBatch.draw(Asset.levelLock,83+(i*233),330,192,240);
				}
			}
		spriteBatch.end();
		
		if(Gdx.input.justTouched()){
			touchLevel();
		}
		if(Gdx.input.isKeyPressed(Keys.BACK)){
			//game.setScreen(new ChoiceScreen(game));
		}
		
	}
	
	public void drawStar(){
		for(int i = 0 ; i < levelCount ; i++){
			if(LevelSetting.levelStar[(part-1)*5+i] != 0){
				for(int j = 0 ; j <3 ; j++){
					if(j < LevelSetting.levelStar[(part-1)*5+i]-1)
						spriteBatch.draw(Asset.star, 105+i*233+j*51, 334, 45, 45);
				}
			}
		}
	}
	
	public void touchLevel(){
		levelCam.unproject(touchPoint.set(Gdx.input.getX(0),Gdx.input.getY(0),0));
		
		//checkTouchBack
		backBonus = new Rectangle(levelCam.position.x-640+10,levelCam.position.y-400+20,128,128);
		if(OverlapTester.pointInRectangle(backBonus, touchPoint.x, touchPoint.y)){
			game.setScreen(new ChoiceScreen(game));
			Asset.playSound(Asset.button);
		}
		//checkTouchLevel
		for(int i = 0 ; i < level.length ; i++){
			if(LevelSetting.levelLock[(part-1)*5+i] == 1)
				level[i] = new Rectangle(83+(i*233),330,192,240);
			else continue;
			
			if(OverlapTester.pointInRectangle(level[i], touchPoint.x, touchPoint.y)){
				Asset.playSound(Asset.button);
				System.out.println(i);
				game.setScreen(new GameScreen(game,part,i+1));
				Asset.choice.stop();	
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

}
