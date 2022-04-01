package com.pokego.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Stack;

/**
 * The GameStateManager controls the different States to be displayed onto the screen. It uses a stack to store various states of the game. By pushing or peeking the stack, the GameStateManager can easily choose which state is currently active in the game.
 */
public class GameStateManager {
    private Stack<State> states;

    /**
     * Constructs the GameStateManager.
     */
    public GameStateManager() {
        states = new Stack<State>();
    }

    /**
     * Push a State to the GameStateManager.
     * @param state State to be pushed.
     */
    public void push(State state) {
        states.push(state);
    }

    /**
     * Pops the top state in the GameStateManager.
     */
    public void pop() {
        if (!states.empty()) {
            states.pop().dispose(); // Dispose currently popped State, frees memory.
        }
    }

    /**
     * Remove the top state, then immediately push new state.
     * @param state State to be set.
     */
    public void set(State state) {
        states.pop().dispose();
        states.push(state);
    }

    /**
     * Runs the current State update() function.
     * @param dt delta time
     */
    public void update(float dt) {
        states.peek().update(dt);
    }

    /**
     * Runs the current State render() function.
     * @param sb SpriteBatch
     * @param sr ShapeRenderer
     */
    public void render(SpriteBatch sb, ShapeRenderer sr) {
        states.peek().render(sb, sr);
    }

}

