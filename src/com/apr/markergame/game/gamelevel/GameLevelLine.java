package com.apr.markergame.game.gamelevel;


import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameLevelLine extends GameLevel{

	private static final float START_Y = 100f;
	
	private Actor line;
	private Actor fog;	
	private Actor dropArea;
	
	public GameLevelLine(){
		super();
		fog = new Actor();
		fog.setSize(400,323);
		dropArea = new Actor();
		dropArea.setSize(400,100);
		line = new Actor();
		line.setX(240);
		line.setY(0);
		line.setWidth(400);
		fog.setX(240);
		fog.setY(250);	
		dropArea.setX(240);
		dropArea.setY(600);	

	}
	

	
	public void startGame(){
		super.startGame();
		line.setY(START_Y);	

	}
	
	public void startPass(){
		super.startPass();
		line.setY(START_Y);	
	}
	
	public void touch(float x, float y){
		started = false;
		System.out.println(dropArea.getY() + " " + line.getY());
		if (line.getY()>=dropArea.getY() && line.getY() <= dropArea.getY()+dropArea.getHeight() ){
			listener.passStep(line.getY()-dropArea.getY());
		}else{
			listener.fail();
		}
	}
	
	public void update(float delta){
		if (!started) return;
		line.translate(0, currentFactor);
		
		if (line.getY()>800){
			listener.fail();
			started = false;
		}
	}
	
	public void render(){
	
		
		batch.begin();	
		batch.draw(atlas.findRegion("dropArea"), dropArea.getX()-dropArea.getWidth()/2, dropArea.getY());

		batch.draw(atlas.findRegion("fog-c"), fog.getX()-fog.getWidth()/2, fog.getY());

		batch.draw(atlas.findRegion("line"), line.getX()-line.getWidth()/2,line.getY());
		batch.draw(atlas.findRegion("fog-d"), fog.getX()-fog.getWidth()/2, fog.getY());
		batch.end();

	}
	
}
