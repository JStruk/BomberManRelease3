package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by dinnatruong on 16-01-18.
 */
public class Explosion {
    static SpriteBatch batch;
    static TextureAtlas taExplosion;
    static float x, y;
    static Sprite spUp, spDown, spLeft, spRight;


//    public enum ExplosionType {
//        CENTER, UP, DOWN, LEFT, RIGHT, HORIZONTAL, VERTICAL
//    }

    Explosion(float fX, float fY) {
        batch = new SpriteBatch();
        x = fX;
        y = fY;
        taExplosion = new TextureAtlas(Gdx.files.internal("Explosion/explosion.pack"));

        spUp = new Sprite(taExplosion.findRegion("explosion_vertical"));
        spUp.setPosition(x, y + 20);
        spDown = new Sprite(taExplosion.findRegion("explosion_vertical"));
        spDown.setPosition(x, y - 20);
        spRight = new Sprite(taExplosion.findRegion("explosion_horizontal"));
        spRight.setPosition(x + 20, y);
        spLeft = new Sprite(taExplosion.findRegion("explosion_horizontal"));
        spLeft.setPosition(x - 20, y);

    }

    public static void render() {
        batch.begin();
        spUp.draw(batch);
        spDown.draw(batch);
        spRight.draw(batch);
        spLeft.draw(batch);
        batch.end();
    }
}