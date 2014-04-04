package game.shootingtaco;

import com.badlogic.gdx.math.Vector2;

public class Arrow{
	public static final int LINE = 1;
	public static final int CURVE = 2;
	
	public final Vector2 position;
	
	public int state;
	public float theta;
	
	public Arrow(float x, float y,int state,float theta) {
		this.position = new Vector2(x, y);
		this.state = state;
		this.theta = theta;
	}
}
