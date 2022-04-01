package com.pokego.game.game_phase;

import com.pokego.datamodel.GameTextWidget;
import com.pokego.game.game_data.Move;
import com.pokego.game.game_data.Player;
import com.pokego.game.game_ui.PlayerNumber;
import com.pokego.game.game_ui.PlayerUI;

/**
 * The PhasePacket is used to pass data around different game phases. It can store the Player info, PlayerUI, GameTextWidgets, and which move is being used for the turn.
 */
public class PhasePacket {

    private Player playerOne;
    private Player playerTwo;
    private PlayerUI playerOneUI;
    private PlayerUI playerTwoUI;
    private GameTextWidget textWidget;
    private Move playerOneMove;
    private Move playerTwoMove;

    private boolean playerOneWins;
    private boolean playerTwoWins;

    /**
     * Constructs the PhasePacket object.
     * @param playerOne Player 1 Player object.
     * @param playerTwo Player 2 Player object.
     * @param playerOneUI Player 1 PlayerUI object.
     * @param playerTwoUI Player 2 PlayerUI object.
     * @param textWidget GameTextWidget used in GameState.
     */
    public PhasePacket(Player playerOne, Player playerTwo, PlayerUI playerOneUI, PlayerUI playerTwoUI, GameTextWidget textWidget) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.playerOneUI = playerOneUI;
        this.playerTwoUI = playerTwoUI;
        this.textWidget = textWidget;

        playerOneWins = false;
        playerTwoWins = false;
    }

    /**
     * Gets the Player object represented by Player 1.
     * @return Returns the Player object represented by Player 1.
     */
    public Player getPlayerOne() {
        return playerOne;
    }

    /**
     * Gets the Player object represented by Player 2.
     * @return Returns the Player object represented by Player 2.
     */
    public Player getPlayerTwo() {
        return playerTwo;
    }

    /**
     * Gets the PlayerUI object represented by Player 1.
     * @return Returns the PlayerUI object represented by Player 1.
     */
    public PlayerUI getPlayerOneUI() {
        return playerOneUI;
    }

    /**
     * Gets the PlayerUI object represented by Player 2.
     * @return Returns the PlayerUI object represented by Player 2.
     */
    public PlayerUI getPlayerTwoUI() {
        return playerTwoUI;
    }

    /**
     * Gets the GameTextWidget used in GameState.
     * @return Returns the GameTextWidget used in GameState.
     */
    public GameTextWidget getTextWidget() {
        return textWidget;
    }

    /**
     * Gets player 1's currently selected move.
     * @return Returns player 1's currently selected move.
     */
    public Move getPlayerOneMove() {
        return playerOneMove;
    }

    /**
     * Gets player 2's currently selected move.
     * @return Returns player 2's currently selected move.
     */
    public Move getPlayerTwoMove() {
        return playerTwoMove;
    }

    /**
     * Sets player 1's move.
     * @param playerOneMove Move used by player 1.
     */
    public void setPlayerOneMove(Move playerOneMove) {
        this.playerOneMove = playerOneMove;
    }

    /**
     * Sets player 2's move.
     * @param playerTwoMove Move used by player 2.
     */
    public void setPlayerTwoMove(Move playerTwoMove) {
        this.playerTwoMove = playerTwoMove;
    }

    /**
     * Set player 1's Player object.
     * @param playerOne player 1 Player object.
     */
    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    /**
     * Set player 2's Player object.
     * @param playerTwo player 2 Player object.
     */
    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    /**
     * Store the Player that won the game. Possible values defined by PlayerWinner enum.
     * PLAYER_ONE, PLAYER_TWO, DRAW
     * @param player Player that won the game. Possible values defined by PlayerWinner enum.
     */
    public void setWinner(PlayerNumber player) {
        if (player == PlayerNumber.PLAYER_ONE) {
            playerOneWins = true;
        } else if (player == PlayerNumber.PLAYER_TWO) {
            playerTwoWins = true;
        }
    }

    /**
     * Get the winner of game. Possible values defined by PlayerWinner enum.
     * PLAYER_ONE, PLAYER_TWO, DRAW
     * @return Returns the winner of game. Possible values defined by PlayerWinner enum.
     */
    public PlayerWinner getWinner() {
        if (playerOneWins && playerTwoWins) {
            return PlayerWinner.DRAW;
        }
        if (playerOneWins) {
            return PlayerWinner.PLAYER_ONE;
        } else if (playerTwoWins) {
            return PlayerWinner.PLAYER_TWO;
        }
        return null;
    }
}
