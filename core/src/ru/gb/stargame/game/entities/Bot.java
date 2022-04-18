package ru.gb.stargame.game.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import ru.gb.stargame.game.constants.ScreenConstants;
import ru.gb.stargame.game.managers.BulletManager;

public class Bot {
    private Ship ship;

    public Bot(TextureRegion texture, BulletManager bulletManager) {
        this.ship = new Ship(texture, bulletManager);
        ship.getPosition().x = MathUtils.random(ScreenConstants.WIDTH);
        ship.getPosition().y = MathUtils.random(ScreenConstants.HEIGHT);
    }

    public Ship getShip() {
        return ship;
    }
}
