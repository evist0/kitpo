package com.evist0.labforwardlist.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ForwardList<T> implements Serializable, Iterable {
    private class Node implements Serializable {
        private final T value;
        private Node next;

        public Node(T value, Node next) {
            this.value = value;
            this.next = next;
        }

        public Node(T value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node root;

    public void add(T value) {
        if (root == null) {
            root = new Node(value);
            return;
        }

        Node previous = root;

        while (previous.next != null) {
            previous = previous.next;
        }

        previous.next = new Node(value);
    }

    public void insert(int at, T value) {
        if (at == 0) {
            Node next = root;
            root = new Node(value, next);
            return;
        }

        Node previous = root;
        int i = 1;

        while (i != at && previous.next != null) {
            previous = previous.next;
            ++i;
        }

        if (i != at) {
            throw new IndexOutOfBoundsException();
        }

        Node next = previous.next;
        previous.next = new Node(value, next);
    }

    public void remove(int at) {
        if (at == 0) {
            if (root == null) {
                throw new IndexOutOfBoundsException();
            }

            root = root.next;
            return;
        }

        Node previous = root;
        int i = 1;

        while (i != at && previous.next != null) {
            previous = previous.next;
            ++i;
        }

        if (i != at) {
            throw new IndexOutOfBoundsException();
        }

        previous.next = previous.next.next;
    }

    public void sort(Comparator<T> comparator) {
        root = mergeSort(root, comparator);
    }

    public List<T> toList() {
        ArrayList<T> arrayList = new ArrayList<>();

        Node current = root;

        while (current != null) {
            arrayList.add(current.value);
            current = current.next;
        }

        return arrayList;
    }

    public Iterator iterator() {
        return new Iterator() {
            private Node node = root;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public Object next() {
                Object value = node.value;
                node = node.next;
                return value;
            }
        };
    }

    private Node mergeSort(Node h, Comparator<T> comparator) {
        if (h == null || h.next == null) {
            return h;
        }

        Node middle = getMiddle(h);
        Node nextOfMiddle = middle.next;

        middle.next = null;

        Node left = mergeSort(h, comparator);

        Node right = mergeSort(nextOfMiddle, comparator);

        return sortedMerge(left, right, comparator);
    }

    private Node sortedMerge(Node a, Node b, Comparator comparator) {
        Node result;

        if (a == null)
            return b;
        if (b == null)
            return a;

        if (comparator.compare(a.value, b.value) < 0) {
            result = a;
            result.next = sortedMerge(a.next, b, comparator);
        } else {
            result = b;
            result.next = sortedMerge(a, b.next, comparator);
        }
        return result;
    }

    private Node getMiddle(Node head) {
        if (head == null)
            return null;

        Node slow = head, fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
