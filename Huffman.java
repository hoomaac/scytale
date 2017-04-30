import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by humac on 4/29/17.
 */
public class Huffman {


    public Huffman()
    {

    }


    static class HuffmanNode
    {
        char c;
        int frequency;
        HuffmanNode left;
        HuffmanNode right;

        public HuffmanNode(char c, int frequency, HuffmanNode left, HuffmanNode right) {
            this.c = c;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }


    }



    public static Map<Character, Integer> getCharFreq(String sentence)
    {
        Map<Character, Integer> charFreq = charFrequency(sentence);
        return charFreq;
    }

    public static HuffmanNode makeRootNode(String sentence)
    {

        HuffmanNode root = buildTree(getCharFreq(sentence));
        return root;
    }



    public static String compress(String sentence) throws FileNotFoundException, IOException {
        if (sentence == null) {
            throw new NullPointerException("Input sentence cannot be null.");
        }
        if (sentence.length() == 0) {
            throw new IllegalArgumentException("The string should atleast have 1 character.");
        }

        Map<Character, String> charCode = generateCodes(getCharFreq(sentence).keySet(), makeRootNode(sentence));
        String encodedMessage = encodeMessage(charCode, sentence);
        return encodedMessage;

    }



    public static String deCompress(HuffmanNode root,String encodedMessage)
    {

        String decoded = decodeMessage(root,encodedMessage);
        return decoded;
    }


    public static Map<Character, Integer> charFrequency(String sentence) {

        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < sentence.length(); i++) {
            char ch = sentence.charAt(i);
            if (!map.containsKey(ch))
                map.put(ch, 1);
            else {
                int val = map.get(ch);
                map.put(ch, ++val);
            }
        }

        return map;

    }


    public static class HuffmanComprator implements Comparator<HuffmanNode> {


        @Override
        public int compare(HuffmanNode node1, HuffmanNode node2) {
            return node1.frequency - node2.frequency;

        }
    }


	//build the huffman Tree using char frequency

    public static HuffmanNode buildTree(Map<Character, Integer> map) {
        Queue<HuffmanNode> nodeQueue = createNodeQueue(map);

        while (nodeQueue.size() > 1) {
            HuffmanNode node1 = nodeQueue.remove();
            HuffmanNode node2 = nodeQueue.remove();
            HuffmanNode node = new HuffmanNode('\0', node1.frequency + node2.frequency, node1, node2);
            nodeQueue.add(node);
        }

        return nodeQueue.remove();
    }

    public static Queue<HuffmanNode> createNodeQueue(Map<Character, Integer> map) {
        Queue<HuffmanNode> pq = new PriorityQueue<HuffmanNode>(11, new HuffmanComprator());

        for (Map.Entry<Character, Integer> m : map.entrySet()) {

            pq.add(new HuffmanNode(m.getKey(), m.getValue(), null, null));
        }

        return pq;

    }



    public static Map<Character, String> generateCodes(Set<Character> chars, HuffmanNode node) {
        final Map<Character, String> map = new HashMap<Character, String>();
        doGenerateCode(node, map, "");
        return map;
    }

    public static void doGenerateCode(HuffmanNode node, Map<Character, String> map, String s) {
        if (node.left == null && node.right == null)
        {
            map.put(node.c, s);
            return;
        }

            doGenerateCode(node.left,map,s +'0');
            doGenerateCode(node.right,map,s +'1');

    }


    static String encodeMessage(Map<Character, String> charCode, String sentence) {
        final StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < sentence.length(); i++) {
            stringBuilder.append(charCode.get(sentence.charAt(i)));
        }
        return stringBuilder.toString();
    }





    public void printMap(Map<Character, Integer> map) {
//        for (Map.Entry<Character,Integer> entry: map.entrySet())
//        {
//            String key = entry.getKey().toString();
//            int value = entry.getValue();
//            System.out.println("key: " + key + " " + "value: " + value);
//        }


    }


    public void getValue(Map<Character, Integer> map) {

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            int value = entry.getValue();
            System.out.println(value);
        }
    }



    public static BitSet getBitSet(String message)
    {
        BitSet bitSet = new BitSet();
        int i =0;
        for (i = 0; i < message.length();i++)
        {
            if (message.charAt(i) == '0')
                bitSet.set(i,false);
            else
                bitSet.set(i,true);
        }
        bitSet.set(i,true);
        return bitSet;
    }




    public static String decodeMessage(HuffmanNode node, String message) {

        StringBuilder stringBuilder = new StringBuilder();

        BitSet bitSet = getBitSet(message);

        for (int i = 0; i < (bitSet.length() - 1); ) {
            HuffmanNode temp = node;
            // since huffman code generates full binary tree, temp.right is certainly null if temp.left is null.
            while (temp.left != null) {
                if (!bitSet.get(i)) {
                    temp = temp.left;
                } else {
                    temp = temp.right;
                }
                i = i + 1;
            }
            stringBuilder.append(temp.c);
        }
        return stringBuilder.toString();
    }


}
