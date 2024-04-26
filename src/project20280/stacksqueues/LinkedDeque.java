package project20280.stacksqueues;

import project20280.interfaces.Deque;
import project20280.list.DoublyLinkedList;

public class LinkedDeque<E> implements Deque<E> {

    DoublyLinkedList<E> ll;

    public LinkedDeque() {
        ll = new DoublyLinkedList<>();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        LinkedDeque<Integer> deque = new LinkedDeque<>();
        deque.addFirst(10);
        deque.addLast(20);
        deque.addFirst(5);
        deque.addLast(25);

        System.out.println("Deque after additions: " + deque);
        System.out.println("First element: " + deque.first());
        System.out.println("Last element: " + deque.last());
        System.out.println("Size of deque: " + deque.size());

        deque.removeFirst();
        deque.removeLast();

        System.out.println("Deque after removals: " + deque);
        System.out.println("New first element: " + deque.first());
        System.out.println("New last element: " + deque.last());

    }

    @Override
    public int size() {
        return ll.size();
    }

    @Override
    public boolean isEmpty() {
        return ll.isEmpty();
    }

    @Override
    public E first() {
        return ll.first();
    }

    @Override
    public E last() {
        return ll.last();
    }

    @Override
    public void addFirst(E e) {
        ll.addFirst(e);
    }

    @Override
    public void addLast(E e) {
        ll.addLast(e);
    }

    @Override
    public E removeFirst() {
        return ll.removeFirst();
    }

    @Override
    public E removeLast() {
        return ll.removeLast();
    }

    public String toString() {
        return ll.toString();
    }
}