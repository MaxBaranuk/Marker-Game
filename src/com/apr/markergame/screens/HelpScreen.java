package com.apr.markergame.screens;

import com.apr.markergame.MarkerGame;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;



public class HelpScreen extends AbstractGameScreen {

	
	
	    
		public HelpScreen(
	        MarkerGame game )
	    {
	        super( game );
	    }

	    @Override
	    public void show()
	    {
	        super.show();

	        // retrieve the default table actor
	        Table table = super.getTable();

	        
	        table.defaults().spaceBottom( 20 );
	        table.columnDefaults( 0 );
	        Label optionsLabel = new Label("Help",getSkin(), "title-text");
	        optionsLabel.getStyle().fontColor = getSkin().getColor("green");
	        table.add( optionsLabel ).colspan( 2 ).spaceBottom(30).padTop(10);
	        table.row();
	        
	        Label label = new Label("On each level you can earn up to 5 stars.",getSkin(),"text");        
	        label.getStyle().font.scale(0.4f);
	        label.setAlignment(Align.center);
	        label.setWrap(true);
	        table.add(label).colspan(2).width(480);
	        table.row();

	        Label label2 = new Label("To pass to the next level, you need to get at least 3 stars on a current level..",getSkin(),"text");  
	        label2.setAlignment(Align.center);
	        label2.setWrap(true);
	        table.add(label2).colspan(2).width(400);;
	        table.row();
	        
	        Label label3 = new Label("To unlock the next level pack, you have to gain at least 17 stars on a current level pack.",getSkin(),"text");        
	        label3.setAlignment(Align.center);
	        label3.setWrap(true);
	        table.add(label3).colspan(2).width(400);;
	         table.row();

	        
	        Label label4 = new Label("If you are stuck, you can get stars to unlock next level pack in the 'Get stars' menu and apply them to the current level pack.",getSkin(),"text");        
	        label4.setAlignment(Align.center);
	        label4.setWrap(true);
	        table.add(label4).colspan(2).width(400);;
	        table.row();

	        Label label5 = new Label("Good luck ;)",getSkin(),"text");        
	        table.add(label5).colspan(2);
	        table.row();


	        
	        // register the back button
	        TextButton backButton = new TextButton( "Back", getSkin() );
	        backButton.addListener( new ClickListener() {
	        	 @Override
		         public void clicked (InputEvent event, float x, float y)
		         {
	                //game.getSoundManager().play( TyrianSound.CLICK );
	                game.setScreen( new MenuScreen( game ) );
	            }
	        } );
	        table.row();
	        table.add( backButton ).size( 500, 80 ).colspan( 2 ).padBottom(100);
	        table.row();
	        
	     }

		@Override
		protected void backAction() {
			
		}

		@Override
		protected void touch(float x, float y) {
			
		}


}
