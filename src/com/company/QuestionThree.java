package com.company;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ingenious on 2016-07-06.
 */
public class QuestionThree {
    List<DeptNode> list;
//    PriorityQueue<Pair> candidateList;
    List<Pair> candidateList;
    int[][] tabuList;
    int[][] flow;
    int cost;
    int numIterations = 0;

    public QuestionThree() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new DeptNode((i / 5), i % 5, i + 1));
//            System.out.println((i / 5) + ", " + i % 5 + ", " + (i + 1));
        }

        // Shuffle
        int listSize = list.size();
        for (int i = 0; i < listSize - 1; i++) {
            int j = ThreadLocalRandom.current().nextInt(i, listSize);
            int tempDept = list.get(i).deptNo;
            int tempDept2 = list.get(j).deptNo;
            list.get(j).deptNo = tempDept;
            list.get(i).deptNo = tempDept2;
            System.out.println(tempDept2);
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

            br.close();
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
        while (numIterations < 100) {
            candidateList = new ArrayList<Pair>();

            cost = heuristicFunction(list);

            for (int i = 0; i < 20; i++) {
                for (int j = i + 1; j < 20; j++) {
                    int tempDept = list.get(i).deptNo;
                    int tempDept2 = list.get(j).deptNo;

                    list.get(i).deptNo = tempDept2;
                    list.get(j).deptNo = tempDept;
                    Pair pair = new Pair(i, j, heuristicFunction(list) - cost);
                    candidateList.add(pair);

                    list.get(i).deptNo = tempDept;
                    list.get(j).deptNo = tempDept2;
                }
            }

            Collections.sort(candidateList, new Comparator<Pair>() {
                @Override
                public int compare(Pair o1, Pair o2) {
                    if (o1.cost == o2.cost)
                        return 0;
                    return o1.cost < o2.cost ? -1 : 1;
                }
            });

            Pair pair = candidateList.remove(0);
            while (tabuList[list.get(pair.i).deptNo - 1][list.get(pair.j).deptNo - 1] != 0) {
                pair = candidateList.remove(0);
            }

            // Swap
            if (pair.cost <= 0) {
                int tempDept = list.get(pair.i).deptNo;
                list.get(pair.i).deptNo = list.get(pair.j).deptNo;
                list.get(pair.j).deptNo = tempDept;
            }

            numIterations++;

            for (int[] array : tabuList) {
                for (int number : array) {
                    if (number > 0)
                        number--;
                }
            }
            tabuList[list.get(pair.i).deptNo - 1][list.get(pair.j).deptNo - 1] = 10;
        }

        System.out.println(heuristicFunction(list));
        System.out.println(numIterations);
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