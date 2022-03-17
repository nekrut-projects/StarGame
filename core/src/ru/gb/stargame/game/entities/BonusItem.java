package ru.gb.stargame.game.entities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import ru.gb.stargame.game.helpers.Poolable;

public class BonusItem implements Poolable {
    public enum Type{
        COINS,
        MEDICINES,
        BULLETS,
        WEAPON;
        private static Type getType(int type){
            Type t = null;
            for (int i = 0; i < Type.values().length; i++) {
                if (values()[i].ordinal() == type){
                    t = values()[i];
                }
            }
            return t;
        }
    }
    private boolean active;
    private Type type;
    private float positionX;
    private float positionY;
    private Circle hitArea;

    public BonusItem() {
        this.hitArea = new Circle(0,0,0);
        this.active = false;
        this.type = Type.MEDICINES;
    }

    public void activate(float positionX, float positionY){
        this.positionX = positionX;
        this.positionY = positionY;
        this.active = true;
        this.type = Type.getType(MathUtils.random(0, Type.values().length-1));
        this.hitArea.set(positionX, positionY, 50);
    }

    public void deactivate(){
        this.active = false;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public Type getType() {
        return type;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public Circle getHitArea() {
        return hitArea;
    }
}
