package com.apr.markergame.services;

import com.apr.markergame.MarkerGame;
import com.apr.markergame.game.GameState;
import com.apr.markergame.game.Packs;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * Profile operations.
 */
public class GameStateManager
{
    // the location of the profile data file
    private static final String PROFILE_DATA_FILE = "data/gameState-v1.json";

    // the loaded profile (may be null)
    private GameState gameState;

    public GameStateManager()
    {
    }

    /**
     * Retrieves the player's profile, creating one if needed.
     */
    public GameState retrieveGameState(Packs packs)
    {
        FileHandle gameStateDataFile = Gdx.files.local( PROFILE_DATA_FILE );
        Gdx.app.log( MarkerGame.LOG, "Retrieving game state from: " + gameStateDataFile.path() );

        if( gameState != null ) return gameState;

        // create the JSON utility object
        Json json = new Json();

        // check if the profile data file exists
        if( gameStateDataFile.exists() ) {

            // load the profile from the data file
            try {

                // read the file as text
                String gameStateAsText = gameStateDataFile.readString().trim();

                // decode the contents (if it's base64 encoded)
                if( gameStateAsText.matches( "^[A-Za-z0-9/+=]+$" ) ) {
                    Gdx.app.log( MarkerGame.LOG, "Persisted game state is base64 encoded" );
                   // gameStateAsText = Base64Coder.decodeString( profileAsText );
                }

                // restore the state
                gameState = json.fromJson( GameState.class, gameStateAsText );

            } catch( Exception e ) {

                // log the exception
                Gdx.app.error( MarkerGame.LOG, "Unable to parse existing game state data file", e );

                // recover by creating a fresh new profile data file;
                // note that the player will lose all game progress
                gameState = new GameState( packs);
                persist( gameState );

            }

        } else {
            // create a new profile data file
            gameState = new GameState( packs);
            persist( gameState );
        }

        // return the result
        return gameState;
    }

    /**
     * Persists the given profile.
     */
    protected void persist(
        GameState gameState )
    {
        // create the handle for the profile data file
        FileHandle gameStateDataFile = Gdx.files.local( PROFILE_DATA_FILE );
        Gdx.app.log( MarkerGame.LOG, "Persisting game state in: " + gameStateDataFile.path() );

        // create the JSON utility object
        Json json = new Json();

        // convert the given profile to text
        String gameStateAsText = json.toJson( gameState );

        // encode the text
        if( ! MarkerGame.DEV_MODE ) {
           // gameStateAsText = Base64Coder.encodeString( gameStateAsText );
        }

        // write the profile data file
        gameStateDataFile.writeString( gameStateAsText, false );
    }

    /**
     * Persists the player's profile.
     * <p>
     * If no profile is available, this method does nothing.
     */
    public void persist()
    {
        if( gameState != null ) {
            persist( gameState );
        }
    }
}