package ru.gb.stargame.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.gb.stargame.screen.utils.Assets;

import static ru.gb.stargame.game.constants.ScreenConstants.WIDTH;

public class LoadingScreen extends AbstractScreen{
    private Texture texture;
    private boolean active;

    public LoadingScreen(SpriteBatch batch) {
        super(batch);
        Pixmap pixmap = new Pixmap(WIDTH - 40, 20, Pixmap.Format.RGB888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        this.texture = new Texture(pixmap);
        pixmap.dispose();
        this.active = true;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float dt) {
        if (Assets.getInstance().getAssetManager().update()){
            active = false;
            return;
        }
        active = true;
        getBatch().begin();
        getBatch().draw(texture, 40, 50, WIDTH *
                Assets.getInstance().getAssetManager().getProgress(), 20);
        getBatch().end();
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean state) {
        active = state;
    }
}
