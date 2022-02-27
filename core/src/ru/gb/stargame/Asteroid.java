package ru.gb.stargame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


import static ru.gb.stargame.ConstantsAsteroid.*;
import static ru.gb.stargame.ScreenManager.SCREEN_HEIGHT;
import static ru.gb.stargame.ScreenManager.SCREEN_WIDTH;

public class Asteroid {
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private float scale;
    private int countRenders = 0;
    private int angle;

    public Asteroid() {
        this.texture = new Texture("asteroid.png");
        this.position = new Vector2(MathUtils.random(-10, SCREEN_WIDTH - 20), -100);
        this.velocity = new Vector2(0, getSpeed());
        this.scale = 100f / velocity.y * 0.4f;
        this.angle = MathUtils.random( ANGLE, 0);
    }

    public void render (SpriteBatch batch) {
        batch.draw(texture, position.x - (texture.getWidth() / 2), position.y - (texture.getHeight() / 2),
                (texture.getWidth() / 2), (texture.getHeight() / 2), texture.getWidth(), texture.getHeight(),
                scale, scale, 0, 0, 0, texture.getWidth(), texture.getHeight(),
                false, false);
    }

    public void update(float dt){
        countRenders++;
        position.x += angle * dt;
        position.y += velocity.y * 4 * dt;

        if (position.y > SCREEN_HEIGHT + texture.getHeight()/2){
            position.x = MathUtils.random(-10, SCREEN_WIDTH - 20);
            position.y = -texture.getHeight()/2;
            velocity.y = getSpeed();
            scale = 100f / velocity.y * 0.4f;
            if (countRenders % 2 == 0 ){
                angle = MathUtils.random( ANGLE, -ANGLE);
                countRenders = 0;
            }
        }
    }

    private int getSpeed(){
        return MathUtils.random(MIN_SPEED, MAX_SPEED);
    }
}
