import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Read a sequence of words from standard input and prints one of those words uniformly at random.
 *
 * 1. Do not store the words in an array or list.
 * 2. When reading the ith word, select it with probability 1/i
 *  to be the champion, replacing the previous champion.
 * 3. After reading all of the words, print the surviving champion.
 */
public class RandomWord {
    public static void main(String[] args) {
        String champion = null;
        int i = 0;

        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            i++;
            if (StdRandom.bernoulli(1.0 / i)) {
                champion = word;
            }
        }

        if (champion != null) {
            StdOut.println(champion);
        }
    }
}
