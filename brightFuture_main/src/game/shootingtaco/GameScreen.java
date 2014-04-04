package game.shootingtaco;

import com.badlogic.gdx.graphics.Color;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import game.shootingtaco.OverlapTester;

public class GameScreen implements Screen,InputProcessor,ContactListener{
	
	
	public static final float PIXELS_PER_METER = 60.0f;
	public static final int FACE_UP = 1;
	public static final int FACE_DOWN = 2;
	public static final int FACE_LEFT = 3;
	public static final int FACE_RIGHT = 4;
	public static final int IN_BUBBLE = 5;
	public static final int PAUSE = 5;
	public static final int RUNNING = 6;
	public static final int LEVEL_END = 7;
	public static final int GAME_OVER = 8;
	
	public World world;
	public Game game;
	public Body taco,ridedTurtle;
	public OrthographicCamera camera;
	public SpriteBatch spriteBatch;
	public Sprite spriteTaco,pause,pauseBonus,background,levelEnd,die;
	public Level level;
	public Rectangle bulletButton;
	public int state;
	
	private float starX,starY,moveX,moveY,endX,endY;
	private double theta;
	private float camX,camY;
	private Box2DDebugRenderer debugRenderer;
	private int count,gameState,camHeight,camWidth,jellyCount;
	private float turnTheta;
	private boolean fly,gameStart,focusTaco,touchTaco,rided,bubble,touchCoral;
	private float accelX,accelY;
	private int totalWidth,totalHeight;
	private float gameTime;
	private float speed;  
	private int energy;
	private Vector2 jellySpeed;
	private int health;
	private float[][] tail;
	private Bullet[] bullets;
	private float pullX,pullY;
	public float tailTime,coralTime;
	public int bulletCount;
	public boolean[][] turtleTurn;
	public Vector2 tmpSpeed;
	public float[] starTime;
	public int star,tmpStar;
	public boolean clear;
	public float dieTime,bubbleTime,bubbleTeachTime,bulletTeachTime,changePartTime,shootTime;
	public boolean bubbleTeach,bulletTeach,changePart,deathTime,lose,pull,bulletTeachSound,bubbleCDSound,camTranslation;
	public int score,eatStar;
	
	private int levelNumber,part;
	//array value[x,y,width,height,second,point,health]
	private int levelValue[][][] = {{{0,249,93,130,100,0,1},{0,0,100,83,100,14,1},{0,400,93,133,150,16,20},{0,178,107,66,100,28,100},{0,91,109,80,100,17,100},{0,0,0,0,0,0,0}},
								  {{0,540,123,112,100,29,100},{0,864,123,112,100,10,100},{390,0,138,113,100,39,50},{0,752,130,102,100,28,100},{0,655,148,88,100,10,60},{0,0,0,0,0,0,0}}
								  }; 
	 
	public GameScreen(Game game,int part,int levelN){
		this.game = game;
		this.levelNumber = levelN-1;
		this.part=part;
		
		Asset.background = Asset.load("data/level"+part+"_bg.png",1280,800);
		Asset.wall = Asset.split("data/wall"+part+".png",32,32);
		
		world = new World(new Vector2(0.0f,0.0f),true);
		level = new Level(this,levelValue[part-1][levelNumber][0],levelValue[part-1][levelNumber][1],levelValue[part-1][levelNumber][2],levelValue[part-1][levelNumber][3]);
		totalHeight = levelValue[part-1][levelNumber][3]*32;
		totalWidth = levelValue[part-1][levelNumber][2]*32;
		health = levelValue[part-1][levelNumber][6];
		
		camHeight = 640;
		camWidth = 960;
		
		camera = new OrthographicCamera(camWidth,camHeight);
		camera.position.set(camWidth/2,camHeight/2,0);
		
		spriteTaco = new Sprite(Asset.player);
		spriteTaco.setScale(1.0f,1.0f);
		spriteTaco.setRotation(90.0f);
		pauseBonus = new Sprite(Asset.pauseBonus);
		pause = new Sprite(Asset.pause);
		background = new Sprite(Asset.background);
		levelEnd = new Sprite(Asset.levelEnd);
		die = new Sprite(Asset.die);
		
		spriteBatch = new SpriteBatch();
		
		
		world.setContactListener(this);
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(level.tacoX,level.tacoY);
		
		taco = world.createBody(bodyDef);
		state = FACE_UP;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(36/(PIXELS_PER_METER*2), 36/(PIXELS_PER_METER*2));
		
		taco.setFixedRotation(true);
		
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 10.0f;
		fixDef.friction = 1.0f;
		fixDef.restitution = 0.0f;
		
		taco.createFixture(fixDef);
		taco.setLinearVelocity(0.0f, -2.0f);
		
		shape.dispose();
			
		debugRenderer = new Box2DDebugRenderer();
		
		if((levelNumber == 0 ||levelNumber == 1 )&& part != 2) bubbleTeach = false;
		else bubbleTeach = true;
		
		bullets = new Bullet[50];
		
		speed = 10.0f;
		if((levelNumber == 0 ||levelNumber == 1 )&& part != 2) speed = 7.0f;
		gameState = RUNNING;
		fly = false;
		gameStart = false;
		focusTaco = true;
		touchTaco = false;
		rided = false;
		gameTime = levelValue[part-1][levelNumber][4];
		turtleTurn = new boolean[level.turtles.size()][level.invisibleWalls.size()];
		starTime = new float[3];
		eatStar = 0;
		
		if(part == 1) Asset.playSound(Asset.bg);
		else if(part == 2) Asset.playSound(Asset.bg2);
	}
	
	@Override
	public void render(float delta) {

		//System.out.println("Time:" +gameTime);
		
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.enableBlending();
		if(Gdx.input.isKeyPressed(Keys.BACK)){
			gameState = PAUSE;
		}
		
		
		if(gameState ==RUNNING)	world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);


		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		background.setPosition(camera.position.x-camWidth/2, camera.position.y-camHeight/2);
		spriteBatch.begin();
			background.draw(spriteBatch);
		spriteBatch.end();
		
		level.render(this, camera);
		
		if(!fly && !Gdx.input.isTouched()){
			switch(state){
				case FACE_UP:pullY = 16.0f;
							 pullX = 0.0f;
							 break;
				case FACE_DOWN:pullY = -16.0f;
							   pullX = 0.0f;
				 break;
				case FACE_RIGHT:pullX = 16.0f;
							    pullY = 0.0f;
				 break;
				case FACE_LEFT:pullX = -16.0f;
				               pullY = 0.0f;
				 break;
			}
		}
		
		if(gameState == RUNNING)   shootBullet();//Bullet
			
		
		spriteTaco.setPosition(
				PIXELS_PER_METER * taco.getPosition().x
						- spriteTaco.getWidth() / 2+pullX,
				PIXELS_PER_METER * taco.getPosition().y
						- spriteTaco.getHeight() / 2+pullY);	
		spriteBatch.begin();
			drawTail();
			if(dieTime == 0)
				spriteTaco.draw(spriteBatch);
			if(bubble) spriteBatch.draw(Asset.bubble, PIXELS_PER_METER * taco.getPosition().x-64,PIXELS_PER_METER * taco.getPosition().y-64,128,128);
			if(dieTime != 0) {
				spriteBatch.draw(Asset.dieAni.getKeyFrame(dieTime, Animation.ANIMATION_NONLOOPING), PIXELS_PER_METER * taco.getPosition().x-64,PIXELS_PER_METER * taco.getPosition().y-48,128,96);
			}
		spriteBatch.end();
		
		

		if(gameState == RUNNING) updateRunning();
		if(gameState == PAUSE) updatePause();
		else if(gameState == LEVEL_END)   updateLevelEnd();
		else if(gameState == GAME_OVER)   {
			dieTime +=delta;
			if(dieTime > 0.7f)
				updateGameOver();
		}
		
		
		/*debugRenderer.render(world, camera.combined.scale(
				GameScreen.PIXELS_PER_METER,
				GameScreen.PIXELS_PER_METER,
				GameScreen.PIXELS_PER_METER));
		*/
		if((focusTaco || (taco.getLinearVelocity().x !=0.0 || taco.getLinearVelocity().y !=0.0)) && !camTranslation)
			setCamPosition(taco.getPosition().x*PIXELS_PER_METER,taco.getPosition().y*PIXELS_PER_METER);
	}
	public void drawTail(){
		if(gameState == RUNNING){
			if(fly){
				tail[0][0] = PIXELS_PER_METER * taco.getPosition().x- spriteTaco.getWidth() / 2;
				tail[0][1] = PIXELS_PER_METER * taco.getPosition().y- spriteTaco.getHeight() / 2;
				for(int i = tail.length-1 ; i > 0 ;i--){
					tail[i][0] = tail[i-1][0];
					tail[i][1] = tail[i-1][1];
				}
			}
			int tmpSpeed;
			if((levelNumber == 0 ||levelNumber == 1 )&& part != 2) tmpSpeed = 7;
			else tmpSpeed = 10;
			if(tail != null){
				for(int i = 3 ; i < tail.length ; i++){
					if(tail[i][0]!= 0){
						if(i == 0)
							spriteBatch.draw(Asset.tail[(int)(i/1.5f)][(int)speed-tmpSpeed],tail[i][0],tail[i][1],64,64);
						else{
							if(tail[i-1][0] == 0)
								spriteBatch.draw(Asset.tail[(int)(i/1.5f)][(int)speed-tmpSpeed],tail[i][0],tail[i][1],64,64);
							else{
								spriteBatch.draw(Asset.tail[(int)(i/1.5f)][(int)speed-tmpSpeed],tail[i][0],tail[i][1],64,64);
								for(int j = 1 ; j < 11 ; j++){
									spriteBatch.draw(Asset.tail[(int)(i/1.5f)][(int)speed-tmpSpeed],((tail[i-1][0]-tail[i][0])/11.0f)*j+tail[i][0],((tail[i-1][1]-tail[i][1])/11.0f)*j+tail[i][1],64,64);
								}
							}
						}
					}
				}
			}
		}
	}
	public void checkTacoCollision(){
		if(taco.getPosition().x*PIXELS_PER_METER > totalWidth || taco.getPosition().x*PIXELS_PER_METER < 0 || 
				taco.getPosition().y*PIXELS_PER_METER > totalHeight ||taco.getPosition().y*PIXELS_PER_METER < 0){
			Asset.breakWall.stop();
			Asset.playSound(Asset.breakWall);
			gameState = GAME_OVER;
		}
		//checkTime
		if(gameTime < 0){
			Asset.breakWall.stop();
			Asset.playSound(Asset.breakWall);
			gameState = GAME_OVER;
		}
		
		//checkCollisionThorn
		Rectangle tacoR ;
		if((theta >135.0f && theta < 225.0f) || (theta > 315.0f || theta < 45.0f)){
			tacoR= new Rectangle(taco.getPosition().x*PIXELS_PER_METER-(64)/2+pullX,
											taco.getPosition().y*PIXELS_PER_METER-(32)/2+pullY,
											64,
											32);
		}
		else{
			tacoR = new Rectangle(taco.getPosition().x*PIXELS_PER_METER-(32)/2+pullX,
											taco.getPosition().y*PIXELS_PER_METER-(64)/2+pullY,
											32,
											64);
		}
		for(int i = 0 ; i < level.thorns.size() ; i++){
			
			
			if(OverlapTester.overlapRectangles(tacoR,level.thorns.get(i).bounds )){
				Asset.breakWall.stop();
				Asset.playSound(Asset.breakWall);
				gameState = GAME_OVER;
			}
			
		} 
		//checkCollisionGoal
		for(int i = 0 ; i < level.goals.size(); i++){
			if(OverlapTester.overlapRectangles(tacoR, level.goals.get(i).bounds)){
				gameState = LEVEL_END;
			}
		}
		//checkCollisionBullet
		for(int i = 0 ; i < level.touchWalls.size();i++){
			for(int j = 0 ; j < bullets.length ; j++){
				
				if(bullets[j] != null){		
					if(OverlapTester.overlapRectangles(bullets[j].bounds, level.touchWalls.get(i).bounds)){
						bullets[j] = null;
						System.out.println("1111111111111");
					}
				}
			}
		}
		//checkCollisionEnergyFish
		for(int i = 0 ; i < level.fishs.size();i++){
			if(level.fishs.get(i).speed != 0) level.fishs.get(i).tmpSpeed = level.fishs.get(i).speed ;
			if(gameState != RUNNING){
				level.fishs.get(i).speed  = 0 ; 
			}
			else{
				if(level.fishs.get(i).speed == 0 ) level.fishs.get(i).speed = level.fishs.get(i).tmpSpeed;
			}
			for(int j = 0 ; j <level.invisibleWalls.size();j++){
				if(OverlapTester.overlapRectangles(level.fishs.get(i).bounds,level.invisibleWalls.get(j).bounds)){
					level.fishs.get(i).speed = level.fishs.get(i).speed*(-1);
				}
			}
			if(OverlapTester.overlapRectangles(tacoR, level.fishs.get(i).bounds)){
				if(level.fishs.get(i).eated != true){
					speed += 1;
					Asset.fish.stop();
					Asset.playSound(Asset.fish);
				}
				level.fishs.get(i).eated = true;
				//level.fishs.remove(i);
				if(speed >= 14.0f) speed = 14.0f;
			}
		}
		//checkCollisionShell
		for(int i = 0 ; i < level.shells.size();i++){
			if(OverlapTester.overlapRectangles(tacoR, level.shells.get(i).bounds)){
				if(level.shells.get(i).eated != true){
					bulletCount += 5;
					eatStar++;
					int tmp = (int)(Math.random()*1000) + 1500;
					score += tmp;
					Asset.shellM.stop();
					Asset.playSound(Asset.shellM);
				}
				level.shells.get(i).eated = true;
			}
		}
		//checkCollisionLaps
		for(int i = 0 ; i < level.lapes.size() ; i++){
			if(OverlapTester.overlapRectangles(tacoR, level.lapes.get(i).bounds)){
				Asset.lapsM.stop();
				Asset.playSound(Asset.lapsM);
				float tmpTheta = (float)theta+turnTheta;
				if(tmpTheta >360.0f) tmpTheta -= 360.0f;  
				if(level.lapes.get(i).state == Laps.FACE_UP){
					if(tmpTheta >270.0f)	theta = 360.0f-(tmpTheta);
					else             			theta = 180.0f-((tmpTheta)-180.0f);
					if(theta>=180.0f && theta<=360.0f) theta = 90.0f;
					turnTheta = 0;					
				}
				if(level.lapes.get(i).state == Laps.FACE_DOWN){
					if(tmpTheta >90.0f)	theta = 180.0f+(180.0f-tmpTheta);
					else             			theta = 360.0f-tmpTheta;
					if(theta>=0.0f && theta<=180.0f) theta = 270.0f;
					turnTheta = 0;					
				}
				if(level.lapes.get(i).state == Laps.FACE_LEFT){
					if(tmpTheta >180.0f)	theta = 270.0f-(tmpTheta-270.0f);
					else             			theta = 90.0f+(90.0f-tmpTheta);
					if(theta>=270.0f || theta<=90.0f) theta = 180.0f;
					turnTheta = 0;					
				}
				if(level.lapes.get(i).state == Laps.FACE_RIGHT){
					if(tmpTheta >180.0f)	theta = 270.0f+(270.0f-tmpTheta);
					else             			theta = 90.0f-(tmpTheta-90.0f);
					if((theta>=90.0f && theta<=270.0f)) theta = 0.0f;
					turnTheta = 0;					
				}
				shoot((float)theta);
				level.lapes.get(i).touch = true;
				System.out.println("touchLaps");
			}
		}
		//checkCollisionHealthFish
		for(int i = 0 ; i < level.healths.size();i++){
			if(level.healths.get(i).speed != 0) level.healths.get(i).tmpSpeed = level.healths.get(i).speed ;
			if(gameState != RUNNING){
				level.healths.get(i).speed  = 0 ; 
			}
			else{
				if(level.healths.get(i).speed == 0 ) level.healths.get(i).speed = level.healths.get(i).tmpSpeed;
			}
			for(int j = 0 ; j <level.invisibleWalls.size();j++){
				if(OverlapTester.overlapRectangles(level.healths.get(i).bounds,level.invisibleWalls.get(j).bounds)){
					level.healths.get(i).speed = level.healths.get(i).speed*(-1);
				}
			}			
			if(OverlapTester.overlapRectangles(tacoR, level.healths.get(i).bounds)){
				if(level.healths.get(i).eated != true){
					health += 30;
					Asset.fish.stop();
					Asset.playSound(Asset.fish);
				}
				level.healths.get(i).eated = true;
				if(health > 100) health = 100;
			}

		}
		//checkJellyFishCollision
		for(int i = 0 ; i < level.jellyfishs.size();i++){
			if(level.jellyfishs.get(i).speed != 0) level.jellyfishs.get(i).tmpSpeed = level.jellyfishs.get(i).speed ;
			if(gameState != RUNNING){
				level.jellyfishs.get(i).speed  = 0 ; 
			}
			else{
				if(level.jellyfishs.get(i).speed == 0 ) level.jellyfishs.get(i).speed = level.jellyfishs.get(i).tmpSpeed;
			}
			for(int j = 0 ; j <level.invisibleWalls.size();j++){
				if(OverlapTester.overlapRectangles(level.jellyfishs.get(i).bounds,level.invisibleWalls.get(j).bounds)){
					if(!level.jellyfishs.get(i).touch)
						level.jellyfishs.get(i).speed = level.jellyfishs.get(i).speed*(-1);
				}
			}			
			if(OverlapTester.overlapRectangles(tacoR, level.jellyfishs.get(i).bounds)){
				if(taco.getLinearVelocity().x == 0 && taco.getLinearVelocity().y == 0){
					level.jellyfishs.get(i).speed = level.jellyfishs.get(i).speed*(-1);
				}
				if(!level.jellyfishs.get(i).touch && !level.jellyfishs.get(i).die){
					level.jellyfishs.get(i).touch = true;
					health -= 10;
					theta = theta+180.0f;
					shoot((float)theta+turnTheta);
				}
			}
			else{
				level.jellyfishs.get(i).touch = false;
			}
			for(int j = 0 ; j < bullets.length ; j++){//Bullet touch Jellyfish
				if(bullets[j] != null && level.jellyfishs.size() > i){
					if(OverlapTester.overlapRectangles(level.jellyfishs.get(i).bounds,bullets[j].bounds)){
						level.jellyfishs.get(i).die = true;
						score += 150 + (int)(Math.random()*100);
						bullets[j] = null;
						Asset.bomb.stop();
						Asset.playSound(Asset.bomb);
					}
				}
			}
		}
		//checkCollisionSwirl
		for(int i = 0 ; i <level.swirls.size();i++){
			Swirl swirl = level.swirls.get(i);
			if(OverlapTester.overlapRectangles(tacoR, swirl.bounds)){
				if(!swirl.touch){
					swirl.touch = true;
					float tmpTheta = (float)theta+turnTheta;
					if(tmpTheta >360.0f) tmpTheta -= 360.0f;
					if(swirl.direction == Swirl.LEFT){
						theta = tmpTheta + 25.0f;
					}
					else{
						theta = tmpTheta - 25.0f;
					}
					turnTheta = 0;
					shoot((float)tmpTheta);
					System.out.println("touchSwirl");
					Asset.swirlM.stop();
					Asset.playSound(Asset.swirlM);
				}			
			}
			else{
				swirl.touch = false;
			}
		}
		//checkBreakWall
		for(int i = 0 ; i < level.breakWalls.size();i++){
			if(OverlapTester.overlapRectangles(tacoR, level.breakWalls.get(i).bounds)){
				if(!level.breakWalls.get(i).touch){
					level.breakWalls.get(i).touch = true;		
					if(taco.getLinearVelocity().x != 0 && taco.getLinearVelocity().y != 0 && !level.breakWalls.get(i).die){
						score += 150* (int)(Math.random()*100);
						theta = theta+180.0f;
						shoot((float)theta+turnTheta);
						level.breakWalls.get(i).die = true;
					}
					Asset.breakWall.stop();
					Asset.playSound(Asset.breakWall);
					return;
				}
			}
			else{
				level.breakWalls.get(i).touch = false;
			}
			for(int j = 0 ; j < bullets.length ; j++){//Bullet touch Jellyfish
				if(bullets[j] != null && level.breakWalls.size() > i){
					if(OverlapTester.overlapRectangles(level.breakWalls.get(i).bounds,bullets[j].bounds)){
						if(!level.breakWalls.get(i).die)
						{
							score += 150 + (int)(Math.random()*100);
							level.breakWalls.get(i).die = true;
							Asset.breakWall.stop();
							Asset.playSound(Asset.breakWall);
							bullets[j] = null;
						}
					}
				}
			}
		}
		//checkTurtleCollision
		for(int i = 0 ; i < level.turtles.size();i++){
			Body turtle = level.turtles.get(i);
			Rectangle turtleR = new Rectangle(turtle.getPosition().x*PIXELS_PER_METER-64,
									turtle.getPosition().y*PIXELS_PER_METER-32,
									128,
									64);
			if(level.turtles.get(i).getLinearVelocity().x == 0){
				if(Math.random() > 0.5){
					turtle.setLinearVelocity(3.0f, 0.0f);
				}
				else{
					turtle.setLinearVelocity(-3.0f, 0.0f);
				}
			}
			for(int j = 0 ; j <level.invisibleWalls.size();j++){
				if(OverlapTester.overlapRectangles(turtleR, level.invisibleWalls.get(j).bounds)){
					if(!turtleTurn[i][j]){
						turtleTurn[i][j] = true;
						turtle.setLinearVelocity(turtle.getLinearVelocity().x*(-1), 0.0f);
					}
				}
				else{
					turtleTurn[i][j] = false;
				}
			}
		}
		//checkCollisionCoral
		for(int i = 0 ; i < level.corals.size();i++){
			if(OverlapTester.overlapRectangles(tacoR,level.corals.get(i).bounds)){
				if(!touchCoral) {
					Asset.coralM.stop();
					Asset.playSound(Asset.coralM);
					health -= 30;
				}
				touchCoral = true;
				level.corals.get(i).touch = true;
			}
		}
		
		//checkBubble
		if(fly || (levelNumber == 0 && bubbleTeachTime > 0.5f && fly)){
			if(bubbleTime <= 0){
				if(Gdx.input.isTouched()){
					if(bulletCount > 0){
						Vector3 touchPoint = new Vector3(0,0,0);
						camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
						if(bulletButton != null){
							if(OverlapTester.pointInRectangle(bulletButton, touchPoint.x, touchPoint.y))
								return;
						}
					}
					bubbleTime = 10.0f;
					bubbleCDSound = false;
					turnTheta = 0.0f;
					fly = false;
					bubble = true;
					state = IN_BUBBLE;
					taco.setLinearVelocity(0.0f,0.0f);
					Asset.bubbleM.stop();
					Asset.playSound(Asset.bubbleM);
				}
			}
		}
	}
	public void updateRunning(){
		if(bubbleTime > 0.0f){
			bubbleTime -= Gdx.app.getGraphics().getDeltaTime();
		}
		else{
			if(!bubbleCDSound){
				Asset.playSound(Asset.bubbleCDM);
				bubbleCDSound = true;
			}
		}
		if(gameStart)
			gameTime -= Gdx.app.getGraphics().getDeltaTime();
		int tmpTime = (int)(gameTime*10);
		if(Gdx.input.justTouched()){
			Vector3 touchPoint = new Vector3(Gdx.input.getX(0),Gdx.input.getY(0),0.0f);
			camera.unproject(touchPoint);
			Rectangle pauesRect = new Rectangle(camera.position.x-camWidth/2+10,camera.position.y+camHeight/2-74,64,64);
			System.out.println("Touch Test"+(camera.position.x-camWidth/2)+","+(camera.position.y+camHeight/2));
			if(OverlapTester.pointInRectangle(pauesRect, touchPoint.x, touchPoint.y)){
				System.out.println("Touch PAUSE");
				gameState = PAUSE;
			}
		}
		checkTacoCollision();
		//checkHealth
		if(health <= 0){
			Asset.breakWall.stop();
			Asset.playSound(Asset.breakWall);
			gameState = GAME_OVER;
		}
		//ÂàÅs
		if((taco.getLinearVelocity().x !=0.0 || taco.getLinearVelocity().y !=0.0) && !rided)
			if(gameStart)
				turn();
		//bubble
		if(bubble){
			if(taco.getLinearVelocity().x != 0 || taco.getLinearVelocity().x != 0){
				bubble = false;
			}
		}
		//rided
		if(rided){
			taco.setLinearVelocity(ridedTurtle.getLinearVelocity());
		}
		//tail
		if(taco.getLinearVelocity().x == 0 && taco.getLinearVelocity().x == 0 || rided){
			if(tail == null)
				tail = new float[12][2];
			else{
				tail[0][0] = 0;
				tail[0][1] = 0;
				for(int i = tail.length-1 ; i > 0 ; i--){
					tail[i][0] = tail[i-1][0];
					tail[i][1] = tail[i-1][1];
				}
			}
		}
		//touchCoral
		if(touchCoral){
			coralTime +=Gdx.app.getGraphics().getDeltaTime();
			if(coralTime < 1.0f){
				taco.setLinearVelocity(0.0f, 0.0f);
				spriteTaco = new Sprite(Asset.tacoCoral);
				spriteTaco.rotate((float)theta+turnTheta);
			}
			else{
				shoot((float)theta+turnTheta);
				coralTime = 0.0f;
				touchCoral = false;
			}
		}
		
		if(gameTime >10) Asset.font.setColor(0.34f, 0.34f, 0.34f,1);
		if(gameTime < 10) Asset.font.setColor(1, 1, 0, 1);
		if(gameTime < 5) {
			Asset.font.setColor(1,0,0,1);
			if(!deathTime){
				Asset.playSound(Asset.deathTime);
				deathTime = false;
			}
		}
		pauseBonus.setPosition(camera.position.x-camWidth/2+10, camera.position.y+camHeight/2-74);
		//render health
		if(health >1){
		spriteBatch.begin();
			for(int i = 0 ; i < 100 ; i++){
				if(i < health){
					if(i == 0) spriteBatch.draw(Asset.health_begin,camera.position.x-camWidth/2+5+73,camera.position.y+camHeight/2-55,4,32);
					else if(i == 99) spriteBatch.draw(Asset.health_end,camera.position.x-camWidth/2+9+(i-1)*2+73,camera.position.y+camHeight/2-55,4,32);
					else spriteBatch.draw(Asset.health,camera.position.x-camWidth/2+9+(i-1)*2+73,camera.position.y+camHeight/2-55,2,32);
				}
				else{
					if(i == 0) spriteBatch.draw(Asset.none_health_begin,camera.position.x-camWidth/2+5+73,camera.position.y+camHeight/2-55,4,32);
					else if(i == 99) spriteBatch.draw(Asset.none_health_end,camera.position.x-camWidth/2+9+(i-1)*2+73,camera.position.y+camHeight/2-55,4,32);
					else spriteBatch.draw(Asset.none_health,camera.position.x-camWidth/2+9+(i-1)*2+73,camera.position.y+camHeight/2-55,2,32);
				}		
			}	
		spriteBatch.end();
		}
		
		spriteBatch.begin();
		//render bulletButton
			if(bulletCount > 0){
				spriteBatch.draw(Asset.bulletButton, camera.position.x-camWidth/2+11,camera.position.y-camHeight/2+ 13, 120,120);
			}
			Asset.bulletFont.setColor(Color.BLACK);
				
					
			spriteBatch.draw(Asset.bulletCount, camera.position.x-camWidth/2+750,camera.position.y-camHeight/2+ 13,80,63);
			if(bulletCount < 10)
				Asset.bulletFont.draw(spriteBatch, bulletCount+"", camera.position.x-camWidth/2+788, camera.position.y-camHeight/2+30);
			else
				Asset.bulletFont.draw(spriteBatch, bulletCount+"", camera.position.x-camWidth/2+783, camera.position.y-camHeight/2+30);
			
		//render speed
			if((levelNumber+1 != 1 && levelNumber+1 != 2) || part != 1)
				spriteBatch.draw(Asset.speed[(int)speed-10][0], camera.position.x-camWidth/2+863,camera.position.y-camHeight/2+13,65,63);
			else{
				spriteBatch.draw(Asset.speed[(int)speed-7][0], camera.position.x-camWidth/2+863,camera.position.y-camHeight/2+13,65,63);
			}
		//render BubbleCD
			Asset.bubbleFont.setColor(0.3f,0.3f,0.3f,1);
			if(bubbleTime > 0){
				spriteBatch.draw(Asset.bubbleCD[0][0], camera.position.x-camWidth/2+656,camera.position.y-camHeight/2+ 13, 60,60);
				Asset.bubbleFont.draw(spriteBatch, (int)bubbleTime + "", camera.position.x-camWidth/2+675, camera.position.y-camHeight/2+28);
			}
			else{
				if(fly)    
					spriteBatch.draw(Asset.bubbleCD[1][0], camera.position.x-camWidth/2+656,camera.position.y-camHeight/2+ 13, 60,60);
				else
					spriteBatch.draw(Asset.bubbleCD[0][0], camera.position.x-camWidth/2+656,camera.position.y-camHeight/2+ 13, 60,60);
			}
		//render Translation
			if(camTranslation){
				spriteBatch.draw(Asset.camShape, camera.position.x-camWidth/2, camera.position.y-camHeight/2,960,640);
				spriteBatch.draw(Asset.translation[0][0], camera.position.x-camWidth/2+863, camera.position.y-camHeight/2+90,64,64);
			}
			else{
				spriteBatch.draw(Asset.translation[1][0], camera.position.x-camWidth/2+863, camera.position.y-camHeight/2+90,64,64);
			}
		//render Score
			String scoreString = Integer.toString(score);
			while(scoreString.length() < 5){
				scoreString = " " + scoreString;
			}
			Asset.scoreFont.setColor(0.34f, 0.34f, 0.34f, 1);
			Asset.scoreFont.draw(spriteBatch, "SCORE:"+scoreString, camera.position.x-camWidth/2+720, camera.position.y+camHeight/2-58);
		spriteBatch.end();
		
		spriteBatch.begin();
			pauseBonus.draw(spriteBatch);
			Asset.font.draw(spriteBatch, tmpTime/10 + "."+ tmpTime%10,camera.position.x-50,camera.position.y+camHeight/2-60);
		spriteBatch.end();
		spriteBatch.begin();
		if(!bubbleTeach && fly){
			bubbleTeachTime += Gdx.app.getGraphics().getDeltaTime();
			spriteBatch.draw(Asset.bubbleTeachAni.getKeyFrame(bubbleTeachTime, Animation.ANIMATION_LOOPING),camera.position.x-camWidth/2+0,camera.position.y-camHeight/2+0,960,640);
			System.out.println("11111");	
		}
		
		if(Gdx.input.isTouched() && (fly || bubble))
			bubbleTeach = true;
		
		if(bulletCount > 0 && levelNumber == 1 && part != 2 && !bulletTeach){
			if(!bulletTeachSound){
				bulletTeachSound = true;
				Asset.playSound(Asset.message2);
			}
			bulletTeachTime += Gdx.app.getGraphics().getDeltaTime();
			gameTime += Gdx.app.getGraphics().getDeltaTime();
			spriteBatch.draw(Asset.bulletTeachAni.getKeyFrame(bulletTeachTime, Animation.ANIMATION_LOOPING),camera.position.x-camWidth/2+0,camera.position.y-camHeight/2+0,960,640);
			if(Gdx.input.isTouched() && bulletTeachTime > 0.5f)
				bulletTeach = true;
		}
		spriteBatch.end();

	}
	public void updatePause(){
		if(Gdx.input.justTouched()){
			Vector3 touchPoint = new Vector3(Gdx.input.getX(0),Gdx.input.getY(0),0.0f);
			camera.unproject(touchPoint);
			Rectangle main = new Rectangle(camera.position.x-camWidth/2+230,camera.position.y-camHeight/2+96,121,130);
			Rectangle restart = new Rectangle(camera.position.x-camWidth/2+610,camera.position.y-camHeight/2+96,121,130);
			Rectangle resume = new Rectangle(camera.position.x-camWidth/2+183,camera.position.y-camHeight/2+196,389,193);
			System.out.println("Touch Test"+(camera.position.x-camWidth/2)+","+(camera.position.y+camHeight/2));
			if(OverlapTester.pointInRectangle(main, touchPoint.x, touchPoint.y)){
				game.setScreen(new LevelScreen(game,part));
				if(part == 1) Asset.bg.stop();
				else if(part == 2) Asset.bg2.stop();
				Asset.playSound(Asset.choice);
			}
			if(OverlapTester.pointInRectangle(restart, touchPoint.x, touchPoint.y)){
				game.setScreen(new GameScreen(game,part,levelNumber+1));
			}
			if(OverlapTester.pointInRectangle(resume, touchPoint.x, touchPoint.y)){
				gameState = RUNNING;
			}
		}
		pause.setPosition(camera.position.x-camWidth/2, camera.position.y-camHeight/2);
		spriteBatch.begin();
			pause.draw(spriteBatch);
		spriteBatch.end();
	}
	public void updateLevelEnd(){
		
		
		if(Gdx.input.justTouched() && !changePart){
			if(Gdx.input.justTouched()){
				Vector3 touchPoint = new Vector3(Gdx.input.getX(0),Gdx.input.getY(0),0.0f);
				camera.unproject(touchPoint);
				Rectangle main = new Rectangle(camera.position.x-camWidth/2+379,camera.position.y-camHeight/2+107,49,54);
				Rectangle restart = new Rectangle(camera.position.x-camWidth/2+532,camera.position.y-camHeight/2+107,49,54);
				Rectangle next = new Rectangle(camera.position.x-camWidth/2+444,camera.position.y-camHeight/2+145,80,82);
				System.out.println("Touch Test"+(camera.position.x-camWidth/2)+","+(camera.position.y+camHeight/2));
				if(OverlapTester.pointInRectangle(main, touchPoint.x, touchPoint.y)){
					game.setScreen(new LevelScreen(game,part));
					Asset.victory.stop();
					if(part == 1) Asset.bg.stop();
					else if(part == 2) Asset.bg2.stop();
					Asset.playSound(Asset.choice);
				}
				if(OverlapTester.pointInRectangle(restart, touchPoint.x, touchPoint.y)){
					game.setScreen(new GameScreen(game,part,levelNumber+1));
					Asset.victory.stop();
				}
				if(OverlapTester.pointInRectangle(next, touchPoint.x, touchPoint.y)){
					if(levelValue[part-1][levelNumber+1][4] != 0){
						game.setScreen(new GameScreen(game,part,levelNumber+2));
					}
					else{
						changePart = true;
					}
					Asset.victory.stop();
				}
			}
		}

		
		levelEnd.setPosition(camera.position.x-camWidth/2, camera.position.y-camHeight/2);
		
		if(!clear){
			if(part == 1) Asset.bg.stop();
			else if(part == 2) Asset.bg2.stop();
			Asset.playSound(Asset.victory);
			int tmp = 5000 + eatStar*500 + (int)(Math.random()*200);
			score += tmp; 
			clear = true;
			if(eatStar >= (int)(levelValue[part-1][levelNumber][5]*0.9)) star = 3;
			else if(eatStar >= (int)(levelValue[part-1][levelNumber][5]*0.6)) star = 2;
			else if(eatStar >= (int)(levelValue[part-1][levelNumber][5]*0.2)) star = 1;
			else star = 0;
			tmpStar = 0;
		}
		spriteBatch.begin();
			levelEnd.draw(spriteBatch);
			Asset.score.setColor(Color.BLACK);
			Asset.score.draw(spriteBatch,score+"",camera.position.x-camWidth/2+480, camera.position.y-camHeight/2+245);
			if(tmpStar < star){
				if(starTime[tmpStar] < 1.0f){
					starTime[tmpStar] += Gdx.app.getGraphics().getDeltaTime();
					spriteBatch.draw(Asset.starAni.getKeyFrame(starTime[tmpStar], Animation.ANIMATION_NONLOOPING),camera.position.x-camWidth/2+320+(tmpStar*116), camera.position.y-camHeight/2+374, 96,96);
				}
				else{
					tmpStar++;
				}		
			}
			for(int i = 0 ; i < tmpStar ; i++){
				spriteBatch.draw(Asset.levelStar[0][0],camera.position.x-camWidth/2+320+(i*115), camera.position.y-camHeight/2+374, 96,96);
			}
		spriteBatch.end();
		
		if(changePart){
			if(part == 1){
				changePartTime += Gdx.graphics.getDeltaTime();
				if(changePartTime > 4.5f){
					game.setScreen(new ChoiceScreen(game));
					if(part == 1) Asset.bg.stop();
					else if(part == 2) Asset.bg2.stop();
					Asset.playSound(Asset.choice);
				}
				spriteBatch.begin();
					spriteBatch.draw(Asset.changePartAni.getKeyFrame(changePartTime, Animation.ANIMATION_NONLOOPING), camera.position.x-camWidth/2,camera.position.y-camHeight/2,960,640);
				spriteBatch.end();
			}
			else if(part == 2){
				game.setScreen(new ChoiceScreen(game));
				if(part == 1) Asset.bg.stop();
				else if(part == 2) Asset.bg2.stop();
				Asset.playSound(Asset.choice);
			}
		}
		//setLevelStar
			LevelSetting.levelUnlock((part-1)*5+levelNumber+2);
			if(LevelSetting.levelStar[(part-1)*5+levelNumber] < star+1)
				LevelSetting.setLevelStar((part-1)*5+levelNumber+1, star+1);
			LevelSetting.save();
		//
	}
	public void updateGameOver(){
		if(!lose){
			lose = true;
			Asset.playSound(Asset.lose);
		}
		if(Gdx.input.justTouched()){
			if(Gdx.input.justTouched()){
				Vector3 touchPoint = new Vector3(Gdx.input.getX(0),Gdx.input.getY(0),0.0f);
				camera.unproject(touchPoint);
				Rectangle main = new Rectangle(camera.position.x-camWidth/2+245,camera.position.y-camHeight/2+170,162,174);
				Rectangle restart = new Rectangle(camera.position.x-camWidth/2+543,camera.position.y-camHeight/2+170,162,174);
				System.out.println("Touch Test"+(camera.position.x-camWidth/2)+","+(camera.position.y+camHeight/2));
				if(OverlapTester.pointInRectangle(main, touchPoint.x, touchPoint.y)){
					game.setScreen(new LevelScreen(game,part));
					if(part == 1) Asset.bg.stop();
					else if(part == 2) Asset.bg2.stop();
					Asset.playSound(Asset.choice);
				}
				if(OverlapTester.pointInRectangle(restart, touchPoint.x, touchPoint.y)){
					game.setScreen(new GameScreen(game,part,levelNumber+1));
				}
			}
		}
		die.setPosition(camera.position.x-camWidth/2, camera.position.y-camHeight/2);
		spriteBatch.begin();
			die.draw(spriteBatch);
		spriteBatch.end();
	}
	public void turn(){
		shootTime += Gdx.graphics.getDeltaTime();
		if(shootTime > 0.5f){
			Asset.shootTime.stop();
			Asset.playSound(Asset.shootTime);
			shootTime = 0;
		}
		
		if(!fly){
			accelX = Gdx.input.getAccelerometerX();
			accelY = Gdx.input.getAccelerometerY();
			fly = true;
			tail = new float[12][2];
		}
		float tmpX = Gdx.input.getAccelerometerX() - accelX;
		float tmpY = Gdx.input.getAccelerometerY() - accelY;
		if(Gdx.app.getType() == Application.ApplicationType.Android){
			if((Math.abs(tmpX) <= 0.8 && Math.abs(tmpY) <= 0.8)){
				shoot((float)theta+turnTheta);
				return;
			}
			if(Math.abs(tmpX)>4.0 || Math.abs(tmpY)>4.0){
				if(Math.abs(tmpX)>4.0f){
					if(tmpX>0)tmpX = 4.0f;
					else tmpX = -4.0f;
				}
				else{
					if(tmpY>0)tmpY = 4.0f;
					else tmpY = -4.0f;
				}
			}
			if(Math.abs(tmpX) > Math.abs(tmpY)){
				if(theta > 120.0 && theta < 240.0){
					turnTheta += tmpX * 2.0f;
				}
				if(theta < 60.0 || theta >300.0){
					turnTheta += tmpX * (-2.0f);
				}
			}
			else{
				if(theta < 150.0 && theta > 30.0){
					turnTheta += tmpY *(-2.0f);
				}
				if(theta > 210.0f && theta < 330.0){
					turnTheta += tmpY * 2.0f;
				}
			}

			if(Math.abs(turnTheta) >40.0f)
			{
				if(turnTheta >= 0)
					turnTheta = 40.0f;
				else
					turnTheta = -40.0f;
			}
			
			shoot((float)theta+turnTheta);
		}	
	}
	
	public void shoot(float theta){	
			pullX = pullY = 0.0f;
			focusTaco = true;
			touchTaco = true;
			while(theta < 0.0f || theta>360.0){
				if(theta < 0.0f) theta+= 360.0f;
				if(theta > 360.0f) theta -=360.0f;
			}
			if((theta <= 90.0 && theta >= 0.0) || (theta < 360.0 && theta > 270.0)) {
				moveX = 1.0f;
				moveY = (float)Math.tan(theta*(Math.PI/180));

			}
			else{
				moveX = -1.0f;
				moveY = (float)-Math.tan(theta*(Math.PI/180));
			}
			
			float line  = (float)Math.sqrt(moveX*moveX + moveY*moveY);
			float num = line / speed;
			
			moveX = moveX/num;
			moveY = moveY/num;
			
			spriteTaco = new Sprite(Asset.player);
			spriteTaco.setScale((float)1.0,(float)1.0);
			spriteTaco.rotate((float)theta);
			taco.setLinearVelocity(moveX,moveY);
			System.out.println(theta);
	}
	
	public void shootBullet(){
		
		float theta = (float)(this.theta + turnTheta);
		
		if(bulletCount>0) bulletButton = new Rectangle(camera.position.x-camWidth/2+0,camera.position.y-camHeight/2+ 0, 170,170);
		
		if(Gdx.input.justTouched()){
			if((!touchTaco && bulletCount > 0) || (touchTaco && Gdx.input.isTouched(1) && bulletCount > 0) ||(fly && touchTaco && bulletCount > 0)){
				Vector3 touchPoint = new Vector3(0,0,0);
				Vector3 touchPoint2 = new Vector3(0,0,0);
				camera.unproject(touchPoint.set(Gdx.input.getX(0), Gdx.input.getY(0), 0));
				if((touchTaco && Gdx.input.isTouched(1)))
					camera.unproject(touchPoint.set(Gdx.input.getX(1), Gdx.input.getY(1), 0));
				if(OverlapTester.pointInRectangle(bulletButton, touchPoint.x, touchPoint.y) || OverlapTester.pointInRectangle(bulletButton, touchPoint2.x, touchPoint2.y)){
					Asset.shootBullet.stop();
					Asset.playSound(Asset.shootBullet);
					for(int i = 0 ; i < bullets.length ; i++){
						if(bullets[i] == null){
							bullets[i] = new Bullet(PIXELS_PER_METER * taco.getPosition().x,PIXELS_PER_METER * taco.getPosition().y);
							System.out.println(theta);
							while(theta < 0.0f || theta>360.0){
								if(theta < 0.0f) theta+= 360.0f;
								if(theta > 360.0f) theta -=360.0f;
							}
							float moveX;
							float moveY;
							if((theta <= 90.0 && theta >= 0.0) || (theta < 360.0 && theta > 270.0)) {
								moveX = 1.0f;
								moveY = (float)Math.tan(theta*(Math.PI/180));
							}
							else{
								moveX = -1.0f;
								moveY = (float)-Math.tan(theta*(Math.PI/180));
							}
							float line  = (float)Math.sqrt(moveX*moveX + moveY*moveY);
							float num = line / 50.0f;
							
							moveX = moveX/num;
							moveY = moveY/num;
							
							bullets[i].speedX = moveX;
							bullets[i].speedY = moveY;
							
							bulletCount--;
							break;
						}
					}
				}

			}
		}
		for(int i = 0 ; i < bullets.length ; i++){
			if(bullets[i]!= null){
				bullets[i].position.x =  bullets[i].position.x + bullets[i].speedX;
				bullets[i].position.y =  bullets[i].position.y + bullets[i].speedY;
				bullets[i].bounds.x = bullets[i].position.x - bullets[i].bounds.width/2;
				bullets[i].bounds.y = bullets[i].position.y - bullets[i].bounds.height/2;
				if(bullets[i].position.x > 0 && bullets[i].position.y > 0 && bullets[i].position.x < totalWidth && bullets[i].position.y < totalHeight)
				{
					if(bullets[i].theta == 1000.0f) bullets[i].theta = (float)theta;
					spriteBatch.begin();
						spriteBatch.draw(Asset.item[1][0],bullets[i].position.x-16,bullets[i].position.y-16,16,16,32,32,1,1,bullets[i].theta);
					spriteBatch.end();
				}
				else{
					bullets[i] = null;
				}
			}
		}

	}
	
	public void setCamPosition(float x ,float y){
		camX = x;
		camY = y;
		 
		
		if(y > totalHeight - 320){
			camY= totalHeight - 320;
		}
		if(y < 320){
			camY= 320;
		}
		if(x > totalWidth - 480){
			camX = totalWidth - 480;
		}
		if(x < 480){
			camX = 480;
		}	
		camera.position.set(camX,camY,0);
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		Asset.loadFont();
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
		if(gameState == RUNNING) gameState = PAUSE;
		if(part == 1) Asset.bg.pause();
		else if(part == 2) Asset.bg2.pause();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		if(gameState == RUNNING) gameState = PAUSE;
		if(part == 1) Asset.playSound(Asset.bg);
		else if(part == 2) Asset.playSound(Asset.bg2);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		Gdx.app.exit();
	}

	@Override
	public void beginContact(Contact contact) {
		
			Body bodyA = contact.getFixtureA().getBody();
			Body bodyB = contact.getFixtureB().getBody();
		
			Vector2 a = bodyA.getPosition();
			Vector2 b = bodyB.getPosition();

			if((bodyA.getUserData() instanceof Turtle || bodyB.getUserData() instanceof Turtle) &&
					(a == taco.getPosition() || b== taco.getPosition())){//Turtle contact Taco
				if(!rided){
					turnTheta = 0.0f;
					fly = false;
					rided = true;
					taco.setLinearVelocity(0.0f,0.0f);
					
					Body turtle;
					if(bodyA.getUserData() instanceof Turtle) turtle = bodyA;
					else turtle = bodyB;
					ridedTurtle = turtle;
					/*float turtleTheta = (float)(Math.atan2((taco.getPosition().y-turtle.getPosition().y)*PIXELS_PER_METER ,(taco.getPosition().x-turtle.getPosition().x)*PIXELS_PER_METER)/(Math.PI/180.0f));
					if(turtleTheta < 0) theta+=360.0f;
					
					float test1 =(float)(Math.atan2(50.0f ,-82.0f)/(Math.PI/180.0f)); //LEFT_UP
					float test2 =(float)(Math.atan2(-50.0f ,-82.0f)/(Math.PI/180.0f)); //LEFT_DOWN
					float test3 =(float)(Math.atan2(-50.0f ,82.0f)/(Math.PI/180.0f)); //RIGHT_DOWN
					float test4 =(float)(Math.atan2(50.0f ,82.0f)/(Math.PI/180.0f)); //RIGHT_UP
					if(test1 < 0)  test1+=360.0f;
					if(test2 < 0)  test2+=360.0f;
					if(test3 < 0)  test3+=360.0f;
					if(test4 < 0)  test4+=360.0f;
					
					if(turtleTheta > test1 && turtleTheta < test2){
						theta = 180.0f;
						state = FACE_LEFT;
					}
					else if(turtleTheta > test4 && turtleTheta < test1){
						theta = 90.0f;
						state = FACE_UP;
					}
					else if(turtleTheta > test2 && turtleTheta < test3){
						theta = 270.0f;
						state = FACE_DOWN;
					}
					else if(turtleTheta > test3 || turtleTheta < test4){
						theta = 0.0f;
						state = FACE_RIGHT;
					}*/
					
					if(Math.abs(taco.getPosition().x-turtle.getPosition().x)/2>Math.abs(taco.getPosition().y-turtle.getPosition().y)){
						if(taco.getPosition().x-turtle.getPosition().x > 0){
							theta = 0.0f;
							state = FACE_RIGHT;
						}
						else{
							theta = 180.0f;
							state = FACE_LEFT;
						}
					}
					else{
						if(taco.getPosition().y-turtle.getPosition().y > 0){
							theta = 90.0f;
							state = FACE_UP;
						}
						else{
							theta = 270.0f;
							state = FACE_DOWN;
						}
					}
				
					spriteTaco = new Sprite(Asset.player);
					spriteTaco.setScale((float)1.0,(float)1.0);
					spriteTaco.rotate((float)theta);
				}
				
			}
			else if(a == taco.getPosition() || b == taco.getPosition()){//Taco contact Wall
				
				if(!gameStart) Asset.playSound(Asset.start);
				
				gameStart = true;
				turnTheta = 0.0f;
				fly = false;
				taco.setLinearVelocity(0.0f,0.0f);
				
				count++;
	
				System.out.println("Body a position x:"+a.x*PIXELS_PER_METER+",y:"+a.y*PIXELS_PER_METER);
				System.out.println("Body b position x:"+b.x*PIXELS_PER_METER+",y:"+b.y*PIXELS_PER_METER);
				System.out.println("x:"+(b.x-a.x)*PIXELS_PER_METER+",y:"+(b.y-a.y)*PIXELS_PER_METER);
				
				float rangeX = (b.x - a.x)*PIXELS_PER_METER;
				float rangeY = (b.y - a.y)*PIXELS_PER_METER;
				
				if(rangeX > 30.0f && rangeX < 38.0f){
					if(count != 1){
						if(Math.abs(rangeY) > 16.0f) return;
					}
					theta = 0.0f;
					state = FACE_RIGHT;
				}
				if(rangeX >-38.0f && rangeX < -30.0f){
					if(count != 1){
						if(Math.abs(rangeY) > 16.0f) return;
					}
					theta = 180.0f;
					state = FACE_LEFT;
				}
				if(rangeY > 30.0f && rangeY < 38.0f){
					if(count != 1){
						if(Math.abs(rangeX) > 16.0f) return;
					}
					theta = 90.0f;
					state = FACE_UP;
				}
				if(rangeY >-38.0f && rangeY < -30.0f){
					if(count != 1){
						if(Math.abs(rangeX) > 16.0f) return;
					}
					theta = 270.0f;
					state = FACE_DOWN;
				}
				spriteTaco = new Sprite(Asset.player);
				spriteTaco.setScale((float)1.0,(float)1.0);
				spriteTaco.rotate((float)theta);
			}
	}

	@Override
	public void endContact(Contact contact) {
		count = 0;
		jellyCount = 0;
		
		Body bodyA = contact.getFixtureA().getBody();
		Body bodyB = contact.getFixtureB().getBody();
	
		Vector2 a = bodyA.getPosition();
		Vector2 b = bodyB.getPosition();
		

		if((bodyA.getUserData() instanceof Turtle || bodyB.getUserData() instanceof Turtle) &&
				(a == taco.getPosition() || b== taco.getPosition())){//Turtle contact Taco
			if(rided){
				rided = false;
			}
			
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
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
		if(pointer ==0 && gameState == RUNNING){
			Vector3 touchPoint = new Vector3();
			camera.unproject(touchPoint.set(x,y,0));
			Rectangle translation = new Rectangle(camera.position.x-camWidth/2+863,camera.position.y-camHeight/2+90,64,64);
			Rectangle test = new Rectangle(PIXELS_PER_METER * taco.getPosition().x- spriteTaco.getWidth() / 2 -75,
					   PIXELS_PER_METER * taco.getPosition().y- spriteTaco.getHeight() / 2 -75,
					   spriteTaco.getWidth()+150,
					   spriteTaco.getHeight()+150);
			
			if(OverlapTester.pointInRectangle(translation, touchPoint.x, touchPoint.y)){
				Asset.playSound(Asset.button);
				if(camTranslation) {
					camTranslation = false;
					starX = x;
					starY = y;
					focusTaco = true;
					touchTaco = true;
				}
				else camTranslation = true;
				return false;
			}
			if(camTranslation){
				starX = x;
				starY = y;
				touchTaco = false;
				focusTaco = false;
				return false;
			}
			if(bulletCount > 0 && bulletButton != null)
				if(OverlapTester.pointInRectangle(bulletButton, touchPoint.x, touchPoint.y)){
					starX = x;
					starY = y;
					touchTaco = false;
					focusTaco = false;
					return false;
				}
			if(OverlapTester.pointInRectangle(test,touchPoint.x,touchPoint.y)){
				System.out.println("TouchTaco!");
				starX = x;
				starY = y;
				focusTaco = true;
				touchTaco = true;
			}
			else{
				starX = x;
				starY = y;
				touchTaco = false;
				focusTaco = false;
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		pull = false;
		if(camTranslation) return false;
		if((taco.getLinearVelocity().x != 0.0f || taco.getLinearVelocity().y != 0.0f) && !rided) return false;
		if(pointer ==0&& gameState == RUNNING){
			pullX = pullY = 0.0f;
			if(touchTaco){

				endX = x;
				endY = y;
				
				Vector3 start = new Vector3();
				Vector3 end = new Vector3();
				
				camera.unproject(start.set(starX,starY,0.0f));
				camera.unproject(end.set(endX,endY,0.0f));
				
				moveX = endX - starX;
				moveY = endY - starY;
				
				if(Math.abs(taco.getLinearVelocity().y) == 0){
					if(Math.abs(moveX) > 32.0 || Math.abs(moveY) > 32.0){
						if(bubble) bubble = false;
						Asset.playSound(Asset.shoot);
						shoot((float)theta);
					}
						
					else{
						switch(state){
							case FACE_UP: theta = 90.0f;
										  break;
							case FACE_DOWN: theta = 270.0f;
										    break;
							case FACE_LEFT: theta = 180.0f;
							                break;
							case FACE_RIGHT: theta = 0.0f;
							                 break;
						}
						spriteTaco = new Sprite(Asset.player);
						spriteTaco.setScale((float)1.0,(float)1.0);
						spriteTaco.rotate((float)theta);
					}
				}
				
				
					
			}
		}
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		if((taco.getLinearVelocity().x != 0.0f || taco.getLinearVelocity().y != 0.0f) && !rided) return false;
		if(pointer == 0&& gameState == RUNNING && !camTranslation){
			Vector3 touchPoint = new Vector3();
			camera.unproject(touchPoint.set(x,y,0));
			if(touchTaco){
				if(!pull){
					pull = true;
					Asset.playSound(Asset.pull);
				}
				theta = 0.0;
				
				theta = Math.atan2(touchPoint.y-PIXELS_PER_METER * taco.getPosition().y,touchPoint.x-PIXELS_PER_METER * taco.getPosition().x)/(Math.PI/180.0);
				while(theta < 0)
					theta += 360.0f;
				switch(state){
					case FACE_UP: if(theta <25.0f || theta > 155.0f)  theta = theta > 270.0f ? 25.0f : theta>90.0f? 155.0f : 25.0f;
								  break;
					case FACE_DOWN:if(theta >335.0f || theta < 205.0f) theta = theta < 90.0f ? 335.0f : theta >270.0f ? 335.0f : 205.0f;  
								   break;
					case FACE_LEFT:if(theta > 245.0f || theta <115.0f) theta = theta < 180.0f ? 115.0f : 245.0f;
								   break; 
					case FACE_RIGHT:if(theta < 295.0f && theta > 65.0f)theta = theta < 180.0f ? 65.0f : 295.0f; 
								    break;
				}
				if(!bubble){
					switch(state){
						case FACE_UP: spriteTaco = new Sprite(Asset.taco[(int)((theta-25)/15)][0]);
									  spriteTaco.rotate(90.0f);
									  pullX = 0.0f;
									  pullY = 32.0f;
						  			break;
						case FACE_DOWN: spriteTaco = new Sprite(Asset.taco[(int)((theta-205)/15)][0]);
										spriteTaco.rotate(270.0f);
										pullX = 0.0f;
										pullY = -32.0f;
									   break;
						case FACE_LEFT: spriteTaco = new Sprite(Asset.taco[(int)((theta-115)/15)][0]);
										spriteTaco.rotate(180.0f);
										pullX = -32.0f;
										pullY = 0.0f;
									   break; 
						case FACE_RIGHT: if(theta>90)spriteTaco = new Sprite(Asset.taco[(int)((theta-295)/15)][0]);
										 else spriteTaco = new Sprite(Asset.taco[(int)((theta+65)/15)][0]);
										spriteTaco.rotate(0.0f);
										pullX = 32.0f;
										pullY = 0.0f;
								    break;
					}
				}
				else{
					spriteTaco = new Sprite(Asset.player);
					spriteTaco.setScale((float)1.5,(float)1.0);
					spriteTaco.rotate((float)theta);
				}

			}
		}
		if((!touchTaco && ((taco.getLinearVelocity().x ==0.0 && taco.getLinearVelocity().y ==0.0) || rided) && gameState == RUNNING) && gameStart && camTranslation){//Move Camera
			Vector3 touchPoint = new Vector3(x,y,0);
			Vector3 touchDown = new Vector3(starX,starY,0);
			camera.unproject(touchPoint);
			camera.unproject(touchDown);
			float camX = (touchPoint.x-touchDown.x)*(-1);
			float camY = (touchPoint.y-touchDown.y)*(-1);
			if(camX < -60.0f) camX = -60.0f;
			if(camX > 60.0f)  camX = 60.0f;
			if(camY < -60.0f) camY = -60.0f;
			if(camY > 60.0f)  camY = 60.0f;
			
			setCamPosition(camX+camera.position.x,camY+camera.position.y);
			
			starX = x;
			starY = y;
			
		}
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
