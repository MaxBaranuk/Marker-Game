package com.apr.markergame.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class DynamicGameObject extends Actor {
    public Vector2 velocity;
    public Vector2 accel;
    public Rectangle bounds;

    public DynamicGameObject(){
    	super();
    }
    
    public DynamicGameObject(float x, float y, float width, float height) {
        super();

        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        velocity = new Vector2();
        accel = new Vector2();
        this.bounds = new Rectangle(x, y, width, height);

    }
    
    public void define(float width, float height){
        velocity = new Vector2();
        accel = new Vector2();
        this.bounds = new Rectangle(getX(), getY(), width, height);

    }
}
