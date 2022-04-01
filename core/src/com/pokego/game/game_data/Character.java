package com.pokego.game.game_data;

import com.pokego.datamodel.GameAnimation;
import com.pokego.datamodel.GameSprite;
import com.pokego.game.game_ui.PlayerNumber;

import java.util.ArrayList;

/**
 * The Character is a creature in the game. It starts with some health points. When the HP reaches 0, it wil faint and be unusable thoroughout the rest of the game. It also comes with a specified type and 4 moves (3 attack, 1 defense). The image files of Characters can be found in the "./assets/characters" folder.
 */
public class Character implements ICollides {
    public final static int CHARACTER_STARTING_HP = 100;

    private String name;
    private GameSprite sprite;
    private Type characterType;
    private int healthPoints;
    private ArrayList<Move> moves;

    /**
     * Constructs a Character.
     * @param name Name of Character.
     * @param characterType Type of Character, specified by Type enum.
     * @param spriteLocation Image file location of sprite. Found in the "./assets/characters" folder.
     * @param fireMove The fire Type move of the Character.
     * @param waterMove The water Type move of the Character.
     * @param grassMove The grass Type move of the Character.
     * @param defenseMove The DefenseMove of the Character.
     */
    public Character(String name, Type characterType, String spriteLocation, Move fireMove, Move waterMove, Move grassMove, Move defenseMove) {
        this.name = name;
        this.characterType = characterType;

        sprite = new GameSprite(spriteLocation);

        moves = new ArrayList<Move>();
        moves.add(fireMove);
        moves.add(waterMove);
        moves.add(grassMove);
        moves.add(defenseMove);

        healthPoints = CHARACTER_STARTING_HP;
    }

    /**
     * Returns true if Character is still alive, False otherwise.
     * @return Returns true if Character is still alive, False otherwise.
     */
    public boolean isAlive() {
        return healthPoints > 0;
    }

    /**
     * Gets the name of the Character.
     * @return Returns the name of the Character.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the GameSprite of the Character.
     * @return Returns the GameSprite of the Character.
     */
    public GameSprite getSprite() {
        return sprite;
    }

    /**
     * Gets the Type of the Character.
     * @return Returns the Type of the Character.
     */
    public Type getCharacterType() {
        return characterType;
    }

    /**
     * Gets the current health points of the Character.
     * @return Returns the current health points of the Character.
     */
    public int getHealthPoints() {
        return Math.max(0, healthPoints);
    }

    /**
     * Gets the fire Type move of the Character.
     * @return Returns the fire Type move of the Character.
     */
    public Move getFireMove() {
        return moves.get(0);
    }

    /**
     * Gets the water Type move of the Character.
     * @return Returns the water Type move of the Character.
     */
    public Move getWaterMove() {
        return moves.get(1);
    }

    /**
     * Gets the grass Type move of the Character.
     * @return Returns the grass Type move of the Character.
     */
    public Move getGrassMove() {
        return moves.get(2);
    }

    /**
     * Gets the DefenseMove of the Character.
     * @return Returns the DefenseMove of the Character.
     */
    public Move getDefenseMove() {
        return moves.get(3);
    }

    /**
     * Decrement the health points of the Character by a specified amount
     * @param amount amount of health points to decrement.
     */
    public void decreaseHealthPoints(int amount) {
        healthPoints -= amount;
        if(healthPoints < 0) {
            healthPoints = 0;
        }
    }

    /**
     * Returns true if character collides with animation. False otherwise.
     * @param animation The GameAnimation to check collision with.
     * @param playerCasted Player which casted the animation.
     * @return Returns true if character collides with animation. False otherwise.
     */
    @Override
    public boolean collidesWith(GameAnimation animation, PlayerNumber playerCasted) {
        /*
            Collision with animation (attack move), playerCasted = specify which player's move is colliding on current character.
         */

        // player one being collided
        if (playerCasted == PlayerNumber.PLAYER_TWO) {
            return animation.getX() <= getSprite().getX() + getSprite().getWidth() - COLLISION_BUFFER;
        }

        else if (playerCasted == PlayerNumber.PLAYER_ONE) {
            return animation.getX() + animation.getWidth() >= getSprite().getX() + COLLISION_BUFFER;
        }

        return false;
    }
}
