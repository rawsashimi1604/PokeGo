package com.pokego.game.game_phase;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pokego.datamodel.GameTextWidget;
import com.pokego.game.game_data.Move;
import com.pokego.game.game_data.Player;
import com.pokego.game.game_ui.PlayerUI;

/**
 * Each Phase represents a checkpoint in the GameState where the logic changes. For example, the text in the text widget might change, HP might decrease or animations might be displayed for a certain time.
 */
public abstract class Phase {

    public static final float BUFFER_TIME = 0.1f;

    protected PhasePacket phasePacket;
    protected GamePhaseManager gpm;
    protected GamePhase phaseName;

    protected Player playerOne;
    protected Player playerTwo;
    protected PlayerUI playerOneUI;
    protected PlayerUI playerTwoUI;
    protected GameTextWidget textWidget;
    protected Move playerOneMove;
    protected Move playerTwoMove;

    // Buffer time before changing to next state.
    protected float timeElapsed;
    protected float bufferTime;

    /**
     * Constructs the Phase object.
     * @param phasePacket PhasePacket to transfer data between Phases.
     * @param gpm The current GamePhaseManager being used in GameState.
     */
    protected Phase(PhasePacket phasePacket, GamePhaseManager gpm) {
        this.phasePacket = phasePacket;
        this.gpm = gpm;
        bufferTime = BUFFER_TIME;
    }

    /**
     * Extracts data from PhasePacket and stores it in the current Phase.
     * @param phasePacket PhasePacket to extract data from.
     */
    protected void extractPhasePacket(PhasePacket phasePacket) {
        // Extract data from phase packet to current phase.
        playerOne = phasePacket.getPlayerOne();
        playerTwo = phasePacket.getPlayerTwo();
        playerOneUI = phasePacket.getPlayerOneUI();
        playerTwoUI = phasePacket.getPlayerTwoUI();
        textWidget = phasePacket.getTextWidget();
        playerOneMove = phasePacket.getPlayerOneMove();
        playerTwoMove = phasePacket.getPlayerTwoMove();
    }

    /**
     * Get current GamePhase name.
     * @return Returns current GamePhase name.
     */
    public GamePhase getPhaseName() {
        return phaseName;
    }

    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb, ShapeRenderer sr);
    public abstract void dispose();

}
