package ru.gb.stargame.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.gb.stargame.game.constants.ScreenConstants;

public abstract class AbstractScreen implements Screen {
    private SpriteBatch batch;
    private Viewport viewport;

    public AbstractScreen(SpriteBatch batch) {
        this.batch = batch;
        this.viewport = new FitViewport(ScreenConstants.WIDTH, ScreenConstants.HEIGHT);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.apply();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public Viewport getViewport() {
        return viewport;
    }
}
