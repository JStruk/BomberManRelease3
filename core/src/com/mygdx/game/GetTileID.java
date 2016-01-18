package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Justin on 2016-01-18.
 */
public class GetTileID {
    public Map map;
    public int nTileWidth, nTileHeight;

    public GetTileID(Map _map) {
        map = _map;
        // nTileWidth = _nTileWidth;
        //   nTileHeight = _nTileHeight;

        nTileHeight = (int) map.collisionLayer.getTileHeight();
        nTileWidth = (int) map.collisionLayer.getTileWidth();
    }

    public String getID(float fX, float fY, int nWidth, Sprite spr) {
        String sID = "";
        //  if (map.collisionLayer.getCell((int) ((fX + sprChar.getWidth() / 2) / nTileWidth), (int) (17 - (fY / nTileHeight))) != null) {
        //}
        // sID = map.collisionLayer.getCell((int) ((fX + sprChar.getWidth() / 2) / nTileWidth), (int) (17 - (fY / nTileHeight))).getTile().getProperties().getKeys().next();
        if (map.collisionLayer.getCell((int) ((fX + nWidth / 2) / nTileWidth), (int) (1 + (fY / nTileHeight))) != null) {
            sID = map.collisionLayer.getCell((int) ((fX + nWidth / 2) / nTileWidth), (int) (1 + (fY / nTileHeight))).getTile().getProperties().getKeys().next();
        }
        if (map.collisionLayer.getCell((int) (fX / nTileWidth), ((int) ((fY + nWidth / 2) / nTileHeight))) != null) {//leftcollision
            sID = map.collisionLayer.getCell((int) (fX / nTileWidth), ((int) (fY + nWidth / 2) / nTileHeight)).getTile().getProperties().getKeys().next();
        } else if (map.collisionLayer.getCell((int) ((fX + spr.getWidth()) / nTileWidth), (int) (1 + (fY + spr.getWidth() / 2) / nTileHeight)) != null) {
            sID = map.collisionLayer.getCell((int) ((fX + spr.getWidth()) / nTileWidth), (int) (1 + (fY + spr.getWidth() / 2) / nTileHeight)).getTile().getProperties().getKeys().next();
        } else if (map.collisionLayer.getCell((int) ((fX + spr.getWidth() / 2) / nTileWidth), (int) ((fY + spr.getHeight() / 2) / nTileHeight)) != null) {
            sID = map.collisionLayer.getCell((int) ((fX + spr.getWidth() / 2) / nTileWidth), (int) ((fY + spr.getHeight() / 2) / nTileHeight)).getTile().getProperties().getKeys().next();
            //   System.out.println("X: " + (int) ((fCharacterX + sprChar.getWidth() / 2) / nTileWidth) + " Y: " + (int) (16 - ((fCharacterY + sprChar.getHeight() / 2) / nTileHeight)));
        }
        // sID = map.collisionLayer.getCell((int) ((fX + nWidth / 2) / nTileWidth), (int) (17 - (fY / nTileHeight))).getTile().getProperties().getKeys().next();
        return sID;
    }
}


