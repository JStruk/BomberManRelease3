package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;


/**
 * Created by Justin on 2015-11-02.
 */
public class Character implements ApplicationListener {
    boolean bCollidedY;
    int nTileHeight, nTileWidth, nYtiles, nCharacterWidth;
    Texture txChar;
    OrthographicCamera cam;
    String sID;
    SpriteBatch batch;
    TextureAtlas taBomberman;
    Sprite[] spBomberman;
    static int i = 0, nCurrentIndex;
    Animation animFront, animBack, animRight, animLeft;
    static float stateTime, fCharacterVelocityX = 0, fCharacterVelocityY = 0, fCharacterX, fCharacterY, fCharacterWidth, fOldX, fOldY;
    int nVelocityX, nVelocityY;
    TextureRegion currentFrame;
    TextureRegion[] atrFront, atrBack, atrLeft, atrRight;
    boolean[] arbDirection = new boolean[4];//0=up, 1=down, 2=right, 3=left
    boolean bStop = true, bCollidedX, bItemHit = false;
    static Sprite sprChar;
    int nSHeight, nSWidth, nLayerCount, nC = 0;
    float fTileWidth, fTileHeight;
    Map map;
    HitTest hitTest;
    ItemSpawner itemSpawner;
    HUD hud;
    static TextureAtlas taBombExplode;
    static ArrayList<Bomb> arlBombs;
    GetTileID getTileID;


    public void setMap(Map _map, int _nYtiles, ItemSpawner spawner, HUD _hud, GetTileID _getTileID) {
        nYtiles = _nYtiles;
        map = _map;
        itemSpawner = spawner;
        hud = _hud;
        getTileID=_getTileID;
    }
    public void create() {

        nSHeight = Gdx.graphics.getHeight(); //use to make scaling
        nSWidth = Gdx.graphics.getWidth();
        nVelocityX = nSWidth * 10 / 1794;
        nVelocityY = nSHeight * 10 / 1080;
        fCharacterWidth = nSWidth * 110 / 1794;
        arbDirection[0] = true;
        batch = new SpriteBatch();
        taBomberman = new TextureAtlas(Gdx.files.internal("bomberchar.pack"));
        spBomberman = new Sprite[4];
        sprChar = new Sprite();
        fCharacterX = (map.nMapWidth - 175);
        fCharacterY = map.nMapHeight - 350;
        nTileHeight = (int) map.collisionLayer.getTileHeight();
        nTileWidth = (int) map.collisionLayer.getTileWidth();
        sprChar = new Sprite(taBomberman.findRegion("frame-0"));
        sprChar.setBounds(fCharacterX, fCharacterY, 1, 1);
        nCharacterWidth = nSWidth * 110 / 1794;

        taBomberman = new TextureAtlas(Gdx.files.internal("bomberchar.pack"));
        spBomberman = new Sprite[4];
        atrFront = new TextureRegion[2];
        atrBack = new TextureRegion[2];
        atrRight = new TextureRegion[2];
        atrLeft = new TextureRegion[2];
        for (int j = 0; j <= 1; j++) {
            atrFront[i] = taBomberman.findRegion("frame-" + i);
            i++;
        }
        animFront = new Animation(0.15f, atrFront);
        for (int j = 0; j <= 1; j++) {
            atrBack[j] = taBomberman.findRegion("frame-" + i);
            i++;
        }
        animBack = new Animation(0.15f, atrBack);
        for (int j = 0; j <= 1; j++) {
            atrLeft[j] = taBomberman.findRegion("frame-" + i);
            i++;
        }
        animLeft = new Animation(0.15f, atrLeft);
        for (int j = 0; j <= 1; j++) {
            atrRight[j] = taBomberman.findRegion("frame-" + i);
            i++;
        }
        animRight = new Animation(0.15f, atrRight);
        stateTime = 0f;
        sprChar = new Sprite(atrRight[0]);
        hitTest = new HitTest();

        //Load file for bomb animation and create an array list for bombs
        taBombExplode = new TextureAtlas(Gdx.files.internal("BombExploding/BombExploding.atlas"));
        arlBombs = new ArrayList<Bomb>();
    }

    @Override
    public void resize(int width, int height) {

    }

    //Add sprites using a button: https://github.com/MatthewBrock/TheDeepDarkTaurock/tree/FireBallScratch/core/src/taurockdeepdark
    public static void makeBomb() {
        arlBombs.add(new Bomb(taBombExplode, fCharacterX, fCharacterY, nCurrentIndex, sprChar));
    }

    public void setCharacterVelocity(int _nVx, int _nVy) {
        fCharacterVelocityX = nVelocityX * _nVx;
        fCharacterVelocityY = nVelocityY * _nVy;
    }



    public void render() {
        if (arbDirection[0]) {//up,down,right,left
            if (bStop) {
                currentFrame = atrBack[0];
            } else {
                currentFrame = animBack.getKeyFrame(stateTime, true);
            }
        } else if (arbDirection[1]) {
            if (bStop) {
                currentFrame = atrFront[0];
            } else {
                currentFrame = animFront.getKeyFrame(stateTime, true);
            }
        } else if (arbDirection[2]) {
            if (bStop) {
                currentFrame = atrLeft[0];
            } else {
                currentFrame = animLeft.getKeyFrame(stateTime, true);
            }
        } else if (arbDirection[3]) {
            if (bStop) {
                currentFrame = atrRight[0];
            } else {
                currentFrame = animRight.getKeyFrame(stateTime, true);
            }
        }
        int nCharX = (int) fCharacterX;
        int nCharY = (int) fCharacterY;
        sprChar = new Sprite(currentFrame);//Create the sprite of the character based on the current texture region frame
        sprChar.setX(nCharX);
        sprChar.setY(nCharY);


        nC++;
        fOldX = fCharacterX;
        fOldY = fCharacterY;
        bCollidedX = false;
        bCollidedY = false;
        fCharacterX += fCharacterVelocityX / 2;
        fCharacterY += fCharacterVelocityY / 2;


        sID = getTileID.getID(fCharacterX, fCharacterY, nCharacterWidth, sprChar);
        if (sID.equalsIgnoreCase("block")) {
            fCharacterX = fOldX;
            fCharacterY = fOldY;
        }
        if (nC % 60 == 0) {
            System.out.println("X: " + (int) fCharacterX / nTileWidth + " Y: " + (int) (nYtiles - (fCharacterY / nTileHeight)));
        }
            stateTime += Gdx.graphics.getDeltaTime();
            for (int i = 0; i < 4; i++) {//set all direction booleans to false unless it's the current direction
                if (nCurrentIndex == i) {
                } else {
                    arbDirection[i] = false;
                }
            }


            if (!itemSpawner.arBananas.get(0).isRemoved()) {
                bItemHit = hitTest.bHit(sprChar, itemSpawner.arBananas.get(0));
            }
            if (nC % 30 == 0) {
                System.out.println(bItemHit);
                System.out.println("s x: " + sprChar.getX() + " " + itemSpawner.arBananas.get(0).getX());

            }
            if (bItemHit) {
                hud.nScore++;
                itemSpawner.arBananas.get(0).remove(true);
                bItemHit = false;
            }

            batch.begin();
            batch.draw(sprChar, fCharacterX, fCharacterY);
            batch.end();

            //Render each bomb in the array list
            for (int i = 0; i < arlBombs.size(); i++) {
                arlBombs.get(i).render();
                if (arlBombs.get(i).isExploded) {    //Remove bomb once animation ends
                    arlBombs.remove(i);
                    System.out.println("Bomb removed");
                }
            }
        }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        sprChar.getTexture().dispose();
    }

    public void getBoolsBack(boolean[] _arbDirection, boolean _bStop, int _nCurrentIndex) {
        arbDirection = _arbDirection;
        bStop = _bStop;
        nCurrentIndex = _nCurrentIndex;
    }
}
