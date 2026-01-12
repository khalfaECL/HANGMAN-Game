package com.mots.pendule.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameEngineTest {

    @Test
    public void correctGuessRevealsAllOccurrences() {
        GameEngine engine = new GameEngine();
        GameState state = engine.startNewGame("BALLOON", 5);

        GuessResult result = engine.guessLetter(state, "L");

        assertEquals(GuessResult.CORRECT, result);
        assertEquals("_ _ L L _ _ _", state.getMaskedWord());
    }

    @Test
    public void invalidInputRejected() {
        GameEngine engine = new GameEngine();
        GameState state = engine.startNewGame("JAVA", 5);

        GuessResult result = engine.guessLetter(state, "1");

        assertEquals(GuessResult.INVALID_INPUT, result);
        assertEquals(5, state.getRemainingErrors());
    }

    @Test
    public void repeatedGuessNotCounted() {
        GameEngine engine = new GameEngine();
        GameState state = engine.startNewGame("JAVA", 5);

        GuessResult first = engine.guessLetter(state, "J");
        GuessResult second = engine.guessLetter(state, "j");

        assertEquals(GuessResult.CORRECT, first);
        assertEquals(GuessResult.ALREADY_GUESSED, second);
        assertEquals(5, state.getRemainingErrors());
    }

    @Test
    public void winConditionDetected() {
        GameEngine engine = new GameEngine();
        GameState state = engine.startNewGame("HI", 5);

        engine.guessLetter(state, "H");
        GuessResult result = engine.guessLetter(state, "I");

        assertEquals(GuessResult.WON, result);
        assertEquals(GameStatus.WON, state.getStatus());
    }

    @Test
    public void loseConditionDetected() {
        GameEngine engine = new GameEngine();
        GameState state = engine.startNewGame("HI", 1);

        GuessResult result = engine.guessLetter(state, "Z");

        assertEquals(GuessResult.LOST, result);
        assertEquals(GameStatus.LOST, state.getStatus());
    }
}
