package com.apr.markergame.screens;

import com.apr.markergame.MarkerGame;
import com.apr.markergame.game.Level;
import com.apr.markergame.game.LevelResults;
import com.apr.markergame.game.Pack;
import com.apr.markergame.game.PackResults;
import com.apr.markergame.services.MarkerSound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;



public class SelectLevelScreen extends AbstractGameScreen {

	
    Array<Pack> packs;
    Array<PackResults> results;
    Label infoLabel;
    //TextButton unlockButton;
	    
		public SelectLevelScreen(
	        MarkerGame game )
	    {
	        super( game );
	    }

	    @Override
	    public void show()
	    {
	        super.show();
	        
	        
	        Table table = getTable();
	        
	        table.setFillParent(true);
	        PagedScrollPane scroll = new PagedScrollPane();
	        scroll.setFlingTime(0.1f);
	        scroll.setPageSpacing(25);
	        
	        
	        packs = world.packs.packs;
	        results = world.gameState.results;
	        
	        for (int l = 0; l < packs.size; l++) {
	        	
	               Pack pack = packs.get(l);
	               PackResults packResults = results.get(l);   
	               Array<Level> levelInfo = pack.levels;
	               Array<LevelResults> levelResults = packResults.levelResults;

	        	
	               Table levels = new Table().padBottom(100);
//	               levels.defaults().expand().fill();
	               levels.columnDefaults(2);
	               levels.add(new Label("Pack " + (l+1),getSkin(),"title-text")).colspan(2);
	              // levels.row();
	              // levels.add(new Label("Current Stars: " + packResults.getStars(),getSkin(),"text"));
	              // levels.row();
	              // infoLabel = new Label("",getSkin(),"text");
	              // levels.add(infoLabel);
	               levels.row();
	               
	              // if (packResults.getStars()<pack.stars){
		          //     infoLabel.setText("Stars to unlock level: " + (pack.stars-packResults.getStars()) );
	              // }else{
	            	//   if (packResults.locked){
	            		   
	            	//   }else{
	            	//	  infoLabel.setText("Level unlocked");
	            	//   }
	               //}
	               
	               
	              // levels.row();
	               
	       //        if (world.getPackResult(l).locked){
	            	   
	       //     	   if (world.canUnlockPack(l)){
	       //     		   unlockButton = new TextButton("Unlock pack",getSkin());
	       //     		   unlockButton.addListener( new MyUnlockClickListener(l));
 	       // 	           levels.add(unlockButton);

	      //      	   }
	       //        }
	               int levelId = 0;
	               for (int y = 0; y < levelInfo.size/2; y++) {
	                   levels.row();
	                   for (int x = 0; x < levelInfo.size/2; x++) {
	                           levels.add(getLevelButton(l,levelId,levelInfo.get(levelId), levelResults.get(levelId))).expand().fill();
	     	                   levelId++;

	                    }
	               }
 	               levels.row();
	  	          TextButton buttonBack = new TextButton("Back",getSkin());
		          buttonBack.addListener( new ClickListener() {
		             @Override
		             public void clicked (InputEvent event, float x, float y)
		             {
		             	game.setScreen(new MenuScreen(game));
		             }
		         } );
		         levels.add(buttonBack);
		         Label starsLabel = new Label(" Stars:\n  " + Math.min(20,packResults.getStars()) + "/ 20",getSkin(),"title-text");
		        
		         if (packResults.getStars()>=17)
		        	 starsLabel.setColor(getSkin().getColor("green")) ;
		         else
		        	 starsLabel.setColor(getSkin().getColor("red")) ;

		         levels.add( starsLabel);
		         
	               scroll.addPage(levels);
	               
	               scroll.setScrollPercentX(100);
	               
	         }
	        
	        table.add(scroll).expand().fill();
	         table.row();
	         

	    }
	    
	    
	    /**
	         * Creates a button to represent the level
	         *
	         * @param level
	         * @return The button to use for the level
	         */
	        public Button getLevelButton(int packId, int levelId, Level level, LevelResults results) {
	            Button button = new Button(getSkin());
	            ButtonStyle style = button.getStyle();
	            style.up =  style.down = null;
	            // Create the label to show the level number
//	            Label label = new Label(Integer.toString(level.id), getSkin());
	          //  label.setFontScale(2f);
//	            label.setAlignment(Align.center);      
	            // Stack the image and the label at the top of our button

	            if (results.locked)
	            	button.stack(new Image(getSkin().getDrawable("level" + level.id)), new Image(getSkin().getDrawable("grid_transparent")), new Image(getSkin().getDrawable("lock")));
	            
	            else
	            	button.stack(new Image(getSkin().getDrawable("level" + level.id)), new Image(getSkin().getDrawable("grid_transparent")));
	            
	            // Randomize the number of stars earned for demonstration purposes
	            int stars = results.numStars;
	            Table starTable = new Table();
	            starTable.defaults().pad(5);
	            if (stars >= 0) {
	                for (int star = 0; star < 5; star++) {
	                    if (stars > star) {
	                        starTable.add(new Image(getSkin().getDrawable("star_on"))).width(20).height(20);
	                    } else {
	                        starTable.add(new Image(getSkin().getDrawable("star_off"))).width(20).height(20);
	                    }
	                }          
	            }
	            button.row();
	            button.add(starTable).height(30);
	            button.setName(Integer.toString(level.id));
	            if (!results.locked)
	            	button.addListener(new MyClickListener(packId,levelId));    
	            return button;
	        }

	       


			@Override
			protected void backAction() {
				game.setScreen(new MenuScreen(game));
				
			}

			@Override
			protected void touch(float x, float y) {}

	        
			 /**
             * Handle the click - in real life, we'd go to the level
             */
            class MyClickListener extends ClickListener {
            	
            	int pack;
            	int level;
            	
            	public MyClickListener(int pack, int level){
            		this.pack = pack;
            		this.level = level;
            	}
            	
                @Override
                public void clicked (InputEvent event, float x, float y) {
                	world.gameState.levelSelectedId = level;
                	world.gameState.packSelectedId = pack;
	            	game.getSoundManager().play(MarkerSound.PEN);

                	game.setScreen(new LevelScreen(game));
                }
            };
            
            class MyUnlockClickListener extends ClickListener {
            	
            	int level;
            	
            	public MyUnlockClickListener(int level){
            		this.level = level;
            	}
            	
                @Override
                public void clicked (InputEvent event, float x, float y) {
	             	world.unlockPack(level);
	             	game.setScreen(new SelectLevelScreen(game));
                }
            };
            
}
