package com.pokego.datamodel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * GameSprite is a wrapper object used to render sprites. It incorporates LibGDX's Texture and Sprite class to render sprites to the screen. Sprite paths are found in the "./assets" folder.
 */
public class GameSprite extends GameSpriteGroup {
    private Texture texture;
    private Sprite sprite;
    private String textureLocation;
    private float width;
    private float height;

    private float scaleX;
    private float scaleY;

    /**
     * Constructs the GameSprite with a (0,0) position.
     * @param textureLocation Location of sprite to render.
     */
    public GameSprite(String textureLocation) {
        this(0, 0, textureLocation);
    }

    /**
     * Constructs the GameSprite with a (x,y) position.
     * @param x The x position to display sprite.
     * @param y The y position to display sprite.
     * @param textureLocation Location of sprite to render.
     */
    public GameSprite(int x, int y, String textureLocation) {
        super(x, y);
        this.textureLocation = textureLocation;
        texture = new Texture(textureLocation);
        sprite = new Sprite(texture);

        width = sprite.getWidth();
        height = sprite.getHeight();

        scaleX = 1f;
        scaleY = 1f;
    }

    /**
     * Flips (invert) the sprite.
     */
    public void flip() {
        sprite.flip(true, false);
    }

    /**
     * Draw sprite using LibGDX's engine. Displays sprite.
     * @param sb SpriteBatch
     */
    @Override
    public void draw(SpriteBatch sb) {
        sb.draw(sprite, getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Frees up memory by disposing sprites from SpriteBatch.
     */
    @Override
    public void dispose() {
        texture.dispose();
    }

    /**
     * Scales sprite size, x and y scaled proportionately.
     * @param scale amount to scale
     */
    @Override
    public void scale(float scale) {
        scaleX *= scale;
        scaleY *= scale;

        width *= scale;
        height *= scale;
    }

    /**
     * Scales sprite size, x and y scaled separately.
     * @param scaleX amount to scale (width)
     * @param scaleY amount to scale (height)
     */
    @Override
    public void scale(float scaleX, float scaleY) {
        this.scaleX *= scaleX;
        this.scaleY *= scaleY;

        width *= scaleX;
        height *= scaleY;
    }

    /**
     * Gets the width of the GameSprite
     * @return Returns the width of the GameSprite
     */
    public float getWidth() {
        return width;
    }

    /**
     * Gets the height of the GameSprite
     * @return Returns the height of the GameSprite
     */
    public float getHeight() {
        return height;
    }

    /**
     * Gets the Texture object of the GameSprite
     * @return Returns the Texture object of the GameSprite
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Gets the Sprite object of the GameSprite
     * @return Returns the Sprite object of the GameSprite
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Gets the texture location of the GameSprite
     * @return Returns the texture location of the GameSprite
     */
    public String getTextureLocation() {
        return textureLocation;
    }
}
