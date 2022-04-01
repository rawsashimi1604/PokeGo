package com.pokego.datamodel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.pokego.PokeGo;

/**
 * GameInputField is used to represent an input box. It uses LibGDX's scene2D library to generate input fields. Requires a Stage to mount on.
 */
public class GameInputField implements IMovable {
    private float width;
    private float height;
    private TextField inputField;
    private Vector2 position;

    /**
     * Constructs the GameInputField object at position (0, 0) using width and height.
     * @param width The width of the GameInputField.
     * @param height The height of the GameInputField.
     */
    public GameInputField(float width, float height) {
        this(0, 0, width, height);
    }

    /**
     * Constructs the GameInputField object at position (x, y) using width and height.
     * @param x The x position of the GameInputField.
     * @param y The y position of the GameInputField.
     * @param width The width of the GameInputField.
     * @param height The height of the GameInputField.
     */
    public GameInputField(float x, float y, float width, float height) {
        position = new Vector2(x, y);
        this.width = width;
        this.height = height;

        Skin defaultSkin = new Skin(Gdx.files.internal("uiskin.json"));
        inputField = new TextField("", defaultSkin);
        inputField.setWidth(width);
        inputField.setHeight(height);
        inputField.setX(getX());
        inputField.setY(getY());
    }

    /**
     * Gets the x position of the GameInputField
     * @return Returns the x position of the GameInputField
     */
    public float getX() {
        return position.x;
    }

    /**
     * Gets the y position of the GameInputField
     * @return Returns the y position of the GameInputField
     */
    public float getY() {
        return position.y;
    }

    /**
     * Adds the GameInputField to stage.
     * @param stage Stage to add GameInputField to.
     */
    public void addToStage(Stage stage) {
        stage.addActor(inputField);
    }

    /**
     * Gets the current text in the GameInputField.
     * @return Returns current text in the GameInputField.
     */
    public String getText() {
        return inputField.getText();
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
        inputField.setX(getX());
        inputField.setY(getY());
    }

    /**
     * Moves the object to the right by a specified amount.
     * @param amount The amount to move the point along the x-axis by
     */
    @Override
    public void moveRight(int amount) {
        position.x = Math.min(PokeGo.WIDTH, position.x + amount);
        inputField.setX(getX());
        inputField.setY(getY());
    }

    /**
     * Moves the object to the left by a specified amount.
     * @param amount The amount to move the point along the x-axis by
     */
    @Override
    public void moveLeft(int amount) {
        position.x = Math.max(0, position.x - amount);
        inputField.setX(getX());
        inputField.setY(getY());
    }

    /**
     * Moves the object up by a specified amount.
     * @param amount The amount to move the point along the y-axis by
     */
    @Override
    public void moveUp(int amount) {
        position.y = Math.min(PokeGo.HEIGHT, position.y + amount);
        inputField.setX(getX());
        inputField.setY(getY());
    }

    /**
     * Moves the object down by a specified amount.
     * @param amount The amount to move the point along the y-axis by
     */
    @Override
    public void moveDown(int amount) {
        position.y = Math.max(0, position.y - amount);
        inputField.setX(getX());
        inputField.setY(getY());
    }

    /**
     * Gets the height of the GameInputField.
     * @return Returns the height of the GameInputField.
     */
    public float getHeight() {
        return inputField.getHeight();
    }

    /**
     * Gets the width of the GameInputField.
     * @return Returns the width of the GameInputField.
     */
    public float getWidth() {
        return inputField.getWidth();
    }
}

