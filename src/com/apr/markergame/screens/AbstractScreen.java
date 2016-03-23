package com.apr.markergame.screens;

import com.apr.markergame.MarkerGame;
import com.apr.markergame.services.MusicManager.GameMusic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class AbstractScreen implements Screen{
	
	// the fixed viewport dimensions (ratio: 1.6)
    public static final int GAME_VIEWPORT_WIDTH = 480, GAME_VIEWPORT_HEIGHT = 800;
    public static final int MENU_VIEWPORT_WIDTH = 480, MENU_VIEWPORT_HEIGHT = 800;
	protected Color[] color = {new Color(0f, 0.0f,0f,1f),new Color(1f,1f,1f,1f)};
    
	 protected final MarkerGame game;
	 protected final Stage stage;

	 protected BitmapFont font;
	 protected SpriteBatch batch;
	 private Skin skin;
	 private TextureAtlas atlas;
	 protected Table table;
	 protected static int backId;

	public AbstractScreen(
	        MarkerGame game )
	{
	     this.game = game;
	     int width = ( isGameScreen() ? GAME_VIEWPORT_WIDTH : MENU_VIEWPORT_WIDTH );
	     int height = ( isGameScreen() ? GAME_VIEWPORT_HEIGHT : MENU_VIEWPORT_HEIGHT );
	     this.stage = new Stage( width, height, true );
	}

	 protected String getName()
	 {
	     return getClass().getSimpleName();
	 }

	 protected boolean isGameScreen()
	 {
		 return false;    
	 }

	 protected BitmapFont getFont(){
		 if (font==null)
			 font = new BitmapFont();
		 
		 return font;
	 }
	 
	 protected SpriteBatch getBatch(){
		 if (batch==null)
			 batch = new SpriteBatch();
		 
		 return batch;
	 }
	 
	 protected Skin getSkin()
	    {
	        if( skin == null ) {
	            skin = new Skin( Gdx.files.internal( "uiskin.json" ), new TextureAtlas( "uiskin.atlas" ) );
	            skin.getFont("default-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	            skin.getFont("title-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	        }
	        return skin;
	    }

	protected Table getTable()
	 {
	     if( table == null ) {
             table = new Table( getSkin() );          
             table.setFillParent( true );
	         if( MarkerGame.DEV_MODE ) {
	             table.debug();
	         }
	         stage.addActor( table );
	     }
	     return table;
	 }
	 
	 @Override
	 public void show()
	 {
	     Gdx.app.log( MarkerGame.LOG, "Showing screen: " + getName() );
	     Gdx.input.setInputProcessor( stage );
	     Gdx.input.setCatchBackKey(true);
	     
	     game.getMusicManager().play(GameMusic.MENU);
    	 // load the texture with the splash image
	     //   Texture back = game.assetManager.get( "back" + game.getPreferencesManager().getBackground() + ".jpg" );

	        // set the linear texture filter to improve the image stretching
	     //   back.setFilter( TextureFilter.Linear, TextureFilter.Linear );
	   // 	 TextureRegion backRegion = new TextureRegion( back, 0, 0, 480, 800 );

	    //    getTable().setBackground(new TextureRegionDrawable(backRegion));
	        backId = game.getPreferencesManager().getBackground();

	 }

	@Override
	 public void resize(
	     int width, 
	     int height )
	 {
        Gdx.app.log( MarkerGame.LOG, "Resizing screen: " + getName() + " to: " + width + " x " + height );


	 }

	 @Override
	 public void render(
	     float delta )
	 {
	        stage.act( delta );

	        Gdx.gl.glClearColor( color[backId].r, color[backId].g, color[backId].b, 1f );
	        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
	        // update and draw the stage actors
	        stage.draw();
	        Table.drawDebug(stage);
	 }

	 @Override
	 public void hide()
	 {
	     Gdx.app.log( MarkerGame.LOG, "Hiding screen: " + getName() );
	     dispose();
	 }

	 @Override
	 public void pause()
	 {
	     Gdx.app.log( MarkerGame.LOG, "Pausing screen: " + getName() );
	 }

	 @Override
	 public void resume()
	 {
	     Gdx.app.log( MarkerGame.LOG, "Resuming screen: " + getName() );
	 }

	 @Override
	 public void dispose()
	 {
	     Gdx.app.log( MarkerGame.LOG, "Disposing screen: " + getName() );
         // dispose the collaborators
	  //   stage.dispose();
	     if (batch!=null) batch.dispose();
	     if (font!=null) font.dispose();
	     if (skin!=null) skin.dispose();
	     if (atlas!=null) atlas.dispose();

	 }
	 
	 protected void updateBackground(){
	        backId = game.getPreferencesManager().getBackground();
	        //Texture back = game.assetManager.get( "back" + game.getPreferencesManager().getBackground() + ".jpg" );

	        // set the linear texture filter to improve the image stretching
	        //back.setFilter( TextureFilter.Linear, TextureFilter.Linear );
	    	// TextureRegion backRegion = new TextureRegion( back, 0, 0, 480, 800 );

	        //getTable().setBackground(new TextureRegionDrawable(backRegion));

	 }

}
