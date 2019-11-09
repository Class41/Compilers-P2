/*
Author: Vasyl Onufriyev
Date: 8.28.2019
Class: CS4280
Instructor: Professor Janikow
Description: Initial point of the program--contains the main function and commandline argument validation/parsing
*/

package com.umsl.vasylonufriyev;

import com.umsl.vasylonufriyev.DataStructures.ProgramNode;
import com.umsl.vasylonufriyev.ProgramParser.ProgramTreePrint;
import com.umsl.vasylonufriyev.DatasourceParser.ParseCMD;
import com.umsl.vasylonufriyev.DatasourceParser.ParseFile;
import com.umsl.vasylonufriyev.ProgramParser.Parser;
import com.umsl.vasylonufriyev.TokenScanner.ProgramDataBuffer;

public class Main {

    public static void main(String[] args) {
        String[] parsedData = classifyAndParseTokens(args);

        if (parsedData == null) { //for the cast that no data was parsed or returned for whatever reason
            return;
        } else if (parsedData.length == 0) { //for case that input file is empty
            System.out.println("Invalid input length");
            return;
        }

        System.out.println("~~ read " + parsedData.length + " lines. ~~");
        Parser parser = new Parser(new ProgramDataBuffer(parsedData));

        try {
            ProgramNode parseResult = parser.beginParse();
            System.out.println("SUCCESSFULLY PARSED");
            ProgramTreePrint.treePrintPreorder(parseResult, 0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private static String[] classifyAndParseTokens(String[] cmdArgs) {
        String[] dataSet = null;

        switch (cmdArgs.length) { //check length of list
            case 0: //if there is no filename provided
                ParseCMD cmdInputParser = new ParseCMD();
                dataSet = cmdInputParser.getParseResult();
                break;
            case 1: //if a filename is provided
                ParseFile fileInputParser = new ParseFile(cmdArgs[0]);
                dataSet = fileInputParser.getParseResult();

                break;
            default: //if more than 1 parameter is provided (filename)
                System.out.println("Input exceeded expected argument count. Expected 0 or 1 got " + cmdArgs.length);
                break;
        }

        return dataSet;
    }
}
