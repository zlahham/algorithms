package praktomatTask3;

public interface LUT<E> {
    /**
     * Inserts a new key-value pair into the look-up table.
     */
    void insert(String key, E value)
    throws LUTKeyException;

    /**
     * Removes the key-value pair with the specified key from the look-up
     * table.
     */
    void remove(String key)
    throws LUTKeyException;

    /**
     * Retrieves the key-value pair with the specified key from the look-up
     * table.
     */
    E retrieve(String key)
    throws LUTKeyException;

    /**
     * Updates the key-value pair with the specified key with the new
     * specified value.
     */
    void update(String key, E value)
    throws LUTKeyException;

    /**
     * Returns a string listing all the key-entry pairs in the LUT
     */
    String toString();
}
