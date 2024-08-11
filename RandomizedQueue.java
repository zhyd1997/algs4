import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;

    private Item[] rq;
    private int n;
    private int headIndex;
    private int tailIndex;

    // construct an empty randomized queue
    public RandomizedQueue() {
        rq = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
        headIndex = 0;
        tailIndex = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            int idx = (headIndex + i) % rq.length;
            copy[i] = rq[idx];
        }
        rq = copy;
        headIndex = 0;
        tailIndex = n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (n == rq.length) resize(2*rq.length);
        rq[tailIndex++] = item;
        if (tailIndex == rq.length) tailIndex = 0;
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        int idx = StdRandom.uniformInt(n);

        Item item = rq[idx];
        rq[idx] = null; // to avoid loitering

        n--;

        if (n > 0) {
            if ( n == rq.length / 4) {
                resize(rq.length / 2);
            }
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();

        int idx = StdRandom.uniformInt(n);
        Item item = rq[idx];

        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < n;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            int idx = StdRandom.uniformInt(n);
            i++;

            return rq[idx];
        }

        public void remove() {
            throw new UnsupportedOperationException("unsupported method");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        StdOut.println("is rq empty? " + rq.isEmpty());

        rq.enqueue("a");
        StdOut.println("dequeue: " + rq.dequeue());

        rq.enqueue("a");
        rq.enqueue("b");
        rq.enqueue("c");
        rq.enqueue("d");

        StdOut.println("sample: " + rq.sample());

        StdOut.println("is rq empty? " + rq.isEmpty());
        StdOut.println("size: " + rq.size());

        Iterator<String> i = rq.iterator();
        while (i.hasNext()) {
            String s = i.next();
            StdOut.println("item: " + s);
        }

        StdOut.println("dequeue: " + rq.dequeue());
        StdOut.println("dequeue: " + rq.dequeue());
        StdOut.println("dequeue: " + rq.dequeue());
        StdOut.println("dequeue: " + rq.dequeue());
    }

}
