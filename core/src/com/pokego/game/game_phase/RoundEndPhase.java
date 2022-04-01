package com.pokego.game.game_phase;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pokego.game.game_ui.PlayerNumber;

/**
 * The Phase triggered at the end of each round. Checks if any Player is defeated, else goes back to RoundStartPhase. If any Player is defeated, set game to game over.
 */
public class RoundEndPhase extends Phase {

    /**
     * Constructs the RoundEndPhase object.
     * @param phasePacket PhasePacket to transfer data between Phases.
     * @param gpm The current GamePhaseManager being used in GameState.
     */
    public RoundEndPhase(PhasePacket phasePacket, GamePhaseManager gpm) {
        super(phasePacket, gpm);

        phaseName = GamePhase.ROUND_END;

        // Extract data from Phase Packet
        extractPhasePacket(phasePacket);

        bufferTime = 0.5f;

        //textWidget.setText(String.format("Player one has %d characters\nPlayer two has %d characters", playerOne.getCharactersLeft(), playerTwo.getCharactersLeft()));
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

            // If both players still have characters remaining, repeat loop
            if (!playerOne.isDefeated() && !playerTwo.isDefeated()) {
                gpm.setPhase(new RoundStartPhase(phasePacket, gpm));
            } else {
                // Set winner in PhasePacket
                // In case of draw
                if (playerOne.isDefeated() && playerTwo.isDefeated()) {
                    phasePacket.setWinner(PlayerNumber.PLAYER_ONE);
                    phasePacket.setWinner(PlayerNumber.PLAYER_TWO);
                }
                else if (playerOne.isDefeated()) {
                    phasePacket.setWinner(PlayerNumber.PLAYER_TWO);
                } else {
                    phasePacket.setWinner(PlayerNumber.PLAYER_ONE);
                }
                gpm.storePhasePacket(phasePacket);
                gpm.setGameOver(); // ==> Go back to GameState, return phase packet.
            }

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
