package com.pokego.datamodel;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pokego.states.ButtonClass;

/**
 * GameCheckbox is used to represent a checkbox. It contains a checked and unchecked GameSprite. When the checkbox is clicked, it will invert the current state of the checkbox.
 */
public class GameCheckbox extends GameButton {
    public static final String CHECKBOX_TICK = "./ui/ticked_checkbox.png";
    public static final String CHECKBOX_UNTICK = "./ui/unticked_checkbox.png";
    private boolean ticked;

    private GameSprite untickedSprite;

    /**
     * Constructs the GameCheckBox object at position (0,0) using type of button.
     * @param state type of button, defined by the ButtonClass enum.
     */
    public GameCheckbox(ButtonClass state) {
        super(CHECKBOX_TICK, CHECKBOX_TICK, state);

        untickedSprite = new GameSprite(CHECKBOX_UNTICK);
        ticked = true;
    }

    /**
     * Constructs the GameCheckBox object at position (0,0) using type of button and state status.
     * @param state type of button, defined by the ButtonClass enum.
     * @param status False if not ticked, true if ticked.
     */
    public GameCheckbox(ButtonClass state, boolean status) {
        super(CHECKBOX_TICK, CHECKBOX_TICK, state);

        untickedSprite = new GameSprite(CHECKBOX_UNTICK);
        ticked = status;
    }

    /**
     * Draw currently selected state using LibGDX's engine. Displays checkbox sprite. (hovered or static)
     * @param sb SpriteBatch
     */
    @Override
    public void draw(SpriteBatch sb) {
        if (ticked) {
            super.draw(sb);
        } else {
            untickedSprite.draw(sb);
        }
    }

    /**
     * Scales animation size, x and y scaled proportionately.
     * @param scale amount to scale
     */
    @Override
    public void scale(float scale) {
        super.scale(scale);
        untickedSprite.scale(scale);
    }

    /**
     * Scales animation size, x and y scaled separately.
     * @param scaleX amount to scale (width)
     * @param scaleY amount to scale (height)
     */
    @Override
    public void scale(float scaleX, float scaleY) {
        super.scale(scaleX, scaleY);
        untickedSprite.scale(scaleX, scaleY);
    }

    /**
     * Returns true if button is clicked. False otherwise.
     * @param cursorX The current x position of mouse.
     * @param cursorY The current y position of mouse.
     * @return Returns true if button is clicked. False otherwise.
     */
    @Override
    public boolean clicked(float cursorX, float cursorY) {
        if(super.clicked(cursorX, cursorY)) {
            ticked = !ticked;
            return true;
        }

        return false;
    }

    /**
     * Gets the current status of checkbox (Ticked or unticked)
     * @return  Returns the current status of checkbox (Ticked or unticked)
     */
    public boolean isTicked() {
        return ticked;
    }

    /**
     * Repositions the object from any point to the point (x, y).
     * @param x The distance of the point along the x-axis
     * @param y The distance of the point along the y-axis
     */
    @Override
    public void reposition(float x, float y) {
        super.reposition(x, y);
        untickedSprite.reposition(x, y);
    }

    /**
     * Moves the object to the right by a specified amount.
     * @param amount The amount to move the point along the x-axis by
     */
    @Override
    public void moveRight(int amount) {
        super.moveRight(amount);
        untickedSprite.moveRight(amount);
    }

    /**
     * Moves the object to the left by a specified amount.
     * @param amount The amount to move the point along the x-axis by
     */
    @Override
    public void moveLeft(int amount) {
        super.moveLeft(amount);
        untickedSprite.moveLeft(amount);
    }

    /**
     * Moves the object up by a specified amount.
     * @param amount The amount to move the point along the y-axis by
     */
    @Override
    public void moveUp(int amount) {
        super.moveUp(amount);
        untickedSprite.moveUp(amount);
    }

    /**
     * Moves the object down by a specified amount.
     * @param amount The amount to move the point along the y-axis by
     */
    @Override
    public void moveDown(int amount) {
        super.moveDown(amount);
        untickedSprite.moveDown(amount);
    }
}
