package com.pokego.game.game_phase;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * The Phase which displays whose Character has fainted.
 */
public class RoundDisplayFaintedPhase extends Phase {

    private boolean playerOneFainted;
    private boolean playerTwoFainted;


    /**
     * Constructs the RoundDisplayFaintedPhase object.
     * @param phasePacket PhasePacket to transfer data between Phases.
     * @param gpm The current GamePhaseManager being used in GameState.
     */
    public RoundDisplayFaintedPhase(PhasePacket phasePacket, GamePhaseManager gpm) {
        super(phasePacket, gpm);

        phaseName = GamePhase.ROUND_DISPLAY_FAINTED;

        // Extract data from Phase Packet
        extractPhasePacket(phasePacket);

        bufferTime = 0.5f;

        // set text widget text according to which character has fainted.
        textWidget.setText(getFaintedText());

    }

    /**
     * Helper function to format text to display to the GameTextWidget.
     * @return Returns formatted text to display to the GameTextWidget.
     */
    private String getFaintedText() {

        // Both fainted
        if (!playerOne.getCurrentCharacter().isAlive() && !playerTwo.getCurrentCharacter().isAlive()) {
            playerOneFainted = true;
            playerTwoFainted = true;
            return String.format("%s has fainted.\n%s has fainted.", playerOne.getCurrentCharacter().getName(), playerTwo.getCurrentCharacter().getName());
        }
        // Player One fainted
        else if (!playerOne.getCurrentCharacter().isAlive()) {
            playerOneFainted = true;
            return String.format("%s has fainted.", playerOne.getCurrentCharacter().getName());
        }
        // Player Two fainted
        else if (!playerTwo.getCurrentCharacter().isAlive()) {
            playerTwoFainted = true;
            return String.format("%s has fainted.", playerTwo.getCurrentCharacter().getName());
        }
        // None fainted (WONT HAPPEN)
        return "";
    }

    /**
     * Updates logic defined in Phase, function is ran once every frame.
     * @param dt delta time
     */
    @Override
    public void update(float dt) {
        timeElapsed += dt;

        // Only update text widget if it hasn't finished scrolling through all text.
        if (!textWidget.displayedFinish() && timeElapsed < textWidget.getScrollDuration()) {
            textWidget.updateTextScroll(dt);
        } else if (timeElapsed > textWidget.getScrollDuration() + bufferTime) {
            // Go to new state after textWidget scrolls finish and after accounting for bufferTime.
            gpm.setPhase(new RoundDisplayNextCharPhase(phasePacket, gpm, playerOneFainted, playerTwoFainted));
        }
    }

    /**
     * Renders sprites and shapes, function is ran once every frame.
     * @param sb SpriteBatch
     * @param sr ShapeRenderer
     */
    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {


        playerOneUI.draw(sb, sr);
        playerTwoUI.draw(sb, sr);
        textWidget.draw(sb, sr);

        sb.begin();
        playerOne.getCurrentCharacter().getSprite().draw(sb);
        playerTwo.getCurrentCharacter().getSprite().draw(sb);
        sb.end();
    }

    /**
     * Frees up memory by disposing sprites from SpriteBatch.
     */
    @Override
    public void dispose() {
    }
}
