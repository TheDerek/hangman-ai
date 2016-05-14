package tech.derek.hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Solver
{
    public static List<String> words = getWordsFromFile(new File("assets/words.txt"));



    public static char guess(String template)
    {
        return guess(template, "");
    }

    public static char guess(String template, String guessed)
    {
        // Get the length the word we need to find
        int length = template.length();

        // Initialise our letter frequency table
        Map<Character, Integer> charFrequencies = new HashMap<>();

        // Filter our word list to only words that could match the template
        List<String> trimmedWords = words.stream()
            // Only get words that are the same length as the template
            .filter(w -> w.length() == length)

            // Only get words that contain characters in the same position
            // as the template
            .filter(w -> matches(template, w))
            .collect(Collectors.toList());


        // Add each character in each word to the letter frequency table
        trimmedWords.stream()
            .forEach(w -> w.chars().forEach(c -> createOrIncrement(charFrequencies, (char) c)));

        // Return the character with the highest frequency
        return charFrequencies.entrySet().stream()
            .filter(e -> !guessed.contains(e.getKey().toString()))
            .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
            .findFirst().get().getKey();
    }

    public static boolean matches(String template, String word)
    {
        if(template.length() != word.length())
            return false;

        // Replace any non letter character with a regex for any character
        String regex = template.replaceAll("[^a-z]", "\\\\w");

        // Let regex do its magic
        return Pattern.matches(regex, word);
    }

    private static <T> void createOrIncrement(Map<T, Integer> map, T key)
    {
        if(map.containsKey(key))
            map.put(key, map.get(key) + 1);
        else
            map.put(key, 1);
    }

    private static List<String> getWordsFromFile(File file)
    {
        try
        {
            return Files.lines(file.toPath()).collect(Collectors.toList());
        } catch (IOException e)
        {
            System.out.println("Error: Could not find word file: " + file);
            System.exit(1);
        }

        return null;
    }
}
