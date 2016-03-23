package com.apr.markergame.screens;


import com.apr.markergame.MarkerGame;
import com.apr.markergame.UpdateTapListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

/**
 * A simple options screen.
 */
public class FreeStarsScreen extends AbstractGameScreen
implements UpdateTapListener
{
    private Label pointsLabel;
    private YesNoDialog yesNoDialog;
    private OkDialog okDialog,nostarsDialog;
    int stars = 0;
    
    TextButton getTapsButton,changeTapsButton,backButton;
    
    public FreeStarsScreen(
        MarkerGame game )
    {
        super( game );
    }

    @Override
    public void show()
    {
        super.show();
        
 //       game.getAdListener().setUpdateTapListener(this);
  //      game.getAdListener().getTapjoyPoints();
        
        yesNoDialog = new YesNoDialog("Are you sure",getSkin(), "Stars will be added to current pack",
        				new ClickListener(){
				        	@Override
					         public void clicked (InputEvent event, float x, float y)
					         {
				        		world.addStars(stars);
					        	//world.getPackResult().freeStars+=stars;
	//				        	game.getAdListener().spendTapjoyPoints(stars);
				        		yesNoDialog.hide();

				             }
        				},
        				new ClickListener(){
				        	@Override
					         public void clicked (InputEvent event, float x, float y)
					         {
				        		yesNoDialog.hide();
				             }
        				});
        
        okDialog = new OkDialog("Error",getSkin(), "You need at least 20 taps to get a star",
				new ClickListener(){
		        	@Override
			         public void clicked (InputEvent event, float x, float y)
			         {
		        		okDialog.hide();

		             }
				});

        nostarsDialog = new OkDialog("Error",getSkin(), "You dont have stars to spend",
				new ClickListener(){
		        	@Override
			         public void clicked (InputEvent event, float x, float y)
			         {
		        		nostarsDialog.hide();

		             }
				});

        // retrieve the default table actor
        Table table = super.getTable();
        

        
        table.defaults().spaceBottom( 20 );
        table.columnDefaults( 0 ).padRight( -50 );
        Label optionsLabel = new Label("Free Stars",getSkin(), "title-text");
        optionsLabel.getStyle().fontColor = getSkin().getColor("blue");
        table.add( optionsLabel ).colspan( 2 ).spaceBottom(30);
        pointsLabel = new Label("Stars: ?" ,getSkin());
 //       game.getAdListener().getTapjoyPoints();


        
        
        
        getTapsButton = new TextButton("Get Stars", getSkin() );
        getTapsButton.getLabel().getStyle().fontColor = getSkin().getColor("yellow");

        getTapsButton.addListener( new ClickListener() {
       	 @Override
	         public void clicked (InputEvent event, float x, float y)
	         {
       		 	getTapsButton.getLabel().getStyle().fontColor = getSkin().getColor("yellow");

   //    		 	game.getAdListener().showOffers();
             }
       } );

        changeTapsButton = new TextButton("Consume stars", getSkin() );
        changeTapsButton.getLabel().getStyle().fontColor = getSkin().getColor("blue");

        changeTapsButton.addListener( new ClickListener() {
       	 @Override
	         public void clicked (InputEvent event, float x, float y)
	         {
             changeTapsButton.getLabel().getStyle().fontColor = getSkin().getColor("blue");

       		 	if (stars>0)
       		 		yesNoDialog.show(getTable().getStage());
       		 	else
       		 		nostarsDialog.show(getTable().getStage());
       		 		
             }
       } );


        table.row();
        table.add(pointsLabel);
        table.row();
        table.add(getTapsButton);
        table.row();
        table.add(changeTapsButton);

        // register the back button
        backButton = new TextButton( "Back", getSkin() );
		 backButton.getLabel().getStyle().fontColor = getSkin().getColor("red");

        backButton.addListener( new ClickListener() {
        	 @Override
	         public void clicked (InputEvent event, float x, float y)
	         {
        		 backButton.getLabel().getStyle().fontColor = getSkin().getColor("red");

                //game.getSoundManager().play( TyrianSound.CLICK );
                game.setScreen( new MenuScreen( game ) );
            }
        } );
        table.row();
        table.add( backButton ).size( 500, 80 ).colspan( 2 );
        table.row();
        
        
        
    }

   
    
	@Override
	protected void backAction() {
		game.setScreen(new MenuScreen(game));
	}

	@Override
	protected void touch(float x, float y) {}

	@Override
	public void updateTaps(int taps) {
		pointsLabel.setText("Stars: " + taps);
		stars = taps;
	}		

}