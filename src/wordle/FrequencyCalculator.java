package wordle;

import project20280.hashtable.ChainHashMap;
import project20280.interfaces.Entry;

import java.util.*;

public class FrequencyCalculator {



    public static void main(String[] args){

        Wordle wordle= new Wordle();
        List<String> dictionary=wordle.readDictionary(wordle.fileName);

        ChainHashMap<String,Integer> letterFrequencyMap= new ChainHashMap<>();

        String alphabet="abcdefghijklmnopqrstuvwxyz";

        for(int i=0;i<26;i++){
            letterFrequencyMap.put(alphabet.substring(i,i+1),0);
        }


        for(String str:dictionary){
            for(char c : str.toCharArray()){
               String currentChar = String.valueOf(c);
               Integer h = letterFrequencyMap.get(currentChar);
               h++;
               letterFrequencyMap.put(currentChar,h);
            }
        }

        ChainHashMap<Character,Integer> chainFQMap = getFrequencyChainMap(wordle);

        for(Entry e : chainFQMap.entrySet()){
            System.out.println(e.getKey()+"="+e.getValue());
        }

//        HashMap<String,Integer> sortedFrequencyMap =new HashMap<>();
//
//
//        List<Map.Entry<String,Integer>> list = new ArrayList<>(letterFrequencyMap.entrySet());
//        //Collections.sort(list, Comparator.comparing(Map.Entry::getValue));
//        list.sort(Collections.reverseOrder(Comparator.comparing(Map.Entry::getValue)));
//        int i=0;
//        for(String s : letterFrequencyMap.keySet()){
//            System.out.println(s+": "+letterFrequencyMap.get(s));
//            //System.out.println(list.get(i));
//            //i++;
//        }
    }

    public static HashMap<Character,Integer> getFrequencyMap(Wordle wordle){

        //Wordle wordle= new Wordle();
        List<String> dictionary=wordle.readDictionary(wordle.fileName);

        HashMap<Character,Integer> letterFrequencyMap= new HashMap<>();


        String alphabet="abcdefghijklmnopqrstuvwxyz";

        for(int i=0;i<26;i++){
            letterFrequencyMap.put(alphabet.toCharArray()[i],0);
        }


        for(String str:dictionary){
            for(char c : str.toCharArray()){
                Character currentChar = c;
                Integer h = letterFrequencyMap.get(currentChar);
                h++;
                letterFrequencyMap.put(currentChar,h);
            }
        }
        return letterFrequencyMap;
    }

    public static ChainHashMap<Character,Integer> getFrequencyChainMap(Wordle wordle){

        //Wordle wordle= new Wordle();
        List<String> dictionary=wordle.readDictionary(wordle.fileName);

        ChainHashMap<Character,Integer> letterFrequencyMap= new ChainHashMap<>();


        String alphabet="abcdefghijklmnopqrstuvwxyz";

        for(int i=0;i<26;i++){
            letterFrequencyMap.put(alphabet.toCharArray()[i],0);
        }


        for(String str:dictionary){
            for(char c : str.toCharArray()){
                Character currentChar = c;
                Integer h = letterFrequencyMap.get(currentChar);
                h++;
                letterFrequencyMap.put(currentChar,h);
            }
        }
        return letterFrequencyMap;
    }


}
