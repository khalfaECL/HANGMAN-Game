package com.mots.pendule.app;

import com.mots.pendule.core.GameEngine;
import com.mots.pendule.core.GameState;
import com.mots.pendule.core.GameStatus;
import com.mots.pendule.core.GuessResult;
import com.mots.pendule.data.DictionaryLoader;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * CLI entry point for the hangman game.
 */
public final class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Mots Pendule - Hangman");

        int maxErrors = readInt(scanner, "Max errors [7]: ", 7);
        int mode = readInt(scanner, "Mode (1=solo, 2=two players) [1]: ", 1);

        String secretWord;
        if (mode == 2) {
            secretWord = readSecretWord(scanner);
        } else {
            DictionaryLoader loader = new DictionaryLoader();
            List<String> words = loader.loadFromResource("dictionary.txt");
            secretWord = loader.pickRandomWord(words, new Random());
        }

        GameEngine engine = new GameEngine();
        GameState state = engine.startNewGame(secretWord, maxErrors);

        while (state.getStatus() == GameStatus.IN_PROGRESS) {
            System.out.println("Word: " + state.getMaskedWord());
            System.out.println("Guessed: " + state.getGuessedLetters());
            System.out.println("Errors left: " + state.getRemainingErrors());

            System.out.print("Enter a letter: ");
            String input = scanner.nextLine();
            GuessResult result = engine.guessLetter(state, input);
            System.out.println("Result: " + result);
            System.out.println();
        }

        if (state.getStatus() == GameStatus.WON) {
            System.out.println("You win! Word was: " + state.getSecretWord());
        } else {
            System.out.println("You lose. Word was: " + state.getSecretWord());
        }
    }

    private static int readInt(Scanner scanner, String prompt, int defaultValue) {
        System.out.print(prompt);
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private static String readSecretWord(Scanner scanner) {
        while (true) {
            System.out.print("Enter secret word: ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.chars().allMatch(Character::isLetter)) {
                return line;
            }
            System.out.println("Invalid word. Letters only.");
        }
    }
}
