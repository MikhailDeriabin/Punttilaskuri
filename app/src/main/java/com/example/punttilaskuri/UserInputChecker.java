package com.example.punttilaskuri;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class checks Strings for special characters, letters and also removes them
 * @author Mikhail Deriabin
 * @version 29.04.2021
 */
public class UserInputChecker {
    //all special characters
    private final String specialCharactersString = "!@#$%&*()'+,./:;<=>?[]^_`{|}¤£½¶©±äöÄÖ";

    /**
     * removes special characters from string
     * @param string inspected string
     * @return String without special characters
     */
    public String removeSpecialCharacters(String string){
        StringBuilder newString = new StringBuilder();
        if (string.length() > 0) {
            for (int i = 0; i < string.length(); i++) {
                char ch = string.charAt(i);
                if (!specialCharactersString.contains(Character.toString(ch))) {
                    newString.append(ch);
                }
            }
        }
        return newString.toString();
    }

    /**
     * checks for string containing special characters
     * @param string inspected string
     * @return true if string contains special characters, false if not
     */
    public boolean isStringContainsSpecialChars(String string){
        if (string.length() > 0){
            for (int i=0; i < string.length() ; i++){
                char ch = string.charAt(i);
                if(specialCharactersString.contains(Character.toString(ch))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * removes letters from string
     * @param string inspected string
     * @return String without letters
     */
    public String removeAllLetters(String string){
        String newString = "";
        if (string.length() > 0){
            Pattern pattern = Pattern.compile("[^0-9]");
            Matcher matcher = pattern.matcher(string);
            newString = matcher.replaceAll("");
        }
        return newString;
    }
}
