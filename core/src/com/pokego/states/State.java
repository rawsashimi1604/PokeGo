package com.pokego.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * A State represents a different checkpoint in the game. Each State contains its own renderable objects and logic. The States are controlled by the GameStateManager.
 */
public abstract class State {

    protected GameStateManager gsm;

    /**
     * Creates a State object.
     * @param gsm The current GameStateManager used to manage all States.
     */
    protected State(GameStateManager gsm) {
        this.gsm = gsm;
    }

    protected abstract void handleInput(); // Handle inputs made by user.
    public abstract void update(float dt); // Update game logic, used by DemoGame's render().
    public abstract void render(SpriteBatch sb, ShapeRenderer sr); // Handle SpriteBatch and ShapeRenderer for that particular State.
    public abstract void dispose(); // Free memory used by SpriteBatch.
}
