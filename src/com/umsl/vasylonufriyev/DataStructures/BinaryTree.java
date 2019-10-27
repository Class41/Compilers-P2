/*
Author: Vasyl Onufriyev
Binary tree code copied from: https://www.geeksforgeeks.org/tree-traversals-inorder-preorder-and-postorder/
Date: 8.28.2019
Class: CS4280
Instructor: Professor Janikow
Description: Tree Implementation, plus tree insert implementation. Handles binary trees
*/

package com.umsl.vasylonufriyev.DataStructures;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BinaryTree {
    private BinaryNode root; //reference to root node

    public BinaryTree() {
        root = null;
    }

    private void printPostorder(BinaryNode binaryNode, int depth, FileWriter fs) throws IOException {
        if (binaryNode == null)
            return;

        printPostorder(binaryNode.left, depth + 1, fs);
        printPostorder(binaryNode.right, depth + 1, fs);
        for (int i = 0; i < (depth * 2); i++)
            fs.write(" ");

        fs.write(binaryNode.key + " ");

        for (Object s : binaryNode.values) {
            fs.write(s + " ");
        }

        fs.write("\n");
    }

    private void printInorder(BinaryNode binaryNode, int depth, FileWriter fs) throws IOException {
        if (binaryNode == null)
            return;

        printInorder(binaryNode.left, depth + 1, fs);
        for (int i = 0; i < (depth * 2); i++)
            fs.write(" ");

        fs.write(binaryNode.key + " ");

        for (Object s : binaryNode.values) {
            fs.write(s + " ");
        }

        fs.write("\n");
        printInorder(binaryNode.right, depth + 1, fs);
    }

    private void printPreorder(BinaryNode binaryNode, int depth, FileWriter fs) throws IOException {
        if (binaryNode == null)
            return;

        for (int i = 0; i < (depth * 2); i++)
            fs.write(" ");

        fs.write(binaryNode.key + " ");

        for (Object s : binaryNode.values) {
            fs.write(s + " ");
        }

        fs.write("\n");
        printPreorder(binaryNode.left, depth + 1, fs);
        printPreorder(binaryNode.right, depth + 1, fs);

    }

    public void printPostorder(String outputBaseString) {

        try {
            FileWriter fs = new FileWriter(new File("./" + outputBaseString + ".postorder"));
            printPostorder(root, 0, fs);
            fs.close();
        } catch (IOException e) {
            System.out.println("Failed to open output file stream for a traversal");
            System.exit(-1);
        }
    }

    public void printInorder(String outputBaseString) {

        try {
            FileWriter fs = new FileWriter(new File("./" + outputBaseString + ".inorder"));
            printInorder(root, 0, fs);
            fs.close();
        } catch (IOException e) {
            System.out.println("Failed to open output file stream for a traversal");
            System.exit(-1);
        }
    }

    public void printPreorder(String outputBaseString) {

        try {
            FileWriter fs = new FileWriter(new File("./" + outputBaseString + ".preorder"));
            printPreorder(root, 0, fs);
            fs.close();
        } catch (IOException e) {
            System.out.println("Failed to open output file stream for a traversal");
            System.exit(-1);
        }
    }

    public BinaryTree buildTree(String[] dataSet) {
        for (String s : dataSet)
            insertNode(s);

        return this;
    }

    private void insertNode(String s) {
        char key = s.charAt(0); //check first character of string, this is our key

        if (root == null) { //if there is not root, this is now our root
            root = new BinaryNode<String>(key, s);
        } else { //otherwise
            BinaryNode currentBinaryNode = root; //set the current node visited equal to root
            boolean placed = false; //keeps track if we have placed the item

            while (!placed) {
                if (key == currentBinaryNode.key) { //check if the key matches current node
                    currentBinaryNode.addValueToValues(s);
                    placed = true;
                } else if (key > currentBinaryNode.key) { //check if current key greater than node key
                    if (currentBinaryNode.right != null) {
                        currentBinaryNode = currentBinaryNode.right;
                    } else {
                        currentBinaryNode.right = new BinaryNode<String>(key, s);
                        placed = true;
                    }
                } else { //if key is less than node key
                    if (currentBinaryNode.left != null) {
                        currentBinaryNode = currentBinaryNode.left;
                    } else {
                        currentBinaryNode.left = new BinaryNode<String>(key, s);
                        placed = true;
                    }
                }
            }
        }
    }
}
