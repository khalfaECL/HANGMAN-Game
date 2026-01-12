package com.mots.pendule.core;

/**
 * Core game engine: validates input and updates state.
 */
public final class GameEngine {

    /**
     * Start a new game.
     *
     * @param secretWord the word to guess (letters only)
     * @param maxErrors maximum errors allowed (>= 1)
     * @return initialized game state
     */
    public GameState startNewGame(String secretWord, int maxErrors) {
        if (secretWord == null || secretWord.trim().isEmpty()) {
            throw new IllegalArgumentException("Secret word must not be empty");
        }
        if (maxErrors < 1) {
            throw new IllegalArgumentException("Max errors must be >= 1");
        }
        String normalized = normalizeWord(secretWord);
        return new GameState(normalized, maxErrors);
    }

    /**
     * Guess a letter and update the state.
     *
     * @param state active game state
     * @param input user input
     * @return result of the attempt
     */
    public GuessResult guessLetter(GameState state, String input) {
        if (state == null) {
            throw new IllegalArgumentException("State must not be null");
        }
        if (state.getStatus() != GameStatus.IN_PROGRESS) {
            return state.getStatus() == GameStatus.WON ? GuessResult.WON : GuessResult.LOST;
        }

        if (input == null) {
            return GuessResult.INVALID_INPUT;
        }
        String trimmed = input.trim();
        if (trimmed.length() != 1) {
            return GuessResult.INVALID_INPUT;
        }
        char letter = trimmed.toUpperCase().charAt(0);
        if (!Character.isLetter(letter)) {
            return GuessResult.INVALID_INPUT;
        }
        if (state.hasGuessedLetter(letter)) {
            return GuessResult.ALREADY_GUESSED;
        }

        state.addGuessedLetter(letter);
        if (state.getSecretWord().indexOf(letter) >= 0) {
            if (isWordComplete(state)) {
                state.setStatus(GameStatus.WON);
                return GuessResult.WON;
            }
            return GuessResult.CORRECT;
        }

        state.decrementErrors();
        if (state.getRemainingErrors() <= 0) {
            state.setStatus(GameStatus.LOST);
            return GuessResult.LOST;
        }
        return GuessResult.INCORRECT;
    }

    private boolean isWordComplete(GameState state) {
        String word = state.getSecretWord();
        for (int i = 0; i < word.length(); i++) {
            if (!state.hasGuessedLetter(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private String normalizeWord(String word) {
        String trimmed = word.trim().toUpperCase();
        if (!trimmed.chars().allMatch(Character::isLetter)) {
            throw new IllegalArgumentException("Secret word must contain letters only");
        }
        return trimmed;
    }
}
