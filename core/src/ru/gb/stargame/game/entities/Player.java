package ru.gb.stargame.game.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.gb.stargame.game.managers.BulletManager;
import ru.gb.stargame.game.managers.ParticleManager;

public class Player {
    private Hero hero;
    private int score;
    private int scoreView;
    private int coins;

    public Player(TextureRegion texture, BulletManager bulletManager, ParticleManager particleManager) {
        this.hero = new Hero(texture, bulletManager,particleManager);
        this.score = 0;
        this.scoreView = 0;
        this.coins = 0;
    }

    public void update(float dt) {

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

    public int getCoins() {
        return coins;
    }
}
