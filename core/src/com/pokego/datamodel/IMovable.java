package com.pokego.datamodel;

/**
 * This interface defines a set of methods for any movable object in the game. Typical movable objects are rendered objects in LibGDX.
 */
public interface IMovable {

    /**
     * Repositions the object from any point to the point (x, y).
     * @param x The distance of the point along the x-axis
     * @param y The distance of the point along the y-axis
     */
    void reposition(float x, float y);

    /**
     * Moves the object to the right by a specified amount.
     * @param amount The amount to move the point along the x-axis by
     */
    void moveRight(int amount);

    /**
     * Moves the object to the left by a specified amount.
     * @param amount The amount to move the point along the x-axis by
     */
    void moveLeft(int amount);

    /**
     * Moves the object up by a specified amount.
     * @param amount The amount to move the point along the y-axis by
     */
    void moveUp(int amount);

    /**
     * Moves the object down by a specified amount.
     * @param amount The amount to move the point along the y-axis by
     */
    void moveDown(int amount);

}
