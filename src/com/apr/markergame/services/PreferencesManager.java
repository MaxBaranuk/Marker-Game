package com.apr.markergame.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PreferencesManager
{
    // constants
    private static final String PREF_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_VIBRATION_ENABLED = "vibration.enabled";
    private static final String PREF_BACKGROUND = "background";

    private static final String PREFS_NAME = "Marker";

    Preferences preferences;
    
    public PreferencesManager()
    {
    }

    protected Preferences getPrefs()
    {
    	if (preferences == null)
    		preferences = Gdx.app.getPreferences(PREFS_NAME);
        return preferences;
    }

    public boolean isVibrationEnabled()
    {
        return getPrefs().getBoolean( PREF_VIBRATION_ENABLED, true );
    }

    public void setVibrationEnabled(
        boolean vibrationEffectsEnabled )
    {
        getPrefs().putBoolean( PREF_VIBRATION_ENABLED, vibrationEffectsEnabled );
        getPrefs().flush();
    }

    
    public boolean isSoundEnabled()
    {
        return getPrefs().getBoolean( PREF_SOUND_ENABLED, true );
    }

    public void setSoundEnabled(
        boolean soundEffectsEnabled )
    {
        getPrefs().putBoolean( PREF_SOUND_ENABLED, soundEffectsEnabled );
        getPrefs().flush();
    }

    public boolean isMusicEnabled()
    {
        return getPrefs().getBoolean( PREF_MUSIC_ENABLED, true );
    }

    public void setMusicEnabled(
        boolean musicEnabled )
    {
        getPrefs().putBoolean( PREF_MUSIC_ENABLED, musicEnabled );
        getPrefs().flush();
    }

    public float getVolume()
    {
        return getPrefs().getFloat( PREF_VOLUME, 0.5f );
    }

    public void setVolume(
        float volume )
    {
        getPrefs().putFloat( PREF_VOLUME, volume );
        getPrefs().flush();
    }
    
    public int getBackground()
    {
        return getPrefs().getInteger( PREF_BACKGROUND, 1 );
    }

    public void setBackground(
        int background )
    {
        getPrefs().putInteger( PREF_BACKGROUND, background );
        getPrefs().flush();
    }
}