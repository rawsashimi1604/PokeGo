package com.pokego.game.game_data;

import com.pokego.datamodel.GameAnimation;
import com.pokego.game.game_ui.PlayerNumber;

/**
 * AttackMoves are Moves which block damage. Their base damage is set to 0.
 */
public class DefenseMove extends Move {
    private final static MoveClass MOVE_CLASS = MoveClass.DEFENSE;

    private int damageBlocked;
    private PlayerNumber castTowards;

    /**
     * Constructs an DefenseMove.
     * @param name Name of DefenseMove.
     * @param moveType Type of DefenseMove, defined by the Type enum.
     * @param animation GameAnimation of DefenseMove.
     * @param damageBlocked Damage blocked of DefenseMove.
     */
    public DefenseMove(String name, Type moveType, GameAnimation animation, int damageBlocked) {
        super(name, moveType, animation);
        this.damageBlocked = damageBlocked;
        castTowards = null;

    }

    /**
     * Gets the base damage.
     * @return Returns base damage of move.
     */
    @Override
    public int getBaseDamage() {
        return 0;
    }

    /**
     * Gets the damage blocked.
     * @return Returns damage blocked.
     */
    @Override
    public int getDamageBlocked() {
        return damageBlocked;
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
    public void animate(float dt) {
        if (animating) {
            animation.update(dt);
        }
    }

    /**
     * Get the move class, ie. Attack or Defense.
     * @return Returns the move class, ie. Attack or Defense.
     */
    @Override
    public MoveClass getMoveClass() {
        return MOVE_CLASS;
    }
}
