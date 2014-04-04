package game.shootingtaco;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LoadingScreen implements Screen{
	
	private SpriteBatch spriteBatch;
	private OrthographicCamera cam;
	private Animation loading;
	private Game game;
	
	private float times;
	
	public LoadingScreen(Game game){
		
		this.game = game;
		
		spriteBatch = new SpriteBatch();
		cam = new OrthographicCamera(1280,800);
		cam.position.set(640,400,0);
		loading = new Animation(0.5f,new TextureRegion(new Texture(Gdx.files.internal("data/Loading01.png")),0,0,1280,800),
						new TextureRegion(new Texture(Gdx.files.internal("data/Loading02.png")),0,0,1280,800));
		Asset.loadAsset();
		times = 0;
	}
	
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		times += delta;
		cam.update();
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.enableBlending();
		spriteBatch.begin();
			spriteBatch.draw(loading.getKeyFrame(times, Animation.ANIMATION_LOOPING),0,0,1280,800);
		spriteBatch.end();
		
		if(Asset.manager.update()){
			Asset.load();
			Asset.playSound(Asset.choice);
			game.setScreen(new MenuScreen(game));
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
