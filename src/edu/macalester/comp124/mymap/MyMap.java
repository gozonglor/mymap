package edu.macalester.comp124.mymap;

import java.util.LinkedList;
import java.util.List;
import java.lang.Math;

/**
 * A simple implementation of a hashtable.
 *
 * @param <K> Class for keys in the table.
 * @param <V> Class for values in the table.
 * @author shilad
 */
public class MyMap<K, V> {
    private static final int INITIAL_SIZE = 4;

    /** The table is package-protected so that the unit test can examine it. */
    List<MyEntry<K, V>>[] buckets;

    /** Number of unique entries (e.g. keys) in the table */
    private int numEntries = 0;

    /** Threshold that determines when the table should expand */
    private double loadFactor = 0.75;

    /**
     * Initializes the data structures associated with a new hashmap.
     */
    public MyMap() {
        buckets = newArrayOfEntries(INITIAL_SIZE);
    }

    /**
     * Returns the number of unique entries (e.g. keys) in the table.
     * @return the number of entries.
     */
    public int size() {
        return numEntries;
    }

    /**
     * Adds a new key, value pair to the table.
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        expandIfNecessary();
        //TODO: Store the key.
        int keysHashCode = Math.abs(key.hashCode());
        int index = keysHashCode % buckets.length;
        List<MyEntry<K, V>> listOfEntries = buckets[index];
        for (int i = 0; i < listOfEntries.size(); i++) {//this is assuming a key exists
            if (listOfEntries.get(i).getKey() == key) {
                listOfEntries.get(i).setValue(value);
                return;
            }
        }

        MyEntry<K, V> keyValuePair = new MyEntry<>(key, value);
        listOfEntries.add(keyValuePair);
        numEntries++;

    }


    /**
     * Returns the value associated with the specified key, or null if it
     * doesn't exist.
     *
     * @param key
     * @return
     */
    public V get(K key) {
        // TODO: retrieve the key.
        int keysHashCode = Math.abs(key.hashCode());
        int index = keysHashCode % buckets.length;
        V value = null;
        List<MyEntry<K, V>> listOfEntries = buckets[index];
        for (int i = 0; i < listOfEntries.size(); i++) {
            if (listOfEntries.get(i).getKey() == key)
                value = listOfEntries.get(i).getValue();
        }

        return value;
    }

    /**
     * Expands the table to double the size, if necessary.
     */
    private void expandIfNecessary() {
        // TODO: expand if necessary
        //Grab list from the old bucket
        //Make a new
        List<MyEntry<K, V>> listOfEntries; //Empty for now until you start the for-loop.
        if (numEntries > (loadFactor * buckets.length)) {
            int newLength = buckets.length * 2;
            List<MyEntry<K, V>>[] array = newArrayOfEntries(newLength); //Creation of the new array with doubled size
            for (int i = 0; i < buckets.length; i++) {
                listOfEntries = buckets[i];
                for (MyEntry<K,V> entry : listOfEntries){
                    K key = entry.getKey();
                    int hashCode = Math.abs(key.hashCode());
                    int compressedHashCode = hashCode % array.length;
                    List<MyEntry<K,V>> list = array[compressedHashCode];
                    list.add(entry);
                }
            }
            buckets = array;
        }
    }



    /**
     * Returns an array of the specified size, each
     * containing an empty linked list that can be
     * filled with MyEntry objects.
     *
     * @param capacity
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<MyEntry<K,V>>[] newArrayOfEntries(int capacity) {
        List<MyEntry<K, V>> [] entries = (List<MyEntry<K,V>> []) (
                java.lang.reflect.Array.newInstance(LinkedList.class, capacity));
        for (int i = 0; i < entries.length; i++) {
            entries[i] = new LinkedList();
        }
        return entries;
    }

}