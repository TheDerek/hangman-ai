package tech.derek.hangman;

import org.junit.Assert;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SolverTest
{
    @org.junit.Test
    public void averageGuessesPerWord() throws Exception
    {
        Random random = new Random();
        int wordCount = 100;

        System.out.println("Solving");
        int numberOfGuesses = 0;
        for(int x = 0; x < wordCount; x++)
        {
            System.out.print(".");
            // Pick a random word from the dictionary
            String word = Solver.words.get(random.nextInt(Solver.words.size()));

            // Create our guesses, empty for now
            String guesses = "";
            String correctGuesses = "";

            // Transform our word into a template
            String template = word.replaceAll(".", "_");

            // Solve the word
            while(!template.equals(word))
            {
                // Increase the number of guesses we have taken
                numberOfGuesses++;

                // Guess the next character
                char g = Solver.guess(template, guesses);

                // Add the character to the list of guessed characters
                guesses += g;

                // Check if the word contains the guessed character
                if(word.contains(String.valueOf(g)))
                {
                    // Update our template with the matched character
                    correctGuesses += g;
                    String guessCorrect = correctGuesses;
                    StringBuilder builder = new StringBuilder();
                    word.chars().forEach(c -> builder.append((char) (guessCorrect.contains(String.valueOf((char) c)) ? c : '_')));
                    template = builder.toString();
                }
            }

            // Word has been solved, remove the number of correct guesses from
            // the total number of guesses
            numberOfGuesses -= correctGuesses.length();
        }
        System.out.println();
        System.out.print("Solved " + wordCount + " words, with an average of " + ((float) numberOfGuesses / wordCount) + " guesses per word.");

    }

    @org.junit.Test
    public void guess() throws Exception
    {
        assertEquals('a', Solver.guess("r_d_r"));
        assertEquals('a', Solver.guess("_pple"));
    }

    @org.junit.Test
    public void matches() throws Exception
    {
        assertTrue(Solver.matches("_pple", "apple"));
        assertTrue(Solver.matches("f###", "fake"));

        assertFalse(Solver.matches("fgwas_", "hasd"));
        assertFalse(Solver.matches("fg###was", "hasd"));
        assertFalse(Solver.matches("test", "testings"));
    }

}