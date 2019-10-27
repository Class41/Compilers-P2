/*
Author: Vasyl Onufriyev
Date: 8.28.2019
Class: CS4280
Instructor: Professor Janikow
Description: Generic node template class
*/

package com.umsl.vasylonufriyev.DataStructures;

import java.util.HashSet;
import java.util.Set;
/* Templated class for a node */
class BinaryNode<T> {
    Set<T> values; //set of values to be stored in this node
    char key; //key of node
    BinaryNode left, right; //references to children nodes

    BinaryNode(char key, T item) {
        this.key = key;
        values = new HashSet<>();
        values.add(item);
        left = right = null;
    }

    void addValueToValues(T value) {
        values.add(value);
    }
}
