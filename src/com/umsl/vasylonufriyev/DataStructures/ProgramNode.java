package com.umsl.vasylonufriyev.DataStructures;

public class ProgramNode {
    String nodeLabel;
    public ProgramNode[] children = new ProgramNode[4];

    public ProgramNode(String label) {
        this.nodeLabel = label;
    }

    public String getNodeLabel() {
        return nodeLabel;
    }
}
