package com.pokego.game.game_data;

import com.pokego.datamodel.GameAnimation;
import com.pokego.game.game_ui.PlayerNumber;

/**
 * Defines the base class for a Move. Each character comes with a set of 4 moves, and they can either be AttackMove or DefenseMove. The move has a specific move type as well, and are made up of GameAnimations.
 */
public abstract class Move {
    protected String name;
    protected Type moveType;
    protected GameAnimation animation;

    protected boolean animating;

    /**
     * Constructs a Move, using a name, specified type, and GameAnimation.
     * @param name Name of the move.
     * @param moveType Type of the move, specified by the Type enum.
     * @param animation GameAnimation of the move.
     */
    public Move(String name, Type moveType, GameAnimation animation) {
        this.name = name;
        this.moveType = moveType;
        this.animation = animation;

        animating = false;
    }

    /**
     * Gets the name of the move.
     * @return Returns the name of the move.
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the type of the move.
     * @return Returns the type of the move.
     */
    public Type getMoveType(){
        return moveType;
    }

    /**
     * Gets the GameAnimation of the move.
     * @return Returns the GameAnimation of the move.
     */
    public GameAnimation getAnimation(){
        return animation;
    }

    abstract public void castMove(PlayerNumber castTowards);
    abstract public void animate(float dt);
    abstract public boolean isAnimating();
    abstract public void stopAnimating();
    abstract public MoveClass getMoveClass();
    abstract public int getBaseDamage();
    abstract public int getDamageBlocked();
    abstract public PlayerNumber getCastTowards();
    abstract public void setCastTowards(PlayerNumber castTowards);
}
