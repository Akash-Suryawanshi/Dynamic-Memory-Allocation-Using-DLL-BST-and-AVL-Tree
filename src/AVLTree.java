// Class: Height balanced AVL Tree
// Binary Search Tree


public class AVLTree extends BSTree {

    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree

    public AVLTree() {
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.

    }

    public AVLTree(int address, int size, int key) {
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.

    public AVLTree Insert(int address, int size, int key) {
        if (getroot() == null) {
            AVLTree element = new AVLTree(address, size, key);
            this.right = element;
            element.parent = this;
            element.height++;
            return element;
        }
        return insert_element(address, size, key);
    }

    public void rebalance(AVLTree first_imbalance, AVLTree grandchild) {
        AVLTree cur = first_imbalance.parent;
        if (first_imbalance.left != null && first_imbalance.left.left != null) {
            if (first_imbalance.left.left == grandchild) {
                AVLTree mid = first_imbalance.left;
                first_imbalance.left = mid.right;
                if (mid.right != null) {
                    mid.right.parent = first_imbalance;
                }
                if (first_imbalance.isLeft()) {
                    cur.left = mid;
                } else {
                    cur.right = mid;
                }
                mid.right = first_imbalance;
                first_imbalance.parent = mid;
                mid.parent = cur;
                first_imbalance.height--;
            }
        }
        if (first_imbalance.right != null && first_imbalance.right.right != null) {
            if (first_imbalance.right.right == grandchild) {
                AVLTree mid = first_imbalance.right;
                first_imbalance.right = mid.left;
                if (mid.left != null) {
                    mid.left.parent = first_imbalance;
                }
                if (first_imbalance.isLeft()) {
                    cur.left = mid;
                } else {
                    cur.right = mid;
                }
                mid.parent = cur;
                mid.left = first_imbalance;
                first_imbalance.parent = mid;
                first_imbalance.height--;
            }
        }
        if (first_imbalance.left != null && first_imbalance.left.right != null) {
            if (first_imbalance.left.right == grandchild) {
                AVLTree mid = first_imbalance.left;
                mid.right = grandchild.left;
                if (grandchild.left != null) {
                    grandchild.left.parent = mid;
                }
                first_imbalance.left = grandchild.right;
                if (grandchild.right != null) {
                    grandchild.right.parent = first_imbalance;
                }
                if (first_imbalance.isLeft()) {
                    cur.left = grandchild;
                } else {
                    cur.right = grandchild;
                }
                grandchild.parent = cur;
                grandchild.left = mid;
                grandchild.right = first_imbalance;
                first_imbalance.parent = grandchild;
                mid.parent = grandchild;
                first_imbalance.height--;
                mid.height--;
                grandchild.height++;
            }
        }
        if (first_imbalance.right != null && first_imbalance.right.left != null) {
            if (first_imbalance.right.left == grandchild) {
                AVLTree mid = first_imbalance.right;
                mid.left = grandchild.right;
                if (grandchild.right != null) {
                    grandchild.right.parent = mid;
                }
                first_imbalance.right = grandchild.left;
                if (grandchild.left != null) {
                    grandchild.left.parent = first_imbalance;
                }
                if (first_imbalance.isLeft()) {
                    cur.left = grandchild;
                } else {
                    cur.right = grandchild;
                }
                grandchild.parent = cur;
                grandchild.left = first_imbalance;
                grandchild.right = mid;
                first_imbalance.parent = grandchild;
                mid.parent = grandchild;
                first_imbalance.height--;
                mid.height--;
                grandchild.height++;

            }
        }
    }

    public boolean isLeft() {
        return this.parent.left == this;
    }

    public AVLTree get_grandchild(AVLTree element) {
        AVLTree cur = this;
        for (int i = 0; i < 2; i++) {
            if (element.key <= cur.key) {
                if (element.key == cur.key) {
                    if (element.address <= cur.address) {
                        cur = cur.left;
                    } else {
                        cur = cur.right;
                    }
                } else {
                    cur = cur.left;
                }
            } else {
                cur = cur.right;
            }
        }
        return cur;
    }

//    public AVLTree first_imbalance(){
//        AVLTree cur = this.parent;
//        while (cur.parent!=null) {
//            if (cur.left==null) {
//                if (cur.right.height > 1) return cur;
//            }
//            else if (cur.right==null) {
//                if (cur.left.height > 1) return cur;
//            }
//            else {
//                if (Math.abs(cur.left.height - cur.right.height) > 1) return cur;
//            }
//            cur = cur.parent;
//        }
//        return null;
//    }

    public AVLTree insert_element(int address, int size, int key) {
        boolean need_to_balance = true;
        AVLTree element = new AVLTree(address, size, key);
        AVLTree cur = getroot();
        while (true) {
            if (key < cur.key) {
                if (cur.left == null) {
                    cur.left = element;
                    element.parent = cur;
                    element.height++;
                    if (cur.right != null) {
                        need_to_balance = false;
                    }
                    break;
                }
                cur = cur.left;
            } else if (key == cur.key) {
                if (address <= cur.address) {
                    if (cur.left == null) {
                        cur.left = element;
                        element.parent = cur;
                        element.height++;
                        if (cur.right != null) {
                            need_to_balance = false;
                        }
                        break;
                    } else {
                        cur = cur.left;
                    }
                } else {
                    if (cur.right == null) {
                        cur.right = element;
                        element.parent = cur;
                        element.height++;
                        if (cur.left != null) {
                            need_to_balance = false;
                        }
                        break;
                    } else {
                        cur = cur.right;
                    }
                }
            } else {
                if (cur.right == null) {
                    cur.right = element;
                    element.parent = cur;
                    element.height++;
                    if (cur.left != null) {
                        need_to_balance = false;
                    }
                    break;
                } else {
                    cur = cur.right;
                }
            }
        }
        if (need_to_balance) {
            while (cur.parent != null) {
                if (cur.left == null || cur.right == null) {
                    if (cur.left == null) {
                        if (cur.right.height > 1) {
                            break;
                        } else {
                            cur.height++;
                            cur = cur.parent;
                        }
                    } else {
                        if (cur.left.height > 1) {
                            break;
                        } else {
                            cur.height++;
                            cur = cur.parent;
                        }
                    }
                } else {
                    if (Math.abs(cur.left.height - cur.right.height) > 1) break;
                    cur.height++;
                    cur = cur.parent;
                }
            }
            if (cur.parent == null) return element;
            AVLTree grandchild = cur.get_grandchild(element);
            rebalance(cur, grandchild);
        }
        return element;
    }

    public AVLTree getroot() {
        AVLTree cur = this;
        while (cur.parent != null) {
            cur = cur.parent;
        }
        return cur.right;
    }

    public boolean Delete(Dictionary e) {
        AVLTree node = delete(e);
        if (node==null) return false;
        if (node.parent==null) return true;
        if (node.parent.address==-1) {
            node.height_adjust();
            return true;
        }
        while (node.parent!=null) {
            if (node.left==null || node.right==null) {
                node.height_adjust();
            }
            else {
                if (Math.abs(node.left.height-node.right.height) > 1) {
                    int maximum_y, maximum_z;
                    maximum_y = Math.max(node.left.height, node.right.height);
                    AVLTree y, z;
                    if (node.left.height==maximum_y) {
                        y = node.left;
                    }
                    else {
                        y = node.right;
                    }
                    if (y.left==null) {
                        z = y.right;
                    }
                    else if (y.right==null) {
                        z = y.left;
                    }
                    else {
                        maximum_z = Math.max(y.left.height, y.right.height);
                        if (y.left.height == maximum_z) {
                            if (y.isLeft()) {
                                z = y.left;
                            } else {
                                z = y.right;
                            }
                        } else {
                            z = y.right;
                        }
                    }
                    rebalance(node, y, z);
                }
                else {
                    node.height_adjust();
                }
            }
            node = node.parent;
        }
        return true;
    }

    public void height_adjust() {
        if (this.left==null&&this.right==null) {
            this.height = 1;
        }
        else {
            if (this.left==null) {
                this.height = this.right.height+1;
            }
            else if (this.right==null) {
                this.height = this.left.height+1;
            }
            else {
                this.height = Math.max(this.left.height, this.right.height) + 1;
            }
        }
    }

    public void rebalance(AVLTree first_imbalance, AVLTree y, AVLTree grandchild) {
        AVLTree cur = first_imbalance.parent;
        if (first_imbalance.left != null && first_imbalance.left.left != null) {
            if (first_imbalance.left.left == grandchild) {
                AVLTree mid = y;
                first_imbalance.left = mid.right;
                if (mid.right != null) {
                    mid.right.parent = first_imbalance;
                }
                if (first_imbalance.isLeft()) {
                    cur.left = mid;
                } else {
                    cur.right = mid;
                }
                mid.right = first_imbalance;
                first_imbalance.parent = mid;
                mid.parent = cur;
                first_imbalance.height_adjust();
                mid.height_adjust();
            }
        }
        if (first_imbalance.right != null && first_imbalance.right.right != null) {
            if (first_imbalance.right.right == grandchild) {
                AVLTree mid = y;
                first_imbalance.right = mid.left;
                if (mid.left != null) {
                    mid.left.parent = first_imbalance;
                }
                if (first_imbalance.isLeft()) {
                    cur.left = mid;
                } else {
                    cur.right = mid;
                }
                mid.parent = cur;
                mid.left = first_imbalance;
                first_imbalance.parent = mid;
                first_imbalance.height_adjust();
                mid.height_adjust();
            }
        }
        if (first_imbalance.left != null && first_imbalance.left.right != null) {
            if (first_imbalance.left.right == grandchild) {
                AVLTree mid = y;
                mid.right = grandchild.left;
                if (grandchild.left != null) {
                    grandchild.left.parent = mid;
                }
                first_imbalance.left = grandchild.right;
                if (grandchild.right != null) {
                    grandchild.right.parent = first_imbalance;
                }
                if (first_imbalance.isLeft()) {
                    cur.left = grandchild;
                } else {
                    cur.right = grandchild;
                }
                grandchild.parent = cur;
                grandchild.left = mid;
                grandchild.right = first_imbalance;
                first_imbalance.parent = grandchild;
                mid.parent = grandchild;
                first_imbalance.height_adjust();
                mid.height_adjust();
                grandchild.height_adjust();
            }
        }
        if (first_imbalance.right != null && first_imbalance.right.left != null) {
            if (first_imbalance.right.left == grandchild) {
                AVLTree mid = first_imbalance.right;
                mid.left = grandchild.right;
                if (grandchild.right != null) {
                    grandchild.right.parent = mid;
                }
                first_imbalance.right = grandchild.left;
                if (grandchild.left != null) {
                    grandchild.left.parent = first_imbalance;
                }
                if (first_imbalance.isLeft()) {
                    cur.left = grandchild;
                } else {
                    cur.right = grandchild;
                }
                grandchild.parent = cur;
                grandchild.left = first_imbalance;
                grandchild.right = mid;
                first_imbalance.parent = grandchild;
                mid.parent = grandchild;
                first_imbalance.height_adjust();
                mid.height_adjust();
                grandchild.height_adjust();
            }
        }
    }

    public AVLTree delete(Dictionary e) {
        AVLTree find = FindExactElement(e.address, e.size, e.key);
        if (find == null) {
            return null;
        } else if (find.left == null && find.right == null) {
            AVLTree cur = find.parent;
            find.remove();
            return cur;
        } else if (find.left == null || find.right == null) {
            if (find.left == null) {
                if (find.isLeft()) {
                    find.right.parent = find.parent;
                    find.parent.left = find.right;
                    return find.parent;
                } else {
                    find.right.parent = find.parent;
                    find.parent.right = find.right;
                    return find.parent;
                }
            } else {
                if (find.isLeft()) {
                    find.left.parent = find.parent;
                    find.parent.left = find.left;
                    return find.parent;
                } else {
                    find.left.parent = find.parent;
                    find.parent.right = find.left;
                    return find.parent;
                }
            }
        } else {
            AVLTree successor = find.successor();
            AVLTree d0 = new AVLTree(successor.address, successor.size,successor.key);
            AVLTree parent = successor.delete(successor);
            find.key = d0.key;
            find.address = d0.address;
            find.size = d0.size;
            return parent;
        }
    }

    public void remove() {
        AVLTree cur = this;
        if (cur.parent==null) return;
        else if (cur.isLeft()) {
            cur.parent.left = null;
        }
        else {
            cur.parent.right = null;
        }
        cur.parent = null;
    }

    public AVLTree successor() {
        AVLTree find = this;
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

    public AVLTree FindExactElement(int address, int size, int key) {
        AVLTree cur = this;
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

    public AVLTree Find(int key, boolean exact) {     // finds from where successor is called
        AVLTree cur = this;
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
            AVLTree first = getFirst();
            while (first!=null) {
                if (first.key >= key) return first;
                first = first.getNext();
            }
        }
        return null;
    }

    public AVLTree getFirst() {
        if (this.parent == null && this.address == -1) {
            if (this.right!=null){
                return this.right.getFirst();
            }
            else return null;
        }
        AVLTree cur = this.getroot();
        if (cur==null) return null;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur;
    }

    public AVLTree getNext() {
        if (this.address == -1 && this.parent == null) return null;
        return successor();
    }

    public boolean sanity() {
        if (!preorder_sanity(this)) return false;
        AVLTree cur = getFirst();
        if (this.address==-1 &&this.size==-1&&this.key==-1) {
            if (this.left!=null||this.parent!=null) return false;
        }
        while (cur.getNext()!=null) {
            if (cur.getNext().key< cur.key) return false;
            cur = cur.getNext();
        }
        return true;
    }

    public boolean preorder_sanity(AVLTree root) {
        if (root==null) return true;
        if (root.left==null || root.right==null) {
            if (root.left==null && root.right==null) {
                return true;
            }
            else if (root.left==null) {
                if (root.address==-1) return true;
                else {
                    if (root.right.parent != root) return false;
                    if (root.height != root.right.height + 1) return false;
                }
            }
            else {
                if (root.left.parent!=root) return false;
                if (root.height!=root.left.height+1) return false;
            }
        }
        else {
            if (root.left.parent!=root||root.right.parent!=root) return false;
            if (Math.abs(root.left.height-root.right.height) > 1) return false;
        }
        boolean left = preorder_sanity(root.left);
        boolean right = preorder_sanity(root.right);
        if (!left||!right) return false;
        return true;
    }

    public void inorder(AVLTree avlTree) {
        if (avlTree==null) return;
        inorder(avlTree.left);
        System.out.print(avlTree.key + " ");
        inorder(avlTree.right);
    }

    public static void main(String[] args) {
        AVLTree a = new AVLTree();
//        a.Insert(1, 1, 1);
//        a.Insert(2, 2, 2);
//        a.Insert(6, 6, 6);
//        System.out.println(a.right.right.right.height);
        a.Insert(1, 1, 1);
        a.Insert(2, 2, 2);
        a.Insert(0, 1, 1);
        a.Insert(6, 6, 6);
        a.Insert(4, 4, 4);
        a.Insert(7, 7, 7);
//        a.Insert(8, 8, 8);
        System.out.println(a);
        System.out.println(a.right.height);
        Dictionary d = new AVLTree(1, 1, 1);
        a.Delete(d);
        a.inorder(a);
        System.out.println();
        System.out.println(a.preorder_sanity(a));


    }
}


