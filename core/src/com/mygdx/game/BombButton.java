package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Created by dinnatruong on 16-01-18.
 */
public class BombButton extends Actor {
    private TextureAtlas taBombDrop;
    ImageButton.ImageButtonStyle ibsBombDrop;
    private Skin skBombDrop;
    ImageButton ibBombDrop;

    public BombButton() {
        //Create an image button to "drop a bomb" when pressed
        //https://github.com/captainkesty/imagebutton.git
        //https://github.com/MatthewBrock/TheDeepDarkTaurock.git
        taBombDrop = new TextureAtlas("BombBtn.atlas");
        skBombDrop = new Skin();
        skBombDrop.addRegions(taBombDrop);
        ibsBombDrop = new ImageButton.ImageButtonStyle();
        ibsBombDrop.up = skBombDrop.getDrawable("bombbtnON");
        ibsBombDrop.down = skBombDrop.getDrawable("bombbtnOFF");
        ibsBombDrop.checked = skBombDrop.getDrawable("bombbtnON");
        ibBombDrop = new ImageButton(ibsBombDrop);
        ibBombDrop.setSize(120, 120);
        ibBombDrop.setPosition(Gdx.graphics.getWidth() - (ibBombDrop.getWidth() + 30), 0);
        ibBombDrop.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (Character.arlBombs.size() < 1) {
                    Character.makeBomb(); //Create a bomb
                    System.out.println("Bomb dropped");
                } else {
                    System.out.println("Cannot drop bomb yet");
                }
            }
        });
    }
}