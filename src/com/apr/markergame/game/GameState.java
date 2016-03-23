package com.apr.markergame.game;

import com.badlogic.gdx.utils.Array;

public class GameState {

	public Array<PackResults> results;
	
	public int levelSelectedId;
	public int packSelectedId;
	
	public GameState(){
		
	}
	
	public GameState(Packs packs){
		results = new Array<PackResults>();
		restart(packs);
	}
	
	 public void restart(Packs packs){
			results.clear();
			int j=0;
			for (Pack pack: packs.packs){
				j = 1;
				PackResults packResults = new PackResults();
				packResults.locked = true;
				for (Level level: pack.levels){
					LevelResults levelResult = new LevelResults();
					
					levelResult.id = level.id;
					levelResult.level = j;
					levelResult.numStars = 0;
					levelResult.score = -1;
					levelResult.done = false;
					levelResult.locked = true;
					j++;
					packResults.add(levelResult);
				}
				results.add(packResults);
			}
			levelSelectedId = 0;
			packSelectedId = 0;
			results.get(0).levelResults.get(0).locked = false;
		}


	

}
