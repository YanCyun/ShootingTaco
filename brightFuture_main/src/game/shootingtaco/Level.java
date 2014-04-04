package game.shootingtaco;

import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;

import aurelienribon.bodyeditor.BodyEditorLoader;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import game.shootingtaco.*;

public class Level {
	
	
	
	public int width,height;
	public byte[] walls;
	public byte[][] green;
	public float tacoX,tacoY;
	public float jellySpeed;
	public float stateTime;
	
	public final ArrayList<Thorn> thorns;
	public final ArrayList<Goal> goals;
	public final ArrayList<EnergyFish> fishs;
	public final ArrayList<HealthFish> healths;
	public final ArrayList<InvisibleWall> invisibleWalls;
	public final ArrayList<Jellyfish> jellyfishs;
	public final ArrayList<Wall> touchWalls;
	public final ArrayList<Body> turtles;
	public final ArrayList<Shell> shells;
	public final ArrayList<Laps> lapes;
	public final ArrayList<Coral> corals;
	public final ArrayList<BreakWall> breakWalls;
	public final ArrayList<Swirl> swirls;
	public final ArrayList<Teach> teachs;
	public final ArrayList<Arrow> arrows;

	
	private Body body;
	private Sprite[][] grass;
	private Sprite spriteGoal;
	//private BodyEditorLoader loader;
	
	public Level(GameScreen gameScreen,int x0 , int y0, int w,int h){
		
		
		width = w;
		height = h;
		
		jellySpeed = 1.0f;
		
		walls = new byte[w*h];
		thorns = new ArrayList<Thorn>();
		goals = new ArrayList<Goal>();
		fishs = new ArrayList<EnergyFish>();
		jellyfishs = new ArrayList<Jellyfish>();
		turtles = new ArrayList<Body>();
		healths = new ArrayList<HealthFish>();
		invisibleWalls = new ArrayList<InvisibleWall>();
		touchWalls = new ArrayList<Wall>();
		shells = new ArrayList<Shell>();
		lapes = new ArrayList<Laps>();
		corals = new ArrayList<Coral>();
		breakWalls = new ArrayList<BreakWall>();
		swirls = new ArrayList<Swirl>();
		teachs = new ArrayList<Teach>();
		arrows = new ArrayList<Arrow>();
		
		green = new byte[w][h];
		grass = new Sprite[w][h];
		
		BodyDef bodydef = new BodyDef();
		FixtureDef fix = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		for(int y = 0 ; y < h ; y++){
			for(int x = 0 ; x < w ; x++){
				float rnd = (float)Math.random()*10;
				bodydef.type = BodyDef.BodyType.StaticBody;
				bodydef.allowSleep = true;
				bodydef.position.set((x*32+16)/gameScreen.PIXELS_PER_METER,((height-y-1)*32+16)/gameScreen.PIXELS_PER_METER);
				body = gameScreen.world.createBody(bodydef);
				fix.friction = 0.0f;
				int col = (Asset.level.getPixel(x0+x, y0+y) & 0xffffff00)>>>8;
				byte wall = 0;
				if(col == 0x000000){
					wall = 1;
				}
				if(col == 0xff1111){
					wall = 10;
				}
				if(col == 0xff2222){
					wall = 11;
				}
				if(col == 0xff3333){
					wall = 12;
				}
				if(col == 0xff4444){
					wall = 13;
				}
				if(col == 0xff0000){
					Wall newWall = new Wall((x*32)+16,(height-y-1)*32+16);
					touchWalls.add(newWall);
					wall = 2;
					shape.setAsBox(32/(gameScreen.PIXELS_PER_METER*2), 32/(gameScreen.PIXELS_PER_METER*2));
					fix.shape = shape;
					body.createFixture(fix);
					if(rnd > 9.5f){
						green[x][y] = (byte)(Math.random()*5+1);
						grass[x][y] = new Sprite(Asset.grass[0][green[x][y]]);
						grass[x][y].rotate(270);
					}
				}
				if(col == 0x00ff00){
					Wall newWall = new Wall((x*32)+16,(height-y-1)*32+16);
					touchWalls.add(newWall);
					wall = 3;
					shape.setAsBox(32/(gameScreen.PIXELS_PER_METER*2), 32/(gameScreen.PIXELS_PER_METER*2));
					fix.shape = shape;
					body.createFixture(fix);
					if(rnd > 9.5f){
						green[x][y] = (byte)(Math.random()*5+1);
						grass[x][y] = new Sprite(Asset.grass[0][green[x][y]]);
						grass[x][y].rotate(0);
					}
				}
				if(col == 0xffff00){
					Wall newWall = new Wall((x*32)+16,(height-y-1)*32+16);
					touchWalls.add(newWall);
					wall = 4;
					shape.setAsBox(32/(gameScreen.PIXELS_PER_METER*2), 32/(gameScreen.PIXELS_PER_METER*2));
					fix.shape = shape;
					body.createFixture(fix);
					if(rnd > 9.5f){
						green[x][y] = (byte)(Math.random()*5+1);
						grass[x][y] = new Sprite(Asset.grass[0][green[x][y]]);
						grass[x][y].rotate(90);
					}
				}
				if(col == 0xff00ff){
					Wall newWall = new Wall((x*32)+16,(height-y-1)*32+16);
					touchWalls.add(newWall);
					wall = 5;
					shape.setAsBox(32/(gameScreen.PIXELS_PER_METER*2), 32/(gameScreen.PIXELS_PER_METER*2));
					fix.shape = shape;
					body.createFixture(fix);
					if(rnd > 9.5f){
						green[x][y] = (byte)(Math.random()*5+1);
						grass[x][y] = new Sprite(Asset.grass[0][green[x][y]]);
						grass[x][y].rotate(180);
					}
				}
				if(col == 0x0077ff){
					Wall newWall = new Wall((x*32)+16,(height-y-1)*32+16);
					touchWalls.add(newWall);
					wall = 6;
					shape.setAsBox(32/(gameScreen.PIXELS_PER_METER*2), 32/(gameScreen.PIXELS_PER_METER*2));
					fix.shape = shape;
					body.createFixture(fix);
					if(rnd > 9.5f){
						green[x][y] = (byte)(Math.random()*5+1);
						grass[x][y] = new Sprite(Asset.grass[0][green[x][y]]);
						grass[x][y].rotate(90);
					}
				}
				if(col == 0xff8800){
					Wall newWall = new Wall((x*32)+16,(height-y-1)*32+16);
					touchWalls.add(newWall);
					wall = 7;
					shape.setAsBox(32/(gameScreen.PIXELS_PER_METER*2), 32/(gameScreen.PIXELS_PER_METER*2));
					fix.shape = shape;
					body.createFixture(fix);
					if(rnd > 9.5f){
						green[x][y] = (byte)(Math.random()*5+1);
						grass[x][y] = new Sprite(Asset.grass[0][green[x][y]]);
						grass[x][y].rotate(270);
					}
				}
				if(col == 0x7700ff){
					Wall newWall = new Wall((x*32)+16,(height-y-1)*32+16);
					touchWalls.add(newWall);
					wall = 8;
					shape.setAsBox(32/(gameScreen.PIXELS_PER_METER*2), 32/(gameScreen.PIXELS_PER_METER*2));
					fix.shape = shape;
					body.createFixture(fix);
					if(rnd > 9.5f){
						green[x][y] = (byte)(Math.random()*5+1);
						grass[x][y] = new Sprite(Asset.grass[0][green[x][y]]);
						grass[x][y].rotate(0);
					}
				}
				if(col == 0x77ff00){
					Wall newWall = new Wall((x*32)+16,(height-y-1)*32+16);
					touchWalls.add(newWall);
					wall = 9;
					shape.setAsBox(32/(gameScreen.PIXELS_PER_METER*2), 32/(gameScreen.PIXELS_PER_METER*2));
					fix.shape = shape;
					body.createFixture(fix);
					if(rnd > 9.5f){
						green[x][y] = (byte)(Math.random()*5+1);
						grass[x][y] = new Sprite(Asset.grass[0][green[x][y]]);
						grass[x][y].rotate(180);
					}
				}
				if(col == 0x11ffff){//Laps
					Laps laps = new Laps((x*32)+64,(height-y-1)*32+32,Laps.FACE_RIGHT);
					lapes.add(laps);
				}
				if(col == 0x22ffff){//Laps
					Laps laps = new Laps((x*32)+64,(height-y-1)*32+32,Laps.FACE_UP);
					lapes.add(laps);
				}
				if(col == 0x33ffff){//Laps
					Laps laps = new Laps((x*32)+64,(height-y-1)*32+32,Laps.FACE_LEFT);
					lapes.add(laps);
				}
				if(col == 0x44ffff){//Laps
					Laps laps = new Laps((x*32)+64,(height-y-1)*32+32,Laps.FACE_DOWN);
					lapes.add(laps);
				}
				if(col == 0xff88ff){ // Thorn
					wall = 14;
					Thorn thorn = new Thorn((x*32)+16,(height-y-1)*32+16);
					thorns.add(thorn);
				}
				if(col == 0x88ff88){
					Shell shell = new Shell((x*32)+16,(height-y-1)*32+16);
					shells.add(shell);
				}
				if(col == 0xffff88){ // EnergyFish
					EnergyFish fish = new EnergyFish((x*32)+32,(height-y-1)*32+32);
					fishs.add(fish);
				}
				if(col == 0x8888ff){//HealthFish
					HealthFish health = new HealthFish((x*32)+32,(height-y-1)*32+32);
					healths.add(health);
				}
				if(col == 0x555555){ // Goal
					Goal goal = new Goal((x*32)+128,(height-y-1)*32+128,false);
					goals.add(goal);
				}
				if(col == 0x666666){ // BigGoal
					Goal goal = new Goal((x*32)+800,(height-y-1)*32+800,true);
					goals.add(goal);
				}
				if(col == 0x0000ff){ //Taco
					tacoX = (x*32+32)/gameScreen.PIXELS_PER_METER;
					tacoY = ((height-y-1)*32+32)/gameScreen.PIXELS_PER_METER;
				}
				if(col == 0x88ffff){  //Jellyfish
					Jellyfish jelly = new Jellyfish((x*32)+32,(height-y-1)*32+32);
					jellyfishs.add(jelly);
				}
				if(col == 0xff8888){//Turtle
					bodydef.type = BodyDef.BodyType.DynamicBody;
					bodydef.position.set((x*32+64)/gameScreen.PIXELS_PER_METER,((height-y-1)*32+32)/gameScreen.PIXELS_PER_METER);
					Body turtle = gameScreen.world.createBody(bodydef);
					shape.setAsBox(128/(gameScreen.PIXELS_PER_METER*2), 64/(gameScreen.PIXELS_PER_METER*2));
					fix.shape = shape;
					fix.density = 100.0f;
					turtle.createFixture(fix);
					turtle.setUserData(new Turtle());
					turtle.setFixedRotation(true);
					turtles.add(turtle);				
				}
				if(col == 0x888888){//InvisibleWall
					InvisibleWall inWall = new InvisibleWall((x*32)+16,(height-y-1)*32+16);
					invisibleWalls.add(inWall);	
				}
				if(col == 0xffff11 ){//Coral
					Coral coral = new Coral((x*32)+48,(height-y-1)*32+32,Coral.FACE_RIGHT);
					corals.add(coral);
				}
				if(col == 0xffff22 ){//Coral
					Coral coral = new Coral((x*32)+48,(height-y-1)*32+32,Coral.FACE_UP);
					corals.add(coral);
				}
				if(col == 0xffff33 ){//Coral
					Coral coral = new Coral((x*32)+48,(height-y-1)*32+32,Coral.FACE_LEFT);
					corals.add(coral);
				}
				if(col == 0xffff44 ){//Coral
					Coral coral = new Coral((x*32)+48,(height-y-1)*32+32,Coral.FACE_DOWN);
					corals.add(coral);
				}
				if(col == 0x5555ff ){//Coral
					BreakWall breakWall = new BreakWall((x*32)+16,(height-y-1)*32+16);
					breakWalls.add(breakWall);
				}
				if(col == 0xff11ff){//Swirl
					Swirl swirl = new Swirl((x*32)+64,(height-y-1)*32+64,Swirl.LEFT);
					swirls.add(swirl);
				}
				if(col == 0xff22ff){//Swirl
					Swirl swirl = new Swirl((x*32)+64,(height-y-1)*32+64,Swirl.RIGHT);
					swirls.add(swirl);
				}
				if(col == 0xaaaaaa){//Arrow
					Teach teach = new Teach((x*32)+128,(height-y-1)*32+128,Teach.PULL);
					teachs.add(teach);
				}
				if(col == 0xbbbbbb){//Arrow
					Teach teach = new Teach((x*32)+128,(height-y-1)*32+128,Teach.GYRO);
					teachs.add(teach);
				}
				if(col == 0xaaaa11){//Arrow
					Arrow arrow = new Arrow((x*32)+32,(height-y-1)*32+32,Arrow.LINE,0.0f);
					arrows.add(arrow);
				}
				if(col == 0xaaaa22){//Arrow
					Arrow arrow = new Arrow((x*32)+32,(height-y-1)*32+32,Arrow.LINE,90.0f);
					arrows.add(arrow);
				}
				if(col == 0xaaaa33){//Arrow
					Arrow arrow = new Arrow((x*32)+32,(height-y-1)*32+32,Arrow.LINE,180.0f);
					arrows.add(arrow);
				}
				if(col == 0xaaaa44){//Arrow
					Arrow arrow = new Arrow((x*32)+32,(height-y-1)*32+32,Arrow.LINE,270.0f);
					arrows.add(arrow);
				}
				if(col == 0xaaaa55){//Arrow
					Arrow arrow = new Arrow((x*32)+32,(height-y-1)*32+32,Arrow.LINE,45.0f);
					arrows.add(arrow);
				}
				if(col == 0xaaaa66){//Arrow
					Arrow arrow = new Arrow((x*32)+32,(height-y-1)*32+32,Arrow.LINE,135.0f);
					arrows.add(arrow);
				}
				if(col == 0xaaaa77){//Arrow
					Arrow arrow = new Arrow((x*32)+32,(height-y-1)*32+32,Arrow.LINE,225.0f);
					arrows.add(arrow);
				}
				if(col == 0xaaaa88){//Arrow
					Arrow arrow = new Arrow((x*32)+32,(height-y-1)*32+32,Arrow.LINE,315.0f);
					arrows.add(arrow);
				}
				if(col == 0xaaaa99){//Arrow_Curve
					Arrow arrow = new Arrow((x*32)+64,(height-y-1)*32+32,Arrow.CURVE,125.0f);
					arrows.add(arrow);
				}
				walls[x+y*w] = wall;
			}
		}
		
		for(int i = 0 ; i < turtles.size() ; i++){
			Body turtle = turtles.get(i);
			turtle.setLinearVelocity(3.0f, 0.0f);
		}
		
	}
	
	Matrix4 matrix = new Matrix4();
	
	public void render(GameScreen screen,OrthographicCamera camera){
		
		
		
		screen.spriteBatch.begin();
			
			for(int y = 0 ; y < height ;y++){
				for(int x = 0 ; x < width ; x++){
					int ximg = 0;
					int yimg = 0;
					
					byte wall = walls[x + y*width];
					if(wall == 0){
						continue;
					}
					if(wall == 1){
						ximg = 1;
						yimg = 1;
						
					}
					if(wall == 10){
						ximg = 5;
						yimg = 5;
						
					}
					if(wall == 11){
						ximg = 5;
						yimg = 3;
						
					}
					if(wall == 12){
						ximg = 5;
						yimg = 9;
						
					}
					if(wall == 13){
						ximg = 5;
						yimg = 7;
						
					}
					if(wall == 2){
						ximg = 1;
						yimg = 3;
						if(green[x][y] != 0){
							grass[x][y].setPosition(x*32,(height-y-1)*32-16);
						}
					}
					if(wall == 3){
						ximg = 1;
						yimg = 5;
						if(green[x][y] != 0){
							grass[x][y].setPosition(x*32+16,(height-y-1)*32);
						}
					}
					if(wall == 4){
						ximg = 1;
						yimg = 7;
						if(green[x][y] != 0){
							grass[x][y].setPosition(x*32,(height-y-1)*32+16);
						}
					}
					if(wall == 5){
						ximg = 1;
						yimg = 9;
						if(green[x][y] != 0){
							grass[x][y].setPosition(x*32-16,(height-y-1)*32);
						}
					}
					if(wall == 6){
						ximg = 3;
						yimg = 7;
						if(green[x][y] != 0){
							grass[x][y].setPosition(x*32,(height-y-1)*32+16);
						}
					}
					if(wall == 7){
						ximg = 3;
						yimg = 3;
						if(green[x][y] != 0){
							grass[x][y].setPosition(x*32,(height-y-1)*32-16);
						}
					}
					if(wall == 8){
						ximg = 3;
						yimg = 5;
						if(green[x][y] != 0){
							grass[x][y].setPosition(x*32+16,(height-y-1)*32);
						}
					}
					if(wall == 9){
						ximg = 3;
						yimg = 9;
						if(green[x][y] != 0){
							grass[x][y].setPosition(x*32-16,(height-y-1)*32);
						}
					}
					if(wall == 14){
						ximg = 0;
						yimg = 0;
					}
					if(wall <= 13)
						screen.spriteBatch.draw(Asset.wall[ximg][yimg],x*32,(height-y-1)*32,32,32);
					else
						screen.spriteBatch.draw(Asset.item[ximg][yimg],x*32,(height-y-1)*32,32,32);
					
					if(wall > 1 && wall < 10 &&green[x][y] !=0){
						grass[x][y].draw(screen.spriteBatch);
					}
				}
			}
			stateTime += Gdx.app.getGraphics().getDeltaTime();
			//System.out.println("StateTime:"+stateTime);
			//render EnergyFish
			for(int i = 0 ; i < fishs.size();i++){
				EnergyFish fish = fishs.get(i);
				fish.position.x = fish.position.x+fish.speed;
				fish.bounds.x = fish.position.x-fish.bounds.width/2;
				if(fish.eated){
					fish.stateTime += Gdx.app.getGraphics().getDeltaTime();
					fish.eatAni++;
					screen.spriteBatch.draw(Asset.partcialAni.getKeyFrame(fish.stateTime, Animation.ANIMATION_NONLOOPING),
							fish.position.x-64,fish.position.y-64,128,128);
					if(fish.eatAni == 3) fishs.remove(i);
				}
				else{
					if(fish.speed < 0 ){
						screen.spriteBatch.draw(Asset.energyFishL.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING),
												fish.position.x-32,fish.position.y-32,64,64);
					}
					else{
						screen.spriteBatch.draw(Asset.energyFishR.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING),
								fish.position.x-32,fish.position.y-32,64,64);
					}
				}
			}
			//reder goal
			if(goals.get(0).big){
				spriteGoal = new Sprite(Asset.bigGoal);
				spriteGoal.setPosition(goals.get(0).position.x-800, goals.get(0).position.y-800);

			}
			else {
				spriteGoal = new Sprite(Asset.goal);
				spriteGoal.setPosition(goals.get(0).position.x-128, goals.get(0).position.y-128);	
			}
			spriteGoal.rotate((stateTime*100%360)*(-1));
			spriteGoal.draw(screen.spriteBatch);
			//render Swirl
			for(int i = 0 ; i < swirls.size();i++){
				Swirl swirl = swirls.get(i);
				if(swirl.direction == Swirl.LEFT){
					screen.spriteBatch.draw(Asset.swirl, swirl.position.x-64, swirl.position.y-64, 64, 64, 128, 128, 1, 1,(stateTime*300));
				}
				else{
					screen.spriteBatch.draw(Asset.swirl2, swirl.position.x-64, swirl.position.y-64, 64, 64, 128, 128, 1, 1,(stateTime*300)*(-1));
				}
			}
			
			//render Shell
			for(int i = 0 ; i < shells.size() ; i++){
				Shell shell = shells.get(i);
				if(shell.eated){
					shell.stateTime += Gdx.graphics.getDeltaTime();
					shell.eatAni++;
					screen.spriteBatch.draw(Asset.partcialAni.getKeyFrame(shell.stateTime, Animation.ANIMATION_NONLOOPING),
							shell.position.x-64,shell.position.y-64,128,128);
					if(shell.eatAni == 3) shells.remove(i);
				}
				else{
					screen.spriteBatch.draw(Asset.shell, shell.position.x-16, shell.position.y-16,96,87);
				}
			}
			//render Laps
			for(int i = 0 ; i < lapes.size();i++){
				Laps laps = lapes.get(i);
				if(laps.touch){
					laps.stateTime +=  Gdx.graphics.getDeltaTime();
					if(laps.state == Laps.FACE_UP) screen.spriteBatch.draw(Asset.lapsAni.getKeyFrame(laps.stateTime,Animation.ANIMATION_NONLOOPING ),
													laps.position.x-64, laps.position.y-32,128,64);
					if(laps.state == Laps.FACE_DOWN) screen.spriteBatch.draw(Asset.lapsAni.getKeyFrame(laps.stateTime,Animation.ANIMATION_NONLOOPING ),
													laps.position.x-64, laps.position.y-32,64,32,128,64,1,1,180.0f);
					if(laps.state == Laps.FACE_LEFT) screen.spriteBatch.draw(Asset.lapsAni.getKeyFrame(laps.stateTime,Animation.ANIMATION_NONLOOPING ),
							laps.position.x-64, laps.position.y-32,64,32,128,64,1,1,90.0f);
					if(laps.state == Laps.FACE_RIGHT) screen.spriteBatch.draw(Asset.lapsAni.getKeyFrame(laps.stateTime,Animation.ANIMATION_NONLOOPING ),
							laps.position.x-64, laps.position.y-32,64,32,128,64,1,1,270.0f);
					laps.animation++;
					if(laps.animation == 2) {
						laps.touch = false;
						laps.animation = 0;
						laps.stateTime = 0;
					}
				}
				else{
					if(laps.state == Laps.FACE_UP) screen.spriteBatch.draw(Asset.laps[1][0], laps.position.x-64, laps.position.y-32,128,64);
					if(laps.state == Laps.FACE_DOWN) screen.spriteBatch.draw(Asset.laps[1][0], laps.position.x-64, laps.position.y-32,64,32,128,64,1,1,180.0f);
					if(laps.state == Laps.FACE_LEFT) screen.spriteBatch.draw(Asset.laps[1][0], laps.position.x-64, laps.position.y-32,64,32,128,64,1,1,90.0f);
					if(laps.state == Laps.FACE_RIGHT) screen.spriteBatch.draw(Asset.laps[1][0], laps.position.x-64, laps.position.y-32,64,32,128,64,1,1,270.0f);
				}
			}
			//render Coral
			for(int i = 0 ; i < corals.size();i++){
				Coral coral = corals.get(i);
				if(coral.touch){
					coral.stateTime += Gdx.app.getGraphics().getDeltaTime();
					coral.touchAni++;
					screen.spriteBatch.draw(Asset.partcialAni.getKeyFrame(coral.stateTime, Animation.ANIMATION_NONLOOPING),
							coral.position.x-64,coral.position.y-64,128,128);
					if(coral.touchAni == 3){
						corals.remove(i);
					}
				}
				else{
					if(coral.state == Coral.FACE_UP) screen.spriteBatch.draw(Asset.coral, coral.position.x-48, coral.position.y-32,96,64);
					if(coral.state == Coral.FACE_DOWN) screen.spriteBatch.draw(Asset.coral, coral.position.x-48, coral.position.y-32,48,32,96,64,1,1,180.0f);
					if(coral.state == Coral.FACE_LEFT) screen.spriteBatch.draw(Asset.coral, coral.position.x-48, coral.position.y-32,48,32,96,64,1,1,90.0f);
					if(coral.state == Coral.FACE_RIGHT) screen.spriteBatch.draw(Asset.coral, coral.position.x-48, coral.position.y-32,48,32,96,64,1,1,270.0f);
				}
			}
			//render HealthFish
			for(int i = 0 ; i < healths.size();i++){
				HealthFish fish = healths.get(i);
				fish.position.x = fish.position.x+fish.speed;
				fish.bounds.x = fish.position.x-fish.bounds.width/2;
				if(fish.eated){
					fish.stateTime += Gdx.app.getGraphics().getDeltaTime();
					fish.eatAni++;
					screen.spriteBatch.draw(Asset.partcialAni.getKeyFrame(fish.stateTime, Animation.ANIMATION_NONLOOPING),
							fish.position.x-64,fish.position.y-64,128,128);
					if(fish.eatAni == 3) healths.remove(i);
				}
				else{
					if(fish.speed < 0 ){
						screen.spriteBatch.draw(Asset.healthFishL.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING),
												fish.position.x-32,fish.position.y-32,64,64);
					}
					else{
						screen.spriteBatch.draw(Asset.healthFishR.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING),
								fish.position.x-32,fish.position.y-32,64,64);
					}
				}
			}
			//render JellyFish
			for(int i = 0 ; i < jellyfishs.size();i++){
				Jellyfish jelly = jellyfishs.get(i);
				if(jelly.die){
					jelly.stateTime += Gdx.app.getGraphics().getDeltaTime();
					jelly.dieAni++;
					screen.spriteBatch.draw(Asset.dieAni.getKeyFrame(jelly.stateTime, Animation.ANIMATION_NONLOOPING),
							jelly.position.x-64,jelly.position.y-32,128,64);
					if(jelly.dieAni == 3) jellyfishs.remove(i);
				}
				else{
					jelly.position.y = jelly.position.y +jelly.speed;
					jelly.bounds.y = jelly.position.y-jelly.bounds.height/2;
					screen.spriteBatch.draw(Asset.jellyP.getKeyFrame(stateTime,Animation.ANIMATION_LOOPING),jelly.position.x-32,jelly.position.y-32,64,64);
				}
			}
			//render BreakWall
			for(int i = 0 ; i < breakWalls.size();i++){
				BreakWall breakWall = breakWalls.get(i);
				if(breakWall.die){
					breakWall.stateTime += Gdx.app.getGraphics().getDeltaTime();
					breakWall.dieAni++;
					screen.spriteBatch.draw(Asset.dieAni.getKeyFrame(breakWall.stateTime, Animation.ANIMATION_NONLOOPING),
							breakWall.position.x-64,breakWall.position.y-32,128,64);
					if(breakWall.dieAni == 3) breakWalls.remove(i); 
				}
				else{
					screen.spriteBatch.draw(Asset.wall[3][1],breakWall.position.x-16,breakWall.position.y-16,32,32);
				}
			}
			//render Turtle
			for(int i = 0 ; i< turtles.size(); i++){
				Body turtle = turtles.get(i);
				if(turtle.getLinearVelocity().x > 0 )
					screen.spriteBatch.draw(Asset.turtleR.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING), turtle.getPosition().x*screen.PIXELS_PER_METER-64,
													  turtle.getPosition().y*screen.PIXELS_PER_METER-32,
													  128,64);
				else
					screen.spriteBatch.draw(Asset.turtleL.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING), turtle.getPosition().x*screen.PIXELS_PER_METER-64,
							  turtle.getPosition().y*screen.PIXELS_PER_METER-32,
							  128,64);
			}
			//render Teach
			for(int i = 0; i < teachs.size() ; i++){
				Teach teach = teachs.get(i);
				if(teach.state == Teach.PULL){
					screen.spriteBatch.draw(Asset.teach[0][0],teach.position.x-128,teach.position.y-128,256,256);
				}
				else{
					screen.spriteBatch.draw(Asset.teach[1][0],teach.position.x-128,teach.position.y-128,256,256);
				}
			}
			//render Arrow
			for(int i = 0 ; i < arrows.size() ; i++){
				Arrow arrow = arrows.get(i);
				if(arrow.state == Arrow.LINE){
					screen.spriteBatch.draw(Asset.arrowLine,arrow.position.x-32,arrow.position.y-32,32,32,64,64,1,1,arrow.theta);
				}
				else{
					screen.spriteBatch.draw(Asset.arrowCurve,arrow.position.x-64,arrow.position.y-32,64,32,128,64,1,1,arrow.theta);
				}
			}
			
		
		screen.spriteBatch.end();
	}
}
