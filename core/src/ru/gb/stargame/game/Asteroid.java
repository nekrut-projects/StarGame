package ru.gb.stargame.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.gb.stargame.game.helpers.Poolable;


import static ru.gb.stargame.game.constants.AsteroidConstants.*;
import static ru.gb.stargame.screen.ScreenManager.SCREEN_WIDTH;

public class Asteroid implements Poolable {
    private Vector2 position;
    private Vector2 velocity;
    private float scale;
    private boolean active;
    private int angle;
    private int hp;
    private float rotationSpeed;
    private Circle hitArea;

    public Asteroid() {
        this.rotationSpeed = MathUtils.random(-180.0f, 180.0f);
        this.position = new Vector2(MathUtils.random(-10, SCREEN_WIDTH - 20), -100);
        this.velocity = new Vector2(0, MathUtils.random(MIN_SPEED, MAX_SPEED));
        this.scale = 100f / velocity.y * 0.4f;
        this.angle = MathUtils.random( ANGLE, 0);
        this.active = true;
        this.hitArea = new Circle(0,0,0);
    }

    public void update(float dt){
        position.x += angle * dt;
        position.y += velocity.y * 4 * dt;
    }

    public float getScale() {
        return scale;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public Vector2 getPosition() {
        return position;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void deactivate(){
        this.active = false;
    }

    public void activate(int x, int y, int vx, int vy, float scale, float radius) {
        position.set(x, y);
        velocity.set(vx, vy);
        hitArea.set(position, radius);
        this.active = true;
        this.hp = HP_MAX;
        this.scale = scale;
        this.rotationSpeed = MathUtils.random(-180.0f, 180.0f);

    }

    public Circle getHitArea() {
        return hitArea;
    }

    public int takeDamage(int damage){
        return hp -= damage;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public int getHp() {
        return hp;
    }
}
