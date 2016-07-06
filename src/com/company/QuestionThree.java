package com.company;

import java.io.*;
import java.util.*;

/**
 * Created by Ingenious on 2016-07-06.
 */
public class QuestionThree {
    List<DeptNode> list;
    PriorityQueue<Pair> candidateList;
    int[][] tabuList;
    int[][] flow;
    int cost;

    public QuestionThree() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new DeptNode((i / 5), i % 5, i + 1));
//            System.out.println((i / 5) + ", " + i % 5 + ", " + (i + 1));
        }

        String csvFile = "Flow.csv";
        BufferedReader br = null;
        String line;
        String csbSplitBy = ",";

        flow = new int[20][20];
        int rowCount = 0;

        try {
            br = new BufferedReader(new FileReader(csvFile));

            while ((line = br.readLine()) != null) {
                String[] flows = line.split(csbSplitBy);
                for (int i = 0; i < 20; i++) {
                    flow[rowCount][i] = Integer.parseInt(flows[i]);
                }

                rowCount++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(Arrays.deepToString(flow));
//        System.out.println(heuristicFunction(list));
        cost = heuristicFunction(list);
    }

    public void tabuSearch() {
        tabuList = new int[20][20];
        while (cost > 1500) {
            candidateList = new PriorityQueue<>(5, new Comparator<Pair>() {
                @Override
                public int compare(Pair o1, Pair o2) {
                    if (o1.cost == o2.cost)
                        return 0;
                    return o1.cost > o2.cost ? -1 : 1;
                }
            });

            for (int i = 0; i < 20; i++) {
                for (int j = i + 1; j < 20; j++) {
                    List<DeptNode> tempList = new ArrayList<DeptNode>(list);
                    tempList.set(i, list.get(j));
                    tempList.set(j, list.get(i));
                    Pair pair = new Pair(i, j, heuristicFunction(tempList));
                    candidateList.add(pair);
                }
            }

            Pair pair = candidateList.remove();
            while (tabuList[list.get(pair.i).deptNo - 1][list.get(pair.j).deptNo - 1] != 0) {
                pair = candidateList.remove();
            }

            // Swap
            DeptNode tempNode = list.get(pair.i);
            list.set(pair.i, list.get(pair.j));
            list.set(pair.j, tempNode);
            cost = pair.cost;

            for (int[] array : tabuList) {
                for (int number : array) {
                    if (number > 0)
                        number--;
                }
            }
            tabuList[list.get(pair.i).deptNo - 1][list.get(pair.j).deptNo - 1] = 5;
        }

        System.out.println(cost);
    }

    private int heuristicFunction(List<DeptNode> tempList) {
        int cost = 0;

        for (int i = 0; i < 20; i++) {
            for (int j = i + 1; j < 20; j++) {
                DeptNode node = tempList.get(i);
                DeptNode node2 = tempList.get(j);

                cost = cost + (Math.abs(node2.x - node.x) + Math.abs(node2.y - node.y)) * flow[node.deptNo - 1][node2.deptNo - 1];
            }
        }

        return cost;
    }
}

class Pair {
    int i;
    int j;
    int cost;

    public Pair(int i, int j, int cost) {
        this.i = i;
        this.j = j;
        this.cost = cost;
    }
}