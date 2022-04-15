package ru.gb.stargame.game.managers;

import com.badlogic.gdx.audio.Sound;
import ru.gb.stargame.screen.utils.Assets;

public class EffectsController {
    private ParticlesManager particlesManager;
    private InfoMessageManager infoMessageManager;
    private Sound shootSound;

    public EffectsController() {
        this.particlesManager = new ParticlesManager();
        this.infoMessageManager = new InfoMessageManager();
        this.shootSound = Assets.getInstance().getAssetManager().get("audio/shoot.mp3");
    }

    public ParticlesManager getParticlesManager() {
        return particlesManager;
    }

    public InfoMessageManager getInfoMessageManager() {
        return infoMessageManager;
    }

    public void update(float dt) {
        particlesManager.update(dt);
        infoMessageManager.update(dt);
    }

    public Sound getShootSound() {
        return shootSound;
    }

    public void playSoundShoot(){
        shootSound.play();
    }

}
