import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first, last;
    private int n;

    private static class Node<Item> {
        private Item item;
        private Node<Item> prev;
        private Node<Item> next;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null || last == null;
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
        first.prev = null;

        n += 1;

        if (size() == 1) {
            last = first;
        } else if (size() == 2) {
            first.next = last;
            last.prev = first;
        } else {
            oldFirst.prev = first;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        validateAdd(item);

        Node<Item> oldLast = last;

        last = new Node<Item>();
        last.item = item;
        last.prev = oldLast;
        last.next = null;

        n += 1;

        if (size() == 1) {
            first = last;
        }
        else if (size() == 2) {
            first.next = last;
            last.prev = first;
        } else {
            oldLast.next = last;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        validateRemove();

        Node<Item> oldFirst = first;
        Item item = oldFirst.item;

        first = oldFirst.next;

        n -= 1;

        if (size() == 0) {
            first = null;
            last = null;
        }

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        validateRemove();

        Node<Item> oldLast = last;
        Item item = oldLast.item;

        oldLast.next = null;
        last = oldLast.prev;

        n -= 1;

        if (size() == 0) {
            first = null;
            last = null;
        }

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator(first);
    }

    private class ListIterator implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> firstNode) {
            current = firstNode;
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

    private void validateRemove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("a");
        StdOut.println(deque.removeLast() + " ");
        StdOut.println("(" + deque.size() + " left on stack)");
        deque.addLast("d");
        StdOut.println(deque.removeFirst() + " ");
        StdOut.println("(" + deque.size() + " left on stack)");
        for (String item : deque) {
            StdOut.println("item: " + item);
        }
        deque.addFirst("b");
        deque.addLast("c");
        deque.addLast("d");
        deque.addFirst("a");
        StdOut.println("(" + deque.size() + " left on stack)");

        Iterator<String> i = deque.iterator();
        while (i.hasNext()) {
            String s = i.next();
            StdOut.println("s: " + s);
        }

        StdOut.println(deque.removeLast() + " ");
        StdOut.println(deque.removeFirst() + " ");
        StdOut.println(deque.removeFirst() + " ");
        StdOut.println(deque.removeFirst() + " ");
        StdOut.println(deque.removeFirst() + " ");
    }
}
