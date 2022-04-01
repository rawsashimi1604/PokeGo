package com.pokego.game.game_ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pokego.datamodel.GameSprite;
import com.pokego.datamodel.GameText;
import com.pokego.utility.Font;

/**
 * TimerUI represents the timer object's UI in the middle of the screen. It consists of a GameText which displays the time remaining, and a GameSprite which encapsulates the GameText.
 */
public class TimerUI{

    public static final String TIMER_UI_FILE_LOCATION = "./ui/timer.png";
    private GameSprite timerSprite;

    private GameText timerText;

    /**
     * Constructs the TimerUI object.
     */
    public TimerUI() {
        timerSprite = new GameSprite(TIMER_UI_FILE_LOCATION);
        timerSprite.scale(5f);
        timerSprite.reposition(Gdx.graphics.getWidth()/2 - timerSprite.getWidth()/2, Gdx.graphics.getHeight()/2 - timerSprite.getHeight()/2);

        timerText = new GameText(Font.LUCIDA_SANS);
        timerText.reposition(Gdx.graphics.getWidth()/2 - timerSprite.getWidth()/2 + 23, Gdx.graphics.getHeight()/2 - timerSprite.getHeight()/2 + 46);
    }

    /**
     * Get the GameSprite of the timer.
     * @return GameSprite of the timer.
     */
    public GameSprite getTimerSprite() {
        return timerSprite;
    }

    /**
     * Get the GameText of the timer.
     * @return GameText of the timer.
     */
    public GameText getTimerText() {
        return timerText;
    }

    /**
     * Draws the both the GameSprite and GameText objects.
     * @param sb SpriteBatch
     */
    public void draw(SpriteBatch sb) {
        timerSprite.draw(sb);
        timerText.draw(sb);
    }

    /**
     * Disposes both the GameSprite and GameText objects.
     */
    public void dispose() {
        timerSprite.dispose();
        timerText.dispose();
    }
}
