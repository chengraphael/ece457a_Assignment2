package com.company;

/**
 * Created by Ingenious on 2016-06-21.
 */
public class Node {
    public final int x;
    public final int y;
    public int distance;
    public double realDistance;
    public Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void print() {
        System.out.print("Final coordinates: (" + x + ", " + y + ") by ");
        this.printPath();
    }

    public void printPath() {
        System.out.print("(" + x + ", " + y + ")");
        if (parent == null) {
            System.out.println();
        }
        else {
            System.out.print(", ");
            parent.printPath();
        }
    }
}
