package com.pokego.datamodel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pokego.utility.Utility;

/**
 * GameCircle is used to represent a rendered circle. Rendered using LibGDX's ShapeRenderer. It contains 2 circles drawn on top of each other - the inner circle and the outer circle. The outer circle is used to represent the border of the circle.
 */
public class GameCircle extends GameShapeGroup {
    private float radius;

    /**
     * Constructs the GameCircle object at position (x, y) using color, border color, border thickness and radius.
     * @param x The x position of the GameCircle.
     * @param y The y position of the GameCircle.
     * @param color The fill color of the GameCircle, uses LibGDX's Color object
     * @param borderColor The border color of the GameCircle, uses LibGDX's Color object
     * @param borderThickness The border thickness of the GameCircle.
     * @param radius The radius of the GameCircle
     */
    public GameCircle(int x, int y, Color color, Color borderColor, int borderThickness, float radius) {
        super(x, y, color, borderColor, borderThickness);
        this.radius = radius;
    }

    /**
     * Constructs the GameCheckBox object at position (0, 0) using radius.
     * @param radius The radius of the GameCircle
     */
    public GameCircle(float radius) {
        super();
        this.radius = radius;
    }

    /**
     * Draw currently rendered GameCircle using LibGDX's engine.
     * @param sr ShapeRenderer
     */
    @Override
    public void draw(ShapeRenderer sr) {
        Utility.enableShapeRendererBlend();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        drawBorder(sr);
        sr.setColor(color);
        sr.circle(getX(), getY(), radius - (borderThickness / 2));
        sr.end();
        Utility.disableShapeRendererBlend();
    }

    /**
     * Draws border circle using LibGDX's engine.
     * @param sr
     */
    private void drawBorder(ShapeRenderer sr) {
        sr.setColor(borderColor);
        sr.circle(getX(), getY(), radius);
    }

    /**
     * Gets radius of GameCircle
     * @return radius of GameCircle
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Sets radius of GameCircle
     * @param radius radius of GameCircle
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }
}
