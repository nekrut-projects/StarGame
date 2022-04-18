package ru.gb.stargame.game.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import ru.gb.stargame.game.managers.BulletManager;

public class Hero extends Ship {
    private Circle magneticArea;

    public Hero(TextureRegion texture, BulletManager bulletManager) {
        super(texture, bulletManager);
        this.magneticArea = new Circle(getHitArea());
    }

    public void update(float dt){
        super.update(dt);
        magneticArea.setPosition(getPosition());
    }

    public Circle getMagneticArea() {
        return magneticArea;
    }
}
