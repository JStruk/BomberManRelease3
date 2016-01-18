package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Release2 extends Stage implements Screen {
    Map map;
    Thumbstick thumbstick;
    Character character;
    ScreenHandler screenHandler;
    HUD hud;
    ItemSpawner itemSpawner;
    BombButton btnBomb;

    public Release2(ScreenHandler _screenHandler) {
        this.screenHandler = _screenHandler;
    }

    public void create() {
        thumbstick = new Thumbstick();
        map = new Map();
        thumbstick.create();
        map.ThumbstickHeight(thumbstick.fTouchPadHeight);
        map.create();
        hud = new HUD();
        hud.create();
        itemSpawner = new ItemSpawner();
        itemSpawner.create();
        character = new Character();
        character.setMap(map, map.nYTiles, itemSpawner, hud);
        character.create();
        thumbstick.setCharacter(character, character.arbDirection, character.bStop);

        btnBomb = new BombButton();
        this.addActor(btnBomb.ibBombDrop);
        this.addActor(thumbstick.touchpad);
    }


    @Override
    public void show() {
        this.create();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        map.render();
        thumbstick.render();
        character.render();
        hud.render();
        itemSpawner.render();
        this.act(Gdx.graphics.getDeltaTime());
        this.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        //   this.render();

    }

    @Override
    public void dispose() {

    }
}
