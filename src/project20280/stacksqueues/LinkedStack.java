package project20280.stacksqueues;

import project20280.interfaces.Stack;
import project20280.list.DoublyLinkedList;

public class LinkedStack<E> implements Stack<E> {

    private DoublyLinkedList<E> ll;

    public static void main(String[] args) {
        LinkedStack<Integer> stack = new LinkedStack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println(stack); // Output the stack contents
        System.out.println("Top element: " + stack.top()); // Show top element
        stack.pop();
        System.out.println(stack); // Output the stack contents after one pop
        System.out.println("Is empty: " + stack.isEmpty()); // Check if the stack is empty
    }

    public LinkedStack() {
        ll = new DoublyLinkedList<>(); // Initialize the doubly linked list
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
    public void push(E e) {
        ll.addFirst(e); // Add element at the beginning of the list (top of the stack)
    }

    @Override
    public E top() {
        if (isEmpty()) {
            return null; // Return null if the stack is empty
        }
        return ll.first(); // Return the first element of the list (top of the stack)
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            return null; // Return null if the stack is empty
        }
        return ll.removeFirst(); // Remove and return the first element of the list (top of the stack)
    }

    @Override
    public String toString() {
        return ll.toString(); // Return a string representation of the stack
    }
}