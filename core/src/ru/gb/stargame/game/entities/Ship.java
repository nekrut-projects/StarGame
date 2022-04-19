package ru.gb.stargame.game.entities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.gb.stargame.game.constants.BulletConstants;
import ru.gb.stargame.game.constants.ScreenConstants;
import ru.gb.stargame.game.constants.ShipConstants;
import ru.gb.stargame.game.managers.BulletManager;

import static ru.gb.stargame.game.constants.BulletConstants.*;
import static ru.gb.stargame.game.constants.BulletConstants.SPEEDUP_SIMPLE_BULLET;
import static ru.gb.stargame.game.constants.ShipConstants.POWER_SHIP;
import static ru.gb.stargame.game.constants.WeaponConstants.START_QUANTITY_BULLETS;

abstract public class Ship {
    private Vector2 position;
    private Vector2 velocity;
    private float angle;
    private BulletManager bulletManager;
    private float fireTime;
    private Circle hitArea;
    private int hp;
    private Ship.Weapon[] weapons;
    private int quantityBullets;

    public void upgradeWeapon() {
        for (int i = 0; i < weapons.length; i++) {
            if (!weapons[i].active){
                weapons[i].active = true;
            }
        }
    }

    public void addHp(int medicine) {
        this.hp += medicine;
    }

    private class Weapon {
        private Vector2 coordOnShip;
        private int angleAttack;
        private boolean active;

        public Weapon(Vector2 coordOnShip, int angleAttack, boolean active) {
            this.active = active;
            this.coordOnShip = coordOnShip;
            this.angleAttack = angleAttack;
        }

        public void fire(OwnerBullet ownerBullet, int damage) {
            if (quantityBullets > 0 && active) {
                quantityBullets--;

                float x, y, vx, vy;
                x = position.x + coordOnShip.x * MathUtils.cosDeg(angle + coordOnShip.y);
                y = position.y + coordOnShip.x * MathUtils.sinDeg(angle + coordOnShip.y);
                vx = velocity.x + SPEEDUP_SIMPLE_BULLET * MathUtils.cosDeg(angle + angleAttack);
                vy = velocity.y + SPEEDUP_SIMPLE_BULLET * MathUtils.sinDeg(angle + angleAttack);

                bulletManager.setup(x, y, vx, vy, ownerBullet, damage);
            }
        }
    }

    public Ship(BulletManager bulletManager) {
        this.bulletManager = bulletManager;
        this.weapons = new Ship.Weapon[]{
                new Weapon(new Vector2(28, 0), 0, true),
                new Weapon(new Vector2(28, 90), 10, false),
                new Weapon(new Vector2(28, -90), -10, false)
        };
        this.quantityBullets = START_QUANTITY_BULLETS;
        this.velocity = new Vector2(0, 0);
        this.position = new Vector2(ScreenConstants.WIDTH / 2, ScreenConstants.HEIGHT / 2);
        this.angle = 0.0f;
        this.fireTime = 0.0f;
        this.hp = ShipConstants.MAX_HP;
        this.hitArea = new Circle(position, ShipConstants.WIDTH/2 -5);
    }

    public void update(float dt){
        fireTime += dt;
        position.mulAdd(velocity, dt);
        hitArea.setPosition(position);

        float stopCoeff = 1.0f - dt;
        if (stopCoeff < 0f){
            stopCoeff = 0.0f;
        }
        velocity.scl(stopCoeff);
        checkBorders();
    }

    public void shoot(OwnerBullet ownerBullet, int damage){
        if (fireTime > ShipConstants.RECHARGE_TIME) {
            fireTime = 0.0f;
            for (int i = 0; i < weapons.length; i++) {
                weapons[i].fire(ownerBullet, damage);
            }
        }
    }

    public void accelerate(float dt){
        velocity.x += MathUtils.cosDeg(angle) * POWER_SHIP * dt;
        velocity.y += MathUtils.sinDeg(angle) * POWER_SHIP * dt;

    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getPosition() {
        return position;
    }

    private void checkBorders() {
        if (position.x < ShipConstants.WIDTH/2){
            position.x = ShipConstants.WIDTH / 2;
            velocity.x *= -0.8;
        }
        if (position.x > ScreenConstants.WIDTH - ShipConstants.WIDTH/2){
            position.x = ScreenConstants.WIDTH - ShipConstants.WIDTH/2;
            velocity.x *= -0.8;
        }
        if (position.y < ShipConstants.HEIGHT/2){
            position.y = ShipConstants.HEIGHT/2;
            velocity.y *= -0.8;
        }
        if (position.y > ScreenConstants.HEIGHT - ShipConstants.HEIGHT/2){
            position.y = ScreenConstants.HEIGHT - ShipConstants.HEIGHT/2;
            velocity.y *= -0.8;
        }
    }

    public int takeDamage(int damage){
        return hp -= damage;
    }

    public int getHp() {
        return hp;
    }

    public float getAngle() {
        return angle;
    }

    public Circle getHitArea() {
        return hitArea;
    }

    public void addBullets(int bullets){
        this.quantityBullets += bullets;
    }

    public int getAmountBullets(){
        return quantityBullets;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    protected int decreaseHp(int damage){
        return hp -= damage;
    }

}
