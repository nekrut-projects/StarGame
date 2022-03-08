package ru.gb.stargame.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static ru.gb.stargame.screen.ScreenManager.*;

public class Background {
    private final GameController gc;
    public static final int STARS_COUNT = 500;

    private class Star{
        private Vector2 position;
        private Vector2 velocity;
        private float scale;

        public Star() {
            position = new Vector2(MathUtils.random(-200, SCREEN_WIDTH + 200),
                    MathUtils.random(-200, SCREEN_HEIGHT + 200));
            velocity = new Vector2(MathUtils.random(-40, -5), 0);
            scale = Math.abs(velocity.x / 40f) * 0.8f;
        }
        public void update(float dt){
            position.x += (velocity.x - gc.getHero().getVelocity().x * 0.1f) * dt;
            position.y += (velocity.y - gc.getHero().getVelocity().y * 0.1f) * dt;

            if (position.x < -20){
                position.x = SCREEN_WIDTH + 20;
                position.y = MathUtils.random(0, SCREEN_HEIGHT);
                scale = Math.abs(velocity.x / 40f) * 0.8f;
            }
        }
    }
    private Texture textureCosmos;
    private TextureRegion textureStar;

    private Star[] stars;


    public Background(GameController gc) {
        this.gc = gc;
        this.textureCosmos = new Texture("images/bg.png");
        this.textureStar = gc.getAtlas().findRegion("star16");
        this.stars = new Star[STARS_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star();
        }
    }

    public void update(float dt){
        for (Star s : stars) {
            s.update(dt);
        }
    }

    public void render (SpriteBatch batch) {
        batch.draw(textureCosmos, 0, 0);
        for (Star s : stars){
            batch.draw(textureStar, s.position.x - 8, s.position.y - 8, 8, 8, 16, 16,
                    s.scale, s.scale, 0);

            if (MathUtils.random(500) < 1) {
                batch.draw(textureStar, s.position.x - 8, s.position.y - 8, 8, 8,
                        16, 16, s.scale * 2, s.scale * 2,0);
            }
        }
    }
}
