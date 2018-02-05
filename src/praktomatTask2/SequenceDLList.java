package praktomatTask2;

/**
 * Implementation of Sequence ADT using a double linked list.
 *
 * This class is an implementation of the Sequence using a double linked
 * list as the underlying data structure. The capacity is therefore
 * unlimited and overflow does not need to be checked.
 */

public class SequenceDLList <E> implements Sequence<E> {

    protected class Node {
        E datum;
        Node previous;
        Node next;

        Node(E datum) {
            this(datum, null, null);
        }

        Node(E datum, Node previous, Node next) {
            this.datum = datum;
            this.previous = previous;
            this.next = next;
        }
    }

    private Node listHead;
    private Node listTail;
    private int size;

    public SequenceDLList() {
        listHead = null;
        listTail = null;
        size     = 0;
    }

    @Override
    public void insertFirst(E e) {
        if (listHead == null) {
            listHead = new Node(e);
            listTail = listHead;
        } else {
            listHead.previous = new Node(e, null, listHead);
            listHead = listHead.previous;
        }
        size += 1;
    }

    @Override
    public void insertLast(E e) {
        if (listHead == null) {
            listHead = new Node(e);
            listTail = listHead;
        } else {
            listTail.next = new Node(e, listTail, null);
            listTail = listTail.next;
        }
        size += 1;
    }

    @Override
    public void insert(E e, int index) throws SequenceException {
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
            listHead = new Node(e, listHead, listHead.next);
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

            Node newNode = new Node(e, nodePointer.next, nodePointer.previous);
            nodePointer.next.previous = newNode;
            nodePointer.next = newNode;

            // Finally we need to check that the tail pointer is
            // correct. Another special case occurs if the new
            // node was inserted at the end, in which case, we need
            // to update the tail pointer.
            if (nodePointer == listTail) {
                listTail = listTail.next;
            }
        }
         size += 1;
    }

    @Override
    public void deleteFirst() throws SequenceException {
        if (listHead == null) {
            throw new SequenceException("Sequence Underflow");
        }

        if (listHead.next == null) {
            listHead = null;
            listTail = null;
        } else {
            listHead = listHead.next;
            listHead.previous = null;
        }
        size -= 1;
    }

    @Override
    public void deleteLast() throws SequenceException {
        if (listHead == null) {
            throw new SequenceException("Sequence Underflow");
        }

        // There is a special case when there is just one item in the
        // sequence. Both pointers then need to be reset to null.
        if (listHead.next == null) {
            listHead = null;
            listTail = null;
        } else {
            listTail = listTail.previous;
            listTail.next = null;
        }
        size -= 1;
    }

    @Override
    public void delete(int index) throws SequenceException {
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

            if (nodePointer.next.next == null) {
                this.deleteLast();
            } else {
                nodePointer.next = nodePointer.next.next;
                nodePointer.next.previous = nodePointer;
                size -=1;
            }
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
    public E last() throws SequenceException {
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
        return listHead == null && listTail == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        listHead = null;
        listTail = null;
    }
}
