package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Justin on 2016-01-17.
 */
public class Item extends Sprite {
    boolean bRemove = false;

    public Item(Texture tx) {
        super(tx);
        //  this.setTexture(tx);
    }

    public void remove(boolean _b) {
        bRemove = _b;
    }

    public boolean isRemoved() {
        return bRemove;
    }
}

