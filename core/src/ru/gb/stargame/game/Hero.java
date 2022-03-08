package ru.gb.stargame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.gb.stargame.screen.ScreenManager;

import static ru.gb.stargame.game.constants.BulletConstants.*;
import static ru.gb.stargame.game.constants.HeroConstants.*;

public class Hero {
    private TextureRegion texture;
    private Vector2 position;
    private Vector2 velocity;
    private float angle;
    private BulletManager bulletManager;
    private float fireTime;
    private int score;
    private int scoreView;
    private Circle hitArea;
    private int hp;


    public Hero(BulletManager bulletRepository, TextureRegion texture) {
        this.bulletManager = bulletRepository;
        this.velocity = new Vector2(0, 0);
        this.texture = texture;
        this.position = new Vector2(ScreenManager.SCREEN_WIDTH / 2, ScreenManager.SCREEN_HEIGHT / 2);
        this.angle = 0.0f;
        this.fireTime = 0.0f;
        this.score = 0;
        this.hp = MAX_HP;
        this.hitArea = new Circle(position,texture.getRegionWidth());
    }

    public void render (SpriteBatch batch) {
        batch.draw(texture, position.x - 32, position.y - 32, 32, 32, 64, 64,
                1, 1, angle);
    }

    public void update(float dt){
        fireTime += dt;
        if (scoreView < score) {
            scoreView += 300 * dt;
            if (scoreView > score) {
                scoreView = score;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            angle += dt * 180;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            angle -= dt * 180;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velocity.x += MathUtils.cosDeg(angle) * POWER_SHIP * dt;
            velocity.y += MathUtils.sinDeg(angle) * POWER_SHIP * dt;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velocity.x += -MathUtils.cosDeg(angle) * (POWER_SHIP / 2) * dt;
            velocity.y += -MathUtils.sinDeg(angle) * (POWER_SHIP / 2) * dt;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            shoot();
        }
        position.mulAdd(velocity, dt);

        float stopCoeff = 1.0f - dt;
        if (stopCoeff < 0f){
            stopCoeff = 0.0f;
        }
        velocity.scl(stopCoeff);

        checkBorders();
    }

    private void shoot(){
        if (fireTime > RECHARGE_TIME){
            fireTime = 0.0f;


            bulletManager.setup(position.x + MathUtils.cosDeg(angle + 90) * 20,
                    position.y + MathUtils.sinDeg(angle + 90) * 20,
                    MathUtils.cosDeg(angle) * SPEEDUP_SIMPLE_BULLET + velocity.x,
                    MathUtils.sinDeg(angle) * SPEEDUP_SIMPLE_BULLET + velocity.y);

            bulletManager.setup(position.x + MathUtils.cosDeg(angle - 90) * 20,
                    position.y + MathUtils.sinDeg(angle - 90) * 20,
                    MathUtils.cosDeg(angle) * SPEEDUP_SIMPLE_BULLET + velocity.x,
                    MathUtils.sinDeg(angle) * SPEEDUP_SIMPLE_BULLET + velocity.y);
        }
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getPosition() {
        return position;
    }

    private void checkBorders() {
        if (position.x < texture.getRegionWidth()/2){
            position.x = texture.getRegionWidth() / 2;
            velocity.x *= -0.8;
        }
        if (position.x > ScreenManager.SCREEN_WIDTH - texture.getRegionWidth()/2){
            position.x = ScreenManager.SCREEN_WIDTH - texture.getRegionWidth()/2;
            velocity.x *= -0.8;
        }
        if (position.y < texture.getRegionHeight()/2){
            position.y = texture.getRegionHeight()/2;
            velocity.y *= -0.8;
        }
        if (position.y > ScreenManager.SCREEN_HEIGHT - texture.getRegionHeight()/2){
            position.y = ScreenManager.SCREEN_HEIGHT - texture.getRegionHeight()/2;
            velocity.y *= -0.8;
        }
    }

    public int getScoreView() {
        return scoreView;
    }

    public void addScore(int amount) {
        score += amount;
    }

    public int takeDamage(int damage){
        return hp -= damage;
    }

    public int getHp() {
        return hp;
    }

    public Circle getHitArea() {
        return hitArea;
    }
}
