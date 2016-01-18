package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Justin on 2016-01-17.
 */
public class ItemSpawner {
    Item banana;
    SpriteBatch sb;

    public void create(){
        banana = new Item(new Texture(Gdx.files.internal("banana.png")));
        banana.setBounds(150,150,25,25);
        sb = new SpriteBatch();
    }

    public void render(){
        sb.begin();
        sb.draw(banana,150,150,25,25);
        sb.end();
    }
}
