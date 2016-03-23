package com.apr.markergame.screens;

import java.util.Random;

import com.apr.markergame.MarkerGame;
import com.apr.markergame.game.World;
import com.apr.markergame.game.WorldRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public abstract class AbstractGameScreen extends AbstractScreen{

	protected World world;
	protected WorldRenderer renderer;
	protected Random random = new Random();
	
	public AbstractGameScreen(MarkerGame game) {
		super(game);
		
	    world = game.world;
	    renderer = game.renderer;

	}
	
	@Override
	 public void show()
	 {
	     super.show();
		 InputMultiplexer multiplexer = new InputMultiplexer();
		 multiplexer.addProcessor(stage);
		 multiplexer.addProcessor(new MyInputProcessor());
		 Gdx.input.setInputProcessor(multiplexer);		

	     Gdx.input.setCatchBackKey(true);
	   
	 }
	
	@Override
	public void render(float delta){
        super.render(delta);

        
      //  renderer.render(delta);
        // update and draw the stage actors
        stage.draw();
        Table.drawDebug(stage);

	}
	
	protected abstract void backAction();
	protected abstract void touch(float x, float y);
	protected  void drag(float x, float y){}
	
	class MyInputProcessor implements InputProcessor{

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer,
				int button) {
			return false;
		}
		public boolean keyDown(int keycode) {return true;}
		public boolean keyUp(int keycode) {
			if (keycode==Keys.BACK){
				backAction();
			}
			return true;
		
		}
		public boolean keyTyped(char character) {return false;}
		public boolean touchDown(int screenX, int screenY, int pointer,int button) {
			
			touch(screenX*game.factorX, screenY*game.factorY);			
			
			return false;}
		public boolean touchDragged(int screenX, int screenY,int pointer) {
			drag(screenX*game.factorX, screenY*game.factorY);			

			return false;}
		public boolean mouseMoved(int screenX, int screenY) {return false;}
		public boolean scrolled(int amount) {	return false;}
	}
	
	class YesNoDialog extends Dialog{

		TextButton yesButton, noButton;
		
		public YesNoDialog(String title, Skin skin, String text, ClickListener yesAction, ClickListener noAction) {
			super("", skin);

	        Label titleLabel = new Label(title,getSkin(), "title-text");

	        Label textLabel = new Label(text,getSkin(), "text");
	        textLabel.getStyle().fontColor = Color.BLACK;
	        textLabel.setAlignment(Align.center);
	        textLabel.setWrap(true);        

	        
	       
	        
	         yesButton = new TextButton("Yes",getSkin());
	        yesButton.getLabel().getStyle().fontColor = getSkin().getColor("blue");
	        yesButton.addListener(yesAction);
	         noButton = new TextButton("No",getSkin());
	        noButton.getLabel().getStyle().fontColor = getSkin().getColor("red");
	        noButton.addListener( noAction );
	       
	        defaults();
	        columnDefaults(1);
	        row();
	        add(titleLabel).padTop(20);
	        row();
	        
	        add(textLabel).width(300);
	        row();
	        add(yesButton);
	        row();
	        add(noButton).padBottom(50);
	        row();


		}
		
		@Override
		public Dialog show(Stage stage){
			yesButton.getLabel().getStyle().fontColor = getSkin().getColor("blue");
			noButton.getLabel().getStyle().fontColor = getSkin().getColor("red");
	        
			return super.show(stage);
	        
		}
		
//		protected void runOverlay(){
//			if (random.nextFloat()>0.3)
//				game.getAdListener().startOverlay();	
//		}

	}
	
	class OkDialog extends Dialog{

		TextButton okButton;
		
		public OkDialog(String title, Skin skin, String text, ClickListener okAction) {
			super("", skin);

	        Label titleLabel = new Label(title,getSkin(), "title-text");

	        Label textLabel = new Label(text,getSkin(), "text");
	        textLabel.getStyle().fontColor = Color.BLACK;
	        textLabel.setAlignment(Align.center);
	        textLabel.setWrap(true);        

	        
	       
	        
	         okButton = new TextButton("Ok",getSkin());
	        okButton.getLabel().getStyle().fontColor = getSkin().getColor("blue");
	        okButton.addListener(okAction);
	        
	       
	        columnDefaults(1);
	        defaults();
	        row();
	        add(titleLabel).padTop(30);
	        row();
	        
	        add(textLabel).width(300);
	        row();
	        add(okButton).padBottom(30);
	        row();


		}
		
		@Override
		public Dialog show(Stage stage){
			okButton.getLabel().getStyle().fontColor = getSkin().getColor("blue");
	        
			return super.show(stage);
	        
		}

	}

}
