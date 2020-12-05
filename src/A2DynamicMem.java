// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {

    public A2DynamicMem() {
        super();
    }

    public A2DynamicMem(int size) {
        super(size);
    }

    public A2DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 
    //Your BST (and AVL tree) implementations should obey the property that keys in the left subtree <= root.key < keys
    // in the right subtree. How is this total order between blocks defined? It shouldn't be a problem when using key=address
    // since those are unique (this is an important invariant for the entire assignment123 module). When using key=size, use
    // address to break ties i.e. if there are multiple blocks of the same size, order them by address. Now think outside the
    // scope of the allocation problem and think of handling tiebreaking in blocks, in case key is neither of the two.


    public void Defragment() {
        AVLTree avlTree = new AVLTree();
        Dictionary cur = freeBlk.getFirst();
        if (cur==null) return;
        while (cur != null) {
            avlTree.Insert(cur.address, cur.size, cur.address);
            cur = cur.getNext();
        }
        AVLTree elem = avlTree.getFirst();
        while (elem != null) {
            AVLTree second = elem.getNext();
            if (second != null && elem.address + elem.size == second.address) {
                Dictionary d1 = new AVLTree(elem.address, elem.size, elem.size);
                Dictionary d2 = new AVLTree(second.address, second.size, second.size);
                Dictionary d10 = new AVLTree(elem.address, elem.size, elem.address);
                Dictionary d20 = new AVLTree(second.address, second.size, second.size);
                elem.Delete(d10);
                second.Delete(d20);
                freeBlk.Delete(d1);
                freeBlk.Delete(d2);
                elem = avlTree.Insert(d1.address, d1.size + d2.size, d1.address);
                freeBlk.Insert(elem.address, elem.size, elem.size);
            } else {
                elem = elem.getNext();
            }
        }
    }
}