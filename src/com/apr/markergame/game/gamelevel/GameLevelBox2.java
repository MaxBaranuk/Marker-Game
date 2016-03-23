package com.apr.markergame.game.gamelevel;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;


public class GameLevelBox2 extends GameLevel{

	

  
  private float min, max,speed;
  private Array<Integer> order;
  private Array<Actor> boxes,shades;
  
  private static final int MIN_POSITION = 100;	
  private static final int MAX_POSITION = 400;	

  private int curBoxIndex;
  private Actor curBox,curShade;
  
	public GameLevelBox2(){
		super();
		order = new Array<Integer>();

		setOrder();
		
		boxes = new Array<Actor>();
		shades = new Array<Actor>();
		for (int i=0;i<5;i++){
			Actor actor = new Actor();
			actor.setPosition(96*i, 1000);
			boxes.add(actor);
			Actor shade = new Actor();
			shade.setPosition(96*i, MathUtils.random(MIN_POSITION, MAX_POSITION));
			shade.setSize(96, 44);
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
			actor.setY(1000);
		}
		
		shades.clear();
		for (int i=0;i<5;i++){
			Actor shade = new Actor();
			shade.setPosition(96*i, MathUtils.random(MIN_POSITION, MAX_POSITION));
			shade.setSize(96, 44);
			shades.add(shade);
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
				listener.passStep(Math.abs(shades.get(curBoxIndex).getY()-curBox.getY()));

			}else{
				started = false;
				listener.fail();

			}
		}
	}
	
	public void update(float delta){
		if (!started) return;
		curBox.translate(0, -speed);
		if (curBox.getY()+100<shades.get(curBoxIndex).getY()){
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
