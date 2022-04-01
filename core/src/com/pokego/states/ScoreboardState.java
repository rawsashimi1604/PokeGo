package com.pokego.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.pokego.datamodel.GameButton;
import com.pokego.datamodel.GameSprite;
import com.pokego.datamodel.GameText;
import com.pokego.datamodel.GameTextWidget;
import com.pokego.utility.CSVHandler;
import com.pokego.utility.Utility;

import java.util.ArrayList;

/**
 * The ScoreboardState displays the scoreboard. The scoreboard is read from a .csv file found in "./assets/data". It uses the CSVHandler object to read the data in, then displays the data to a GameTextWidget.
 */
public class ScoreboardState extends State {

    private GameText mainScoreText;
    private GameSprite background;
    private ArrayList<GameButton> scoreStateButtons;
    private GameButton exitImg;
    private GameTextWidget scoreBoard;

    /**
     * Constructs the ScoreboardState.
     * @param gsm The current GameStateManager used to manage all States.
     */
    protected ScoreboardState(GameStateManager gsm) {
        super(gsm);
        System.out.println("created ScoreBoard State");

        // sort leaderboard
        CSVHandler.sortLeaderboard();

        // background
        background = new GameSprite("./maps/title_wallpaper.jpg");
        background.scale(1f);
        background.reposition(0, 0);

        // text in screen
        mainScoreText = new GameText();
        mainScoreText.setText("SCOREBOARD");
        mainScoreText.reposition(0, Gdx.graphics.getHeight() - 2*(mainScoreText.getHeight()));

        // back button
        exitImg = new GameButton("./ui/back_primary.png", "./ui/back_secondary.png", ButtonClass.EXIT);
        exitImg.reposition(0, Gdx.graphics.getHeight() - exitImg.getHeight());
        scoreStateButtons = new ArrayList<GameButton>();
        scoreStateButtons.add(exitImg);

        //Scoreboard
        scoreBoard = new GameTextWidget(600, 800, true, 0.5f);
        scoreBoard.reposition((Gdx.graphics.getWidth() - 800) / 2, (Gdx.graphics.getHeight() - 600) / 2);
        scoreBoard.setText(CSVHandler.readLeaderboard());
        scoreBoard.setTextScale(0.6f);
    }

    /**
     * Handle inputs in the current State.
     */
    @Override
    protected void handleInput() {
        for (GameButton button : scoreStateButtons) {
            if (button.clicked(Gdx.input.getX(), Gdx.input.getY())) {
                System.out.println("clicked " + button.getButtonState());
                handleButtonClick(button);
            }
        }
    }

    /**
     * Handle button clicks in the current State.
     * @param button Type of button to handle.
     */
    private void handleButtonClick(GameButton button) {
        switch (button.getButtonState()) {
            case EXIT: //go back to menu
                System.out.println("SCOREBOARD STATE CREATED");
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
        scoreBoard.updateTextScroll(dt);

        // Update btn states
        for (GameButton button : scoreStateButtons) {
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
        Utility.drawBackground(sb, background);

        scoreBoard.draw(sb, sr);

        sb.begin();
        mainScoreText.draw(sb, Gdx.graphics.getWidth(), Align.center, false);

        // Draw all buttons in menu state
        for(GameButton button : scoreStateButtons) {
            button.draw(sb);
        }

        sb.end();
    }

    /**
     * Frees up memory by disposing sprites from SpriteBatch.
     */
    @Override
    public void dispose() {
        mainScoreText.dispose();
        exitImg.dispose();
    }
}
