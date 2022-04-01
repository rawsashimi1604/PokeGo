package com.pokego.datamodel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.pokego.PokeGo;

/**
 * Classes that inherit from GameShapeGroup uses LibGDX's ShapeRenderer to draw them. It also specifies default colors, border colors, and border thickness of the different shapes to be rendered.
 */
public abstract class GameShapeGroup implements IMovable {
    public final static Color DEFAULT_COLOR = Color.WHITE;
    public final static Color DEFAULT_BORDER_COLOR = Color.BLACK;
    public final static int DEFAULT_BORDER_THICKNESS = 10;

    abstract public void draw(ShapeRenderer sr);

    protected Color color;
    protected Color borderColor;
    protected int borderThickness;
    private Vector2 position;

    /**
     * Creates GameShapeGroup object at position (0,0) with default color, border color and border thickness.
     */
    public GameShapeGroup() {
        this(0, 0, DEFAULT_COLOR, DEFAULT_BORDER_COLOR, DEFAULT_BORDER_THICKNESS);
    }

    /**
     * Creates GameShapeGroup object at position (x,y) with specified color, border color and border thickness.
     * @param x The x position of the GameShapeGroup.
     * @param y The y position of the GameShapeGroup.
     * @param color The color of the object.
     * @param borderColor The border color of the object.
     * @param borderThickness The border thickness of the object.
     */
    public GameShapeGroup(int x, int y, Color color, Color borderColor, int borderThickness) {
        position = new Vector2(x, y);
        this.color = color;
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
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
     * Gets the x position of the GameShapeGroup
     * @return Returns the x position of the GameShapeGroup
     */
    public float getX() {
        return position.x;
    }

    /**
     * Gets the y position of the GameShapeGroup
     * @return Returns the y position of the GameShapeGroup
     */
    public float getY() {
        return position.y;
    }

    /**
     * Gets the color of the GameShapeGroup
     * @return Returns the color of the GameShapeGroup
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the GameShapeGroup
     * @param color The color of the GameShapeGroup
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Gets the border color of the GameShapeGroup
     * @return Returns the border color of the GameShapeGroup
     */
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * Sets the border color of the GameShapeGroup
     * @param borderColor The border of the GameShapeGroup
     */
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    /**
     * Gets the border thickness of the GameShapeGroup
     * @return Returns the border thickness of the GameShapeGroup
     */
    public int getBorderThickness() {
        return borderThickness;
    }

    /**
     * Sets the border thickness of the GameShapeGroup
     * @param borderThickness The border thickness of the GameShapeGroup
     */
    public void setBorderThickness(int borderThickness) {
        this.borderThickness = borderThickness;
    }
}
