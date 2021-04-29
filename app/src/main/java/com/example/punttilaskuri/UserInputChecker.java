package com.example.punttilaskuri;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInputChecker {
    private final String specialCharactersString = "!@#$%&*()'+,-./:;<=>?[]^_`{|}¤£½¶©±äöÄÖ";

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
