package com.apr.markergame.game.gamelevel;




public class GameLevelGridRun extends GameLevel{


	
	private float time;
	
	private static final int COLS = 7;
	private static final int ROWS = 9;
	
	private int path[];
	
	private int position,goal;
//	Array
	

	public GameLevelGridRun(){
		super();
		path = new int[ROWS*2+(COLS-1)*2-2];
		goal = ((ROWS-1)*COLS + COLS/2); 
		position = goal;
		for (int i=0;i<COLS;i++){
			path[i] = (ROWS-1)*COLS+i;
		}

		for (int i=0;i<ROWS-1;i++){
			path[COLS+i] = path[COLS+i-1]-COLS;
		}
		for (int i=0;i<COLS-1;i++){
			path[COLS+ROWS-1+i] = path[COLS+ROWS+i-2]-1;

		}

		for (int i=0;i<ROWS-2;i++){
			path[COLS-1+COLS+ROWS-1+i] = path[COLS-1+COLS+ROWS-1+i]+COLS*(i+1);
		}
	}
	
	
	public void startGame(){
		super.startGame();
		time = currentFactor;;
		position = COLS/2;
	}
	
	public void startPass(){
		super.startPass();
		time = currentFactor;
		position = COLS/2;
	}
	
	public void touch(float screenX, float screenY){
		started = false;
		if (Math.abs(path[position]-goal)<5){
			listener.passStep(Math.abs(path[position]-goal));
		}else{
			listener.fail();
		}
		
//		if (path[position]==goal){
//			listener.passStep(currentFactor-time);
//		}else{
//			listener.fail();
//		}
			
	}
	
	/*private void move(){
		System.out.println(position + " " + position%COLS);
		if (position%COLS==0){
			position+=COLS;
		}
		else if (position<=COLS){
			position--;
		}
		else if ((position+1)%COLS==0){
			System.out.println("L");
			position-=COLS;
		}
		else{
			position++;
		}
	}*/
	
	public void update(float delta){
		if (!started) return;
		
		time-=delta;
		if (time<0){
			position++;
			if (position>=path.length) position = 0;
			time = currentFactor;
		}
	}
	
	public void render(){
		
		batch.begin();	
		batch.setColor(1f, 1f, 1f, 1f);
		for (int i=0;i<path.length;i++){
			batch.draw(atlas.findRegion("grid_small"),  (path[i]%COLS)*68, 100 + (path[i]/COLS)*68,68,68);
		}
		
		
		
		if (started)
		{
			batch.draw(atlas.findRegion("grid_small_goal"),  (goal%COLS)*68, 100 + (goal/COLS)*68,68,68);

			batch.draw(atlas.findRegion("grid_small_pressed"),  (path[position]%COLS)*68, 100 + (path[position]/COLS)*68,68,68);
		}
		
		batch.end();

	}
	
}
