import java.util.List;
import java.util.NoSuchElementException;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;

/**
 * Your implementation of a QuadraticProbingHashMap.
 *
 * @author Rudra Goel
 * @version 1.0
 * @userid rgoel68
 * @GTID 903897740
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class QuadraticProbingHashMap<K, V> {

    /**
     * The initial capacity of the QuadraticProbingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the QuadraticProbingHashMap
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    private static final double MAX_LOAD_FACTOR = 0.67;

    // Do not add new instance variables or modify existing ones.
    private QuadraticProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new QuadraticProbingHashMap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public QuadraticProbingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new QuadraticProbingHashMap.
     *
     * The backing array should have an initial capacity of initialCapacity.
     *
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    @SuppressWarnings("unchecked")
    public QuadraticProbingHashMap(int initialCapacity) {
        table = new QuadraticProbingMapEntry[initialCapacity];
        size = 0;
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use quadratic probing as your resolution
     * strategy.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. For example, let's say the array is of length 5 and the current
     * size is 3 (LF = 0.6). For this example, assume that no elements are
     * removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. Be
     * careful to consider the differences between integer and double
     * division when calculating load factor.
     *
     * You must also resize when there are not any valid spots to add a
     * (key, value) pair in the HashMap after checking table.length/2 spots.
     * There is more information regarding this case in the assignment PDF.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot add null key to map");
        }
        if (value == null) {
            throw new IllegalArgumentException("Cannot add null value to map");
        }

        //first compute the load factor fo the current table
        double loadFactor = (1.0 * size + 1) / table.length;

        if (loadFactor > MAX_LOAD_FACTOR) {
            resizeBackingTable(2 * table.length + 1);
        }

        //table is either of proper size or table is resized to be ready to add the new data
        
        /**
         * For adding to a hash map
         * 1. compute the intended index of where to add the 
         * 2. check if the table is valid to add there
         * 2a. if the index is null --> add there
         * 2b. if the index is the key passed in --> update the <key, value> pair with the new value passed in
         * 2c. if the index is some other <key, value> pair, 
         *  --> probe right quadratically table/2 times. Once this is done, resize and then add again.
         * 2d. if the index is a DEL marker, 
         *  --> probe right until you hit null at which you add data to the first DEL marker we saw
         */
        /**
         * For probing quadratically
         * 1. compute the hash
         * 2. mod the hash by the array length
         * 3. increment (hash % table.length) by i^2 where i increases linearly
         * 4. mod (3) by table.length
         */
        
        return putHelper(key, value, 0, -1);

    }

    /**
     * helper method for put 
     * @param key key to pass
     * @param value value to add
     * @param probeCounter current probing count
     * @param firstDelIndex index of first del marker seen in recursive call stack 
     * @return the element that was previously there or null
     */
    private V putHelper(K key, V value, int probeCounter, int firstDelIndex) {

        if (probeCounter > table.length / 2) {

            resizeBackingTable(2 * table.length + 1);
            /**
             * Once we have resized, we want to reset the probe counter from this point on in the recursive call stack
             * the probeCounter value held from previous call stacks will be irrelevant
             *  --> as this is the only part of the method that requires it
             * any recursive calls made from this point on will be from the reset probe counter
             */
            probeCounter = 0;
        }
        
        int index = Math.abs((Math.abs(key.hashCode() % table.length)
                                + (int) Math.pow(probeCounter, 2)) % table.length);

        if (table[index] == null) {
            if (firstDelIndex != -1) {
                //this means we found a null spot after we found a DEL spot
                //add data at the DEL spot
                table[firstDelIndex].setKey(key);
                table[firstDelIndex].setValue(value);   
                table[firstDelIndex].setRemoved(false);
                size++;
                return null;
            } else {
                table[index] = new QuadraticProbingMapEntry<K, V>(key, value);
                size++;
                return null;
            }
        } else if (table[index].isRemoved()) {
            //DEL Marker
            //need to probe right quadratically until we find a null marker
            //if we find a null marker, we put the data at the first DEL marker we saw
            return putHelper(key, value, probeCounter + 1, index);
        } else if (table[index].getKey().equals(key)) {
            //duplicate key attempt to put
            //so update the old value and return it
            V oldVal = table[index].getValue();
            table[index].setValue(value);
            return oldVal;
        } else {
            //entry that is not null and not the intended key and not a DEL marker
            //it is some other <key, value> pair there
            //we need to probe right quadratically

            return putHelper(key, value, probeCounter + 1, firstDelIndex);
        }
    }

    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot remove a key that is null");
        }

        if (!containsKey(key)) {
            throw new NoSuchElementException("Cannot remove a key that was never in the Hash Map");
        }

        /**
         * First compute the hash of the key
         * Then find the index of where it is supposed to be
         * If it is there --> DEL marker & return data
         * If it is some other data --> probe right quadratically by calculating new index
         */
        int index = Math.abs(key.hashCode() % table.length);
        int probeCounter = 0;
        //while the table at index is not equal to the key, keep incrementing probeCounter and recompute the next index
        while (!table[index].getKey().equals(key)) {
            probeCounter++;
            index = Math.abs((Math.abs(key.hashCode() % table.length)
                                + (int) Math.pow(probeCounter, 2)) % table.length);
        }

        //at this point, table at index contains the data
        table[index].setRemoved(true);
        size--;
        return table[index].getValue();


    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     */
    public V get(K key) {
        //this is the exact same method as remove except we do no set the remove flag to be true
        if (key == null) {
            throw new IllegalArgumentException("Cannot remove a key that is null");
        }

        if (!containsKey(key)) {
            throw new NoSuchElementException("Cannot get a key that was never in the Hash Map");
        }

        /**
         * First compute the hash of the key
         * Then find the index of where it is supposed to be
         * If it is there --> DEL marker & return data
         * If it is some other data --> probe right quadratically by calculating new index
         */
        int index = Math.abs(key.hashCode() % table.length);
        int probeCounter = 0;
        //while the table at index is not equal to the key, keep incrementing probeCounter and recompute the next index
        while (!table[index].getKey().equals(key)) {
            probeCounter++;
            index = Math.abs((Math.abs(key.hashCode() % table.length)
                                + (int) Math.pow(probeCounter, 2)) % table.length);
        }

        //at this point, table at index contains the data
        return table[index].getValue();
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot find a key that is null in a map");
        }

        int index = Math.abs(key.hashCode() % table.length);

        if (table[index] != null && table[index].isRemoved() && table[index].getKey().equals(key)) {
            return false; // data was deleted previously
        } else if (table[index] != null && !table[index].isRemoved() && table[index].getKey().equals(key)) {
            return true; // data is present in table
        } else if (table[index] != null) {
            //means arbitrary data is there but not the key that is in parameter
            //thus we have to probe a total of table.length / 2 times
            int probeCount;

            for (probeCount = 1; probeCount <= table.length / 2; probeCount++) {
                
                int probedIndex = Math.abs((key.hashCode() % table.length
                                    + (int) Math.pow(probeCount, 2)) % table.length);

                if (table[probedIndex] != null && table[probedIndex].isRemoved()
                            && table[probedIndex].getKey().equals(key)) {
                    return false;
                } else if (table[probedIndex] != null && !table[probedIndex].isRemoved()
                                && table[probedIndex].getKey().equals(key)) {
                    return true;
                }
            }
            //if we reach this point in the code, we have probed table.length / 2 times
            //thus the key does not exist in the map
        }
        //either probed table.length / 2 times or table at initial index is null
        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> ret = new HashSet<K>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                ret.add(table[i].getKey());
            }
        }

        return ret;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> ret = new LinkedList<V>();

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                ret.add(table[i].getValue());
            }
        }

        return ret;
    }

    /**
     * Resize the backing table to length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * Note: This method does not have to handle the case where the new length
     * results in collisions that cannot be resolved without resizing again. It
     * also does not have to handle the case where size = 0, and length = 0 is
     * passed into the function.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     * number of items in the hash map
     */
    @SuppressWarnings("unchecked")
    public void resizeBackingTable(int length) {

        if (length < size) {
            throw new 
            IllegalArgumentException("Cannot resize map to be smaller than the number of elements in the map");
        }

        //construct a new map of size 2n+1
        QuadraticProbingMapEntry<K, V>[] temp = new QuadraticProbingMapEntry[length];
    
        //now we have to add ever element from the original map to the new map 
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                //compute a new index of where to add the data from the original table
                int index = Math.abs(table[i].getKey().hashCode() % temp.length);
                if (temp[index] == null) {
                    //address available so add it here
                    temp[index] = table[i];
                } else {
                    //collision detected in resize for the new table
                    //we must probe until we can insert it at an index that is null
                    //guaranteed to never see a DEL marker since there was never anything removed from the temp table
                    int probeCount  = 1;
                    
                    index = Math.abs((Math.abs(table[i].getKey().hashCode() % temp.length)
                                        + (int) Math.pow(probeCount, 2)) % temp.length);
                    
                    while (temp[index] != null) {
                        //increment the prob count
                        probeCount++;
                        //recompute the index via quadratic probing
                        index = Math.abs((Math.abs(table[i].getKey().hashCode() % temp.length)
                                        + (int) Math.pow(probeCount, 2)) % temp.length);
                    }
                    //meaning temp[index] is null
                    temp[index] = table[i];
                    //data has been inserted for data at table[i]
                    //end this instance and insert next data
                }
            }
        }

        table = temp;

    }

    /**
     * Clears the map.
     *
     * Resets the table to a new array of the INITIAL_CAPACITY and resets the
     * size.
     *
     * Must be O(1).
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        table = new QuadraticProbingMapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public QuadraticProbingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    // /**
    //  * helper method for debugging
    //  * 
    //  * prints the table
    //  */
    // private void printTable() {

    //     System.out.print("{");
    //     for (int i = 0; i < table.length - 1; i++) {
    //         System.out.print(table[i] + ", ");
    //     }
    //     System.out.println(table[table.length - 1] + "}");
    //     System.out.println("Table Size: " + table.length);

    // }
}
