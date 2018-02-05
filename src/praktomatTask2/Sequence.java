package praktomatTask2;

public interface Sequence<E> {
    /**
     * Adds a new item at a specified position in the sequence.
     */
    void insert(E e, int index)
            throws SequenceException;

    /**
     * Adds a new item at the start of the sequence.
     */
    void insertFirst(E e)
            throws SequenceException;

    /**
     * Adds a new item at the end of the sequence.
     */
    void insertLast(E e)
            throws SequenceException;

    /**
     * Removes the item at the specified position in the sequence.
     */
    void delete(int index)
            throws SequenceException;

    /**
     * Removes the item at the start of the sequence.
     */
    void deleteFirst()
            throws SequenceException;

    /**
     * Removes the item at the end of the sequence.
     */
    void deleteLast()
            throws SequenceException;

    /**
     * Returns the item at the specified position in the sequence.
     */
    E element(int index)
            throws SequenceException;

    /**
     * Returns the item at the start of the sequence.
     */
    E first()
            throws SequenceException;

    /**
     * Returns the item at the end of the sequence.
     */
    E last()
            throws SequenceException;

    /**
     * Returns the number of items in the sequence.
     */
    int size();

    /**
     * Tests whether there are any items in the sequence.
     */
    boolean empty();

    /**
     * Empties the sequence.
     */
    void clear();
}

