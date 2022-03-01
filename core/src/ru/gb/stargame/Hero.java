package ru.gb.stargame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Hero {
    private Texture texture;
    private Vector2 position;
    private float angle;
    private final int power = 500;
    private Vector2 lastMove;

    public Hero() {
        this.lastMove = new Vector2(0, 0);
        this.texture = new Texture("ship.png");
        this.position = new Vector2(ScreenManager.SCREEN_WIDTH / 2, ScreenManager.SCREEN_HEIGHT / 2);
        angle = 0.0f;
    }

    public void render (SpriteBatch batch) {
        batch.draw(texture, position.x - 32, position.y - 32, 32, 32, 64, 64,
                1, 1, angle, 0, 0, 64, 64, false, false);
    }

    public void update(float dt){
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            angle += dt * 180;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            angle -= dt * 180;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            lastMove.set(MathUtils.cosDeg(angle) * power * dt, MathUtils.sinDeg(angle) * power * dt);
            position.x += lastMove.x;
            position.y += lastMove.y;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            lastMove.set(MathUtils.cosDeg(angle) * (power / 2) * dt, MathUtils.sinDeg(angle) * power * dt);
            position.x -= lastMove.x;
            position.y -= lastMove.y;
        }

        checkBorders();
    }

    public Vector2 getLastMove() {
        return lastMove;
    }

    private void checkBorders() {
        if (position.x < 32){
            position.x = 32;
        }
        if (position.x > ScreenManager.SCREEN_WIDTH - 32){
            position.x = ScreenManager.SCREEN_WIDTH - 32;
        }
        if (position.y < 32){
            position.y = 32;
        }
        if (position.y > ScreenManager.SCREEN_HEIGHT - 32){
            position.y = ScreenManager.SCREEN_HEIGHT - 32;
        }
    }
}
