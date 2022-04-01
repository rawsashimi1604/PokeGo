package com.pokego.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pokego.datamodel.*;
import com.pokego.utility.Chatbot;
import com.pokego.utility.Utility;

import java.util.ArrayList;

/**
 * The HelpState contains a Chatbot object. It is used to read the instructions of the game, and get useful tips for the game.
 */
public class HelpState extends State {

    private static final float HELP_WIDGET_MARGIN = 100;

    private GameSprite background;

    private ArrayList<GameButton> helpStateButtons;
    private GameButton exitImg;
    private GameTextWidget outputWidget;

    private Stage stage;
    private GameInputField inputField;
    private GameText inputFieldLabel;

    private Chatbot chatbot;

    /**
     * Constructs the HelpState.
     * @param gsm The current GameStateManager used to manage all States.
     */
    protected HelpState(GameStateManager gsm) {
        super(gsm);
        System.out.println("created Help State");

        // Create Background
        background = new GameSprite("./maps/title_wallpaper.jpg");
        background.scale(1f);
        background.reposition(0, 0);

        // Create Buttons
        helpStateButtons = new ArrayList<GameButton>();

        exitImg = new GameButton("./ui/back_primary.png", "./ui/back_secondary.png", ButtonClass.BACK);
        exitImg.reposition(0, Gdx.graphics.getHeight() - exitImg.getHeight());
        helpStateButtons.add(exitImg);

        // Create Output Text widget
        outputWidget = new GameTextWidget(500, Gdx.graphics.getWidth() - (HELP_WIDGET_MARGIN * 2), true, 0.5f);
        outputWidget.reposition(HELP_WIDGET_MARGIN, 200);
        outputWidget.setText("Hello, I'm Pokie, your little assistant in Poke-go. \nHow can I help you today?\n\n-Help\n-Smalltalk");
        outputWidget.setTextScale(0.6f);

        // Create Stage (For input field)
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        inputField = new GameInputField(Gdx.graphics.getWidth() - (HELP_WIDGET_MARGIN * 2), 50);
        inputField.reposition(HELP_WIDGET_MARGIN, 100);
        inputField.addToStage(stage);

        // Create input field label
        inputFieldLabel = new GameText();
        inputFieldLabel.setText("Enter your help command: ");
        inputFieldLabel.scale(0.7f);
        inputFieldLabel.reposition(HELP_WIDGET_MARGIN, inputField.getY() + inputField.getHeight() + 25);

        // Create Chatbot
        chatbot = new Chatbot();
    }

    /**
     * Handle inputs in the current State.
     */
    @Override
    protected void handleInput() {
        // handle buttons
        for (GameButton button : helpStateButtons) {
            if (button.clicked(Gdx.input.getX(), Gdx.input.getY())) {
                System.out.println("clicked " + button.getButtonState());
                handleButtonClick(button);
            }
        }

        // handle input field
        if (Gdx.input.isKeyJustPressed((Input.Keys.ENTER))) {
            String response = chatbot.getResponse(inputField.getText());
            System.out.printf("response = %s\n", response);

            if(response == null) {
                outputWidget.setText("Could not find response, please check that you have entered the correct command.");
                System.out.println(System.getProperty("user.dir"));
            } else {
                // Reflect \n character in text widget.
                outputWidget.setText(response.replaceAll("\\\\n", System.lineSeparator()));
            }
        }
    }

    /**
     * Handle button clicks in the current State.
     * @param button Type of button to handle.
     */
    private void handleButtonClick(GameButton button) {
        switch (button.getButtonState()) {
            case BACK: //go back to menu
                System.out.println("BACK TO MENU");
                gsm.pop();
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
        outputWidget.updateTextScroll(dt);
        stage.act(dt);

        // Update btn states
        for (GameButton button : helpStateButtons) {
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

        // Draw stage
        stage.draw();

        // Draw widgets
        outputWidget.draw(sb, sr);

        // Render sprites
        sb.begin();
        // Draw all buttons in menu state
        for(GameButton button : helpStateButtons) {
            button.draw(sb);
        }
        inputFieldLabel.draw(sb);
        sb.end();
    }

    /**
     * Frees up memory by disposing sprites from SpriteBatch.
     */
    @Override
    public void dispose() {
        // Dispose all buttons in help state
        for (GameButton button : helpStateButtons) {
            button.dispose();
        }
        outputWidget.dispose();
        stage.dispose();
    }
}
