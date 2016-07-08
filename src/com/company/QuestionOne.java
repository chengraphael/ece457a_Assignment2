package com.company;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ingenious on 2016-06-21.
 */
public class QuestionOne {
    //    int[][] maze;
//    Queue<Integer> queue;
    private int gridLength;
    private int startX;
    private int startY;
    private int exitX;
    private int exitY;
    private HashMap<Integer, HashSet<Integer>> walls;

    public QuestionOne(int gridLength) {
        this.gridLength = gridLength;
        walls = new HashMap<Integer, HashSet<Integer>>();

        // Manual walls
        walls.put(0, new HashSet(Arrays.asList(new Integer[] {4, 5, 17, 18})));
        walls.put(1, new HashSet(Arrays.asList(new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})));
        walls.put(2, new HashSet(Arrays.asList(new Integer[] {0, 1, 2, 3, 4, 8, 14, 15, 16, 17, 18, 19, 20})));
        walls.put(3, new HashSet(Arrays.asList(new Integer[] {3, 4, 5, 8, 14, 15, 16, 17, 18, 19, 20})));
        walls.put(4, new HashSet(Arrays.asList(new Integer[] {3, 4, 5, 8, 9, 10, 14, 15, 16, 17, 18, 19, 20})));
        walls.put(5, new HashSet(Arrays.asList(new Integer[] {8, 9, 10})));
        walls.put(6, new HashSet(Arrays.asList(new Integer[] {8, 9, 10, 14, 15, 16, 17, 18, 19, 20})));
        walls.put(7, new HashSet(Arrays.asList(new Integer[] {0, 1, 2, 3, 4, 5, 6, 8})));
        walls.put(8, new HashSet(Arrays.asList(new Integer[] {10, 24})));
        walls.put(9, new HashSet(Arrays.asList(new Integer[] {10, 23, 24})));
        walls.put(10, new HashSet(Arrays.asList(new Integer[] {8, 9, 10, 11, 12, 15, 16, 22, 23, 24})));
        walls.put(11, new HashSet(Arrays.asList(new Integer[] {10, 15, 16, 21, 22, 23, 24})));
        walls.put(12, new HashSet(Arrays.asList(new Integer[] {10, 15, 16, 18, 21, 24})));
        walls.put(13, new HashSet(Arrays.asList(new Integer[] {4, 15, 16, 18, 21})));
        walls.put(14, new HashSet(Arrays.asList(new Integer[] {15, 16, 18, 21})));
        walls.put(15, new HashSet(Arrays.asList(new Integer[] {18, 21})));
        walls.put(16, new HashSet(Arrays.asList(new Integer[] {2, 3, 4, 18, 21})));
        walls.put(17, new HashSet(Arrays.asList(new Integer[] {2, 3, 4, 6, 7, 10, 11, 12, 18})));
        walls.put(18, new HashSet(Arrays.asList(new Integer[] {2, 6, 7, 10, 11, 12, 18, 19, 20, 21, 22, 23})));
        walls.put(19, new HashSet(Arrays.asList(new Integer[] {0, 1, 2, 6, 7, 10, 11, 12, 18, 19, 20, 21})));
        walls.put(20, new HashSet(Arrays.asList(new Integer[] {0, 1, 2, 6, 7, 10, 11, 12, 18, 19})));
        walls.put(21, new HashSet(Arrays.asList(new Integer[] {6, 7, 10, 11, 12, 18, 19})));
        walls.put(22, new HashSet(Arrays.asList(new Integer[] {10, 11, 12, 18, 19})));
        walls.put(23, new HashSet(Arrays.asList(new Integer[] {18, 19})));
        walls.put(24, new HashSet(Arrays.asList(new Integer[] {})));

        this.generatePositions();
    }

    public void generatePositions() {
        do {
            startX = ThreadLocalRandom.current().nextInt(0, gridLength);
            startY = ThreadLocalRandom.current().nextInt(0, gridLength);
        } while (walls.get(startX).contains(startY));

        do {
            exitX = ThreadLocalRandom.current().nextInt(0, gridLength);
            exitY = ThreadLocalRandom.current().nextInt(0, gridLength);
        } while (walls.get(exitX).contains(exitY));

        System.out.println("Start: (" + startX + ", " + startY +")");
        System.out.println("End: (" + exitX + ", " + exitY +")");
    }

    public void setPositions(int startX, int startY, int exitX, int exitY) {
        this.startX = startX;
        this.startY = startY;
        this.exitX = exitX;
        this.exitY = exitY;
        System.out.println("Start: (" + startX + ", " + startY +")");
        System.out.println("End: (" + exitX + ", " + exitY +")");
    }

    public void useBFS() {
        Queue<Node> queue = new LinkedList<>();
        HashSet<Integer> hashset = new HashSet<>();

        Node node = new Node(startX, startY);
        node.distance = 0;
        queue.add(node);

        while(!queue.isEmpty()) {
            Node currentNode = queue.remove();
            if (currentNode.x == exitX && currentNode.y == exitY) {
                System.out.println("Nodes explored: " + hashset.size());
                currentNode.print();
                break;
            }

            int currentX;
            int currentY;

            // currentNode North
            currentX = currentNode.x;
            currentY = currentNode.y - 1;
            if (currentY > -1 && currentY < gridLength && !walls.get(currentX).contains(currentY) && !hashset.contains((currentX) * gridLength + (currentY + 1))) {
                Node childNode = new Node(currentX, currentY);
                childNode.distance = currentNode.distance + 1;
                childNode.parent = currentNode;
                queue.add(childNode);
                hashset.add((currentX) * gridLength + (currentY + 1));
            }

            // currentNode South
            currentX = currentNode.x;
            currentY = currentNode.y + 1;
            if (currentY > -1 && currentY < gridLength && !walls.get(currentX).contains(currentY) && !hashset.contains((currentX) * gridLength + (currentY + 1))) {
                Node childNode = new Node(currentX, currentY);
                childNode.distance = currentNode.distance + 1;
                childNode.parent = currentNode;
                queue.add(childNode);
                hashset.add((currentX) * gridLength + (currentY + 1));
            }

            // currentNode East
            currentX = currentNode.x + 1;
            currentY = currentNode.y;
            if (currentX > -1 && currentX < gridLength && !walls.get(currentX).contains(currentY) && !hashset.contains((currentX) * gridLength + (currentY + 1))) {
                Node childNode = new Node(currentX, currentY);
                childNode.distance = currentNode.distance + 1;
                childNode.parent = currentNode;
                queue.add(childNode);
                hashset.add((currentX) * gridLength + (currentY + 1));
            }

            // CurrentNode West
            currentX = currentNode.x - 1;
            currentY = currentNode.y;
            if (currentX > -1 && currentX < gridLength && !walls.get(currentX).contains(currentY) && !hashset.contains((currentX) * gridLength + (currentY + 1))) {
                Node childNode = new Node(currentX, currentY);
                childNode.distance = currentNode.distance + 1;
                childNode.parent = currentNode;
                queue.add(childNode);
                hashset.add((currentX) * gridLength + (currentY + 1));
            }
        }
    }

    public void useDFS() {
        Stack<Node> stack = new Stack<>();
        HashSet<Integer> hashset = new HashSet<>();

        Node node = new Node(startX, startY);
        node.distance = 0;
        stack.add(node);

        while(!stack.isEmpty()) {
            Node currentNode = stack.pop();
            if (currentNode.x == exitX && currentNode.y == exitY) {
                System.out.println("Nodes explored: " + hashset.size());
                currentNode.print();
                break;
            }

            int currentX;
            int currentY;

            // currentNode North
            currentX = currentNode.x;
            currentY = currentNode.y - 1;
            if (currentY > -1 && currentY < gridLength && !walls.get(currentX).contains(currentY) && !hashset.contains((currentX) * gridLength + (currentY + 1))) {
                Node childNode = new Node(currentX, currentY);
                childNode.distance = currentNode.distance + 1;
                childNode.parent = currentNode;
                stack.push(childNode);
                hashset.add((currentX) * gridLength + (currentY + 1));
            }

            // currentNode South
            currentX = currentNode.x;
            currentY = currentNode.y + 1;
            if (currentY > -1 && currentY < gridLength && !walls.get(currentX).contains(currentY) && !hashset.contains((currentX) * gridLength + (currentY + 1))) {
                Node childNode = new Node(currentX, currentY);
                childNode.distance = currentNode.distance + 1;
                childNode.parent = currentNode;
                stack.push(childNode);
                hashset.add((currentX) * gridLength + (currentY + 1));
            }

            // currentNode East
            currentX = currentNode.x + 1;
            currentY = currentNode.y;
            if (currentX > -1 && currentX < gridLength && !walls.get(currentX).contains(currentY) && !hashset.contains((currentX) * gridLength + (currentY + 1))) {
                Node childNode = new Node(currentX, currentY);
                childNode.distance = currentNode.distance + 1;
                childNode.parent = currentNode;
                stack.push(childNode);
                hashset.add((currentX) * gridLength + (currentY + 1));
            }

            // CurrentNode West
            currentX = currentNode.x - 1;
            currentY = currentNode.y;
            if (currentX > -1 && currentX < gridLength && !walls.get(currentX).contains(currentY) && !hashset.contains((currentX) * gridLength + (currentY + 1))) {
                Node childNode = new Node(currentX, currentY);
                childNode.distance = currentNode.distance + 1;
                childNode.parent = currentNode;
                stack.push(childNode);
                hashset.add((currentX) * gridLength + (currentY + 1));
            }
        }
    }

    public void useAStar() {
        List<Node> queue = new LinkedList<>();
        HashSet<Integer> hashset = new HashSet<>();

        Node node = new Node(startX, startY);
        node.distance = 0;
        queue.add(node);

        while(!queue.isEmpty()) {
            List<Node> list = new ArrayList<>();
            Node currentNode = queue.remove(0);
            if (currentNode.x == exitX && currentNode.y == exitY) {
                System.out.println("Nodes explored: " + hashset.size());
                currentNode.print();
                break;
            }

            int currentX;
            int currentY;

            // currentNode North
            currentX = currentNode.x;
            currentY = currentNode.y - 1;
            if (currentY > -1 && currentY < gridLength && !walls.get(currentX).contains(currentY) && !hashset.contains((currentX) * gridLength + (currentY + 1))) {
                Node childNode = new Node(currentX, currentY);
                childNode.distance = currentNode.distance + 1;
                childNode.realDistance = Math.sqrt(Math.pow(exitX - currentX, 2) + Math.pow((exitY - currentY),2));
                childNode.parent = currentNode;
                list.add(childNode);
                hashset.add((currentX) * gridLength + (currentY + 1));
            }

            // currentNode South
            currentX = currentNode.x;
            currentY = currentNode.y + 1;
            if (currentY > -1 && currentY < gridLength && !walls.get(currentX).contains(currentY) && !hashset.contains((currentX) * gridLength + (currentY + 1))) {
                Node childNode = new Node(currentX, currentY);
                childNode.distance = currentNode.distance + 1;
                childNode.realDistance = Math.sqrt(Math.pow(exitX - currentX, 2) + Math.pow((exitY - currentY),2));
                childNode.parent = currentNode;
                list.add(childNode);
                hashset.add((currentX) * gridLength + (currentY + 1));
            }

            // currentNode East
            currentX = currentNode.x + 1;
            currentY = currentNode.y;
            if (currentX > -1 && currentX < gridLength && !walls.get(currentX).contains(currentY) && !hashset.contains((currentX) * gridLength + (currentY + 1))) {
                Node childNode = new Node(currentX, currentY);
                childNode.distance = currentNode.distance + 1;
                childNode.realDistance = Math.sqrt(Math.pow(exitX - currentX, 2) + Math.pow((exitY - currentY),2));
                childNode.parent = currentNode;
                list.add(childNode);
                hashset.add((currentX) * gridLength + (currentY + 1));
            }

            // CurrentNode West
            currentX = currentNode.x - 1;
            currentY = currentNode.y;
            if (currentX > -1 && currentX < gridLength && !walls.get(currentX).contains(currentY) && !hashset.contains((currentX) * gridLength + (currentY + 1))) {
                Node childNode = new Node(currentX, currentY);
                childNode.distance = currentNode.distance + 1;
                childNode.realDistance = Math.sqrt(Math.pow(exitX - currentX, 2) + Math.pow((exitY - currentY),2));
                childNode.parent = currentNode;
                list.add(childNode);
                hashset.add((currentX) * gridLength + (currentY + 1));
            }

            queue.addAll(list);

            Collections.sort(queue, new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    if (o1.realDistance == o2.realDistance)
                        return 0;
                    return o1.realDistance < o2.realDistance ? -1 : 1;
                }
            });
        }
    }
}
