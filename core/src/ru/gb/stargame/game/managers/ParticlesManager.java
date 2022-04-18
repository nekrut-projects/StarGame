package ru.gb.stargame.game.managers;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.gb.stargame.game.entities.*;
import ru.gb.stargame.game.helpers.ObjectPool;
import ru.gb.stargame.screen.utils.Assets;

public class ParticlesManager extends ObjectPool<Particle> {
    public class EffectBuilder {
        public void buildMonsterSplash(float x, float y) {
            for (int i = 0; i < 15; i++) {
                float randomAngle = MathUtils.random(0, 6.28f);
                float randomSpeed = MathUtils.random(0, 50.0f);
                setup(x, y, (float) Math.cos(randomAngle) * randomSpeed, (float) Math.sin(randomAngle) * randomSpeed, 1.2f, 2.0f, 1.8f, 1, 0, 0, 1, 1, 0, 0, 0.2f);
            }
        }
    }

    private EffectBuilder effectBuilder;
//    private InfoMessageManager infoMessageManager;

    public EffectBuilder getEffectBuilder() {
        return effectBuilder;
    }

    public ParticlesManager() {
        this.effectBuilder = new EffectBuilder();
//        this.infoMessageManager = new InfoMessageManager();
    }

    @Override
    protected Particle newObject() {
        return new Particle();
    }

    public void setup(float x, float y, float vx, float vy,
                      float timeMax, float size1, float size2,
                      float r1, float g1, float b1, float a1,
                      float r2, float g2, float b2, float a2) {
        Particle item = getActiveElement();
        item.init(x, y, vx, vy, timeMax, size1, size2, r1, g1, b1, a1, r2, g2, b2, a2);
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

        for (int i = 0; i < 3; i++) {
            setup(bx + MathUtils.random(-4, 4), by + MathUtils.random(-4, 4),
                    hero.getVelocity().x * 0.1f + MathUtils.random(-20, 20),
                    hero.getVelocity().y * 0.1f + MathUtils.random(-20, 20),
                    0.2f,
                    1.2f, 0.2f,
                    1.0f, 0.5f, 0.0f, 1.0f,
                    1.0f, 1.0f, 1.0f, 0.0f);
        }
    }

    public void showBulletEffect(Bullet bullet){
        float bx = bullet.getPosition().x ;
        float by = bullet.getPosition().y ;

        for (int i = 0; i < 2; i++) {
            setup(bx + MathUtils.random(-4, 4), by + MathUtils.random(-4, 4),
                    bullet.getVelocity().x * -0.1f + MathUtils.random(-20, 20),
                    bullet.getVelocity().y * -0.1f + MathUtils.random(-20, 20),
                    0.1f,
                    1.5f, 0.2f,
                    0.0f, 0.5f, 1.0f, 1.0f,
                    0.0f, 0.7f, 1.0f, 0.0f);
        }
    }

    public void takePowerUpsEffect(BonusItem bonus) {
        float r1, g1, b1, a1, r2, g2, b2, a2;
        r1 = g1 = b1 = a1 = r2 = g2 = b2 = a2 = 0;
        switch (bonus.getType()) {
            case MEDICINES:
                    r1 = 0; g1 = 1; b1 = 0; a1 = 1;
                    r2 = 0.2f; g2 = 1f; b2 = 0; a2 = 0.4f;
                break;
            case COINS:
                    r1 = 1; g1 = 1; b1 = 0; a1 = 1;
                    r2 = 1; g2 = 0.7f; b2 = 0; a2 = 0.4f;
                break;
            case BULLETS:
                    r1 = 1; g1 = 0; b1 = 0; a1 = 1;
                    r2 = 1; g2 = 0; b2 = 0.8f; a2 = 0.4f;
                break;
            case WEAPON:
                    r1 = 0.5f; g1 = 1; b1 = 0; a1 = 1;
                    r2 = 0.5f; g2 = 0; b2 = 0.8f; a2 = 0.4f;
                break;
        }

        for (int i = 0; i < 16; i++) {
            float angle = 6.28f / 16.0f * i;
            setup(bonus.getPosition().x, bonus.getPosition().y,
                    (float) Math.cos(angle) * 100.0f, (float) Math.sin(angle) * 100.0f,
                    0.8f, 3.0f, 2.5f,
                    r1, g1, b1, a1, r2, g2, b2, a2);
        }
    }



}
