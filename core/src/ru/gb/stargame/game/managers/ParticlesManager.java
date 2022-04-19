package ru.gb.stargame.game.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import ru.gb.stargame.game.entities.*;
import ru.gb.stargame.game.helpers.ObjectPool;


public class ParticlesManager extends ObjectPool<Particle> {
    private Color beginColor;
    private Color endColor;

    public class EffectBuilder {
        public void buildMonsterSplash(float x, float y) {
            beginColor.set(1, 0, 0, 1);
            endColor.set(1, 0, 0, 0.2f);
            for (int i = 0; i < 15; i++) {
                float randomAngle = MathUtils.random(0, 6.28f);
                float randomSpeed = MathUtils.random(0, 50.0f);
                setup(x, y, (float) Math.cos(randomAngle) * randomSpeed, (float) Math.sin(randomAngle) * randomSpeed,
                        1.2f, 2.0f, 1.8f,
                        beginColor, endColor);
            }
        }
    }

    private EffectBuilder effectBuilder;

    public EffectBuilder getEffectBuilder() {
        return effectBuilder;
    }

    public ParticlesManager() {
        this.beginColor = new Color();
        this.endColor = new Color();
        this.effectBuilder = new EffectBuilder();
    }

    @Override
    protected Particle newObject() {
        return new Particle();
    }

    public void setup(float x, float y, float vx, float vy,
                      float timeMax, float size1, float size2,
                      Color beginColor, Color endColor) {
        Particle item = getActiveElement();
        item.init(x, y, vx, vy, timeMax, size1, size2, beginColor, endColor);
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        checkPool();
    }

    public void showEngineEffects(Hero hero, int angleEngine) {
        float bx = hero.getPosition().x + MathUtils.cosDeg(hero.getAngle() + angleEngine) * 25;
        float by = hero.getPosition().y + MathUtils.sinDeg(hero.getAngle() + angleEngine) * 25;

        beginColor.set(1.0f, 0.5f, 0.0f, 1.0f);
        endColor.set(1.0f, 1.0f, 1.0f, 0.0f);

        for (int i = 0; i < 3; i++) {
            setup(bx + MathUtils.random(-4, 4), by + MathUtils.random(-4, 4),
                    hero.getVelocity().x * 0.1f + MathUtils.random(-20, 20),
                    hero.getVelocity().y * 0.1f + MathUtils.random(-20, 20),
                    0.2f,
                    1.2f, 0.2f,
                    beginColor, endColor);
        }
    }

    public void showBulletEffect(Bullet bullet){
        float bx = bullet.getPosition().x ;
        float by = bullet.getPosition().y ;
        switch (bullet.getOwner()){
            case PLAYER:
                beginColor.set(0.0f, 0.5f, 1.0f, 1.0f);
                endColor.set(0.0f, 0.7f, 1.0f, 0.0f);
                break;
            case BOT:
                beginColor.set(3.0f, 0.2f, 0.0f, 1.0f);
                endColor.set(1.0f, 0.8f, 0.0f, 0.0f);
                break;
        }

        for (int i = 0; i < 2; i++) {
            setup(bx + MathUtils.random(-4, 4), by + MathUtils.random(-4, 4),
                    bullet.getVelocity().x * -0.1f + MathUtils.random(-20, 20),
                    bullet.getVelocity().y * -0.1f + MathUtils.random(-20, 20),
                    0.1f,
                    1.5f, 0.2f,
                    beginColor, endColor);
        }
    }

    public void takePowerUpsEffect(BonusItem bonus) {
        switch (bonus.getType()) {
            case MEDICINES:
                beginColor.set(0,1, 0,1);
                endColor.set(0.2f,1f, 0, 0.4f);
                break;
            case COINS:
                beginColor.set(1,1, 0,1);
                endColor.set(1,0.7f, 0, 0.4f);
                break;
            case BULLETS:
                beginColor.set(1,0,0,1);
                endColor.set(1, 0,0.8f,0.4f);
                break;
            case WEAPON:
                beginColor.set(0.5f,1,0, 1);
                endColor.set(0.5f,0,0.8f,0.4f);
                break;
        }

        for (int i = 0; i < 16; i++) {
            float angle = 6.28f / 16.0f * i;
            setup(bonus.getPosition().x, bonus.getPosition().y,
                    (float) Math.cos(angle) * 100.0f, (float) Math.sin(angle) * 100.0f,
                    0.8f, 3.0f, 2.5f,
                    beginColor, endColor);
        }
    }



}
