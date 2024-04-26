package wordle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.*;

public class Wordle {

    String fileName = "wordle/resources/dictionary.txt";
    //String fileName = "wordle/resources/extended-dictionary.txt";
    List<String> dictionary = null;
    final int num_guesses = 5;
    final long seed = 42;
    //Random rand = new Random(seed);
    Random rand = new Random();

    static final String winMessage = "CONGRATULATIONS! YOU WON! :)";
    static final String lostMessage = "YOU LOST :( THE WORD CHOSEN BY THE GAME IS: ";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_GREY_BACKGROUND = "\u001B[100m";

    Wordle() {

        this.dictionary = readDictionary(fileName);

        //System.out.println("dict length: " + this.dictionary.size());
        //System.out.println("dict: " + dictionary);

    }

    public static void main(String[] args) {
        Wordle game = new Wordle();

        String target = game.getRandomTargetWord();
//      String target = "marry";
//       String target = "quilt";
//        String target= "navel";
//        String target = "dowry";
//        System.out.println("target: " + target);

        game.play(target);
        //game.machinePlay(target);

    }

    public void play(String target) {
        // TODO
        // TODO: You have to fill in the code

        System.out.println("\n"+target);
        for(int i = 0; i < num_guesses; ++i) {
            String guess = getGuess();

            if(guess.equals(target)) { // you won!
                win(target);
                return;
            }

            // the hint is a string where green="+", yellow="o", grey="_"
            // didn't win ;(
            String [] hint = {"_", "_", "_", "_", "_"};
            String temp=target;
            char [] c;
            c = temp.toCharArray();
            for (int k = 0; k < 5; k++) {
                // TODO:
                if(target.substring(k,k+1).equals(guess.substring(k,k+1))){
                    hint[k]="+";
                    c[k]='!';
                    temp=String.copyValueOf(c);
                }
            }
            System.out.println(c);
            temp=String.copyValueOf(c);

            // set the arrays for yellow (present but not in right place), grey (not present)
            // loop over each entry:
            //  if hint == "+" (green) skip it
            //  else check if the letter is present in the target word. If yes, set to "o" (yellow)
            for (int k = 0; k < 5; k++) {
                // TODO:
                System.out.println(k);
                if(!hint[k].equals("+") && temp.contains(guess.substring(k,k+1))){
                    //guess=guess.replaceAll(guess.substring(k,k+1),"!");
                    System.out.println(temp.indexOf(guess.substring(k,k+1)));

                    c = temp.toCharArray();
                    //temp.indexOf(guess.substring(k,k+1))
                    c[k]='!';
                    temp=String.copyValueOf(c);
                    hint[k]="o";
                }
            }

            // after setting the yellow and green positions, the remaining hint positions must be "not present" or "_"
            System.out.println("hint: " + Arrays.toString(hint));


            // check for a win
            int num_green = 0;
            for(int k = 0; k < 5; ++k) {
                if(hint[k].equals("+")) num_green += 1;
            }
            if(num_green == 5) {
                 win(target);
                 return;
            }
        }

        lost(target);
    }

    public void lost(String target) {
        System.out.println();
        System.out.println(lostMessage + target.toUpperCase() + ".");
        System.out.println();

    }
    public void win(String target) {
        System.out.println(ANSI_GREEN_BACKGROUND + target.toUpperCase() + ANSI_RESET);
        System.out.println();
        System.out.println(winMessage);
        System.out.println();
    }

    public String getGuess() {
        Scanner myScanner = new Scanner(System.in, StandardCharsets.UTF_8.displayName());  // Create a Scanner object
        System.out.println("Guess:");

        String userWord = myScanner.nextLine();  // Read user input
        userWord = userWord.toLowerCase(); // covert to lowercase

        // check the length of the word and if it exists
        while ((userWord.length() != 5) || !(dictionary.contains(userWord))) {
            if ((userWord.length() != 5)) {
                System.out.println("The word " + userWord + " does not have 5 letters.");
            } else {
                System.out.println("The word " + userWord + " is not in the word list.");
            }
            // Ask for a new word
            System.out.println("Please enter a new 5-letter word.");
            userWord = myScanner.nextLine();
        }
        return userWord;
    }

    public String getRandomTargetWord() {
        // generate random values from 0 to dictionary size
        return dictionary.get(rand.nextInt(dictionary.size()));
    }
    public List<String> readDictionary(String fileName) {
        List<String> wordList = new ArrayList<>();

        try {
            // Open and read the dictionary file
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);
            assert in != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String strLine;

            //Read file line By line
            while ((strLine = reader.readLine()) != null) {
                wordList.add(strLine.toLowerCase());
            }
            //Close the input stream
            in.close();

        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        return wordList;
    }

    public int machinePlay(String target) {
        // Code to run the solver
        // Same code as in play but guesses will be generated by the solver
        Solver solver = new Solver(this);
        //System.out.println("\n"+target);
        for(int i = 0; i < num_guesses; ++i) {
            String guess = solver.getGuess();

            if(guess.equals(target)) { // you won!
                win(target);
                return 1;
            }

            // the hint is a string where green="+", yellow="o", grey="_"
            // didn't win ;(
            String[] hint =getHints(target,guess);
            //System.out.println(Arrays.toString(c));

            // after setting the yellow and green positions, the remaining hint positions must be "not present" or "_"
            System.out.println("hint: " + Arrays.toString(hint));
            String solverHint="";
            for(int z=0;z<5;z++){
                solverHint=solverHint+hint[z];
            }
            solver.updateHints(solverHint, guess);

            // check for a win
            int num_green = 0;
            for(int k = 0; k < 5; ++k) {
                if(hint[k].equals("+")) num_green += 1;
            }
            if(num_green == 5) {
                win(target);
                return 1;
            }
        }

        lost(target);
        return 0;
    }

    public static String[] getHints(String target, String guess){
        //System.out.println("guess: "+ guess);
        String [] hint = {"_", "_", "_", "_", "_"};
        String temp=target;
        char [] c;
        c = temp.toCharArray();
        for (int k = 0; k < 5; k++) {
            // TODO:
            if(target.substring(k,k+1).equals(guess.substring(k,k+1))){
                hint[k]="+";
                c[k]='_';
                temp=String.copyValueOf(c);
            }
        }
        //System.out.println(Arrays.toString(c));
        temp=String.copyValueOf(c);
        //System.out.println(temp);

        // set the arrays for yellow (present but not in right place), grey (not present)
        // loop over each entry:
        //  if hint == "+" (green) skip it
        //  else check if the letter is present in the target word. If yes, set to "o" (yellow)
        for (int k = 0; k < 5; k++) {
            // TODO:
            //System.out.println(k);
            temp=String.copyValueOf(c);
            //System.out.println(temp);
            if(!hint[k].equals("+") && temp.contains(guess.substring(k,k+1))){
                    hint[k]="o";
                    c[temp.indexOf(guess.substring(k,k+1))]='!';
            }
        }

        return hint;

    }

}
