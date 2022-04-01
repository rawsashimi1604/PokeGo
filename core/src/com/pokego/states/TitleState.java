package com.pokego.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.pokego.datamodel.GameSprite;
import com.pokego.datamodel.GameText;
import com.pokego.utility.Font;
import com.pokego.utility.Utility;

/**
 * The TitleState is displayed at the start of the game.
 */
public class TitleState extends State {

    private GameText gameText;
    private Color defaultGameTextColor;

    private GameSprite titleBackground;
    private GameSprite logo;

    private float runTime; // for blinking text

    /**
     * Constructs the TitleState.
     * @param gsm The current GameStateManager used to manage all States.
     */
    public TitleState(GameStateManager gsm) {
        super(gsm);
        System.out.println("PUSHED TITLE STATE");

        // create font
        gameText = new GameText(Font.LUCIDA_SANS);

        gameText.setText("PRESS ANYWHERE TO CONTINUE...");
        gameText.scale(1.2f);
        gameText.setColor(Color.BLACK);
        gameText.reposition(0, 120);
        defaultGameTextColor = gameText.getColor(); // Used to store color for blinking in title screen.

        // create images / sprites
        titleBackground = new GameSprite("./maps/title_wallpaper_blur.jpg");
        titleBackground.scale(1f);
        titleBackground.reposition(0, 0);

        logo = new GameSprite("./ui/logo.png");
        logo.scale(1f);
        logo.reposition(Gdx.graphics.getWidth()/2 - logo.getWidth()/2 + 50, 380);

        runTime = 0;
    }

    /**
     * Handle inputs in the current State.
     */
    @Override
    protected void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
            System.out.println("GO TO MENU STATE");
            gsm.push(new MenuState(gsm));
        }
    }

    /**
     * Updates the current State. Function is ran once every frame.
     * @param dt delta time
     */
    @Override
    public void update(float dt) {
        runTime += dt;

        handleInput();
        blinkTitleScreenText(gameText, 1.15f, defaultGameTextColor);
    }

    /**
     * Blinks the GameText according to blink time.
     * @param text GameText object to blink.
     * @param blinkTime Time between each blink.
     * @param defaultColor The color of the GameText.
     */
    private void blinkTitleScreenText(GameText text, float blinkTime, Color defaultColor) {
        // Blinking text
        if (runTime < blinkTime/2) {
            gameText.setColor(new Color(0, 0, 0, 0));
        } else if(runTime > blinkTime/2 && runTime < blinkTime) {
            gameText.setColor(defaultColor);
        } else {
            runTime = 0;
        }
    }

    /**
     * Renders the current State. Function is ran once every frame.
     * @param sb SpriteBatch
     * @param sr ShapeRenderer
     */
    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {
        Utility.drawBackground(sb, titleBackground);

        // Draw sprites
        sb.begin();
        gameText.draw(sb, Gdx.graphics.getWidth(), Align.center, false);
        logo.draw(sb);
        sb.end();
    }

    /**
     * Frees up memory by disposing sprites from SpriteBatch.
     */
    @Override
    public void dispose() {
        // dispose GameSpriteGroup objects
        gameText.dispose();
        titleBackground.dispose();
    }
}
