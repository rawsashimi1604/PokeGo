package com.pokego.datamodel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.pokego.PokeGo;

/**
 * GameTextWidget represents a text box. It is made up of a GameRectangle and GameText. It also contains a padding element, allowing to pad the GameText in the GameRectangle. The text can also be set to scroll according to a scrolling duration.
 */
public class GameTextWidget implements IMovable {
    public static final float DEFAULT_PADDING = 20f;
    public static final float DEFAULT_SCROLLING_DURATION = 4.5f;

    private Vector2 position;

    // If scrolling, set GameText text to increase every (scrolling speed) scaled to delta time.
    private float timeElapsed;
    private float scrollDuration;

    private String text; // Text to be displayed
    private GameText currText; // Game Text object


    private GameRectangle rectangle;
    private float padding;

    private float breadth;
    private float length;

    private boolean scroll;

    /**
     * Constructs a GameTextWidget at position (0,0) with a default padding and no scroll.
     * @param breadth The breadth of the GameTextWidget.
     * @param length The length of the GameTextWidget.
     */
    public GameTextWidget(float breadth, float length) {
        this(0, 0, DEFAULT_PADDING, breadth, length, false, DEFAULT_SCROLLING_DURATION);
    }

    /**
     * Constructs a GameTextWidget at position (0,0) with a default padding and specified scroll. Scrolling duration will be default.
     * @param breadth The breadth of the GameTextWidget.
     * @param length The length of the GameTextWidget.
     * @param scroll True if scroll, False otherwise.
     */
    public GameTextWidget(float breadth, float length, boolean scroll) {
        this(0, 0, DEFAULT_PADDING, breadth, length, scroll, DEFAULT_SCROLLING_DURATION);
    }

    /**
     * Constructs a GameTextWidget at position (0,0) with a default padding, specified scroll and specified scrolling duration.
     * @param breadth The breadth of the GameTextWidget.
     * @param length The length of the GameTextWidget.
     * @param scroll True if scroll, False otherwise.
     * @param scrollDuration Scrolling duration of the GameTextWidget.
     */
    public GameTextWidget(float breadth, float length, boolean scroll, float scrollDuration) {
        this(0, 0, DEFAULT_PADDING, breadth, length, scroll, scrollDuration);
    }

    /**
     * Constructs a GameTextWidget at position (x,y) with a specified padding, specified scroll and specified scrolling duration.
     * @param x The x position of the GameTextWidget.
     * @param y The y position of the GameTextWidget.
     * @param padding Padding of GameTextWidget.
     * @param breadth The breadth of the GameTextWidget.
     * @param length The length of the GameTextWidget.
     * @param scroll True if scroll, False otherwise.
     * @param scrollDuration Scrolling duration of the GameTextWidget.
     */
    public GameTextWidget(float x, float y, float padding, float breadth, float length, boolean scroll, float scrollDuration) {
        position = new Vector2(x, y);
        this.breadth = breadth;
        this.length = length;
        this.padding = padding;
        this.scroll = scroll;
        this.scrollDuration = scrollDuration;

        timeElapsed = 0f;

        rectangle = new GameRectangle(breadth, length, 10f);
        rectangle.reposition(x, y);

        currText = new GameText();
        currText.reposition(getX() + padding, (getY() + breadth - padding));
    }

    /**
     * Gets the x position of the GameTextWidget
     * @return Returns the x position of the GameTextWidget
     */
    public float getX() {
        return position.x;
    }

    /**
     * Gets the y position of the GameTextWidget
     * @return Returns the y position of the GameTextWidget
     */
    public float getY() {
        return position.y;
    }

    /**
     * Gets the width of the GameTextWidget.
     * @return Returns the width of the GameTextWidget.
     */
    public float getWidth() {
        return rectangle.getLength();
    }

    /**
     * Gets the height of the GameTextWidget.
     * @return Returns the height of the GameTextWidget.
     */
    public float getHeight() {
        return rectangle.getBreadth();
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
        rectangle.reposition(getX(), getY());
        currText.reposition(getX() + padding, (getY() + breadth - padding));
    }

    /**
     * Moves the object to the right by a specified amount.
     * @param amount The amount to move the point along the x-axis by
     */
    @Override
    public void moveRight(int amount) {
        position.x = Math.min(PokeGo.WIDTH, position.x + amount);
        rectangle.reposition(getX(), getY());
        currText.reposition(getX() + padding, (getY() + breadth - padding));
    }

    /**
     * Moves the object to the left by a specified amount.
     * @param amount The amount to move the point along the x-axis by
     */
    @Override
    public void moveLeft(int amount) {
        position.x = Math.max(0, position.x - amount);
        rectangle.reposition(getX(), getY());
        currText.reposition(getX() + padding, (getY() + breadth - padding));
    }

    /**
     * Moves the object up by a specified amount.
     * @param amount The amount to move the point along the y-axis by
     */
    @Override
    public void moveUp(int amount) {
        position.y = Math.min(PokeGo.HEIGHT, position.y + amount);
        rectangle.reposition(getX(), getY());
        currText.reposition(getX() + padding, (getY() + breadth - padding));
    }

    /**
     * Moves the object down by a specified amount.
     * @param amount The amount to move the point along the y-axis by
     */
    @Override
    public void moveDown(int amount) {
        position.y = Math.max(0, position.y - amount);
        rectangle.reposition(getX(), getY());
        currText.reposition(getX() + padding, (getY() + breadth - padding));
    }

    /**
     * Frees up memory by disposing sprites from SpriteBatch.
     */
    public void dispose() {
        currText.dispose();
    }

    /**
     * Gets the scroll duration of the GameTextWidget.
     * @return Returns the scroll duration of the GameTextWidget.
     */
    public float getScrollDuration() {
        return scrollDuration;
    }

    /**
     * Sets the scroll duration of the GameTextWidget.
     * @param scrollDuration The scroll duration of the GameTextWidget.
     */
    public void setScrollDuration(float scrollDuration) {
        this.scrollDuration = scrollDuration;
    }

    /**
     * Sets the border color of the GameTextWidget.
     * @param color The border color of the GameTextWidget.
     */
    public void setBorderColor(Color color) {
        rectangle.setBorderColor(color);
    }

    /**
     * Sets the border radius of the GameTextWidget.
     * @param borderRadius The border radius of the GameTextWidget.
     */
    public void setBorderRadius(float borderRadius) {
        rectangle.setBorderRadius(borderRadius);
    }

    /**
     * Sets the border thickness of the GameTextWidget.
     * @param borderThickness The border thickness of the GameTextWidget.
     */
    public void setBorderThickness(int borderThickness) {
        rectangle.setBorderThickness(borderThickness);
    }

    /**
     * Sets the rectangle fill color of the GameTextWidget.
     * @param color The rectangle fill color of the GameTextWidget.
     */
    public void setColor(Color color) {
        rectangle.setColor(color);
    }

    /**
     * Sets the text inside the GameTextWidget
     * @param text The text of GameTextWidget
     */
    public void setText(String text) {
        timeElapsed = 0f;
        if (scroll) {
            this.text = text;
        } else {
            currText.setText(text);
            currText.reposition(getX() + padding, (getY() + breadth - padding));
        }
    }

    /**
     * Helper function to set current display text of GameTextWidget according to delta time.
     */
    private void setDisplayText(String text) {
        currText.setText(getDisplayText());
        currText.reposition(getX() + padding, (getY() + breadth - padding));
    }

    /**
     * Helper function to get current display text of GameTextWidget according to delta time.
     */
    private String getDisplayText() {
        // curr output text index = (elapsedTime / duration) * strLen
        float progressPrc = timeElapsed / scrollDuration;
        int textIndexToDisplay = (int)(progressPrc * text.length());
        if (textIndexToDisplay >= text.length() - 1) {
            textIndexToDisplay = text.length() - 1;
        }

        return text.substring(0, textIndexToDisplay + 1);
    }

    /**
     * Helper function to increment time elapsed for scrolling GameTextWidget.
     * @param dt delta time.
     */
    private void incrementTimeElapsed(float dt) {
        timeElapsed += dt;
        timeElapsed = Math.min(timeElapsed, scrollDuration);
    }

    /**
     * Set size of text.
     * @param scale Size of text.
     */
    public void setTextScale(float scale) {
        currText.scale(scale);
    }

    /**
     * Updates text scroll according to delta time.
     * @param dt delta time.
     */
    public void updateTextScroll(float dt) {
        incrementTimeElapsed(dt);
        String textOut = getDisplayText();
        setDisplayText(textOut);
    }

    /**
     * Returns true if scroll has finished.
     * @return True if scroll has finished, False otherwise.
     */
    public boolean displayedFinish() {
        return getDisplayText().equals(text);
    }

    /**
     * Draw text widget using LibGDX's engine. Displays text widget.
     * @param sb SpriteBatch
     * @param sr ShapeRenderer
     */
    public void draw(SpriteBatch sb, ShapeRenderer sr) {
        rectangle.draw(sr);

        sb.begin();
        currText.draw(sb, rectangle.getLength() - padding - padding, Align.left, true);
        sb.end();
    }

    /**
     * Gets GameText object.
     * @return Returns GameText object.
     */
    public GameText getTextObject() {
        return currText;
    }

    /**
     * Gets GameRectangle object.
     * @return Returns GameRectangle object.
     */
    public GameRectangle getRectangle() {
        return rectangle;
    }
}
