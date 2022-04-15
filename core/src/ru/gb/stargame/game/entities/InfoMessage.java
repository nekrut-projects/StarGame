package ru.gb.stargame.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import ru.gb.stargame.game.helpers.Poolable;

public class InfoMessage implements Poolable {
    private boolean active;
    private Vector2 position;
    private StringBuilder text;
    private float lifetime;
    private Color color;

    public InfoMessage() {
        this.active = false;
        this.lifetime = 1.0f;
        this.text = new StringBuilder();
        this.position = new Vector2(0,0);
        this.color = new Color();
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void activate(Vector2 position, StringBuilder text, int color){
        this.position.set(position);
        this.text.setLength(0);
        this.text.append(text);
        this.color.set(color);
        this.lifetime = 1.0f;
        this.active = true;
    }

    public void update(float dt){
        this.lifetime -= dt;
        if (lifetime < 0){
            active = false;
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public StringBuilder getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }
}
