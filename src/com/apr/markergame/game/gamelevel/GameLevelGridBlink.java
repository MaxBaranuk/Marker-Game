package com.apr.markergame.game.gamelevel;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class GameLevelGridBlink extends GameLevel{

	private static final int BLINK = 1;
	private static final int PLAY = 2;
	private int state;

	private static int MIN_PATH = 3;
	private static int MAX_PATH = 6;

	private static int PATH_SIZE = 5;
	private static Array<Integer> path;

	private static final float MIN_BLINK = 0.01f;
	private static final float MAX_BLINK = 0.3f;
	class Blink{
		float time;
		boolean on;
		Blink(float time){
			this.time = time;
			on = false;
		}
	}

	private static Array<Blink> blinks;
	
	private static final float MAX_TIME = 10f;
	private float time;

	private static final float MAX_DELAY = 5;
	private float delay;

	
	private static final int COLS = 6;
	private static final int ROWS = 6;

	
	public GameLevelGridBlink(){
		super();
		path = new Array<Integer>();
		blinks = new Array<Blink>();
		
	}
	
	private void createPath(){
		path.clear();
		blinks.clear();
		for (int i=0;i<PATH_SIZE;i++){
			int value = random.nextInt(ROWS*COLS);
			while (path.contains(value, true)){
				value = random.nextInt(ROWS*COLS);
			}
			path.add(value);
			blinks.add(new Blink(MathUtils.random(MIN_BLINK, MAX_BLINK)));
		}
	}
	
	public void startGame(){
		started = true;
		pass = 1;
		state = BLINK;
		MIN_PATH = 3;
		MAX_PATH = 6;
		PATH_SIZE = MathUtils.random(MIN_PATH, MAX_PATH);
		createPath();
		time = MAX_TIME;
		delay = MAX_DELAY;
	}
	
	public void startPass(){
		started = true;
		pass++;
		state = BLINK;
		MIN_PATH++;
		MAX_PATH++;
		PATH_SIZE = MathUtils.random(MIN_PATH, MAX_PATH);
		createPath();
		time = MAX_TIME;
		delay = MAX_DELAY;
	}
	
	public void touch(float screenX, float screenY){
		
		if (state==PLAY){
			int tx = (int) (screenX / 80f);
			int ty =  COLS-1-(int) ( (screenY+80-200) / 80f);
			if (path.size==ty*COLS+tx+1){
				started = false;
				listener.passStep(MAX_TIME-time);
			}else{
				started = false;
				listener.fail();		
			}
			
		}
	}
	
	public void update(float delta){
		if (!started) return;
		if (state==BLINK){
				for (Blink blink: blinks){
					blink.time = blink.time - delta;
					if (blink.time<0){
						blink.on = !blink.on;
						blink.time = MathUtils.random(MIN_BLINK, MAX_BLINK);
					}
				}
			
				delay-=delta;
				if (delay<0){
					state = PLAY;
					time = MAX_TIME;
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
				batch.draw(atlas.findRegion("grid"),  (i%ROWS)*80, 200 + (i/ROWS)*80);
		}
		
		
		
		
		if (started)
		{
			if (state==BLINK){
				for (int i=0;i<blinks.size;i++){
					int position = path.get(i);
					if (blinks.get(i).on)
						batch.draw(atlas.findRegion("grid_pressed"),  (position%ROWS)*80, 200 + (position/ROWS)*80);
				}

			}
			
			if (state==PLAY){
				font.draw(batch, "Time Left: " + twoDForm.format(time), 0, 750);
				for (int i=0;i<ROWS*COLS;i++){
					
					font.draw(batch,"" + (i+1), (i%ROWS)*80+20, 200 + (i/ROWS)*80+60);

				}
			}
			
		}
		
		batch.end();

	}
	
}
