package com.apr.markergame.game.gamelevel;


public class GameLevelNA extends GameLevel{

	
	public GameLevelNA(){
		super();


	}
	
	public void startGame(){
		started = true;

	}
	
	public void startPass(){
		started = true;
		pass++;
	}
	
	public void touch(float x, float y){
		started = false;
		listener.fail();
	}
	
	public void update(float delta){
		if (!started) return;
	}
	
	public void render(){
	
		
		
	}
	
}
