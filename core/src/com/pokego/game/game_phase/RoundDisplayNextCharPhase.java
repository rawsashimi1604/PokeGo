package com.pokego.game.game_phase;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pokego.game.game_ui.PlayerNumber;

/**
 * The Phase which updates the Player's Character. Phase is only triggered if Player's current Character has fainted.
 */
public class RoundDisplayNextCharPhase extends Phase {

    private boolean playerOneFainted;
    private boolean playerTwoFainted;

    /**
     * Constructs the RoundDisplayNextCharPhase object.
     * @param phasePacket PhasePacket to transfer data between Phases.
     * @param gpm The current GamePhaseManager being used in GameState.
     * @param playerOneFainted True if player 1's character has fainted. False otherwise.
     * @param playerTwoFainted True if player 2's character has fainted. False otherwise.
     */
    public RoundDisplayNextCharPhase(PhasePacket phasePacket, GamePhaseManager gpm, boolean playerOneFainted, boolean playerTwoFainted) {
        super(phasePacket, gpm);

        phaseName = GamePhase.ROUND_DISPLAY_NEXT_CHAR;

        // Extract data from Phase Packet
        extractPhasePacket(phasePacket);

        bufferTime = 0.5f;

        // Get state of character from prev state.
        this.playerOneFainted = playerOneFainted;
        this.playerTwoFainted = playerTwoFainted;

        // Toggle next char based on prev state.
        if (playerOneFainted && playerOne.getCharactersLeft() > 1) {
            System.out.println("Player one char fainted..");
            System.out.println("Player one size: " + playerOne.getCharactersLeft());
            playerOne.killCharacter();
            playerOne.nextCharacter();
        } else if (playerOneFainted && playerOne.getCharactersLeft() == 1) {
            System.out.println("Player one char fainted..");
            System.out.println("Player one size: " + playerOne.getCharactersLeft());
            playerOne.killCharacter();
        }

        if (playerTwoFainted && playerTwo.getCharactersLeft() > 1) {
            System.out.println("Player two char fainted..");
            System.out.println("Player two size: " + playerTwo.getCharactersLeft());
            playerTwo.killCharacter();
            playerTwo.nextCharacter();
            System.out.println("Player two size after kill: " + playerTwo.getCharactersLeft());
            System.out.println(playerTwo.getCharacters());
        } else if (playerTwoFainted && playerTwo.getCharactersLeft() == 1) {
            System.out.println("Player two char fainted..");
            System.out.println("Player two size: " + playerTwo.getCharactersLeft());
            playerTwo.killCharacter();
            System.out.println("Player two size after kill: " + playerTwo.getCharactersLeft());
            System.out.println(playerTwo.getCharacters());
        }

        // Set text widget according to what character was sent out.
        textWidget.setText(getNextCharString());

    }

    /**
     * Helper function to format text to be sent to the GameTextWidget.
     * @return Returns formatted text to be sent to the GameTextWidget.
     */
    private String getNextCharString() {
        if (playerOneFainted && playerTwoFainted) {
            if (playerOne.getCharactersLeft() >= 1 && playerTwo.getCharactersLeft() >= 1) {
                return String.format("%s sent out %s.\n%s sent out %s.", playerOne.getName(), playerOne.getCurrentCharacter().getName(), playerTwo.getName(), playerTwo.getCurrentCharacter().getName());
            } else if (playerOne.isDefeated() && playerTwo.getCharactersLeft() >= 1) {
                return String.format("%s has no more Poke-mon to fight!", playerOne.getName());
            }  else if (playerOne.getCharactersLeft() >= 1 && playerTwo.isDefeated()) {
                return String.format("%s has no more Poke-mon to fight!", playerTwo.getName());
            }
        } else if (playerOneFainted) {
            if (playerOne.getCharactersLeft() >= 1){
                return String.format("%s sent out %s.", playerOne.getName(), playerOne.getCurrentCharacter().getName());
            } else if (playerOne.isDefeated()){
                return String.format("%s has no more Poke-mon to fight!", playerOne.getName());
            }
        } else if (playerTwoFainted) {
            if (playerTwo.getCharactersLeft() >= 1){
                return String.format("%s sent out %s.", playerTwo.getName(), playerTwo.getCurrentCharacter().getName());
            } else if (playerTwo.isDefeated()){
                return String.format("%s has no more Poke-mon to fight!", playerTwo.getName());
            }
        }

        return "";
    }

    /**
     * Updates logic defined in Phase, function is ran once every frame.
     * @param dt delta time
     */
    @Override
    public void update(float dt) {
        timeElapsed += dt;

        // Update HP bars and characters remaining to latest
        playerOneUI.updateHealth(playerOne.getCurrentCharacter().getHealthPoints());
        playerTwoUI.updateHealth(playerTwo.getCurrentCharacter().getHealthPoints());

        playerOneUI.updateCharacterName(playerOne.getCurrentCharacter().getName());
        playerTwoUI.updateCharacterName(playerTwo.getCurrentCharacter().getName());

        playerOneUI.updateCharacters(playerOne.getCharactersLeft());
        playerTwoUI.updateCharacters(playerTwo.getCharactersLeft());

        playerOneUI.updateMoves(PlayerNumber.PLAYER_ONE);
        playerTwoUI.updateMoves(PlayerNumber.PLAYER_TWO);

        // Only update text widget if it hasn't finished scrolling through all text.
        if (!textWidget.displayedFinish() && timeElapsed < textWidget.getScrollDuration()) {
            textWidget.updateTextScroll(dt);
        } else if (timeElapsed > textWidget.getScrollDuration() + bufferTime) {
            // Go to new state after textWidget scrolls finish and after accounting for bufferTime.
            phasePacket.setPlayerOne(playerOne);
            phasePacket.setPlayerTwo(playerTwo);
            gpm.setPhase(new RoundEndPhase(phasePacket, gpm));
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
