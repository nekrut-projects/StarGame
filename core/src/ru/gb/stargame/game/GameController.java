package ru.gb.stargame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.gb.stargame.game.constants.AsteroidConstants;
import ru.gb.stargame.game.constants.InfoMessagesConstants;
import ru.gb.stargame.game.entities.*;
import ru.gb.stargame.game.managers.*;
import ru.gb.stargame.screen.utils.Assets;

import static ru.gb.stargame.game.constants.BonusConstants.*;
import static ru.gb.stargame.game.constants.HeroConstants.*;
import static ru.gb.stargame.game.constants.InfoMessagesConstants.*;

public class GameController {
    private Background background;
    private Player player;
    private BonusItemManager bonusItemManager;
    private AsteroidManager asteroidManager;
    private BulletManager bulletManager;
    private EffectsController effectsController;
    private final TextureAtlas atlas;
    private Vector2 tempVec;
    private int countRenders = 0;
    private StringBuilder tempSB;


    public GameController() {
        this.tempSB = new StringBuilder();
        this.atlas = Assets.getInstance().getAtlas();
        this.tempVec = new Vector2();
        this.background = new Background(this);
        this.effectsController = new EffectsController();
        this.bulletManager = new BulletManager(atlas.findRegion("bullet"));
        this.asteroidManager = new AsteroidManager(atlas.findRegion("asteroid"));
        this.player = new Player(atlas.findRegion("ship"), bulletManager);
        this.bonusItemManager = new BonusItemManager();
    }

    public void update(float dt){
        countRenders++;
        background.update(dt);
        bulletManager.update(dt);
        asteroidManager.update(dt);
        effectsController.update(dt);
        bonusItemManager.update(dt);
        player.update(dt);
        checkPressedKeys(dt);
        checkCollisionWithAsteroids();
        checkHitting();
        checkCollisionWithBonus();
    }

    public EffectsController getEffectsController() {
        return effectsController;
    }
//    public ParticlesManager getParticleManager() {
//        return particlesManager;
//    }

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
                Hero hero = player.getHero();
                tempSB.setLength(0);
                tempSB.append("-").append(COLLISION_DAMAGE).append(" damage");
                effectsController.getInfoMessageManager()
                        .showInfoMessage(hero.getPosition(), tempSB,
                                COLOR_DAMAGE_BY_ASTEROID);
                float halfOverLen = (a.getHitArea().radius + hero.getHitArea().radius - dst) / 2.0f;
                tempVec.set(hero.getPosition()).sub(a.getPosition()).nor();
                hero.getPosition().mulAdd(tempVec, halfOverLen);
                a.getPosition().mulAdd(tempVec, -halfOverLen);

                float sumScl = hero.getHitArea().radius * 2 + a.getHitArea().radius;
                hero.getVelocity().mulAdd(tempVec, 200.0f * a.getHitArea().radius / sumScl);
                a.getVelocity().mulAdd(tempVec, -200.0f * hero.getHitArea().radius / sumScl);

                a.takeDamage(COLLISION_DAMAGE);
                if (player.getHero().takeDamage(COLLISION_DAMAGE) <= 0){
                    player.addScore(POINTS_FOR_DESTROYING_ASTEROID);
                }
            }
        }

    }

    public void checkHitting(){
        for (int i = 0; i < bulletManager.getActiveList().size(); i++) {
            Bullet b = bulletManager.getActiveList().get(i);
            effectsController.getParticlesManager().showBulletEffect(b);
            for (int j = 0; j < asteroidManager.getActiveList().size(); j++) {
                Asteroid a = asteroidManager.getActiveList().get(j);
                if (a.getHitArea().contains(b.getPosition())){
                    b.deactivate();
                    if (a.takeDamage(b.getDamage()) <= 0){
                        a.deactivate();
                        player.incrementDestroyedAsteroid();
                        player.reduceNumberAsteroids();
                        int hp = AsteroidConstants.HP_MAX + player.getDifficulty() * 15;
                        if (a.getScale() > 0.5f) {
                            asteroidManager.generatePartsAsteroid(a, hp);
                        } else if (countRenders % 5 == 0) {
                            countRenders = 0;
                            asteroidManager.generateAsteroid(hp);
                        }
                        if (countRenders % 7 == 0){
                            bonusItemManager.generateBonus(a.getPosition());
                        }
                        player.addScore(POINTS_FOR_DESTROYING_ASTEROID);
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
                int color = 0;
                tempSB.setLength(0);
                tempSB.append("+");
                switch (bonus.getType()){
                    case COINS:
                        player.addCoins(SOME_COINS);
                        tempSB.append(SOME_COINS).append(" coins");
                        color = COLOR_COINS;
                        break;
                    case WEAPON:
                        player.getHero().upgradeWeapon();
                        tempSB.append(" Level up");
                        color = COLOR_LEVEL_UP;
                        break;
                    case MEDICINES:
                        player.getHero().addHp(SMALL_MEDICINE);
                        tempSB.append(SMALL_MEDICINE).append(" hp");
                        color = COLOR_MEDICINE;
                        break;
                    case BULLETS:
                        player.getHero().addBullets(MEDIUM_BULLETS);
                        tempSB.append(MEDIUM_BULLETS).append(" bullets");
                        color = COLOR_BULLETS;
                        break;
                }
                effectsController.getInfoMessageManager()
                        .showInfoMessage(bonus.getPosition(), tempSB, color);

                effectsController.getParticlesManager().takePowerUpsEffect(bonus);
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

    private void checkPressedKeys(float dt){
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.getHero().setAngle(player.getHero().getAngle() + dt * 180);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.getHero().setAngle(player.getHero().getAngle() - dt * 180);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.getHero().getVelocity().x += MathUtils.cosDeg(player.getHero().getAngle()) * POWER_SHIP * dt;
            player.getHero().getVelocity().y += MathUtils.sinDeg(player.getHero().getAngle()) * POWER_SHIP * dt;
            effectsController.getParticlesManager().showEngineEffects(player.getHero(), 180);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.getHero().getVelocity().x += -MathUtils.cosDeg(player.getHero().getAngle()) * POWER_SHIP/2 * dt;
            player.getHero().getVelocity().y += -MathUtils.sinDeg(player.getHero().getAngle()) * POWER_SHIP/2 * dt;
            effectsController.getParticlesManager().showEngineEffects(player.getHero(), - 90);
            effectsController.getParticlesManager().showEngineEffects(player.getHero(), 90);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player.getHero().shoot();
            if (player.getHero().getAmountBullets() > 0) {
                effectsController.playSoundShoot();
            }
        }
    }

}
