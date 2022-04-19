package ru.gb.stargame.game.managers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.gb.stargame.game.constants.BulletConstants;
import ru.gb.stargame.game.entities.Bullet;
import ru.gb.stargame.game.helpers.ObjectPool;

import static ru.gb.stargame.game.constants.BulletConstants.*;
import static ru.gb.stargame.game.constants.ScreenConstants.HEIGHT;
import static ru.gb.stargame.game.constants.ScreenConstants.WIDTH;

public class BulletManager extends ObjectPool<Bullet> {
    private final TextureRegion texture;

    public BulletManager(TextureRegion texture) {
        this.texture = texture;
    }

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

    public void setup(float x, float y, float vx, float vy, OwnerBullet owner, int damage){
        getActiveElement().activate(x, y, vx, vy, owner, damage);
    }

    public void update(float dt) {
        Bullet b;
        for (int i = 0; i < activeList.size(); i++) {
            b = activeList.get(i);
            b.update(dt);
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
