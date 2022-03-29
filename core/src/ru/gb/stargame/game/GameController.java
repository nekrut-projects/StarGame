package ru.gb.stargame.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.gb.stargame.game.constants.BonusConstants;
import ru.gb.stargame.game.constants.HeroConstants;
import ru.gb.stargame.game.entities.*;
import ru.gb.stargame.game.managers.AsteroidManager;
import ru.gb.stargame.game.managers.BonusItemManager;
import ru.gb.stargame.game.managers.BulletManager;
import ru.gb.stargame.game.managers.ParticleManager;
import ru.gb.stargame.screen.utils.Assets;

public class GameController {
    private Background background;
    private Player player;
    private BonusItemManager bonusItemManager;
    private AsteroidManager asteroidManager;
    private BulletManager bulletManager;
    private ParticleManager particleManager;
    private final TextureAtlas atlas;
    private Vector2 tempVec;
    private int countRenders = 0;
    private int difficultyLevel;

    public GameController() {
        this.atlas = Assets.getInstance().getAtlas();
        this.tempVec = new Vector2();
        this.background = new Background(this);
        this.particleManager = new ParticleManager();
        this.bulletManager = new BulletManager(atlas.findRegion("bullet"), particleManager);
        this.asteroidManager = new AsteroidManager(atlas.findRegion("asteroid"));
        this.player = new Player(atlas.findRegion("ship"), bulletManager, particleManager);
        this.bonusItemManager = new BonusItemManager();
        this.difficultyLevel = 1;
    }

    public void update(float dt){
        countRenders++;
        background.update(dt);
        bulletManager.update(dt);
        asteroidManager.update(dt);
        particleManager.update(dt);
        bonusItemManager.update(dt);
        player.update(dt);
        checkCollisionWithAsteroids();
        checkHitting();
        checkCollisionWithBonus();
    }

    public ParticleManager getParticleManager() {
        return particleManager;
    }

    public Background getBackground() {
        return background;
    }

    public AsteroidManager getAsteroidManager() {
        return asteroidManager;
    }

    public BulletManager getBulletManager() {
        return bulletManager;
    }

    private void checkCollisionWithAsteroids(){
        //столкновение астероидов и героя
        for (int i = 0; i < asteroidManager.getActiveList().size(); i++) {
            Asteroid a = asteroidManager.getActiveList().get(i);
            float dst = a.getPosition().dst(player.getHero().getPosition());
            if (a.getHitArea().overlaps(player.getHero().getHitArea())){
                float halfOverLen = (a.getHitArea().radius + player.getHero().getHitArea().radius - dst) / 2.0f;
                tempVec.set(player.getHero().getPosition()).sub(a.getPosition()).nor();
                player.getHero().getPosition().mulAdd(tempVec, halfOverLen);
                a.getPosition().mulAdd(tempVec, -halfOverLen);

                float sumScl = player.getHero().getHitArea().radius * 2 + a.getHitArea().radius;
                player.getHero().getVelocity().mulAdd(tempVec, 200.0f * a.getHitArea().radius / sumScl);
                a.getVelocity().mulAdd(tempVec, -200.0f * player.getHero().getHitArea().radius / sumScl);

                a.takeDamage(HeroConstants.COLLISION_DAMAGE);
                if (player.getHero().takeDamage(HeroConstants.COLLISION_DAMAGE) <= 0){
                    player.addScore(HeroConstants.POINTS_FOR_DESTROYING_ASTEROID);
                }
            }
        }

    }

    public void checkHitting(){
        for (int i = 0; i < bulletManager.getActiveList().size(); i++) {
            Bullet b = bulletManager.getActiveList().get(i);
            for (int j = 0; j < asteroidManager.getActiveList().size(); j++) {
                Asteroid a = asteroidManager.getActiveList().get(j);
                if (a.getHitArea().contains(b.getPosition())){
                    b.deactivate();
                    if (a.takeDamage(b.getDamage()) <= 0){
                        a.deactivate();
                        player.incrementDestroyedAsteroid();
                        if (a.getScale() > 0.5f) {
                            asteroidManager.generatePartsAsteroid(a);
                        } else if (countRenders % 5 == 0) {
                            countRenders = 0;
                            asteroidManager.generateAsteroid();
                        }
                        if (countRenders % 7 == 0){
                            bonusItemManager.generateBonus(a.getPosition());
                        }
                        player.addScore(HeroConstants.POINTS_FOR_DESTROYING_ASTEROID);
                    }
                    break;
                }
            }
        }
    }

    public void checkCollisionWithBonus(){
        for (int i = 0; i < bonusItemManager.getActiveList().size(); i++) {
            BonusItem bonus = bonusItemManager.getActiveList().get(i);
            if (bonus.getHitArea().overlaps(player.getHero().getMagneticArea())){
                tempVec.set(player.getHero().getPosition()).sub(bonus.getPosition()).nor();
                bonus.getVelocity().mulAdd(tempVec, 100);
            }
            if (bonus.getHitArea().overlaps(player.getHero().getHitArea())){
                switch (bonus.getType()){
                    case COINS:
                        player.addCoins(BonusConstants.SOME_COINS);
                        break;
                    case WEAPON:
                        player.getHero().addWeapon();
                        break;
                    case MEDICINES:
                        player.getHero().addHp(BonusConstants.SMALL_MEDICINE);
                        break;
                    case BULLETS:
                        player.getHero().addBullets(BonusConstants.MEDIUM_BULLETS);
                        break;
                }
                particleManager.takePowerUpsEffect(bonus);
                bonus.deactivate();
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public BonusItemManager getBonusItemManager() {
        return bonusItemManager;
    }

}
