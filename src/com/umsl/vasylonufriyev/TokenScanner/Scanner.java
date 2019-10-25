/*
Author: Vasyl Onufriyev
Date: 10.1.19
Class: CS4280
Instructor: Professor Janikow
Description: Scans given character input and translates to tokens using a table
*/

package com.umsl.vasylonufriyev.TokenScanner;

import com.umsl.vasylonufriyev.DataStructures.Token;

public class Scanner {
    /*
     * State array. Initial values for each row are:
     * 1000, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010, 1011, 1012, 1013, 1014, 1015, 1016, 1017, 1018, 1019, 1020, 1021, 1022, 1023
     */
    private int[][] FAD = new int[][]{
            //0 Initial state
            {1000, 2, 1002, 1, 3, 5, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 0, -23, 1023, 23},
            //1 Number state. Keep getting integer values until any other character comes by.
            {1000, -22, -22, 1, -22, -22, -22, -22, -22, -22, -22, -22, -22, -22, -22, -22, -22, -22, -22, -22, -22, -22, -22, -22, -22},
            //2 Identifier state. Keep getting letters/numbers until any other character comes by.
            {1000, 2, 2, 2, -21, -21, -21, -21, -21, -21, -21, -21, -21, -21, -21, -21, -21, -21, -21, -21, -21, -21, -21, -21, -21},
            //3 Detect =, transfer to state 4 if ==
            {1000, -1, -1, -1, 4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            //4 Detect ==
            {1000, -6, -6, -6, -6, -6, -6, -6, -6, -6, -6, -6, -6, -6, -6, -6, -6, -6, -6, -6, -6, -6, -6, -6, -6},
            //5 Detect < or transfer to state 6 to check for <=
            {1000, -2, -2, -2, 6, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2},
            //6 Detect <=
            {1000, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4},
            //7 Detect >
            {1000, -3, -3, -3, 8, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3},
            //8 Detect >=
            {1000, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5, -5},
            //9 Detect :
            {1000, -7, -7, -7, -7, -7, -7, -7, -7, -7, -7, -7, -7, -7, -7, -7, -7, -7, -7, -7, -7, -7, -7, -7, -7},
            //10 Detect +
            {1000, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8},
            //11 Detect -
            {1000, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9},
            //12 Detect *
            {1000, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, -10},
            //13 Detect /
            {1000, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11, -11},
            //14 Detect %
            {1000, -12, -12, -12, -12, -12, -12, -12, -12, -12, -12, -12, -12, -12, -12, -12, -12, -12, -12, -12, -12, -12, -12, -12, -12},
            //15 Detect .
            {1000, -13, -13, -13, -13, -13, -13, -13, -13, -13, -13, -13, -13, -13, -13, -13, -13, -13, -13, -13, -13, -13, -13, -13, -13},
            //16 Detect (
            {1000, -14, -14, -14, -14, -14, -14, -14, -14, -14, -14, -14, -14, -14, -14, -14, -14, -14, -14, -14, -14, -14, -14, -14, -14},
            //17 Detect )
            {1000, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15, -15},
            //18 Detect ,
            {1000, -16, -16, -16, -16, -16, -16, -16, -16, -16, -16, -16, -16, -16, -16, -16, -16, -16, -16, -16, -16, -16, -16, -16, -16},
            //19 Detect {
            {1000, -17, -17, -17, -17, -17, -17, -17, -17, -17, -17, -17, -17, -17, -17, -17, -17, -17, -17, -17, -17, -17, -17, -17, -17},
            //20 Detect }
            {1000, -18, -18, -18, -18, -18, -18, -18, -18, -18, -18, -18, -18, -18, -18, -18, -18, -18, -18, -18, -18, -18, -18, -18, -18},
            //21 Detect [
            {1000, -19, -19, -19, -19, -19, -19, -19, -19, -19, -19, -19, -19, -19, -19, -19, -19, -19, -19, -19, -19, -19, -19, -19, -19},
            //22 Detect ]
            {1000, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20, -20},
            //23 Detect ;
            {1000, -24, -24, -24, -24, -24, -24, -24, -24, -24, -24, -24, -24, -24, -24, -24, -24, -24, -24, -24, -24, -24, -24, -24, -24},
    };

    ProgramDataBuffer scannerDriver(ProgramDataBuffer data) throws Exception {
        StringBuilder proccessedData = new StringBuilder(); //Store data we have accumulated so far
        int state = 0; //Current state
        int nextState; //Next state
        char nextChar = data.getNextCharacter(); //Returns next character from filtered data
        int nextColumn; //Holds which column in the array will be used with this specific character

        while (state >= 0) { //If state is not final, IE: greater than 0
            nextColumn = KeywordTranslatorService.tryTranslateToColumnPosition(nextChar); //get next column

            if (nextColumn < 1000) { //if a character fails to parse, it returns 1023. This will be handled further on
                nextState = FAD[state][nextColumn];
            } else {
                nextState = nextColumn;
            }

            if (nextState >= 1000) { //If there is an error
                throw new Exception("SCANNER ERROR:ERR" + nextState + ":LN" + (data.getLineNumber() + 1) + ":CH" + data.getCharPosition() + ": "
                        + KeywordTranslatorService.tryTranslateErrorCode(nextState));
            }
            if (nextState < 0) { //If this is an exit state
                if (nextState == -21) { //Identifier token final state
                    if (KeywordTranslatorService.tryTranslateToToken(proccessedData.toString()) != null) { //Keyword
                        data.setParsedTk(new Token(KeywordTranslatorService.tryTranslateToToken(proccessedData.toString()), "", data.getLineNumber() + 1));
                        data.ungetNextCharacter();
                        return data;
                    } else { //Identifier
                        data.setParsedTk(new Token(KeywordTranslatorService.tryTranslateExitState(nextState), proccessedData.toString(), data.getLineNumber() + 1));
                        data.ungetNextCharacter();
                        return data;
                    }
                } else { //Other exit state
                    data.setParsedTk(new Token(KeywordTranslatorService.tryTranslateExitState(nextState), proccessedData.toString(), data.getLineNumber() + 1));
                    data.ungetNextCharacter();
                    return data;
                }
            } else { //Continue reading
                state = nextState;
                if(nextChar != ' ') //Don't append spaces if there are any
                    proccessedData.append(nextChar);
                nextChar = data.getNextCharacter();
            }
        }

        return data;
    }
}
