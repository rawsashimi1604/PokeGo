package com.pokego.game.game_ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pokego.datamodel.GameRectangle;
import com.pokego.datamodel.GameText;
import com.pokego.datamodel.GameTextWidget;
import com.pokego.game.game_data.Character;
import com.pokego.game.game_data.Player;
import com.pokego.utility.Font;

/**
 * The PlayerUI object represents a collection of Player UI elements to be displayed in GameState.
 */
public class PlayerUI {
    private final static float DEFAULT_BREADTH = 200f;
    private final static float DEFAULT_LENGTH = 400f;
    private final static float DEFAULT_BORDER_RADIUS = 10f;

    private final static float PLAYER_ONE_X = 0;
    private final static float PLAYER_ONE_Y = 500;

    private final static float PLAYER_TWO_X = 600;
    private final static float PLAYER_TWO_Y = 500;


    private GameRectangle playerUIBox;
    private GameText playerName;
    private GameText playerHealthLabel;
    private GameText playerHealth;
    private GameRectangle playerOuterHealthBar;
    private GameRectangle playerInnerHealthBar;
    private GameText playerCharacterLabel;
    private GameText playerCharacters;

    private GameTextWidget playerHelpBox;

    private Player player;
    private int currentHealth;
    private int currentCharacters;
    private String currCharacterName;


    /**
     * Constructs a PlayerUI object according to the PlayerNumber and Player object details. Moves all UI elements to the PlayerNumber's respective location.
     * @param player The Player object to receive data from.
     * @param playerNumber PlayerNumber to display, possible values are defined in the PlayerNumber enum.
     */
    public PlayerUI(Player player, PlayerNumber playerNumber) {
        this.player = player;

        currentHealth = 100;
        currentCharacters = Player.DEFAULT_NO_CHARACTERS;

        // Player one UI
        playerUIBox = new GameRectangle(DEFAULT_BREADTH, DEFAULT_LENGTH, DEFAULT_BORDER_RADIUS);
        if (playerNumber == PlayerNumber.PLAYER_ONE) {
            playerUIBox.reposition(PLAYER_ONE_X, PLAYER_ONE_Y);
        } else if (playerNumber == PlayerNumber.PLAYER_TWO) {
            playerUIBox.reposition(PLAYER_TWO_X, PLAYER_TWO_Y);
        }

        playerUIBox.setBorderThickness(8);
        playerUIBox.setColor(Color.GOLD);
        playerUIBox.setBorderColor(Color.YELLOW);

        this.playerName = new GameText();
        this.playerName.setText(player.getName());
        this.playerName.scale(0.9f);
        this.playerName.reposition(playerUIBox.getX() + 20, playerUIBox.getY() + playerUIBox.getBreadth() - 20);

        playerHealthLabel = new GameText(Font.LUCIDA_SANS);
        playerHealthLabel.setText("Health");
        playerHealthLabel.scale(0.6f);
        playerHealthLabel.reposition(this.playerName.getX(), this.playerName.getY() - this.playerName.getHeight() - 20);

        playerOuterHealthBar = new GameRectangle(25, 300);
        playerOuterHealthBar.setColor(Color.WHITE);
        playerOuterHealthBar.setBorderRadius(12f);
        playerOuterHealthBar.setBorderColor(Color.WHITE);
        playerOuterHealthBar.setBorderThickness(3);
        playerOuterHealthBar.reposition(playerHealthLabel.getX(), playerHealthLabel.getY() - 50);

        playerInnerHealthBar = new GameRectangle(25, (float)currentHealth/100 * playerOuterHealthBar.getLength());
        playerInnerHealthBar.setColor(Color.GREEN);
        playerInnerHealthBar.setBorderRadius(12f);
        playerInnerHealthBar.setBorderThickness(2);
        playerInnerHealthBar.setBorderColor(Color.WHITE);
        playerInnerHealthBar.reposition(playerHealthLabel.getX(), playerHealthLabel.getY() - 50);

        playerHealth = new GameText(Font.LUCIDA_SANS);
        String healthDisplay = String.format("%d/100", currentHealth);
        playerHealth.setText(healthDisplay);
        playerHealth.scale(0.5f);
        playerHealth.reposition(playerOuterHealthBar.getX() + playerOuterHealthBar.getLength() + 10, playerOuterHealthBar.getY() + playerHealth.getHeight() - 5);

        playerCharacterLabel = new GameText();
        playerCharacterLabel.setText("Characters");
        playerCharacterLabel.scale(0.6f);
        playerCharacterLabel.reposition(playerOuterHealthBar.getX(), playerOuterHealthBar.getY() - 20);

        playerCharacters = new GameText(Font.LUCIDA_SANS);
        String charactersDisplay = String.format("%d/%d REMAINING", currentCharacters, Player.DEFAULT_NO_CHARACTERS);
        playerCharacters.setText(charactersDisplay);
        playerCharacters.scale(0.7f);
        playerCharacters.reposition(playerCharacterLabel.getX() + 50, playerCharacterLabel.getY() - 30);

        playerHelpBox = new GameTextWidget(Gdx.graphics.getHeight() - playerUIBox.getY() - playerUIBox.getBreadth(), playerUIBox.getLength());
        playerHelpBox.setBorderRadius(0f);
        playerHelpBox.setBorderThickness(4);
        playerHelpBox.setColor(new Color(255, 255, 255, 0.5f));
        playerHelpBox.setBorderColor(new Color(50, 50, 50, 0.7f));
        playerHelpBox.setTextScale(0.45f);
        playerHelpBox.reposition(playerUIBox.getX() , playerUIBox.getY() + playerUIBox.getBreadth());

        if (playerNumber == PlayerNumber.PLAYER_ONE) {
            playerHelpBox.setText(String.format("Q - %s\nW - %s\nE - %s\nR - %s\n", player.getCurrentCharacter().getFireMove().getName(), player.getCurrentCharacter().getWaterMove().getName(), player.getCurrentCharacter().getGrassMove().getName(), player.getCurrentCharacter().getDefenseMove().getName()));
        } else {
            playerHelpBox.setText(String.format("Up - %s\nDown - %s\nLeft - %s\nRight - %s\n", player.getCurrentCharacter().getFireMove().getName(), player.getCurrentCharacter().getWaterMove().getName(), player.getCurrentCharacter().getGrassMove().getName(), player.getCurrentCharacter().getDefenseMove().getName()));
        }

    }

    /**
     * Updates playerUI Character name
     * @param name Name of Character.
     */
    public void updateCharacterName(String name) {
        playerHealthLabel.setText(name);
    }

    public void updateMoves(PlayerNumber playerNumber) {
        if (playerNumber == PlayerNumber.PLAYER_ONE) {
            playerHelpBox.setText(String.format("Q - %s\nW - %s\nE - %s\nR - %s\n", player.getCurrentCharacter().getFireMove().getName(), player.getCurrentCharacter().getWaterMove().getName(), player.getCurrentCharacter().getGrassMove().getName(), player.getCurrentCharacter().getDefenseMove().getName()));
        } else if (playerNumber == PlayerNumber.PLAYER_TWO) {
            playerHelpBox.setText(String.format("Up - %s\nDown - %s\nLeft - %s\nRight - %s\n", player.getCurrentCharacter().getFireMove().getName(), player.getCurrentCharacter().getWaterMove().getName(), player.getCurrentCharacter().getGrassMove().getName(), player.getCurrentCharacter().getDefenseMove().getName()));
        }
    }

    /**
     * Update playerUI health bar and health value.
     * @param health Health of Character.
     */
    public void updateHealth(int health) {
        currentHealth = health;
        String healthDisplay = String.format("%d/%d", currentHealth, Character.CHARACTER_STARTING_HP);
        playerHealth.setText(healthDisplay);

        if (health != 0) {
            playerInnerHealthBar.setLength((float)currentHealth/100 * playerOuterHealthBar.getLength());
        }


        // Change HP Bar color based on health
        if (health <= 0) {
            playerInnerHealthBar.setColor(new Color(255/255f, 255/255f, 255/255f, 0f));
        }
        else if (health <= 0.15 * Character.CHARACTER_STARTING_HP) {
            playerInnerHealthBar.setColor(Color.RED);
        } else if (health <= 0.5 * Character.CHARACTER_STARTING_HP) {
            playerInnerHealthBar.setColor(Color.ORANGE);
        } else {
            playerInnerHealthBar.setColor(Color.GREEN);
        }

    }

    /**
     * Update number of characters still alive
     * @param characters Number of characters still alive
     */
    public void updateCharacters(int characters) {
        currentCharacters = characters;
        String charactersDisplay = String.format("%d/6 REMAINING", currentCharacters);
        playerCharacters.setText(charactersDisplay);
    }

    /**
     * Render playerUI using LibGDX's engine.
     * @param sb SpriteBatch
     * @param sr ShapeRenderer
     */
    public void draw(SpriteBatch sb, ShapeRenderer sr) {
        playerUIBox.draw(sr);
        playerOuterHealthBar.draw(sr);
        playerInnerHealthBar.draw(sr);
        playerHelpBox.draw(sb, sr);


        sb.begin();
        playerName.draw(sb);
        playerHealthLabel.draw(sb);
        playerHealth.draw(sb);
        playerCharacterLabel.draw(sb);
        playerCharacters.draw(sb);
        sb.end();
    }

    /**
     * Frees up memory by disposing sprites from SpriteBatch.
     */
    public void dispose() {
        playerName.dispose();
        playerHealthLabel.dispose();
        playerHealth.dispose();
        playerCharacterLabel.dispose();
        playerCharacters.dispose();
    }
}
