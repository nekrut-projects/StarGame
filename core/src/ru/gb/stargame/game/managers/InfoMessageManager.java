package ru.gb.stargame.game.managers;

import com.badlogic.gdx.math.Vector2;
import ru.gb.stargame.game.entities.InfoMessage;
import ru.gb.stargame.game.helpers.ObjectPool;

public class InfoMessageManager extends ObjectPool<InfoMessage> {
    @Override
    protected InfoMessage newObject() {
        return new InfoMessage();
    }

    public void update(float dt){
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        checkPool();
    }

    public void showInfoMessage(Vector2 position, StringBuilder text, int color){
        getActiveElement().activate(position, text, color);
    }
}
