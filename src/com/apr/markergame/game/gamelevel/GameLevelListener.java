package com.apr.markergame.game.gamelevel;

import com.apr.markergame.services.MarkerSound;

public interface GameLevelListener{
	public void passStep(float score);
	public void fail();
	public void playSound(MarkerSound sound);
}