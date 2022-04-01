package com.pokego.errors;

/**
 * Error enum defining available errors in the game.
 */
public enum Error {
    LONG_NAME(0, "Player name cannot be more than 25 characters."),
    FIELD_EMPTY(1, "Player name cannot be empty"),
    FIELD_SAME(2, "Players cannot have the same name.");

    private final int code;
    private final String description;

    private Error(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }
}
