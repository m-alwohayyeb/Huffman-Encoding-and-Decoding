

public class Node<T extends Comparable <T>> {


    // -------------------- Attributes -------------------- \\

    public T        value;  // A generic attribute ment to store information within a node of the tree.
    public Node<T>  left;   // A pointer which stores the left child.
    public Node<T>  right;  // A pointer which stores the righ child.



    // -------------------- Constructor -------------------- \\

    public Node(T value){

        this.value = value;
        this.left=null;
        this.right=null;

    }



    // -------------------- Methods -------------------- \\

    // Setters and Getters.
    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getLeftChild() {
        return this.left;
    }

    public void setLeftChild(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRightChild() {
        return this.right;
    }

    public void setRightChild(Node<T> right) {
        this.right = right;
    }

}