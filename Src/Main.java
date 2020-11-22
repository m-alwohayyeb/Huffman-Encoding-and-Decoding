import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

public class Main {

    public static Random rand = new Random();


    public static void main(String[] args) {

        /*
            you can either call "customHuffmanTest(fileName)" and pass it a input file name to perform a custom test
                        OR call "testHuffman(int additionFactor, int maxLength, int nbIterations)" and pass it the 
                                 additionFactor which it adds by after each sample test and the maxLength which is 
                                 the maximum length possible for it to reach and the nbIterations which determines 
                                 the number of test ran on a singular sample size before taking the average 
                                 (Note: an additional run is added which is discarded and not factored within the 
                                  average calculation as to account for the possiblity of delayes due to load time 
                                  of the code to the cashes)
                                 (Note: the output of "the testHuffman()" is as in the file "testOutput.txt").
        */

        customHuffmanTest("test.txt");
        // OR
        // testHuffman(500, 20000, 10);
    }

    public static void customHuffmanTest(String fileName) {
        Huffman h = new Huffman();
        h.excuteAlgorithim(fileName);
    }

    /*
        genRandBST: This method will generate a random bst tree.
    */
    public static BST<Integer> genRandBST(int size, int maxKey) {
        BST<Integer> bst = new BST<Integer>();
        for (int i = 0; i < size; i++) {
            int key = rand.nextInt(maxKey) + 1;
            if (!bst.find(key))
                bst.insert(key);
        }
        return bst;
    }

    /*
        testHuffman: This method will test Huffman algorithim with 
                     random starting string of size additionFactor and 
                     replicate that string till it reaches maxLength,
                     each time the code will be tested nbIterations times,
                     lastly it will write the input size and the averege 
                     running time into testOutput.txt file
    */
    public static void testHuffman(int additionFactor, int maxLength, int nbIterations) {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter("testOutput.txt"));
            String originalString = genRandFile(additionFactor);
            String testString = originalString;
            for (int i = additionFactor; i <= maxLength; i = i + additionFactor) {
                BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt"));
                bw.write(testString);
                bw.close();
                long time=0;
                for (int j = 0; j < nbIterations+1; j++) {
                    Huffman h = new Huffman();
                    h.excuteAlgorithim("test.txt");
                    if(j != 0 )
                        time = time + h.getCompleteTime();
                }
                long average = time / nbIterations;
                output.write(i+","+ average +"\n");
                testString = testString + originalString;

            }
            output.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        genRandFile: This method will generate a random String (with respect to the ACSII table ) with length of nbChar.
    */
    public static String genRandFile(int nbChar) {
        String Str = "";
        for (int i = 0; i < nbChar; i++) {
            Str = Str + ((char) (rand.nextInt(128)));
        }
        return Str;
    }
}
