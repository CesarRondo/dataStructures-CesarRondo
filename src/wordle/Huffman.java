package wordle;

import org.junit.runners.model.TestTimedOutException;
import project20280.hashtable.ChainHashMap;
import project20280.interfaces.Entry;
import project20280.interfaces.Position;

import project20280.priorityqueue.HeapPriorityQueue;
import project20280.tree.LinkedBinaryTree;


import java.io.*;
import java.util.*;

public class Huffman {

    //public static ChainHashMap<Character,String> charEncoding = new ChainHashMap<>();
    public static HashMap<Character,String> charEncoding = new HashMap<>();
    public static ChainHashMap<String,Character> reverseCharEncoding = new ChainHashMap<>();
    public static String fileName = "wordle/resources/dictionary-encoded.txt";
    public static LinkedBinaryTree<Character> getHuffmanTree(){

        String alphabet="abcdefghijklmnopqrstuvwxyz";
        //LTcomparator comp = new LTcomparator();
        Wordle wordle=new Wordle();
        //HashMap<Character,Integer> frequencyMap=FrequencyCalculator.getFrequencyMap(wordle);
        ChainHashMap<Character,Integer> frequencyMap=FrequencyCalculator.getFrequencyChainMap(wordle);
        //HeapPriorityQueue<Integer,LinkedBinaryTree<Character>> Q = new HeapPriorityQueue<>((Comparator)comp);
        HeapPriorityQueue<Integer,LinkedBinaryTree<Character>> Q = new HeapPriorityQueue<>();
        for(char c : alphabet.toCharArray()){
            LinkedBinaryTree<Character> tree =new LinkedBinaryTree<>();
            tree.addRoot(c);
            Q.insert(frequencyMap.get(c),tree);
        }
        //System.out.println(Q);
        while(Q.size()>1){
            //System.out.println(Q);
            Entry<Integer,LinkedBinaryTree<Character>> entry1 = Q.removeMin();
            Entry<Integer,LinkedBinaryTree<Character>> entry2 = Q.removeMin();
            LinkedBinaryTree<Character> tree =new LinkedBinaryTree<>();
            tree.addRoot('.');
            tree.attach(tree.root(),entry1.getValue(),entry2.getValue());
            Q.insert((entry1.getKey()+ entry2.getKey()),tree);
        }
        Entry<Integer,LinkedBinaryTree<Character>> entry = Q.removeMin();
        System.out.println(entry.getValue().toBinaryTreeString());
        //System.out.println(entry.getValue().root());
        return entry.getValue();
    }

    public static LinkedBinaryTree<Character> getStandardHuffmanTree(){

        String alphabet="abcdefghijklmnopqrstuvwxyz";
        //LTcomparator comp = new LTcomparator();
        Wordle wordle=new Wordle();
        HashMap<Character,Integer> frequencyMap=FrequencyCalculator.getFrequencyMap(wordle);
        //ChainHashMap<Character,Integer> frequencyMap=FrequencyCalculator.getFrequencyChainMap(wordle);
        //HeapPriorityQueue<Integer,LinkedBinaryTree<Character>> Q = new HeapPriorityQueue<>((Comparator)comp);
        HeapPriorityQueue<Integer,LinkedBinaryTree<Character>> Q = new HeapPriorityQueue<>();
        for(char c : alphabet.toCharArray()){
            LinkedBinaryTree<Character> tree =new LinkedBinaryTree<>();
            tree.addRoot(c);
            Q.insert(frequencyMap.get(c),tree);
        }
        //System.out.println(Q);
        while(Q.size()>1){
            //System.out.println(Q);
            Entry<Integer,LinkedBinaryTree<Character>> entry1 = Q.removeMin();
            Entry<Integer,LinkedBinaryTree<Character>> entry2 = Q.removeMin();
            LinkedBinaryTree<Character> tree =new LinkedBinaryTree<>();
            tree.addRoot('.');
            tree.attach(tree.root(),entry1.getValue(),entry2.getValue());
            Q.insert((entry1.getKey()+ entry2.getKey()),tree);
        }
        Entry<Integer,LinkedBinaryTree<Character>> entry = Q.removeMin();
        System.out.println(entry.getValue().toBinaryTreeString());
        //System.out.println(entry.getValue().root());
        return entry.getValue();
    }

    public static void main(String[] args){
        LinkedBinaryTree<Character> huffmanTree =getHuffmanTree(); //generate a huffman tree in the huff class
        getCharEncoding(huffmanTree,"1",huffmanTree.root()); //generate a character encoding in the HUff Class
        Wordle wordle = new Wordle();
        //writeEncodedDictionary(fileName, wordle);

        System.out.println("Note: for this implementation, an extra 1 and previous 0s are added to complete a byte");
        System.out.println("Compression ratio and bits are calculated independantly of that");
        System.out.println();
        System.out.println(charEncoding);
        //System.out.println(reverseCharEncoding);
        System.out.println();

        HashMap<Character, Integer> frequencyMap=FrequencyCalculator.getFrequencyMap(wordle);
        Integer totalSize=0;
        Integer totalSizeEncoded=0;

        for(Character c : charEncoding.keySet()){
            totalSize+=frequencyMap.get(c)*8;

            String noExtra0s = charEncoding.get(c);
            while(noExtra0s.toCharArray()[0]!='1'){
                noExtra0s=noExtra0s.substring(1);
            }
            totalSizeEncoded+=frequencyMap.get(c)*((noExtra0s).length()-1);
        }
        System.out.println("Bits used in encoded format: "+ totalSizeEncoded);
        System.out.println("Bits used in standard format: "+ totalSize);
        System.out.println("encoded/normal compresion ratio= "+ (((double)totalSizeEncoded)/totalSize));

        String[] mostBitsUsed = new String[5];
        Integer[] mostBitsUsedInt =new Integer[5];
        Integer leastBits=0;
        int i=0;
        for(String word: wordle.dictionary){
            Integer currentBitsize=0;
            for(Character c : word.toCharArray()){
                String noExtra0s = charEncoding.get(c);
                while(noExtra0s.toCharArray()[0]!='1'){
                    noExtra0s=noExtra0s.substring(1);
                }
                currentBitsize+=(noExtra0s).length()-1;
            }
            if(i<5){
                mostBitsUsed[i]=word;
                mostBitsUsedInt[i]=currentBitsize;
            }
            else{
                int j=0;
                //System.out.println("Switch");
                for(Integer bits: mostBitsUsedInt){
                    if(currentBitsize>bits){
                        mostBitsUsed[j]=word;
                        mostBitsUsedInt[j]=currentBitsize;
                        break;
                    }
                    j++;
                }
            }
            i++;
        }
        System.out.println("Most heavy words: "+ Arrays.toString(mostBitsUsed));
        System.out.println("Bits: "+ Arrays.toString(mostBitsUsedInt));

    }

    public static void getCharEncoding(LinkedBinaryTree<Character> huffmanTree, String code, Position<Character> p){

        /*
        For this implementation, each encoding is preceeded by a 1
        * **/

        if(huffmanTree.left(p).getElement()=='.'){
            getCharEncoding(huffmanTree,code+"0", huffmanTree.left(p));
        }
        else{
            int length= code.length();
            while(code.length()<7){ //add preceeding
                code= "0"+code;
                length=code.length();
            }
            charEncoding.put(huffmanTree.left(p).getElement(),code+"0");
            String s= charEncoding.get(huffmanTree.left(p).getElement());
            while(s.length()>8){
                s=s.substring(1);
            }
            charEncoding.put(huffmanTree.left(p).getElement(),s);
            reverseCharEncoding.put(s,huffmanTree.left(p).getElement());
        }
        if(huffmanTree.right(p).getElement()=='.'){
            getCharEncoding(huffmanTree,code+"1", huffmanTree.right(p));
        }
        else{
            int length=code.length();
            while(length<=6){
                code= "0"+code;
                length=code.length();
            }
            charEncoding.put(huffmanTree.right(p).getElement(),code+"1");
            String s= charEncoding.get(huffmanTree.right(p).getElement());

            while(s.length()>8){
                s=s.substring(1);
            }
            charEncoding.put(huffmanTree.right(p).getElement(),s);
            reverseCharEncoding.put(s,huffmanTree.right(p).getElement());
        }
    }

    public static void writeEncodedDictionary(String dictionaryFileName, Wordle wordle){
        //HashMap<Character,Integer> frequencyMap=FrequencyCalculator.getFrequencyMap();
        //Wordle wordle = new Wordle();
        ArrayList<String> dictionary = (ArrayList<String>) wordle.dictionary; //note the wordle dictionary, IS an arrayList
        //System.out.println(dictionaryFileName);
        //write to a file using fileOutputStream
        try{
            //FileOutputStream encodeStream = wordle.getClass().getClassLoader().getResourceAsStream(fileName);
            String currentDirectory = Huffman.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            FileOutputStream encodeStream = new FileOutputStream(currentDirectory+dictionaryFileName);
            short currentChar;
            System.out.println("File accessed");
            for(int i=0;i<dictionary.size();i++){
                char[] currentWord = (dictionary.get(i)).toCharArray();
                for (char c:currentWord){
                    try {
                        //System.out.println("editing file");
                        //System.out.println(Integer.parseInt(charEncoding.get(c), 2));
                        //encodeStream.write(Byte.parseByte(charEncoding.get(c), 2));
                        //System.out.println((((Short)(Short.parseShort(charEncoding.get(c), 2))).byteValue() & 0xff));
                        encodeStream.write(((Short)(Short.parseShort(charEncoding.get(c), 2))).byteValue() & 0xff);
                        //encodeStream.write(Integer.parseInt(charEncoding.get(c), 2));
                    }
                    catch(IOException exception){
                        System.out.println(exception.toString());
                        return;
                    }
                }
            }
            //encodeStream.flush();
            encodeStream.close();
        }
        catch (FileNotFoundException exception){
            System.out.println("File not found");
        }
        catch(IOException exception){
            System.out.println("IOexception");
        }
    }

    public static void readEncodedDictionary(String dictionaryFileName, Wordle wordle){

        //Read line 1 for testing
        try {
            // Open and read the dictionary file
            InputStream readStream = wordle.getClass().getClassLoader().getResourceAsStream(fileName);
            assert readStream != null;

            ArrayList<String> dictionary = new ArrayList<>(); //note the wordle dictionary, IS an arrayList
            System.out.println(dictionaryFileName);
            int[] currentChar= new int[5];
//            while((currentChar=readStream.read())!=-1){
//
//            }

            for(int j=0;j<10;j++){
                for(int i=0;i<5;i++){
                    currentChar[i]=readStream.read();
                    long unsignedValue = currentChar[i];
                    //System.out.println("Unsigned value="+unsignedValue);
                    String currentByte = Integer.toBinaryString((int)unsignedValue & 0xff);
                    while(currentByte.length()<8){
                        currentByte="0"+currentByte;
                    }
                    //System.out.print(currentByte);
                    //System.out.print(reverseCharEncoding.get((Integer.toBinaryString(currentChar[i]))));
                    System.out.print(reverseCharEncoding.get(currentByte));
                    //System.out.print(currentByte);
//                    System.out.print((Integer.toBinaryString(currentChar[i]))+" ,");
                }
                System.out.println();
            }

            readStream.close();

        } catch (FileNotFoundException e) {//Catch exception if any
            System.out.println("File not found");
        }
        catch (IOException e){
            System.out.println("IOException: ");
        }
    }
    public static class LTcomparator implements Comparator<LinkedBinaryTree<Character>>{
        @Override
        public int compare(LinkedBinaryTree<Character> tree1, LinkedBinaryTree<Character> tree2) {
            Character c1 = tree1.root().getElement();
            Character c2 = tree2.root().getElement();
            return c1.compareTo(c2);
        }
    }
}
