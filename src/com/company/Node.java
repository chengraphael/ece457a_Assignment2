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
    public int serviceTime; //For Q5

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getCost() {
        if (parent == null) {
            return 0;
        } else {
            return parent.getCost() + 1;
        }
    }

    public void print() {
        System.out.print("Final coordinates: (" + x + ", " + y + ") by ");
        this.printPath();
        System.out.println("Path cost: " + getCost());
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
