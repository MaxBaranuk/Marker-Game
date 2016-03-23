package com.apr.markergame.game.gamelevel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class GameLevelBall extends GameLevel {

	private static final float MAX_TIME = 30f;
	private int goalArea;
	
	private Actor ball;
	private float acceleration;
	private float time,insideTime,goalTime;
	
	public GameLevelBall(){
        
		ball = new Actor();
		ball.setSize(50, 50);
	}
	
	private void createObjects(){
		ball.setPosition(random.nextInt(480-(int)ball.getWidth()), 400);
		goalArea = random.nextInt(480-(int)(ball.getWidth()*1.5f));
		acceleration = 0;
		time = MAX_TIME;
		insideTime = 0;
		goalTime = currentFactor;
	}
	
	public void startGame(){
		super.startGame();
		createObjects();
	}
	
	public void startPass(){
		super.startPass();
		createObjects();
	}
	
	
	@Override
	public void touch(float x, float y) {

		
	}
	

	@Override
	public void update(float delta) {
		if (!started) return;
		time -= delta;
		if (ball.getX()>goalArea+10 && ball.getX()+ball.getWidth()<goalArea+80){
			insideTime+=delta;
		}else{
			insideTime = 0;
		}
		if (insideTime>=goalTime){
			started = false;
			listener.passStep(MAX_TIME-time);
		}
		
		if (time<0){
			started = false;
			listener.fail();
		}
		
		acceleration = acceleration*0.8f - Gdx.input.getAccelerometerX();
		ball.translate(acceleration, 0);
		ball.setRotation( 360f-(ball.getX()%100)*3.6f);
		if (ball.getX()<0) ball.setX(0);
		if (ball.getX()+ball.getWidth()>480) ball.setX(480-ball.getWidth());
		
	}

	@Override
	public void render() {
		batch.begin();
		if (started){
			batch.setColor(COLORS_RGB[0]);
			batch.draw(atlas.findRegion("ball"), ball.getX(), ball.getY(),ball.getWidth()/2,ball.getHeight()/2, ball.getWidth(),ball.getHeight(), 1f,1f, ball.getRotation(), false);
			batch.setColor(1f,1f,1f,1f);
			
			batch.draw(atlas.findRegion("goal-area"), goalArea, ball.getY());
			batch.draw(atlas.findRegion("line_white"), 0, ball.getY()-10,480,10);
			batch.draw(atlas.findRegion("line_white"), 0, ball.getY()+ball.getHeight(),480,10);
			
			font.draw(batch, "Time: "  + twoDForm.format(time), 0, 750);
			font.draw(batch, "Time in area: "  + twoDForm.format(insideTime), 0, 700);
			font.draw(batch, "Goal time: "  + goalTime, 0, 650);

		}
		batch.end();
	}

}
