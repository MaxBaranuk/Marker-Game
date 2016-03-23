package com.apr.markergame.game;




public class World {
    
	public Packs packs;
	public GameState gameState;
	public long startTime;
	
    public World(Packs packs, GameState gameState) {
    	
    	this.packs = packs;
        this.gameState = gameState;
        startTime = System.currentTimeMillis();
        
    }
    
    public boolean spent30seconds(){
    	return System.currentTimeMillis()-startTime>=30000;
    }

    public boolean spent5Minutes(){
    	return System.currentTimeMillis()-startTime>=300000;
    }

   public void restart(){
	   gameState.restart(packs);
   }
   
   public PassedLevel getNextLevel(){
	   if (gameState.packSelectedId+1>=packs.packs.size){
		   return PassedLevel.NO_MORE_LEVELS;
	   }
	   PackResults packResult = getPackResult();
	   if (gameState.levelSelectedId+1>=packResult.levelResults.size){
		   PackResults nextPackResult = getPackResult(gameState.packSelectedId+1);
		   if (nextPackResult.locked){
			   return PassedLevel.NOT_ENOUGH_STARS;
		   }else{
			   gameState.packSelectedId++;
			   gameState.levelSelectedId = 0;
			   return PassedLevel.NEXT_LEVEL;
		   }
	   }else{
		   LevelResults nextLevelResult = getLevelResult(gameState.packSelectedId,gameState.levelSelectedId+1);
		   if (nextLevelResult.locked){
			   return PassedLevel.NOT_3_STARS;
		   }else{
			   gameState.levelSelectedId++;
			   return PassedLevel.NEXT_LEVEL;
		   }
	   }
   }
   
  /* public PassedLevel setNextLevel(){
		PackResults currentPackResult = getPackResult();
		Pack currentPack = getPack();
		LevelResults currentLevel = getLevelResult();
	    if (currentLevel.numStars<3){
	    	return PassedLevel.NOT_ENOUGH_STARS;
	    }
	   
		if (gameState.levelSelectedId+1<currentPackResult.levelResults.size){
			gameState.levelSelectedId++;
			LevelResults newLevel = getLevelResult();
			
			newLevel.locked = false;
			if (currentPackResult.locked && currentPackResult.getStars()>=currentPack.stars){
				currentPackResult.locked = false;
				gameState.packSelectedId++;
				gameState.levelSelectedId = 0;
				if (gameState.packSelectedId<packs.packs.size){
					LevelResults nextLevel = getLevelResult();
					nextLevel.locked = false;
					return PassedLevel.NEXT_PACK;
				}else{
					return PassedLevel.NO_MORE_LEVELS;
				}
			}else{
				return PassedLevel.NEXT_LEVEL;
			}
		}else{
			if (currentPackResult.getStars()>=currentPack.stars && currentPackResult.locked){
				currentPackResult.locked = false;
				gameState.packSelectedId++;
				gameState.levelSelectedId = 0;
				if (gameState.packSelectedId<packs.packs.size){
					LevelResults newLevel = getLevelResult();
					newLevel.locked = false;
					return PassedLevel.NEXT_PACK;
				}else{
					return PassedLevel.NO_MORE_LEVELS;
				}
			}else{
				return PassedLevel.NOT_ENOUGH_STARS;
			}
		}

	   
	   
   }*/
    
	public void passLevel(int numStars, float score){
		PackResults currentPackResult = getPackResult();
		LevelResults currentLevelResult = getLevelResult();
		
		//Increment current level
		if (currentLevelResult.numStars<numStars){
			currentPackResult.gameStars += numStars-currentLevelResult.numStars;
			currentLevelResult.numStars = numStars;
			currentLevelResult.score = score;
			
			
			currentLevelResult.done = true;
			
			if (currentLevelResult.numStars>=3){
				if (gameState.levelSelectedId+1>=currentPackResult.levelResults.size){
					
					if (gameState.packSelectedId+1 >= packs.packs.size){
						return;
					}
					
					
				}else{
					LevelResults newLevel = getLevelResult(gameState.packSelectedId,gameState.levelSelectedId+1);
					newLevel.locked = false;
					
					
				}
				PackResults oldPack = getPackResult();
				PackResults newPack = getPackResult(gameState.packSelectedId+1);
				Pack pack = getPack();
				if (oldPack.getStars()>=pack.stars){
					newPack.locked = false;
					LevelResults newLevel = getLevelResult(gameState.packSelectedId+1,0);
					newLevel.locked = false;
				}

			}
		}
		
		
		
		
	
	}

    
    
/*	private boolean allUnlocked(PackResults pack){
		for (LevelResults level: pack.levelResults){
			if (level.locked) return false;
		}
		return true;
	}*/
    
	public boolean firstThreeLevelsPassed(){
		if (gameState.packSelectedId>=1) return true;
		
		boolean firstThree = false;
		PackResults results = getPackResult();
		firstThree = !results.levelResults.get(0).locked && !results.levelResults.get(1).locked && !results.levelResults.get(2).locked ;
		
		return firstThree;
	}
	
	public void unlockPack(){
		PackResults currentPackResult = getPackResult();
		if (currentPackResult.locked){
			currentPackResult.locked = false;
			if (gameState.packSelectedId+1<packs.packs.size){
				gameState.packSelectedId++;
				gameState.levelSelectedId = 0;
				LevelResults nextLevel = getLevelResult();
				nextLevel.locked = false;
			}
		}
	}

	public void unlockPack(int pack){
		PackResults currentPackResult = getPackResult(pack);
		if (currentPackResult.locked){
			currentPackResult.locked = false;
			if (gameState.packSelectedId+1<packs.packs.size){
				gameState.packSelectedId++;
				gameState.levelSelectedId = 0;
				LevelResults nextLevel = getLevelResult();
				nextLevel.locked = false;
			}
		}
	}

	
	public boolean canUnlockPack(){
		return (getPackResult().getStars()>=getPack().stars);
	}

	public boolean canUnlockPack(int packId){
		return (getPackResult(packId).getStars()>=getPack(packId).stars && getPackResult(packId).locked);
	}

	
    private LevelResults getLevelResult(){
    	return gameState.results.get(gameState.packSelectedId).levelResults.get(gameState.levelSelectedId);
    }

    private LevelResults getLevelResult(int pack, int level){
    	return gameState.results.get(pack).levelResults.get(level);
    }

    
    public Level getLevel(){
    	return packs.packs.get(gameState.packSelectedId).levels.get(gameState.levelSelectedId);
    }

    public PackResults getPackResult(){
    	return gameState.results.get(gameState.packSelectedId);
    }


    
    private Pack getPack(){
    	return packs.packs.get(gameState.packSelectedId);
    }

    
  //  private LevelResults getLevelResult(int packId, int levelId){
  //  	return gameState.results.get(packId).levelResults.get(levelId);
  //  }

    public PackResults getPackResult(int packId){
    	return gameState.results.get(packId);
    }

    private Pack getPack(int packId){
    	return packs.packs.get(packId);
    }
    
    public void addStars(int stars){
    	PackResults packResults = getPackResult();
    	int currentStars = packResults.freeStars + packResults.gameStars;
    	Pack pack = getPack();
    	int goalStars = 20;
    	if (goalStars-currentStars>=stars){
    		packResults.freeStars += stars;
    	}else{
    		packResults.freeStars += (goalStars-currentStars);
    		
    	}
    	if (packResults.getStars()>= pack.stars){
    		PackResults newPack = getPackResult(gameState.packSelectedId+1);
    		newPack.locked = false;
    		LevelResults newResults = getLevelResult(gameState.packSelectedId+1,0);
    		newResults.locked = false;
    	}
    }



}