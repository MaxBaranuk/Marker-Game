package com.apr.markergame.game.gamelevel;

import com.apr.markergame.services.MarkerSound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;


public class GameLevelTimer extends GameLevel{

	private static final float MIN_TIMER= 3f;
	private static final float MAX_TIMER= 8f;

	private static final int NUM_TIMERS = 4;
	
	class Timer{
		float timer;
		boolean stopped;
		public Timer(float timer){
			this.timer = timer;
			stopped = false;
		}
	}
	private Array<Actor> boxes;
	private Array<Timer> timers;
	//private int curTimer;
	//private float sum;
	
	public GameLevelTimer(){
		super();
		timers = new Array<Timer>();
		boxes = new Array<Actor>();
		for (int i=0;i<NUM_TIMERS;i++){
			Actor box = new Actor();
			box.setPosition(100, 100 + i*150);
			box.setSize(96,44);
			box.setScale(2f);
			boxes.add(box);
		}
	}
	
	private void setTimers(){
		timers.clear();
		for (int i=0;i<NUM_TIMERS;i++){
			
			float time = 0;
			boolean validTime = true;
			do{
				time = MathUtils.random(MIN_TIMER, MAX_TIMER);			
				validTime = true;
				for (int j=0;j<timers.size;j++){
					if (Math.abs(time-timers.get(j).timer)<0.5f){
						validTime = false;
					}
				}
			}while(!validTime);
			timers.add(new Timer(time));
		}
	//	curTimer = 0;
	//	sum = 0;
	}
	
	public void startGame(){
		started = true;
		setTimers();
	}
	
	public void startPass(){
		started = true;
		pass++;
		setTimers();
	}
	
	public void touch(float x, float y){
		
		boolean finished = true;
		float sum = 0;
		for (int i=0;i<NUM_TIMERS;i++){
			Actor box = boxes.get(i);
			
			if (this.intersectsPoints(x,y,box)){
				listener.playSound(MarkerSound.TICK);

				timers.get(i).stopped = true;
			}
			if (!timers.get(i).stopped){
				finished = false;
			}else{
				sum += timers.get(i).timer;

			}
		}
		
		if (finished){
			started = false;
			listener.passStep(sum);
		}
		
	}
	
	public void update(float delta){
		if (!started) return;
		
		
		for (Timer timer: timers){
			if (!timer.stopped)
				timer.timer-=delta;
			if (timer.timer<0){
				listener.playSound(MarkerSound.EXPLOSION);
				started = false;
				listener.fail();
			}
			
		}
		
		
		
		
	}
	
	public void render(){
	//	if (!started) return;
		batch.begin();
		for (int i=0;i<timers.size;i++){
			if (timers.get(i).timer>=0){
				batch.draw(atlas.findRegion("box_" + colors[i%colors.length]), boxes.get(i).getX(), boxes.get(i).getY(), 0,0,
						boxes.get(i).getWidth(),boxes.get(i).getHeight(), boxes.get(i).getScaleX(),boxes.get(i).getScaleY(),0f );
				font.draw(batch, twoDForm.format(timers.get(i).timer), 120, 100 + i*150+50);
				
			}
		}
		batch.end();
		
	}
	
}
