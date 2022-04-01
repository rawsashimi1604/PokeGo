package com.pokego.datamodel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pokego.PokeGo;
import com.pokego.states.ButtonClass;

/**
 * GameButton is used to represent a clickable button. It contains a hovered and static GameSprite. When the mouse is hovered over the button, it will display the hovered state. Else, it displays the static state.
 */
public class GameButton extends GameSprite{
    private ButtonClass BUTTON_Class_STATE;

    private boolean btnHovered;
    private GameSprite secondaryBtn;
    private boolean Enter = false;

    /**
     * Constructs the GameButton object at position (0,0) using the static filePath, hovered filePath and the type of button.
     * @param textureLocation file location of static button image file.
     * @param secondaryTextureLocation file location of hovered button image file.
     * @param state type of button, defined by the ButtonClass enum.
     */
    public GameButton(String textureLocation, String secondaryTextureLocation, ButtonClass state) {
        this(0 ,0, textureLocation, secondaryTextureLocation, state);
    }

    /**
     * Constructs the GameButton object at position (x,y) using the static filePath, hovered filePath and the type of button.
     * @param x The x position of the GameButton.
     * @param y The y position of the GameButton.
     * @param textureLocation file location of static button image file.
     * @param secondaryTextureLocation file location of hovered button image file.
     * @param state type of button, defined by the ButtonClass enum.
     */
    public GameButton(int x, int y, String textureLocation, String secondaryTextureLocation, ButtonClass state) {
        super(x, y, textureLocation);
        BUTTON_Class_STATE = state;
        this.secondaryBtn = new GameSprite(secondaryTextureLocation);
        this.btnHovered = false;
    }

    /**
     * Draw currently selected state using LibGDX's engine. Displays button sprite. (hovered or static)
     * @param sb SpriteBatch
     */
    @Override
    public void draw(SpriteBatch sb) {
        // If hovered draw secondary btn
        if (btnHovered) {
            sb.draw(secondaryBtn.getSprite(), getX(), getY(), getWidth(), getHeight());
        } else {
            // else draw primary btn
            super.draw(sb);
        }
    }

    /**
     * Updates current button state.
     * @param cursorX The current x position of mouse.
     * @param cursorY The current y position of mouse.
     */
    public void updateBtnState(float cursorX, float cursorY) {
        btnHovered = hovered(cursorX, cursorY);
    }

    /**
     * Returns true if button is currently hovered. False otherwise.
     * @param cursorX The current x position of mouse.
     * @param cursorY The current y position of mouse.
     * @return Returns true if button is currently hovered. False otherwise.
     */
    public boolean hovered(float cursorX, float cursorY) {
        // If hovered, change to the secondary_btn
        cursorY = Gdx.graphics.getHeight() - cursorY; // Flip y axis


        if(cursorX > getX() && cursorX < getX() + getWidth()) {
            if ((cursorY > getY()) && cursorY < (getY()+ getHeight())){
                if (Enter == false){
                    PokeGo.s1.setSound("./audio/hover.wav");
                    PokeGo.s1.playSound();
                    Enter = true;
                }
                return true;
            }
            else{
                Enter = false;
            }
        }
        else{
            Enter = false;
        }
        return false;
    }

    /**
     * Returns true if button is clicked. False otherwise.
     * @param cursorX The current x position of mouse.
     * @param cursorY The current y position of mouse.
     * @return Returns true if button is clicked. False otherwise.
     */
    public boolean clicked(float cursorX, float cursorY) {
        if (Gdx.input.justTouched()) {
            if (hovered(cursorX, cursorY)) {
                PokeGo.s1.setSound("./audio/click.wav");
                PokeGo.s1.playSound();
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the type of button the GameButton is.
     * @return Returns the type of button the GameButton is.
     */
    public ButtonClass getButtonState() {
        return BUTTON_Class_STATE;
    }
}
