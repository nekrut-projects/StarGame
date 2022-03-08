package ru.gb.stargame.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import ru.gb.stargame.game.helpers.ObjectPool;

import static ru.gb.stargame.game.constants.AsteroidConstants.*;
import static ru.gb.stargame.screen.ScreenManager.SCREEN_HEIGHT;
import static ru.gb.stargame.screen.ScreenManager.SCREEN_WIDTH;

public class AsteroidManager extends ObjectPool<Asteroid> {
    private final TextureRegion texture;
    private int countRenders = 0;

    public AsteroidManager(TextureAtlas.AtlasRegion texture) {
        this.texture = texture;

        activeList.add(activate(getActiveElement()));
        activeList.add(activate(getActiveElement()));
    }

    @Override
    protected Asteroid newObject() {
        return new Asteroid();
    }

    public void render(SpriteBatch batch) {
        for (Asteroid a : activeList){
            batch.draw(texture, a.getPosition().x - (texture.getRegionWidth() / 2),
                    a.getPosition().y - (texture.getRegionHeight() / 2),(texture.getRegionWidth() / 2),
                    (texture.getRegionHeight() / 2), texture.getRegionWidth(), texture.getRegionHeight(),
                    a.getScale(), a.getScale(), a.getRotationSpeed());
        }
    }

    public void update(float dt){
        countRenders++;
        for (Asteroid a : activeList){
            a.update(dt);
            checkBorders(a);
        }
        checkPool();
    }

    private Asteroid activate(Asteroid asteroid) {
        float scale = 100f / asteroid.getVelocity().y * 0.4f;
        float radius = (texture.getRegionWidth() / 2) * scale;

        int coordX = MathUtils.random(0, SCREEN_WIDTH - texture.getRegionWidth());
        int coordY = -texture.getRegionHeight();

        int velocityX = 0;
        int velocityY = MathUtils.random(MIN_SPEED, MAX_SPEED);

        asteroid.activate(coordX, coordY, velocityX, velocityY, scale, radius);
        if (countRenders % 2 == 0 ){
            asteroid.setAngle(MathUtils.random( ANGLE, -ANGLE));
            countRenders = 0;
        }
        return asteroid;
    }

    public void generateAsteroid() {
        activate(getActiveElement());
    }

    private void checkBorders(Asteroid asteroid) {
        if (asteroid.getPosition().x < -texture.getRegionWidth() - 1 ||
            asteroid.getPosition().x > SCREEN_WIDTH + texture.getRegionWidth() + 1||
            asteroid.getPosition().y < -texture.getRegionHeight() - 1 ||
            asteroid.getPosition().y > SCREEN_HEIGHT + texture.getRegionHeight() + 1) {

            asteroid.deactivate();
            activate(asteroid);
        }
    }
    public TextureRegion getTexture() {
        return texture;
    }
}
