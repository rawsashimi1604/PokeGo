package com.pokego.game.game_phase;

import com.pokego.game.game_ui.TimerUI;

/**
 * The GameRoundTimer object represents the timer in the GameState. It contains all logic for the timer in the RoundStartPhase as well as the UI of the timer.
 */
public class GameRoundTimer {
    private float timeElapsed;
    private float timeBetweenRound;
    private TimerUI timerUI;

    /**
     * Constructs a timer with a specified time allocated for the Player to key in their inputs.
     * @param timeBetweenRound the time allocated for the Player to key in their inputs.
     */
    public GameRoundTimer(float timeBetweenRound) {
        this.timeBetweenRound = timeBetweenRound;

        timerUI = new TimerUI();
    }

    /**
     * Gets the amount of time left before timer reaches 0.
     * @return Returns the amount of time left before timer reaches 0.
     */
    public float getTimeLeft() {
        return timeBetweenRound - timeElapsed;
    }

    /**
     * Decrements remaining time of timer
     * @param dt delta time
     */
    public void updateTime(float dt) {
        timeElapsed += dt;
        timerUI.getTimerText().setText(String.format("%2.2f",timeBetweenRound - timeElapsed));
    }

    /**
     * Returns true if timer has ended. False otherwise.
     * @return Returns true if timer has ended. False otherwise.
     */
    public boolean timerEnded() {
        return (timeBetweenRound - timeElapsed) <= 0;
    }

    /**
     * Gets the GameSprite and GameText of the timer.
     * @return Returns the GameSprite and GameText of the timer.
     */
    public TimerUI getTimerUI() {
        return timerUI;
    }
}
