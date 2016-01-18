package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by Justin on 2016-01-17.
 */
public class ItemSpawner {
    SpriteBatch sb;
    ArrayList<Item> arBananas = new ArrayList<Item>();
    GetTileID getTileID;

    public ItemSpawner(GetTileID _getTileID) {
        getTileID = _getTileID;
    }

    public void create() {
        sb = new SpriteBatch();
    }

    void createBanana() {
        int w = Gdx.graphics.getWidth() - 32;
        int h = Gdx.graphics.getHeight() - 32;
        int randnumX = 32 + (int) (Math.random() * ((w - 32) + 1));
        int randnumY = 100 + (int) (Math.random() * ((h - 100) + 1));
        Item banana = new Item(new Texture(Gdx.files.internal("banana.png")));
      //  banana.setBounds(150, 150, 25, 25);
        arBananas.add(banana);
        String sID = getTileID.getID(randnumX, randnumY,(int) arBananas.get(0).getWidth(), arBananas.get(0));
       // if(!sID.equalsIgnoreCase("block")|| !sID.equalsIgnoreCase("")) {
        if(sID.equalsIgnoreCase("spawnable")) {
            banana.setBounds(randnumX, randnumY, 25, 25);
        }
      /*  }else{
            createBanana();
        }*/
    }

    public void render() {
        createBanana();
        sb.begin();
        for (int i = 0; i < arBananas.size(); i++) {
            if (!arBananas.get(i).isRemoved()) {
              //  sb.draw(arBananas.get(i), 150, 150, 25, 25);
                sb.draw(arBananas.get(i), (int) arBananas.get(i).getX(), (int) arBananas.get(i).getWidth(),(int) arBananas.get(i).getWidth(), arBananas.get(i).getHeight());
            }
        }
        sb.end();
    }
}
