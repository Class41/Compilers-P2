package com.umsl.vasylonufriyev.ProgramParser;

import com.umsl.vasylonufriyev.DataStructures.Token;
import com.umsl.vasylonufriyev.DataStructures.Tree;
import com.umsl.vasylonufriyev.TokenScanner.ProgramDataBuffer;
import com.umsl.vasylonufriyev.TokenScanner.Scanner;

public class Parser {
    private ProgramDataBuffer programSource;
    private Scanner scanner;
    private Tree programTree;
    private Token lastTk;

    public Parser(ProgramDataBuffer programDataBuffer) {
        this.programSource = programDataBuffer;
        this.scanner = new Scanner();
        this.programTree = new Tree();
        lastTk = null;
    }

    public Exception exceptionBuilder(String expectedTk) {
        StringBuilder exceptionString = new StringBuilder();
        exceptionString
                .append("PROGPARSER:L")
                .append(lastTk.getTokenLine())
                .append(":")
                .append("Expected ")
                .append(expectedTk)
                .append(" got ")
                .append(lastTk.getTokenType());
        return new Exception(exceptionString.toString());
    }

    private void getNextToken() throws Exception {
        lastTk = (scanner.scannerDriver(programSource)).getParsedTk();
    }

    private boolean compareToken(String expected) {
        return lastTk.getTokenType().equals(expected);
    }

    public Tree beginParse() throws Exception {
        getNextToken();
        nontermProgram();

        if (compareToken("EOF_TK"))
            return programTree;
        else
            throw exceptionBuilder("EOF_TK");
    }

    private void nontermProgram() throws Exception {
        nontermVars();
        nontermBlock();
    }

    private void nontermBlock() throws Exception {
        if (compareToken("START_TK")) {
            getNextToken();
            nontermVars();
            nontermStats();
            if (compareToken("STOP_TK")) {
                getNextToken();
            } else {
                throw exceptionBuilder("STOP_TK");
            }
        } else {
            throw exceptionBuilder("START_TK");
        }
    }

    private void nontermVars() throws Exception {
        return;
    }

    private void nontermStats() {
        return;
    }


}
