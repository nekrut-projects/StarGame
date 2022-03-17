package ru.gb.stargame.game.managers;

import com.badlogic.gdx.math.Vector2;
import ru.gb.stargame.game.entities.BonusItem;
import ru.gb.stargame.game.helpers.ObjectPool;

import static ru.gb.stargame.screen.ScreenManager.SCREEN_HEIGHT;
import static ru.gb.stargame.screen.ScreenManager.SCREEN_WIDTH;


public class BonusItemManager extends ObjectPool<BonusItem> {

    public BonusItemManager() {
    }

    @Override
    protected BonusItem newObject() {
        return new BonusItem();
    }

    public void generateBonus(Vector2 position) {
        getActiveElement().activate(position.x, position.y);
    }

    public void update(){
        for (int i = 0; i < activeList.size(); i++) {
            checkOutOfBorder(activeList.get(i));
        }
        checkPool();
    }

    private void checkOutOfBorder(BonusItem bonus){
        if (bonus.getPositionX() > SCREEN_WIDTH){
            bonus.setPositionX(SCREEN_WIDTH-100);
        }
        if (bonus.getPositionX() < 0){
            bonus.setPositionX(100);
        }
        if (bonus.getPositionY() > SCREEN_HEIGHT){
            bonus.setPositionY(SCREEN_HEIGHT-100);
        }
        if (bonus.getPositionY() < 0){
            bonus.setPositionY(100);
        }
    }

}
