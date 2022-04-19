package ru.gb.stargame.game.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.gb.stargame.game.constants.ScreenConstants;
import ru.gb.stargame.game.helpers.Poolable;
import ru.gb.stargame.game.managers.BulletManager;

public class BotShip extends Ship implements Poolable {
    private boolean active;

    public BotShip(BulletManager bulletManager) {
        super(bulletManager);
        getPosition().x = MathUtils.random(ScreenConstants.WIDTH);
        getPosition().y = MathUtils.random(ScreenConstants.HEIGHT);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void activate(Vector2 position){
        active = true;
    }



    public void deactivate(){
        active = false;
    }

}
