import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Pair<M extends Comparable<M>, N extends Comparable<N>> {

    // -------------------- Attributes -------------------- \\

    private M           first;  // A generic value.
    private N           second; // A generic value.
    public Pair<M, N>   left;   // Stores the left child.
    public Pair<M, N>   right;  // Stores the right child.

    private int         countNulls;     // Counter for each null in the whole tree (Used for representaion)


    // -------------------- Constructors -------------------- \\

    public Pair() {
        this.first = null;
        this.second = null;
        this.left = null;
        this.right = null;
    }

    public Pair(M m, N n) {
        this.first = m;
        this.second = n;
        this.left = null;
        this.right = null;
    }

    public Pair(M m, N n, Pair<M, N> left, Pair<M, N> right) {
        this.first = m;
        this.second = n;
        this.left = left;
        this.right = right;
    }



    // -------------------- Methods -------------------- \\

    /*
     *   isLeaf: This method returns true if a this node is a leaf 
     *           and false otherwise.
     */
    public boolean isLeaf() {
        return (this.left == null && this.right == null);
    }

    /*
     *   getFirst: This method returns the first generic value.
     */
    public M getFirst() {
        return this.first;
    }

    /*
     *   setFirst: This method recives a generic value 
     *             it then will assign that value to 
     *             the first generic attribute.
     */
    public void setFirst(M m) {
        this.first = m;
    }

    /*
     *   getSecond: This method returns the second generic value.
     */
    public N getSecond() {
        return this.second;
    }

    /*
     *   setSecond: This method recives a generic value 
     *              it then will assign that value to 
     *              the second generic attribute.
     */
    public void setSecond(N n) {
        this.second = n;
    }

    /*
     *   equals: This method returns true if this and the passed 
     *           pair equal each others' values and false otherwise.
     */
    public boolean equals(Pair<M, N> o) {
        return (this.first.equals(o.first) && this.second.equals(o.second));
    }

    /*
     *  toString: This method returns the defult formatting for printing a Pair object.
     */
    @Override
    public String toString() {
        return "first = '" + getFirst() + "'" + ", second = '" + getSecond() + "'";
    }

    /*
     *   toGraph: This method outputs the current Pair information with the information of it's decendents.
     */
    public void toGraph() {
        BufferedWriter buffer;
        try {

            buffer = new BufferedWriter(new FileWriter("HuffmanTree.dot"));
            buffer.write("digraph MyGraph {\n");
            recToGraph(buffer, this);

            buffer.write("}");
            buffer.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /*
        recToGraph: This helper method is used to write the relations for 
                    each Pair to HuffmanTree.dot file to be represented using GraphViz.     
    */
    private void recToGraph(BufferedWriter buffer, Pair<M, N> current) throws IOException {

        if (current == null) {
            return;
        }
        if (current.left != null) {
                buffer.write("\t" + current.getFirst() + "->" + current.left.getFirst() + "\n" + "\n");
                recToGraph(buffer, current.left);
        } else {
            buffer.write("\t" + "null" + countNulls + "[shape=point, color = white]\n");
            buffer.write("\t" + current.getFirst() + "-> null" + countNulls + "\n");
            countNulls++;
        }
        if (current.right != null) {
            buffer.write("\t" + current.getFirst() + "->" + current.right.getFirst() + "\n");
            recToGraph(buffer, current.right);
        } else {
            buffer.write("\t" + "null" + countNulls + "[shape=point, color = white]\n");
            buffer.write("\t" + current.getFirst() + "-> null" + countNulls + "\n");
            countNulls++;
        }

    }

}