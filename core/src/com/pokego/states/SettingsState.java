package com.pokego.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.pokego.PokeGo;
import com.pokego.datamodel.GameButton;
import com.pokego.datamodel.GameCheckbox;
import com.pokego.datamodel.GameSprite;
import com.pokego.datamodel.GameText;
import com.pokego.utility.Utility;

import java.util.ArrayList;

/**
 * The SettingsState displays the settings menu. It contains 2 GameCheckBoxes to control the music volume and sound volume. The music and sound are controlled by the GameAudioManager.
 */
public class SettingsState extends State {

    private static final float SETTINGS_X_POS = 350;
    private static final float BOX_X_POS = 550;

    private GameText mainSettingsText, mainSettingsText1, mainSettingsText2;
    private GameSprite background;
    private ArrayList<GameButton> settingsStateButtons;
    private GameButton exitImg;
    private GameCheckbox musicCheckbox;
    private GameCheckbox soundCheckbox;

    /**
     * Constructs the SettingsState.
     * @param gsm The current GameStateManager used to manage all States.
     */
    protected SettingsState(GameStateManager gsm) {
        super(gsm);
        System.out.println("created Settings State");

        //background
        background = new GameSprite("./maps/title_wallpaper.jpg");
        background.scale(1f);
        background.reposition(0, 0);

        //text in screen
        mainSettingsText = new GameText();
        mainSettingsText.setText("SETTINGS");
        mainSettingsText.reposition(0, Gdx.graphics.getHeight() - 2*(mainSettingsText.getHeight()));

        //music on/off
        mainSettingsText1 = new GameText();
        mainSettingsText1.setText("Music:");
        mainSettingsText1.reposition(SETTINGS_X_POS, 500);

        //sound effects on/off
        mainSettingsText2 = new GameText();
        mainSettingsText2.setText("Sound FX:");
        mainSettingsText2.reposition(SETTINGS_X_POS, 350);

        // back button
        exitImg = new GameButton("./ui/back_primary.png", "./ui/back_secondary.png", ButtonClass.EXIT);
        exitImg.reposition(0, Gdx.graphics.getHeight() - exitImg.getHeight());
        settingsStateButtons = new ArrayList<GameButton>();
        settingsStateButtons.add(exitImg);

        // make music checkbox
        if (PokeGo.m1.getVolume() == 0){
            musicCheckbox = new GameCheckbox(ButtonClass.MUSIC_CHECKBOX,false);
        }
        else{
            musicCheckbox = new GameCheckbox(ButtonClass.MUSIC_CHECKBOX);
        }

        musicCheckbox.scale(3f);
        musicCheckbox.reposition(BOX_X_POS ,500 - mainSettingsText1.getHeight() - musicCheckbox.getHeight()/3);

        // make sound checkbox
        if (PokeGo.s1.getSoundVolume() == 0){
            soundCheckbox = new GameCheckbox(ButtonClass.SOUND_CHECKBOX, false);
            soundCheckbox.scale(3f);
            soundCheckbox.reposition(BOX_X_POS ,350 - mainSettingsText2.getHeight() - soundCheckbox.getHeight()/3);
        }
        else{
            soundCheckbox = new GameCheckbox(ButtonClass.SOUND_CHECKBOX);
            soundCheckbox.scale(3f);
            soundCheckbox.reposition(BOX_X_POS ,350 - mainSettingsText2.getHeight() - soundCheckbox.getHeight()/3);
        }
    }

    /**
     * Handle inputs in the current State.
     */
    @Override
    protected void handleInput() {
        for (GameButton button : settingsStateButtons) {
            if (button.clicked(Gdx.input.getX(), Gdx.input.getY())) {
                System.out.println("clicked " + button.getButtonState());
                handleButtonClick(button);
            }
        }

        if (musicCheckbox.clicked(Gdx.input.getX(), Gdx.input.getY())) {
            System.out.println("triggered");
            handleButtonClick(musicCheckbox);
        }

        if (soundCheckbox.clicked(Gdx.input.getX(), Gdx.input.getY())) {
            System.out.println("triggered");
            handleButtonClick(soundCheckbox);
        }

        if (Gdx.input.justTouched()) {
            Utility.printCursorLocation();
        }
    }

    /**
     * Handle button clicks in the current State.
     * @param button Type of button to handle.
     */
    private void handleButtonClick(GameButton button) {
        switch (button.getButtonState()) {
            case EXIT: //go back to menu
                System.out.println("BACK TO MENU");
                gsm.pop();
                break;
        }
    }

    /**
     * Handle check box clicks in the current State.
     * @param checkbox Checkbox to handle.
     */
    private void handleButtonClick(GameCheckbox checkbox) {
        switch (checkbox.getButtonState()) {
            case MUSIC_CHECKBOX:
                System.out.println("CLICKED music CHECKBOX");
                if (checkbox.isTicked()) {
                    PokeGo.m1.defaultVolume();
                } else {
                    PokeGo.m1.setVolume(0);
                }
                break;
            case SOUND_CHECKBOX:
                System.out.println("CLICKED sound CHECKBOX");
                if (checkbox.isTicked()) {
                    PokeGo.s1.setSoundVolume(0.1f);
                    System.out.println("Turn on sound");
                } else {
                    PokeGo.s1.setSoundVolume(0);
                    System.out.println("Mute sound");
                }
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
        for (GameButton button : settingsStateButtons) {
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
        mainSettingsText.draw(sb, Gdx.graphics.getWidth(), Align.center, false);
        mainSettingsText1.draw(sb);
        mainSettingsText2.draw(sb);

        // Draw all buttons in menu state
        for(GameButton button : settingsStateButtons) {
            button.draw(sb);
        }
        musicCheckbox.draw(sb);
        soundCheckbox.draw(sb);

        sb.end();
    }

    /**
     * Frees up memory by disposing sprites from SpriteBatch.
     */
    @Override
    public void dispose() {
        mainSettingsText.dispose();
        mainSettingsText1.dispose();
        mainSettingsText2.dispose();
        exitImg.dispose();
    }
}
