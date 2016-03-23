package com.apr.markergame.screens;


import com.apr.markergame.MarkerGame;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

/**
 * A simple options screen.
 */
public class OptionsScreen extends AbstractGameScreen
{
    private Label volumeValue;

    public OptionsScreen(
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
        table.columnDefaults( 0 ).padRight( -50 );
        Label optionsLabel = new Label("Options",getSkin(), "title-text");
        optionsLabel.getStyle().fontColor = getSkin().getColor("blue");
        table.add( optionsLabel ).colspan( 2 ).spaceBottom(30);

        
        // create the labels widgets
        final CheckBox vibrationCheckbox = new CheckBox( "", getSkin() );
        vibrationCheckbox.setChecked( game.getPreferencesManager().isVibrationEnabled() );
        vibrationCheckbox.addListener( new ChangeListener() {
            @Override
            public void changed(
                ChangeEvent event,
                Actor actor )
            {
                boolean enabled = vibrationCheckbox.isChecked();
                game.getPreferencesManager().setVibrationEnabled( enabled );
                
            }
        } );
     //   table.row();
    //    Label vibrationLabel = new Label("Vibration",getSkin());
    //    vibrationLabel.getStyle().fontColor = getSkin().getColor("green");
     //   table.add( vibrationLabel );
     //   table.add( vibrationCheckbox ).colspan( 2 ).left();


        
        // create the labels widgets
        final CheckBox soundEffectsCheckbox = new CheckBox( "", getSkin() );
        soundEffectsCheckbox.setChecked( game.getPreferencesManager().isSoundEnabled() );
        soundEffectsCheckbox.addListener( new ChangeListener() {
            @Override
            public void changed(
                ChangeEvent event,
                Actor actor )
            {
                boolean enabled = soundEffectsCheckbox.isChecked();
                game.getPreferencesManager().setSoundEnabled( enabled );
                game.getSoundManager().setEnabled( enabled );
            }
        } );
        table.row();
        Label soundLabel = new Label("Sound",getSkin());
        soundLabel.getStyle().fontColor = getSkin().getColor("blue");

        table.add( soundLabel );
        table.add( soundEffectsCheckbox ).colspan( 2 ).left();

        final CheckBox musicCheckbox = new CheckBox( "", getSkin() );
        musicCheckbox.setChecked( game.getPreferencesManager().isMusicEnabled() );
        
        musicCheckbox.addListener( new ChangeListener() {
            @Override
            public void changed(
                ChangeEvent event,
                Actor actor )
            {
            	
                boolean enabled = musicCheckbox.isChecked();
                musicCheckbox.setChecked(enabled);
                game.getPreferencesManager().setMusicEnabled( enabled );
                game.getMusicManager().setEnabled( enabled );

                // if the music is now enabled, start playing the menu music
               
            }
        } );
        table.row();
        Label musicLabel = new Label("Music",getSkin());
        musicLabel.getStyle().fontColor = getSkin().getColor("yellow");

        table.add( musicLabel );
        table.add( musicCheckbox ).colspan( 2 ).left();

        // range is [0.0,1.0]; step is 0.1f
        Slider volumeSlider = new Slider( 0f, 1f, 0.1f, false, getSkin() ,"default");
        volumeSlider.setValue( game.getPreferencesManager().getVolume() );
        volumeSlider.addListener( new ChangeListener() {
            @Override
            public void changed(
                ChangeEvent event,
                Actor actor )
            {
                float value = ( (Slider) actor ).getValue();
                game.getPreferencesManager().setVolume( value );
                game.getMusicManager().setVolume( value );
              //  game.getSoundManager().setVolume( value );
                updateVolumeLabel();
            }
        } );

        // create the volume label

        // add the volume row
        table.row();
        Label volumeLabel = new Label("Volume",getSkin());
        volumeLabel.getStyle().fontColor = getSkin().getColor("green");

        table.add( volumeLabel);
        table.add( volumeSlider );
        table.add( volumeValue ).width( 40 );

        table.row();
        Label label = new Label("Please, choose a color  of your device",getSkin(),"text");        
        table.add(label).colspan(2);
        table.row();
        
        
        final Button back1Button = new Button( getSkin(),"back1" );
        back1Button.addListener( new ClickListener() {
       	 @Override
	         public void clicked (InputEvent event, float x, float y)
	         {
       		   int old = game.getPreferencesManager().getBackground();
               game.getPreferencesManager().setBackground(0);
               if (old==1)
            	   updateBackground();
           }
       } );
        final Button back2Button = new Button(getSkin(),"back2" );
        back2Button.addListener( new ClickListener() {
          	 @Override
   	         public void clicked (InputEvent event, float x, float y)
   	         {
          		  int old = game.getPreferencesManager().getBackground();
                  game.getPreferencesManager().setBackground(1);
                  if (old==0)
                	  updateBackground();
              }
          } );
        if (game.getPreferencesManager().getBackground()==0){
        	back1Button.setChecked(true);
        }else{
        	back2Button.setChecked(true);
        }
        
         new ButtonGroup(back1Button, back2Button);

        

        table.row();
        table.add(back1Button);
        table.add(back2Button);

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
        table.add( backButton ).size( 500, 80 ).colspan( 2 );
        table.row();
        
        
    }

    /**
     * Updates the volume label next to the slider.
     */
    private void updateVolumeLabel()
    {
       // float volume = ( game.getPreferencesManager().getVolume() * 100 );
       // volumeValue.setText( String.format( Locale.US, "%1.0f%%", volume ) );
    }
    
	@Override
	protected void backAction() {
		game.setScreen(new MenuScreen(game));
	}

	@Override
	protected void touch(float x, float y) {}		

}