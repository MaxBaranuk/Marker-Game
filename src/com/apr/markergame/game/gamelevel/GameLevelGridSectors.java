package com.apr.markergame.game.gamelevel;

import com.badlogic.gdx.utils.Array;

public class GameLevelGridSectors extends GameLevel{

	private static final int SHOW = 1;
	private static final int PLAY = 2;
	private int state;

	
	private static final float ANSWER_TIME = 10f;
	private static final float INITIAL_DELAY_SECTOR = 3f;
	private static final float DECREMENT_DELAY_SECTOR = 0.2f;	
	private float delay;
	private int totalSum;
	
	private static final int COLS = 12;
	private static final int ROWS = 12;
	private static final int NUM_SECTORS = 4;
	
	class Sector{
		Array<Integer> list;
		public Sector(){
			list = new Array<Integer>();
		}
		
	}
	
	private Array<Sector> sectors;
	private int curSector;

	public GameLevelGridSectors(){
		super();
		sectors  = new Array<Sector>();
		
	}
	
	private void createPathSectors(){
		sectors.clear();

		for (int i=0;i<NUM_SECTORS;i++){
			Sector sector = new Sector();
			int numPoints = 1+random.nextInt(10);
			totalSum+=numPoints;
			for (int j=0;j<numPoints;j++){
				int x = random.nextInt(COLS);
				int y = i*(COLS/NUM_SECTORS)+random.nextInt(COLS/NUM_SECTORS);
				int position = y*COLS + x;
				while (sector.list.contains(position, true)){
					x = random.nextInt(COLS);
					y = i*(COLS/NUM_SECTORS)+random.nextInt(COLS/NUM_SECTORS);
					position = y*COLS + x;					
				}
				sector.list.add(position);
				
				
			}
			sectors.add(sector);

		}
		System.out.println(totalSum);

	}
	
	public void startGame(){
		started = true;
		pass = 1;
		state = SHOW;
		totalSum = 0;
		curSector = 0;
		createPathSectors();
		delay = INITIAL_DELAY_SECTOR;
	}
	
	public void startPass(){
		started = true;
		pass++;
		state = SHOW;
		totalSum = 0;
		curSector = 0;
		createPathSectors();
		delay = INITIAL_DELAY_SECTOR - DECREMENT_DELAY_SECTOR*pass;
	}
	
	public void touch(float screenX, float screenY){
		
		if (state==PLAY){
			int tx = (int) (screenX / 80f);
			int ty = (int) ((800-screenY-50)/80f);
			if (totalSum==ty*6+tx+4){
				started = false;
				listener.passStep(ANSWER_TIME-delay);
			}else{
				started = false;
				listener.fail();		
			}
			
		}
	}
	
	public void update(float delta){
		if (!started) return;
		if (state==SHOW){		
				delay-=delta;
				if (delay<0){
					curSector++;
					delay = INITIAL_DELAY_SECTOR - DECREMENT_DELAY_SECTOR*pass;
					if (curSector>=NUM_SECTORS){
						delay = ANSWER_TIME;
						state = PLAY;
					}
				}
			
				return;
		}
		
		if (state==PLAY){
			delay-=delta;
			if (delay<0){
				started = false;
				listener.fail();
			}
		
			return;
		}
	}
	
	public void render(){
		
		batch.begin();	
		batch.setColor(1f, 1f, 1f, 1f);
		
		if (started)
		{
			if (state==SHOW){
				for (int i=0;i<ROWS*COLS;i++){
					batch.draw(atlas.findRegion("grid_small"),  (i%ROWS)*40, 200 + (i/ROWS)*40);
				}

				Sector sector = sectors.get(curSector);
				for (Integer i: sector.list){
					batch.draw(atlas.findRegion("grid_small_pressed"),  (i%ROWS)*40, 200 + (i/ROWS)*40);
				}
				
			}

			
			if (state==PLAY){

				for (int i=0;i<48;i++){
					batch.draw(atlas.findRegion("grid"),  (i%6)*80, 50 + (i/6)*80);
					font.draw(batch,"" + (i+4), (i%6)*80+20, 50 + (i/6)*80+80);

				}
				font.draw(batch, "Time Left: " + twoDForm.format(delay), 0, 750);

			}
			
		}
		
		batch.end();

	}
	
}
