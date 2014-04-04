package game.shootingtaco;

import com.badlogic.gdx.math.Vector2;


public class Teach{
	public static final int PULL = 1;
	public static final int GYRO = 2;
	
	public final Vector2 position;
	
	public int state;
	
	public Teach(float x, float y,int state) {
		this.position = new Vector2(x, y);
		this.state = state;
	}
}
