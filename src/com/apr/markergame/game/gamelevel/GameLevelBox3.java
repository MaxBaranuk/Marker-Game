package com.apr.markergame.game.gamelevel;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;


public class GameLevelBox3 extends GameLevel{

	  private static final int MIN_POSITION = 100;	
	  private static final int MAX_POSITION = 400;	

//   private static final int GAME_Y = 200;

  
  private float min, max,speed;
  private Array<Integer> order;
  private Array<Actor> boxes,shades;
  
  private int hits;
  private float distance;
  private boolean[] hit;
  
  
	public GameLevelBox3(){
		super();
		hits = 0;
		distance = 0;
		order = new Array<Integer>();
		setOrder();
		
		boxes = new Array<Actor>();
		shades = new Array<Actor>();
		hit = new boolean[5];
		for (int i=0;i<5;i++){
			Actor actor = new Actor();
			actor.setPosition(96*i, 1000);
			actor.setSize(96, 43);
			boxes.add(actor);
			Actor shade = new Actor();
			shade.setPosition(96*i, MathUtils.random(MIN_POSITION, MAX_POSITION));
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
		int i=0;
		for (Actor shade: shades){
			shade.setPosition(96*i, MathUtils.random(MIN_POSITION, MAX_POSITION));
			i++;
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
	
//	private void setCurBox(){
//		curBoxIndex = order.removeIndex(0);
//		curBox = boxes.get(curBoxIndex);
//		curShade = shades.get(curBoxIndex);

//	}

	
	public void startGame(){
		super.startGame();
		startSpeed();
		setOrder();		
		resetBoxes();
//		setCurBox();
		hits =0;
		distance = 0;
		hit = new boolean[5];
	}
	
	public void startPass(){
		super.startPass();
		incrementSpeed();
//		setCurBox();
		hits = 0;
		distance = 0;
		hit = new boolean[5];
	}
	
	public void touch(float x, float y){
		for (int i=0;i<shades.size;i++){
			if (intersectsPoints(x, y, shades.get(i))){
				if (boxes.get(i).getY()>shades.get(i).getY()-44 && boxes.get(i).getY()<shades.get(i).getY()+44){
					hits++;
					hit[i] = true;
					distance +=  Math.abs(shades.get(i).getY()-boxes.get(i).getY());
					if (hits>=shades.size){
						started = false;
						listener.passStep(distance/shades.size);
					}

				}else{
					started = false;
					listener.fail();

				}
				return;
			}			
		}
	}
	
	public void update(float delta){
		if (!started) return;
		for (int i=0;i<boxes.size;i++){
			Actor box = boxes.get(i);
			if (!hit[i]){
				box.translate(0, -speed);
				if (box.getY()+100<0){
					started = false;
					listener.fail();
				}
				
			}
			
		}
	}
	
	public void render(){
	
		if (!started) return;
		batch.begin();	
		for (int i=0;i<5;i++){
			batch.draw(atlas.findRegion("box_gray"), 96*i, shades.get(i).getY());

			batch.draw(atlas.findRegion("box_"+ colors[i]), boxes.get(i).getX(), boxes.get(i).getY());

		}
		batch.end();

	}
	
}
