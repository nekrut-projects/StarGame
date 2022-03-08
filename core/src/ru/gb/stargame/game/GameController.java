package ru.gb.stargame.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import ru.gb.stargame.game.constants.AsteroidConstants;
import ru.gb.stargame.game.constants.HeroConstants;
import ru.gb.stargame.screen.utils.Assets;

public class GameController {
    private Background background;
    private Hero hero;
    private AsteroidManager asteroidManager;
    private BulletManager bulletManager;
    private final TextureAtlas atlas;
    private Vector2 tempVec;

    public GameController() {
        this.tempVec = new Vector2();
        this.atlas = Assets.getInstance().getAtlas();
        this.background = new Background(this);
        this.bulletManager = new BulletManager(atlas.findRegion("bullet"));
        this.asteroidManager = new AsteroidManager(atlas.findRegion("asteroid"));
        this.hero = new Hero(bulletManager, atlas.findRegion("ship"));
    }
    public void update(float dt){
        background.update(dt);
        bulletManager.update(dt);
        hero.update(dt);
        asteroidManager.update(dt);
        checkCollisions();
    }

    public Background getBackground() {
        return background;
    }

    public Hero getHero() {
        return hero;
    }

    public AsteroidManager getAsteroidManager() {
        return asteroidManager;
    }

    public BulletManager getBulletManager() {
        return bulletManager;
    }

    private void checkCollisions(){
        //столкновение астероидов и героя
        for (int i = 0; i < asteroidManager.getActiveList().size(); i++) {
            Asteroid a = asteroidManager.getActiveList().get(i);
            float dst = a.getPosition().dst(hero.getPosition());
            if (dst < asteroidManager.getTexture().getRegionWidth()/2 * a.getScale()){
                float halfOverLen = (a.getHitArea().radius + hero.getHitArea().radius - dst) / 2.0f;
                tempVec.set(hero.getPosition()).sub(a.getPosition()).nor();
                hero.getPosition().mulAdd(tempVec, halfOverLen);
                a.getPosition().mulAdd(tempVec, -halfOverLen);

                float sumScl = hero.getHitArea().radius * 2 + a.getHitArea().radius;
                hero.getVelocity().mulAdd(tempVec, 200.0f * a.getHitArea().radius / sumScl);
                a.getVelocity().mulAdd(tempVec, -200.0f * hero.getHitArea().radius / sumScl);

                a.takeDamage(HeroConstants.COLLISION_DAMAGE);
                if (hero.takeDamage(HeroConstants.COLLISION_DAMAGE) <= 0){
                    hero.addScore(HeroConstants.POINTS_FOR_DESTROYING_ASTEROID);
                }
            }
        }
        for (int i = 0; i < bulletManager.getActiveList().size(); i++) {
            Bullet b = bulletManager.getActiveList().get(i);
            for (int j = 0; j < asteroidManager.getActiveList().size(); j++) {
                Asteroid a = asteroidManager.getActiveList().get(j);
                if (a.getPosition().dst(b.getPosition()) <
                        asteroidManager.getTexture().getRegionWidth()/2 * a.getScale()){
                    b.deactivate();
                    if (a.takeDamage(b.getDamage()) <= 0){
                        a.deactivate();
                        asteroidManager.generateAsteroid();
                        hero.addScore(HeroConstants.POINTS_FOR_DESTROYING_ASTEROID);
                    }
                }
            }
        }
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }
}
