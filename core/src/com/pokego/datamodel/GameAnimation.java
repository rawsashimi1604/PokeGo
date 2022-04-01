package com.pokego.datamodel;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * GameAnimation is a wrapper object used to render animations. Each animation consists of a TextureRegion (the image file that you wish to render), the frameCount (number of frames in the animation), and cycleTime (how long each frame is displayed in seconds). Animations are found in the "./assets/animations" folder.
 */
public class GameAnimation extends GameSpriteGroup {
    private Array<TextureRegion> frames;
    private float maxFrameTime; // Duration a frame stays in view before switching.
    private float currentFrameTime; // Duration that animation has been in current frame.
    private int frameCount; // Number of frames in animation.
    private int frame; // Current frame sprite is currently in.

    private float width;
    private float height;

    /**
     * Constructs the GameAnimation object with a (x, y) position.
     * @param x The x position to display animation.
     * @param y The y position to display animation.
     * @param region The TextureRegion to animate.
     * @param frameCount The number of frames in the animation.
     * @param cycleTime How long each frame is displayed in seconds.
     */
    public GameAnimation(int x, int y, TextureRegion region, int frameCount, float cycleTime) {
        super(x, y);
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;

        width = frames.get(0).getRegionWidth();
        height = frames.get(0).getRegionHeight();
    }

    /**
     * Constructs the GameAnimation object with a (0, 0) position.
     * @param region The TextureRegion to animate.
     * @param frameCount The number of frames in the animation.
     * @param cycleTime How long each frame is displayed in seconds.
     */
    public GameAnimation(TextureRegion region, int frameCount, float cycleTime) {
        this(0, 0, region, frameCount, cycleTime);
    }

    /**
     * Updates the current frame that is being displayed
     * @param dt Delta time
     */
    public void update(float dt) {
        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }

        if (frame >= frameCount) {
            frame = 0;
        }
    }

    /**
     * Gets the TextureRegion.
     * @return Returns animation TextureRegion.
     */
    public TextureRegion getFrame() {
        return frames.get(frame);
    }

    /**
     * Flips (inverts) all frames in the animation.
     */
    public void flip() {
        // Flip all texture regions
        for (TextureRegion textureRegion : frames) {
            textureRegion.flip(true, false);
        }
    }

    /**
     * Draw currently selected frame using LibGDX's engine. Displays animation sprites.
     * @param sb SpriteBatch
     */
    @Override
    public void draw(SpriteBatch sb) {
        // Draw currently selected frame
        sb.draw(getFrame(), getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Frees up memory by disposing texture regions from SpriteBatch.
     */
    @Override
    public void dispose() {
        // Dispose all texture regions
        for(TextureRegion textureRegion : frames) {
            textureRegion.getTexture().dispose();
        }
    }

    /**
     * Scales animation size, x and y scaled proportionately.
     * @param scale amount to scale
     */
    @Override
    public void scale(float scale) {
        width *= scale;
        height *= scale;
    }

    /**
     * Scales animation size, x and y scaled separately.
     * @param scaleX amount to scale (width)
     * @param scaleY amount to scale (height)
     */
    @Override
    public void scale(float scaleX, float scaleY) {
        width *= scaleX;
        height *= scaleY;
    }

    /**
     * Gets width of currently selected frame.
     * @return width of currently selected frame.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Gets height of currently selected frame.
     * @return height of currently selected frame.
     */
    public float getHeight() {
        return height;
    }
}
