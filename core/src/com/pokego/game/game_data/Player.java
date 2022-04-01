package com.pokego.game.game_data;

import java.util.ArrayList;

/**
 * The Player represents the players that are currently playing the game. They can either be PLAYER_ONE or PLAYER_TWO. A player has a number Characters, and loses if all Characters have fainted.
 */
public class Player {
    public final static int DEFAULT_NO_CHARACTERS = 6;
    private String name;

    private ArrayList<Character> characters;
    private Character currentCharacter;
    private int charactersLeft;

    /**
     * Constructs a Player object with the default number of characters.
     * @param name Name of the player.
     * @param characters ArrayList of characters to assign to the Player.
     */
    public Player(String name, ArrayList<Character> characters) {
        this.name = name;
        this.characters = characters;

        charactersLeft = characters.size();
        currentCharacter = characters.get(0);
    }

    /**
     * Returns True if Player is defeated (all Characters have fainted), False otherwise.
     * @return Returns True if Player is defeated (all Characters have fainted), False otherwise.
     */
    public boolean isDefeated() {
        return charactersLeft == 0;
    }

    /**
     * Gets the number of Characters that are still alive.
     * @return Returns the number of Characters that are still alive.
     */
    public int getCharactersLeft() {
        return charactersLeft;
    }

    /**
     * Gets the ArrrayList of Characters that is assigned to the Player.
     * @return Returns the ArrrayList of Characters that is assigned to the Player.
     */
    public ArrayList<Character> getCharacters() {
        return characters;
    }

    /**
     * Gets the name of the Player.
     * @return Returns the name of the Player.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the current Character of the Player.
     * @return Returns the current Character of the Player.
     */
    public Character getCurrentCharacter() {
        return currentCharacter;
    }

    /**
     * Removes Character from ArrayList after it is defeated.
     */
    public void killCharacter() {
        characters.remove(0);
        charactersLeft -= 1;
    }

    /**
     * Sends out next Character in the ArrayList.
     */
    public void nextCharacter() {
        // Remove defeated character from ArrayList, set next character to currentCharacter.
        currentCharacter = characters.get(0);
    }

}
