package ru.gb.stargame.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.gb.stargame.game.constants.HeroConstants;
import ru.gb.stargame.screen.utils.Assets;

public class GameRenderer {
    private GameController gc;
    private SpriteBatch batch;
    private BitmapFont font32;
    private StringBuilder sb;

    public GameRenderer(GameController gc, SpriteBatch batch) {
        this.gc = gc;
        this.batch = batch;
        this.font32 = Assets.getInstance().getAssetManager().get("fonts/font32.ttf", BitmapFont.class);
        this.sb = new StringBuilder();
    }

    public void render () {
        ScreenUtils.clear(0, 0, 0.5f, 1f);
        batch.begin();
            gc.getBackground().render(batch);
            gc.getBulletManager().render(batch);
            gc.getHero().render(batch);
            gc.getAsteroidManager().render(batch);
            renderGUI();
        batch.end();
    }

    public void renderGUI() {
        sb.setLength(0);
        sb.append("SCORE: ").append(gc.getHero().getScoreView()).append("\n");
        sb.append("HP: ").append(gc.getHero().getHp()).append("/").append(HeroConstants.MAX_HP).append("\n");
        font32.draw(batch, sb, 20, 700);
    }
}
