package com.apr.markergame;

import com.apr.markergame.game.Packs;
import com.apr.markergame.game.World;
import com.apr.markergame.game.WorldRenderer;
import com.apr.markergame.screens.MenuScreen;
import com.apr.markergame.services.GameStateManager;
import com.apr.markergame.services.MusicManager;
import com.apr.markergame.services.PreferencesManager;
import com.apr.markergame.services.SoundManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Json;

public class MarkerGame extends Game {

	public static final String LOG = MarkerGame.class.getSimpleName();

	public static final boolean DEV_MODE = false;
	
	
	public World world;
	public WorldRenderer renderer;
	
    // a libgdx helper class that logs the current FPS each second
    private FPSLogger fpsLogger;

 // services
    private PreferencesManager preferencesManager;
    private GameStateManager gameStateManager;
    private MusicManager musicManager;
    private SoundManager soundManager;
    public AssetManager assetManager;
    public float factorX, factorY;
    public ActionResolver actionResolver;
 // Services' getters

    
    public MarkerGame(
			ActionResolver actionResolver
			) {
		// TODO Auto-generated constructor stub
		this.actionResolver = actionResolver;
	}
    
    public PreferencesManager getPreferencesManager()
    {
        return preferencesManager;
    }

    public GameStateManager getProfileManager()
    {
        return gameStateManager;
    }

    public MusicManager getMusicManager()
    {
        return musicManager;
    }

    public SoundManager getSoundManager()
    {
        return soundManager;
    }
    
//    private AdsListener adListener;
//    
//    public AdsListener getAdListener(){
//    	return adListener;
//    }
//    
//    public MarkerGame(AdsListener adListener){
//    	this.adListener = adListener;
//    }
    
    
 // Screen methods

    @Override
    public void create()
    {
    	Gdx.app.log( MarkerGame.LOG, "Creating game on " + Gdx.app.getType() );

    	assetManager = new AssetManager();
    	assetManager.load("game.atlas", TextureAtlas.class);
    	assetManager.load("default.fnt", BitmapFont.class);
    	assetManager.load("bigFont.fnt", BitmapFont.class);
    	assetManager.load("sounds/explosion.mp3", Sound.class);
    	assetManager.load("sounds/balloon.mp3", Sound.class);
    	assetManager.load("sounds/fail.mp3", Sound.class);
    	assetManager.load("sounds/level.mp3", Sound.class);
    	assetManager.load("sounds/step.mp3", Sound.class);
    	assetManager.load("sounds/tick.mp3", Sound.class);
    	assetManager.load("sounds/pen.mp3", Sound.class);
    	
    	assetManager.finishLoading();

        // create the preferences manager
        preferencesManager = new PreferencesManager();
        
        // create the music manager
        musicManager = new MusicManager();
        musicManager.setVolume( preferencesManager.getVolume() );
        musicManager.setEnabled( preferencesManager.isMusicEnabled() );

        // create the sound manager
        soundManager = new SoundManager(assetManager);
        soundManager.setVolume( preferencesManager.getVolume() );
        soundManager.setEnabled( preferencesManager.isSoundEnabled() );


        // create the profile manager
        gameStateManager = new GameStateManager();

        Json json = new Json();
        Packs packs = json.fromJson(Packs.class, Gdx.files.internal("packs.json"));
        

        
	    world = new World(packs,gameStateManager.retrieveGameState(packs));
	    renderer = new WorldRenderer(world);

        
        // create the helper objects
        fpsLogger = new FPSLogger();
    }

    @Override
    public void resize(
        int width,
        int height )
    {
    	super.resize( width, height );
    	factorX = 480f/(float)(width);
    	factorY = 800f/(float)(height);

        Gdx.app.log( MarkerGame.LOG, "Resizing game to: " + width + " x " + height );

        // show the splash screen when the game is resized for the first time;
        // this approach avoids calling the screen's resize method repeatedly
        if( getScreen() == null ) {
            if( DEV_MODE ) {
                setScreen( new MenuScreen( this ) );
            } else {
                setScreen( new MenuScreen( this ) );
            }
        }
   }

    @Override
    public void render()
    {
    	 super.render();

         // output the current FPS
    	 if( DEV_MODE ) fpsLogger.log();
    }

    @Override
    public void pause()
    {
    	super.pause();
        Gdx.app.log( MarkerGame.LOG, "Pausing game" );
        gameStateManager.persist();
    }

    @Override
    public void resume()
    {
    	super.resume();
        Gdx.app.log( MarkerGame.LOG, "Resuming game" );
    }
    
    @Override
    public void setScreen(
        Screen screen )
    {
        super.setScreen( screen );
        Gdx.app.log( MarkerGame.LOG, "Setting screen: " + screen.getClass().getSimpleName() );
    }


    @Override
    public void dispose()
    {
    	super.dispose();
        Gdx.app.log( MarkerGame.LOG, "Disposing game" );
       // musicManager.dispose();
       // soundManager.dispose();
    }
}
