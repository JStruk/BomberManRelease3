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
    int i = 0, nCurrentIndex;
    Animation animFront, animBack, animRight, animLeft;
    float stateTime, fCharacterVelocityX = 0, fCharacterVelocityY = 0, fCharacterX, fCharacterY, fCharacterWidth, fOldX, fOldY;
    int nVelocityX, nVelocityY;
    TextureRegion currentFrame;
    TextureRegion[] atrFront, atrBack, atrLeft, atrRight;
    boolean[] arbDirection = new boolean[4];//0=up, 1=down, 2=right, 3=left
    boolean bStop = true, bCollidedX, bItemHit = false;
    Sprite sprChar;
    int nSHeight, nSWidth, nLayerCount, nC = 0;
    float fTileWidth, fTileHeight;
    Map map;
    HitTest hitTest;
    ItemSpawner itemSpawner;
    HUD hud;

   /* public void setMap(Map _map) {
        map=_map;
    }*/


    public void setMap(Map _map, int _nYtiles, ItemSpawner spawner, HUD _hud) {
        nYtiles = _nYtiles;
        map = _map;
        itemSpawner = spawner;
        hud = _hud;
    }

    public void create() {

//        collisionObjects = map.tiledMap.getLayers().get("CollisionLayer").getObjects();
        // cam = map.getCam();
        nSHeight = Gdx.graphics.getHeight(); //use to make scaling
        nSWidth = Gdx.graphics.getWidth();
        nVelocityX = nSWidth * 10 / 1794;
        nVelocityY = nSHeight * 10 / 1080;
        fCharacterWidth = nSWidth * 110 / 1794;
        arbDirection[0] = true;
        batch = new SpriteBatch();
        taBomberman = new TextureAtlas(Gdx.files.internal("bomberchar.pack"));
        //txChar = new Texture(new TextureRegion(taBomberman.findRegion("frame-0.png")));
        //    txChar= new Texture(taBomberman.findRegion("frame-0.png"));
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
    }

    @Override
    public void resize(int width, int height) {

    }


    public void setCharacterVelocity(int _nVx, int _nVy) {
        fCharacterVelocityX = nVelocityX * _nVx;
        fCharacterVelocityY = nVelocityY * _nVy;
    }

    public String getTileID(float fX, float fY, int nWidth) {
        String sID = "";
        //  if (map.collisionLayer.getCell((int) ((fX + sprChar.getWidth() / 2) / nTileWidth), (int) (17 - (fY / nTileHeight))) != null) {
        //}
        // sID = map.collisionLayer.getCell((int) ((fX + sprChar.getWidth() / 2) / nTileWidth), (int) (17 - (fY / nTileHeight))).getTile().getProperties().getKeys().next();
        if (map.collisionLayer.getCell((int) ((fX + nWidth / 2) / nTileWidth), (int) (1 + (fY / nTileHeight))) != null) {
            sID = map.collisionLayer.getCell((int) ((fX + nWidth / 2) / nTileWidth), (int) (1 + (fY / nTileHeight))).getTile().getProperties().getKeys().next();
        }
        if (map.collisionLayer.getCell((int) (fX / nTileWidth), ((int) ((fY + nWidth / 2) / nTileHeight))) != null) {//leftcollision
            sID = map.collisionLayer.getCell((int) (fX / nTileWidth), ((int) (fY + nWidth / 2) / nTileHeight)).getTile().getProperties().getKeys().next();
        } else if (map.collisionLayer.getCell((int) ((fCharacterX + sprChar.getWidth()) / nTileWidth), (int) (1 + (fCharacterY + sprChar.getWidth() / 2) / nTileHeight)) != null) {
            sID = map.collisionLayer.getCell((int) ((fCharacterX + sprChar.getWidth()) / nTileWidth), (int) (1 + (fCharacterY + sprChar.getWidth() / 2) / nTileHeight)).getTile().getProperties().getKeys().next();
        } else if (map.collisionLayer.getCell((int) ((fCharacterX + sprChar.getWidth() / 2) / nTileWidth), (int) ((fCharacterY + sprChar.getHeight() / 2) / nTileHeight)) != null) {
            sID = map.collisionLayer.getCell((int) ((fCharacterX + sprChar.getWidth() / 2) / nTileWidth), (int) ((fCharacterY + sprChar.getHeight() / 2) / nTileHeight)).getTile().getProperties().getKeys().next();
            //   System.out.println("X: " + (int) ((fCharacterX + sprChar.getWidth() / 2) / nTileWidth) + " Y: " + (int) (16 - ((fCharacterY + sprChar.getHeight() / 2) / nTileHeight)));
        }
        // sID = map.collisionLayer.getCell((int) ((fX + nWidth / 2) / nTileWidth), (int) (17 - (fY / nTileHeight))).getTile().getProperties().getKeys().next();
        return sID;
    }
   /* public boolean getTileID(float fX, float fY, int nWidth, String sID) {// this is slightly complicated but its basically grabbing the tile that the character is standing on and getting the ID
        boolean bCollided = false;
        //for (nLayerCount = 0; nLayerCount < armMaps[nCurrentMap].tiledMap.getLayers().getCount() - 1; nLayerCount++) {

            bCollided = map.collisionLayer.getCell((int) ((fX + nWidth / 4) / nTileWidth), (int) (fY / nTileHeight))
                    .getTile().getProperties().containsKey(sID);

            bCollided |=map.collisionLayer.getCell((int) ((fX + 3 * nWidth / 4) / nTileWidth), (int) (fY / nTileHeight))
                    .getTile().getProperties().containsKey(sID);

            bCollided |=map.collisionLayer.getCell((int) ((fX + nWidth / 2) / nTileWidth), (int) (fY / nTileHeight))
                    .getTile().getProperties().containsKey(sID);
       // }
        return bCollided;
    }/*

    /*private void getTiles(int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
        // TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("walls");
        rectPool.freeAll(tiles);
        tiles.clear();
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                TiledMapTileLayer.Cell cell = map.collisionLayer.getCell(x, y);
                if (cell != null) {
                    Rectangle rect = rectPool.obtain();
                    rect.set(x, y, 1, 1);
                    tiles.add(rect);
                }
            }
        }
    }*/


    public void render() {

        // bItemHit = false;
      /*  for (int i = 0; i < collisionObjects.getCount(); i++) {
            RectangleMapObject obj = (RectangleMapObject) collisionObjects.get(i);
            Rectangle rect = obj.getRectangle();

          //  rect.set(fCharacterX, fCharacterY, 32,31);

           // Rectangle rectobject = obj.getRectangle();
            rect.x /= nTileWidth;
            rect.y /= nTileHeight;
          //  rectobject.width /= nTileWidth;
          ///  rectobject.height /= nTileHeight;


            if(rect.overlaps(sprChar.getBoundingRectangle())) {
                System.out.println("collision");
            }
        }*/
        // cam.setToOrtho(false, fCharacterX, fCharacterY);
        // cam.position.y = fCharacterY;
        //cam.position.x = fCharacterX;
        //  map.setCamera(cam);
        nC++;
        fOldX = fCharacterX;
        fOldY = fCharacterY;
        bCollidedX = false;
        bCollidedY = false;
        //Gdx.gl.glClearColor(0, 0, 0, 1);
        // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //  fCharacterX += fCharacterVelocityX / 2;
        // fCharacterY += fCharacterVelocityY / 2;
        // (int) ((fCharacterY+sprChar.getHeight()/2)/nTileHeight)
       /*if (map.collisionLayer.getCell((int) (fCharacterX / nTileWidth), ((int) (16 - ((fCharacterY + sprChar.getHeight() / 2) / nTileHeight)))) != null) {//leftcollision
            bCollidedX = map.collisionLayer.getCell((int) (fCharacterX / nTileWidth), ((int) (16 -(fCharacterY + sprChar.getHeight() / 2) / nTileHeight))).getTile().getProperties().containsKey("Block");
            System.out.println("X: " + (int) (fCharacterX / nTileWidth) + " Y: " + (int) (16 - ((fCharacterY + sprChar.getHeight() / 2) / nTileHeight)));
        }
        if (map.collisionLayer.getCell((int) ((fCharacterX + sprChar.getWidth() / 2) / nTileWidth), (int) (17 - (fCharacterY / nTileHeight))) != null) {//bottomcollision
            bCollidedY = map.collisionLayer.getCell((int) ((fCharacterX + sprChar.getWidth() / 2) / nTileWidth), (int) (17 - (fCharacterY / nTileHeight))).getTile().getProperties().containsKey("Block");
        }
        if (map.collisionLayer.getCell((int) (2 + ((fCharacterX + sprChar.getWidth()) / nTileWidth)), (int) (17 - (fCharacterY + sprChar.getWidth() / 2) / nTileHeight)) != null) {
            System.out.println("right");
            bCollidedX = map.collisionLayer.getCell((int) (2 + ((fCharacterX + sprChar.getWidth()) / nTileWidth)), (int) (17 - (fCharacterY + sprChar.getWidth() / 2) / nTileHeight)).getTile().getProperties().containsKey("Block");
            System.out.println("X: " + (int) (2 + ((fCharacterX + sprChar.getWidth()) / nTileWidth)) + "Y: " + (int) (17 - (fCharacterY + sprChar.getWidth() / 2) / nTileHeight));
            // bCollidedX = map.collisionLayer.getCell(((int) ((fCharacterX + sprChar.getWidth() / nTileWidth))), ((int) (((fCharacterY + sprChar.getWidth() / 2) / nTileHeight)))).getTile().getProperties().containsKey("Block");
        }
        if (map.collisionLayer.getCell((int) ((fCharacterX + sprChar.getWidth() / 2) / nTileWidth), (int) (15 - ((fCharacterY + sprChar.getHeight() / 2) / nTileHeight))) != null) {
            System.out.println("top");
            bCollidedY = map.collisionLayer.getCell((int) ((fCharacterX + sprChar.getWidth() / 2) / nTileWidth), (int) (15 - ((fCharacterY + sprChar.getHeight() / 2) / nTileHeight))).getTile().getProperties().containsKey("Block");
            //   System.out.println("X: "+(int)((fCharacterX + sprChar.getWidth() / 2) / nTileWidth) + " Y: "+(int)(16-((fCharacterY + sprChar.getHeight() / 2) / nTileHeight)));
        }
       /* if (map.collisionLayer.getCell((int) (fCharacterX / nTileWidth), (int) (nYtiles-(fCharacterY / nTileHeight))) != null) {
            bCollidedX = map.collisionLayer.getCell((int) (fCharacterX / nTileWidth), (int)(nYtiles-(fCharacterY / nTileHeight))).getTile().getProperties().containsKey("Block");
s
        }*/
        //  bCollidedX = getTileID(fCharacterX, fCharacterY,nSWidth, "Block");
        fCharacterX += fCharacterVelocityX / 2;
        fCharacterY += fCharacterVelocityY / 2;
        sID = getTileID(fCharacterX, fCharacterY, nCharacterWidth);
        //  hitTest.sprites(sprChar, itemSpawner.banana);


        if (sID.equalsIgnoreCase("block")) {
            fCharacterX = fOldX;
            fCharacterY = fOldY;
        }
        // System.out.println(sID);
        if (nC % 60 == 0) {
            System.out.println("X: " + (int) fCharacterX / nTileWidth + " Y: " + (int) (nYtiles - (fCharacterY / nTileHeight)));
        }
        //  updateCharacter(fCharacterX, fCharacterY, sprChar.getWidth(), sprChar.getHeight());
        // }
        //}
        if (bCollidedX || bCollidedY) {
            fCharacterX = fOldX;
            fCharacterY = fOldY;
            // fCharacterX=100;
            // fCharacterY=100;
        }
    /*    batch.begin();
        batch.draw(sprChar, fCharacterX, fCharacterY);
        batch.end();*/
        stateTime += Gdx.graphics.getDeltaTime();
        for (int i = 0; i < 4; i++) {//set all direction booleans to false unless it's the current direction
            if (nCurrentIndex == i) {
            } else {
                arbDirection[i] = false;
            }
        }

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
        bItemHit = hitTest.bHit(sprChar, itemSpawner.banana);
        if (nC % 30 == 0) {
            System.out.println(bItemHit);
            System.out.println("s x: " + sprChar.getX() + " " + itemSpawner.banana.getX());

        }
        if (bItemHit) {
            hud.nScore++;
            bItemHit = false;
        }

        batch.begin();
        batch.draw(sprChar, fCharacterX, fCharacterY);
        batch.end();
    }

    /* public void updateCharacter(float fX, float fY, float fCharWidth, float fCharHeight) {
         Rectangle koalaRect = rectPool.obtain();
         koalaRect.set(fX, fY, fCharWidth, fCharHeight);
         int startX, startY, endX, endY;
         if (fCharacterVelocityX > 0) {
             startX = endX = (int) (fX + fCharWidth + fCharacterVelocityX);
         }
           else {
          startX = endX = (int)(fX+ fCharacterVelocityX);
            }
         startY = (int) (fY);
         endY = (int) (fY + fCharHeight);
         getTiles(startX, startY, endX, endY, tiles);
         fCharacterX += fCharacterVelocityX;
         for (Rectangle tile : tiles) {
             if (koalaRect.overlaps(tile)) {
                 System.out.println("collision");
                 fCharacterVelocityX = 0;
                 fCharacterX=fOldX;
                 break;
             }
         }
      //   fCharacterX = fOldX;
     }
 */
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
