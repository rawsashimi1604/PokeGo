package com.pokego.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pokego.datamodel.GameButton;
import com.pokego.datamodel.GameRectangle;
import com.pokego.datamodel.GameSprite;
import com.pokego.datamodel.GameText;
import com.pokego.game.game_data.Character;
import com.pokego.game.game_data.Player;
import com.pokego.game.game_ui.PlayerNumber;
import com.pokego.utility.CSVHandler;
import com.pokego.utility.Font;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The GameStartState is displayed after the PreGameState. It randomly generates a list of characters for each Player. The Character's information and Moves are retrieved from their respective CSV data files and read using the CSVHandler object. The .csv files can be found in the "./assets/data" folder.
 */
public class GameStartState extends State {

    private DataPacket datapacket;
    private GameSprite background;
    private ArrayList<GameButton> gamestartStateButtons;
    private GameButton startButton;

    private GameRectangle playerOneUI, playerTwoUI;
    private GameText playerOneName, playerTwoName;

    private ArrayList<Character> playerOneChars;
    private ArrayList<GameText> playerOneCharsLabels;
    private ArrayList<Character> playerTwoChars;
    private ArrayList<GameText> playerTwoCharsLabels;


    /**
     * Constructs the GameStartState.
     * @param gsm The current GameStateManager used to manage all States.
     * @param data The DataPacket to store and receive data.
     */
    protected GameStartState(GameStateManager gsm, DataPacket data) {
        super(gsm);
        System.out.println("created GameStart State");

        // Receive datapacket from PreGameState
        datapacket = data;

        // generate characters for player 1 and 2
        playerOneChars = generateCharacters(CSVHandler.getCharacterCount());
        playerTwoChars = generateCharacters(CSVHandler.getCharacterCount());

        // Generate background
        background = new GameSprite("./maps/title_wallpaper.jpg");
        background.scale(1f);
        background.reposition(0, 0);

        // Generate Buttons
        gamestartStateButtons = new ArrayList<GameButton>();
        startButton = new GameButton("./ui/start_primary.png", "./ui/start_secondary.png", ButtonClass.GAME);
        startButton.scale(1.25f);
        startButton.reposition(Gdx.graphics.getWidth()/2 - startButton.getWidth()/2, Gdx.graphics.getHeight()/2 - startButton.getHeight()/2);
        gamestartStateButtons.add(startButton);

        // Generate UI Boxes
        playerOneUI = new GameRectangle(250, 700, 5f);
        playerOneUI.setColor(new Color(211/255f, 236/255f, 167/255f, 0.45f));
        playerOneUI.setBorderColor(new Color(161/255f, 181/255f, 125/255f, 0.4f));
        playerOneUI.reposition(100, 450);

        playerTwoUI = new GameRectangle(250, 700, 5f);
        playerTwoUI.setColor(new Color(211/255f, 236/255f, 167/255f, 0.45f));
        playerTwoUI.setBorderColor(new Color(161/255f, 181/255f, 125/255f, 0.4f));
        playerTwoUI.reposition(200, 100);

        // Generate Player names
        playerOneName = new GameText(Font.LUCIDA_SANS);
        playerOneName.setText(String.format("PLAYER 1: %s", datapacket.getPlayerOneName()));
        playerOneName.reposition(playerOneUI.getX() + 30, playerOneUI.getY() + playerOneUI.getBreadth() - 30);

        playerTwoName = new GameText(Font.LUCIDA_SANS);
        playerTwoName.setText(String.format("PLAYER 2: %s", datapacket.getPlayerTwoName()));
        playerTwoName.reposition(playerTwoUI.getX() + 30, playerTwoUI.getY() + playerTwoUI.getBreadth() - 30);

        // Generate Player character sprites and their labels
        playerOneCharsLabels = new ArrayList<GameText>();

        int playerOneOffset = 0;
        for (Character character : playerOneChars) {
            character.getSprite().scale(2f);
            character.getSprite().reposition(playerOneUI.getX() + 30 + playerOneOffset, playerOneUI.getY() + 30);
            playerOneOffset += character.getSprite().getWidth() + 10;

            GameText characterLabel = new GameText(Font.LUCIDA_SANS);
            characterLabel.scale(0.4f);
            characterLabel.setText(character.getName());
            characterLabel.reposition(character.getSprite().getX(), character.getSprite().getY() + character.getSprite().getHeight() + characterLabel.getHeight() + 20);

            playerOneCharsLabels.add(characterLabel);
        }

        playerTwoCharsLabels = new ArrayList<GameText>();

        int playerTwoOffset = 0;
        for (Character character : playerTwoChars) {
            character.getSprite().scale(2f);
            character.getSprite().flip(); // flip sprite to face the other way.
            character.getSprite().reposition(playerTwoUI.getX() + 30 + playerTwoOffset, playerTwoUI.getY() + 30);
            playerTwoOffset += character.getSprite().getWidth() + 10;

            GameText characterLabel = new GameText(Font.LUCIDA_SANS);
            characterLabel.scale(0.4f);
            characterLabel.setText(character.getName());
            characterLabel.reposition(character.getSprite().getX(), character.getSprite().getY() + character.getSprite().getHeight() + characterLabel.getHeight() + 20);

            playerTwoCharsLabels.add(characterLabel);
        }
    }

    /**
     * Randomly generates a specified number of Characters into an ArrayList. Uses data from the .csv files found in the "./assets/data" folder. Uses CSVHandler to access the data.
     * @param charsAvailable Number of characters to generate.
     * @return Randomly generated Character ArrayList.
     */
    private ArrayList<Character> generateCharacters(int charsAvailable) {
        ArrayList<Integer> numbersGenerated = new ArrayList<Integer>();
        ArrayList<Character> charactersGenerated = new ArrayList<Character>();
        while(numbersGenerated.size() != Player.DEFAULT_NO_CHARACTERS) {
            int charID = generateRandomInt(1, charsAvailable);
            // If charID does not exist in array, add to array
            if (!numbersGenerated.contains(charID)) {
                numbersGenerated.add(charID);
            }
        }

        for (Integer charID : numbersGenerated) {
            charactersGenerated.add(CSVHandler.getCharacterByID(charID));
        }

        return charactersGenerated;
    }

    /**
     * Helper function to generate random integer. Random integer is used to select Character ID in the .csv file.
     * @param min Lower bound of number.
     * @param max Upper bound of number.
     * @return Returns randomly generated integer between min and max, inclusive.
     */
    private int generateRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * Handle inputs in the current State.
     */
    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            for (GameButton button : gamestartStateButtons) {
                if (button.hovered(Gdx.input.getX(), Gdx.input.getY())) {
                    System.out.println("clicked " + button.getButtonState());
                    handleButtonClick(button);
                }
            }
        }
    }

    /**
     * Handle button clicks in the current State.
     * @param button Type of button to handle.
     */
    private void handleButtonClick(GameButton button) {
        switch (button.getButtonState()) {
            case GAME: //go to game state
                // Add player's respective characters to datapacket to pass along to GameState.
                datapacket.setPlayerChars(PlayerNumber.PLAYER_ONE, playerOneChars);
                datapacket.setPlayerChars(PlayerNumber.PLAYER_TWO, playerTwoChars);

                System.out.println("GAME STATE CREATED");
                gsm.push(new GameState(gsm, datapacket));
                break;
        }
    }

    /**
     * Updates the current State. Function is ran once every frame.
     * @param dt delta time
     */
    @Override
    public void update(float dt) {
        handleInput();
        // Update btn states
        for (GameButton button : gamestartStateButtons) {
            button.updateBtnState(Gdx.input.getX(), Gdx.input.getY());
        }

    }

    /**
     * Renders the current State. Function is ran once every frame.
     * @param sb SpriteBatch
     * @param sr ShapeRenderer
     */
    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        background.draw(sb);
        sb.end();

        //draw rectangle
        playerOneUI.draw(sr);
        playerTwoUI.draw(sr);

        // Render sprites
        sb.begin();
        playerOneName.draw(sb);
        playerTwoName.draw(sb);

        // Draw all buttons in menu state
        for (GameButton button : gamestartStateButtons) {
            button.draw(sb);
        }

        // Draw all characters
        for (Character character : playerOneChars) {
            character.getSprite().draw(sb);
        }
        for (Character character : playerTwoChars) {
            character.getSprite().draw(sb);
        }

        // Draw all character labels
        for (GameText text : playerOneCharsLabels) {
            text.draw(sb);
        }
        for (GameText text : playerTwoCharsLabels) {
            text.draw(sb);
        }

        sb.end();
    }

    /**
     * Frees up memory by disposing sprites from SpriteBatch.
     */
    @Override
    public void dispose() {
        //dispose game text
        playerOneName.dispose();
        playerTwoName.dispose();

        // Dispose buttons
        for (GameButton button : gamestartStateButtons) {
            button.dispose();
        }
    }
}
