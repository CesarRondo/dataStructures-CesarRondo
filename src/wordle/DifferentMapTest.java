package wordle;

import org.junit.jupiter.api.Test;
import project20280.priorityqueue.DefaultComparator;
import project20280.tree.LinkedBinaryTree;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
public class DifferentMapTest {

    @Test
    void testChainHashMap(){
        LinkedBinaryTree<Character> huffmanTree =Huffman.getHuffmanTree(); //generate a huffman tree using ChainHashMap
        LinkedBinaryTree<Character> huffmanTreeChain =Huffman.getStandardHuffmanTree(); //generate huffman tree using HashMap



        assertEquals(huffmanTreeChain.toBinaryTreeString(),huffmanTree.toBinaryTreeString());


    }

}
