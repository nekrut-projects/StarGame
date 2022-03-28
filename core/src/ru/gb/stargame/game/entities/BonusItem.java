package ru.gb.stargame.game.entities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.gb.stargame.game.constants.BonusConstants;
import ru.gb.stargame.game.helpers.Poolable;

import static ru.gb.stargame.game.managers.BonusItemManager.*;

public class BonusItem implements Poolable {
    private boolean active;
    private TypeBonus type;
    private float lifetime;
    private Vector2 position;
    private Vector2 velocity;
    private Circle hitArea;

    public BonusItem() {
        this.position = new Vector2(0,0);
        this.velocity = new Vector2(0, 0);
        this.hitArea = new Circle(0,0,0);
        this.active = false;
        this.type = TypeBonus.MEDICINES;
    }

    public void activate(Vector2 position){
        this.position.set(position);
        this.velocity.set(MathUtils.random(-1.0f, 1.0f), MathUtils.random(-1.0f, 1.0f));
        this.velocity.nor().scl(50.0f);
        this.active = true;
        this.type = TypeBonus.getType(MathUtils.random(0, TypeBonus.values().length-1));
        this.hitArea.set(position, 50.0f);
        this.lifetime = 0f;
    }

    public void deactivate(){
        this.active = false;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public TypeBonus getType() {
        return type;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Circle getHitArea() {
        return hitArea;
    }

    public void update(float dt) {
        position.mulAdd(velocity, dt);
        hitArea.setPosition(position);
        lifetime += dt;
        if (lifetime >= BonusConstants.LIFETIME){
            deactivate();
        }
    }

    public float getLifetime() {
        return lifetime;
    }
}
