import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Huffman {

    // -------------------- Attributes -------------------- \\
    
    public int[]                    CharacterFreq;  // An array hashmap which maps the symbol to frequncy of apperance.
    public Heap<Character, Integer> pq;             // A heap priority queue.
    public String                   inputSentence;  // A string which stores the input message.
    public String[]                 charCode;       // An array hashmap which mapes each symbol to it's binary code.

    
    public long                     completeTime;   // Long variable which have the time took to complete exucustion in millisecond.
    
    
    
    // -------------------- Constructor -------------------- \\
    
    Huffman() {
        CharacterFreq = new int[128];
        pq = new Heap<Character, Integer>(128);
        charCode =  new String[128];
    }


    
    // -------------------- Methods -------------------- \\

    /* 
     *   encoding: This method will assign binary code for each charecter in the sentence
     */
    public void encoding(Pair<Character, Integer> root, String str) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            charCode[(int) root.getFirst()] = str;
        }
        encoding(root.left, str + "0");
        encoding(root.right, str + "1");
    }
    /* 
     *   decoding: This method will take the encoded massege 
     *             and traverse through the huffman tree to decode the sentence 
     */
    public String decoding(Pair<Character, Integer> root, String str) {
        String out="";
        Pair<Character, Integer> current = root;
        for (int v=0; v < str.length(); v++) {
                if(str.charAt(v) == '0'){
                    current=current.left;
                }else
                    current=current.right;

                if(current.left==null && current.right==null) {
                out = out+current.getFirst();
                current=root;
            }
        }
        return out;
    }

    /*
     *  excuteAlgorithim: This method will read the sentence from the file (FileName)
     *                    and then perform huffman algorithim to the sentence 
     *                    then it will encode and decode the encoded sentence  
     */
    public void excuteAlgorithim(String fileName) {
        System.out.println("|=====================================================================|");
        this.completeTime = System.currentTimeMillis();

        // 1- Read the sentence from the file 
        this.readFile(fileName);

        // 2- Add the characters of the sentence with their frequencies in the minHeap (PriorityQueue)
        this.buildMinHeap(); // Method with max Running time of O(n lg n )

        // 3- Constructing Huffamn tree
        Pair<Character, Integer> root = this.buildHuffmanTree();

        // 4- Encode the symbols of the sentence  and 
        //    print each symbol with its binary code
        this.encoding(root, "");
        System.out.println("\nThe input sentance : " + inputSentence);
        System.out.println("\nHuffman code for each symbol in the sentence:-");
        for (int i = 0; i < charCode.length; i++) {
            if (charCode[i] != null) {
                System.out.println(((char) i) + ": " + charCode[i]);
            }
        }

        // 5- Build the encoded sentence from the binary codes then print it 
        String encodedMsg = "";
        for (int i = 0; i < inputSentence.length(); i++) {
            encodedMsg = encodedMsg + charCode[(int) inputSentence.charAt(i)];
        }
        System.out.println("\nThe encoded massege is: " + encodedMsg);
        System.out.println("\nThe length of the encoded message is: " + encodedMsg.length());

        // 6- Decode the encoded sentence and print it   
        String output = decoding(root, encodedMsg);
        System.out.println( "\nThe Decoded massege: " + output);
        completeTime = System.currentTimeMillis() - completeTime;
        System.out.println("\nTime taken to complete encoding and decoding : " + ( completeTime ) +"ms \n");
        encodedMsg = null;
        System.out.println("|=====================================================================|");

    }

    /*
     *   readFile: This method will recive a file name 
     *             it then will read the file and fill 
     *             CharacterFreq with the frequencies 
     *             of each symbol and return the 
     *             contents of the file as a string.
     */
    public void readFile(String filePath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if((int)c > 128) {
                System.out.println(c);
            } else
                CharacterFreq[((int) c)]++;
        }
        inputSentence = content;
    }
    /*
     * buildMinHeap: this method will add the symbols of the sentence 
     *               with their frequencies in the minHeap (PriorityQueue)
     */
    public void buildMinHeap(){
        for (int i = 0; i < CharacterFreq.length; i++) {
            if (CharacterFreq[i] > 0) {
                pq.insert((char) i, CharacterFreq[i]);
            }
        }
        pq.minHeap();
        pq.toGraph();
    }
    /*
     * buildHuffmanTree: this method will build huffman tree 
     *                  (by performing huffman algorithim)
     *                   and then returns the root of the tree
     */
    public Pair<Character, Integer> buildHuffmanTree(){
        int insert = 49; // Used for new nodes (For representaion in HuffmanTree.dot)
        while (pq.getSize() > 1) {
            Pair<Character, Integer> pLeft = pq.remove();
            Pair<Character, Integer> pRight = pq.remove();
            int sum = pLeft.getSecond() + pRight.getSecond();
            pq.insert((char) insert, sum, pLeft, pRight);
            insert++;
            pq.minHeap();
        }
        Pair<Character, Integer> root = pq.peek();
        if(root != null)
            root.toGraph(); // Huffman tree is in HuffmanTree.dot
        return root;
    }

    public long getCompleteTime() {
        return this.completeTime;
    }
    
}
