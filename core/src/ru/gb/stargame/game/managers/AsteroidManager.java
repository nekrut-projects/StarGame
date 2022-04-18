package ru.gb.stargame.game.managers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import ru.gb.stargame.game.constants.AsteroidConstants;
import ru.gb.stargame.game.entities.Asteroid;
import ru.gb.stargame.game.helpers.ObjectPool;

import static ru.gb.stargame.game.constants.AsteroidConstants.*;
import static ru.gb.stargame.game.constants.ScreenConstants.HEIGHT;
import static ru.gb.stargame.game.constants.ScreenConstants.WIDTH;

public class AsteroidManager extends ObjectPool<Asteroid> {
    private final TextureRegion texture;


    public AsteroidManager(TextureAtlas.AtlasRegion texture) {
        this.texture = texture;
        for (int i = 0; i < 3; i++) {
            generateAsteroid(HP_MAX);
        }
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
        if (asteroid.getPosition().x > WIDTH + texture.getRegionWidth()/2 + 10){
            asteroid.getPosition().x = -texture.getRegionWidth()/2;
        }
        if (asteroid.getPosition().x < -texture.getRegionWidth()/2 - 10){
            asteroid.getPosition().x = WIDTH + texture.getRegionWidth()/2;
        }
        if (asteroid.getPosition().y > HEIGHT + texture.getRegionHeight()/2 + 10){
            asteroid.getPosition().y = -texture.getRegionHeight()/2;
        }
        if (asteroid.getPosition().y < -texture.getRegionHeight()/2 - 10){
            asteroid.getPosition().y = HEIGHT + texture.getRegionHeight()/2;
        }
    }

    public Asteroid generateAsteroid(int hp) {
        float scale = MathUtils.random(0.6f, 1.1f);

        Asteroid a = getActiveElement();
        a.activate(
            MathUtils.random(0, WIDTH),
            MathUtils.random(0, WIDTH),
            MathUtils.random(MIN_SPEED, MAX_SPEED),
            MathUtils.random(MIN_SPEED, MAX_SPEED),
            scale, scale * texture.getRegionWidth()/2 - 10, hp);
        return a;
    }

    public void generatePartsAsteroid(Asteroid asteroid, int hp) {
        float scale = asteroid.getScale() - 0.3f;
        for (int i = 0; i < 3; i++) {
            getActiveElement().activate(asteroid.getPosition().x, asteroid.getPosition().y,
                    MathUtils.random(-150, 150), MathUtils.random(-150, 150),
                    scale, texture.getRegionWidth()/2 * scale - 10, hp);
        }
    }
}
