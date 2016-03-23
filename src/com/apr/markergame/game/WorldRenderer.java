package com.apr.markergame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class WorldRenderer {
    static final float FRUSTUM_WIDTH = 4.8f;
    static final float FRUSTUM_HEIGHT = 8.0f;    
    World world;
    OrthographicCamera  cam;
    TextureAtlas atlas;
    SpriteBatch batch;    
    BitmapFont font;
    boolean doneIncreased;
    
    public WorldRenderer(World world) {
        this.world = world;
        this.cam = new OrthographicCamera(480, 800);
        this.cam.position.set(240, 400,0f);
        this.cam.update();
        
        atlas = new TextureAtlas("game.atlas");
        batch = new SpriteBatch();
        batch.setProjectionMatrix(cam.combined);

        
        font = new BitmapFont(Gdx.files.internal("default.fnt"),false);
        

    }
    
    public void setCamera(){
    	this.cam.position.set(240, 400,0f);
        this.cam.update();
        batch.setProjectionMatrix(cam.combined);

    }
    
    public void render(float delta) {

    	
    	
        renderObjects(delta);        
        
    }
  
    
    
    public void renderObjects(float delta) {
        
                   
        

    }
}
