package ru.gb.stargame.game.managers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import ru.gb.stargame.game.entities.Asteroid;
import ru.gb.stargame.game.helpers.ObjectPool;

import static ru.gb.stargame.game.constants.AsteroidConstants.*;
import static ru.gb.stargame.screen.ScreenManager.SCREEN_HEIGHT;
import static ru.gb.stargame.screen.ScreenManager.SCREEN_WIDTH;

public class AsteroidManager extends ObjectPool<Asteroid> {
    private final TextureRegion texture;


    public AsteroidManager(TextureAtlas.AtlasRegion texture) {
        this.texture = texture;
        generateAsteroid();
        generateAsteroid();
        generateAsteroid();
    }

    @Override
    protected Asteroid newObject() {
        return new Asteroid();
    }

    public void update(float dt){
        for (Asteroid a : activeList){
            a.update(dt);
            checkBorders(a);
        }
        checkPool();
    }

    private void checkBorders(Asteroid asteroid) {
        if (asteroid.getPosition().x > SCREEN_WIDTH + texture.getRegionWidth()/2 + 10){
            asteroid.getPosition().x = -texture.getRegionWidth()/2;
        }
        if (asteroid.getPosition().x < -texture.getRegionWidth()/2 - 10){
            asteroid.getPosition().x = SCREEN_WIDTH + texture.getRegionWidth()/2;
        }
        if (asteroid.getPosition().y > SCREEN_HEIGHT + texture.getRegionHeight()/2 + 10){
            asteroid.getPosition().y = -texture.getRegionHeight()/2;
        }
        if (asteroid.getPosition().y < -texture.getRegionHeight()/2 - 10){
            asteroid.getPosition().y = SCREEN_HEIGHT + texture.getRegionHeight()/2;
        }
    }

    public void generateAsteroid() {
        float scale = MathUtils.random(0.6f, 1.1f);

        getActiveElement().activate(
            MathUtils.random(0, SCREEN_WIDTH),
            MathUtils.random(0, SCREEN_WIDTH),
            MathUtils.random(MIN_SPEED, MAX_SPEED),
            MathUtils.random(MIN_SPEED, MAX_SPEED),
            scale, scale * texture.getRegionWidth()/2 - 10);
    }

    public void generatePartsAsteroid(Asteroid asteroid) {
        float scale = asteroid.getScale() - 0.3f;
        for (int i = 0; i < 3; i++) {
            getActiveElement().activate(asteroid.getPosition().x, asteroid.getPosition().y,
                    MathUtils.random(-150, 150), MathUtils.random(-150, 150),
                    scale, texture.getRegionWidth()/2 * scale - 10);
        }
    }
}
