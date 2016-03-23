package com.apr.markergame.game.gamelevel;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;


public class GameLevelBox extends GameLevel{


   private static final int GAME_Y = 200;

  
  private float min, max,speed;
  private Array<Integer> order;
  private Array<Actor> boxes,shades;
  
  private int curBoxIndex;
  private Actor curBox, curShade;
	public GameLevelBox(){
		super();
		order = new Array<Integer>();
		setOrder();
		
		boxes = new Array<Actor>();
		shades = new Array<Actor>();
		for (int i=0;i<5;i++){
			Actor actor = new Actor();
			actor.setPosition(96*i, 1000);
			actor.setSize(96, 43);
			boxes.add(actor);
			Actor shade = new Actor();
			shade.setPosition(96*i, GAME_Y);
			shade.setSize(96, 43);
			shades.add(shade);
		}

	}
	
	private void setOrder(){
		order.clear();
		order.add(0);
		order.add(1);
		order.add(2);
		order.add(3);
		order.add(4);
		order.shuffle();
	}
	
	private void resetBoxes(){
		for (Actor actor: boxes){
			actor.setY(800);
		}
		
	}
	
	private void startSpeed(){
		min = currentFactor;
		max = currentFactor*2;
		speed = min+random.nextInt((int)max-(int)min);		
	}

	private void incrementSpeed(){
		min = currentFactor;
		max = currentFactor*2;
		speed = MathUtils.random(min, max);		
	}
	
	private void setCurBox(){
		curBoxIndex = order.removeIndex(0);
		curBox = boxes.get(curBoxIndex);
		curShade = shades.get(curBoxIndex);

	}

	
	public void startGame(){
		super.startGame();
		startSpeed();
		setOrder();		
		resetBoxes();
		setCurBox();
	}
	
	public void startPass(){
		super.startPass();
		incrementSpeed();
		setCurBox();
	}
	
	public void touch(float x, float y){
			if (intersectsPoints(x, y, curShade)){
				if (curBox.getY()>curShade.getY()-44 && curBox.getY()<curShade.getY()+44){
						started = false;
						listener.passStep(Math.abs(curShade.getY()-curBox.getY()));

				}else{
					started = false;
					listener.fail();

				}
			}			
	}
	
	public void update(float delta){
		if (!started) return;

		curBox.translate(0, -speed);
		if (curBox.getY()+100<GAME_Y){
			started = false;
			listener.fail();
		}

	}
	
	public void render(){
	
		
		batch.begin();	
		for (int i=0;i<5;i++){
			batch.draw(atlas.findRegion("box_gray"), 96*i, shades.get(i).getY());

			batch.draw(atlas.findRegion("box_"+ colors[i]), boxes.get(i).getX(), boxes.get(i).getY());

		}
		batch.end();

	}
	
}
