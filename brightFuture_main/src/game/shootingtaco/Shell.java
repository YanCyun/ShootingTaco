package game.shootingtaco;

public class Shell extends GameObject{
	private static float width = 64.0f;
	private static float height = 64.0f;
	public boolean eated = false;
	public int eatAni = 0;
	public float stateTime = 0.0f;
	
	public Shell(float x,float y){
		super(x,y,width,height);
	}
}
