package ru.gb.stargame.game.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import ru.gb.stargame.game.managers.BulletManager;

import static ru.gb.stargame.game.constants.ShipConstants.POWER_SHIP;

public class Hero extends Ship {
    private Circle magneticArea;

    public Hero(BulletManager bulletManager) {
        super(bulletManager);
        this.magneticArea = new Circle(getHitArea());
    }

    public void update(float dt){
        super.update(dt);
        magneticArea.setPosition(getPosition());
    }

    public void reverseCourse(float dt){
        getVelocity().x += -MathUtils.cosDeg(getAngle()) * POWER_SHIP/2 * dt;
        getVelocity().y += -MathUtils.sinDeg(getAngle()) * POWER_SHIP/2 * dt;


//        player.getHero().getVelocity().x += -MathUtils.cosDeg(player.getHero().getAngle()) * POWER_SHIP/2 * dt;
//        player.getHero().getVelocity().y += -MathUtils.sinDeg(player.getHero().getAngle()) * POWER_SHIP/2 * dt;

    }


    public Circle getMagneticArea() {
        return magneticArea;
    }
}
