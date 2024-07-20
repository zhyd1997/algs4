import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first, last, least;
    private int n;

    private static class Node<Item> {
        Item item;
        Node<Item> next;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        // next one is last
        least = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        validateAdd(item);

        Node<Item> oldFirst = first;

        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;

        n += 1;

        if (size() == 1) {
            last = first;
            least = first;
        }

        if (size() == 2) {
            least = first;
            last = first.next;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        validateAdd(item);

        Node<Item> oldLast = last;

        last = new Node<Item>();
        last.item = item;
        last.next = null;

        if (!isEmpty()) {
            oldLast.next = last;
        }

        n += 1;

        if (size() == 1) {
            first = last;
            least = last;
        }

        if (size() == 2) {
            least = oldLast;
            first = last;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        validateRemoveEnd();

        Item item = first.item;

        first = first.next;

        n -= 1;

        if (isEmpty()) {
            first = last;
        }

        if (size() == 1) {
            least = first;
            last = first;
        }

        if (size() == 2) {
            least = first;
            last = first.next;
        }

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        validateRemoveEnd();

        Node<Item> oldLast = last;
        Item item = oldLast.item;

        least.next = null;
        last = least;

        n -= 1;

        if (isEmpty()) {
            last = null;
        }

        if (size() == 1) {
            least = first;
            last = first;
        }

        if (size() == 2) {
            least = first;
            last = first.next;
        }

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator(first);
    }

    private class ListIterator implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("unsupported method");
        }

        public Item next() {
            validate();

            Item item = current.item;

            current = current.next;

            return item;
        }

        private void validate() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
        }
    }

    private void validateAdd(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    private void validateRemoveEnd() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        int count = 0;

        while (!StdIn.isEmpty()) {
            count += 1;
            String item = StdIn.readString();

            System.out.println("count: " + count);
            if (!item.equals("-")) {
                if (count % 2 == 0) {
                    System.out.println(item + " - addFirst");
                    deque.addFirst(item);
                } else {
                    System.out.println(item + " - addLast");
                    deque.addLast(item);
                }
            } else if (!deque.isEmpty()) {
                if (count % 2 == 0) {
                    StdOut.println(deque.removeFirst() + " ");
                } else {
                    StdOut.println(deque.removeLast() + " ");
                }
            }
        }

        StdOut.println("(" + deque.size() + " items left in the deque)");
    }
}
