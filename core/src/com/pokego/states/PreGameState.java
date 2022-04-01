package com.pokego.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.pokego.datamodel.GameButton;
import com.pokego.datamodel.GameInputField;
import com.pokego.datamodel.GameSprite;
import com.pokego.datamodel.GameText;
import com.pokego.errors.Error;
import com.pokego.errors.GameErrorWidget;
import com.pokego.utility.Utility;

import java.util.ArrayList;

/**
 * The PreGameState displays the game start page. It contains 2 GameInputBoxes to input each Player's name. Then, it creates a DataPacket used to send data across different States.
 */
public class PreGameState extends State {

    private Stage stage;
    private GameInputField inputField1, inputField2;
    private GameText inputFieldLabel1, inputFieldLabel2;

    private GameText mainPreGameText;
    private GameSprite background;
    private ArrayList<GameButton> pregameStateButtons;

    private GameButton exitImg, startImg;

    private DataPacket dataPacket;

    private static final float INPUT_FIELD_MARGIN = 100;
    private static final float SPACE_BETWEEN_INPUT = 80;
    private static final float INPUTFIELD1_Y_POS = 450;
    private static final float INPUTFIELD2_Y_POS = 250;

    private GameErrorWidget gameErrorWidget;
    private static final int MAX_CHARS_INPUT = 25;

    /**
     * Constructs the PreGameState.
     * @param gsm The current GameStateManager used to manage all States.
     */
    protected PreGameState(GameStateManager gsm) {
        super(gsm);
        System.out.println("created PreGame State");

        // background
        background = new GameSprite("./maps/title_wallpaper.jpg");
        background.scale(1f);
        background.reposition(0, 0);

        //prompt to enter name
        mainPreGameText = new GameText();
        mainPreGameText.setText("Enter Your Name");
        mainPreGameText.reposition(0, Gdx.graphics.getHeight() - 2*(mainPreGameText.getHeight()));

        // Buttons
        pregameStateButtons = new ArrayList<GameButton>();
        exitImg = new GameButton("./ui/back_primary.png", "./ui/back_secondary.png", ButtonClass.BACK);
        startImg = new GameButton("./ui/start_primary.png", "./ui/start_secondary.png", ButtonClass.GAME_START);
        exitImg.reposition(0,Gdx.graphics.getHeight() - exitImg.getHeight());
        startImg.scale(1.25f);
        startImg.reposition(Gdx.graphics.getWidth()/2 - startImg.getWidth()/2,50);
        pregameStateButtons.add(exitImg);
        pregameStateButtons.add(startImg);

        // Input for Player 1
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        inputField1 = new GameInputField(Gdx.graphics.getWidth() - (INPUT_FIELD_MARGIN * 2), 50);
        inputField1.reposition(INPUT_FIELD_MARGIN, INPUTFIELD1_Y_POS);
        inputField1.addToStage(stage);

        // Input label for Player 1
        inputFieldLabel1 = new GameText();
        inputFieldLabel1.setText("Player 1: ");
        inputFieldLabel1.scale(1f);
        inputFieldLabel1.reposition(INPUT_FIELD_MARGIN, INPUTFIELD1_Y_POS + SPACE_BETWEEN_INPUT);

        // Input for Player 2
        inputField2 = new GameInputField(Gdx.graphics.getWidth() - (INPUT_FIELD_MARGIN * 2), 50);
        inputField2.reposition(INPUT_FIELD_MARGIN, INPUTFIELD2_Y_POS);
        inputField2.addToStage(stage);

        // Input label for Player 2
        inputFieldLabel2 = new GameText();
        inputFieldLabel2.setText("Player 2: ");
        inputFieldLabel2.scale(1f);
        inputFieldLabel2.reposition(INPUT_FIELD_MARGIN, INPUTFIELD2_Y_POS + SPACE_BETWEEN_INPUT);

    }

    /**
     * Handle inputs in the current State.
     */
    @Override
    protected void handleInput() {
        // Handle error hiding if game error is showing
        if (gameErrorWidget != null) {
            gameErrorWidget.hideError();
        }

        // Handle button clicks
        for (GameButton button : pregameStateButtons) {
            if (button.clicked(Gdx.input.getX(), Gdx.input.getY())) {
                System.out.println("clicked " + button.getButtonState());
                handleButtonClick(button);
            }
        }

        if (Gdx.input.isKeyJustPressed((Input.Keys.ENTER))) {
            dataPacket = new DataPacket(inputField1.getText(), inputField2.getText());
            System.out.println("GAMESTART STATE CREATED");
            gsm.push(new GameStartState(gsm, dataPacket));
            return;
        }
    }

    /**
     * Handle button clicks in the current State.
     * @param button Type of button to handle.
     */
    private void handleButtonClick(GameButton button) {
        switch (button.getButtonState()) {
            case BACK: //go back to menu
                System.out.println("MENU STATE CREATED");
                gsm.pop();
                break;
            case GAME_START: //go to game start state
                // Check errors
                if (inputField1.getText().length() > MAX_CHARS_INPUT || inputField2.getText().length() > MAX_CHARS_INPUT) {
                    System.out.println("ERROR CREATED!");
                    gameErrorWidget = new GameErrorWidget(Error.LONG_NAME);

                    return;
                }

                if (inputField1.getText().length() == 0 || inputField2.getText().length() == 0) {
                    System.out.println("ERROR CREATED!");
                    gameErrorWidget = new GameErrorWidget(Error.FIELD_EMPTY);

                    return;
                }

                if (inputField1.getText().equals(inputField2.getText())){
                    System.out.println("ERROR CREATED!");
                    gameErrorWidget = new GameErrorWidget(Error.FIELD_SAME);
                    return;
                }
                // if input has no errors
                dataPacket = new DataPacket(inputField1.getText(), inputField2.getText());

                System.out.println("GAMESTART STATE CREATED");
                gsm.push(new GameStartState(gsm, dataPacket));

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
        stage.act(dt);

        // Update btn states
        for (GameButton button : pregameStateButtons) {
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
        // Draw background
        Utility.drawBackground(sb, background);

        // draw stage (for input fields)
        stage.draw();

        // Render sprites
        sb.begin();
        mainPreGameText.draw(sb, Gdx.graphics.getWidth(), Align.center, false);

        // Draw all buttons in menu state
        for(GameButton button : pregameStateButtons) {
            button.draw(sb);
        }
        inputFieldLabel1.draw(sb);
        inputFieldLabel2.draw(sb);
        sb.end();

        // Draw error widgets if they exist
        if (gameErrorWidget != null) {
            gameErrorWidget.draw(sb, sr);
        }

    }

    /**
     * Frees up memory by disposing sprites from SpriteBatch.
     */
    @Override
    public void dispose() {
        mainPreGameText.dispose();

        // Dispose error widget
        if (gameErrorWidget != null) {
            gameErrorWidget.dispose();
        }

        // Dispose buttons
        for (GameButton button : pregameStateButtons) {
            button.dispose();
        }

        stage.dispose();
    }
}
