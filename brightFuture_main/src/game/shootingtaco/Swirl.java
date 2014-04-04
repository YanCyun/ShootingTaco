package game.shootingtaco;

public class Swirl extends GameObject{
	public static int LEFT = 1;
	public static int RIGHT = 2;
	
	private static float width = 85.0f;
	private static float height = 85.0f;
	
	public boolean touch= false;
	public float stateTime = 0.0f;
	public int direction;
	
	public Swirl(float x,float y,int dir){
		super(x,y,width,height);
		direction = dir;
	}
}