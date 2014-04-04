package game.shootingtaco;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.stbtt.TrueTypeFontFactory;

public class Asset{
	
	public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
	
	public static Pixmap level ;
	public static Texture goal;
	public static TextureRegion bigGoal;
	public static TextureRegion[][] wall;
	public static TextureRegion[][] item;
	public static TextureRegion[][] grass;
	public static TextureRegion[][] animation;
	public static TextureRegion[][] taco;
	public static TextureRegion[][] tail;
	public static TextureRegion[][] partcial;
	public static TextureRegion[][] laps;
	public static TextureRegion[][] turtle;
	public static TextureRegion[][] jellyfish;
	public static TextureRegion[][] levelStar;
	public static TextureRegion[][] dieAnimation;
	public static TextureRegion[][] bubbleCD;
	public static TextureRegion[][] teach;
	public static TextureRegion[][] speed;
	public static TextureRegion[] bubbleTeach;
	public static TextureRegion[] bulletTeach;
	public static TextureRegion[] howToPlay;
	public static TextureRegion[][] translation;
	public static Texture player;
	public static TextureRegion arrowLine;
	public static TextureRegion arrowCurve;
	public static TextureRegion tacoCoral;
	public static TextureRegion swirl;
	public static TextureRegion swirl2;
	public static Texture soundoff,soundon;
	public static TextureRegion background;
	public static TextureRegion menuPage;
	public static TextureRegion pauseBonus;
	public static TextureRegion pause;
	public static TextureRegion levelEnd;
	public static TextureRegion die;
	public static TextureRegion choice_bg;
	public static TextureRegion choicePart;
	public static TextureRegion selectLevel;
	public static TextureRegion back;
	public static TextureRegion star,unStar;
	public static TextureRegion bubble;
	public static TextureRegion shell;
	public static TextureRegion coral;
	public static TextureRegion camShape;

	public static TextureRegion bulletButton;
	public static TextureRegion bulletCount;
	public static TextureRegion partLock,levelLock;
	public static TextureRegion health_begin,health,health_end,none_health_begin,none_health,none_health_end;
	public static Animation energyFishL;
	public static Animation energyFishR;
	public static Animation healthFishL;
	public static Animation healthFishR;
	public static Animation turtleL;
	public static Animation turtleR;
	public static Animation jellyR;
	public static Animation jellyY;
	public static Animation jellyP;
	public static Animation partcialAni;
	public static Animation lapsAni;
	public static Animation starAni;
	public static Animation dieAni;
	public static Animation bubbleTeachAni;
	public static Animation bulletTeachAni;
	public static Animation changePartAni;
	
	public static Music bg;
	public static Music shoot;
	public static Music deathTime;
	public static Music fish;
	public static Music shellM;
	public static Music shootTime;
	public static Music shootBullet;
	public static Music bomb;
	public static Music message;
	public static Music message2;
	public static Music lapsM;
	public static Music swirlM;
	public static Music victory;
	public static Music choice;
	public static Music bg2;
	public static Music breakWall;
	public static Music bubbleM;
	public static Music lose;
	public static Music pull;
	public static Music coralM;
	public static Music button;
	public static Music bubbleCDM;
	public static Music start;
	
	public static BitmapFont font;
	public static BitmapFont bulletFont;
	public static BitmapFont score;
	public static BitmapFont scoreFont;
	public static BitmapFont bubbleFont;
	
	public static AssetManager manager;
	
	
	public static void loadAsset(){
		manager = new AssetManager();
		manager.load("data/level.png",Pixmap.class);
		manager.load("data/animation.png",Texture.class);
		manager.load("data/arrow_Line.png",Texture.class);
		manager.load("data/arrow_Curve.png",Texture.class);
		manager.load("data/teach.png",Texture.class);
		manager.load("data/speed.png",Texture.class);
		manager.load("data/dieAni.png",Texture.class);
		manager.load("data/bubbleCD.png",Texture.class);
		manager.load("data/wall1.png",Texture.class);
		manager.load("data/wall2.png",Texture.class);
		manager.load("data/changePart1.jpg",Texture.class);
		manager.load("data/changePart2.jpg",Texture.class);
		manager.load("data/item.png",Texture.class);
		manager.load("data/grass.png",Texture.class);
		manager.load("data/sprite.png",Texture.class);
		manager.load("data/level1_bg.png",Texture.class);
		manager.load("data/level2_bg.png",Texture.class);
		manager.load("data/menupage.png",Texture.class);
		manager.load("data/pause_bg.png",Texture.class);
		manager.load("data/pause.png",Texture.class);
		manager.load("data/level_end.png",Texture.class);
		manager.load("data/choice.png",Texture.class);
		manager.load("data/choicePart.png",Texture.class);
		manager.load("data/selectLevel.png",Texture.class);
		manager.load("data/back.png",Texture.class);
		manager.load("data/star.png",Texture.class);
		manager.load("data/jellyfish.png",Texture.class);
		manager.load("data/Turtle.png",Texture.class);
		manager.load("data/health.png",Texture.class);
		manager.load("data/die.png",Texture.class);
		manager.load("data/taco.png",Texture.class);
		manager.load("data/goal.png",Texture.class);
		manager.load("data/bigGoal.png",Texture.class);
		manager.load("data/bubble.png",Texture.class);
		manager.load("data/partcial.png",Texture.class);
		manager.load("data/music_on.png",Texture.class);
		manager.load("data/music_off.png",Texture.class);
		manager.load("data/partLock.png",Texture.class);
		manager.load("data/levelLock.png",Texture.class);
		manager.load("data/shell.png",Texture.class);
		manager.load("data/coral.png",Texture.class);
		manager.load("data/laps.png",Texture.class);
		manager.load("data/starAni.png",Texture.class);
		manager.load("data/translation.png",Texture.class);
		manager.load("data/tacoCoral.png",Texture.class);
		manager.load("data/bulletButton.png",Texture.class);
		manager.load("data/bulletCount.png",Texture.class);
		manager.load("data/swirl.png",Texture.class);
		manager.load("data/swirl2.png",Texture.class);
		manager.load("data/bubbleTeach1.png",Texture.class);
		manager.load("data/bubbleTeach2.png",Texture.class);
		manager.load("data/bulletTeach1.png",Texture.class);
		manager.load("data/bulletTeach2.png",Texture.class);
		manager.load("data/howToPlay1.png",Texture.class);
		manager.load("data/howToPlay2.png",Texture.class);
		manager.load("data/camShape.png",Texture.class);
		manager.load("data/bg.mp3",Music.class);
		manager.load("data/shoot.mp3",Music.class);
		manager.load("data/deathTime.mp3",Music.class);
		manager.load("data/fish.mp3",Music.class);
		manager.load("data/shell.mp3",Music.class);
		manager.load("data/shootTime.mp3",Music.class);
		manager.load("data/shootBullet.mp3",Music.class);
		manager.load("data/bomb.mp3",Music.class);
		manager.load("data/message.mp3",Music.class);
		manager.load("data/message2.mp3",Music.class);
		manager.load("data/laps.mp3",Music.class);
		manager.load("data/swirl.mp3",Music.class);
		manager.load("data/victory.mp3",Music.class);
		manager.load("data/choice.mp3",Music.class);
		manager.load("data/bg2.mp3",Music.class);
		manager.load("data/breakWall.mp3",Music.class);
		manager.load("data/coral.mp3",Music.class);
		manager.load("data/bubble.mp3",Music.class);
		manager.load("data/lose.mp3",Music.class);
		manager.load("data/pull.mp3",Music.class);
		manager.load("data/button.mp3",Music.class);
		manager.load("data/bubbleCD.mp3",Music.class);
		manager.load("data/start.mp3",Music.class);
	}
	
	public static Texture loadTexture (String file) {
		//return new Texture(Gdx.files.internal(file));
		Texture texture = manager.get(file, Texture.class);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return texture;
	}

	/*public static TextureRegion load (String name, int width, int height) {
		Texture texture = new Texture(Gdx.files.internal(name));
		TextureRegion region = new TextureRegion(texture, 0, 0, width, height);
		region.flip(false, true);
		return region;
	}*/
	public static void load() {
		// TODO Auto-generated method stub
		level = new Pixmap(Gdx.files.internal("data/level.png"));
		//wall = split("data/wall.png",32,32);
		item = split("data/item.png",32,32);
		grass = split("data/grass.png",32,32);
		animation = split("data/animation.png",64,64);
		teach = split("data/teach.png",256,256);
		speed = split("data/speed.png",70,68);
		dieAnimation = split("data/dieAni.png",128,96);
		bubbleCD = split("data/bubbleCD.png",128,128);
		partcial = split("data/partcial.png",128,128);
		taco = split("data/taco.png",96,96);
		laps = split("data/laps.png",128,64);
		turtle = split("data/Turtle.png",128,64);
		jellyfish = split("data/jellyfish.png",64,64);
		levelStar = split("data/starAni.png",96,96);
		translation = split("data/translation.png",64,64);
		player = loadTexture("data/sprite.png");
		arrowLine = load("data/arrow_Line.png",64,64);
		arrowCurve = load("data/arrow_Curve.png",128,64);
		tacoCoral = load("data/tacoCoral.png",96,96);
		swirl = load("data/swirl.png",128,128);
		swirl2 = load("data/swirl2.png",128,128);
		menuPage = load("data/menupage.png",1280,800);
		pauseBonus = load("data/pause.png",64,64);
		pause = load("data/pause_bg.png",960,640);
		levelEnd = load("data/level_end.png",960,640);
		die = load("data/die.png",960,640);
		choice_bg = load("data/choice.png",1280,800);
		choicePart = load("data/choicePart.png",901,380);
		selectLevel = load("data/selectLevel.png",1280,800);
		back = load("data/back.png",128,128);
		star = load("data/star.png",45,45);
		goal = loadTexture("data/goal.png");
		bigGoal = load("data/bigGoal.png",1600,1600);
		bubble = load("data/bubble.png",128,128);
		partLock = load("data/partLock.png",380,380);
		levelLock = load("data/levelLock.png",192,240);
		shell = load("data/shell.png",96,87);
		coral = load("data/coral.png",96,64);
		bulletButton = load("data/bulletButton.png",179,191);
		camShape = load("data/camShape.png",960,640);
		bulletCount = load("data/bulletCount.png",89,70);
		soundoff = loadTexture("data/music_off.png");
		soundon = loadTexture("data/music_on.png");
		tail = new TextureRegion[8][5];
		for(int i = 0 ; i < 8 ; i++){
			for(int j = 4 ; j < 9 ; j++){
				tail[i][j-4] = animation[i][j];	
			}
			
		}
		bubbleTeach = new TextureRegion[2];
		bubbleTeach[0] = load("data/bubbleTeach1.png",960,640);
		bubbleTeach[1] = load("data/bubbleTeach2.png",960,640);
		bulletTeach = new TextureRegion[2];
		bulletTeach[0] = load("data/bulletTeach1.png",960,640);
		bulletTeach[1] = load("data/bulletTeach2.png",960,640);
		howToPlay = new TextureRegion[2];
		howToPlay[0] = load("data/howToPlay1.png",1280,800);
		howToPlay[1] = load("data/howToPlay2.png",1280,800);
		//Health
		health_begin = new TextureRegion(loadTexture("data/health.png"),0,0,4,32);
		health = new TextureRegion(loadTexture("data/health.png"),4,0,2,32);
		health_end = new TextureRegion(loadTexture("data/health.png"),6,0,4,32);
		none_health_begin = new TextureRegion(loadTexture("data/health.png"),10,0,4,32);
		none_health = new TextureRegion(loadTexture("data/health.png"),14,0,2,32);
		none_health_end = new TextureRegion(loadTexture("data/health.png"),16,0,4,32);
		////
		
		//animation
		energyFishL = new Animation(0.2f,animation[0][0],animation[1][0],animation[2][0],animation[3][0],animation[4][0],animation[5][0],animation[6][0],animation[7][0]);
		energyFishR = new Animation(0.2f,animation[0][1],animation[1][1],animation[2][1],animation[3][1],animation[4][1],animation[5][1],animation[6][1],animation[7][1]);
		healthFishL = new Animation(0.2f,animation[0][2],animation[1][2],animation[2][2],animation[3][2],animation[4][2],animation[5][2],animation[6][2],animation[7][2]);
		healthFishR = new Animation(0.2f,animation[0][3],animation[1][3],animation[2][3],animation[3][3],animation[4][3],animation[5][3],animation[6][3],animation[7][3]);
		partcialAni = new Animation(0.1f,partcial[0][0],partcial[1][0],partcial[2][0]);
		lapsAni = new Animation(0.1f,laps[0][0],laps[1][0]);
		turtleL = new Animation(0.2f,turtle[0][1],turtle[1][1]);
		turtleR = new Animation(0.2f,turtle[0][0],turtle[1][0]);
		jellyP = new Animation(0.2f,jellyfish[0][2],jellyfish[1][2]);
		starAni = new Animation(0.2f,levelStar[1][0],levelStar[2][0],levelStar[1][0],levelStar[2][0]);
		dieAni = new Animation(0.2f,dieAnimation[0][0],dieAnimation[1][0],dieAnimation[2][0]);
		bubbleTeachAni = new Animation(0.4f,bubbleTeach[0],bubbleTeach[1]);
		bulletTeachAni = new Animation(0.4f,bulletTeach[0],bulletTeach[1]);
		changePartAni = new Animation(2.0f,load("data/changePart1.jpg",1280,800),load("data/changePart2.jpg",1280,800));
		
		//music = Gdx.audio.newMusic(Gdx.files.internal("data/bg.mp3"));
		
		
		//shoot = Gdx.audio.newMusic(Gdx.files.internal("data/shoot.mp3"));
		
		loadMusic();
		loadFont();		
		
	}
	public static void loadMusic(){
		bg = manager.get("data/bg.mp3", Music.class);
		bg.setLooping(true);
		shoot = manager.get("data/shoot.mp3", Music.class);	
		deathTime = manager.get("data/deathTime.mp3", Music.class);	
		fish= manager.get("data/fish.mp3", Music.class);	
		shellM= manager.get("data/shell.mp3", Music.class);	
		shootTime= manager.get("data/shootTime.mp3", Music.class);	
		shootBullet= manager.get("data/shootBullet.mp3", Music.class);	
		bomb= manager.get("data/bomb.mp3", Music.class);	
		message= manager.get("data/message.mp3", Music.class);	
		message2= manager.get("data/message2.mp3", Music.class);	
		lapsM= manager.get("data/laps.mp3", Music.class);	
		swirlM= manager.get("data/swirl.mp3", Music.class);	
		victory= manager.get("data/victory.mp3", Music.class);	
		choice= manager.get("data/choice.mp3", Music.class);
		choice.setLooping(true);
		bg2= manager.get("data/bg2.mp3", Music.class);	
		bg2.setLooping(true);
		breakWall= manager.get("data/breakWall.mp3", Music.class);	
		bubbleM= manager.get("data/bubble.mp3", Music.class);	
		lose= manager.get("data/lose.mp3", Music.class);	
		pull= manager.get("data/pull.mp3", Music.class);
		coralM= manager.get("data/coral.mp3", Music.class);	
		button = manager.get("data/button.mp3", Music.class);	
		bubbleCDM = manager.get("data/bubbleCD.mp3", Music.class);
		start = manager.get("data/start.mp3", Music.class);
		start.setVolume(1.0f);
	}
	public static void loadFont(){
		font = TrueTypeFontFactory.createBitmapFont(Gdx.files.internal("data/show.ttf"), FONT_CHARACTERS, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 50.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		bulletFont = TrueTypeFontFactory.createBitmapFont(Gdx.files.internal("data/show.ttf"), FONT_CHARACTERS, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight(), 28.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		score = TrueTypeFontFactory.createBitmapFont(Gdx.files.internal("data/show.ttf"), FONT_CHARACTERS, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight(), 50.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		scoreFont = TrueTypeFontFactory.createBitmapFont(Gdx.files.internal("data/har.ttf"), FONT_CHARACTERS, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight(), 35.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		bubbleFont = TrueTypeFontFactory.createBitmapFont(Gdx.files.internal("data/show.ttf"), FONT_CHARACTERS, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight(), 40.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public static TextureRegion load (String name, int width, int height) {
		//Texture texture = new Texture(Gdx.files.internal(name));
		Texture texture = manager.get(name, Texture.class);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion region = new TextureRegion(texture, 0, 0, width, height);
		region.flip(false, true);
		return region;
	}
	
	public static TextureRegion[][] split (String name, int width, int height) {
		return split(name, width, height, false);
	}

	public static TextureRegion[][] split (String name, int width, int height, boolean flipX) {
		//Texture texture = new Texture(Gdx.files.internal(name));
		Texture texture = manager.get(name, Texture.class);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		int xSlices = texture.getWidth() / width;
		int ySlices = texture.getHeight() / height;
		TextureRegion[][] res = new TextureRegion[xSlices][ySlices];
		for (int x = 0; x < xSlices; x++) {
			for (int y = 0; y < ySlices; y++) {
				res[x][y] = new TextureRegion(texture, x * width, y * height, width, height);
				res[x][y].flip(flipX, true);
			}
		}
		return res;
	}
	public static void playSound (Music sound) {
		if (LevelSetting.voice) sound.play();
	}




}
