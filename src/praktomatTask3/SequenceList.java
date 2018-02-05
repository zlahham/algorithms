package praktomatTask3;

/**
 * Implementation of Sequence ADT using a linked list.
 *
 * This class is an implementation of the Sequence using an linked
 * list as the underlying data structure. The capacity is therefore
 * unlimited and overflow does not need to be checked.
 */
public class SequenceList<E> implements Sequence<E> {

    /**
     * Member class Node encapsulates the nodes of the linked list in
     * which the stack is stored. Each node contains a data item and a
     * reference to another node - the next in the linked list.
     */
    protected class Node {

        // The Node data structure consists of two object references.
        // One for the datum contained in the node and the other for
        // the next node in the list.

        E datum;
        Node next;

        Node(E e) {
            this(e, null);
        }

        Node(E e, Node n) {
            datum = e;
            next = n;
        }
    }

    // We use object references to the head and tail of the list (the head
    // and tail of the sequence, respectively).
    private Node listHead;
    private Node listTail;

    /**
     * Constructs an empty sequence object.
     */
    public SequenceList() {
        // Only require a single constructor, which sets both object
        // references to null.
        listHead = null;
        listTail = null;
    }

    @Override
    public void insertFirst(E e) {
        // There is a special case when the sequence is empty.
        // Then the both the head and tail pointers needs to be
        // initialised to reference the new node.
        if (listHead == null) {
            listHead = new Node(e);
            listTail = listHead;
        }

        // In the general case, we simply add a new node at the start
        // of the list via the head pointer.
        else {
            listHead = new Node(e, listHead);
        }
    }

    @Override
    public void insertLast(E e) {
        // There is a special case when the sequence is empty.
        // Then the both the head and tail pointers needs to be
        // initialised to reference the new node.
        if (listHead == null) {
            listHead = new Node(e);
            listTail = listHead;
        }

        // In the general case, we simply add a new node to the end
        // of the list via the tail pointer.
        else {
            listTail.next = new Node(e, listTail.next);
            listTail = listTail.next;
        }
    }

    @Override
    public void insert(E e, int index)
    throws SequenceException {
        // Check the index is positive.
        if (index < 0) {
            throw new SequenceException("Indexed Element out of Range");
        }

        // There is a special case when the sequence is empty.
        // Then the both the head and tail pointers needs to be
        // initialised to reference the new node.
        if (listHead == null) {
            if (index == 0) {
                listHead = new Node(e);
                listTail = listHead;
            } else {
                throw new SequenceException("Indexed element is out of range");
            }
        }

        // There is another special case for insertion at the head of
        // the sequence.
        else if (index == 0) {
            listHead = new Node(e, listHead);
        }

        // In the general case, we need to chain down the linked list
        // from the head until we find the location for the new
        // list node. If we reach the end of the list before finding
        // the specified location, we know that the given index was out
        // of range and throw an exception.
        else {
            Node nodePointer = listHead;
            int i = 1;
            while (i < index) {
                nodePointer = nodePointer.next;
                i += 1;
                if (nodePointer == null) {
                    throw new SequenceException("Indexed Element out of Range");
                }
            }

            // Now we've found the node before the position of the
            // new one, so we 'hook in' the new Node.

            nodePointer.next = new Node(e, nodePointer.next);

            // Finally we need to check that the tail pointer is
            // correct. Another special case occurs if the new
            // node was inserted at the end, in which case, we need
            // to update the tail pointer.
            if (nodePointer == listTail) {
                listTail = listTail.next;
            }
        }
    }

    @Override
    public void deleteFirst()
    throws SequenceException {
        // Check there is something in the sequence to delete.
        if (listHead == null) {
            throw new SequenceException("Sequence Underflow");
        }

        // There is a special case when there is just one item in the
        // sequence. Both pointers then need to be reset to null.
        if (listHead.next == null) {
            listHead = null;
            listTail = null;
        }

        // In the general case, we just unlink the first node of the
        // list.
        else {
            listHead = listHead.next;
        }
    }

    @Override
    public void deleteLast()
    throws SequenceException {
        // Check there is something in the sequence to delete.
        if (listHead == null) {
            throw new SequenceException("Sequence Underflow");
        }

        // There is a special case when there is just one item in the
        // sequence. Both pointers then need to be reset to null.
        if (listHead.next == null) {
            listHead = null;
            listTail = null;
        }

        // In the general case, we need to chain all the way down the
        // list in order to reset the link of the second to last
        // element to null.
        else {
            Node nodePointer = listHead;
            while (nodePointer.next != listTail) {
                nodePointer = nodePointer.next;
            }

            // Unlink the last node and reset the tail pointer.
            nodePointer.next = null;
            listTail = nodePointer;
        }
    }

    @Override
    public void delete(int index)
    throws SequenceException {
        // Check there is something in the sequence to delete.
        if (listHead == null) {
            throw new SequenceException("Sequence Underflow");
        }

        // Check the index is positive.
        if (index < 0) {
            throw new SequenceException("Indexed Element out of Range");
        }

        // There is a special case when there is just one item in the
        // sequence. Both pointers then need to be reset to null.
        if (listHead.next == null) {
            if (index == 0) {
                listHead = null;
                listTail = null;
            } else {
                throw new SequenceException("Indexed element is out of range.");
            }
        }

        // There is also a special case when the first element has to
        // be removed.

        else if (index == 0) {
            deleteFirst();
        }

        // In the general case, we need to chain down the list to find
        // the node in the indexed position.
        else {
            Node nodePointer = listHead;
            int i = 1;
            while (i < index) {
                nodePointer = nodePointer.next;
                i += 1;
                if (nodePointer.next == null) {
                    throw new SequenceException("Indexed Element out of Range");
                }

            }

            // Unlink the node and reset the tail pointer if that
            // node was the last one.
            if (nodePointer.next == listTail) {
                listTail = nodePointer;
            }
            nodePointer.next = nodePointer.next.next;
        }
    }

    @Override
    public E first()
    throws SequenceException {
        if (listHead != null) {
            return listHead.datum;
        } else {
            throw new SequenceException("Indexed Element out of Range");
        }
    }

    @Override
    public E last()
    throws SequenceException {
        if (listTail != null) {
            return listTail.datum;
        } else {
            throw new SequenceException("Indexed Element out of Range");
        }
    }

    @Override
    public E element(int index)
    throws SequenceException {
        // Check the index is positive.
        if (index < 0) {
            throw new SequenceException("Indexed Element out of Range");
        }

        // We need to chain down the list until we reach the indexed
        // position

        Node nodePointer = listHead;
        int i = 0;
        while (i < index) {
            if (nodePointer.next == null) {
                throw new SequenceException("Indexed Element out of Range");
            } else {
                nodePointer = nodePointer.next;
                i += 1;
            }
        }

        return nodePointer.datum;
    }

    @Override
    public boolean empty() {
        return listHead == null;
    }

    @Override
    public int size() {
        // Chain down the list counting the elements

        Node nodePointer = listHead;
        int size = 0;
        while (nodePointer != null) {
            size += 1;
            nodePointer = nodePointer.next;
        }
        return size;
    }

    @Override
    public void clear() {
        listHead = null;
        listTail = null;
    }
}
