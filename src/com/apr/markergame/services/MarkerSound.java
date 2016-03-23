package com.apr.markergame.services;

/**
 * The available sound files.
 */
public enum MarkerSound
{
    EXPLOSION( "sounds/explosion.mp3" ),
    BALLOON( "sounds/balloon.mp3" ),
    FAIL( "sounds/fail.mp3" ),
    PASS_LEVEL( "sounds/level.mp3" ),
    PASS_STEP( "sounds/step.mp3" ),
    PEN( "sounds/pen.mp3" ),
    TICK( "sounds/tick.mp3" );
    

    private final String fileName;

    private MarkerSound(
        String fileName )
    {
        this.fileName = fileName;
    }

    public String getFileName()
    {
        return fileName;
    }
}