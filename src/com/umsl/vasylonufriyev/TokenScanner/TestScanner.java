/*
Author: Vasyl Onufriyev
Date: 10.1.19
Class: CS4280
Instructor: Professor Janikow
Description: Temporarily drives the scanner until the next project
*/

package com.umsl.vasylonufriyev.TokenScanner;

public class TestScanner {
    public TestScanner(String[] parsedData) { //Drives the scanner until EOF
        ProgramDataBuffer t = new ProgramDataBuffer(parsedData); //Data buffer contains filtered file data
        Scanner s = new Scanner(); //My file scanner
        do {
            try {
                s.scannerDriver(t); //Try to parse and retrieve token of next item in file
                System.out.println(t.getParsedTk().toString()); //print on success
            } catch (Exception e) {
                System.out.println(e.getMessage()); //Invalid action performed, quit
                System.exit(-1);
            }
        } while (!t.getParsedTk().getTokenType().equals("EOF_TK"));
    }
}
