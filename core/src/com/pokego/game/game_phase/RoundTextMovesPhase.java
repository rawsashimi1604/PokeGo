package com.pokego.game.game_phase;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * The Phase which updates GameTextWidget according to what move each Character has used.
 */
public class RoundTextMovesPhase extends Phase{

    /**
     * Constructs the RoundTextMovesPhase object.
     * @param phasePacket PhasePacket to transfer data between Phases.
     * @param gpm The current GamePhaseManager being used in GameState.
     */
    public RoundTextMovesPhase(PhasePacket phasePacket, GamePhaseManager gpm) {
        super(phasePacket, gpm);
        System.out.println("ROUND_DISPLAY_MOVES PHASE");

        phaseName = GamePhase.ROUND_TEXT_MOVES;

        // Extract data from Phase Packet.
        extractPhasePacket(phasePacket);

        // Set text widget according to what moves each character has used.
        textWidget.setText(getMoveString());
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
            // Go to new state after textWidget scrolls finish and after accounting for BUFFER_TIME.
            gpm.setPhase(new RoundDisplayMovesPhase(phasePacket, gpm));
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

    /**
     * Helper function to get formatted String for the current Phase.
     * @return Formatted String to send to GameTextWidget for the current Phase.
     */
    private String getMoveString() {
        // String to display in text widget, depending on what moves were chosen.
        String textWidgetOut;
        if (phasePacket.getPlayerOneMove() == null && phasePacket.getPlayerTwoMove() == null) {
            textWidgetOut = String.format("%s did not use any move.\n%s did not use any move.", playerOne.getCurrentCharacter().getName(), playerTwo.getCurrentCharacter().getName());
        }  else if(phasePacket.getPlayerOneMove() == null) {
            textWidgetOut = String.format("%s did not use any move.\n%s used %s.", playerOne.getCurrentCharacter().getName(), playerTwo.getCurrentCharacter().getName(), playerTwoMove.getName());
        } else if(phasePacket.getPlayerTwoMove() == null) {
            textWidgetOut = String.format("%s used %s.\n%s did not use any move.", playerOne.getCurrentCharacter().getName(), playerOneMove.getName(), playerTwo.getCurrentCharacter().getName());
        } else {
            textWidgetOut = String.format("%s used %s.\n%s used %s.", playerOne.getCurrentCharacter().getName(), playerOneMove.getName(), playerTwo.getCurrentCharacter().getName(), playerTwoMove.getName());
        }

        return textWidgetOut;
    }
}
