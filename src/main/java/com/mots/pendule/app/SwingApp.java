package com.mots.pendule.app;

import com.mots.pendule.core.GameEngine;
import com.mots.pendule.core.GameState;
import com.mots.pendule.core.GameStatus;
import com.mots.pendule.core.GuessResult;
import com.mots.pendule.data.DictionaryLoader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Simple Swing UI for the hangman game.
 */
public final class SwingApp {
    private final GameEngine engine = new GameEngine();
    private final Random random = new Random();
    private final List<String> dictionary;

    private GameState state;

    private final JLabel wordLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel guessedLabel = new JLabel("Guessed: -");
    private final JLabel errorsLabel = new JLabel("Errors left: -");
    private final JLabel statusLabel = new JLabel("Status: -");
    private final JLabel messageLabel = new JLabel("Message: -");
    private final HangmanPanel hangmanPanel = new HangmanPanel();
    private final JTextArea historyArea = new JTextArea(8, 24);
    private final JTextField inputField = new JTextField(5);
    private final JButton guessButton = new JButton("Guess");
    private final JButton soloButton = new JButton("New Solo Game");
    private final JButton twoPlayerButton = new JButton("New Two-Player Game");
    private final JSpinner maxErrorsSpinner = new JSpinner(new SpinnerNumberModel(7, 1, 26, 1));

    private SwingApp(List<String> dictionary) {
        this.dictionary = dictionary;
    }

    public static void launch() {
        List<String> words = new ArrayList<>();
        try {
            DictionaryLoader loader = new DictionaryLoader();
            words = loader.loadFromResource("dictionary.txt");
        } catch (IOException e) {
            // handled in UI init
        }
        List<String> finalWords = words;
        SwingUtilities.invokeLater(() -> new SwingApp(finalWords).show());
    }

    private void show() {
        trySetSystemLookAndFeel();

        JFrame frame = new JFrame("Mots Pendule - Hangman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setBackground(new Color(245, 248, 250));

        Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 26);
        Font labelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 13);
        wordLabel.setFont(titleFont);
        wordLabel.setText("Press a button to start");
        wordLabel.setForeground(new Color(33, 33, 33));

        hangmanPanel.setBackground(new Color(245, 248, 250));
        hangmanPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        historyArea.setEditable(false);
        historyArea.setLineWrap(true);
        historyArea.setWrapStyleWord(true);
        historyArea.setFont(labelFont);
        historyArea.setBackground(new Color(252, 253, 254));
        historyArea.setBorder(new EmptyBorder(6, 6, 6, 6));

        JPanel topPanel = new JPanel(new GridLayout(2, 2, 10, 8));
        topPanel.setBorder(new EmptyBorder(10, 12, 8, 12));
        topPanel.setBackground(new Color(245, 248, 250));

        JLabel maxErrorsLabel = new JLabel("Max errors:");
        maxErrorsLabel.setFont(labelFont);
        maxErrorsLabel.setForeground(new Color(60, 60, 60));

        topPanel.add(maxErrorsLabel);
        topPanel.add(maxErrorsSpinner);
        topPanel.add(soloButton);
        topPanel.add(twoPlayerButton);

        JPanel centerPanel = new JPanel(new BorderLayout(8, 8));
        centerPanel.setBorder(new EmptyBorder(6, 12, 6, 12));
        centerPanel.setBackground(new Color(245, 248, 250));
        centerPanel.add(wordLabel, BorderLayout.NORTH);
        centerPanel.add(hangmanPanel, BorderLayout.CENTER);
        JScrollPane historyScroll = new JScrollPane(historyArea);
        historyScroll.setBorder(new EmptyBorder(6, 6, 6, 6));
        centerPanel.add(historyScroll, BorderLayout.EAST);

        JPanel statusPanel = new JPanel(new GridLayout(4, 1));
        statusPanel.setBorder(new EmptyBorder(6, 12, 10, 12));
        statusPanel.setBackground(new Color(245, 248, 250));
        guessedLabel.setFont(labelFont);
        errorsLabel.setFont(labelFont);
        statusLabel.setFont(labelFont);
        messageLabel.setFont(labelFont);
        guessedLabel.setForeground(new Color(70, 70, 70));
        errorsLabel.setForeground(new Color(70, 70, 70));
        statusLabel.setForeground(new Color(70, 70, 70));
        messageLabel.setForeground(new Color(70, 70, 70));
        statusPanel.add(guessedLabel);
        statusPanel.add(errorsLabel);
        statusPanel.add(statusLabel);
        statusPanel.add(messageLabel);

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(new EmptyBorder(4, 12, 10, 12));
        inputPanel.setBackground(new Color(245, 248, 250));

        JLabel letterLabel = new JLabel("Letter:");
        letterLabel.setFont(labelFont);
        letterLabel.setForeground(new Color(60, 60, 60));
        inputPanel.add(letterLabel);
        inputPanel.add(inputField);
        inputPanel.add(guessButton);

        frame.getContentPane().setLayout(new BorderLayout(8, 8));
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
        frame.getContentPane().add(statusPanel, BorderLayout.SOUTH);
        frame.getContentPane().add(inputPanel, BorderLayout.PAGE_END);

        guessButton.addActionListener(e -> handleGuess());
        inputField.addActionListener(e -> handleGuess());
        soloButton.addActionListener(e -> startSoloGame());
        twoPlayerButton.addActionListener(e -> startTwoPlayerGame());

        styleControls();
        setGameActive(false);
        frame.setSize(620, 360);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void startSoloGame() {
        if (dictionary.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Dictionary not found or empty.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        int maxErrors = (Integer) maxErrorsSpinner.getValue();
        String word = dictionary.get(random.nextInt(dictionary.size()));
        state = engine.startNewGame(word, maxErrors);
        statusLabel.setText("Status: game started (solo)");
        messageLabel.setText("Message: good luck");
        historyArea.setText("");
        updateView();
        setGameActive(true);
    }

    private void startTwoPlayerGame() {
        String secret = promptSecretWord();
        if (secret == null) {
            return;
        }
        int maxErrors = (Integer) maxErrorsSpinner.getValue();
        state = engine.startNewGame(secret, maxErrors);
        statusLabel.setText("Status: game started (two players)");
        messageLabel.setText("Message: good luck");
        historyArea.setText("");
        updateView();
        setGameActive(true);
    }

    private String promptSecretWord() {
        while (true) {
            JPasswordField field = new JPasswordField();
            int option = JOptionPane.showConfirmDialog(null, field, "Enter secret word",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option != JOptionPane.OK_OPTION) {
                return null;
            }
            String value = new String(field.getPassword()).trim();
            if (value.isEmpty()) {
                continue;
            }
            if (value.chars().allMatch(Character::isLetter)) {
                return value;
            }
            JOptionPane.showMessageDialog(null, "Letters only.", "Invalid word",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleGuess() {
        if (state == null || state.getStatus() != GameStatus.IN_PROGRESS) {
            return;
        }
        String input = inputField.getText();
        inputField.setText("");
        GuessResult result = engine.guessLetter(state, input);
        statusLabel.setText("Status: " + result);
        messageLabel.setText("Message: " + messageForResult(result));
        appendHistory(input, result);
        updateView();

        if (state.getStatus() == GameStatus.WON) {
            JOptionPane.showMessageDialog(null, "You win! Word was: " + state.getSecretWord());
            setGameActive(false);
        } else if (state.getStatus() == GameStatus.LOST) {
            JOptionPane.showMessageDialog(null, "You lose. Word was: " + state.getSecretWord());
            setGameActive(false);
        }
    }

    private void updateView() {
        if (state == null) {
            wordLabel.setText("Press a button to start");
            guessedLabel.setText("Guessed: -");
            errorsLabel.setText("Errors left: -");
            hangmanPanel.setProgress(0, (Integer) maxErrorsSpinner.getValue());
            return;
        }
        wordLabel.setText(state.getMaskedWord());
        guessedLabel.setText("Guessed: " + formatGuesses());
        errorsLabel.setText("Errors left: " + state.getRemainingErrors());
        int maxErrors = (Integer) maxErrorsSpinner.getValue();
        int errorsMade = Math.max(0, maxErrors - state.getRemainingErrors());
        hangmanPanel.setProgress(errorsMade, maxErrors);
    }

    private String formatGuesses() {
        if (state.getGuessedLetters().isEmpty()) {
            return "-";
        }
        List<Character> letters = new ArrayList<>(state.getGuessedLetters());
        Collections.sort(letters);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < letters.size(); i++) {
            sb.append(letters.get(i));
            if (i < letters.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private void setGameActive(boolean active) {
        inputField.setEnabled(active);
        guessButton.setEnabled(active);
        inputField.requestFocusInWindow();
    }

    private void styleControls() {
        Font buttonFont = new Font(Font.SANS_SERIF, Font.BOLD, 12);

        styleButton(soloButton, new Color(127, 199, 217), new Color(98, 175, 196));
        styleButton(twoPlayerButton, new Color(182, 206, 168), new Color(153, 180, 137));
        styleButton(guessButton, new Color(244, 194, 147), new Color(230, 171, 120));

        soloButton.setFont(buttonFont);
        twoPlayerButton.setFont(buttonFont);
        guessButton.setFont(buttonFont);

        inputField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        inputField.setMargin(new Insets(4, 6, 4, 6));
        maxErrorsSpinner.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
    }

    private void styleButton(JButton button, Color base, Color hover) {
        button.setBackground(base);
        button.setForeground(new Color(33, 33, 33));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(6, 12, 6, 12));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(hover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(base);
            }
        });
    }

    private void trySetSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // fallback to default
        }
    }

    private void appendHistory(String input, GuessResult result) {
        String normalized = input == null ? "" : input.trim().toUpperCase();
        if (normalized.isEmpty()) {
            normalized = "(empty)";
        }
        historyArea.append("Guess: " + normalized + " -> " + result + System.lineSeparator());
    }

    private String messageForResult(GuessResult result) {
        switch (result) {
            case CORRECT:
                return "Yes! you guessed a letter.";
            case INCORRECT:
                return "Letter not here.";
            case ALREADY_GUESSED:
                return "Already guessed.";
            case INVALID_INPUT:
                return "Invalid input. Enter one letter.";
            case WON:
                return "You win!";
            case LOST:
                return "You lose.";
            default:
                return "-";
        }
    }

}
