import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class BST<T extends Comparable<T>> {

    // -------------------- Attributes -------------------- \\
    private Node<T>     root;           // Pointer to the root of the tree.
    private int         nbNodes;        // Stores the current number of nodes within the tree.

    private int         countNulls;     // Counter for each null in the whole tree (Used for representaion)

    // -------------------- Constructor -------------------- \\
    public BST() {
        this.root = null;
        nbNodes = 0;
    }

    // -------------------- Methods -------------------- \\

    /* find: will search through the entire tree until
     *       it encounters the specified key it then returns true otherwise false.
     */
    public boolean find(T Key) {

        Node<T> current = root;
        while (current != null) {         // searches for the exact key
            if (current.getValue().compareTo(Key) > 0) {
                current = current.getLeftChild();
            } else if (current.getValue().compareTo(Key) < 0) {
                current = current.getRightChild();
            } else {
                return true;
            }
        }
        return false;
    }

    /* insert: will search through the entire tree until 
     *         it encounters the right position for the 
     *         key to be added. 
     */
    public void insert(T Key) {

        Node<T> newNode = new Node<T>(Key);
        if (root == null) {
            root = newNode;
            nbNodes++;
            return;
        }

        Node<T> current = root;
        Node<T> parent = null;

        while (true) {          // searches for the correct position in the tree for the newNode to be inserted
            parent = current;
            if (Key.compareTo(current.getValue()) < 0) {
                current = current.getLeftChild();
                if (current == null) {
                    parent.setLeftChild(newNode);
                    nbNodes++;
                    return;
                }
            } else {
                current = current.getRightChild();
                if (current == null) {
                    parent.setRightChild(newNode);
                    nbNodes++;
                    return;
                }
            }
        }

    }

    /* delete: will search through the entire tree until 
     *         it encounters the the specified key it 
     *         then deletes the node. 
     */
    public boolean delete(T Key) {

        Node<T> parent = root;
        Node<T> current = root;
        boolean isLeftChild = false;

        // Looking for the node
        while (!current.getValue().equals(Key)) {
            parent = current;
            if (current.getValue().compareTo(Key) > 0) {
                isLeftChild = true;
                current = current.getLeftChild();
            } else {
                isLeftChild = false;
                current = current.getRightChild();
            }
            if (current == null) {
                return false;
            }
        }

        // Case 1.
        if (current.getLeftChild() == null && current.getRightChild() == null) {
            if (current == root) {
                root = null;
            }
            if (isLeftChild == true) {
                parent.setLeftChild(null);
            } else {
                parent.setRightChild(null);
                ;
            }
        }

        // Case 2.
        else if (current.getRightChild() == null) {
            if (current == root) {
                root = current.getLeftChild();
            } else if (isLeftChild) {
                parent.setLeftChild(current.getLeftChild());
            } else {
                parent.setRightChild(current.getLeftChild());
            }
        } else if (current.getLeftChild() == null) {
            if (current == root) {
                root = current.getRightChild();
            } else if (isLeftChild) {
                parent.setLeftChild(current.getRightChild());
            } else {
                parent.setRightChild(current.getRightChild());
            }
        }

        // Case 3.
        else if (current.getLeftChild() != null && current.getRightChild() != null) {
            Node<T> successor = getSuccessor(current);
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.setLeftChild(successor);
            } else {
                parent.setRightChild(successor);
            }
            successor.setLeftChild(current.getLeftChild());
        }
        nbNodes--;
        return true;
    }

    /* getSuccessor: will search through the left subtree until
     *               it finds the left most node then returns it.
     */
    public Node<T> getSuccessor(Node<T> deleteNode) {

        Node<T> successsor = null;
        Node<T> successsorParent = null;
        Node<T> current = deleteNode.getRightChild();

        while (current != null) {

            successsorParent = successsor;
            successsor = current;
            current = current.getLeftChild();
        }

        if (successsor != deleteNode.getRightChild()) {
            successsorParent.setLeftChild(successsor.getRightChild());
            successsor.setRightChild(deleteNode.getRightChild());
        }

        return successsor;
    }

    /* retrive: will search through the entire tree until
     *          it encounters the specified key it then returns the key otherwise null.
     */
    public T retrive(T Key) {

        Node<T> current = root;

        while (current != null) {

            if (current.getValue().compareTo(Key) > 0) {
                current = current.getLeftChild();
            } else if (current.getValue().compareTo(Key) < 0) {
                current = current.getRightChild();
            } else {
                return current.getValue();
            }
        }
        return null;
    }

    // /* printAll: prints all the words in the tree in infix order
    //  */
    /*
        toGraph : its used to generate BSTGraph.dot file for the tree to be represented using GraphViz
    */
    public void toGraph() {
        BufferedWriter buffer;
        try {
            countNulls=0;
            buffer = new BufferedWriter(new FileWriter("BSTGraph.dot"));
            buffer.write("digraph MyGraph {\n");

            recToGraph(buffer, this.root);

            buffer.write("}");
            buffer.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
    /*
        recToGraph : helper method that is used to write the relations
                     for each Node to BSTGraph.dot file to be represented using GraphViz
    */
    private void recToGraph(BufferedWriter buffer, Node<T> current) throws IOException {
        if ( current == null ) {
            return;
        }
        if( current.left != null ) { // adds relation if there is left Child
            buffer.write("\t" + current.value + "->" + current.left.value + "\n" + "\n");
            recToGraph(buffer, current.left);
        } else { // adds empty shape
            buffer.write("\t"+"null"+ countNulls +"[shape=point, color = white]\n");
            buffer.write("\t" + current.value + "-> null"+ countNulls + "\n");
            countNulls++;
        }

        if( current.right != null ) { // adds relation if there is right Child
            buffer.write("\t" + current.value + "->" + current.right.value + "\n");
            recToGraph(buffer, current.right);
        } else { // adds empty shape
            buffer.write("\t"+"null"+ countNulls +"[shape=point, color = white]\n");
            buffer.write("\t" + current.value + "-> null"+ countNulls + "\n");
            countNulls++;
        }
        
    }

}

