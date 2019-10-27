/*
Author: Vasyl Onufriyev
Date: 10.27.2019
Class: CS4280
Instructor: Professor Janikow
Description: Parses tokens from scanner using system stack, outputs a tree using ProgramNode objects.
*/

package com.umsl.vasylonufriyev.ProgramParser;

import com.umsl.vasylonufriyev.DataStructures.ProgramNode;
import com.umsl.vasylonufriyev.DataStructures.Token;
import com.umsl.vasylonufriyev.TokenScanner.ProgramDataBuffer;
import com.umsl.vasylonufriyev.TokenScanner.Scanner;

public class Parser {
    private ProgramDataBuffer programSource; //Source of the program to be fed into the scanner
    private Scanner scanner; //Scanner reference
    private Token lastTk; //Keeps track of the last toke obtained from scanner

    public Parser(ProgramDataBuffer programDataBuffer) { //dependency injection
        this.programSource = programDataBuffer;
        this.scanner = new Scanner();
        lastTk = null;
    }

    private Exception exceptionBuilder(String expectedTk) { //Builds exception strings for unexpected input from scanner
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

    private void getNextToken() throws Exception { //Gets next token from scanner and sets to lastTk
        lastTk = (scanner.scannerDriver(programSource)).getParsedTk();
    }

    private boolean compareToken(String expected) { //Compares token to given
        return lastTk.getTokenType().equals(expected);
    }

    public ProgramNode beginParse() throws Exception { //Starts parsing the program, entry point for parser
        getNextToken(); //get first token
        ProgramNode root = nontermProgram();

        if (compareToken("EOF_TK"))
            return root;
        else
            throw exceptionBuilder("EOF_TK");
    }

    private ProgramNode nontermProgram() throws Exception {
        ProgramNode node = new ProgramNode("<Program>");
        if (compareToken("VAR_TK") || compareToken("START_TK")) {
            node.children[0] = nontermVars();
            node.children[1] = nontermBlock();
            return node;
        } else {
            throw exceptionBuilder("VAR_TK OR START_TK");
        }
    }

    private ProgramNode nontermBlock() throws Exception {
        ProgramNode node = new ProgramNode("<Block>");
        if (compareToken("START_TK")) {
            getNextToken(); //consume startk
            node.children[0] = nontermVars();
            node.children[1] = nontermStats();
            if (compareToken("STOP_TK")) {
                getNextToken(); //consume stoptk
                return node;
            } else {
                throw exceptionBuilder("STOP_TK");
            }
        } else {
            throw exceptionBuilder("START_TK");
        }
    }

    private ProgramNode nontermVars() throws Exception {
        ProgramNode node = new ProgramNode("<Vars>");
        if (compareToken("VAR_TK")) {
            getNextToken(); //consume vartk
            if (compareToken("IDENTIFIER_TK")) {
                node.tokenData[0] = lastTk;
                getNextToken(); //consume identifiertk
                if (compareToken("COLON_TK")) {
                    getNextToken(); //consume colontk
                    if (compareToken("NUMBER_TK")) {
                        node.tokenData[1] = lastTk;
                        getNextToken(); //consume numbertk
                        node.children[0] = nontermVars();
                        return node;
                    } else {
                        throw exceptionBuilder("NUMBER_TK");
                    }
                } else {
                    throw exceptionBuilder("COLON_TK");
                }
            } else {
                throw exceptionBuilder("IDENTIFIER_TK");
            }
        }
        return null;
    }

    private ProgramNode nontermExpr() throws Exception { //FLAGGED
        ProgramNode node = new ProgramNode("<Expr>");
        if (compareToken("MINUS_TK") ||
                compareToken("SQUAREBRACKETOPEN_TK") ||
                compareToken("IDENTIFIER_TK") ||
                compareToken("NUMBER_TK")) {
            node.children[0] = nontermA();
            node.children[1] = nontermExprFactor();
            return node;
        } else {
            throw exceptionBuilder("MINUS_TK OR SQUAREBRACKETOPEN_TK OR IDENTIFIER_TK OR NUMBER_TK");
        }
    }

    private ProgramNode nontermExprFactor() throws Exception { //FLAGGED
        ProgramNode node = new ProgramNode("<ExprFactor>");
        if (compareToken("PLUS_TK")) {
            node.tokenData[0] = lastTk;
            getNextToken(); //consume +
            node.children[0] = nontermExpr();
            return node;
        } else if (compareToken("MINUS_TK")) {
            node.tokenData[0] = lastTk;
            getNextToken(); //consume -
            node.children[0] = nontermExpr();
            return node;
        }
        return null;
    }

    private ProgramNode nontermA() throws Exception { //FLAGGED
        ProgramNode node = new ProgramNode("<A>");
        if (compareToken("MINUS_TK") ||
                compareToken("SQUAREBRACKETOPEN_TK") ||
                compareToken("IDENTIFIER_TK") ||
                compareToken("NUMBER_TK")) {
            node.children[0] = nontermN();
            node.children[1] = nontermAFactor();
            return node;
        } else {
            throw exceptionBuilder("MINUS_TK OR SQUAREBRACKETOPEN_TK OR IDENTIFIER_TK OR NUMBER_TK");
        }
    }

    private ProgramNode nontermAFactor() throws Exception { //FLAGGED
        ProgramNode node = new ProgramNode("<AFactor>");
        if (compareToken("MINUS_TK")) {
            node.tokenData[0] = lastTk;
            getNextToken();
            node.children[0] = nontermA();
            return node;
        }
        return null;
    }

    private ProgramNode nontermN() throws Exception { //FLAGGED
        ProgramNode node = new ProgramNode("<N>");
        if (compareToken("MINUS_TK") ||
                compareToken("SQUAREBRACKETOPEN_TK") ||
                compareToken("IDENTIFIER_TK") ||
                compareToken("NUMBER_TK")) {
            node.children[0] = nontermM();
            node.children[1] = nontermNFactor();
            return node;
        } else {
            throw exceptionBuilder("MINUS_TK OR SQUAREBRACKETOPEN_TK OR IDENTIFIER_TK OR NUMBER_TK");
        }
    }

    private ProgramNode nontermNFactor() throws Exception { //FLAGGED
        ProgramNode node = new ProgramNode("<NFactor>");
        if (compareToken("DIVIDE_TK")) {
            node.tokenData[0] = lastTk;
            getNextToken(); //consume /
            node.children[0] = nontermN();
            return node;
        } else if (compareToken("MULT_TK")) {
            node.tokenData[0] = lastTk;
            getNextToken(); //consume *
            node.children[0] = nontermN();
            return node;
        }
        return null;
    }

    private ProgramNode nontermM() throws Exception { //FLAGGED
        ProgramNode node = new ProgramNode("<M>");
        if (compareToken("SQUAREBRACKETOPEN_TK") ||
                compareToken("IDENTIFIER_TK") ||
                compareToken("NUMBER_TK")) {
            node.children[0] = nontermR();
            return node;
        } else if (compareToken("MINUS_TK")) {
            getNextToken();
            node.children[0] = nontermM();
            return node;
        } else {
            throw exceptionBuilder("MINUS_TK OR SQUAREBRACKETOPEN_TK OR IDENTIFIER_TK OR NUMBER_TK");
        }
    }

    private ProgramNode nontermR() throws Exception { //FLAGGED
        ProgramNode node = new ProgramNode("<R>");
        if (compareToken("SQUAREBRACKETOPEN_TK")) {
            getNextToken(); //consume [
            node.children[0] = nontermExpr();
            if (compareToken("SQUAREBRACKETCLOSE_TK")) {
                getNextToken(); //consume ]
                return node;
            } else {
                throw exceptionBuilder("SQUAREBRACKETCLOSE_TK");
            }
        } else if (compareToken("IDENTIFIER_TK")) {
            node.tokenData[0] = lastTk;
            getNextToken(); //consume identifier
            return node;
        } else if (compareToken("NUMBER_TK")) {
            node.tokenData[0] = lastTk;
            getNextToken(); //consume number
            return node;
        } else {
            throw exceptionBuilder("MINUS_TK OR SQUAREBRACKETOPEN_TK OR IDENTIFIER_TK OR NUMBER_TK");
        }
    }

    private ProgramNode nontermStats() throws Exception {
        ProgramNode node = new ProgramNode("<Stats>");
        if (compareToken("IN_TK") ||
                compareToken("OUT_TK") ||
                compareToken("START_TK") ||
                compareToken("COND_TK") ||
                compareToken("ITERATE_TK") ||
                compareToken("IDENTIFIER_TK")) {
            node.children[0] = nontermStat();
            if (compareToken("SEMICOLON_TK")) {
                getNextToken();
            } else {
                throw exceptionBuilder("SEMICOLON_TK");
            }
            node.children[1] = nontermMStat();
            return node;
        } else {
            throw exceptionBuilder("IN_TK OR OUT_TK OR OR START_TK OR COND_TK OR ITERATE_TK OR IDENTIFIER_TK");
        }
    }

    private ProgramNode nontermMStat() throws Exception {
        ProgramNode node = new ProgramNode("<MStat>");
        //in, out, start, cond, iterate, Identifier
        if (compareToken("IN_TK") ||
                compareToken("OUT_TK") ||
                compareToken("START_TK") ||
                compareToken("COND_TK") ||
                compareToken("ITERATE_TK") ||
                compareToken("IDENTIFIER_TK")) {
            node.children[0] = nontermStat();
            if (compareToken("SEMICOLON_TK")) {
                getNextToken();
            }
            node.children[1] = nontermMStat();
            return node;
        }
        return null;
    }

    private ProgramNode nontermStat() throws Exception {
        ProgramNode node = new ProgramNode("<Stat>");
        if (compareToken("IN_TK")) {
            node.children[0] = nontermIn();
            return node;
        } else if (compareToken("OUT_TK")) {
            node.children[0] = nontermOut();
            return node;
        } else if (compareToken("START_TK")) {
            node.children[0] = nontermBlock();
            return node;
        } else if (compareToken("COND_TK")) {
            node.children[0] = nontermIf();
            return node;
        } else if (compareToken("ITERATE_TK")) {
            node.children[0] = nontermLoop();
            return node;
        } else if (compareToken("IDENTIFIER_TK")) {
            node.children[0] = nontermAssign();
            return node;
        } else {
            throw exceptionBuilder("IN_TK, OR OUT_TK OR START_TK OR COND_TK OR ITERATE_TK OR IDENTIFIER_TK");
        }
    }

    private ProgramNode nontermIn() throws Exception {
        ProgramNode node = new ProgramNode("<In>");
        if (compareToken("IN_TK")) {
            getNextToken(); //consume intk
            if (compareToken("IDENTIFIER_TK")) {
                node.tokenData[0] = lastTk;
                getNextToken(); //consume identifier tk
                return node;
            } else {
                throw exceptionBuilder("IDENTIFIER_TK");
            }
        } else {
            throw exceptionBuilder("IN_TK");
        }
    }

    private ProgramNode nontermOut() throws Exception {
        ProgramNode node = new ProgramNode("<Out>");
        if (compareToken("OUT_TK")) {
            getNextToken(); //consume outtk
            node.children[0] = nontermExpr();
            return node;
        } else {
            throw exceptionBuilder("OUT_TK");
        }

    }

    private ProgramNode nontermIf() throws Exception {
        ProgramNode node = new ProgramNode("<If>");
        if (compareToken("COND_TK")) {
            getNextToken(); //consume cond
            if (compareToken("PARENTHESISOPEN_TK")) {
                getNextToken(); //consme (
                if (compareToken("PARENTHESISOPEN_TK")) {
                    getNextToken(); //consume (
                    node.children[0] = nontermExpr();
                    node.children[1] = nontermRO();
                    node.children[2] = nontermExpr();
                    if (compareToken("PARENTHESISCLOSE_TK")) {
                        getNextToken(); //consume )
                        if (compareToken("PARENTHESISCLOSE_TK")) {
                            getNextToken(); //consume )
                            node.children[3] = nontermStat();
                            return node;
                        } else {
                            throw exceptionBuilder("PARENTHESISCLOSE_TK");
                        }
                    } else {
                        throw exceptionBuilder("PARENTHESISCLOSE_TK");
                    }
                } else {
                    throw exceptionBuilder("PARENTHESISOPEN_TK");
                }
            } else {
                throw exceptionBuilder("PARENTHESISOPEN_TK");
            }
        } else {
            throw exceptionBuilder("COND_TK");
        }
    }

    private ProgramNode nontermLoop() throws Exception {
        ProgramNode node = new ProgramNode("<Loop>");
        if (compareToken("ITERATE_TK")) {
            getNextToken();
            if (compareToken("PARENTHESISOPEN_TK")) {
                getNextToken(); //consme (
                if (compareToken("PARENTHESISOPEN_TK")) {
                    getNextToken(); //consume (
                    node.children[0] = nontermExpr();
                    node.children[1] = nontermRO();
                    node.children[2] = nontermExpr();
                    if (compareToken("PARENTHESISCLOSE_TK")) {
                        getNextToken(); //consume )
                        if (compareToken("PARENTHESISCLOSE_TK")) {
                            getNextToken(); //consume )
                            node.children[3] = nontermStat();
                            return node;
                        } else {
                            throw exceptionBuilder("PARENTHESISCLOSE_TK");
                        }
                    } else {
                        throw exceptionBuilder("PARENTHESISCLOSE_TK");
                    }
                } else {
                    throw exceptionBuilder("PARENTHESISOPEN_TK");
                }
            } else {
                throw exceptionBuilder("PARENTHESISOPEN_TK");
            }
        } else {
            throw exceptionBuilder("ITERATE_TK");
        }
    }

    private ProgramNode nontermAssign() throws Exception {
        ProgramNode node = new ProgramNode("<Assign>");
        if (compareToken("IDENTIFIER_TK")) {
            node.tokenData[0] = lastTk;
            getNextToken(); //consume identifier
            if (compareToken("LESSTHAN_TK")) {
                node.tokenData[1] = lastTk;
                getNextToken(); //consume less than
                if (compareToken("LESSTHAN_TK")) {
                    node.tokenData[2] = lastTk;
                    getNextToken(); //consume less than
                    node.children[0] = nontermExpr();
                    return node;
                } else {
                    throw exceptionBuilder("LESSTHAN_TK");
                }
            } else {
                throw exceptionBuilder("LESSTHAN_TK");
            }
        } else {
            throw exceptionBuilder("IDENTIFIER_TK");
        }
    }

    private ProgramNode nontermRO() throws Exception {
        ProgramNode node = new ProgramNode("<RO>");
        if (compareToken("LESSTHAN_TK")) {
            node.tokenData[0] = lastTk;
            getNextToken();
            node.children[0] = nontermROFactorLT();
            return node;
        } else if (compareToken("GREATERTHAN_TK")) {
            node.tokenData[0] = lastTk;
            getNextToken();
            node.children[0] = nontermVaROFactorGT();
            return node;
        } else if (compareToken("ASSIGN_TK")) {
            node.tokenData[0] = lastTk;
            getNextToken();
            return node;
        } else {
            throw exceptionBuilder("LESSTHAN_TK OR GREATERTHAN_TK OR ASSIGN_TK");
        }
    }

    private ProgramNode nontermVaROFactorGT() throws Exception {
        ProgramNode node = new ProgramNode("<ROFactorGT>");
        if (compareToken("GREATERTHAN_TK")) {
            node.tokenData[0] = lastTk;
            getNextToken();
            return node;
        }
        return null;
    }

    private ProgramNode nontermROFactorLT() throws Exception {
        ProgramNode node = new ProgramNode("<ROFactorLT>");
        if (compareToken("GREATERTHAN_TK")) {
            node.tokenData[0] = lastTk;
            getNextToken();
            return node;

        } else if (compareToken("LESSTHAN_TK")) {
            node.tokenData[0] = lastTk;
            getNextToken();
            return node;
        }
        return null;
    }
}
