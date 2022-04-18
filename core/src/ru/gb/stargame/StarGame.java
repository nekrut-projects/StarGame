package ru.gb.stargame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.gb.stargame.screen.AbstractScreen;
import ru.gb.stargame.screen.ScreenManager;
import ru.gb.stargame.screen.utils.Assets;

public class StarGame extends Game {
	private SpriteBatch batch;
	private ScreenManager screenManager;

	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.screenManager = new ScreenManager(batch, this);
		screenManager.changeScreen(ScreenManager.ScreenType.MAIN_MENU);
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		screenManager.checkLoading();
		getScreen().render(dt);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}


