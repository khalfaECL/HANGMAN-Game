package com.mots.pendule.core;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Mutable game state for a single hangman session.
 */
public final class GameState {
    private final String secretWord;
    private final Set<Character> guessedLetters = new HashSet<>();
    private int remainingErrors;
    private GameStatus status;

    GameState(String secretWord, int maxErrors) {
        this.secretWord = secretWord;
        this.remainingErrors = maxErrors;
        this.status = GameStatus.IN_PROGRESS;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public Set<Character> getGuessedLetters() {
        return Collections.unmodifiableSet(guessedLetters);
    }

    public int getRemainingErrors() {
        return remainingErrors;
    }

    public GameStatus getStatus() {
        return status;
    }

    void setStatus(GameStatus status) {
        this.status = status;
    }

    void decrementErrors() {
        remainingErrors--;
    }

    void addGuessedLetter(char letter) {
        guessedLetters.add(letter);
    }

    boolean hasGuessedLetter(char letter) {
        return guessedLetters.contains(letter);
    }

    public String getMaskedWord() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < secretWord.length(); i++) {
            char c = secretWord.charAt(i);
            if (guessedLetters.contains(c)) {
                sb.append(c);
            } else {
                sb.append('_');
            }
            if (i < secretWord.length() - 1) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }
}
