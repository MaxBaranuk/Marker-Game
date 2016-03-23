package com.apr.markergame.game.gamelevel;

import com.apr.markergame.services.MarkerSound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;


public class GameLevelMarker extends GameLevel {

	protected static final int MARKER_WIDTH = 56;
	protected static final int MARKER_HEIGHT = 100;

	private static final float MAX_TIME = 5f;
	private Array<Actor> markers;
	
	private float time;
	private int total;
	private int hit;
	
	public GameLevelMarker(){
		markers = new Array<Actor>();
		
	}
	
	

	
	private void createObjects(){
		markers.clear();
		total = 15+random.nextInt(5);
		for (int i=0;i<total;i++){
			Actor actor = new Actor();
			actor.setPosition(random.nextInt(480-MARKER_WIDTH), 100+random.nextInt(550));
			actor.setSize(MARKER_WIDTH, MARKER_HEIGHT);
			actor.setVisible(true);
			actor.setRotation(0);
			actor.setColor(COLORS_RGB[random.nextInt(COLORS_RGB.length)]);
			while (intersectsRectangles(actor,markers)){
				actor.setPosition(random.nextInt(480-MARKER_WIDTH), 100+random.nextInt(550));
			}
			markers.add(actor);
		}
		
		time = MAX_TIME;
		hit = 0;
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
		for (Actor actor: markers){
			if (actor.isVisible() && intersectsPoints(x,y,actor) && actor.getRotation()==0f){
				//actor.setVisible(false);
				actor.setRotation(0.5f);
				listener.playSound(MarkerSound.BALLOON);

				hit++;
				if (hit==total){
					started = false;
					listener.passStep(0f);
				}
				return;
			}
		}
		

	}
	

	@Override
	public void update(float delta) {
		if (!started) return;
		
		for (Actor balloon: markers){
			if (balloon.getRotation()>0){
				balloon.setRotation(balloon.getRotation()-delta);

				if (balloon.getRotation()<0){
					balloon.setRotation(0);
					balloon.setVisible(false);
				}
			}
		}
		
		time-=delta;
		if (time<0){
			started = false;
			
			float avg = (float)hit/(float)total;
			if (avg>=currentFactor){
				listener.passStep(1f-avg);
			}else{
				listener.fail();
			}
			
		}
	}

	@Override
	public void render() {
		batch.begin();
		if (started){
			for (Actor actor: markers){
				if (actor.isVisible()){
					batch.setColor(actor.getColor());	
					if (actor.getRotation()>0.4f){						
						batch.draw(atlas.findRegion("balloon"),actor.getX(), actor.getY(),actor.getWidth(),actor.getHeight());
					}else if (actor.getRotation()>0.2){
						batch.draw(atlas.findRegion("balloon-explode-1"),actor.getX(), actor.getY(),actor.getWidth(),actor.getHeight());						
					}
					else if (actor.getRotation()>0.0000001){
						batch.draw(atlas.findRegion("balloon-explode-2"),actor.getX(), actor.getY(),actor.getWidth(),actor.getHeight());
					}else{
						batch.draw(atlas.findRegion("balloon"),actor.getX(), actor.getY(),actor.getWidth(),actor.getHeight());
					}
					
				}
			}
			batch.setColor(Color.WHITE);
			font.draw(batch, "Time: " + twoDForm.format(time), 0, 750);
		}
		batch.end();
	}

}
