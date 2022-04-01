package com.pokego.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.Random;


/**
 * The GameAudioManager manages all music and sound in the game.
 */
public class GameAudioManager {

    //Sets the default music and volume
    private final static String DEFAULT_AUDIO = "./audio/maple_island_cropped.wav";
    private final static float DEFAULT_VOLUME = 0.1f;

    //variable to store current Music settings
    private float musicVolume;
    private boolean musicPlaying;

    //variable to store current sound settings
    private float soundVolume = 0.2f;
    private boolean soundPlaying;

    private Music music;
    private Sound sound;

    /**
     * Sets the sound object
     * @param filePath File path to sound.
     */
    public void setSound(String filePath) {
        sound = Gdx.audio.newSound(Gdx.files.internal(filePath));
    }

    /**
     * Plays the currently selected sound object.
     */
    public void playSound(){
        long id = sound.play();
        sound.setVolume(id,soundVolume);
    }

    /**
     * Gets the currently selected sound object's volume.
     * @return
     */
    public double getSoundVolume(){
        return this.soundVolume;
    }

    /**
     * Dispose sound object.
     */
    public void removeSound(){
        sound.dispose();
    }

    /**
     * Toggle sound to play.
     */
    public void playVoice(){
        System.out.println(this.soundVolume);
        Random r = new Random();
        float min = 0.8f, max = 1f;
        //random to set the pitch for the sound.
        float random = min + r.nextFloat() * (max - min);
        long id = sound.play();
        sound.setPitch(id,random);
        sound.setVolume(id,this.soundVolume);
    }

    /**
     * Set the sound volume.
     * @param soundVolume Volume of sound.
     */
    public void setSoundVolume(float soundVolume){
        this.soundVolume = soundVolume;
    }

    /**
     * Sets the looping music to play.
     */
    public void setMusic(){

        music = Gdx.audio.newMusic(Gdx.files.internal(DEFAULT_AUDIO));
        music.setLooping(true);
        music.setVolume(DEFAULT_VOLUME);
        this.musicVolume = DEFAULT_VOLUME;
    }

    /**
     * Play the music object.
     */
    public void playMusic(){
        music.play();
    }

    /**
     * Gets the current music object's volume.
     * @return Current music object's volume.
     */
    public float getVolume(){
        this.musicVolume = music.getVolume();
        return this.musicVolume;
    }

    /**
     * Sets the music object's volume to the default volume.
     */
    public void defaultVolume(){
        music.setVolume(DEFAULT_VOLUME);
        this.musicVolume = DEFAULT_VOLUME;

    }

    /**
     * Sets the volume of the music object.
     * @param volume Volume to be set to the music object.
     */
    public void setVolume(float volume){
        music.setVolume(volume);
        this.musicVolume = volume;
    }

    /**
     * Switches the music by disposing the previous music object.
     * @param filePath File path to the new music file.
     */
    public void switchMusic(String filePath){
        music.dispose();
        music = Gdx.audio.newMusic(Gdx.files.internal(filePath));
        music.setLooping(true);
        music.setVolume(this.musicVolume);

        //If you want more control after switching music can activate the music manually.
        music.play();
    }

    /**
     * Returns true if music is currently playing. False otherwise.
     * @return Returns true if music is currently playing. False otherwise.
     */
    public boolean musicPlaying(){
        this.musicPlaying = music.isPlaying();
        return this.musicPlaying;
    }
}