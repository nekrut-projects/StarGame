package ru.gb.stargame.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.gb.stargame.game.GameController;
import ru.gb.stargame.game.GameRenderer;
import ru.gb.stargame.screen.utils.Assets;

public class GameScreen extends AbstractScreen {
    private SpriteBatch batch;
    private GameController gc;
    private GameRenderer gr;

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        Assets.getInstance().loadAssets(ScreenManager.ScreenType.GAME);
        gc = new GameController();
        gr = new GameRenderer(gc, batch);
    }

    @Override
    public void render(float delta) {
        gc.update(delta);
        gr.render();
    }
}
