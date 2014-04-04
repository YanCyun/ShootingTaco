package game.shootingtaco;

public class BreakWall extends GameObject{
	private static float width = 32.0f;
	private static float height = 32.0f;
	public float stateTime = 0.0f;
	public float dieAni = 0;
	public boolean die = false;
	public boolean touch = false;
	public BreakWall(float x,float y){
		super(x,y,width,height);
	}
}