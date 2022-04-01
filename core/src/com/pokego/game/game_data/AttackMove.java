package com.pokego.game.game_data;

import com.pokego.datamodel.GameAnimation;
import com.pokego.game.game_ui.PlayerNumber;

/**
 * AttackMoves are Moves which deal damage. Their damage blocked is set to 0.
 */
public class AttackMove extends Move {
    private final static MoveClass moveClass = MoveClass.ATTACK;
    private int baseDamage;
    private PlayerNumber castTowards;

    /**
     * Constructs an AttackMove.
     * @param name Name of AttackMove.
     * @param moveType Type of AttackMove, defined by the Type enum.
     * @param animation GameAnimation of AttackMove.
     * @param baseDamage Base damage of AttackMove.
     */
    public AttackMove(String name, Type moveType, GameAnimation animation, int baseDamage) {
        super(name, moveType, animation);
        this.baseDamage = baseDamage;
        castTowards = null;
    }

    /**
     * Gets the damage blocked.
     * @return Returns damage blocked.
     */
    @Override
    public int getDamageBlocked() {
        return 0;
    }

    /**
     * Gets the base damage.
     * @return Returns base damage of move.
     */
    @Override
    public int getBaseDamage() {
        return baseDamage;
    }

    /**
     * Gets the player that the move is casted towards.
     * @return Returns the player that the move is casted towards.
     */
    @Override
    public PlayerNumber getCastTowards() {
        return castTowards;
    }

    /**
     * Set the move to cast towards the specified player.
     * @param castTowards The specified player to cast the move towards.
     */
    @Override
    public void setCastTowards(PlayerNumber castTowards) {
        this.castTowards = castTowards;
    }

    /**
     * Cast the move and start the animation. If move is casted towards player 1, flip the move.
     * @param castTowards
     */
    @Override
    public void castMove(PlayerNumber castTowards) {
        this.castTowards = castTowards;
        // run once
        System.out.printf("Player has casted %s.\n", getName());
        animating = true;
        if (this.castTowards == PlayerNumber.PLAYER_ONE) {
            System.out.println("flipped");
            animation.flip();
        }
    }

    /**
     * Animate the move according to delta time.
     * @param dt delta time
     */
    @Override
    public void animate(float dt){
        if (animating) {
            animation.update(dt);

            // move animation
            // If player one casts, move animation right, else move left
            if (castTowards == PlayerNumber.PLAYER_TWO) {
                animation.moveRight(5);
            } else if (castTowards == PlayerNumber.PLAYER_ONE){
                animation.moveLeft(5);
            }
        }
    }

    /**
     * Returns whether the move is animating, if animating True, False otherwise.
     * @return Returns whether the move is animating, if animating True, False otherwise.
     */
    @Override
    public boolean isAnimating() {
        return animating;
    }

    /**
     * Stop the animation of the move.
     */
    @Override
    public void stopAnimating() {
        animating = false;
    }

    /**
     * Get the move class, ie. Attack or Defense.
     * @return Returns the move class, ie. Attack or Defense.
     */
    @Override
    public MoveClass getMoveClass() {
        return moveClass;
    }
}
