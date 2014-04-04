package game.shootingtaco;

public class Bullet extends GameObject{
	private static float width = 17.0f;
	private static float height = 17.0f;
	public float speedX,speedY;
	public float theta = 1000.0f;
	
	public Bullet(float x,float y){
		super(x,y,width,height);
	}
}
