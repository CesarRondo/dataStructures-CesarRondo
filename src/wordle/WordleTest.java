package wordle;

import org.junit.jupiter.api.Test;
import project20280.priorityqueue.DefaultComparator;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class WordleTest {

    @Test
    void testHints() {
        Wordle wordleA = new Wordle();
        String target, guess;
        String [] hints;

        target = "abbey";
        guess = "keeps";
        hints = wordleA.getHints(target, guess);
        assertEquals("[_, o, _, _, _]", Arrays.toString(hints));

        target = "abbey";
        guess = "kebab";
        hints = wordleA.getHints(target, guess);
        assertEquals( "[_, o, +, o, o]", Arrays.toString(hints));

        target = "abbey";
        guess = "babes";
        hints = wordleA.getHints(target, guess);
        assertEquals( "[o, o, +, +, _]", Arrays.toString(hints));

        target = "lobby";
        guess = "table";
        hints = wordleA.getHints(target, guess);
        assertEquals("[_, _, +, o, _]", Arrays.toString(hints));

        target = "ghost";
        guess = "pious";
        hints = wordleA.getHints(target, guess);
        assertEquals("[_, _, +, _, o]", Arrays.toString(hints));

        target = "ghost";
        guess = "slosh";
        hints = wordleA.getHints(target, guess);
        assertEquals("[_, _, +, +, o]", Arrays.toString(hints));


        target = "kayak";
        guess = "aorta";
        hints = wordleA.getHints(target, guess);
        assertEquals("[o, _, _, _, o]", Arrays.toString(hints));

        target = "kayak";
        guess = "kayak";
        hints = wordleA.getHints(target, guess);
        System.out.println(target + ", " + guess + ", " + hints);
        assertEquals("[+, +, +, +, +]", Arrays.toString(hints));

        target = "kayak";
        guess = "fungi";
        hints = wordleA.getHints(target, guess);
        System.out.println(target + ", " + guess + ", " + hints);
        assertEquals("[_, _, _, _, _]", Arrays.toString(hints));
    }
}
