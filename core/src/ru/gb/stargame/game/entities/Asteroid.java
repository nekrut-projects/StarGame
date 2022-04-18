package ru.gb.stargame.game.entities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.gb.stargame.game.constants.AsteroidConstants;
import ru.gb.stargame.game.helpers.Poolable;


import static ru.gb.stargame.game.constants.AsteroidConstants.*;
import static ru.gb.stargame.game.constants.ScreenConstants.WIDTH;

public class Asteroid implements Poolable {
    private Vector2 position;
    private Vector2 velocity;
    private float scale;
    private boolean active;
    private float angle;
    private int hp;
    private float rotationSpeed;
    private Circle hitArea;

    public Asteroid() {
        this.rotationSpeed = MathUtils.random(-180.0f, 180.0f);
        this.position = new Vector2(MathUtils.random(-10, WIDTH - 20), -100);
        this.velocity = new Vector2(0, MathUtils.random(MIN_SPEED, MAX_SPEED));
        this.scale = 1.0f;
        this.angle = MathUtils.random( ANGLE, 0);
        this.active = true;
        this.hitArea = new Circle(0,0,0);
        this.hp = HP_MAX;
    }

    public void update(float dt){
        position.mulAdd(velocity, dt);
        angle += rotationSpeed * dt;
        hitArea.setPosition(position);
    }

    public float getScale() {
        return scale;
    }

    public Vector2 getVelocity() {
        return velocity;
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

    public void activate(float x, float y, float vx, float vy, float scale, float radius, int hp) {
        active = true;
        position.set(x, y);
        velocity.set(vx, vy);

        angle = MathUtils.random(0.0f, 360.0f);
        rotationSpeed = MathUtils.random(-180.0f, 180.0f);
        hitArea.setPosition(x, y);
        this.scale = scale;
        this.hp = (int) (hp * scale);
        hitArea.setRadius(radius);
    }

    public Circle getHitArea() {
        return hitArea;
    }

    public int takeDamage(int damage){
        return hp -= damage;
    }

    public float getAngle() {
        return angle;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
