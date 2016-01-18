package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HUD {
    SpriteBatch spriteBatch;
    BitmapFont font;
    CharSequence str = "Hello World!";
    String sScore;
    int nScore = 0, nHealth=0,nFrames=0,nTime=0;

    public void create() {
        spriteBatch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("default.fnt"),
                Gdx.files.internal("default.png"), false);

    }

    public void render() {
        nFrames++;
        if(nFrames%60==0) {
            nTime++;
        }
        sScore = "Time: "+nTime+"                            "+ "Score: "+nScore+"                            "+"Health: "+nHealth+"                            "+"Bomb Counter: 0";
        spriteBatch.begin();
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        font.draw(spriteBatch, sScore, 0, Gdx.graphics.getHeight()-15);
        spriteBatch.end();
    }
}
