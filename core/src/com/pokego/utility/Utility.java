package com.pokego.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pokego.datamodel.GameSprite;

/**
 * Collection of misc functions to be used around the whole project.
 */
public class Utility {

    /**
     * Prints the current cursor location
     */
    public static void printCursorLocation() {
        System.out.printf("Cursor at (%d, %d).\n", Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
    }

    /**
     * Enables ShapeRenderer blend for displaying translucent objects.
     */
    public static void enableShapeRendererBlend() {
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    /**
     * Disable ShapeRenderer blend for displaying translucent objects.
     */
    public static void disableShapeRendererBlend() {
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    /**
     * Draw currently selected background GameSprite.
     * @param sb SpriteBatch
     * @param background GameSprite of background
     */
    public static void drawBackground(SpriteBatch sb, GameSprite background) {
        sb.begin();
        background.draw(sb);
        sb.end();
    }
}
