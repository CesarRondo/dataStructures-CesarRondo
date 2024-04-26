package wordle;

import project20280.hashtable.ChainHashMap;

import java.util.ArrayList;
import java.util.HashMap;

public class Solver {

    private ArrayList<String> dictionary;//words in the dictianary
                                         //these words are being removed each time getGuess is ran
    //private final ChainHashMap<Character,Integer> frequencyMap; //frequency of each character in the dictionary-Using chainHashMap

    private final HashMap<Character,Integer> frequencyMap;
    private ArrayList<Character> discarded= new ArrayList<>(); //characters marked in grey and thus, discarded

    private ChainHashMap<String,Integer> frequencyScores; //frequencyScores of the words in the dictionary -using ChainHashmaps
    //private HashMap<String,Integer> frequencyScores;
    private char[] hintGreen = {'.','.','.','.','.'};
    //Array of chars that represents the greens obtained
    private ArrayList<Character>[] discardedSlot=new ArrayList[5];
    //Array of arraylists, it represents the yellow letters obtained
    //index 0 for letters that got marked in yellow in the 1st letter, index 1 for the ones in the 2nd letter etc.
    private ArrayList<Character> contained =new ArrayList<>();

    public static void main(String[] args){
        Integer wins=0;
        int tests =1;
        for(int i=0;i<tests;i++){
            Wordle game = new Wordle();
            String target = game.getRandomTargetWord();
            System.out.println("target: " + target);
            wins += game.machinePlay(target);
        }
        System.out.println("winrate = "+(double)wins/tests);

        Solver solver = new Solver(new Wordle());
        System.out.println("possible_words load Factor: "+solver.frequencyScores.loadFactor());;
    }

    public Solver(Wordle wordle){
        dictionary=(ArrayList<String>) wordle.dictionary; //get the dictionary from the wordle
        //System.out.println(frequencyScores);
        frequencyMap=FrequencyCalculator.getFrequencyMap(wordle); //generate the frequency of each letter
        //frequencyMap=FrequencyCalculator.getFrequencyChainMap(wordle);
        for(int i=0;i<5;i++){
            discardedSlot[i]=new ArrayList<>();
        }

        //frequencyScores=new ChainHashMap<>();
        frequencyScores=new ChainHashMap<>();
        for(String word: dictionary){
            //Calculate frequencyScores for the words in the dictionary
            frequencyScores.put(word,getFrequencyScore(word));
        }


    }
    public String getGuess(){
        //wait(10);
        int i=0; //index to remove elements
        /*
        * This function takes a guess by iterating through the dictionary and will calculate a score for each word
        * that consist in the sum of the frequency of each of its letters in the dictionary.
        * For this score, repeats do not accumulate
        * */
        int currentLettersContained=0;
        int maxLettersContained=0; //number of missing yellow letters contained: has priority over the score
        Integer  maxFrequency=0; //maximumFrequency that has been found
        String guess=null;   //string that has the highest score
        Integer currentFrequency;//variable to count the frequency score of each word
        while(guess==null && !dictionary.isEmpty()){
            i=0;
            for(int j=0;j< dictionary.size();j++){
                String word= dictionary.get(j);
                if(checkDiscard(word)){
                    currentFrequency=frequencyScores.get(word);
                    currentLettersContained=getContainsYellow(word);
                    if(maxFrequency==0 || guess.isBlank()){
                        //System.out.println("isEmptyTriggered");
                        maxFrequency=currentFrequency;
                        guess=word;
                    }
                    if(currentFrequency>maxFrequency && currentLettersContained>=maxLettersContained){
                        maxFrequency=currentFrequency;
                        guess=word;
                        maxLettersContained=currentLettersContained;
                    }
                    if(currentLettersContained>maxLettersContained){
                        maxFrequency=currentFrequency;
                        guess=word;
                        maxLettersContained=currentLettersContained;
                    }
                }
                else{
                    //System.out.println("removed: "+ dictionary.get(i));
                    dictionary.remove(i);
                }
                i++;
            }
        }
        System.out.println(guess);
        dictionary.remove(guess);
        return guess;
    }

    public Integer getFrequencyScore(String word){
        word =word.toLowerCase();
        ArrayList<Character> repeats = new ArrayList<>(); //array containing the letters that have been found
        Integer frequency=0;
        for(Character letter: word.toCharArray()){
            if(!repeats.contains(letter)){
                frequency+=frequencyMap.get(letter);
                repeats.add(letter);
            }
        }
        return frequency;
    }

    public boolean checkDiscard(String word){
        //Function to check if the current word contains letters we have discarded
        //or to check that it contains a letter that was marked yellow in the spot where it is located
        int i=0;
        for(char letter: word.toCharArray()){
            if(hintGreen[i]!='.' && letter!=hintGreen[i]){
                return false;
            }
            if(discarded.contains(letter) ){ //check if the letter was marked grey or
                return false;                                                   //yellow in the current slot
            }
            if(discardedSlot[i].contains(letter)){
                return false;
            }
            i++;
        }
        return true;
    }
    public Integer getContainsYellow(String word){//Function that returns the number of yellow hints that the current word contains
        int lettersContained=0; //yellow letters that we find
        ArrayList<Character> repeats = new ArrayList<>(); //array containing the letters that have been found
        char[] wordArray = word.toCharArray();
        for(int i=0;i<5;i++){
            if(hintGreen[i]=='.'){
                for(int j=0;j<5;j++){
                    if(j!=i && discardedSlot[i].contains(wordArray[j])&& !repeats.contains(wordArray[i])){//&& !repeats.contains(wordArray[i]) - j!=i &&
                        repeats.add(wordArray[i]);
                        lettersContained++;
                        break;
                    }
                }
            }
        }
        return lettersContained;
    }
    public void updateHints(String hint, String previousGuess){
        char[] hintArr=hint.toCharArray();
        char[] prevArr=previousGuess.toCharArray();
        //System.out.println(hintArr);
        for(int i=0;i<5;i++){
            if(hintArr[i]=='+'){
                hintGreen[i]=prevArr[i];
                for(int j=0;j<5;j++){
                    if(discardedSlot[i].contains(prevArr[i])){
                        discardedSlot[i].remove(prevArr[i]);
                    }
                    contained.add(prevArr[i]);
                }
            }
            else if(hintArr[i]=='o'){
                if(!discardedSlot[i].contains(prevArr[i])){
                    discardedSlot[i].add(prevArr[i]);
                }
            }
            else{
                if(!contained.contains(prevArr[i])){
                    discarded.add(prevArr[i]);
                }
            }
        }

        for(int i=0 ;i<discarded.size();i++){
            if(contained.contains(discarded.get(i))){
                discarded.remove(discarded.get(i));
            }
        }
        //System.out.println(hintGreen);

    }

}
