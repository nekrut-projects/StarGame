package ru.gb.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.gb.stargame.game.entities.Player;
import ru.gb.stargame.screen.utils.Assets;

import static ru.gb.stargame.game.constants.ScreenConstants.HEIGHT;
import static ru.gb.stargame.game.constants.ScreenConstants.WIDTH;

public class GameOverScreen extends AbstractScreen{
    public GameOverScreen(SpriteBatch batch, ScreenManager screenManager) {
        super(batch);
        this.screenManager = screenManager;
    }
    private ScreenManager screenManager;
    private BitmapFont font72;
    private BitmapFont font24;
    private BitmapFont font32;
    private Stage stage;

    @Override
    public void show() {
        this.stage = new Stage(getViewport(), getBatch());
        this.font72 = Assets.getInstance().getAssetManager().get("fonts/font72.ttf");
        this.font24 = Assets.getInstance().getAssetManager().get("fonts/font24.ttf");
        this.font32 = Assets.getInstance().getAssetManager().get("fonts/font32.ttf");

        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up= skin.getDrawable("simpleButton");
        textButtonStyle.font = font24;
        skin.add("simpleSkin", textButtonStyle);

        Button btnMainMenu = new TextButton("Main menu", textButtonStyle);
        btnMainMenu.setPosition(480, 110);

        btnMainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenManager.changeScreen(ScreenManager.ScreenType.MAIN_MENU);
            }
        });

        stage.addActor(btnMainMenu);
        skin.dispose();
    }

    public void update(float dt) {
        stage.act(dt);
    }

    @Override
    public void render(float delta) {
        update(delta);
        ScreenUtils.clear(0.0f, 0.0f, 0.2f, 1);
        getBatch().begin();
        font72.draw(getBatch(), "Game Over", 0, HEIGHT - 150,
                    WIDTH, 1, false);
        displayPlayerStatistics();
        getBatch().end();
        stage.draw();
    }

    private void displayPlayerStatistics() {
        Player p = ((GameScreen)screenManager.getScreen(ScreenManager.ScreenType.GAME))
                .getGameController().getPlayer();
        font32.draw(getBatch(), "Statistics:", WIDTH/2 - 120, HEIGHT - 250);

        StringBuilder sb = new StringBuilder();
        sb.append("received coins: ").append(p.getCoins()).append("\n");
        sb.append("received score: ").append(p.getScoreView()).append("\n");
        sb.append("destroyed asteroid: ").append(p.getDestroyedAsteroid()).append("\n");

        font24.draw(getBatch(),sb, WIDTH/2 - 100,HEIGHT - 300);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
