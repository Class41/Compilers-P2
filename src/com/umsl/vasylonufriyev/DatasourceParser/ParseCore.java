/*
Author: Vasyl Onufriyev
Date: 8.28.2019
Class: CS4280
Instructor: Professor Janikow
Description: Performs file read-in. Different source inputs are facilitated by different constructors.
*/

package com.umsl.vasylonufriyev.DatasourceParser;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ParseCore {
    private String[] parseResult = null; //declare/ temp initialize token array

    private ParseSourceType inType; //Stores determined type of input, file or commandline stdin input
    private String fileString;

    ParseCore() { //no name passed, assume cmd stdin input
        inType = ParseSourceType.CMDINPUT;
        doParse();
    }

    ParseCore(String qualifiedFileName) { //got name, proceed with trying to read file
        inType = ParseSourceType.FILEINPUT;
        fileString = qualifiedFileName;
        doParse();
    }

    private void doParse() {
        File finRef = null; //file input reference
        FileInputStream finStream = null; //file stream for input
        Scanner dataScanner = null; //scanner

        switch (inType) { //depending on input type enum, perform different actions
            case CMDINPUT: //if cmd stdin input
                dataScanner = new Scanner(System.in); //stdin readin
                break;
            case FILEINPUT: //if file input
                finRef = new File("./" + fileString);

                try {
                    finStream = new FileInputStream(finRef);
                } catch (Exception e) {
                    System.out.println("Failed to open input stream.");
                    System.exit(-1);
                }

                dataScanner = new Scanner(finStream); //put in the file stream into the scanner
                break;
        }

        readInData(dataScanner);

        try {
            if (finStream != null) //make sure we aren't leaving behind a mem leak
                finStream.close();
        } catch (Exception e) {
            System.out.println("Failed to close input file stream...");
        }
    }

    private void readInData(Scanner dataScanner) {
        List<String> data = new ArrayList<String>();

        while (dataScanner.useDelimiter("\\n").hasNext()) { //split on new line
            String readData = dataScanner.next();
            if (readData.length() > 0) //check length for case where just spaces/extra spaces
            {
                if (readData.indexOf('#') >= 0) { //substring to remove comments while reading it
                    data.add((readData.substring(0, readData.indexOf('#'))) + " "); //Adds spaces at the end as replace for new line
                } else {
                    data.add(readData + " "); //Adds spaces at the end
                }
            }
        }

        parseResult = new String[data.size()];

        System.arraycopy(data.toArray(), 0, parseResult, 0, data.size()); //copy over the arraylist to array

    }

    String[] getResult() {
        return parseResult;
    }
}
