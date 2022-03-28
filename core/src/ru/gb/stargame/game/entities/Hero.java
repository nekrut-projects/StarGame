package ru.gb.stargame.game.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.gb.stargame.game.constants.ScreenConstants;
import ru.gb.stargame.game.managers.BulletManager;
import ru.gb.stargame.game.managers.ParticleManager;

import static ru.gb.stargame.game.constants.BulletConstants.*;
import static ru.gb.stargame.game.constants.HeroConstants.*;
import static ru.gb.stargame.game.constants.WeaponConstants.START_QUANTITY_BULLETS;

public class Hero {
    private TextureRegion texture;
    private ParticleManager particleManager;
    private Vector2 position;
    private Vector2 velocity;
    private float angle;
    private BulletManager bulletManager;
    private float fireTime;
    private Circle hitArea;
    private int hp;
    private Weapon[] weapons;

    public void addWeapon() {
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
        private int quantityBullets;
        private Vector2 coordOnShip;
        private int angleAttack;
        private boolean active;

        public Weapon(Vector2 coordOnShip, int angleAttack, boolean active) {
            this.active = active;
            this.coordOnShip = coordOnShip;
            this.angleAttack = angleAttack;
            this.quantityBullets = START_QUANTITY_BULLETS;
        }

        public void fire() {
            if (quantityBullets > 0 && active) {
                quantityBullets--;

                float x, y, vx, vy;
                x = position.x + coordOnShip.x * MathUtils.cosDeg(angle + coordOnShip.y);
                y = position.y + coordOnShip.x * MathUtils.sinDeg(angle + coordOnShip.y);
                vx = velocity.x + SPEEDUP_SIMPLE_BULLET * MathUtils.cosDeg(angle + angleAttack);
                vy = velocity.y + SPEEDUP_SIMPLE_BULLET * MathUtils.sinDeg(angle + angleAttack);

                bulletManager.setup(x, y, vx, vy);
            }
        }
    }

    public Hero(TextureRegion texture, BulletManager bulletRepository, ParticleManager particleManager) {
        this.weapons = new Weapon[]{
            new Weapon(new Vector2(28, 0), 0, true),
            new Weapon(new Vector2(28, 90), 10, false),
            new Weapon(new Vector2(28, -90), -10, false)
        };
        this.bulletManager = bulletRepository;
        this.particleManager = particleManager;
        this.velocity = new Vector2(0, 0);
        this.texture = texture;
        this.position = new Vector2(ScreenConstants.WIDTH / 2, ScreenConstants.HEIGHT / 2);
        this.angle = 0.0f;
        this.fireTime = 0.0f;
        this.hp = MAX_HP;
        this.hitArea = new Circle(position,texture.getRegionWidth()/2 -5);
    }

    public void update(float dt){
        fireTime += dt;
        hitArea.setPosition(position);
        position.mulAdd(velocity, dt);

        float stopCoeff = 1.0f - dt;
        if (stopCoeff < 0f){
            stopCoeff = 0.0f;
        }
        velocity.scl(stopCoeff);
        checkBorders();
    }

    public void shoot(){
        if (fireTime > RECHARGE_TIME) {
            fireTime = 0.0f;
            for (int i = 0; i < weapons.length; i++) {
                weapons[i].fire();
            }
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
        if (position.x > ScreenConstants.WIDTH - texture.getRegionWidth()/2){
            position.x = ScreenConstants.WIDTH - texture.getRegionWidth()/2;
            velocity.x *= -0.8;
        }
        if (position.y < texture.getRegionHeight()/2){
            position.y = texture.getRegionHeight()/2;
            velocity.y *= -0.8;
        }
        if (position.y > ScreenConstants.HEIGHT - texture.getRegionHeight()/2){
            position.y = ScreenConstants.HEIGHT - texture.getRegionHeight()/2;
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
        for (int i = 0; i < weapons.length; i++) {
            weapons[i].quantityBullets += bullets;
        }
    }

    public int getAmountBullets(){
        return weapons[0].quantityBullets;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
