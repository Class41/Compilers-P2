/*
Author: Vasyl Onufriyev
Date: 10.1.19
Class: CS4280
Instructor: Professor Janikow
Description: Token object used by the scanner
*/

package com.umsl.vasylonufriyev.DataStructures;

public class Token {
    private String tokenType = null;
    private String tokenValue = null;
    private int tokenLine = -1;

    public Token(String tokenType, String tokenValue, int tokenLine) {
        this.tokenType = tokenType;
        this.tokenValue = tokenValue;
        this.tokenLine = tokenLine;
    }

    public Token() { }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public int getTokenLine() {
        return tokenLine;
    }

    public void setTokenLine(int tokenLine) {
        this.tokenLine = tokenLine;
    }

    @Override
    public String toString() {
        return "{ tokenType: \"" + tokenType + "\", tokenValue:\"" + tokenValue + "\", tokenLine:\"" + tokenLine + "\" }";
    }
}
