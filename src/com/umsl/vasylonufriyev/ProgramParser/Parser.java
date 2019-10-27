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

    private Exception exceptionBuilder(String expectedTk) {
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
        getNextToken(); //get first token
        nontermProgram();

        if (compareToken("EOF_TK"))
            return programTree;
        else
            throw exceptionBuilder("EOF_TK");
    }

    private void nontermProgram() throws Exception {
        if (compareToken("VAR_TK") || compareToken("START_TK")) {
            nontermVars();
            nontermBlock();
        } else {
            throw exceptionBuilder("VAR_TK OR START_TK");
        }
    }

    private void nontermBlock() throws Exception {
        if (compareToken("START_TK")) {
            getNextToken(); //consume startk
            nontermVars();
            nontermStats();
            if (compareToken("STOP_TK")) {
                getNextToken(); //consume stoptk
            } else {
                throw exceptionBuilder("STOP_TK");
            }
        } else {
            throw exceptionBuilder("START_TK");
        }
    }

    private void nontermVars() throws Exception {
        if (compareToken("VAR_TK")) {
            getNextToken(); //consume vartk
            if (compareToken("IDENTIFIER_TK")) {
                getNextToken(); //consume identifiertk

                if (compareToken("COLON_TK")) {
                    getNextToken(); //consume colontk

                    if (compareToken("NUMBER_TK")) {
                        getNextToken(); //consume numbertk
                        nontermVars();
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
    }

    private void nontermExpr() throws Exception { //FLAGGED
        if (compareToken("MINUS_TK") ||
                compareToken("SQUAREBRACKETOPEN_TK") ||
                compareToken("IDENTIFIER_TK") ||
                compareToken("NUMBER_TK")) {
            nontermA();
            nontermExprFactor();
        } else {
            throw exceptionBuilder("MINUS_TK OR SQUAREBRACKETOPEN_TK OR IDENTIFIER_TK OR NUMBER_TK");
        }
    }

    private void nontermExprFactor() throws Exception { //FLAGGED
        if (compareToken("PLUS_TK")) {
            getNextToken(); //consume +
            nontermExpr();
        } else if (compareToken("MINUS_TK")) {
            getNextToken(); //consume -
            nontermExpr();
        }
        return;
    }

    private void nontermA() throws Exception { //FLAGGED
        if (compareToken("MINUS_TK") ||
                compareToken("SQUAREBRACKETOPEN_TK") ||
                compareToken("IDENTIFIER_TK") ||
                compareToken("NUMBER_TK")) {
            nontermN();
            nontermAFactor();
        } else {
            throw exceptionBuilder("MINUS_TK OR SQUAREBRACKETOPEN_TK OR IDENTIFIER_TK OR NUMBER_TK");
        }
    }

    private void nontermAFactor() throws Exception { //FLAGGED
        if (compareToken("MINUS_TK")) {
            getNextToken();
            nontermA();
        }
        return;
    }

    private void nontermN() throws Exception { //FLAGGED
        if (compareToken("MINUS_TK") ||
                compareToken("SQUAREBRACKETOPEN_TK") ||
                compareToken("IDENTIFIER_TK") ||
                compareToken("NUMBER_TK")) {
            nontermM();
            nontermNFactor();
        } else {
            throw exceptionBuilder("MINUS_TK OR SQUAREBRACKETOPEN_TK OR IDENTIFIER_TK OR NUMBER_TK");
        }
    }

    private void nontermNFactor() throws Exception { //FLAGGED
        if (compareToken("DIVIDE_TK")) {
            getNextToken(); //consume /
            nontermN();
        } else if (compareToken("MULT_TK")) {
            getNextToken(); //consume *
            nontermN();
        }
        return;
    }

    private void nontermM() throws Exception { //FLAGGED
        if (compareToken("SQUAREBRACKETOPEN_TK") ||
                compareToken("IDENTIFIER_TK") ||
                compareToken("NUMBER_TK")) {
            nontermR();
        } else if (compareToken("MINUS_TK")) {
            getNextToken();
            nontermM();
        } else {
            throw exceptionBuilder("MINUS_TK OR SQUAREBRACKETOPEN_TK OR IDENTIFIER_TK OR NUMBER_TK");
        }
    }

    private void nontermR() throws Exception { //FLAGGED
        if (compareToken("SQUAREBRACKETOPEN_TK")) {
            getNextToken(); //consume [
            nontermExpr();
            if (compareToken("SQUAREBRACKETCLOSE_TK")) {
                getNextToken(); //consume ]
            } else {
                throw exceptionBuilder("SQUAREBRACKETCLOSE_TK");
            }
        } else if (compareToken("IDENTIFIER_TK")) {
            getNextToken(); //consume identifier
        } else if (compareToken("NUMBER_TK")) {
            getNextToken(); //consume number
        } else {
            throw exceptionBuilder("MINUS_TK OR SQUAREBRACKETOPEN_TK OR IDENTIFIER_TK OR NUMBER_TK");
        }
    }

    private void nontermStats() throws Exception {
        if (compareToken("IN_TK") ||
                compareToken("OUT_TK") ||
                compareToken("START_TK") ||
                compareToken("COND_TK") ||
                compareToken("ITERATE_TK") ||
                compareToken("IDENTIFIER_TK")) {
            nontermStat();
            if (compareToken("SEMICOLON_TK")) {
                getNextToken();
            } else {
                throw exceptionBuilder("SEMICOLON_TK");
            }
            nontermMStat();
        } else {
            throw exceptionBuilder("IN_TK OR OUT_TK OR OR START_TK OR COND_TK OR ITERATE_TK OR IDENTIFIER_TK");
        }
    }

    private void nontermMStat() throws Exception {
        //in, out, start, cond, iterate, Identifier
        if (compareToken("IN_TK") ||
                compareToken("OUT_TK") ||
                compareToken("START_TK") ||
                compareToken("COND_TK") ||
                compareToken("ITERATE_TK") ||
                compareToken("IDENTIFIER_TK")) {
            nontermStat();
            if (compareToken("SEMICOLON_TK")) {
                getNextToken();
            }
            nontermMStat();
        }
        return;
    }

    private void nontermStat() throws Exception {
        if (compareToken("IN_TK")) {
            nontermIn();
        } else if (compareToken("OUT_TK")) {
            nontermOut();
        } else if (compareToken("START_TK")) {
            nontermBlock();
        } else if (compareToken("COND_TK")) {
            nontermIf();
        } else if (compareToken("ITERATE_TK")) {
            nontermLoop();
        } else if (compareToken("IDENTIFIER_TK")) {
            nontermAssign();
        } else {
            throw exceptionBuilder("IN_TK, OR OUT_TK OR START_TK OR COND_TK OR ITERATE_TK OR IDENTIFIER_TK");
        }

    }

    private void nontermIn() throws Exception {
        if (compareToken("IN_TK")) {
            getNextToken(); //consume intk
            if (compareToken("IDENTIFIER_TK")) {
                getNextToken(); //consume identifier tk
            } else {
                throw exceptionBuilder("IDENTIFIER_TK");
            }
        } else {
            throw exceptionBuilder("IN_TK");
        }

    }

    private void nontermOut() throws Exception {
        if (compareToken("OUT_TK")) {
            getNextToken(); //consume outtk
            nontermExpr();
        } else {
            throw exceptionBuilder("OUT_TK");
        }

    }

    private void nontermIf() throws Exception {
        if (compareToken("COND_TK")) {
            getNextToken(); //consume cond
            if (compareToken("PARENTHESISOPEN_TK")) {
                getNextToken(); //consme (
                if (compareToken("PARENTHESISOPEN_TK")) {
                    getNextToken(); //consume (
                    nontermExpr();
                    nontermRO();
                    nontermExpr();
                    if (compareToken("PARENTHESISCLOSE_TK")) {
                        getNextToken(); //consume )
                        if (compareToken("PARENTHESISCLOSE_TK")) {
                            getNextToken(); //consume )
                            nontermStat();
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

    private void nontermLoop() throws Exception {
        if (compareToken("ITERATE_TK")) {
            getNextToken();
            if (compareToken("PARENTHESISOPEN_TK")) {
                getNextToken(); //consme (
                if (compareToken("PARENTHESISOPEN_TK")) {
                    getNextToken(); //consume (
                    nontermExpr();
                    nontermRO();
                    nontermExpr();
                    if (compareToken("PARENTHESISCLOSE_TK")) {
                        getNextToken(); //consume )
                        if (compareToken("PARENTHESISCLOSE_TK")) {
                            getNextToken(); //consume )
                            nontermStat();
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

    private void nontermAssign() throws Exception {
        if (compareToken("IDENTIFIER_TK")) {
            getNextToken(); //consume identifier
            if (compareToken("LESSTHAN_TK")) {
                getNextToken(); //consume less than
                if (compareToken("LESSTHAN_TK")) {
                    getNextToken(); //consume less than
                    nontermExpr();
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

    private void nontermRO() throws Exception {
        if (compareToken("LESSTHAN_TK")) {
            getNextToken();
            nontermROFactorLT();
        } else if (compareToken("GREATERTHAN_TK")) {
            getNextToken();
            nontermVaROFactorGT();
        } else if (compareToken("ASSIGN_TK")) {
            getNextToken();
        } else {
            throw exceptionBuilder("LESSTHAN_TK OR GREATERTHAN_TK OR ASSIGN_TK");
        }
    }

    private void nontermVaROFactorGT() throws Exception {
        if (compareToken("GREATERTHAN_TK")) {
            getNextToken();
        }
        return;
    }

    private void nontermROFactorLT() throws Exception {
        if (compareToken("GREATERTHAN_TK")) {
            getNextToken();
        } else if (compareToken("LESSTHAN_TK")) {
            getNextToken();
        }
        return;
    }


}
