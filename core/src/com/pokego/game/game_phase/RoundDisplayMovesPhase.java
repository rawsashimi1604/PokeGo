package com.pokego.game.game_phase;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pokego.game.game_ui.PlayerNumber;
import com.pokego.game.game_data.MoveClass;

/**
 * The Phase which displays animations according to Moves used by respective Characters.
 */
public class RoundDisplayMovesPhase extends Phase {

    private static final float ANIMATION_SCALE = 3f;
    private final static int DEFENSE_DISPLAY_OFFSET = 50;
    private final static int PLAYER_TWO_DEFENSE_DISPLAY_OFFSET = 45;

    /**
     * Constructs the RoundDisplayMovesPhase object.
     * @param phasePacket PhasePacket to transfer data between Phases.
     * @param gpm The current GamePhaseManager being used in GameState.
     */
    public RoundDisplayMovesPhase(PhasePacket phasePacket, GamePhaseManager gpm) {
        super(phasePacket, gpm);

        phaseName = GamePhase.ROUND_DISPLAY_MOVES;

        // Extract data from Phase Packet
        extractPhasePacket(phasePacket);

        System.out.println("Display Moves Phase.");

        // Set buffer time for Phase,
        bufferTime = 0.5f;

        // Position player one and two moves according to move class.
        if (playerOneMove != null) {
            playerOneMove.getAnimation().scale(ANIMATION_SCALE);
            playerOneMove.getAnimation().reposition(playerOne.getCurrentCharacter().getSprite().getX(), playerOne.getCurrentCharacter().getSprite().getY());
            if (playerOneMove.getMoveClass() == MoveClass.DEFENSE) {
                playerOneMove.getAnimation().moveRight(DEFENSE_DISPLAY_OFFSET);
            }
        }
        if (playerTwoMove != null) {
            playerTwoMove.getAnimation().scale(ANIMATION_SCALE);
            playerTwoMove.getAnimation().reposition(playerTwo.getCurrentCharacter().getSprite().getX(), playerTwo.getCurrentCharacter().getSprite().getY());
            if (playerTwoMove.getMoveClass() == MoveClass.DEFENSE) {
                playerTwoMove.getAnimation().moveLeft(DEFENSE_DISPLAY_OFFSET + PLAYER_TWO_DEFENSE_DISPLAY_OFFSET);
            }
        }
    }

    /**
     * Updates logic defined in Phase, function is ran once every frame.
     * @param dt delta time
     */
    @Override
    public void update(float dt) {
        timeElapsed += dt;

        // animate and check for playerOneMove collisions
        if (playerOneMove != null) {
            playerOneMove.animate(dt);
            if (playerOneMove.getMoveClass() == MoveClass.ATTACK && playerOneMove.isAnimating()) {
                if (playerTwo.getCurrentCharacter().collidesWith(playerOneMove.getAnimation(), PlayerNumber.PLAYER_ONE)) {
                    System.out.println("playerTWO : COLLISION ");
                    playerOneMove.stopAnimating();
                }
            }
        }

        // animate and check for playerTwoMove collisions
        if (playerTwoMove != null) {
            playerTwoMove.animate(dt);
            if (playerTwoMove.getMoveClass() == MoveClass.ATTACK && playerTwoMove.isAnimating()) {
                if (playerOne.getCurrentCharacter().collidesWith(playerTwoMove.getAnimation(), PlayerNumber.PLAYER_TWO)) {
                    System.out.println("playerONE : COLLISION ");
                    playerTwoMove.stopAnimating();
                }
            }
        }

        // Only update text widget if it hasn't finished scrolling through all text.
        if (!textWidget.displayedFinish() && timeElapsed < textWidget.getScrollDuration()) {
            textWidget.updateTextScroll(dt);
        } else if (timeElapsed > textWidget.getScrollDuration() + bufferTime) {

            // Rescale move back to normal size, reset move direction
            if (playerOneMove != null) {
                playerOneMove.getAnimation().scale(1/ANIMATION_SCALE);
            }
            if (playerTwoMove != null) {
                playerTwoMove.getAnimation().scale(1/ANIMATION_SCALE);
                playerTwoMove.getAnimation().flip();
            }


            // Go to new state after textWidget scrolls finish and after accounting for bufferTime.
            gpm.setPhase(new RoundLowerHpPhase(phasePacket, gpm));
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
        if (playerOneMove != null) {
            if (playerOneMove.isAnimating()) {
                playerOneMove.getAnimation().draw(sb);
            }
        }

        if (playerTwoMove != null) {
            if (playerTwoMove.isAnimating()) {
                playerTwoMove.getAnimation().draw(sb);
            }
        }

        playerOne.getCurrentCharacter().getSprite().draw(sb);
        playerTwo.getCurrentCharacter().getSprite().draw(sb);
        sb.end();
    }

    /**
     * Frees up memory by disposing sprites from SpriteBatch.
     */
    @Override
    public void dispose() {
        playerOneMove.getAnimation().dispose();
        playerTwoMove.getAnimation().dispose();
    }
}
