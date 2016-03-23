package com.apr.markergame.screens;

import com.apr.markergame.MarkerGame;
import com.apr.markergame.services.MarkerSound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;



public class MenuScreen extends AbstractGameScreen {

	
	
		YesNoDialog yesNodialog,exitDialog;
	    
		TextButton newGameButton,exitButton;
		
		public MenuScreen(
	        MarkerGame game )
	    {
	        super( game );
	    }

	    @Override
	    public void show()
	    {
	        super.show();
//	        game.getAdListener().prepareOverlay();
	        // retrieve the default table actor
	        final Table table = super.getTable();
	        table.columnDefaults( 1);
	      //  Texture back = new Texture( "back" + game.getPreferencesManager().getBackground() + ".jpg" );

	        // set the linear texture filter to improve the image stretching
	      //  back.setFilter( TextureFilter.Linear, TextureFilter.Linear );
	   // 	 TextureRegion backRegion = new TextureRegion( back, 0, 0, 480, 800 );

	    //    getTable().setBackground(new TextureRegionDrawable(backRegion));
	        
	        
	        Label title = new Label("", getSkin(),"title");
	        
	        table.add(title).size(400, 200).center().colspan(2);
	        table.row();

	        newGameButton = new TextButton( "New game", getSkin() );
	        newGameButton.getLabel().getStyle().fontColor = getSkin().getColor("yellow");
	        newGameButton.addListener( new ClickListener() {
	            @Override
	            public void clicked (InputEvent event, float x, float y)
	            {
	            	newGameButton.getLabel().getStyle().fontColor = getSkin().getColor("yellow");
	            	game.getSoundManager().play(MarkerSound.PEN);
	            	yesNodialog.show(table.getStage());
	            }
	        } );
	        table.add( newGameButton ).size( 450, 60 ).uniform().colspan(2);
	        table.row();

	        // register the button "start game"
	        TextButton continueGameButton = new TextButton( "Continue game", getSkin() );
	        continueGameButton.getLabel().getStyle().fontColor = getSkin().getColor("purple");
	        continueGameButton.addListener( new ClickListener() {
	            @Override
	            public void clicked (InputEvent event, float x, float y)
	            {
	            	game.getSoundManager().play(MarkerSound.PEN);
	            	game.setScreen(new LevelScreen(game));

	            }
	        } );
	        table.add( continueGameButton ).size( 450, 60 ).uniform().colspan(2);
	        table.row();

	        
	        // register the button "options"
	        TextButton selectLevelButton = new TextButton( "Select Level", getSkin() );
	        selectLevelButton.getLabel().getStyle().fontColor = getSkin().getColor("blue");
	        selectLevelButton.addListener( new ClickListener() {
	            @Override
	            public void clicked (InputEvent event, float x, float y)
	            {
	            	table.addAction(Actions.sequence(Actions.moveBy(480,0f,0.5f), new Action(){

						@Override
						public boolean act(float delta) {
			            	game.getSoundManager().play(MarkerSound.PEN);
			            	runOverlay();
			            	game.setScreen(new SelectLevelScreen(game));
							return false;
						}
	            		
	            	}));

	            }
	        } );
	        table.add( selectLevelButton ).size( 500, 60 ).uniform().colspan(2);
	        table.row();

	        
	        TextButton optionsButton = new TextButton( "Options", getSkin() );
	        optionsButton.getLabel().getStyle().fontColor = getSkin().getColor("green");
	        optionsButton.addListener( new ClickListener() {
	            @Override
	            public void clicked (InputEvent event, float x, float y)
	            {
	            	table.addAction(Actions.sequence(Actions.moveBy(480,0f,0.5f), new Action(){

						@Override
						public boolean act(float delta) {
			            	game.getSoundManager().play(MarkerSound.PEN);
			            	game.setScreen(new OptionsScreen(game));
							return false;
						}
	            		
	            	}));
	            }
	        } );
	        table.add( optionsButton ).size(500,60).uniform().colspan(2);
	        table.row();
	        
	        exitButton = new TextButton( "Exit", getSkin() );
	        exitButton.getLabel().getStyle().fontColor = getSkin().getColor("red");
	        exitButton.addListener( new ClickListener() {
	            @Override
	            public void clicked (InputEvent event, float x, float y)
	            {
	            	exitButton.getLabel().getStyle().fontColor = getSkin().getColor("red");
	            	game.getSoundManager().play(MarkerSound.PEN);
	            	exitDialog.show(getTable().getStage());
	            }
	        } );
	        table.add( exitButton ).size(500,60).uniform().colspan(2);
	        table.row();
	        

	        
	        TextButton freeStarsButton = new TextButton( "Free \nStars", getSkin() );
	        freeStarsButton.getLabel().getStyle().fontColor = getSkin().getColor("red");
	        freeStarsButton.addListener( new ClickListener() {
	            @Override
	            public void clicked (InputEvent event, float x, float y)
	            {
	            	game.getSoundManager().play(MarkerSound.PEN);
	            	game.setScreen(new FreeStarsScreen(game));

	            }
	        } );
	        table.add( freeStarsButton ).size(200,130).spaceRight(100);
	        
	        TextButton rateAppButton = new TextButton( "Help", getSkin() );
	        rateAppButton.getLabel().getStyle().fontColor = getSkin().getColor("red");
	        rateAppButton.addListener( new ClickListener() {
	            @Override
	            public void clicked (InputEvent event, float x, float y)
	            {
	            	game.getSoundManager().play(MarkerSound.PEN);
	            	game.setScreen(new HelpScreen(game));

	            }
	        } );
	        table.add(rateAppButton ).size(200,130);
	        
	        TextButton yesButton = new TextButton("Yes",getSkin());
	        yesButton.getLabel().getStyle().fontColor = getSkin().getColor("blue");
	        yesButton.addListener( new ClickListener() {
	            @Override
	            public void clicked (InputEvent event, float x, float y)
	            {
	            	runOverlay();

	            	world.restart();
	            	game.getProfileManager().persist();
	            	game.setScreen(new LevelScreen(game));
	            }
	        } );
	        
	        TextButton noButton = new TextButton("No",getSkin());
	        noButton.getLabel().getStyle().fontColor = getSkin().getColor("red");
	        noButton.addListener( new ClickListener() {
	            @Override
	            public void clicked (InputEvent event, float x, float y)
	            {
	            	yesNodialog.hide();
	            }
	        } );
	        
	         yesNodialog = new YesNoDialog("Start",getSkin(), "All progress will be deleted, are you sure?", 
						        		 new ClickListener() {
							            @Override
							            public void clicked (InputEvent event, float x, float y)
							            {
							            	world.restart();
							            	game.getProfileManager().persist();
							            	game.setScreen(new LevelScreen(game));
							            }
							        	},
							        	new ClickListener() {
								            @Override
								            public void clicked (InputEvent event, float x, float y)
								            {
								            	yesNodialog.hide();
								            }
								        } 
							        	
	        		 );
	         
	         exitDialog = new YesNoDialog("Exit",getSkin(), "Are you sure?", 
	        		 new ClickListener() {
		            @Override
		            public void clicked (InputEvent event, float x, float y)
		            {
		            	Gdx.app.exit();
		            }
		        	},
		        	new ClickListener() {
			            @Override
			            public void clicked (InputEvent event, float x, float y)
			            {
			            	exitDialog.hide();
			            }
			        } 
		        	
 );
	        
	        }

		@Override
		protected void backAction() {
			exitDialog.show(getTable().getStage());
			
		}

		@Override
		protected void touch(float x, float y) {}
		private void runOverlay(){
			if (!world.firstThreeLevelsPassed() || !world.spent5Minutes()) return;
//			if (random.nextFloat()>0.75)
//				game.getAdListener().startOverlay();	
		}

}
