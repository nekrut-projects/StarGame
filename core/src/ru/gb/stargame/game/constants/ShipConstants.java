package ru.gb.stargame.game.constants;

import ru.gb.stargame.screen.utils.Assets;

public class ShipConstants {
    public static final int POWER_SHIP = 500;
    public static final float RECHARGE_TIME = 0.2f;
    public static final int MAX_HP = 100;
    public static final int POINTS_FOR_DESTROYING_ASTEROID = 100;
    public static final int COLLISION_DAMAGE = 10;
    public static final int WIDTH = Assets.getInstance().getAtlas().findRegion("ship").getRegionWidth();
    public static final int HEIGHT = Assets.getInstance().getAtlas().findRegion("ship").getRegionHeight();
}
