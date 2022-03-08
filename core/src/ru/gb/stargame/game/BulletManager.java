package ru.gb.stargame.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.gb.stargame.game.helpers.ObjectPool;

import static ru.gb.stargame.screen.ScreenManager.SCREEN_HEIGHT;
import static ru.gb.stargame.screen.ScreenManager.SCREEN_WIDTH;

public class BulletManager extends ObjectPool<Bullet> {
    private final TextureRegion texture;

    public BulletManager(TextureRegion texture) {
        this.texture = texture;
    }

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

    public void render(SpriteBatch batch) {
        for (Bullet b : activeList){
            batch.draw(texture, b.getPosition().x - texture.getRegionWidth()/2,
                    b.getPosition().y - texture.getRegionHeight()/2);
        }
    }

    public void setup(float x, float y, float vx, float vy){
        getActiveElement().activate(x, y, vx, vy);
    }

    public void update(float dt) {
        for (Bullet b : activeList){
            b.update(dt);
            checkBorders(b);
        }
        checkPool();
    }

    private void checkBorders(Bullet b) {
        if (b.getPosition().x < - texture.getRegionWidth() ||
            b.getPosition().x > SCREEN_WIDTH + texture.getRegionWidth() ||
            b.getPosition().y < - texture.getRegionHeight() ||
            b.getPosition().y > SCREEN_HEIGHT + texture.getRegionHeight()) {

            b.deactivate();
        }
    }

    public void deactivate(Bullet bullet) {
        bullet.deactivate();
        free(bullet);
    }
}
