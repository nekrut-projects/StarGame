package ru.gb.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.gb.stargame.game.GameController;
import ru.gb.stargame.game.GameRenderer;
import ru.gb.stargame.game.entities.Asteroid;
import ru.gb.stargame.screen.utils.Assets;

public class GameScreen extends AbstractScreen {
    private GameController gc;
    private GameRenderer gr;
    private ScreenManager screenManager;
    private boolean active;
    private Shop shop;
    private float timer;

    public GameScreen(SpriteBatch batch, ScreenManager screenManager) {
        super(batch);
        this.screenManager = screenManager;
        this.active = true;
        this.timer = 0;
    }

    @Override
    public void show() {
        Assets.getInstance().loadAssets(ScreenManager.ScreenType.GAME);
        gc = new GameController();
        gr = new GameRenderer(gc, getBatch());
        this.shop = new Shop(getViewport(), getBatch(), this);
        super.playMusic((Music) Assets.getInstance().getAssetManager().get("audio/mortal.mp3"));
    }

    public void update(float delta){
        if (gc.getAsteroidManager().getActiveList().size() == 0 ||
            gc.getPlayer().getRemainsDestroy() <= 0){
            gc.increaseDifficulty();
            gc.getAsteroidManager().getActiveList().clear();
            for (int i = 0; i < 3; i++) {
                Asteroid asteroid = gc.getAsteroidManager().generateAsteroid(gc.getDifficulty());
                asteroid.getVelocity().x += gc.getDifficulty();
                asteroid.getVelocity().y += gc.getDifficulty();
            }
            timer = 0;
            gc.getBotShipManager().getActiveElement().activate(gc.getPlayer().getHero().getPosition());
        }
        if (!gc.getPlayer().isAlive()){
            screenManager.changeScreen(ScreenManager.ScreenType.GAME_OVER);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            active = active ? false : true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)){
            if (shop.isActive()) {
                shop.deactivate();
                active = true;
            } else {
                shop.activate();
                active = false;
            }
        }
        if (active){
            gc.update(delta);
        }
        shop.act(delta);
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
            gr.showLevelNumber(gc.getDifficulty());
        }
        getBatch().end();
        shop.draw();
    }

    @Override
    public void dispose() {
        gr.dispose();
        shop.dispose();
    }

    public void activate() {
        active = true;
    }
}
