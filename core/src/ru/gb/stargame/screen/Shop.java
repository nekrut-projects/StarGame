package ru.gb.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.gb.stargame.game.constants.BonusConstants;
import ru.gb.stargame.game.constants.ShopConstants;
import ru.gb.stargame.game.entities.Player;
import ru.gb.stargame.screen.utils.Assets;

public class Shop{
    private Group group;
    private Stage mainStage;
    private Stage stageBtn;
    private TextButton bayBtn;
    private ImageButton btnClose;
    private TextButton btnBayBullets;
    private TextButton btnBayHp;
    private TextButton btnBayMagnetic;
    private BitmapFont font24;
    private Player player;

    public Shop(Viewport viewport, SpriteBatch batch, final GameScreen gameScreen) {
        this.mainStage = new Stage(viewport, batch);
        this.stageBtn = new Stage(viewport, batch);
        this.player = gameScreen.getGameController().getPlayer();
        this.font24 = Assets.getInstance().getAssetManager().get("fonts/font24.ttf");
        this.group = new Group();


        Pixmap pixmap = new Pixmap(400, 400, Pixmap.Format.RGB888);
        pixmap.setColor(0, 0, 0.6f, 0.3f);
        pixmap.fill();
        Image image = new Image(new Texture(pixmap));
        this.group.addActor(image);

        Skin skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());

        this.btnClose = new ImageButton(skin.getDrawable("close"));
        btnClose.setPosition(340, 340);
        btnClose.setTransform(true);
        btnClose.setScale(0.7f);

        btnClose.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                group.setVisible(false);
                gameScreen.activate();
            }
        });

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("baybtn");
        textButtonStyle.font = font24;
        skin.add("simpleSkin", textButtonStyle);

        this.btnBayHp = new TextButton("Bay HP", textButtonStyle);
        btnBayHp.setPosition(20, 250);
        btnBayHp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (player.reduceCoins(ShopConstants.HP_PRICE)){
                    player.addHp(BonusConstants.SMALL_MEDICINE);
                }
            }
        });

        this.btnBayBullets = new TextButton("Bay Bullets", textButtonStyle);
        btnBayBullets.setPosition(20, 150);
        btnBayBullets.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (player.reduceCoins(ShopConstants.BULLETS_PRICE)){
                    player.addBullets(BonusConstants.MEDIUM_BULLETS);
                }
            }
        });

        this.btnBayMagnetic = new TextButton(" Upgrade magnetic power ", textButtonStyle);
        btnBayMagnetic.setPosition(20, 50);
        btnBayMagnetic.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (player.reduceCoins(ShopConstants.MAGNETIC_PRICE)){
                    player.increaseMagnetismLevel();
                }
            }
        });

        this.group.addActor(btnClose);
        this.group.addActor(btnBayHp);
        this.group.addActor(btnBayBullets);
        this.group.addActor(btnBayMagnetic);




        this.group.setVisible(false);
        mainStage.addActor(group);
        Gdx.input.setInputProcessor(mainStage);
        skin.dispose();
    }

    public void act(float dt){
        mainStage.act(dt);
    }

    public void activate(){
        group.setVisible(true);
    }

    public void draw(){
        this.mainStage.draw();
    }

    public void deactivate(){
        group.setVisible(false);
    }

    public boolean isActive(){
        return group.isVisible();
    }

    public void dispose(){
        this.mainStage.dispose();
    }

}
