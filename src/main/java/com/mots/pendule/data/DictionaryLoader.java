package com.mots.pendule.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Load and validate dictionary words.
 */
public final class DictionaryLoader {

    public List<String> loadFromResource(String resourceName) throws IOException {
        InputStream input = DictionaryLoader.class.getClassLoader().getResourceAsStream(resourceName);
        if (input == null) {
            throw new IOException("Dictionary resource not found: " + resourceName);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            return readLines(reader);
        }
    }

    public List<String> loadFromFile(Path path) throws IOException {
        if (!Files.exists(path)) {
            throw new IOException("Dictionary file not found: " + path);
        }
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return readLines(reader);
        }
    }

    public String pickRandomWord(List<String> words, Random random) {
        if (words == null || words.isEmpty()) {
            throw new IllegalArgumentException("Dictionary is empty");
        }
        int index = random.nextInt(words.size());
        return words.get(index);
    }

    private List<String> readLines(BufferedReader reader) throws IOException {
        List<String> words = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            String normalized = normalizeWord(line);
            if (!normalized.isEmpty()) {
                words.add(normalized);
            }
        }
        if (words.isEmpty()) {
            throw new IOException("Dictionary contains no valid words");
        }
        return words;
    }

    private String normalizeWord(String line) {
        if (line == null) {
            return "";
        }
        String trimmed = line.trim().toUpperCase();
        if (trimmed.isEmpty()) {
            return "";
        }
        if (!trimmed.chars().allMatch(Character::isLetter)) {
            return "";
        }
        return trimmed;
    }
}
