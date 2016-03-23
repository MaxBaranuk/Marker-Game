package com.apr.markergame.game.gamelevel;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class GameLevelGridFlash extends GameLevel{

	private static final int SHOW = 1;
	private static final int PLAY = 2;
	private int state;

	
	private float time;
	
	private static final int COLS = 16;
	private static final int ROWS = 12;
	private static final int NUM_FLASH = 10;
	private static final float START_Y = 50f;
	
	private Array<Integer> path;
	private int selected;
	private boolean on;
	private int curFlash;
	

	public GameLevelGridFlash(){
		super();
		path = new Array<Integer>();
		
	}
	
	private void createPath(){
		path.clear();
		int size = MathUtils.random(10, 15);
		for (int i=0;i<size;i++){
			int value = random.nextInt(ROWS*COLS);
			while (path.contains(value, true)){
				value = random.nextInt(ROWS*COLS);			
			}
			path.add(value);
				
				
		}
		selected = random.nextInt(size);
		
	}
	
	public void startGame(){
		super.startGame();
		state = SHOW;
		createPath();
		time = currentFactor;;
		curFlash = 0;
	}
	
	public void startPass(){
		super.startPass();
		state = SHOW;
		createPath();
		time = currentFactor;
		curFlash = 0;
	}
	
	public void touch(float screenX, float screenY){
		
		if (state==PLAY){
			int tx = (int) (screenX / 40f);
			int ty = (int) ((800f-screenY-START_Y)/40f);

			if (ty*ROWS+tx == path.get(selected)){
				started = false;
				listener.passStep(TIME_TO_ANSWER-time);
			}else{
				started = false;
				listener.fail();
			}
			
		}else{
			started = false;
			listener.fail();
		}
	}
	
	public void update(float delta){
		if (!started) return;
		if (state==SHOW){		
				time-=delta;
				if (time<0){
					curFlash++;
					on = !on;
					time = currentFactor;
					if (curFlash>=NUM_FLASH){
						state = PLAY;
						time = TIME_TO_ANSWER;
						
					}
				}
			
				return;
		}
		
		if (state==PLAY){
			time-=delta;
			if (time<0){
				started = false;
				listener.fail();
			}
		
			return;
		}
	}
	
	public void render(){
		
		batch.begin();	
		batch.setColor(1f, 1f, 1f, 1f);
		for (int i=0;i<ROWS*COLS;i++){
			batch.draw(atlas.findRegion("grid_small"),  (i%ROWS)*40, START_Y + (i/ROWS)*40,40,40);
		}
		
		
		
		if (started)
		{
			if (state==SHOW){
				if (on){
					for (int i=0;i<path.size;i++){
						if (curFlash!=5 || i!=selected)
							batch.draw(atlas.findRegion("grid_small_pressed"),  (path.get(i)%ROWS)*40, START_Y + (path.get(i)/ROWS)*40,40,40);
						//else{
						//	batch.draw(atlas.findRegion("grid_green"),  (path.get(i)%ROWS)*40, START_Y + (path.get(i)/ROWS)*40,40,40);
						//}
					}
					
				}
			}


			if (state==PLAY){
				font.draw(batch, "Time Left: " + twoDForm.format(time), 0, 750);
			}
			
		}
		
		batch.end();

	}
	
}
