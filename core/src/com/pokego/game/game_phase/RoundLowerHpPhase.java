package com.pokego.game.game_phase;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pokego.game.game_data.Character;
import com.pokego.game.game_data.Move;
import com.pokego.game.game_data.MoveClass;
import com.pokego.game.game_data.Type;

/**
 * The Phase which lowers the HP of each Character, after calculating the amount of damage to be received for the current turn.
 */
public class RoundLowerHpPhase extends Phase {

    /**
     * Constructs the RoundLowerHpPhase object.
     * @param phasePacket PhasePacket to transfer data between Phases.
     * @param gpm The current GamePhaseManager being used in GameState.
     */
    public RoundLowerHpPhase(PhasePacket phasePacket, GamePhaseManager gpm) {
        super(phasePacket, gpm);

        phaseName = GamePhase.ROUND_LOWER_HP;

        // Extract data from Phase Packet
        extractPhasePacket(phasePacket);

        System.out.print("Round Lower HP phase.");

        // Calculate damage received for each character
        int playerOneDamageReceived =  calculateDamageReceived(playerOne.getCurrentCharacter(), playerTwo.getCurrentCharacter(), playerTwoMove, playerOneMove);
        int playerTwoDamageReceived =  calculateDamageReceived(playerTwo.getCurrentCharacter(), playerOne.getCurrentCharacter(), playerOneMove, playerTwoMove);

        // Change HP values for each character
        playerOne.getCurrentCharacter().decreaseHealthPoints(playerOneDamageReceived);
        playerTwo.getCurrentCharacter().decreaseHealthPoints(playerTwoDamageReceived);

        // Update UIs for corresponding health.
        playerOneUI.updateHealth(playerOne.getCurrentCharacter().getHealthPoints());
        playerTwoUI.updateHealth(playerTwo.getCurrentCharacter().getHealthPoints());

        // Set buffer time for Phase,
        bufferTime = 0.5f;
    }

    /**
     * Updates logic defined in Phase, function is ran once every frame.
     * @param dt delta time
     */
    @Override
    public void update(float dt) {
        // Slowly lower hp based on time...

        timeElapsed += dt;
        // Only update text widget if it hasn't finished scrolling through all text.
        if (!textWidget.displayedFinish() && timeElapsed < textWidget.getScrollDuration()) {
            textWidget.updateTextScroll(dt);
        } else if (timeElapsed > textWidget.getScrollDuration() + bufferTime) {
            // Go to new state after textWidget scrolls finish and after accounting for BUFFER_TIME.

            // Load RoundStartPhase if both characters are still alive, else load DisplayFaintedPhase
            if (playerOne.getCurrentCharacter().isAlive() && playerTwo.getCurrentCharacter().isAlive()) {

                gpm.setPhase(new RoundStartPhase(phasePacket, gpm));
            } else {
                gpm.setPhase(new RoundDisplayFaintedPhase(phasePacket, gpm));
            }

        }

    }

    /**
     * Renders sprites and shapes, function is ran once every frame.
     * @param sb SpriteBatch
     * @param sr ShapeRenderer
     */
    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {
        playerOneUI.draw(sb, sr);
        playerTwoUI.draw(sb, sr);
        textWidget.draw(sb, sr);

        sb.begin();
        playerOne.getCurrentCharacter().getSprite().draw(sb);
        playerTwo.getCurrentCharacter().getSprite().draw(sb);
        sb.end();
    }

    /**
     * Frees up memory by disposing sprites from SpriteBatch.
     */
    @Override
    public void dispose() {

    }

    /**
     * Calculate damage to be received by Character after accounting for their Type, Move used, Move received, and opponent's Character Type.
     * @param characterReceiving Character receiving damage.
     * @param characterUsing Opponent Character.
     * @param moveReceived Move received by opponent Character.
     * @param moveUsed Move used by the Character receiving damage.
     * @return Returns damage taken by Character.
     */
    private int calculateDamageReceived(Character characterReceiving, Character characterUsing, Move moveReceived, Move moveUsed) {
        int damage = 0;
        // if received no move
        if (moveReceived == null) {
            return damage;
        }

        // received defense move
        if (moveReceived.getMoveClass() == MoveClass.DEFENSE) {
            return damage;
        }

        // received attack move and used attack move, or received attack move and used no moves
        if ((moveReceived.getMoveClass() == MoveClass.ATTACK && moveUsed == null) || (moveReceived.getMoveClass() == MoveClass.ATTACK && moveUsed.getMoveClass() == MoveClass.ATTACK)) {
            damage = moveReceived.getBaseDamage();
            // Calculate base damage if STAB (Same Type Attack Bonus)
            if (moveReceived.getMoveType() == characterUsing.getCharacterType()) {
                damage *= 1.5;
            }

            // Calculate move effective damage
            if (moveReceived.getMoveType() == Type.FIRE && characterReceiving.getCharacterType() == Type.GRASS) {
                damage *= 2;
            } else if (moveReceived.getMoveType() == Type.GRASS && characterReceiving.getCharacterType() == Type.WATER) {
                damage *= 2;
            } else if (moveReceived.getMoveType() == Type.WATER && characterReceiving.getCharacterType() == Type.FIRE) {
                damage *= 2;
            }

            // Calculate move ineffective damage
            if (moveReceived.getMoveType() == Type.FIRE && characterReceiving.getCharacterType() == Type.WATER) {
                damage /= 2;
            } else if (moveReceived.getMoveType() == Type.GRASS && characterReceiving.getCharacterType() == Type.FIRE) {
                damage /= 2;
            } else if (moveReceived.getMoveType() == Type.WATER && characterReceiving.getCharacterType() == Type.GRASS) {
                damage /= 2;
            }
            return damage;
        }

        // received attack move and used defense move
        if (moveReceived.getMoveClass() == MoveClass.ATTACK && moveUsed.getMoveClass() == MoveClass.DEFENSE) {
            damage = moveReceived.getBaseDamage();
            // Calculate base damage if STAB (Same Type Attack Bonus)
            if (moveReceived.getMoveType() == characterUsing.getCharacterType()) {
                damage *= 1.5;
            }

            // Calculate move effective damage
            if (moveReceived.getMoveType() == Type.FIRE && characterReceiving.getCharacterType() == Type.GRASS) {
                damage *= 2;
            } else if (moveReceived.getMoveType() == Type.GRASS && characterReceiving.getCharacterType() == Type.WATER) {
                damage *= 2;
            } else if (moveReceived.getMoveType() == Type.WATER && characterReceiving.getCharacterType() == Type.FIRE) {
                damage *= 2;
            }

            // Calculate move ineffective damage
            if (moveReceived.getMoveType() == Type.FIRE && characterReceiving.getCharacterType() == Type.WATER) {
                damage /= 2;
            } else if (moveReceived.getMoveType() == Type.GRASS && characterReceiving.getCharacterType() == Type.FIRE) {
                damage /= 2;
            } else if (moveReceived.getMoveType() == Type.WATER && characterReceiving.getCharacterType() == Type.GRASS) {
                damage /= 2;
            }

            // deduct damage after damageBlocked
            int damageBlocked = moveUsed.getDamageBlocked();
            damage -= damageBlocked;
            return Math.max(0, damage);
        }

        return damage;
    }
}
