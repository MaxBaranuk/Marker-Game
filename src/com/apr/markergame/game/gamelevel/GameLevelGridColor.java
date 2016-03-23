package com.apr.markergame.game.gamelevel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.utils.Array;

public class GameLevelGridColor extends GameLevel{

	private static final int ALL_RED = 1;
	private static final int ALL_UNCOLOR = 2;
	private static final int ALL_COLOR = 3;
	private static final int WAIT_ANSWER = 4;
	private int state;
	
	private static final int COLS = 12;
	private static final int ROWS = 15;
	private static final float MAX_DIST = (float)Math.sqrt((COLS-1)*(COLS-1)*2);	
	private static final float START_Y = 100;
	
	
	class Cell{
		int x, y;
		float time,curTime;
		
		public Cell(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	private Array<Cell> cells;
	private Cell selected;
	
	private float time;
	private float avg;


	
	public GameLevelGridColor(){
		super();
		cells = new Array<Cell>();
		cells.clear();
		for (int i=0;i<ROWS*COLS;i++){
			cells.add(new Cell(i%COLS, i/COLS));
		}
	}
	
	
	private void setSelectedCell(){
		selected = cells.get(random.nextInt(cells.size));
		for (Cell cell: cells){
			cell.time = (1f-((float)Math.sqrt((cell.x-selected.x)*(cell.x-selected.x)+((cell.y-selected.y)*(cell.y-selected.y))))/MAX_DIST)*currentFactor;
			cell.curTime = cell.time;
			
		}
	}
	
	private void resetTime(){
		for (Cell cell: cells){
			cell.time = (((float)Math.sqrt((cell.x-selected.x)*(cell.x-selected.x)+((cell.y-selected.y)*(cell.y-selected.y))))/MAX_DIST)*currentFactor;

			cell.curTime = cell.time;
			
		}
	}
	
	public void startGame(){
		super.startGame();
		state = ALL_RED;		
		setSelectedCell();
		time = currentFactor;
	}
	
	public void startPass(){
		super.startPass();
		state = ALL_RED;
		setSelectedCell();
		time = currentFactor;

	}
	
	public void touch(float screenX, float screenY){
		
		if (state==WAIT_ANSWER){
			int tx = (int) (screenX / 40f);
			int ty =  ROWS-(int) ( (screenY+40-START_Y) / 40f);
			started = false;


			if (tx==selected.x && ty==selected.y){
				listener.passStep(TIME_TO_ANSWER-time);
			}else{
				listener.fail();
			}
		}else{
			started = false;
			listener.fail();
		}
	}
	
	public void update(float delta){
		if (!started) return;
		if (state==ALL_RED){
			time -= delta;
			avg = 1.0f-time/currentFactor;
			
			if (time<0){
				state = ALL_UNCOLOR;
				time = currentFactor;
			}
			return;
		}
		
		if (state==ALL_UNCOLOR){
			time-= delta;
			for (Cell cell: cells){
				cell.curTime-=delta;
				if (cell.curTime<0) cell.curTime=0;
			}
			if (time<0){
				state = ALL_COLOR;
				resetTime();
				time = currentFactor;
			}
			return;
		}
		
		if (state==ALL_COLOR){
			time-= delta;
			for (Cell cell: cells){
				cell.curTime-=delta;
				if (cell.curTime<0) cell.curTime=0;
			}
			if (time<0){
				state = WAIT_ANSWER;
				time = TIME_TO_ANSWER;
			}
			return;
		}
		
		if (state==WAIT_ANSWER){
			time-= delta;
			if (time<0){
				started = false;
				listener.fail();
			}
			return;
		}

		

	}
	
	public void render(){
		
		GL10 gl = Gdx.graphics.getGL10();
		cam.update();
        cam.apply(gl);
		batch.setProjectionMatrix(cam.combined);
		batch.begin();	
		
		for (Cell cell: cells){
			batch.draw(atlas.findRegion("grid_small"), cell.x*40, START_Y + cell.y*40);
		}
		
		if (state==ALL_RED){
			for (Cell cell: cells){
				batch.setColor(1f, 1f, 1f, avg);

				batch.draw(atlas.findRegion("grid_small_pressed"), cell.x*40, START_Y + cell.y*40);
			}
		}
		
		if (state==ALL_UNCOLOR){
			for (Cell cell: cells){
				//batch.setColor(1f, 1f, 1f, cell.curTime/cell.time);
				if (cell.curTime>0)
					batch.draw(atlas.findRegion("grid_small_pressed"), cell.x*40, START_Y + cell.y*40);
			}
			batch.draw(atlas.findRegion("grid_small_pressed"), selected.x*40, START_Y + selected.y*40);
		}
		
		if (state==ALL_COLOR){
			for (Cell cell: cells){
				//batch.setColor(1f, 1f, 1f, cell.curTime/cell.time);
				if (cell.curTime==0)
					batch.draw(atlas.findRegion("grid_small_pressed"), cell.x*40, START_Y + cell.y*40);
			}
			batch.draw(atlas.findRegion("grid_small_pressed"), selected.x*40, START_Y + selected.y*40);
		}
		batch.setColor(1f, 1f, 1f, 1f);
		
		
		if (started)
		{
			if (state==WAIT_ANSWER){
				font.draw(batch, "Time Left: " + twoDForm.format(time), 0, 750);
			}
			
		}
		
		batch.end();

	}
	
}
