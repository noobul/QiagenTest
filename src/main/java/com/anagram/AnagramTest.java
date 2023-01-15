package com.anagram;

import java.io.Console;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class AnagramTest {

    private static char[] stringToArray(String s){
        return s.toCharArray();
    }

    static  boolean anagramCheck(String s1, String s2){
        char[] string1 = stringToArray(s1);
        char[] string2 = stringToArray(s2);

        int n1 = string1.length;
        int n2 = string2.length;

        if (n1 != n2)
            return false;

        Arrays.sort(string1);
        Arrays.sort(string2);

        for (int i = 0; i < n1; i++)
            if(string1[i] != string2[i])
                return false;

        return true;
    }

    /**
     * For some reason, it doesn't work when it's run from the IDE.
     * You have to CD to the location of this class and run java AnagramTest.java
     */

    public static void main(String[] args) {
        Console c = System.console();

        System.out.println("We are going to check if the next two words are anagrams!");

        System.out.println("Please enter first word: " );
        String firstTest = c.readLine();

        System.out.println("Please enter second word: " );
        String secondText = c.readLine();


        if(anagramCheck(firstTest, secondText)){
            System.out.println("Is an Anagram");
        }else {
            System.out.println("Not an anagram");
        }
    }
}
