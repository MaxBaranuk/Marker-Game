package com.apr.markergame.game.gamelevel;

import com.badlogic.gdx.scenes.scene2d.Actor;


public class GameLevelAverage extends GameLevel {


	private int goalAvg;
	
	private Actor mark, confirmButton;
	private boolean marked;
	private int curAvg;
	
	public GameLevelAverage(){
		mark = new Actor();
		mark.setPosition(40, 175);
		mark.setSize(25, 50);
		confirmButton = new Actor();
		confirmButton.setPosition( 240, 100);
		confirmButton.setSize( 200, 70);
        
		
	}
	
	private void createAvg(){
		goalAvg = 10+random.nextInt(60);
		mark.setPosition(40, 175);
		curAvg = 0;
	}
	
	public void startGame(){
		super.startGame();
		createAvg();
		marked = false;
	}
	
	public void startPass(){
		super.startPass();
		createAvg();
		marked = false;
	}
	
	
	@Override
	public void touch(float x, float y) {

		if (x>mark.getX() && 800-y>mark.getY() && x<mark.getX()+mark.getWidth() && 800-y<mark.getY()+mark.getHeight()*2){
			marked = true;
		}else{
			marked = false;
			if (x>confirmButton.getX() && 800-y>confirmButton.getY() && 
					x<confirmButton.getX()+confirmButton.getWidth() && 800-y<confirmButton.getY()+confirmButton.getHeight()){
				started = false;
				
				if (Math.abs(goalAvg-curAvg)<=currentFactor){
					listener.passStep(Math.abs(goalAvg-curAvg));
					
				}else{
					listener.fail();
				}
			}
		}
		
	}
	
	@Override
	public void drag(float x, float y){
		if (marked && x>40 && x<440){
			mark.translate(x-mark.getX(), 0);
			curAvg = (int)(mark.getX()-40)/4;
		}
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render() {
		batch.begin();
		if (started){
			batch.draw(atlas.findRegion("space"), 0, 800-goalAvg*8, 480, goalAvg*8);
			batch.draw(atlas.findRegion("grid_white_big"), confirmButton.getX(), confirmButton.getY(), confirmButton.getWidth(), confirmButton.getHeight());
			font.draw(batch, "Confirm",  confirmButton.getX()+20, confirmButton.getY()+50);
			
			batch.draw(atlas.findRegion("line_white"), 40, 200);
			batch.draw(atlas.findRegion("mark"), mark.getX(), 175);
			font.draw(batch, "" + curAvg, mark.getX()-20,280);
			
		}
		batch.end();
	}

}
