package ru.gb.stargame.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.gb.stargame.StarGame;
import ru.gb.stargame.screen.utils.Assets;

import java.util.HashMap;
import java.util.Map;

import static ru.gb.stargame.screen.ScreenManager.ScreenType.*;

public class ScreenManager {
    public enum ScreenType{
        GAME, MAIN_MENU, GAME_OVER;
    }
    private Map<ScreenType, AbstractScreen> screens;
    private LoadingScreen loading;
    private StarGame game;
    private AbstractScreen targetScreen;

    public ScreenManager(SpriteBatch batch, StarGame starGame) {
        this.screens = new HashMap<>();
        initScreens(batch);
        this.loading = new LoadingScreen(batch);
        this.game = starGame;
    }

    private void initScreens(SpriteBatch batch){
        screens.put(GAME, new GameScreen(batch, this));
        screens.put(MAIN_MENU, new MenuScreen(batch, this));
        screens.put(GAME_OVER, new GameOverScreen(batch, this));
    }

    public void changeScreen(ScreenType type){
        Assets.getInstance().clear();
        if (game.getScreen() != null){
            game.getScreen().dispose();
        }
        game.setScreen(loading);
        targetScreen = screens.get(type);
        Assets.getInstance().loadAssets(type);
    }

    public void checkLoading() {
        if (!loading.isActive()){
			Assets.getInstance().setAtlas();
			game.setScreen(targetScreen);
			loading.setActive(true);
		}
    }

    public AbstractScreen getScreen(ScreenType type){
        return screens.get(type);
    }
}
