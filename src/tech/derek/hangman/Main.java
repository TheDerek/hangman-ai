package tech.derek.hangman;

import java.util.Random;

public class Main
{
    public static void main(String[] args)
    {
        Random random = new Random();
        int wordCount = 100;

        System.out.print("Solving");
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
}
