package com.pokego.datamodel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pokego.utility.Font;

/**
 * GameText represents text that is rendered by LibGDX's engine. The GameText is implemented using BitmapFonts and GlyphLayouts. The available fonts are specified by the Font enum. Font paths are found in the "./assets/fonts" folder. New .fnt files can be generated using Hiero.
 */
public class GameText extends GameSpriteGroup {
    public final static Font DEFAULT_FONT = Font.VERDANA;
    public final static Color DEFAULT_FONT_COLOR = Color.BLACK;

    private String text;

    private Font font;
    private Color color;
    private float scale;

    private BitmapFont bmFont;
    private String fontPath;
    private GlyphLayout layout;

    /**
     * Constructs the GameText with a (0,0) position. Uses the default font and default font color.
     */
    public GameText() {
        this(0, 0, DEFAULT_FONT, DEFAULT_FONT_COLOR, 1f);
    }

    /**
     * Constructs the GameText with a (0,0) position and a specified font. Uses the default font color.
     * @param font Font to use, found in the Font enum.
     */
    public GameText(Font font) {
        this(0, 0, font, DEFAULT_FONT_COLOR, 1f);
    }

    /**
     * Constructs the GameText with a (0,0) position, a specified font and default font color.
     * @param font Font to use, found in the Font enum.
     * @param fontColor Color of font, uses LibGDX's Color class.
     */
    public GameText(Font font, Color fontColor) {
        this(0, 0, font, fontColor, 1f);
    }

    /**
     * Constructs the GameText with a (x,y) position, a specified font and default font color. Also specifies the scale of the font.
     * @param x The x position of the GameText.
     * @param y The x position of the GameText.
     * @param font Font to use, found in the Font enum.
     * @param color Color of font, uses LibGDX's Color class.
     * @param scale Size of font.
     */
    public GameText(int x, int y, Font font, Color color, float scale) {
        super(x, y);
        this.text = "";
        this.color = color;
        this.scale = scale;
        this.font = font;

        // Link font to .ttf file in assets folder
        setFontPath(font);

        // Create LibGDX font objects
        bmFont = new BitmapFont(Gdx.files.internal(fontPath));
        layout = new GlyphLayout();

        bmFont.setColor(color);

    }

    /**
     * Draw font using LibGDX's engine. Displays font.
     * @param sb SpriteBatch
     */
    @Override
    public void draw(SpriteBatch sb) {
        bmFont.draw(sb, text, getX(), getY());
    }

    /**
     * Draws font with specific alignment using LibGDX's engine. Displays font.
     * @param sb SpriteBatch
     * @param targetWidth How large the text grows before wrapping. To wrap at end of screen, put screen width.
     * @param alignment Align left, center or right. Uses LibGDX's Align util.
     * @param wrap Specifies whether to wrap text. True to wrap, False otherwise.
     */
    public void draw(SpriteBatch sb, float targetWidth, int alignment, boolean wrap) {
        // Use Align util to set alignment
        // Target width = text width until wrap
        bmFont.draw(sb, text, getX(), getY(), targetWidth, alignment, wrap);
    }

    /**
     * Frees up memory by disposing sprites from SpriteBatch.
     */
    @Override
    public void dispose() {
        bmFont.dispose();
    }

    /**
     * Scales text size, x and y scaled proportionately.
     * @param scale amount to scale
     */
    @Override
    public void scale(float scale) {
        this.scale = scale;
        bmFont.getData().setScale(scale);
    }

    /**
     * Scales text size, x and y scaled separately.
     * @param scaleX amount to scale (width)
     * @param scaleY amount to scale (height)
     */
    @Override
    public void scale(float scaleX, float scaleY) {
        this.scale = scale;
        bmFont.getData().setScale(scaleX, scaleY);
    }

    /**
     * Helper function to set font path when constructing GameText.
     * @param font
     */
    private void setFontPath(Font font) {
        this.font = font;
        switch(font) {
            case VERDANA:
                fontPath = "./fonts/verdana.fnt";
                break;
            case LUCIDA_SANS:
                fontPath = "./fonts/lucida_sans.fnt";
                break;
            case NONE:
                fontPath = "";
                break;
        }
    }

    /**
     * Gets the current text of GameText.
     * @return Returns the current text of GameText.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of GameText.
     * @param text Text to set.
     */
    public void setText(String text) {
        this.text = text;
        layout.setText(bmFont, text);
    }

    /**
     * Gets the current color of GameText.
     * @return Returns the current color of GameText
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of GameText
     * @param color Color of GameText
     */
    public void setColor(Color color) {
        this.color = color;
        bmFont.setColor(color);
    }

    /**
     * Gets the height of GameText.
     * @return Returns the height of GameText.
     */
    public float getHeight() {
        return layout.height;
    }

    /**
     * Gets the width of GameText.
     * @return Returns the width of GameText.
     */
    public float getWidth() {
        return layout.width;
    }

}
