// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

import java.util.Objects;

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.

    public BSTree() {
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }

    public BSTree(int address, int size, int key) {
        super(address, size, key);
    }


    public BSTree Insert(int address, int size, int key) {
        BSTree element = new BSTree(address, size, key);
        BSTree cur = this;
        while (true) {
            if (key < cur.key) {
                if (cur.left == null) {
                    cur.left = element;
                    element.parent = cur;
                    break;
                }
                cur = cur.left;
            }
            else if (key==cur.key) {
                if (cur.address < address) {
                    if (cur.right == null) {
                        cur.right = element;
                        element.parent = cur;
                        break;
                    }
                    cur = cur.right;
                }
                else {
                    if (cur.left == null) {
                        cur.left = element;
                        element.parent = cur;
                        break;
                    }
                    cur = cur.left;
                }
            }
            else {
                if (cur.right == null) {
                    cur.right = element;
                    element.parent = cur;
                    break;
                }
                cur = cur.right;
            }
        }
        return element;
    }


    public BSTree FindExactElement(int address, int size, int key) {
        BSTree cur = this;
        while (cur != null) {
//            System.out.println(cur.address);
            if (key <= cur.key) {
                if (cur.address == address && cur.size == size && cur.key==key) return cur;
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return null;
    }

    public BSTree getroot() {
        BSTree cur = this;
        while (cur.parent != null) {
            cur = cur.parent;
        }
        return cur.right;
    }

    public boolean Delete(Dictionary e) {
        BSTree find = FindExactElement(e.address, e.size, e.key);
        if (find == null) {
            return false;
        } else if (find.left == null && find.right == null) {
            find.remove();
        } else if (find.left == null || find.right == null) {
            if (find.left == null) {
                if (find.parent.left == find) {
                    find.right.parent = find.parent;
                    find.parent.left = find.right;
                } else {
                    find.right.parent = find.parent;
                    find.parent.right = find.right;
                }
            } else {
                if (find.parent.right == find) {
                    find.left.parent = find.parent;
                    find.parent.right = find.left;
                } else {
                    find.left.parent = find.parent;
                    find.parent.left = find.left;
                }
            }
        } else {
            BSTree successor = find.successor();
            successor.Delete(successor);
            find.key = successor.key;
            find.address = successor.address;
            find.size = successor.size;
        }
        return true;
    }

    public void remove() {
        BSTree cur = this;
        if (cur.parent==null) return;
        else if (cur.parent.left==cur) {
            cur.parent.left = null;
        }
        else {
            cur.parent.right = null;
        }
        cur.parent = null;
    }

    public BSTree successor() {
        BSTree find = this;
        if (find.right == null) {
            while (find.parent != null && find.parent.right == find) {
                find = find.parent;
            }
            if (find.address == -1) {
                return null;
            } else {
                return find.parent;
            }
        }
        else {
            find = find.right;
            while (find.left != null) {
                find = find.left;
            }
            return find;
        }
    }

    public BSTree Find(int key, boolean exact) {     // finds from where successor is called
        BSTree cur = this;
        if (exact) {
            while (cur != null) {
                if (key == cur.key) return cur;
                else if (key < cur.key) {
                    cur = cur.left;
                } else {
                    cur = cur.right;
                }
            }
        }
        else {
            BSTree first = getFirst();
            while (first!=null) {
                if (first.key >= key) return first;
                first = first.getNext();
            }
        }
        return null;
    }

//    public BSTree where_to_be_inserted(int key) {
//        BSTree cur = this;
//        while (true) {
//            if (key <= cur.key) {
//                if (cur.left == null) {
//                    return cur;
//                }
//                cur = cur.left;
//            } else {
//                if (cur.right == null) {
//                    return cur;
//                }
//                cur = cur.right;
//            }
//        }
//    }

    public BSTree getFirst() {
        if (this.parent == null && this.address == -1) {
            if (this.right!=null){
                return this.right.getFirst();
            }
            else return null;
        }
        BSTree cur = this.getroot();
        if (cur==null) return null;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur;
    }

    public BSTree getNext() {
        if (this.address == -1 && this.parent == null) return null;
        return successor();
    }

    public boolean inorder_check(BSTree b) {
        if (b != null) {
            if (b.left==null&&b.right==null&&b.parent==null&&b.address==-1) return false;
            if (b.left==null&&b.right==null) return false;
            if (b.left!=null&&b.right!=null) {
                if (b.left.parent!=b || b.right.parent!=b) return true;
            }
//            else if (Objects.requireNonNullElseGet(b.left, () -> b.right).parent != b) return true;
            if (inorder_check(b.left)) return true;
            return inorder_check(b.right);
        }
        return false;
    }

    public boolean sanity() {
        if (inorder_check(this)) return false;
        BSTree cur = getFirst();
        if (this.address==-1 &&this.size==-1&&this.key==-1) {
            if (this.left!=null||this.parent!=null) return false;
        }
        while (cur.getNext()!=null) {
            if (cur.getNext().key< cur.key) return false;
            cur = cur.getNext();
        }
        return true;
    }

//    public static void main(String[] args) {
//        BSTree b = new BSTree();
//        b.Insert(1, 1, 1);
//        b.Insert(0, 0, 0);
//        b.Insert(7, 7, 7);
//        b.Insert(4, 4, 4);
//        b.Insert(2, 2, 2);
//        b.Insert(5, 5, 5);
//        b.Insert(14, 14, 14);
//        b.Insert(4, 4, 4);
//        BSTree k = b.getFirst();
//        while (k.getNext()!=null) {
//            System.out.print(k.key);
//            k = k.getNext();
//        }
//        Dictionary d = new BSTree(7, 7, 7);
//        b.Delete(d);
////        BSTree find = b.Find(4, true);
////        BSTree x = b.successor();
////        System.out.println(x.address);
////        b.inorder(b);
//        System.out.print(b.sanity());
//        b.Delete(d);
////        b.inorder(b);
//        System.out.println();
//    }
}
