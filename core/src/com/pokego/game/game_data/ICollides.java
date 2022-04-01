package com.pokego.game.game_data;

import com.pokego.datamodel.GameAnimation;
import com.pokego.game.game_ui.PlayerNumber;

/**
 * This interface defines a set of methods for objects that can be collided against animations.
 */
public interface ICollides {

    int COLLISION_BUFFER = 30;

    /**
     * Returns true if current object collides with GameAnimation. False otherwise
     * @param animation The GameAnimation to check collision with.
     * @param playerCasted Player which casted the animation.
     * @return
     */
    boolean collidesWith(GameAnimation animation, PlayerNumber playerCasted);

}
