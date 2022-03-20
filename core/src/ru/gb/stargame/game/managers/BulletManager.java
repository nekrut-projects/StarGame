package ru.gb.stargame.game.managers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import ru.gb.stargame.game.entities.Bullet;
import ru.gb.stargame.game.helpers.ObjectPool;

import static ru.gb.stargame.screen.ScreenManager.SCREEN_HEIGHT;
import static ru.gb.stargame.screen.ScreenManager.SCREEN_WIDTH;

public class BulletManager extends ObjectPool<Bullet> {
    private final TextureRegion texture;
    private ParticleManager particleManager;

    public BulletManager(TextureRegion texture, ParticleManager particleManager) {
        this.texture = texture;
        this.particleManager = particleManager;
    }

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

    public void setup(float x, float y, float vx, float vy){
        getActiveElement().activate(x, y, vx, vy);
    }

    public void update(float dt) {
        for (Bullet b : activeList){
            b.update(dt);
            showBulletEffect(b);
            checkBorders(b);
        }
        checkPool();
    }

    private void showBulletEffect(Bullet bullet){
        float bx = bullet.getPosition().x ;
        float by = bullet.getPosition().y ;

        for (int i = 0; i < 2; i++) {
            particleManager.setup(bx + MathUtils.random(-4, 4), by + MathUtils.random(-4, 4),
                    bullet.getVelocity().x * -0.1f + MathUtils.random(-20, 20),
                    bullet.getVelocity().y * -0.1f + MathUtils.random(-20, 20),
                    0.1f,
                    1.5f, 0.2f,
                    0.0f, 0.5f, 1.0f, 1.0f,
                    0.0f, 0.7f, 1.0f, 0.0f);
        }
    }

    private void checkBorders(Bullet b) {
        if (b.getPosition().x < - texture.getRegionWidth() ||
            b.getPosition().x > SCREEN_WIDTH + texture.getRegionWidth() ||
            b.getPosition().y < - texture.getRegionHeight() ||
            b.getPosition().y > SCREEN_HEIGHT + texture.getRegionHeight()) {

            b.deactivate();
        }
    }
}
