package com.pokego.errors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pokego.datamodel.GameTextWidget;

/**
 * Custom error widget built using GameTextWidget. Used to display errors in the game. When clicked, the GameErrorWidget will be hidden from the screen.
 */
public class GameErrorWidget extends GameTextWidget {
    public static final float DEFAULT_BREADTH = 100;
    public static final float DEFAULT_LENGTH = 400;
    public static final Color DEFAULT_COLOR = new Color(255,0,0,0.5f);
    public static final Color DEFAULT_BORDER_COLOR = new Color(0, 0, 0, 0.5f);
    public static final float DEFAULT_BORDER_RADIUS = 10f;
    public static final float DEFAULT_TEXT_SCALE = 0.6f;

    private Error error;
    private boolean showError;

    /**
     * Constructs a GameErrorWidget with default parameters defined in the class.
     * @param error Error to display, defined in the error enum.
     */
    public GameErrorWidget(Error error) {
        this(DEFAULT_BREADTH, DEFAULT_LENGTH, DEFAULT_COLOR, DEFAULT_BORDER_COLOR, DEFAULT_BORDER_RADIUS, error);
    }

    /**
     * Constructs a GameErrorWidget with customized parameters.
     * @param breadth The breadth of the GameErrorWidget.
     * @param length The length of the GameErrorWidget.
     * @param color The color of the GameErrorWidget.
     * @param borderColor The borderColor of the GameErrorWidget.
     * @param borderRadius The borderRadius of the GameErrorWidget.
     * @param error Error to display, defined in the error enum.
     */
    public GameErrorWidget(float breadth, float length, Color color, Color borderColor, float borderRadius, Error error) {
        super(breadth, length, true, DEFAULT_SCROLLING_DURATION);
        setColor(color);
        setBorderColor(borderColor);
        getRectangle().setBorderRadius(borderRadius);

        // Set initial state of error widget to show.
        showError = true;

        this.error = error;

        // Set error text to widget.
        String errorText = String.format("ERROR %d: %s\n", error.getCode(), error.getDescription());
        getTextObject().setText(errorText);
        setTextScale(DEFAULT_TEXT_SCALE);

        reposition(Gdx.graphics.getWidth()/2 - getWidth()/2, Gdx.graphics.getHeight()/2 - getHeight()/2);
    }

    /**
     * Draw error text widget using LibGDX's engine. Displays error text widget.
     * @param sb SpriteBatch
     * @param sr ShapeRenderer
     */
    @Override
    public void draw(SpriteBatch sb, ShapeRenderer sr) {
        if (showError) {
            super.draw(sb, sr);
        }
    }

    /**
     * Hides error if clicked.
     */
    public void hideError() {
        // hide error if clicked
        if(Gdx.input.justTouched()) {
            showError = false;
        }
    }

    /**
     * Gets the current display status of the error widget.
     * @return Returns the current display status of the error widget.
     */
    public boolean isDisplay() {
        return showError;
    }
}
