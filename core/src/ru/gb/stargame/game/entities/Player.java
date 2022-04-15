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

    public Player(TextureRegion texture, BulletManager bulletManager) {
        this.hero = new Hero(texture, bulletManager);
        this.score = 0;
        this.scoreView = 0;
        this.coins = 0;
        this.destroyedAsteroid = 0;
        this.difficulty = 1;
        this.remainsDestroy = AsteroidConstants.NEEDED_DESTROY_ASTEROIDS_ON_LEVEL;
        this.magnetismLevel = 2;
    }

    public void update(float dt) {
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

    public int getRemainsDestroy() {
        return remainsDestroy;
    }

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
