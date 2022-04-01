package com.pokego;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pokego.states.GameStateManager;
import com.pokego.states.TitleState;
import com.pokego.utility.GameAudioManager;
import com.pokego.utility.Utility;

/**
 * The main game class. Uses the GameStateManager to control States, GameAudioManager to control audio.
 */
public class PokeGo extends ApplicationAdapter {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 800;
	public static final String TITLE = "Poke Go";


	private SpriteBatch batch;
	private ShapeRenderer sr;
	private GameStateManager gsm;
	public static GameAudioManager m1, s1;

	/**
	 * Creates the initial configuration of the game. Function is run once when the game is launched.
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		gsm = new GameStateManager();

		//initialise sound to keep a global volume
		s1 = new GameAudioManager();


		// set initial looping music
		m1 = new GameAudioManager();
		m1.setMusic();
		m1.playMusic();

		// Clears color
		Gdx.gl.glClearColor(1, 0, 0, 1);

		gsm.push(new TitleState(gsm));
		System.out.println("created Title State\n");

		sr = new ShapeRenderer();
	}

	/**
	 * Updates and renders the current State in the GameStateManager. Function is run once every frame.
	 */
	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Sets screen back to black
		Utility.enableShapeRendererBlend();
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch, sr);
		Utility.disableShapeRendererBlend();
	}

	/**
	 * Dispose the SpriteBatch after closing the game.
	 */
	@Override
	public void dispose () {
		batch.dispose();
	}
}
