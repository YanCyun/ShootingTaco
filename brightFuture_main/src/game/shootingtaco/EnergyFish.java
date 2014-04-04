package game.shootingtaco;

public class EnergyFish extends GameObject{
	private static float width = 64.0f;
	private static float height = 32.0f;
	public float speed = 5.0f;
	public float tmpSpeed;
	public boolean eated = false;
	public int eatAni = 0;
	public float stateTime = 0.0f;
	
	public EnergyFish(float x,float y){
		super(x,y,width,height);
	}
}
