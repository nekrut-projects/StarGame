package ru.gb.stargame.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import ru.gb.stargame.game.managers.BulletManager;
import ru.gb.stargame.game.managers.ParticleManager;

import static ru.gb.stargame.game.constants.HeroConstants.POWER_SHIP;

public class Player {
    private Hero hero;
    private int score;
    private int scoreView;
    private int coins;
    private int destroyedAsteroid;
    private ParticleManager particleManager;

    public Player(TextureRegion texture, BulletManager bulletManager, ParticleManager particleManager) {
        this.hero = new Hero(texture, bulletManager,particleManager);
        this.score = 0;
        this.scoreView = 0;
        this.coins = 0;
        this.destroyedAsteroid = 0;
        this.particleManager = particleManager;
    }

    public void update(float dt) {
        checkPressedKeys(dt);
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

    private void checkPressedKeys(float dt){
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            hero.setAngle(hero.getAngle() + dt * 180);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            hero.setAngle(hero.getAngle() - dt * 180);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            hero.getVelocity().x += MathUtils.cosDeg(hero.getAngle()) * POWER_SHIP * dt;
            hero.getVelocity().y += MathUtils.sinDeg(hero.getAngle()) * POWER_SHIP * dt;
            particleManager.showEngineEffects(hero, 180);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            hero.getVelocity().x += -MathUtils.cosDeg(hero.getAngle()) * POWER_SHIP/2 * dt;
            hero.getVelocity().y += -MathUtils.sinDeg(hero.getAngle()) * POWER_SHIP/2 * dt;
            particleManager.showEngineEffects(hero, - 90);
            particleManager.showEngineEffects(hero, 90);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            hero.shoot();
        }
    }

}
