package praktomatTask4;

public class BinaryTreeLUT<E> implements LUT<E> {

    protected class Key {

        private String kString;

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

    protected class BSTreeNode {

        protected Entry kvPair;
        protected BSTreeNode left;
        protected BSTreeNode right;

        BSTreeNode(Entry e) {
            kvPair = e;
            left = null;
            right = null;
        }

        BSTreeNode(Entry e, BSTreeNode l, BSTreeNode r) {
            kvPair = e;
            left = l;
            right = r;
        }
    }

    protected BSTreeNode root;

    public BinaryTreeLUT() {
        root = null;
    }

    @Override
    public void insert(String key, E value) {
        Entry newEntry = new Entry(new Key(key), value);
        BSTreeNode newNode = new BSTreeNode(newEntry);
        addToTree(newNode, root);
    }

    @Override
    public void remove(String key)
            throws LUTKeyException {
        Key searchKey = new Key(key);
        removeFromTree(searchKey, root);
    }

    @Override
    public E retrieve(String key)
            throws LUTKeyException {
        Key searchKey = new Key(key);
        BSTreeNode treeNode = getFromTree(searchKey, root);
        return treeNode.kvPair.value;
    }

    @Override
    public void update(String key, E value)
            throws LUTKeyException {
        Key searchKey = new Key(key);
        BSTreeNode treeNode = getFromTree(searchKey, root);
        treeNode.kvPair.value = value;
    }

    @Override
    public String toString() {
        return treeString(root);
    }

    protected void addToTree(BSTreeNode newNode,
                             BSTreeNode curNode) {
        if (curNode == null) {
            // Special case for empty tree.
            root = newNode;
        } else {
            Key curKey = curNode.kvPair.key;
            Key newKey = newNode.kvPair.key;

            if (newKey.lessThan(curKey)) {
                // General case: recurse left or right depending on comparison.
                if (curNode.left == null) {
                    curNode.left = newNode;
                } else {
                    addToTree(newNode, curNode.left);
                }
            } else {
                if (curNode.right == null) {
                    curNode.right = newNode;
                } else {
                    addToTree(newNode, curNode.right);
                }
            }
        }
    }

    protected BSTreeNode getFromTree(Key k, BSTreeNode node)
            throws LUTKeyException {
        if (node == null) {
            throw new LUTKeyException();
        } else if (k.equals(node.kvPair.key)) {
            return node;
        } else if (k.lessThan(node.kvPair.key)) {
            return getFromTree(k, node.left);
        } else {
            return getFromTree(k, node.right);
        }
    }

    protected void removeFromTree(Key k, BSTreeNode node) throws LUTKeyException {
        if (node == null) {
            throw new LUTKeyException();
        } else if (k.equals(root.kvPair.key)) {
            root = lrMerge(root);
        } else if (k.lessThan(node.kvPair.key)) {
            // General case, go left

            // If the search key is less than key at the current node,
            // go to the left subtree.

            if (node.left == null) {
                // If the left subtree is empty, the tree cannot contain the
                // search key.
                throw new LUTKeyException();
            }

            if (k.equals(node.left.kvPair.key)) {
                // If this is the parent of the node to be removed, do the
                // removal.
                node.left = lrMerge(node.left);
            } else {
                // Otherwise, recurse down another level.
                removeFromTree(k, node.left);
            }
        } else {
            // General case, go right

            // Otherwise go to the right subtree.
            if (node.right == null) {
                // If the right subtree is empty, the tree cannot contain the
                // search key.
                throw new LUTKeyException();
            }

            if (k.equals(node.right.kvPair.key)) {
                // If this is the parent of the node to be removed, do the
                // removal.
                node.right = lrMerge(node.right);
            } else {
                // Otherwise, recurse down another level.
                removeFromTree(k, node.right);
            }
        }
    }

    protected BSTreeNode lrMerge(BSTreeNode node) throws LUTKeyException {
        BSTreeNode mergedTrees = null;
        // First two cases occur when one or both subtrees of the node to
        // be deleted are empty.
        if (node.left == null) {
            mergedTrees = node.right;
        } else if (node.right == null) {
            mergedTrees = node.left;
        } else {
            // general case
            // Otherwise, find the maximum key value in the left subtree
            // copy the value to the targeted node
            // and then delete the duplicate from the left subtree
            BSTreeNode max = findMax(node.left);

            BSTreeNode parent = node;
            if (parent.left == max) {
                if (max.left != null) {
                    parent.left = max.left;
                } else {
                    parent.left = null;
                }
            } else {
                parent = parent.left;
                while (parent.right != null) {
                    if (parent.right == max) {
                        if (max.left != null) {
                            parent.right = max.left;
                        } else {
                            parent.right = null;
                        }
                    } else {
                        parent = parent.right;
                    }
                }
            }
            // Switch the node to be deleted with the Max from the left subtree
            max.left = node.left;
            max.right = node.right;
            mergedTrees = max;
        }
        return mergedTrees;
    }

    protected BSTreeNode findMax(BSTreeNode node) throws LUTKeyException {

        if (node == null) {
            throw new LUTKeyException();
        }

        if (node.right == null) {
            return node;
        }
        return findMax(node.right);
    }

    protected String treeString(BSTreeNode node) {
        if (node == null) {
            return "";
        }
        Entry lutEntry = node.kvPair;
        String thisNode =
                lutEntry.key.toString() + ":"
                        + lutEntry.value;
        return "(" + treeString(node.left) + thisNode
                + treeString(node.right) + ")";
    }
}
