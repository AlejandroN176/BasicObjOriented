//Alejandro Nino, Hw2,  A object-oriented Morse code translator.
//Takes user input and interprets a word to morse code or vice versa.

import java.io.*;
import java.util.*;

class Morse{
    private String code;
    public Morse(String c){code = c;}
    public void setCode(String c) {code = c;}
    public String getCode() {return code;}
}
class Letter{
    private char character;
     public Letter(char c){character = c;}
     public void setCharacter(char c){character = c;}
     public char getCharacter() {return character;}
}
class MorseTranslator{
    private ArrayList<Morse> morses;
    private ArrayList<Letter> letters;
    private static MorseTranslator uniqueInstance;

    private MorseTranslator(){
        morses = new ArrayList<Morse>();
        letters = new ArrayList<Letter>();
    }
    public static MorseTranslator getInstance() {
        if(uniqueInstance == null) {
            uniqueInstance = new MorseTranslator();
        }
        return uniqueInstance;
    }
    public int findMorse(String n){
        boolean found = false;
        int i = 0;
        while(i < size() && !found){
            if (getMorse(i).equals(n))
                found = true;
            else
                i++;
        }
        if (!found)
            return -1;
        else
            return i;
    }
    public int findLetter(char letter){
        boolean found = false;
        int i = 0;
        while(i < size() && !found){
            if (getLetter(i) == letter)
                found = true;
            else
                i++;
        }
        if (!found)
            return -1;
        else
            return i;
    }
    public void add(char letter, String morse){
        Letter temp = new Letter(letter);
        letters.add(temp);
        Morse temp2 = new Morse(morse);
        morses.add(temp2);
    }
    public boolean remove(char letter) {
        int idx = findLetter(letter);
        if (idx == -1)
            return false;
        else {
            letters.remove(idx);
            morses.remove(idx);
            return true;
        }
    }
    public Character getLetter(int idx){
        if(idx >= 0 && idx < size()){
            return letters.get(idx).getCharacter();
        }
        else
            return ' ';
    }
    public String getMorse(int idx){
        if(idx >= 0 && idx < size()){
            return morses.get(idx).getCode();
        }
        else
            return " ";
    }
    public int size(){return morses.size();}

    public String translator(String translate){
        int location;
        String convert = "";
        String builder = "";
        if (Character.isLetter(translate.charAt(0))){
            for(int i = 0; i < translate.length()-1; i++){
                location = findLetter(translate.charAt(i));
                convert += getMorse(location) + " ";
            }
            location = findLetter(translate.charAt(translate.length()-1));
            convert += getMorse(location);
        }else{
            for(int i = 0; i < translate.length(); i++){
                if (!Character.isSpaceChar(translate.charAt(i)))
                    builder += translate.charAt(i);
                else {
                    location = findMorse(builder);
                    convert += getLetter(location);
                    builder = "";
                }
            }
            location = findMorse(builder);
            convert += getLetter(location);
        }
        return convert;
    }
}

public class Main {
    public static void fileRead(MorseTranslator m){
        String line;
        Scanner file = null;
        try {
            file = new Scanner(new File("morse.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("morse.txt not found");
        }
        while (file.hasNext()) {
            line = file.nextLine();
            String[] s = line.split(" ");
            m.add(s[0].charAt(0),s[1]);
        }
    }

    public static void userInterface(Scanner scan, MorseTranslator m) {
        String input, output;
        int select;
        System.out.println("Morse Code Translator: 1: Translate word 2: Translate morse, -1 to exit");
        select = scan.nextInt();
        scan.nextLine();
        while (select != -1) {
            if (select == 1) {
                System.out.println("\nInput Word");
                input = scan.nextLine();
                input = validWord(input);
                output = m.translator(input);
                System.out.println("\nWord translated to " + output);
            } else if (select == 2) {
                System.out.println("\ninput morse");
                input = scan.nextLine();
                output = m.translator(input);
                System.out.println("\nMorse Translated to " + output);
            }
            else
                System.out.println("\nIncorrect option");
            System.out.println("\nMorse Code Translator: 1: Translate word 2: Translate morse, -1 to exit");
            select = scan.nextInt();
            scan.nextLine();
        }
    }

    public static String validWord(String input) {
        String builder = "";
        for(int i = 0; i < input.length(); i++) {
            if (Character.isLetter(input.charAt(i))) {
                if (Character.isUpperCase(input.charAt(i)))
                    builder += input.charAt(i);
                else
                    builder += Character.toUpperCase(input.charAt(i));
            }
        }
        return builder;
    }
    public static void main(String[] args) {
        MorseTranslator m = MorseTranslator.getInstance();
        Scanner scan = new Scanner(System.in);
        fileRead(m);
        userInterface(scan, m);
        System.out.println("Program ending...");
    }
}
