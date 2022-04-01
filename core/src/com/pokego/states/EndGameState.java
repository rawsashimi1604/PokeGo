package com.pokego.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.pokego.PokeGo;
import com.pokego.datamodel.GameButton;
import com.pokego.datamodel.GameSprite;
import com.pokego.datamodel.GameText;
import com.pokego.game.game_phase.PlayerWinner;
import com.pokego.utility.CSVHandler;
import com.pokego.utility.Font;
import com.pokego.utility.Utility;

import java.util.ArrayList;

/**
 * The EndGameState is displayed at the end of the game. It displays the winner of the game, and updates the scoreboard using the CSVHandler object. If a draw result is derived, the scoreboard will not be updated.
 */
public class EndGameState extends State {

    private ArrayList<GameButton> endgameStateButtons;
    private GameSprite background;
    private DataPacket datapacket;
    private GameText winner;

    public final static float ENDGAME_BUTTON_GAP = 60;
    public final static float ENDGAME_BUTTON_Y_LOC = 200;

    /**
     * Constructs the EndGameState.
     * @param gsm The current GameStateManager used to manage all States.
     * @param data The DataPacket to store and receive data.
     */
    protected EndGameState(GameStateManager gsm, DataPacket data) {
        super(gsm);
        System.out.println("created GameEnd State");

        // Switch music back to menu music
        PokeGo.m1.switchMusic("./audio/henesys.wav");

        datapacket = data;

        // background
        background = new GameSprite("./maps/title_wallpaper.jpg");
        background.scale(1f);
        background.reposition(0, 0);

        // Winner Output
        winner = new GameText(Font.LUCIDA_SANS);
        String winnerName;
        if (datapacket.getWinner() == PlayerWinner.PLAYER_ONE) {
            winnerName = data.getPlayerOneName();
            CSVHandler.insertIntoLeaderboard(datapacket.getPlayerOneName(), datapacket.getPlayerTwoName());
        } else if (datapacket.getWinner() == PlayerWinner.PLAYER_TWO) {
            winnerName = data.getPlayerTwoName();
            CSVHandler.insertIntoLeaderboard(datapacket.getPlayerTwoName(), datapacket.getPlayerOneName());
        } else {
            // If draw, don't update and rematch
            winnerName = "DRAW, please play again.";
        }

        winner.setText(String.format("WINNER: %s", winnerName));
        winner.scale(1.5f);
        winner.reposition(0, 600);

        //buttons
        endgameStateButtons = new ArrayList<GameButton>();
        endgameStateButtons.add(new GameButton("./ui/start_primary.png", "./ui/start_secondary.png", ButtonClass.BACK));
        endgameStateButtons.add(new GameButton("./ui/exit_primary.png", "./ui/exit_secondary.png", ButtonClass.EXIT));
        float buttonPos = 0;
        for(GameButton endgameButton : endgameStateButtons) {
            endgameButton.scale(1.25f);
            endgameButton.reposition(Gdx.graphics.getWidth()/2 - endgameButton.getWidth()/2, ENDGAME_BUTTON_Y_LOC - buttonPos );
            buttonPos += ENDGAME_BUTTON_GAP;
            System.out.println(endgameButton.getButtonState() + " : (" + endgameButton.getX() + ", " + endgameButton.getY() + ")");
        }



    }

    /**
     * Handle inputs in the current State.
     */
    @Override
    protected void handleInput() {
        for (GameButton button : endgameStateButtons) {
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
        switch(button.getButtonState()) {
            case BACK:
                System.out.println("PREGAME STATE CREATED!");

                // pop stack 4 times to go to menu state
                gsm.pop();
                gsm.pop();
                gsm.pop();
                gsm.pop();

                // Switch music back to menu
                PokeGo.m1.switchMusic("./audio/maple_island_cropped.wav");

                break;
            case EXIT:
                System.out.println("EXIT GAME!");
                Gdx.app.exit();
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
        for (GameButton button : endgameStateButtons) {
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

        sb.begin();
        // Draw all buttons in end game state
        for(GameButton button : endgameStateButtons) {
            button.draw(sb);
        }
        winner.draw(sb, Gdx.graphics.getWidth(), Align.center, true);

        sb.end();
    }

    /**
     * Frees up memory by disposing sprites from SpriteBatch.
     */
    @Override
    public void dispose() {
        background.dispose();
        winner.dispose();
        // Dispose all buttons in menu state
        for (GameButton button : endgameStateButtons) {
            button.dispose();
        }
    }
}