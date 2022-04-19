package ru.gb.stargame.game.entities;

import com.badlogic.gdx.math.Vector2;
import ru.gb.stargame.game.constants.BulletConstants;
import ru.gb.stargame.game.helpers.Poolable;

import static ru.gb.stargame.game.constants.BulletConstants.*;

public class Bullet implements Poolable {

    private OwnerBullet owner;
    private Vector2 position;
    private Vector2 velocity;
    private boolean active;
    private int damage;

    public Bullet() {
        this.damage = DAMAGE_SIMPLE_BULLET;
        this.position = new Vector2();
        this.velocity = new Vector2();
        this.active = false;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void deactivate(){
        this.active = false;
    }

    public void update(float dt) {
        position.mulAdd(velocity, dt);
    }

    public void activate(float x, float y, float vx, float vy, OwnerBullet owner, int damage) {
        position.set(x, y);
        velocity.set(vx, vy);
        this.active = true;
        this.owner = owner;
        this.damage = damage;
    }

    public OwnerBullet getOwner() {
        return owner;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public int getDamage() {
        return damage;
    }
}
