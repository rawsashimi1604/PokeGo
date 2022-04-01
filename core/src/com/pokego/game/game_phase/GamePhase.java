package com.pokego.game.game_phase;

/**
 * Defines the different types of Phases available in GameState.
 */

public enum GamePhase {
    ROUND_START, // TextWidget: awaiting moves, and timer showing
    ROUND_TEXT_MOVES, // TextWidget: Character used move
    ROUND_DISPLAY_MOVES, // Display Animation
    ROUND_LOWER_HP, // LowerHP, HP bar will lower, change color if < certain threshold
    ROUND_DISPLAY_FAINTED, // TextWidget: Character fainted + animation (pokemon turns red, gets smaller, turns into particles)
    ROUND_DISPLAY_NEXT_CHAR, // TextWidget: Player sent out Character + animation (pokeball throw + red silouhette growing -> actual pokemon)
    ROUND_END, // TextWidget: round ended
}

