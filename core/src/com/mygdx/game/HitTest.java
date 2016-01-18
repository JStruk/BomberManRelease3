package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Justin on 2016-01-17.
 */
public class HitTest {
    Sprite spr1, spr2;

    public boolean bHit(Sprite s1, Sprite s2) {
        spr1 = new Sprite(s1);
        spr2 = new Sprite(s2);
        if (spr1.getBoundingRectangle().overlaps(spr2.getBoundingRectangle())) {
            return true;
        } else {
            return false;
        }
    }
    public HitTest() {

        // spr1 = new Sprite(spr1);
    }
}
