package ru.gb.stargame.game.managers;

import com.badlogic.gdx.math.Vector2;
import ru.gb.stargame.game.entities.BonusItem;
import ru.gb.stargame.game.helpers.ObjectPool;

import static ru.gb.stargame.game.constants.ScreenConstants.HEIGHT;
import static ru.gb.stargame.game.constants.ScreenConstants.WIDTH;


public class BonusItemManager extends ObjectPool<BonusItem> {
    public enum TypeBonus{
        COINS,
        MEDICINES,
        BULLETS,
        WEAPON;

        public static TypeBonus getType(int type){
            TypeBonus t = null;
            for (int i = 0; i < TypeBonus.values().length; i++) {
                if (values()[i].ordinal() == type){
                    t = values()[i];
                }
            }
            return t;
        }
    }

    public BonusItemManager() {
    }

    @Override
    protected BonusItem newObject() {
        return new BonusItem();
    }

    public void generateBonus(Vector2 position) {
        getActiveElement().activate(position);
    }

    public void update(float dt){
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
            checkOutOfBorder(activeList.get(i));
        }
        checkPool();
    }

    private void checkOutOfBorder(BonusItem bonus){
        float radius = bonus.getHitArea().radius;
        if (bonus.getPosition().x > WIDTH + radius ||
            bonus.getPosition().x < 0 - radius ||
            bonus.getPosition().y > HEIGHT + radius ||
            bonus.getPosition().y < 0 - radius){
            bonus.deactivate();
        }
    }

}
