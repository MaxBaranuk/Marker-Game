package com.apr.markergame.game;

import com.badlogic.gdx.utils.Array;

public class PackResults {

	public int freeStars;
	public int gameStars;
	public boolean locked;
	
	public Array<LevelResults> levelResults = new Array<LevelResults>();
	
	public void add(LevelResults level){
		levelResults.add(level);
	}
	
	public int getStars(){
		return freeStars + gameStars;
	}
}
