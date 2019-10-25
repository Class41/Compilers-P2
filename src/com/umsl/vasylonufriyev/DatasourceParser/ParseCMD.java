/*
Author: Vasyl Onufriyev
Date: 8.28.2019
Class: CS4280
Instructor: Professor Janikow
Description: Performs preparation to parse commandline arguments. 
Although this is empty, it will be used for future projects for pre-processing. 
*/

package com.umsl.vasylonufriyev.DatasourceParser;

public class ParseCMD {
    private ParseCore parser;

    public ParseCMD() {
        System.out.println("Attempting to read data from console input...");

        parser = new ParseCore();
    }

    public String[] getParseResult() {
        return parser.getResult();
    }
}
