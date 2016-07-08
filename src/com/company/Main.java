package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        System.out.println("Hello World!");
//        QuestionOne q1 = new QuestionOne(25);
//        q1.setPositions(0, 0, 24, 24);
//        System.out.println("Using breadth-first search...");
//        q1.useBFS();
//        System.out.println("Using depth-first search...");
//        q1.useDFS();
//        System.out.println("Using A* search...");
//        q1.useAStar();

        QuestionThree q3 = new QuestionThree();
        q3.tabuSearch();

//        int cost = 0;
//        do {
//            QuestionFive q5 = new QuestionFive();
//            cost = q5.simulatedAnnealing();
//            System.out.println(cost);
//        } while (cost > 1000);
//        System.out.println(cost);

    }

}
