package game.shootingtaco;

public class InvisibleWall extends GameObject{
	private static float width = 32.0f;
	private static float height = 32.0f;
	
	public InvisibleWall(float x,float y){
		super(x,y,width,height);
	}
}
