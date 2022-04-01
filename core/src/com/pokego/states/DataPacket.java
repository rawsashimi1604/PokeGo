package com.pokego.states;

import com.pokego.game.game_data.Character;
import com.pokego.game.game_phase.PlayerWinner;
import com.pokego.game.game_ui.PlayerNumber;

import java.util.ArrayList;

/**
 * The DataPacket is used to move data across different States. It contains information such as the Player's names, list of Characters, and the winner of the game.
 */
public class DataPacket {
    private String playerOneName;
    private String playerTwoName;
    private ArrayList<Character> playerOneChars;
    private ArrayList<Character> playerTwoChars;
    private PlayerWinner winner;


    /**
     * Constructs a DataPacket object.
     * @param playerOneName Player 1's name.
     * @param playerTwoName Player 2's name.
     */
    public DataPacket(String playerOneName, String playerTwoName) {
        this.playerOneName = playerOneName;
        this.playerTwoName = playerTwoName;
    }

    /**
     * Set the winner of the game. Possible outcomes are defined in the PlayerWinner enum.
     * @param winner Winner of the game. Possible outcomes are defined in the PlayerWinner enum.
     */
    public void setWinner(PlayerWinner winner) {
        this.winner = winner;
    }

    /**
     * Sets the player name.
     * @param playerNumber Player name.
     * @param playerName Player number.
     */
    public void setPlayerName(PlayerNumber playerNumber, String playerName) {
        if (playerNumber == PlayerNumber.PLAYER_ONE) {
            playerOneName = playerName;
        } else if (playerNumber == PlayerNumber.PLAYER_TWO) {
            playerTwoName = playerName;
        }
    }

    /**
     * Sets the generated list of Characters for the respective PlayerNumber.
     * @param playerNumber Player number.
     * @param characters List of Characters generated.
     */
    public void setPlayerChars(PlayerNumber playerNumber, ArrayList<Character> characters) {
        if (playerNumber == PlayerNumber.PLAYER_ONE) {
            playerOneChars = characters;
        } else {
            playerTwoChars = characters;
        }
    }

    /**
     * Gets player 1's name.
     * @return Returns player 1's name.
     */
    public String getPlayerOneName() { return playerOneName; }

    /**
     * Gets player 2's name.
     * @return Returns player 2's name.
     */
    public String getPlayerTwoName() { return playerTwoName; }

    /**
     * Gets winner of the game. Possible outcomes are defined in the PlayerWinner enum.
     * @return Returns the winner of the game. Possible outcomes are defined in the PlayerWinner enum.
     */
    public PlayerWinner getWinner() {
        return winner;
    }

    /**
     * Gets list of player 1's characters.
     * @return Returns list of player 1's characters.
     */
    public ArrayList<Character> getPlayerOneChars() {
        return playerOneChars;
    }

    /**
     * Gets list of player 2's characters.
     * @return Returns list of player 2's characters.
     */
    public ArrayList<Character> getPlayerTwoChars() {
        return playerTwoChars;
    }
}

