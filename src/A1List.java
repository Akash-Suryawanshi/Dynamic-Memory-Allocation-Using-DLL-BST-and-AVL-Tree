// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

import java.io.DataInputStream;

public class A1List extends List {

    private A1List next; // Next Node
    private A1List prev;  // Previous Node

    public A1List(int address, int size, int key) {
        super(address, size, key);
    }

    public A1List() {
        super(-1, -1, -1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1, -1, -1); // Initiate the tail sentinel

        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key) {
        if (this.next == null) {
            A1List cur = this.prev;
            A1List element = new A1List(address, size, key);
            element.next = cur.next;
            cur.next = element;
            element.prev = cur;
            element.next.prev = element;
            return element;

        }
        A1List element = new A1List(address, size, key);
        element.next = this.next;
        this.next = element;
        element.prev = this;
        element.next.prev = element;
        return element;
    }

    public boolean Delete(Dictionary d) {
        if (this.prev == null && this.next.next == null) {
            return false;
        }
        if (this.next == null && this.prev.prev == null) {
            return false;
        }

        A1List forward = this;
        A1List backward = this;
        if (forward.prev != null && forward.next != null) {
            while (forward.address != -1) {
                if (forward.key == d.key) {
                    if (forward.address == d.address) {
                        if (forward.size == d.size) {
                            forward.prev.next = forward.next;
                            forward.next.prev = forward.prev;
                            return true;
                        }
                    }
                }
                forward = forward.next;
            }
            while (backward.address != -1) {
                if (backward.key == d.key) {
                    if (backward.address == d.address) {
                        if (backward.size == d.size) {
                            backward.prev.next = backward.next;
                            backward.next.prev = backward.prev;
                            return true;
                        }
                    }
                }
                backward = backward.prev;
            }
        } else {
            if (forward.prev == null) {
                forward = forward.next;
                while (forward.address != -1) {
                    if (forward.key == d.key) {
                        if (forward.address == d.address) {
                            if (forward.size == d.size) {
                                forward.prev.next = forward.next;
                                forward.next.prev = forward.prev;
                                return true;
                            }
                        }
                    }
                    forward = forward.next;
                }
            } else {
                backward = backward.prev;
                while (backward.address != -1) {
                    if (backward.key == d.key) {
                        if (backward.address == d.address) {
                            if (backward.size == d.size) {
                                backward.prev.next = backward.next;
                                backward.next.prev = backward.prev;
                                return true;
                            }
                        }
                    }
                    backward = backward.prev;
                }
            }
        }
        return false;
    }

    public A1List Find(int k, boolean exact) {
        A1List forward = this;
        A1List backward = this;
        if (exact) {
            if (forward.next != null) {
                while (forward.next != null) {
                    if (forward.key == k) {
                        return forward;
                    }
                    forward = forward.next;
                }
            }
            if (backward.prev != null) {
                while (backward.prev != null) {
                    if (backward.key == k) {
                        return backward;
                    }
                    backward = backward.prev;
                }
            }
        } else {
            if (forward.next != null) {
                while (forward.next != null) {
                    if (forward.key >= k) {
                        return forward;
                    }
                    forward = forward.next;
                }
            }
            if (backward.prev != null) {
                while (backward.prev != null) {
                    if (backward.key >= k) {
                        return backward;
                    }
                    backward = backward.prev;
                }
            }
        }
        return null;
    }

    public A1List getFirst() {
        if (this.prev == null && this.next.next == null) {
            return null;
        }
        if (this.next == null && this.prev.prev == null) {
            return null;
        } else {
            A1List cur = this;
            while (cur.prev != null) {
                cur = cur.prev;
            }
            return cur.next;
        }
    }

    public A1List getNext() {
        if (this.next != null) {
            return this.next;
        }
        return null;
    }

    public boolean sanity() {
        // Loop detection
        A1List front1 = this;
        A1List front2 = this;
        A1List back1 = this;
        A1List back2 = this;

        while (front1.next != null && front2.next != null && front2.next.next != null) {
            front1 = front1.next;
            front2 = front2.next.next;
            if (front1 == front2) {
                return false;
            }
        }
        while (back1.prev != null && back2.prev != null && back2.prev.prev != null) {
            back1 = back1.prev;
            back2 = back2.prev.prev;
            if (back1 == back2) {
                return false;
            }
        }

        //

        if (this.address==-1 && this.size==-1 && this.key==-1) {
            if (this.prev==null){
                if (this.next==null) return false;
            }
            else {
                if (this.next!=null) return false;
            }
        }

        if (this.prev==null && this.next ==null) return false;
        // for every element in DDL, the prev of next should be itself.
        A1List forward = this;
        A1List backward = this;
        if (this.prev!=null && this.next!=null) {
            while (forward.address != -1) {
                if (forward.next.prev != forward) {
                    return false;
                }
                if (forward.address < 0|| forward.size < 0) return false;     // address and size should be positive
                forward = forward.next;
            }
            if (forward.key != -1 || forward.size != -1) return false;    // checking if sentinel node has all the entries as -1.
            if (forward.next!=null) return false;                       // since tail sentinel node is reached, next should be null.
            while (backward.address != -1) {
                if (backward.prev.next != backward) {
                    return false;
                }
                if (backward.address < 0 || backward.size < 0) return false;
                backward = backward.prev;
            }
            if (backward.key != -1 || backward.size != -1) return false;
            if (backward.prev!=null) return false;
        }
        if (this.prev==null) {
            if (forward.next.prev!=forward) return false;
            forward = forward.next;
            while (forward.address != -1) {
                if (forward.next.prev != forward) {
                    return false;
                }
                if (forward.address < 0|| forward.size < 0) return false;      // address and size should be positive
                forward = forward.next;
            }
            if (forward.key != -1 || forward.size != -1) return false;    // checking if sentinel node has all the entries as -1.
            if (forward.next!=null) return false;
        }
        if (this.next==null) {
            if (backward.prev.next!=backward) return false;
            backward = backward.prev;
            while (backward.address != -1) {
                if (backward.prev.next != backward) {
                    return false;
                }
                if (backward.address < 0 || backward.size < 0) return false;
                backward = backward.prev;
            }
            if (backward.key != -1 || backward.size != -1) return false;
            if (backward.prev!=null) return false;
        }
        return true;
    }

//    public static void main(String[] args) {
//        A1List list = new A1List();
//        A1List x = list.Insert(1, 1, 1);
////        list.Insert(2, 2, 2);
////        list.Insert(1, 6, 4);
////        Dictionary d = new A1List(1, -1, 4);
////        A1List a = new A1List(6, 7, 8);
////        list.Delete(d);
////        System.out.println(list.next.address);
////        A1List cur = list.Find(1, true);
////        System.out.println(cur.size);
////        a.next = list;
////        a.prev = list.next;
////        list.next.next = a;
////        list.next = a;
//        System.out.println(x.address);
//    }
}


