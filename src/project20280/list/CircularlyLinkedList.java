package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class CircularlyLinkedList<E> implements List<E> {

    private class Node<T> {
        private final T data;
        private Node<T> next;

        public Node(T e, Node<T> n) {
            data = e;
            next = n;
        }

        public T getData() {
            return data;
        }

        public void setNext(Node<T> n) {
            next = n;
        }

        public Node<T> getNext() {
            return next;
        }
    }

    private  Node<E> tail = null;
    private  int size = 0;

    public CircularlyLinkedList() {
        tail = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int i) {
        // TODO
        if (isEmpty() || i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<E> curr = tail.getNext();  //head
        for (int j = 0; j < i; j++) {
            curr = curr.next;
        }
        return curr.getData();
    }

    /**
     * Inserts the given element at the specified index of the list, shifting all
     * subsequent elements in the list one position further to make room.
     *
     * @param i the index at which the new element should be stored
     * @param e the new element to be stored
     */
    @Override
    public void add(int i, E e) {
        // TODO
        if (i < 0 || i > size) {
            throw new IllegalArgumentException("Index not existing in the List");
        }
        if (i == 0) {
            addFirst(e);
        } else if (i == size) {
            addLast(e);
        } else {
            Node<E> newNode = new Node<>(e, null);
            Node<E> prev = tail;
            for (int j = 0; j < i; j++) {
                prev = prev.next;
            }
            newNode.setNext(prev.getNext());
            prev.setNext(newNode);
            size++;
        }
    }

    @Override
    public E remove(int i) {
        //TODO
        if (isEmpty() || i < 0 || i >= size) {
            throw new IllegalArgumentException("Index not existing in the List");
        }
        if (i == 0) {
            return removeFirst();
        } else if (i == size - 1) {
            return removeLast();
        } else {
            Node<E> prev = tail;
            for (int j = 0; j < i; j++) {
                prev = prev.next;
            }
            Node<E> current = prev.next;
            prev.setNext(current.next);
            size--;
            return current.data;
        }
    }

    public void rotate() {
        // TODO
        if (tail != null) {
            tail = tail.getNext();
        }
    }

    private class CircularlyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) tail;

        @Override
        public boolean hasNext() {
            return curr != tail;
        }

        @Override
        public E next() {
            E res = curr.data;
            curr = curr.next;
            return res;
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new CircularlyLinkedListIterator<E>();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E removeFirst() {
        // TODO
        if (isEmpty()) {
            return null;
            //throw new IllegalStateException("Empty List");
        }
        Node<E> head = tail.next;
        if (size == 1) {
            tail = null;
        } else {
            tail.setNext(head.next);
        }
        size--;
        return head.data;
    }

    @Override
    public E removeLast() {
        // TODO
        if (isEmpty()) {
            throw new IllegalArgumentException("Empty list");
        }
        Node<E> head = tail.next;
        if (size == 1) {
            tail = null;
            size = 0;
            return head.getData();
        } else {
            Node<E> prev = tail;
            for (int i = 0; i < size - 1; i++) {
                prev = prev.next;
            }
            E removedData = prev.next.data;
            prev.setNext(head);
            tail = prev;
            size--;
            return removedData;
        }
    }

    @Override
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e, null);
        if (isEmpty()) {
            tail = newNode;
            newNode.setNext(newNode);
        } else {
            newNode.setNext(tail.next);
            tail.setNext(newNode);
        }
        size++;
    }

    @Override
    public void addLast(E e) {
        // TODO
        addFirst(e);
        tail = tail.next;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = tail;
        do {
            curr = curr.next;
            sb.append(curr.data);
            if (curr != tail) {
                sb.append(", ");
            }
        } while (curr != tail);
        sb.append("]");
        return sb.toString();
    }


    public static void main(String[] args) {
        CircularlyLinkedList<Integer> ll = new CircularlyLinkedList<Integer>();
        for (int i = 10; i < 20; ++i) {
            ll.addLast(i);
        }

        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        ll.rotate();
        System.out.println(ll);

        ll.removeFirst();
        ll.rotate();
        System.out.println(ll);

        ll.removeLast();
        ll.rotate();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }

    }
}