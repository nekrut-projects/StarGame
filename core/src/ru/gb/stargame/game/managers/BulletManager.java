package ru.gb.stargame.game.managers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.gb.stargame.game.entities.Bullet;
import ru.gb.stargame.game.helpers.ObjectPool;

import static ru.gb.stargame.game.constants.ScreenConstants.HEIGHT;
import static ru.gb.stargame.game.constants.ScreenConstants.WIDTH;

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
            particleManager.showBulletEffect(b);
            checkBorders(b);
        }
        checkPool();
    }

    private void checkBorders(Bullet b) {
        if (b.getPosition().x < - texture.getRegionWidth() ||
            b.getPosition().x > WIDTH + texture.getRegionWidth() ||
            b.getPosition().y < - texture.getRegionHeight() ||
            b.getPosition().y > HEIGHT + texture.getRegionHeight()) {

            b.deactivate();
        }
    }
}
