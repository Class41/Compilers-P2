/*
Author: Vasyl Onufriyev
Date: 8.28.2019
Class: CS4280
Instructor: Professor Janikow
Description: Prepares file arg for processing and checks for file existence and appends extension if nonexistent
*/
package com.umsl.vasylonufriyev.DatasourceParser;

import java.io.File;

public class ParseFile {
    private static final String EXTENSION = ".fs19";
    private ParseCore parser;

    public ParseFile(String cmdArg) {
        String qualifiedFileName = cmdArg;
        System.out.println("Filename detected. Attempt to read from file.");

        if (cmdArg.endsWith(".fs19")) { //check if extension exists
            System.out.println("Extension detected. Proceeding normally.");
        } else { //if not, append it
            qualifiedFileName += EXTENSION;
        }

        File inFile = new File("./" + qualifiedFileName);

        try {
            if (!inFile.exists()) { //check if file exists
                System.out.println("File " + inFile.getCanonicalPath() + " does not exist. Aborting");
                return;
            }
            if (!inFile.canRead()) { //check is file is readable
                System.out.println("File " + inFile.getCanonicalPath() + " cannot be read. Aborting");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error getting canonical path of file...");
            return;
        }

        parser = new ParseCore(qualifiedFileName);
    }

    public String[] getParseResult() {
        if(parser != null)
            return parser.getResult();
        else
            return null;
    }
}
