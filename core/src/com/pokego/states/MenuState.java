package com.pokego.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pokego.datamodel.GameButton;
import com.pokego.datamodel.GameSprite;
import com.pokego.utility.Utility;

import java.util.ArrayList;

/**
 * The MenuState displays the main menu of the game. It contains a collection of GameButtons to navigate to various sections of the game.
 */
public class MenuState extends State {

    private ArrayList<GameButton> menuStateButtons;
    private GameSprite background;
    private GameSprite logo;

    public final static float MENU_BUTTON_GAP = 60;
    public final static float MENU_BUTTON_Y_LOC = 300;

    /**
     * Constructs the MenuState.
     * @param gsm The current GameStateManager used to manage all States.
     */
    protected MenuState(GameStateManager gsm) {
        super(gsm);
        System.out.println("created Menu State");

        background = new GameSprite("./maps/title_wallpaper.jpg");
        background.scale(1f);
        background.reposition(0, 0);

        // logo
        logo = new GameSprite("./ui/logo.png");
        logo.scale(1f);
        logo.reposition(Gdx.graphics.getWidth()/2 - logo.getWidth()/2 + 50, 380);

        // Use array of buttons to dynamically draw buttons on screen.
        menuStateButtons = new ArrayList<GameButton>();
        menuStateButtons.add(new GameButton("./ui/start_primary.png", "./ui/start_secondary.png", ButtonClass.PRE_GAME));
        menuStateButtons.add(new GameButton("./ui/help_primary.png", "./ui/help_secondary.png", ButtonClass.HELP));
        menuStateButtons.add(new GameButton("./ui/scoreboard_primary.png", "./ui/scoreboard_secondary.png", ButtonClass.SCOREBOARD));
        menuStateButtons.add(new GameButton("./ui/settings_primary.png", "./ui/settings_secondary.png", ButtonClass.SETTINGS));
        menuStateButtons.add(new GameButton("./ui/exit_primary.png", "./ui/exit_secondary.png", ButtonClass.EXIT));

        float buttonPos = 0;
        for(GameButton menuButton : menuStateButtons) {
            menuButton.scale(1.25f);
            menuButton.reposition(((Gdx.graphics.getWidth()/2) - menuButton.getWidth()/2), (MENU_BUTTON_Y_LOC - buttonPos));
            buttonPos += MENU_BUTTON_GAP;
            System.out.println(menuButton.getButtonState() + " : (" + menuButton.getX() + ", " + menuButton.getY() + ")");
        }
    }

    /**
     * Handle inputs in the current State.
     */
    @Override
    protected void handleInput() {
        for (GameButton button : menuStateButtons) {
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
            case PRE_GAME:
                System.out.println("PREGAME STATE CREATED!");
                gsm.push(new PreGameState(gsm));
                break;
            case HELP:
                System.out.println("HELP STATE CREATED!");
                gsm.push(new HelpState(gsm));
                break;
            case SCOREBOARD:
                System.out.println("SCOREBOARD STATE CREATED!");
                gsm.push(new ScoreboardState(gsm));
                break;
            case SETTINGS:
                System.out.println("SETTINGS STATE CREATED!");
                gsm.push(new SettingsState(gsm));
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
        for (GameButton button : menuStateButtons) {
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
        logo.draw(sb);
        // Draw all buttons in menu state
        for(GameButton button : menuStateButtons) {
            button.draw(sb);
        }

        sb.end();
    }

    /**
     * Frees up memory by disposing sprites from SpriteBatch.
     */
    @Override
    public void dispose() {
        background.dispose();
        logo.dispose();

        // Dispose all buttons in menu state
        for (GameButton button : menuStateButtons) {
            button.dispose();
        }
    }
}
