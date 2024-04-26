package project20280.priorityqueue;

/*
 */

import project20280.interfaces.Entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


/**
 * An implementation of a priority queue using an array-based heap.
 */

public class HeapPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {

    protected ArrayList<Entry<K, V>> heap = new ArrayList<>();

    /**
     * Creates an empty priority queue based on the natural ordering of its keys.
     */
    public HeapPriorityQueue() {
        super();
    }

    /**
     * Creates an empty priority queue using the given comparator to order keys.
     *
     * @param comp comparator defining the order of keys in the priority queue
     */
    public HeapPriorityQueue(Comparator<K> comp) {
        super(comp);
    }

    /**
     * Creates a priority queue initialized with the respective key-value pairs. The
     * two arrays given will be paired element-by-element. They are presumed to have
     * the same length. (If not, entries will be created only up to the length of
     * the shorter of the arrays)
     *
     * @param keys   an array of the initial keys for the priority queue
     * @param values an array of the initial values for the priority queue
     */
    public HeapPriorityQueue(K[] keys, V[] values) {
        // TODO
        int i=0;
        if(keys.length< values.length){
            for(K k: keys){
                insert(k,values[i]);
                i++;
            }
        }
        else{
            for(V v: values){
                insert(keys[i], v);
                i++;
            }
        }
        heapify();
    }


    // protected utilities
    protected int parent(int j) {
        // TODO
        return (int)(Math.floor(((double)(j+1))/2))-1;
    }

    protected int left(int j) {
        // TODO
        return ((j+1)*2)-1;
    }

    protected int right(int j) {
        // TODO
        return ((j+1)*2);
    }

    protected boolean hasLeft(int j) {
        // TODO
        return (heap.size()>left(j));
    }

    protected boolean hasRight(int j) {
        // TODO
        return (heap.size()>right(j));
    }

    /**
     * Exchanges the entries at indices i and j of the array list.
     */
    protected void swap(int i, int j) {
        // TODO
        K tempKey=((heap.get(i))).getKey();
        V tempValue=((heap.get(i))).getValue();
        ((Node<K,V>)(heap.get(i))).setKey(((heap.get(j))).getKey());
        ((Node<K,V>)(heap.get(i))).setValue(((heap.get(j))).getValue());
        ((Node<K,V>)(heap.get(j))).setKey(tempKey);
        ((Node<K,V>)(heap.get(j))).setValue(tempValue);
    }

    /**
     * Moves the entry at index j higher, if necessary, to restore the heap
     * property.
     */
    protected void upheap(int j) {
        // TODO
        if(j>0){
            //this.swap(j,j-1);
            while (j > 0) {
                int p = parent(j);
                if (compare(heap.get(j), heap.get(p)) >= 0){
                    break;
                }
                swap(j, p);
                j = p;
            }
        }
        else{
            System.out.println("The specified element is already the first in the queue or is not in it");
        }
    }

    /**
     * Moves the entry at index j lower, if necessary, to restore the heap property.
     */
    protected void downheap(int j) {
        // TODO

        while (hasLeft(j)) {
            int leftIndex = left(j);
            int smallChildIndex = leftIndex;
            if (hasRight(j)) {
                int rightIndex = right(j);
                if (compare(heap.get(leftIndex), heap.get(rightIndex)) > 0) {
                    smallChildIndex = rightIndex;
                }
            }
            if (compare(heap.get(smallChildIndex), heap.get(j)) >= 0) break;
            swap(j, smallChildIndex);
            j = smallChildIndex;
        }

    }

    /**
     * Performs a bottom-up construction of the heap in linear time.
     */
    protected void customHeapSort(Node<K,V> entry){
        // TODO
        //Customs algorithm for heapSort instead of usual heapify, upheap, downheap  NOT USED
        int currentIndex = heap.indexOf(entry);
        if(entry!=this.min()){
            Node<K,V> currentParent=(Node<K,V>)heap.get(parent(currentIndex));
            if(compare(entry,currentParent)<=0){
                if(left(parent(currentIndex))==currentIndex){
                    swap(currentIndex,heap.indexOf(currentParent));
                    customHeapSort(currentParent);
                    return;
                }
                else if(right(parent(currentIndex))==currentIndex){
                    swap(currentIndex,heap.indexOf(currentParent));
                    customHeapSort(currentParent);
                    return;
                }
                else{
                    swap(currentIndex,heap.indexOf(currentParent));
                    customHeapSort(currentParent);
                    return;
                }
            }
        }
        if(hasLeft(currentIndex)){//&& actionCode!=1
            //System.out.println(left(currentIndex));
            customHeapSort((Node<K, V>) heap.get(left(currentIndex)));
        }
        if(hasRight(currentIndex)){//&& actionCode!=0
            customHeapSort((Node<K, V>) heap.get(left(currentIndex)));
        }
    }

    protected void heapify() {
        int startIndex = parent(size() - 1);
        for (int j = startIndex; j >= 0; j--) {
            downheap(j);
        }
    }

    // public methods

    /**
     * Returns the number of items in the priority queue.
     *
     * @return number of items
     */
    @Override
    public int size() {
        return heap.size();
    }

    /**
     * Returns (but does not remove) an entry with minimal key.
     *
     * @return entry having a minimal key (or null if empty)
     */
    @Override
    public Entry<K, V> min() {
        return heap.get(0);
    }

    /**
     * Inserts a key-value pair and return the entry created.
     *
     * @param key   the key of the new entry
     * @param value the associated value of the new entry
     * @return the entry storing the new key-value pair
     * @throws IllegalArgumentException if the key is unacceptable for this queue
     */
    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        // TODO
        if((size()!=0) && !(key.getClass() == this.min().getKey().getClass())){
            //System.out.println(key.getClass());
            throw new IllegalArgumentException("Invalid key");
        }
        Node<K,V> newEntry = new Node<>(key,value);
        heap.add(newEntry);
        //System.out.println("Added entry: "+newEntry.getValue());
        upheap(heap.size() - 1);
        return newEntry;
    }

    /**
     * Removes and returns an entry with minimal key.
     *
     * @return the removed entry (or null if empty)
     */
    @Override
    public Entry<K, V> removeMin() {
        // TODO
        if(size()>0){
            Entry<K,V> Min =min();
            heap.remove(Min);
            if(size()!=0){
                downheap(0);
            }
            return Min;
        }
        return null;
    }

    public String toString() {
        //return heap.toString();
        String output = "[";
        for(Entry<K,V> n: heap){
            output=output+n.getKey();
            if(!(heap.indexOf(n)==size()-1)){
                output=output+", ";
            }
        }
        output=output+"]";
        return output;
    }

    public String toTreeString(){
        //Function to draw the heap as a tree


        return null;
    }

    /**
     * Used for debugging purposes only
     */
    private void sanityCheck() {
        for (int j = 0; j < heap.size(); j++) {
            int left = left(j);
            int right = right(j);
            //System.out.println("-> " +left + ", " + j + ", " + right);
            Entry<K, V> e_left, e_right;
            e_left = left < heap.size() ? heap.get(left) : null;
            e_right = right < heap.size() ? heap.get(right) : null;
            if (left < heap.size() && compare(heap.get(left), heap.get(j)) < 0) {
                System.out.println("Invalid left child relationship");
                System.out.println("=> " + e_left + ", " + heap.get(j) + ", " + e_right);
            }
            if (right < heap.size() && compare(heap.get(right), heap.get(j)) < 0) {
                System.out.println("Invalid right child relationship");
                System.out.println("=> " + e_left + ", " + heap.get(j) + ", " + e_right);
            }
        }
    }

    // make my own implementation of Entry

    private static class Node<K,V> implements Entry<K,V>{

        public void setValue(V value) {
            Value = value;
        }
        public void setSorted() {
            this.sorted = true;
        }
        public void setKey(K key) {
            Key = key;
        }
        boolean sorted;
        private V Value;
        private K Key;
        @Override
        public K getKey() {
            return Key;
        }
        @Override
        public V getValue(){
            return Value;
        }
        public Node(K key, V value){
            Value=value;
            Key=key;
            sorted=false;
        }
    }

    public static void main(String[] args) {
        Integer[] rands = new Integer[]{35, 26, 15, 24, 33, 4, 12, 1, 23, 21, 2, 5};
        HeapPriorityQueue<Integer, Integer> pq = new HeapPriorityQueue<>(rands, rands);

        System.out.println("elements: " + Arrays.toString(rands));
        System.out.println("after adding elements: " + pq);

        System.out.println("min element: <" +(pq.min()).getKey() + ", "+pq.min().getValue()+">");

        pq.removeMin();
        System.out.println("after removeMin: " + pq);
        // [             1,
        //        2,            4,
        //   23,     21,      5, 12,
        // 24, 26, 35, 33, 15]
    }
}
