package com.pokego.datamodel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pokego.utility.Utility;

/**
 * GameRectangle is used to represent a rendered rectangle. Rendered using LibGDX's ShapeRenderer. It contains 2 rectangle drawn on top of each other - the inner rectangle and the outer rectangle. The outer rectangle is used to represent the border of the rectangle.
 */
public class GameRectangle extends GameShapeGroup {
    private float breadth;
    private float length;
    private float borderRadius;

    /**
     * Constructs the GameRectangle object at position (0, 0) using default color, default border color, default border thickness, default borderRadius and specified breadth and length.
     * @param breadth The breadth of the GameRectangle.
     * @param length The length of the GameRectangle.
     */
    public GameRectangle(float breadth, float length) {
        super();
        this.breadth = breadth;
        this.length = length;
    }

    /**
     * Constructs the GameRectangle object at position (0, 0) using default color, default border color, default border thickness and specified breadth, length and border radius.
     * @param breadth The breadth of the GameRectangle.
     * @param length The length of the GameRectangle.
     * @param borderRadius The border radius of the GameRectangle.
     */
    public GameRectangle(float breadth, float length, float borderRadius) {
        this(breadth, length, GameShapeGroup.DEFAULT_COLOR, GameShapeGroup.DEFAULT_BORDER_COLOR, GameShapeGroup.DEFAULT_BORDER_THICKNESS, borderRadius);
    }

    /**
     * Constructs the GameRectangle object at position (0, 0) using default border thickness and specified breadth, length, border radius, fill color and border color.
     * @param breadth The breadth of the GameRectangle.
     * @param length The length of the GameRectangle.
     * @param color The fill color of the GameRectangle.
     * @param borderColor The border color of the GameRectangle.
     * @param borderRadius The border radius of the GameRectangle.
     */
    public GameRectangle(float breadth, float length, Color color, Color borderColor, float borderRadius) {
        this(breadth, length, color, borderColor, DEFAULT_BORDER_THICKNESS, borderRadius);
    }

    /**
     * Constructs the GameRectangle object at position (0, 0) using specified breadth, length, border radius, border thickness, fill color and border color.
     * @param breadth The breadth of the GameRectangle.
     * @param length The length of the GameRectangle.
     * @param color The fill color of the GameRectangle.
     * @param borderColor The border color of the GameRectangle.
     * @param borderThickness The border thickness of the GameRectangle.
     * @param borderRadius The border radius of the GameRectangle.
     */
    public GameRectangle(float breadth, float length, Color color, Color borderColor, int borderThickness, float borderRadius) {
        super(0, 0, color, borderColor, borderThickness);
        this.breadth = breadth;
        this.length = length;
        this.borderRadius = borderRadius;
    }

    /**
     * Draw currently rendered GameRectangle using LibGDX's engine.
     * @param sr ShapeRenderer
     */
    @Override
    public void draw(ShapeRenderer sr) {
        Utility.enableShapeRendererBlend();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        drawRoundedRectangle(sr); // draw outer rectangle

        sr.setColor(Color.WHITE);
        // draw inner rectangle
        GameRectangle innerRect = new GameRectangle(breadth - borderThickness, length - borderThickness, color, color, 0, borderRadius);
        innerRect.setColor(Color.WHITE);
        innerRect.reposition(getX() + borderThickness/2, getY() + borderThickness/2);
        innerRect.drawRoundedRectangle(sr);
        sr.end();
        Utility.disableShapeRendererBlend();
    }

    /**
     * Draws a rounded rectangle using LibGDX's engine.
     * @param sr ShapeRenderer
     */
    private void drawRoundedRectangle(ShapeRenderer sr) {
        sr.setColor(borderColor);

        // main rectangle
        sr.rect(getX() + borderRadius, getY() + borderRadius, getLength() - 2*borderRadius, getBreadth() - 2*borderRadius);

        // side rectangles, clockwise
        sr.rect(getX() + borderRadius, getY(), getLength() - 2*borderRadius, borderRadius);
        sr.rect(getX() + getLength() - borderRadius, getY() + borderRadius, borderRadius, getBreadth() - 2*borderRadius);
        sr.rect(getX() + borderRadius, getY() + getBreadth() - borderRadius, getLength() - 2*borderRadius, borderRadius);
        sr.rect(getX(), getY() + borderRadius, borderRadius, getBreadth() - 2*borderRadius);

        // arches, clockwise
        sr.arc(getX() + borderRadius, getY() + borderRadius, borderRadius, 180f, 90f);
        sr.arc(getX() + getLength() - borderRadius, getY() + borderRadius, borderRadius, 270f, 90f);
        sr.arc(getX() + getLength() - borderRadius, getY() + getBreadth() - borderRadius, borderRadius, 0f, 90f);
        sr.arc(getX() + borderRadius, getY() + getBreadth() - borderRadius, borderRadius, 90f, 90f);
    }

    /**
     * Gets the breadth of the GameRectangle
     * @return Returns the breadth of the GameRectangle
     */
    public float getBreadth() {
        return breadth;
    }

    /**
     * Sets the breadth of the GameRectangle.
     * @param breadth Breadth of GameRectangle.
     */
    public void setBreadth(float breadth) {
        this.breadth = breadth;
    }

    /**
     * Gets the length of the GameRectangle.
     * @return Returns the length of the GameRectangle.
     */
    public float getLength() {
        return length;
    }

    /**
     * Sets the length of the GameRectangle.
     * @param length Length of GameRectangle.
     */
    public void setLength(float length) {
        this.length = length;
    }

    /**
     * Sets the border radius of the GameRectangle.
     * @param borderRadius Border radius of GameRectangle.
     */
    public void setBorderRadius(float borderRadius) {
        this.borderRadius = borderRadius;
    }
}
