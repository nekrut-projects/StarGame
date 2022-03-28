package ru.gb.stargame.game;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.gb.stargame.game.constants.HeroConstants;
import ru.gb.stargame.game.entities.*;
import ru.gb.stargame.game.managers.ParticleManager;
import ru.gb.stargame.screen.utils.Assets;

public class GameRenderer {
    private GameController gc;
    private SpriteBatch batch;
    private BitmapFont font32;
    private Texture textureCosmos;
    private TextureRegion textureStar;
    private TextureRegion textureBullet;
    private TextureRegion textureShip;
    private TextureRegion textureAsteroid;
    private TextureRegion textureMedicine;
    private TextureRegion textureAddBullets;
    private TextureRegion textureAddWeapon;
    private TextureRegion textureCoins;

    private StringBuilder sb;

    public GameRenderer(GameController gc, SpriteBatch batch) {
        initTextures();
        this.gc = gc;
        this.batch = batch;
        this.font32 = Assets.getInstance().getAssetManager().get("fonts/font32.ttf", BitmapFont.class);
        this.sb = new StringBuilder();
    }

    private void initTextures(){
        TextureAtlas atlas = Assets.getInstance().getAtlas();
        this.textureCosmos = new Texture("images/bg.png");
        this.textureCoins = atlas.findRegion("coins");
        this.textureStar = atlas.findRegion("star16");
        this.textureAsteroid = atlas.findRegion("asteroid");
        this.textureBullet = atlas.findRegion("bullet");
        this.textureShip = atlas.findRegion("ship");
        this.textureMedicine = atlas.findRegion("medicine");
        this.textureAddBullets = atlas.findRegion("addbullets");
        this.textureAddWeapon = atlas.findRegion("levelup");
    }
    public void render () {
        ScreenUtils.clear(0, 0, 0.5f, 1f);
        batch.begin();
            renderBackground();
            renderBullets();
            renderShipHero();
            renderParticles();
            renderBonusItems();
            renderAsteroids();
            renderGUI();
        batch.end();
    }

    private void renderBackground(){
        Background back = gc.getBackground();
        batch.draw(textureCosmos, 0, 0);
        for (int i = 0; i < back.getStars().length; i++) {
            Background.Star s = back.getStars()[i];
            batch.draw(textureStar, s.getPosition().x - textureStar.getRegionWidth()/2,
                    s.getPosition().y - textureStar.getRegionHeight()/2,
                    textureStar.getRegionWidth()/2,textureStar.getRegionHeight()/2,
                    textureStar.getRegionWidth(), textureStar.getRegionHeight(),
                    s.getScale(), s.getScale(), 0);

            if (MathUtils.random(500) < 1) {
                batch.draw(textureStar, s.getPosition().x - textureStar.getRegionWidth()/2,
                        s.getPosition().y - textureStar.getRegionHeight()/2,
                        textureStar.getRegionWidth()/2, textureStar.getRegionHeight()/2,
                        textureStar.getRegionWidth(), textureStar.getRegionHeight(),
                        s.getScale() * 2, s.getScale() * 2,0);
            }
        }
    }

    private void renderBullets() {
        for (int i = 0; i < gc.getBulletManager().getActiveList().size(); i++) {
            Bullet b = gc.getBulletManager().getActiveList().get(i);
            batch.draw(textureBullet, b.getPosition().x - textureBullet.getRegionWidth()/2,
                    b.getPosition().y - textureBullet.getRegionHeight()/2);
        }
    }

    private void renderAsteroids() {
        for (int i = 0; i < gc.getAsteroidManager().getActiveList().size(); i++) {
            Asteroid a = gc.getAsteroidManager().getActiveList().get(i);
            batch.draw(textureAsteroid, a.getPosition().x - (textureAsteroid.getRegionWidth() / 2),
                    a.getPosition().y - (textureAsteroid.getRegionHeight() / 2),(textureAsteroid.getRegionWidth() / 2),
                    (textureAsteroid.getRegionHeight() / 2), textureAsteroid.getRegionWidth(), textureAsteroid.getRegionHeight(),
                    a.getScale(), a.getScale(), a.getAngle());
        }
    }

    private void renderShipHero() {
        Hero h = gc.getPlayer().getHero();
        batch.draw(textureShip, h.getPosition().x - textureShip.getRegionWidth()/2,
                h.getPosition().y - textureShip.getRegionHeight()/2,
                textureShip.getRegionWidth()/2, textureShip.getRegionHeight()/2,
                textureShip.getRegionWidth(), textureShip.getRegionHeight(),
                1, 1, h.getAngle());
    }

    public void renderParticles() {
        TextureRegion textureParticle = textureStar;
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        ParticleManager pm = gc.getParticleManager();
        for (int i = 0; i < pm.getActiveList().size(); i++) {
            Particle p = pm.getActiveList().get(i);
            float t = p.getTime() / p.getTimeMax();
            float scale = lerp(p.getSize1(), p.getSize2(), t);
            batch.setColor(lerp(p.getR1(), p.getR2(), t), lerp(p.getG1(), p.getG2(), t),
                    lerp(p.getB1(), p.getB2(), t), lerp(p.getA1(), p.getA2(), t));
            batch.draw(textureParticle, p.getPosition().x - textureParticle.getRegionWidth()/2,
                    p.getPosition().y - textureParticle.getRegionHeight()/2,
                    textureParticle.getRegionWidth()/2, textureParticle.getRegionHeight()/2,
                    textureParticle.getRegionWidth(), textureParticle.getRegionHeight(),
                    scale, scale, 0);
        }
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        for (int i = 0; i < pm.getActiveList().size(); i++) {
            Particle p = pm.getActiveList().get(i);
            float t = p.getTime() / p.getTimeMax();
            float scale = lerp(p.getSize1(), p.getSize2(), t);
            if (MathUtils.random(0, 300) < 3) {
                scale *= 5;
            }
            batch.setColor(lerp(p.getR1(), p.getR2(), t), lerp(p.getG1(), p.getG2(), t),
                    lerp(p.getB1(), p.getB2(), t), lerp(p.getA1(), p.getA2(), t));
            batch.draw(textureStar, p.getPosition().x - textureParticle.getRegionWidth()/2,
                    p.getPosition().y - textureParticle.getRegionHeight()/2,
                    textureParticle.getRegionWidth()/2, textureParticle.getRegionHeight()/2,
                    textureParticle.getRegionWidth(), textureParticle.getRegionHeight(),
                    scale, scale, 0);
        }
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderBonusItems(){
        TextureRegion texture = textureAddWeapon;
        for (int i = 0; i < gc.getBonusItemManager().getActiveList().size(); i++) {
            BonusItem bonus = gc.getBonusItemManager().getActiveList().get(i);
            switch (bonus.getType()){
                case COINS:
                    texture = textureCoins;
                    break;
                case MEDICINES:
                    texture = textureMedicine;
                    break;
                case WEAPON:
                    texture = textureAddWeapon;
                    break;
                case BULLETS:
                    texture = textureAddBullets;
                    break;
            }
            float scale = (int)bonus.getLifetime() % 2 == 0 ? 1 : 0.8f;

            batch.draw(texture, bonus.getPosition().x - (texture.getRegionWidth() / 2),
                    bonus.getPosition().y - (texture.getRegionHeight() / 2),
                    (texture.getRegionWidth() / 2), (texture.getRegionHeight() / 2),
                    texture.getRegionWidth(), texture.getRegionHeight(),
                    scale, scale, 0);
        }

    }

    private void renderGUI() {
        sb.setLength(0);
        sb.append("SCORE: ").append(gc.getPlayer().getScoreView()).append("\n");
        sb.append("COINS: ").append(gc.getPlayer().getCoins()).append("\n");
        sb.append("HP: ").append(gc.getPlayer().getHero().getHp()).append(" / ")
                .append(HeroConstants.MAX_HP).append("\n");
        sb.append("BULLETS: ").append(gc.getPlayer().getHero().getAmountBullets()).append("\n");
        font32.draw(batch, sb, 20, 700);
    }

    public float lerp(float value1, float value2, float point) {
        return value1 + (value2 - value1) * point;
    }
}
