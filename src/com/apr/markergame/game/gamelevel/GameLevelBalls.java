package com.apr.markergame.game.gamelevel;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;


public class GameLevelBalls extends GameLevel {

	private static final float MAX_TIME = 20f;
	private Array<Actor> boxesLeft;
	private Array<Actor> boxesRight;
	private Actor touched;
	private Actor line;
	
	private float time;
	private int total;
	
	public GameLevelBalls(){
		boxesLeft = new Array<Actor>();
		boxesRight = new Array<Actor>();
		line = new Actor();
		line.setPosition(240,100);
		line.setSize(10,600);
		
	}
	
	
	
	private void createObjects(){
		boxesLeft.clear();
		boxesRight.clear();
		int left = 10+random.nextInt(5);
		int right = 10+random.nextInt(5);
		total = left+right;
		for (int i=0;i<left;i++){
			Actor actor = new Actor();
			actor.setPosition(random.nextInt(190), 100+random.nextInt(600));
			actor.setSize(50, 50);
			while (intersectsCircles(actor,boxesLeft)){
				actor.setPosition(random.nextInt(190), 100+random.nextInt(600));
			}
			boxesLeft.add(actor);
		}
		
		for (int i=0;i<right;i++){
			Actor actor = new Actor();
			actor.setPosition(250+random.nextInt(200), 100+random.nextInt(500));
			actor.setSize(50, 50);
			while (intersectsCircles(actor,boxesRight)){
				actor.setPosition(250+random.nextInt(200), 100+random.nextInt(500));
			}

			boxesRight.add(actor);
		}
		
		time = MAX_TIME;
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
		for (Actor actor: boxesLeft){
			if (x+5>=actor.getX() && 800-y+5>=actor.getY() && x+5<=actor.getX()+actor.getWidth()+5 && 800-y+5<=actor.getY()+actor.getHeight()+5){
				touched = actor;
				return;
			}
		}
		
		for (Actor actor: boxesRight){
			if (x-5>actor.getX() && 800-y+5>actor.getY() && x-5<actor.getX()+actor.getWidth()+5 && 800-y+5<actor.getY()+actor.getHeight()+5){
				touched = actor;
				return;
			}
		}

		touched = null;
	}
	
	@Override
	public void drag(float x, float y) {
		if (touched ==null) return;
		float pX = touched.getX();
		float pY = touched.getY();
		touched.setPosition(x-25, 800-y-25);

		if (intersectsCircles(touched,boxesLeft)){
//			started = false;
//			listener.fail();
			touched.setPosition(pX, pY);
			return;
		}
		if (intersectsCircles(touched,boxesRight)){
//			started = false;
//			listener.fail();
			touched.setPosition(pX, pY);

			return;
		}
		if (intersectsRectangles(touched,line)){
//			started = false;
//			listener.fail();
			touched.setPosition(pX, pY);

			return;
		}
	
	}
	

	@Override
	public void update(float delta) {
		if (!started) return;
		time-=delta;
		int count = 0;
		for (Actor actor: boxesLeft){
			if (actor.getX()>240)
				count++;
		}
		for (Actor actor: boxesRight){
			if (actor.getX()<240)
				count++;
		}
		if (count==total){
			started = false;
			listener.passStep(0);
			return;
		}
		if (time<0){
			started = false;
			
			float avg = (float)count/(float)total;
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
			for (Actor actor: boxesLeft){
				batch.setColor(COLORS_RGB[0]);
				batch.draw(atlas.findRegion("ball"),actor.getX(), actor.getY());
				
			}
			for (Actor actor: boxesRight){
				batch.setColor(COLORS_RGB[3]);
				batch.draw(atlas.findRegion("ball"),actor.getX(), actor.getY());
				
			}
			batch.draw(atlas.findRegion("mark"),line.getX(), line.getY(),line.getWidth(),line.getHeight());
			
			font.draw(batch, "Time: " + twoDForm.format(time), 0, 750);
		}
		batch.end();
	}

}
