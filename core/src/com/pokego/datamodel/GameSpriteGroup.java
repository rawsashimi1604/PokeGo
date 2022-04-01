package com.pokego.datamodel;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.pokego.PokeGo;

/**
 * Classes that inherit from GameSpriteGroup uses LibGDX's SpriteBatch to draw them.
 */
public abstract class GameSpriteGroup implements IMovable {
    private float scale = 1f;
    private Vector2 position;

    /**
     * Creates GameShapeGroup object at position (x,y).
     * @param x The x position of the GameShapeGroup.
     * @param y The y position of the GameShapeGroup.
     */
    public GameSpriteGroup(int x, int y) {
        position = new Vector2(x, y);
    }

    /**
     * Repositions the object from any point to the point (x, y).
     * @param x The distance of the point along the x-axis
     * @param y The distance of the point along the y-axis
     */
    @Override
    public void reposition(float x, float y) {
        position.x = Math.max(0, x);
        position.y = Math.max(0, y);
    }

    /**
     * Moves the object to the right by a specified amount.
     * @param amount The amount to move the point along the x-axis by
     */
    @Override
    public void moveRight(int amount) {
        position.x = Math.min(PokeGo.WIDTH, position.x + amount);
    }

    /**
     * Moves the object to the left by a specified amount.
     * @param amount The amount to move the point along the x-axis by
     */
    @Override
    public void moveLeft(int amount) {
        position.x = Math.max(0, position.x - amount);
    }

    /**
     * Moves the object up by a specified amount.
     * @param amount The amount to move the point along the y-axis by
     */
    @Override
    public void moveUp(int amount) {
        position.y = Math.min(PokeGo.HEIGHT, position.y + amount);
    }

    /**
     * Moves the object down by a specified amount.
     * @param amount The amount to move the point along the y-axis by
     */
    @Override
    public void moveDown(int amount) {
        position.y = Math.max(0, position.y - amount);
    }

    /**
     * Gets the x position of the GameSpriteGroup
     * @return Returns the x position of the GameSpriteGroup
     */
    public float getX() {
        return position.x;
    }

    /**
     * Gets the y position of the GameSpriteGroup
     * @return Returns the y position of the GameSpriteGroup
     */
    public float getY() {
        return position.y;
    }

    /**
     * Gets the scale of the GameSpriteGroup
     * @return Returns the scale of the GameSpriteGroup
     */
    public float getScale() {
        return scale;
    }

    abstract public void draw(SpriteBatch sb);
    abstract public void dispose();
    abstract public void scale(float scale);
    abstract public void scale(float scaleX, float scaleY);
}
