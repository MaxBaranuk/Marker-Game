package com.apr.markergame.game.gamelevel;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;


public class GameLevelShuffle extends GameLevel {

	private static final int NUM_SHUFFLES = 5;

	private static final int SHOW_KEY = 1;
	private static final int HIDE_KEY = 2;
	private static final int SHUFFLE = 3;
	private static final int PLAY = 4;

	private int state;
	
	private float time;
	
	private Stage stage;
	
	private static Array<Vector2> positions;
	private static Array<Integer> boxesIndex;
	private static Array<Actor> boxes;

	private int key;
	private int curShuffle;
	
	public GameLevelShuffle(){
		stage = new Stage();
		positions = new Array<Vector2>();
		boxesIndex = new Array<Integer>();
		boxes = new Array<Actor>();
		
		for (int i=0;i<4;i++){
			positions.add(new Vector2(150,200+i*150));
			positions.add(new Vector2(300,200+i*150));

		}
		for (int i=0;i<8;i++){
			boxesIndex.add(i);
			Actor actor = new Actor();
			actor.setPosition(positions.get(i).x, positions.get(i).y);
			actor.setSize(40,40);
			stage.addActor(actor);
			boxes.add(actor);
		}
	}
	
	private void createObjects(){
		
		key =  random.nextInt(8);
		state = SHOW_KEY;
		time = currentFactor;
		curShuffle = 0;
	}
	
	private void shuffle(){
		boxesIndex.shuffle();
		int i=0;
		for (Actor actor: boxes){
			actor.addAction(Actions.moveTo(positions.get(boxesIndex.get(i)).x, positions.get(boxesIndex.get(i)).y, currentFactor));			
			i++;
		}
		
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
		int i=0;
		for (Actor actor: boxes){
			if (x>actor.getX() && 800-y>actor.getY() && x<actor.getX()+actor.getWidth() && 800-y<actor.getY()+actor.getHeight()){
				started = false;
				if (i==key){
					listener.passStep(TIME_TO_ANSWER-time);
				}else{
					listener.fail();
				}
			}
			i++;
		}
		
	}
	

	@Override
	public void update(float delta) {
		if (!started) return;
		stage.act();
		
		if (state==SHOW_KEY){
			time-=delta;
			if (time<0){
				time = currentFactor;
				state = HIDE_KEY;
			}
			return;
		}
		if (state==HIDE_KEY){
			time-=delta;
			if (time<0){
				time = currentFactor;
				state = SHUFFLE;
				shuffle();
			}
			return;
		}
		
		if (state==SHUFFLE){
			time-=delta;
			if (time<0){
				time = currentFactor;
				curShuffle++;
				if (curShuffle>=NUM_SHUFFLES){
					state = PLAY;
					time = TIME_TO_ANSWER;
				}else
					shuffle();
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

	@Override
	public void render() {
		batch.begin();
		if (started){
			for (int i=0;i<boxes.size;i++){
				
				
				
				batch.draw(atlas.findRegion("grid_small_pressed"),boxes.get(i).getX(), boxes.get(i).getY());
				
				if (state==SHOW_KEY && key==i){
					batch.draw(atlas.findRegion("grid_small"),boxes.get(i).getX(), boxes.get(i).getY());
				}
			}
			if (state == PLAY)
				font.draw(batch, "Time: " + twoDForm.format(time), 0, 750);
		}
		batch.end();
	}

}
