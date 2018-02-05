package praktomatTask3;
public class LinearLUTM2F<E> implements LUT<E>{
    protected SequenceList<Entry> seq = new SequenceList<>();

    @Override
    public void insert(String key, E value) throws LUTKeyException {
        Entry newEntry = new Entry(new Key(key), value);
        seq.insertFirst(newEntry);
    }

    @Override
    public void remove(String key) throws LUTKeyException {
        Key searchKey = new Key(key);
        int index = findPosition(searchKey);
        if (index >= 0) {
            try {
                seq.delete(index);
            } catch (SequenceException e) {
                throw new AssertionError("This should not happen." + e);
            }
        } else {
            throw new LUTKeyException();
        }
    }

    @Override
    public E retrieve(String key) throws LUTKeyException {
        Key searchKey = new Key(key);
        int index = findPosition(searchKey);
        if (index >= 0) {
            try {
                Entry searchEntry = (Entry) seq.element(index);
                seq.delete(index);
                seq.insertFirst(searchEntry);

                return searchEntry.value;
            } catch (SequenceException e) {
                throw new AssertionError("This should not happen." + e);
            }
        } else {
            throw new LUTKeyException("Key not found");
        }
    }

    @Override
    public void update(String key, E value) throws LUTKeyException {
        Key searchKey = new Key(key);
        int index = findPosition(searchKey);
        if (index >= 0) {
            try {
                Entry searchEntry = (Entry) seq.element(index);
                searchEntry.value = value;
                seq.delete(index);
                seq.insertFirst(searchEntry);
            } catch (SequenceException e) {
                throw new AssertionError("This should not happen." + e);
            }
        } else {
            throw new LUTKeyException("Key not found");
        }
    }

    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < seq.size(); i++) {
            try {
                Entry tableEntry = (Entry) seq.element(i);
                output += tableEntry.key.toString() + ":" +
                        tableEntry.value.toString() + ", ";
            } catch (SequenceException e) {
                throw new AssertionError("This should not happen." + e);
            }
        }
        return output;
    }

    protected int findPosition(Key k) {
        return linearSearch(k);
    }

    protected int linearSearch(Key k) {
        for (int i = 0; i < seq.size(); i++) {
            if (k.equals(keyAt(i))) {
                return i;
            }
        }
        return -1;
    }

    protected Key keyAt(int index) {
        try {
            Entry entryAt = (Entry) seq.element(index);
            return entryAt.key;
        } catch (SequenceException e) {
            throw new AssertionError("This should not happen." + e);
        }
    }


    protected class Key {
        private String kString;

        Key() {
            kString = null;
        }

        Key(String s) {
            kString = s;
        }

        boolean equals(Key k) {
          return kString.equals(k.toString());
        }


        boolean lessThan(Key k) {
            return kString.compareTo(k.toString()) < 0;
        }

        boolean greaterThan(Key k) {
            return kString.compareTo(k.toString()) > 0;
        }


        @Override
        public String toString() {
            return kString;
        }
    }


    protected class Entry {

        protected Key key;
        protected E value;

        Entry(Key k, E v) {
            key = k;
            value = v;
        }
    }
}
