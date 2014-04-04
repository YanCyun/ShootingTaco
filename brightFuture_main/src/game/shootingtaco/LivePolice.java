package game.shootingtaco;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class LivePolice implements Screen{
	
	private static TextureRegion livePolice;
	private Rectangle police;
	private Rectangle callPolice;
	private Rectangle message;
	private Rectangle map;
	private boolean clickButton;
	
	private SpriteBatch spriteBatch;
	private Sprite sprite;
	private OrthographicCamera cam;
	private Vector3 touchPoint;
	Game game;
	
	
	public LivePolice(Game game){
		
		this.game = game;
		spriteBatch = new SpriteBatch();
		cam = new OrthographicCamera(720,1280);
		cam.position.set(360,640,0);
		
		
		livePolice = new TextureRegion(new Texture(Gdx.files.internal("data/livepolice.png")),0,0,720,1280);
		police = new Rectangle(115,815,495,145);
		callPolice = new Rectangle(115,590,495,145);
		message = new Rectangle(115,375,495,145);
		map = new Rectangle(115,150,495,145);
		
		sprite = new Sprite(livePolice);
		
		clickButton = false;
		
		touchPoint = new Vector3();
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cam.update();
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.enableBlending();
		
		if(Gdx.input.justTouched()){
			cam.unproject(touchPoint.set(Gdx.input.getX(0), Gdx.input.getY(0), 0));
			
			if(OverlapTester.pointInRectangle(police, touchPoint.x, touchPoint.y)){
				System.out.println("Touch Play!!");
				clickButton = true;
			}
			if(OverlapTester.pointInRectangle(callPolice, touchPoint.x, touchPoint.y)){
				System.out.println("Touch Play!!");
				clickButton = true;
			}
			if(OverlapTester.pointInRectangle(message, touchPoint.x, touchPoint.y)){
				System.out.println("Touch Play!!");
				clickButton = true;
			}
			if(OverlapTester.pointInRectangle(map, touchPoint.x, touchPoint.y)){
				System.out.println("Touch Play!!");
				clickButton = true;
			}		
		}
		
		spriteBatch.begin();
			if(!clickButton)
				sprite.draw(spriteBatch);
		spriteBatch.end();
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
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
