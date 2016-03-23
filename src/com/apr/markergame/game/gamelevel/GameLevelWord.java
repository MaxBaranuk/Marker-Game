package com.apr.markergame.game.gamelevel;

import com.apr.markergame.services.MarkerSound;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;


public class GameLevelWord extends GameLevel{


  private static final char[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
  
   private static final int GAME_Y = 300;
  private static final int WORD_Y = 600;
  private static final int SIZE_WORD = 4;
  
  private static final float MAX_TIME = 3;	
  
  private float time;
  private Array<Integer> code, shown;
  private Array<Actor> boxes;
  private int curBoxIndex;
  private BitmapFont bigFont;
  
	public GameLevelWord(){
		super();
		code = new Array<Integer>();
		shown = new Array<Integer>();
		boxes = new Array<Actor>();

		

	}
	
	public void setAssets(AssetManager manager){
		super.setAssets(manager);
        bigFont = manager.get("bigFont.fnt",BitmapFont.class);

	}

	private void createPattern(){
		code.clear();
		shown.clear();
		boxes.clear();
		for (int i=0;i<SIZE_WORD;i++){
			int selected = random.nextInt(letters.length);
			while (code.contains(selected, true)){
				selected = random.nextInt(letters.length);
			}
			code.add(selected);
			shown.add(selected);
			Actor actor = new Actor();
			actor.setPosition( i*120, GAME_Y);
			boxes.add(actor);
		}
		shown.shuffle();
		code.shuffle();
		code.reverse();

	}
	
	private void setTime(){
		time = MAX_TIME;
		
	}
	
	public void startGame(){
		started = true;
		pass = 1;
		createPattern();
		setTime();
		curBoxIndex = 0;
	}
	
	public void startPass(){
		started = true;
		pass++;
		createPattern();
		setTime();
		curBoxIndex = 0;
	}
	
	public void touch(float x, float y){
		for (int i=0;i<boxes.size;i++){
			Actor box = boxes.get(i);
			if ( x>box.getX() && y>box.getY() && x<box.getX()+100 && 800-y<box.getY()+100){
				
				if (code.get(curBoxIndex)==shown.get(i)){
					listener.playSound(MarkerSound.TICK);
					curBoxIndex++;
					box.setRotation(1);
					if (curBoxIndex>=SIZE_WORD){
						started = false;
						listener.passStep(MAX_TIME-time);
					}
				}else{
					started = false;
					listener.fail();
				}
			}
		}
	}
	
	public void update(float delta){
		if (!started) return;
		time-=delta;
		if (time<0){
			started = false;
			listener.fail();
		}
		
	}
	
	public void render(){
		batch.begin();	
		for (int i=0;i<boxes.size;i++){
			Actor box = boxes.get(i);
			if (box.getRotation()==1)
				batch.draw(atlas.findRegion("grid_green_big"), boxes.get(i).getX(), boxes.get(i).getY());
			else
				batch.draw(atlas.findRegion("grid_white_big"), boxes.get(i).getX(), boxes.get(i).getY());
			bigFont.setColor(COLORS_RGB[i%COLORS_RGB.length]);
			bigFont.draw(batch,"" + letters[shown.get(i)], boxes.get(i).getX()+20, boxes.get(i).getY()+130);
			//batch.draw(atlas.findRegion("grid_white"), boxes.get(i).getX(), WORD_Y);
			bigFont.draw(batch,"" + letters[code.get(i)], boxes.get(i).getX()+30, WORD_Y+70);
			
		}
		font.draw(batch, "Time Left: " + twoDForm.format(time), 0, 750);

		batch.end();

	}
	
}
