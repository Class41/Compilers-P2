package com.umsl.vasylonufriyev.DataStructures;

public class ProgramTreePrint {
    public static void treePrintPreorder(ProgramNode node, int depth) {
        printMe(node, depth);
        for (int i = 0; i < node.children.length; i++) {
            if (node.children[i] != null) {
                treePrintPreorder(node.children[i], depth + 1);
            }
        }
    }

    private static void printMe(ProgramNode node, int depth) {
        StringBuilder sb = new StringBuilder();
        if (depth > 0) {
            sb.append("|  ".repeat(Math.max(0, depth)));
            //sb.append("| ");
        }
        sb.append(node.nodeLabel);
        sb.append(" (");

        for (Token t : node.tokenData) {
            if (t != null)
                sb.append(t.toString());
        }
        sb.append(")");

        System.out.println(sb.toString());
    }
}
