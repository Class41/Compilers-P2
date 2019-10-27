/*
Author: Vasyl Onufriyev
Date: 10.27.2019
Class: CS4280
Instructor: Professor Janikow
Description: Program node. Used in generating a tree for traversal later.
*/

package com.umsl.vasylonufriyev.DataStructures;

public class ProgramNode {
    String nodeLabel;
    public ProgramNode[] children = new ProgramNode[4];
    public Token[] tokenData = new Token[4];

    public ProgramNode(String label) {
        this.nodeLabel = label;
    }

    public String getNodeLabel() {
        return nodeLabel;
    }
}
