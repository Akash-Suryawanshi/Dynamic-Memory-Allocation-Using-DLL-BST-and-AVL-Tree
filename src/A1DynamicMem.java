// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {

    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).

    public int Allocate(int blockSize) {
        if (blockSize<=0) return -1;
        Dictionary approx = freeBlk.Find(blockSize, false);
        if (approx != null){
            int remaining = approx.size - blockSize;
            if (remaining == 0) {
                allocBlk.Insert(approx.address, blockSize, approx.address);
                freeBlk.Delete(approx);
                return approx.address;
            }
            if (remaining > 0) {
                freeBlk.Insert(approx.address+ blockSize, remaining, remaining);
                allocBlk.Insert(approx.address, blockSize, approx.address);
                freeBlk.Delete(approx);
                return approx.address;
            }
        }
        return -1;
    }

    public int Free(int startAddr) {
        if (allocBlk.Find(startAddr, true) != null) {
            Dictionary found = allocBlk.Find(startAddr, true);
            freeBlk.Insert(startAddr, found.size, found.size);
            allocBlk.Delete(found);
            return 0;
        }
        return -1;
    }

}