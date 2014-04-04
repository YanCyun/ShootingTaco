package game.shootingtaco;

public class Goal extends GameObject {
	private static float width = 150.0f;
	private static float height = 150.0f;
	
	public boolean big;
	public Goal(float x,float y,boolean big){
		super(x,y,width,height);
		this.big = big;
		if(big){
			this.bounds.width = 600.0f;
			this.bounds.height = 600.0f;		
			this.bounds.x = this.position.x - this.bounds.width/2;
			this.bounds.y = this.position.y - this.bounds.height/2;
		}
	}

}
