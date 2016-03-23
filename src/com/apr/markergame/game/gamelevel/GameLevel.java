package com.apr.markergame.game.gamelevel;

import java.text.DecimalFormat;
import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public abstract class GameLevel {

	protected static DecimalFormat twoDForm = new DecimalFormat("#.##");
	protected static final float TIME_TO_ANSWER = 3f;

	protected static final String[] colors = {"blue","yellow","purple","red","green"};
	protected static final Color[] COLORS_RGB = {new Color(0.45f,0.74f,0.9f,1f),
		new Color(0.89f,0.99f,0.47f,1f),
		new Color(0.9f,0.49f,0.76f,1f),
		new Color(0.97f,0.63f,0.39f,1f),
		new Color(0.37f,0.87f,0.55f,1f)};


    OrthographicCamera  cam;
    protected TextureAtlas atlas;
    protected SpriteBatch batch;    
    
    BitmapFont font;

	protected Random random = new Random();
	protected GameLevelListener listener;
	
	protected int pass;

	protected boolean started = false;
	
	protected Array<Float> factor;
	protected float currentFactor;
	
	public GameLevel(){
        this.cam = new OrthographicCamera(480, 800);
        this.cam.position.set(240, 400,0f);
        this.cam.update();
        
        

        
	}
	
	public void setAssets(AssetManager manager){
        atlas = manager.get("game.atlas",TextureAtlas.class);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(cam.combined);
        font = manager.get("default.fnt",BitmapFont.class);

	}
	
	public void setGameLevelListener(GameLevelListener listener){
		this.listener = listener;
	}
	
	public void setFactor(Array<Float> factor){
		this.factor = factor;
	}

	public void pause(){
		started = false;
	}

	public void resume(){
		started = true;
	}

	public void startGame(){
		started = true;
		pass = 1;
		currentFactor = factor.get(0);
	}
	public void startPass(){
		started = true;
		pass++;
		currentFactor = factor.get(pass-1);
	}
	public abstract void touch(float x, float y);
	public void drag(float x, float y){};
	public abstract void update(float delta);
	public abstract void render();
	
	

	private float dist(float x1, float y1, float x2, float y2){
		return (float)Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
	}

	
	protected boolean intersectsCircles(Actor actor, Array<Actor> actors){
		for (Actor other:actors){
			if (actor!=other && dist(actor.getX()+actor.getWidth()/2, actor.getY()+actor.getHeight()/2,
					other.getX()+other.getWidth()/2, other.getY()+other.getHeight()/2)<50){
				return true;
			}
		}
		return false;
	}
	

	protected boolean intersectsRectangles(Actor actor, Array<Actor> actors){
		for (Actor other:actors){
			if (actor!=other && intersectsRectangles(actor,other)){
				return true;
			}
		}
		return false;
	}

	
	 protected boolean intersectsRectangles(Actor r1, Actor r2) {
	        if(r1.getX() < r2.getX()  + r2.getWidth() &&
	           r1.getX()  + r1.getWidth() > r2.getX() &&
	           r1.getY()  < r2.getY()  + r2.getHeight() &&
	           r1.getY()  + r1.getHeight() > r2.getY())
	            return true;
	        else
	            return false;
	    }

	 protected boolean intersectsPoints(float x, float y, Actor actor){
		 if(x>actor.getX() && 800-y>actor.getY() && x<actor.getX()+actor.getWidth()*actor.getScaleX() && 800-y<actor.getY()+actor.getHeight()*actor.getScaleY())
			 return true;
		 
		 return false;
	 }

}
