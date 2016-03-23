package com.apr.markergame.services;

import com.apr.markergame.MarkerGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/**
 * A service that manages the sound effects.
 */
public class SoundManager
{
   

    /**
     * The volume to be set on the sound.
     */
    private float volume = 1f;

    /**
     * Whether the sound is enabled.
     */
    private boolean enabled = true;

     private AssetManager manager;
    /**
     * Creates the sound manager.
     */
    public SoundManager(AssetManager manager)
    {
    	this.manager = manager;
    }

    /**
     * Plays the specified sound.
     */
    public void play(
    		MarkerSound sound )
    {
        // check if the sound is enabled
        if( ! enabled ) return;

        Sound soundToPlay = manager.get(sound.getFileName());
        if (soundToPlay==null) return;
        
        // play the sound
        Gdx.app.log( MarkerGame.LOG, "Playing sound: " + sound.name() );
        soundToPlay.play( volume );
    }

    /**
     * Sets the sound volume which must be inside the range [0,1].
     */
    public void setVolume(
        float volume )
    {
        Gdx.app.log( MarkerGame.LOG, "Adjusting sound volume to: " + volume );

        // check and set the new volume
        if( volume < 0 || volume > 1f ) {
            throw new IllegalArgumentException( "The volume must be inside the range: [0,1]" );
        }
        this.volume = volume;
    }

    /**
     * Enables or disabled the sound.
     */
    public void setEnabled(
        boolean enabled )
    {
        this.enabled = enabled;
    }

}
