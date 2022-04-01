package com.pokego.game.game_phase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pokego.game.game_ui.PlayerNumber;
import com.pokego.states.GameState;

/**
 * The Phase which receive inputs from both Players. Displays the GameRoundTimer object.
 */
public class RoundStartPhase extends Phase {

    private GameRoundTimer timer;

    /**
     * Constructs the RoundStartPhase object.
     * @param phasePacket PhasePacket to transfer data between Phases.
     * @param gpm The current GamePhaseManager being used in GameState.
     */
    public RoundStartPhase(PhasePacket phasePacket, GamePhaseManager gpm) {
        // RUN ONCE
        super(phasePacket, gpm);
        System.out.println("ROUND_START PHASE");

        phaseName = GamePhase.ROUND_START;

        // Extract data from Phase Packet
        extractPhasePacket(phasePacket);

        // Start and create timer, based on time between rounds
        timer = new GameRoundTimer(GameState.TIME_BETWEEN_ROUNDS);

        // Reset moves
        playerOneMove = null;
        playerTwoMove = null;

        textWidget.setText("Choose your move...");
    }

    /**
     * Receive inputs from each Player. Q W E R for player 1, Up Down Left Right for player 2.
     */
    private void handleInputs() {
        // Await inputs for each player

        // Player 1 inputs
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            playerOneMove = playerOne.getCurrentCharacter().getFireMove();

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            playerOneMove = playerOne.getCurrentCharacter().getWaterMove();

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            playerOneMove = playerOne.getCurrentCharacter().getGrassMove();

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            playerOneMove = playerOne.getCurrentCharacter().getDefenseMove();



        }

        // Player 2
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            playerTwoMove = playerTwo.getCurrentCharacter().getFireMove();

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            playerTwoMove = playerTwo.getCurrentCharacter().getWaterMove();

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            playerTwoMove = playerTwo.getCurrentCharacter().getGrassMove();

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            playerTwoMove = playerTwo.getCurrentCharacter().getDefenseMove();

        }


    }

    /**
     * Updates logic defined in Phase, function is ran once every frame.
     * @param dt delta time
     */
    @Override
    public void update(float dt) {
        // If round is still ongoing, update
        if (!timer.timerEnded()) {
            handleInputs();
            textWidget.updateTextScroll(dt);
            timer.updateTime(dt);
            return;
        }

        // Cast moves after receiving inputs
        if (playerOneMove != null) {
            playerOneMove.castMove(PlayerNumber.PLAYER_TWO);
        }
        if (playerTwoMove != null) {
            playerTwoMove.castMove(PlayerNumber.PLAYER_ONE);
        }

        // Set new phase after time ends
        phasePacket.setPlayerOneMove(playerOneMove);
        phasePacket.setPlayerTwoMove(playerTwoMove);

        timer.getTimerUI().dispose();
        gpm.setPhase(new RoundTextMovesPhase(phasePacket, gpm));
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
        timer.getTimerUI().draw(sb);
        playerOne.getCurrentCharacter().getSprite().draw(sb);
        playerTwo.getCurrentCharacter().getSprite().draw(sb);
        sb.end();
    }

    /**
     * Frees up memory by disposing sprites from SpriteBatch.
     */
    @Override
    public void dispose() {
        // Remove timer as no need for timer after this.
        timer.getTimerUI().dispose();
    }
}
