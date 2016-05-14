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