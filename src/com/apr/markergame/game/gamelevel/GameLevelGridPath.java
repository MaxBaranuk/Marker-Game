package com.apr.markergame.game.gamelevel;

import com.apr.markergame.services.MarkerSound;
import com.badlogic.gdx.utils.Array;

public class GameLevelGridPath extends GameLevel{

	private static final int APPEAR = 1;
	private static final int ALL_RED = 2;
	private static final int ALL_WHITE = 3;
	private static final int PLAY = 4;
	private int state;
	private static final int SIZE_PATH = 4;
	
	private static final int START_Y = 100;
	private int curPathSize = 4;
	private static Array<Integer> path;
	Array<Integer> options = new Array<Integer>();
	
	
	private static final float MAX_TIME = 10f;
	private float time;
	private float avg;

	
	private static final int COLS = 8;
	private static final int ROWS = 6;
	int curPath;
	int solution;

	
	public GameLevelGridPath(){
		super();
		path = new Array<Integer>();

	}
	
	private boolean createPath(){
		path.clear();
		/*int curPos = random.nextInt(COLS*ROWS);
		getOptions(curPos);
		if (options.size==0){
			return false;		
		}
		for (int i=0;i<PATH_SIZE;i++){
			path.add(curPos);
			curPos = options.get(random.nextInt(options.size));
			getOptions(curPos);
			if (options.size==0){
				return false;		
			}
		}*/
		
		for (int i=0;i<curPathSize;i++){
			int selected = random.nextInt(COLS*ROWS);
			while (path.contains(selected, true)){
				selected = random.nextInt(COLS*ROWS);
			}
			path.add(selected);
		}
		return true;
	}
	/*
	private void getOptions(int curPos){
		options.clear();
		if (curPos+1<COLS && !path.contains(curPos+1, true))
			options.add(curPos+1);
		
		if (curPos-1>=0&& !path.contains(curPos-1, true))
			options.add(curPos-1);
		
		if (curPos-COLS>=0 && !path.contains(curPos-COLS, true))
			options.add(curPos-COLS);
		
		if (curPos+COLS<ROWS*COLS && !path.contains(curPos+COLS, true))
			options.add(curPos+COLS);
	}*/
	public void startGame(){
		super.startGame();
		state = APPEAR;
		curPath = 0;
		solution = 0;
		curPathSize = SIZE_PATH;
		time = currentFactor;
		while (!createPath()){};
		

	}
	
	public void startPass(){
		super.startPass();
		state = APPEAR;
		curPathSize++;
		curPath = 0;
		solution = 0;
		time = currentFactor;
		while (!createPath()){};
		
		System.out.println(path);
	}
	
	public void touch(float screenX, float screenY){
		
		
		if (state==PLAY){
			int tx = (int) (screenX / 80f);
			int ty = (int) ((800-screenY-START_Y)/80f);
				
			System.out.println(tx + " " + ty);
			System.out.println(tx+ty*ROWS + " " +  path.get(solution));
			
			
			if (tx+ty*ROWS == path.get(solution)){
				listener.playSound(MarkerSound.TICK);

				solution++;
				if (solution>=path.size){
					started = false;
					listener.passStep(MAX_TIME-time);
				}
			}else{
				started = false;
				listener.fail();
			}
		}
	}
	
	public void update(float delta){
		if (!started) return;
		if (state==APPEAR){
				time-=delta;
				avg = time / currentFactor;

				if (time<0){
					curPath++;
					time = currentFactor;
					avg =1f;
					if (curPath>=path.size){
						time = 0.5f;
						state=ALL_RED;
					}
				}
			
				return;
		}
		
		
		if (state==ALL_RED){
			time-=delta;
			avg = time / 0.5f;
			if (time<0){
				state = ALL_WHITE;
				time = 0.5f;
				avg = 1f;
			}
		
			return;
		}
		
		if (state==ALL_WHITE){
			time-=delta;
			avg = time / 0.5f;
			if (time<0){
				state = PLAY;
				time = MAX_TIME;
			}
		
			return;
		}
		
		if (state==PLAY){
			time-=delta;
			avg = time / currentFactor;
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
				batch.draw(atlas.findRegion("grid"),  (i%ROWS)*80, START_Y + (i/ROWS)*80);
		}
				
		
		if (started)
		{
			if (path.size>0){
				if (state==APPEAR){
					for (int i=0;i<curPath;i++){						
						batch.setColor(1f, 1f, 1f, 1f);
						batch.draw(atlas.findRegion("grid_pressed"), (path.get(i)%ROWS)*80, START_Y + (path.get(i)/ROWS)*80);
					}

					batch.setColor(1f, 1f, 1f, 1f-avg);
					batch.draw(atlas.findRegion("grid_pressed"), (path.get(curPath)%ROWS)*80, START_Y + (path.get(curPath)/ROWS)*80);
				}else if (state==ALL_RED){
					
					
					
					for (int i=0;i<ROWS*COLS;i++){
						batch.setColor(1f, 1f, 1f, 1f-avg);
						batch.draw(atlas.findRegion("grid_pressed"),  (i%ROWS)*80, START_Y + (i/ROWS)*80);
					}
					for (int i=0;i<curPath;i++){						
						batch.setColor(1f, 1f, 1f, 1f);
						batch.draw(atlas.findRegion("grid_pressed"), (path.get(i)%ROWS)*80, START_Y + (path.get(i)/ROWS)*80);
					}
					
				}else if (state==ALL_WHITE){
					for (int i=0;i<ROWS*COLS;i++){
						batch.setColor(1f, 1f, 1f, avg);
						batch.draw(atlas.findRegion("grid_pressed"),  (i%ROWS)*80, START_Y + (i/ROWS)*80);
					}
				}
			}
			
			if (state==PLAY){
				font.draw(batch, "Time Left: " + twoDForm.format(time), 0, 750);
				for (int i=0;i<solution;i++){
					batch.draw(atlas.findRegion("grid_pressed"),  (path.get(i)%ROWS)*80, START_Y + (path.get(i)/ROWS)*80);
				}
			}
			
		}
		
		batch.end();

	}
	
}
