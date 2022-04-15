package ru.gb.stargame.game.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.gb.stargame.game.constants.AsteroidConstants;
import ru.gb.stargame.game.managers.BulletManager;

public class Player {
    private Hero hero;
    private int score;
    private int scoreView;
    private int coins;
    private int destroyedAsteroid;
    private int remainsDestroy;
    private int difficulty;
    private int magnetismLevel;

//    private EffectsManager effectsManager;

    public Player(TextureRegion texture, BulletManager bulletManager) {
        this.hero = new Hero(texture, bulletManager);
        this.score = 0;
        this.scoreView = 0;
        this.coins = 0;
        this.destroyedAsteroid = 0;
//        this.effectsManager = effectsManager;
        this.difficulty = 1;
        this.remainsDestroy = AsteroidConstants.NEEDED_DESTROY_ASTEROIDS_ON_LEVEL;
        this.magnetismLevel = 2;
    }

    public void update(float dt) {
//        checkPressedKeys(dt);
        hero.getMagneticArea().setRadius(hero.getHitArea().radius * magnetismLevel);
        hero.update(dt);
        if (scoreView < score) {
            scoreView += 300 * dt;
            if (scoreView > score) {
                scoreView = score;
            }
        }
    }

    public Hero getHero() {
        return hero;
    }

    public void addScore(int amount) {
        score += amount;
    }

    public int getScoreView() {
        return scoreView;
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }

    public int getDestroyedAsteroid() {
        return destroyedAsteroid;
    }

    public void incrementDestroyedAsteroid() {
        this.destroyedAsteroid++;
    }

    public int getCoins() {
        return coins;
    }

//    private void checkPressedKeys(float dt){
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//            hero.setAngle(hero.getAngle() + dt * 180);
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//            hero.setAngle(hero.getAngle() - dt * 180);
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//            hero.getVelocity().x += MathUtils.cosDeg(hero.getAngle()) * POWER_SHIP * dt;
//            hero.getVelocity().y += MathUtils.sinDeg(hero.getAngle()) * POWER_SHIP * dt;
//            effectsManager.showEngineEffects(hero, 180);
//
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//            hero.getVelocity().x += -MathUtils.cosDeg(hero.getAngle()) * POWER_SHIP/2 * dt;
//            hero.getVelocity().y += -MathUtils.sinDeg(hero.getAngle()) * POWER_SHIP/2 * dt;
//            effectsManager.showEngineEffects(hero, - 90);
//            effectsManager.showEngineEffects(hero, 90);
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
//            hero.shoot();
//        }
//    }

    public int getRemainsDestroy() {
        return remainsDestroy;
    }

//    public void setRemainsDestroy(int remainsDestroy) {
//        this.remainsDestroy = remainsDestroy;
//    }

    public void reduceNumberAsteroids(){
        this.remainsDestroy--;
    }

    public void increaseDifficulty(){
        this.difficulty++;
        this.remainsDestroy = AsteroidConstants.NEEDED_DESTROY_ASTEROIDS_ON_LEVEL + 10 * difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

//    public void resetRemainsDestroyedAsteroids() {
//        this.remainsDestroy = AsteroidConstants.NEEDED_DESTROY_ASTEROIDS_ON_LEVEL + 10 * difficulty;
//    }

    public boolean reduceCoins(int coins){
        if (this.coins >= coins) {
            this.coins -= coins;
            return true;
        }
        return false;
    }

    public boolean isAlive(){
        return hero.getHp() > 0 ? true : false;
    }

    public void addHp(int hp) {
        hero.addHp(hp);
    }

    public void addBullets(int bullets) {
        hero.addBullets(bullets);
    }

    public int getMagnetismLevel() {
        return magnetismLevel;
    }

    public void increaseMagnetismLevel() {
        magnetismLevel++;
    }
}
