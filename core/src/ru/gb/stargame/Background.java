package ru.gb.stargame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static ru.gb.stargame.ScreenManager.*;

public class Background {
    private final StarGame starGame;

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
            position.x += (velocity.x - starGame.getHero().getLastMove().x * 15) * dt;
            position.y += (velocity.y - starGame.getHero().getLastMove().y * 15) * dt;

            if (position.x < -20){
                position.x = SCREEN_WIDTH + 20;
                position.y = MathUtils.random(0, SCREEN_HEIGHT);
                scale = Math.abs(velocity.x / 40f) * 0.8f;
            }
        }
    }
    private Texture textureCosmos;
    private Texture textureStar;

    private Star[] stars;


    public Background(StarGame starGame) {
        this.starGame = starGame;
        this.textureCosmos = new Texture("bg.png");
        this.textureStar = new Texture("star16.png");
        stars = new Star[STARS_COUNT];
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
                    s.scale, s.scale, 0, 0, 0, 16, 16, false, false);

            if (MathUtils.random(300) < 1) {
                batch.draw(textureStar, s.position.x - 8, s.position.y - 8, 8, 8,
                        16, 16, s.scale * 2, s.scale * 2,
                        0, 0, 0, 16, 16, false, false);
            }
        }
    }
}
