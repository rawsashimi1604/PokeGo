package com.pokego.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pokego.PokeGo;
import com.pokego.datamodel.GameSprite;
import com.pokego.datamodel.GameTextWidget;
import com.pokego.game.game_data.Character;
import com.pokego.game.game_data.Player;
import com.pokego.game.game_phase.GamePhaseManager;
import com.pokego.game.game_phase.PhasePacket;
import com.pokego.game.game_phase.PlayerWinner;
import com.pokego.game.game_phase.RoundStartPhase;
import com.pokego.game.game_ui.PlayerNumber;
import com.pokego.game.game_ui.PlayerUI;
import com.pokego.utility.Utility;

/**
 * The GameState is displayed after the GameStartState. It contains all logic of the game and a GamePhaseManager to manage the different Phases in each turn.
 */
public class GameState extends State {

    // Data Packet from previous state
    private DataPacket data;

    // UI Elements
    private GameSprite background;
    private PlayerUI playerOneUI;
    private PlayerUI playerTwoUI;
    private GameTextWidget textWidget;

    // GameData Elements
    private Player playerOne;
    private Player playerTwo;


    // Phase and round elements
    private GamePhaseManager gamePhaseManager;
    private PhasePacket phasePacket;

    public final static float TIME_BETWEEN_ROUNDS = 3f;
    private final static float TEXT_WIDGET_SCROLL = 1f;

    /**
     * Constructs the GameState.
     * @param gsm The current GameStateManager used to manage all States.
     * @param data The DataPacket to store and receive data.
     */
    protected GameState(GameStateManager gsm, DataPacket data) {
        super(gsm);
        System.out.println("Gamestate created.");

        // Create battle music
        PokeGo.m1.switchMusic("./audio/nautilus.wav");

        // Get DataPacket from previous state.
        this.data = data;

        // Generate player 1 data

        playerOne = new Player(data.getPlayerOneName(), data.getPlayerOneChars());
        // Reposition all sprites for player 1
        for(Character character : playerOne.getCharacters()) {
            character.getSprite().scale(1f);
            character.getSprite().reposition(100, 250);
        }
        playerOne.getCurrentCharacter().getSprite().scale(1f);
        playerOne.getCurrentCharacter().getSprite().reposition(100, 250);

        // Generate Player 2 data

        playerTwo = new Player(data.getPlayerTwoName(), data.getPlayerTwoChars());
        for(Character character : playerTwo.getCharacters()) {
            character.getSprite().scale(1f);
            character.getSprite().reposition(Gdx.graphics.getWidth() - 100 - playerTwo.getCurrentCharacter().getSprite().getWidth(), 250);
        }

        // UI
        background = new GameSprite("./maps/battle_bg1.jpg");

        playerOneUI = new PlayerUI(playerOne, PlayerNumber.PLAYER_ONE);
        playerOneUI.updateCharacterName(playerOne.getCurrentCharacter().getName());
        playerTwoUI = new PlayerUI(playerTwo, PlayerNumber.PLAYER_TWO);
        playerTwoUI.updateCharacterName(playerTwo.getCurrentCharacter().getName());

        // Text widget
        textWidget = new GameTextWidget(150, Gdx.graphics.getWidth(), true);
        textWidget.reposition(0, 0);
        textWidget.setScrollDuration(TEXT_WIDGET_SCROLL);
        textWidget.setTextScale(0.8f);
        textWidget.setText("Hello, this is the main text widget for GameState.");

        // Create GamePhaseManager -> handles game logic for respective phases
        gamePhaseManager = new GamePhaseManager();

        // Create PhasePacket for StartPhase to read
        phasePacket = new PhasePacket(playerOne, playerTwo, playerOneUI, playerTwoUI, textWidget);

        // After that, set phase to ROUND_START phase
        gamePhaseManager.setPhase(new RoundStartPhase(phasePacket, gamePhaseManager));
    }

    /**
     * Handle inputs in the current State.
     */
    @Override
    protected void handleInput() {

    }

    /**
     * Updates the current State. Function is ran once every frame.
     * @param dt delta time
     */
    @Override
    public void update(float dt) {
        handleInput();
        gamePhaseManager.update(dt); // Update GameState according to the current Phase.

        // If game is over... go to end game state.
        if (gamePhaseManager.isGameOver()) {
            // Get winner, push to endgame state.
            PhasePacket packet = gamePhaseManager.getPhasePacket();
            PlayerWinner winner = phasePacket.getWinner();
            System.out.println(phasePacket.getWinner());
            data.setWinner(winner);
            gsm.push(new EndGameState(gsm, data));
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
        gamePhaseManager.render(sb, sr); // Render GameState according to the current Phase.

        sb.begin();

        sb.end();

    }

    /**
     * Frees up memory by disposing sprites from SpriteBatch.
     */
    @Override
    public void dispose() {
        playerOneUI.dispose();
        playerTwoUI.dispose();
        background.dispose();
    }
}
