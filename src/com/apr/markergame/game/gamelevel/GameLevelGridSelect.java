package com.apr.markergame.game.gamelevel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class GameLevelGridSelect extends GameLevel{

	private static final int ZOOM_IN = 1;
	private static final int WAIT = 2;
	private static final int ZOOM_OUT = 3;
	private static final int WAIT_ANSWER = 4;
	private int state;
	
	private static final float START_Y = 200;
	
	
	private static final int COLS = 6;
	private static final int ROWS = 6;
	private int x,y;
	private int tx, ty;
	
	private static final float INITIAL_DELAY_WAIT = 1.0f;
	private static final float DECREMENT_DELAY_WAIT = 0.12f;	
	private float timeElapsed;
	private float timeDelay;

	private static final float INITIAL_DELAY_ZOOM = 1.5f;
	private static final float DECREMENT_DELAY_ZOOM = 0.15f;	
	private float zoomElapsed;
	private float zoomDelay;

	private static final float INITIAL_DELAY_ANSWER = 3f;
	private static final float DECREMENT_DELAY_ANSWER = 0f;	
	private float answerElapsed;

	
	public GameLevelGridSelect(){
		super();


	}
	
	public void startGame(){
		started = true;
		pass = 1;
		state = ZOOM_IN;
		x = random.nextInt(ROWS);
		y = random.nextInt(COLS);
		
		timeElapsed = 0;
		timeDelay = INITIAL_DELAY_WAIT;
		zoomElapsed = 0;
		zoomDelay = INITIAL_DELAY_ZOOM;
		answerElapsed = INITIAL_DELAY_ANSWER;

	}
	
	public void startPass(){
		started = true;
		pass++;
		state = ZOOM_IN;
		x = random.nextInt(ROWS);
		y = random.nextInt(COLS);

		timeElapsed = 0;
		timeDelay = INITIAL_DELAY_WAIT-DECREMENT_DELAY_WAIT*pass;
		zoomElapsed = 0;
		zoomDelay = INITIAL_DELAY_ZOOM-DECREMENT_DELAY_ZOOM*pass;
		answerElapsed = INITIAL_DELAY_ANSWER-DECREMENT_DELAY_ANSWER*pass;

	}
	
	public void touch(float screenX, float screenY){
		
		if (state==WAIT_ANSWER){
			tx = (int) (screenX / 80f);
			ty =  COLS-1-(int) ( (screenY+80-START_Y) / 80f);
			started = false;

			if (tx==x && ty==y){
				listener.passStep(3f-answerElapsed);
			}else{
				listener.fail();
			}
		}
	}
	
	public void update(float delta){
		if (!started) return;
		if (state==ZOOM_IN){
			zoomElapsed += delta;
			
			cam.zoom=0.2f + 0.8f*(1f-zoomElapsed/zoomDelay);
			cam.position.x =  240f + ((x*80 + 40)-240f)*(zoomElapsed/zoomDelay);
			cam.position.y =  400f + ((y*80 + START_Y + 40)-400f)*(zoomElapsed/zoomDelay);
			
			if (zoomElapsed>=zoomDelay){
				zoomElapsed = 0;
				state = WAIT;
			}
			return;
		}
		
		if (state==WAIT){
			timeElapsed += delta;
			
			if (timeElapsed>=timeDelay){
				state = ZOOM_OUT;
				timeElapsed = 0;
			}
			return;
		}
		
		if (state==ZOOM_OUT){
			zoomElapsed += delta;

			cam.zoom= 0.2f + 0.8f*(zoomElapsed/zoomDelay);
			
			cam.position.x =  (x*80 + 40) + (240f-(x*80 + 40))*(zoomElapsed/zoomDelay);
			cam.position.y =  (y*80 + START_Y + 40) + (400-(y*80 + START_Y + 40))*(zoomElapsed/zoomDelay);

	//		cam.position.y = 400;
			
			if (zoomElapsed>=zoomDelay){
				zoomElapsed = 0;
				state = WAIT_ANSWER;
			}
			return;
		}
		
		if (state==WAIT_ANSWER){
			answerElapsed -= delta;
			if (answerElapsed<0){
				listener.fail();
				started = false;
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
		for (int i=0;i<ROWS;i++){
			for (int j=0;j<COLS;j++){
				if (tx==j && ty==i)
					batch.draw(atlas.findRegion("grid_pressed"), j*80, START_Y + i*80);
				else
					batch.draw(atlas.findRegion("grid"), j*80, START_Y + i*80);
				
			}
		}
		
		if (started)
		{
			if (state==WAIT_ANSWER){
				font.draw(batch, "Time Left: " + twoDForm.format(answerElapsed), 0, 750);
			}
			
		}
		
		batch.end();

	}
	
}
