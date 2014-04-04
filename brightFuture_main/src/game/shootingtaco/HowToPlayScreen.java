package game.shootingtaco;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class HowToPlayScreen implements Screen,InputProcessor{
	
	private SpriteBatch spriteBatch;
	private OrthographicCamera menuCam;
	private Vector3 touchPoint;
	
	private Rectangle tap;
	private Sprite drawPage;
	
	int page;
	
	Game game;
	
	public HowToPlayScreen(Game game){
		this.game = game;
		
		menuCam = new OrthographicCamera(1280,800);
		menuCam.position.set(640.0f,400.0f,0.0f);
		
		spriteBatch = new SpriteBatch();		
		touchPoint = new Vector3();
		
		tap = new Rectangle(441,49,420,77);
		
		drawPage = new Sprite(Asset.howToPlay[0]);
		page = 1;
		
		Gdx.input.setInputProcessor(this);
		
	}
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		menuCam.update();
		spriteBatch.setProjectionMatrix(menuCam.combined);
		spriteBatch.enableBlending();
		
		drawPage.setPosition(menuCam.position.x-640, menuCam.position.y-400);
		
		spriteBatch.begin();
			drawPage.draw(spriteBatch);
		spriteBatch.end();
		
		
	}
	public void touchTap(){
		
		
		if(OverlapTester.pointInRectangle(tap, touchPoint.x, touchPoint.y)){
			if(page == 1){
				Asset.playSound(Asset.button);
				page++;
				drawPage = new Sprite(Asset.howToPlay[1]);
			}
			else if(page == 2){
				Asset.playSound(Asset.button);
				game.setScreen(new MenuScreen(game));
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
		menuCam.unproject(touchPoint.set(x, y, 0));
		touchTap();
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
