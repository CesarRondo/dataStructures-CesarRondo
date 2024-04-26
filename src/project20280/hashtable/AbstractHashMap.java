package project20280.hashtable;

import project20280.interfaces.AbstractMap;
import project20280.interfaces.Entry;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 * An abstract base class supporting Map implementations that use hash
 * tables with MAD compression.
 * <p>
 * The base class provides the following means of support:
 * 1) Support for calculating hash values with MAD compression
 * 2) Support for resizing table when load factor reaches 1/2
 * <p>
 * Subclass is responsible for providing abstract methods:
 * createTable(), bucketGet(h,k), bucketPut(h,k,v),
 * bucketRemove(h,k), and entrySet()
 * and for accurately maintaining the protected member, n,
 * to reflect changes within bucketPut and bucketRemove.
 */
public abstract class AbstractHashMap<K, V> extends AbstractMap<K, V> {
    private final int prime;                   // prime factor
    private final long scale;
    private final long shift;           // the shift and scaling factors
    protected int n = 0;                 // number of entries in the dictionary
    protected int capacity;              // length of the table

    /**
     * Creates a hash table with the given capacity and prime factor.
     */
    public AbstractHashMap(int cap, int p) {
        prime = p;
        capacity = cap;
        Random rand = new Random();
        scale = rand.nextInt(prime - 1) + 1;
        shift = rand.nextInt(prime);
        createTable();
    }

    /**
     * Creates a hash table with given capacity and prime factor 109345121.
     */
    public AbstractHashMap(int cap) {
        this(cap, 109345121);
    }  // default prime

    /**
     * Creates a hash table with capacity 17 and prime factor 109345121.
     */
    public AbstractHashMap() {
        this(50);
    }                     // default capacity

    // public methods

    /**
     * Tests whether the map is empty.
     *
     * @return true if the map is empty, false otherwise
     */
    @Override
    public int size() {
        return n;
    }

    /**
     * Returns the value associated with the specified key, or null if no such entry exists.
     *
     * @param key the key whose associated value is to be returned
     * @return the associated value, or null if no such entry exists
     */
    @Override
    public V get(K key) {
        return bucketGet(hashValue(key), key);
    }

    /**
     * Removes the entry with the specified key, if present, and returns
     * its associated value. Otherwise does nothing and returns null.
     *
     * @param key the key whose entry is to be removed from the map
     * @return the previous value associated with the removed key, or null if no such entry exists
     */
    @Override
    public V remove(K key) {
        n--;
        return bucketRemove(hashValue(key), key);
    }

    /**
     * Associates the given value with the given key. If an entry with
     * the key was already in the map, this replaced the previous value
     * with the new one and returns the old value. Otherwise, a new
     * entry is added and null is returned.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with the key (or null, if no such entry)
     */
    @Override
    public V put(K key, V value) {
        // TODO
        n++;
//        if(n>=capacity-5){
//            resize(n+10);
//        }
        int hashCode = hashValue(key);
        //System.out.println("hash: "+ hashCode+" key: "+key+" value: "+value);
        return bucketPut(hashCode,key,value);
    }

    // private utilities

    /**
     * Hash function applying MAD method to default hash code.
     */
    private int hashValue(K key) {
        // TODO
        int hashCode=-1;
        if(key instanceof String){
            char c =((String)key).toCharArray()[0];
            hashCode=c%capacity;
//            Polynomial hashing for Strings
//            int h = 0;
//            for(int i=0; i<((String)key).length(); i++) {
//                char s_i = ((String)key).charAt(i);
//                //h += s_i * ((int) Math.pow(31,((String)(key)).length()-i-1));
//                h = 31 * h + s_i;
//                hashCode=h%capacity;
//            }
        }
        //simple numeric hashing for other types
        else if(key instanceof Character){
            char c = (char)key;
            hashCode=c%capacity;
        }
        else if(key instanceof Integer){
            hashCode = (int)key%capacity;

        }
        else{
            throw new IllegalArgumentException("This map does not support inputted type as a key");
        }
        return hashCode;
    }

    /**
     * Updates the size of the hash table and rehashes all entries.
     */
    private void resize(int newCap) {
        // TODO
        //capacity=newCap;
        ArrayList<Entry<K,V>> entries = new ArrayList<>();
        for(Entry<K,V> entry : this.entrySet()){
            entries.add(entry);
            remove(entry.getKey());
        }
        capacity=newCap;
        createTable();
        for(Entry<K,V> entry : entries){
            put(entry.getKey(),entry.getValue());
        }
    }

    // protected abstract methods to be implemented by subclasses

    /**
     * Creates an empty table having length equal to current capacity.
     */
    protected abstract void createTable();

    /**
     * Returns value associated with key k in bucket with hash value h.
     * If no such entry exists, returns null.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @return associate value (or null, if no such entry)
     */
    protected abstract V bucketGet(int h, K k);

    /**
     * Associates key k with value v in bucket with hash value h, returning
     * the previously associated value, if any.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @param v the value to be associated
     * @return previous value associated with k (or null, if no such entry)
     */
    protected abstract V bucketPut(int h, K k, V v);

    /**
     * Removes entry having key k from bucket with hash value h, returning
     * the previously associated value, if found.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @return previous value associated with k (or null, if no such entry)
     */
    protected abstract V bucketRemove(int h, K k);
}
