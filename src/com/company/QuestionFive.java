package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Administrator on 7/7/2016.
 */
public class QuestionFive {
    HashMap<Integer, Node> coordinates;
    HashMap<Integer, ArrayList<Integer>> routes;

    public QuestionFive(){
        String file = "A-n32-k5.vrp";
        BufferedReader br = null;
        String line;
        String delim = " ";
        coordinates = new HashMap<>();
        routes = new HashMap<>();

        try {
            br = new BufferedReader(new FileReader(file));

            while (!(line = br.readLine()).equals("NODE_COORD_SECTION ")) {

            }

            while(!(line = br.readLine()).equals("DEMAND_SECTION ")){
                String[] info = line.trim().split(delim);
                int location = Integer.parseInt(info[0]);
                int x = Integer.parseInt(info[1]);
                int y = Integer.parseInt(info[2]);

                Node node = new Node(x, y);

                coordinates.put(location, node);
            }

            while(!(line = br.readLine()).equals("DEPOT_SECTION ")){
                String[] info = line.trim().split(delim);
                int location = Integer.parseInt(info[0]);
                int serviceTime = Integer.parseInt(info[1]);

                coordinates.get(location).serviceTime = serviceTime;
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Integer> route1 = new ArrayList<>(Arrays.asList(new Integer[]{2, 3, 4, 5, 6, 7}));
//        route1.add(2);
//        route1.add(3);
//        route1.add(4);
//        route1.add(5);
//        route1.add(6);
//        route1.add(7);

        ArrayList<Integer> route2 = new ArrayList<>(Arrays.asList(new Integer[]{8, 9, 10, 11, 12, 13}));
//        route2.add(8);
//        route2.add(9);
//        route2.add(10);
//        route2.add(11);
//        route2.add(12);
//        route2.add(13);

        ArrayList<Integer> route3 = new ArrayList<>(Arrays.asList(new Integer[]{14, 15, 16, 17, 18, 19}));
//        route3.add(14);
//        route3.add(15);
//        route3.add(16);
//        route3.add(17);
//        route3.add(18);
//        route3.add(19);

        ArrayList<Integer> route4 = new ArrayList<>(Arrays.asList(new Integer[]{20, 21, 22, 23, 24, 25}));
//        route4.add(20);
//        route4.add(21);
//        route4.add(22);
//        route4.add(23);
//        route4.add(24);
//        route4.add(25);

        ArrayList<Integer> route5 = new ArrayList<>(Arrays.asList(new Integer[]{26, 27, 28, 29, 30, 31, 32}));
//        route5.add(26);
//        route5.add(27);
//        route5.add(28);
//        route5.add(29);
//        route5.add(30);
//        route5.add(31);

        routes.put(1, route1);
        routes.put(2, route2);
        routes.put(3, route3);
        routes.put(4, route4);
        routes.put(5, route5);

        System.out.println(calculateCost());

    }

    public void simulatedAnnealing(){

    }

    public int calculateCost(){
        int cost = 0;

        for(int i : routes.keySet()){
            for(int j = 0; j < routes.get(i).size(); j++){
                if(j == 0){
                    int location = routes.get(i).get(j);
                    cost = cost + coordinates.get(location).serviceTime;

                    int xdist = Math.abs(coordinates.get(1).x - coordinates.get(location).x);
                    int ydist = Math.abs(coordinates.get(1).y - coordinates.get(location).y);
                    int realdist = (int) Math.round(Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist,2)));

                    cost = cost + realdist;
                } else if(j == routes.get(i).size() - 1){
                    int location = routes.get(i).get(j);
                    int previouslocation = routes.get(i).get(j - 1);
                    cost = cost + coordinates.get(location).serviceTime;

                    int xdist = Math.abs(coordinates.get(1).x - coordinates.get(location).x);
                    int ydist = Math.abs(coordinates.get(1).y - coordinates.get(location).y);
                    int realdist = (int) Math.round(Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist,2)));

                    cost = cost + realdist;

                    xdist = Math.abs(coordinates.get(previouslocation).x - coordinates.get(location).x);
                    ydist = Math.abs(coordinates.get(previouslocation).y - coordinates.get(location).y);
                    realdist = (int) Math.round(Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist,2)));

                    cost = cost + realdist;
                } else {
                    int location = routes.get(i).get(j);
                    int previouslocation = routes.get(i).get(j - 1);
                    cost = cost + coordinates.get(location).serviceTime;

                    int xdist = Math.abs(coordinates.get(previouslocation).x - coordinates.get(location).x);
                    int ydist = Math.abs(coordinates.get(previouslocation).y - coordinates.get(location).y);
                    int realdist = (int) Math.round(Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist,2)));

                    cost = cost + realdist;
                }
            }
        }

        return cost;
    }
}
