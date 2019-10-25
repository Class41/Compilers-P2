/*
Author: Vasyl Onufriyev
Date: 10.1.19
Class: CS4280
Instructor: Professor Janikow
Description: Used by the scanner for translating various exit states, determining which columns to go to, and errors
*/

package com.umsl.vasylonufriyev.TokenScanner;

import java.util.HashMap;
import java.util.Map;

class KeywordTranslatorService {
    private static Map<String, String> keywordDictionary = new HashMap<String, String>() {{ //Keyword token translator
        this.put("start", "START_TK");
        this.put("stop", "STOP_TK");
        this.put("iterate", "ITERATE_TK");
        this.put("void", "VOID_TK");
        this.put("var", "VAR_TK");
        this.put("return", "RETURN_TK");
        this.put("in", "IN_TK");
        this.put("out", "OUT_TK");
        this.put("program", "PROGRAM_TK");
        this.put("cond", "COND_TK");
        this.put("then", "THEN_TK");
        this.put("let", "LET_TK");
    }};

    static String tryTranslateToToken(String key) {
        return keywordDictionary.getOrDefault(key, null);
    }

    private static Map<Integer, String> exitStateDictionary = new HashMap<Integer, String>() {{ //Operator translator
        this.put(-1, "ASSIGN_TK");
        this.put(-2, "LESSTHAN_TK");
        this.put(-3, "GREATERTHAN_TK");
        this.put(-4, "LESSTHANEQUAL_TK");
        this.put(-5, "GREATERTHANEQUAL_TK");
        this.put(-6, "EQUALCOMPARE_TK");
        this.put(-7, "COLON_TK");
        this.put(-8, "PLUS_TK");
        this.put(-9, "MINUS_TK");
        this.put(-10, "MULT_TK");
        this.put(-11, "DIVIDE_TK");
        this.put(-12, "MODULO_TK");
        this.put(-13, "MEMBER_TK");
        this.put(-14, "PARENTHESISOPEN_TK");
        this.put(-15, "PARENTHESISCLOSE_TK");
        this.put(-16, "COMMA_TK");
        this.put(-17, "CURLYBRACEOPEN_TK");
        this.put(-18, "CURLYBRACECLOSE_TK");
        this.put(-19, "SQUAREBRACKETOPEN_TK");
        this.put(-20, "SQUAREBRACKETCLOSE_TK");
        this.put(-21, "IDENTIFIER_TK");
        this.put(-22, "NUMBER_TK");
        this.put(-23, "EOF_TK");
        this.put(-24, "SEMICOLON_TK");
    }};

    static String tryTranslateExitState(int key) {
        return exitStateDictionary.getOrDefault(key, null);
    }

    private static Map<Character, Integer> charColumnDictionary = new HashMap<Character, Integer>() {{ //Character to column value translator
        this.put('=', 4);
        this.put('<', 5);
        this.put('>', 6);
        this.put(':', 7);
        this.put('+', 8);
        this.put('-', 9);
        this.put('*', 10);
        this.put('/', 11);
        this.put('%', 12);
        this.put('.', 13);
        this.put('(', 14);
        this.put(')', 15);
        this.put(',', 16);
        this.put('{', 17);
        this.put('}', 18);
        this.put('[', 19);
        this.put(']', 20);
        this.put((char) 0x7f, 22);
        this.put(';', 24);
    }};

    //Used to translate given character to the column it corresponds to in the state diagram for access
    static int tryTranslateToColumnPosition(char key) {
        if (Character.isAlphabetic(key) && Character.isLowerCase(key)) //If it is a lowercase letter, that is stored in column 1 (starting at 0)
            if(key >= 'a' && key <= 'z') //check against english alphabet
                return 1;
            else
                return 1023;
        if (Character.isAlphabetic(key) && Character.isUpperCase(key)) //If it is a uppercase letter, that is stored in column 2
            if(key >= 'A' && key <= 'Z') //Check if in english alphabet
                return 2;
            else
                return 1023;
        if (Character.isDigit(key)) //if it is a digit, it is stored in column 3
            return 3;
        if (Character.isWhitespace(key)) //If it is a whitespace, it is stored in column 21
            return 21;

        return charColumnDictionary.getOrDefault(key, 1023); //If returns 1023, invalid character detected.
    }

    private static Map<Integer, String> errorStatesDictionary = new HashMap<Integer, String>() {{ //Error code translator
        this.put(1000, "RESERVED. NOT ALLOWED IN CURRENT CONTEXT"); //RESERVED
        this.put(1001, "LowerAlpha is not allowed in attempted context");
        this.put(1002, "UpperAlpha is not allowed in attempted context");
        this.put(1003, "Digit is not allowed in attempted context");
        this.put(1004, "= is not allowed in attempted context");
        this.put(1005, "< is not allowed in attempted context");
        this.put(1006, "> is not allowed in attempted context");
        this.put(1007, ": is not allowed in attempted context");
        this.put(1008, "+ is not allowed in attempted context");
        this.put(1009, "- is not allowed in attempted context");
        this.put(1010, "* is not allowed in attempted context");
        this.put(1011, "/ is not allowed in attempted context");
        this.put(1012, "% is not allowed in attempted context");
        this.put(1013, ". i s not allowed in attempted context");
        this.put(1014, "( is not allowed in attempted context");
        this.put(1015, ") is not allowed in attempted context");
        this.put(1016, ",is not allowed in attempted context");
        this.put(1017, "{ is not allowed in attempted context");
        this.put(1018, "} is not allowed in attempted context");
        this.put(1019, "[ is not allowed in attempted context");
        this.put(1020, "] is not allowed in attempted context");
        this.put(1021, "Whitespace is not allowed in attempted context");
        this.put(1022, "EOF is not allowed in attempted context");
        this.put(1023, "Could not parse character");
        this.put(1024, "; is not allowed in attempted context");
    }};

    static String tryTranslateErrorCode(int code) {
        return errorStatesDictionary.getOrDefault(code, "Error occurred but no error matched the code!");
    }

}
