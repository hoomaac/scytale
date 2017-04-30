import java.io.IOException;
import java.util.BitSet;
import java.util.Map;

/**
 * Created by humac on 4/29/17.
 */
public class MainClass
{
    public static void main(String[] args) {
//        Huffman huffman = new Huffman();
//
//        Map map = huffman.charFrequency("naaavvvviid");
        try {

            String sentence = "Sample Text";


            String main = Huffman.compress(sentence);

            String str1 = Huffman.compress(sentence)

            String dstr = Huffman.deCompress(Huffman.makeRootNode(sentence),main);
            



        } catch (IOException e) {
            e.printStackTrace();
        }


//        System.out.println(huffman.getValue(map));
//        huffman.getValue(map);







    }
}





















