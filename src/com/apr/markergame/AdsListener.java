package com.apr.markergame;

public interface AdsListener {

	public void startOverlay();
	public void prepareOverlay();
	
	public void getTapjoyPoints();
	public void spendTapjoyPoints(int points);
	public void showOffers();
	public void setUpdateTapListener(UpdateTapListener listener);
}
