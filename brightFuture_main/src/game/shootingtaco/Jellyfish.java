package game.shootingtaco;



public class Jellyfish extends GameObject{
	private static float width = 64.0f;
	private static float height = 64.0f;
	public float speed = 2.0f;
	public float tmpSpeed;
	public float stateTime = 0.0f;
	public float dieAni = 0;
	public boolean die = false;
	public boolean touch = false;
	public Jellyfish(float x,float y){
		super(x,y,width,height);
	}
}
