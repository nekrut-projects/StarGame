package ru.gb.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.gb.stargame.game.GameController;
import ru.gb.stargame.game.GameRenderer;
import ru.gb.stargame.screen.utils.Assets;

public class GameScreen extends AbstractScreen {
    private GameController gc;
    private GameRenderer gr;
    private ScreenManager screenManager;
    private boolean active;
    private int difficultyLevel;
    private float timer;

    public GameScreen(SpriteBatch batch, ScreenManager screenManager) {
        super(batch);
        this.screenManager = screenManager;
        this.active = true;
        this.difficultyLevel = 1;
        this.timer = 0;
    }

    @Override
    public void show() {
        Assets.getInstance().loadAssets(ScreenManager.ScreenType.GAME);
        gc = new GameController();
        gr = new GameRenderer(gc, getBatch());
    }

    public void update(float delta){
        if (gc.getAsteroidManager().getActiveList().size() == 0 ||
                gc.getPlayer().getDestroyedAsteroid() >= 100){

        }
        if (gc.getPlayer().getHero().getHp() <= 0){
            screenManager.changeScreen(ScreenManager.ScreenType.GAME_OVER);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            active = active ? false : true;
        }
        if (active){
            gc.update(delta);
        }
    }

    public GameController getGameController() {
        return gc;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.5f, 1f);
        update(delta);

        getBatch().begin();
        gr.render();

        if (timer <= 3){
            timer += delta;
            gr.showLevelNumber(difficultyLevel);
        }
        getBatch().end();
    }
}
