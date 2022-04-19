package ru.gb.stargame.game.managers;

import com.badlogic.gdx.math.Vector2;
import ru.gb.stargame.game.constants.BulletConstants;
import ru.gb.stargame.game.entities.BotShip;
import ru.gb.stargame.game.helpers.ObjectPool;

public class BotShipManager extends ObjectPool<BotShip> {
    private BulletManager bulletManager;
    private Vector2 tempVec;
    private Vector2 positionPlayer;
    @Override
    protected BotShip newObject() {
        return new BotShip(bulletManager);
    }

    public BotShipManager(BulletManager bulletManager, Vector2 positionPlayer) {
        this.bulletManager = bulletManager;
        this.positionPlayer = positionPlayer;
        this.tempVec = new Vector2(0,0);
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            BotShip bs = activeList.get(i);

            tempVec.set(positionPlayer).sub(bs.getPosition()).nor();
            bs.setAngle(tempVec.angleDeg());

            if (positionPlayer.dst(bs.getPosition()) > 200){
                bs.accelerate(dt);
                bs.shoot(BulletConstants.OwnerBullet.BOT, 5);
            }

            bs.update(dt);
        }
        checkPool();
    }

}
