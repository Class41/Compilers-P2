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

public class Tree {
    private Node root; //reference to root node

    public Tree() {
        root = null;
    }

    private void printPostorder(Node node, int depth, FileWriter fs) throws IOException {
        if (node == null)
            return;

        printPostorder(node.left, depth + 1, fs);
        printPostorder(node.right, depth + 1, fs);
        for (int i = 0; i < (depth * 2); i++)
            fs.write(" ");

        fs.write(node.key + " ");

        for (Object s : node.values) {
            fs.write(s + " ");
        }

        fs.write("\n");
    }

    private void printInorder(Node node, int depth, FileWriter fs) throws IOException {
        if (node == null)
            return;

        printInorder(node.left, depth + 1, fs);
        for (int i = 0; i < (depth * 2); i++)
            fs.write(" ");

        fs.write(node.key + " ");

        for (Object s : node.values) {
            fs.write(s + " ");
        }

        fs.write("\n");
        printInorder(node.right, depth + 1, fs);
    }

    private void printPreorder(Node node, int depth, FileWriter fs) throws IOException {
        if (node == null)
            return;

        for (int i = 0; i < (depth * 2); i++)
            fs.write(" ");

        fs.write(node.key + " ");

        for (Object s : node.values) {
            fs.write(s + " ");
        }

        fs.write("\n");
        printPreorder(node.left, depth + 1, fs);
        printPreorder(node.right, depth + 1, fs);

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

    public Tree buildTree(String[] dataSet) {
        for (String s : dataSet)
            insertNode(s);

        return this;
    }

    private void insertNode(String s) {
        char key = s.charAt(0); //check first character of string, this is our key

        if (root == null) { //if there is not root, this is now our root
            root = new Node<String>(key, s);
        } else { //otherwise
            Node currentNode = root; //set the current node visited equal to root
            boolean placed = false; //keeps track if we have placed the item

            while (!placed) {
                if (key == currentNode.key) { //check if the key matches current node
                    currentNode.addValueToValues(s);
                    placed = true;
                } else if (key > currentNode.key) { //check if current key greater than node key
                    if (currentNode.right != null) {
                        currentNode = currentNode.right;
                    } else {
                        currentNode.right = new Node<String>(key, s);
                        placed = true;
                    }
                } else { //if key is less than node key
                    if (currentNode.left != null) {
                        currentNode = currentNode.left;
                    } else {
                        currentNode.left = new Node<String>(key, s);
                        placed = true;
                    }
                }
            }
        }
    }
}
