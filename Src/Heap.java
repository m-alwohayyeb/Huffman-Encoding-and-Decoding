
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Heap<M extends Comparable<M>, N extends Comparable<N>> {

    // -------------------- Attributes -------------------- \\
    private Pair<M, N>[]    heap;       // An array representation of a heap which stores genric data and priority value.
    private int             size;       // Stores the current size of the array heap.
    private int             MaxSize;    // Stores the max size of the array heap.

    // -------------------- Constructor -------------------- \\
    @SuppressWarnings("unchecked")
    public Heap(int MaxSize) {
        this.heap = new Pair[MaxSize + 1];
        this.heap[0] = null;
        this.size = 0;
        this.MaxSize = MaxSize + 1;
    }

    // -------------------- Methods -------------------- \\

    /* 
        leftChild: This method returns the left child index of the Pair heap[i]
    */
    public int leftChild(int i) {
        return (i * 2);
    }

    /* 
        rightChild: This method returns the right child index of the Pair heap[i]
    */
    public int rightChild(int i) {
        return (i * 2) + 1;
    }

    /* 
        parent: This method returns the parent index of the Pair heap[i]
    */
    public int parent(int i) {
        return i / 2;
    }

    /* 
        isLeaf: returns true when heap[i] is a leaf and false otherwise.
    */
    protected boolean isLeaf(int i) {
        return i > size / 2 && i <= size;
    }

    /* 
        swap: This method swaps the two given Pair positions  
    */
    public void swap(int i, int j) {
        Pair<M, N> tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    /* 
        getSize: This method returns the size of the Heap 
    */
    public int getSize() {
        return size;
    }

    /* 
        minHeapify: This method will recive a postion within the heap array
                    it then will ensure that the sub graph of that postion
                    is a heap.
    */
    private void minHeapify(int pos) {

        if (( leftChild(pos) < MaxSize && heap[leftChild(pos)] != null) || (rightChild(pos) < MaxSize && heap[rightChild(pos)] != null)) {
            if (( leftChild(pos) < MaxSize && heap[leftChild(pos)] == null )
                    && (heap[pos].getSecond().compareTo(heap[rightChild(pos)].getSecond()) > 0)) {
                swap(pos, rightChild(pos));
                return;
            }
            if (rightChild(pos) < MaxSize && heap[rightChild(pos)] == null
                    && (heap[pos].getSecond().compareTo(heap[leftChild(pos)].getSecond()) > 0)) {
                swap(pos, leftChild(pos));
                return;
            }
        }
        if ((leftChild(pos) < MaxSize && heap[leftChild(pos)] != null) && (rightChild(pos) < MaxSize && heap[rightChild(pos)] != null)) {
            if ((heap[pos].getSecond().compareTo(heap[leftChild(pos)].getSecond()) > 0)
                    || ((heap[pos].getSecond().compareTo(heap[rightChild(pos)].getSecond()) > 0))) {
                if (heap[leftChild(pos)].getSecond().compareTo(heap[rightChild(pos)].getSecond()) <= 0) {
                    swap(pos, leftChild(pos));
                    minHeapify(leftChild(pos));
                } else {
                    swap(pos, rightChild(pos));
                    minHeapify(rightChild(pos));
                }
            }
        }
    }

    /* 
        minHeap: This method performs minHeapify for all non leaf Pairs
    */
    public void minHeap() {
        for (int i = (size / 2); i >= 1; i--) {
            minHeapify(i);
        }
    }

    /* 
        insert: This methods creates a Pair(without Child assignment) of M and N 
                and inserts it to the heap.
    */
    public void insert(M m, N n) {
        if (size >= MaxSize) {
            return;
        }
        Pair<M, N> newElement = new Pair<M, N>(m, n);
        heap[++size] = newElement;
        int current = size;
        while (heap[current] != null && parent(current) > 0
                && heap[current].getSecond().compareTo(heap[parent(current)].getSecond()) < 0) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    /* 
        insert: This methods creates a Pair(with Child assignment) of M and N 
                and inserts it to the heap , with Child assignment.
    */
    public void insert(M m, N n, Pair<M, N> left, Pair<M, N> right) {
        if (size >= MaxSize) {
            return;
        }
        Pair<M, N> newElement = new Pair<M, N>(m, n, left, right);
        heap[++size] = newElement;
        int current = size;
        while (heap[current] != null && parent(current) > 0
                && heap[current].getSecond().compareTo(heap[parent(current)].getSecond()) < 0) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    /* 
        peek: This method returns the root's value.
    */
    public Pair<M, N> peek() {
        return heap[1];
    }

    /* 
        remove: This method removes the minimum valued Pair(root) , and returns its value.
    */
    public Pair<M, N> remove() {

        if (size <= 0)
            return null;

        Pair<M, N> removedElement = heap[1];
        heap[1] = heap[size];
        heap[size] = null;
        size--;
        minHeap();

        return removedElement;
    }

    /*
        toGraph: This method is used to generate HeapGraph.dot file for the heap tree to be represented using GraphViz.
    */
    public void toGraph() {

        BufferedWriter buffer;
        try {

            buffer = new BufferedWriter(new FileWriter("HeapGraph.dot"));
            buffer.write("digraph MyGraph {\n\n");

            recToGraph(buffer, 1);

            buffer.write("}\n");
            buffer.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /*
        recToGraph: This helper method is used to write the relations for 
                    each Pair to HeapGraph.dot file to be represented using GraphViz.
    */
    private void recToGraph(BufferedWriter buffer, int pos) throws IOException {

        for (int i = 1; i <= size; i++) {
            int c = (Character)heap[i].getFirst() - 0;
            buffer.write("\t" + c + " [label= \""
                    + heap[i].getFirst() + ":" + heap[i].getSecond() + "\"]" + "\n");
        }
        for (int i = 1; i <= size / 2; i++) {
            if (leftChild(i) < size + 1) {
                int c = (Character)heap[i].getFirst() - 0;
                int h = (Character)heap[leftChild(i)].getFirst() - 0;
                buffer.write("\t" + c + "->"
                        + h + "\n");
            }
            if (rightChild(i) < size + 1){
                int c = (Character)heap[i].getFirst() - 0;
                int h = (Character)heap[rightChild(i)].getFirst() - 0;
                buffer.write("\t" + c + "->"
                        + h + "\n");
                    }
        }
    }

}