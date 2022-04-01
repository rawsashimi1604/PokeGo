package com.pokego.game.game_phase;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * The GamePhaseManager controls different Phases in the GameState. It controls what Phase is currently being rendered, updated and disposes unused GameSpriteGroup objects after the lifetime of the GamePhase is over. It also stores a PhasePacket which is used to pass data around the phases.
 */
public class GamePhaseManager {

    private Phase gamePhase;
    private PhasePacket phasePacket;
    private boolean gameOver;


    public GamePhaseManager() {
        gamePhase = null;
    }

    public void setPhase(Phase gamePhase) {
        this.gamePhase = gamePhase;
    }

    public void update(float dt) {
        gamePhase.update(dt);
    }

    public void render(SpriteBatch sb, ShapeRenderer sr) {
        gamePhase.render(sb, sr);
    }

    public void dispose() {
        gamePhase.dispose();
    }

    public void setGameOver() {
        gameOver = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void storePhasePacket(PhasePacket phasePacket) {
        this.phasePacket = phasePacket;
    }

    public PhasePacket getPhasePacket() {
        return phasePacket;
    }



}
