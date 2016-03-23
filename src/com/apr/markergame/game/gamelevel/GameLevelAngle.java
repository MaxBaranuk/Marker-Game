package com.apr.markergame.game.gamelevel;

import com.badlogic.gdx.scenes.scene2d.Actor;


public class GameLevelAngle extends GameLevel {


	private int goalAvg;
	private int curAvg;
	
	private Actor mark, confirmButton;
	private boolean marked;
	
	public GameLevelAngle(){
		mark = new Actor();
		mark.setPosition(240, 390);
		mark.setSize(200, 30);
		mark.setOrigin(0, 15);
		confirmButton = new Actor();
		confirmButton.setPosition( 240, 100);
		confirmButton.setSize( 200, 70);
        
		
	}
	
	private void createAvg(){
		goalAvg = random.nextInt(360);
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

			if (x>confirmButton.getX() && 800-y>confirmButton.getY() && 
					x<confirmButton.getX()+confirmButton.getWidth() && 800-y<confirmButton.getY()+confirmButton.getHeight()){
				started = false;
				
				if (Math.abs(goalAvg-curAvg)<=currentFactor){
					listener.passStep(Math.abs(goalAvg-curAvg));
					
				}else{
					listener.fail();
				}
				return;
			}
			marked = true;
		
	}
	
	@Override
	public void drag(float x, float y){
		if (marked ){
			
			curAvg = (int)(Math.atan2(800-y-400,x-240)* 180f / Math.PI);
			if (curAvg<0){
				curAvg = 180+(180+curAvg);
			}
		}
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render() {
		batch.begin();
		if (started){

			batch.draw(atlas.findRegion("line_angle"), 40, 400,400,10);;
			batch.draw(atlas.findRegion("line_angle"), 40, 400,200,5,400,10,1f,1f,90);;
			batch.draw(atlas.findRegion("90"), 240,600);;
			
			batch.setColor(COLORS_RGB[0]);
			batch.draw(atlas.findRegion("angle"), mark.getX(), mark.getY(),mark.getOriginX(), mark.getOriginY(), mark.getWidth(), mark.getHeight(),1f,1f,curAvg);;
			batch.setColor(1f,1f,1f,1f);
			
			batch.draw(atlas.findRegion("grid_white_big"), confirmButton.getX(), confirmButton.getY(), confirmButton.getWidth(), confirmButton.getHeight());
			font.draw(batch, "Confirm",  confirmButton.getX()+20, confirmButton.getY()+50);
			
			font.draw(batch, "Show a " + goalAvg + "° angle", 0,700);
			//font.draw(batch, "" + curAvg, 0,750);

		}
		batch.end();
	}

}
