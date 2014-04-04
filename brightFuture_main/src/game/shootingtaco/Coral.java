package game.shootingtaco;

public class Coral extends GameObject{
	public static final int FACE_UP = 1;
	public static final int FACE_DOWN = 2;
	public static final int FACE_LEFT = 3;
	public static final int FACE_RIGHT = 4;
	
	private static float width = 80.0f;
	private static float height = 50.0f;

	public boolean touch = false;
	public int touchAni = 0;
	public float stateTime = 0.0f;
	public int state;
	
	public Coral(float x,float y,int state){
		super(x,y,width,height);
		this.state = state;
		if(state == FACE_UP || state == FACE_DOWN){
			this.bounds.width = 80.0f;
			this.bounds.height = 50.0f;
		}
		else{
			this.position.x = this.position.x - 16.0f;
			this.position.y = this.position.y + 16.0f;
			this.bounds.width = 50.0f;
			this.bounds.height = 80.0f;
			this.bounds.x = this.position.x - this.bounds.width/2;
			this.bounds.y = this.position.y - this.bounds.height/2;
			
		}
	}
}
