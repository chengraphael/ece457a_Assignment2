package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Administrator on 7/7/2016.
 */
public class QuestionFive {
    HashMap<Integer, Node> coordinates;
    HashMap<Integer, ArrayList<Integer>> routes;
    double alpha = 0.85;
    double temperature = 1000;
    int iterations = 0;
    int numCars = 5;
    int bestCost = 10000;

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

//        ArrayList<Integer> route1 = new ArrayList<>(Arrays.asList(new Integer[]{2, 3, 4, 5, 6, 7}));
//
//        ArrayList<Integer> route2 = new ArrayList<>(Arrays.asList(new Integer[]{8, 9, 10, 11, 12, 13}));
//
//        ArrayList<Integer> route3 = new ArrayList<>(Arrays.asList(new Integer[]{14, 15, 16, 17, 18, 19}));
//
//        ArrayList<Integer> route4 = new ArrayList<>(Arrays.asList(new Integer[]{20, 21, 22, 23, 24, 25}));
//
//        ArrayList<Integer> route5 = new ArrayList<>(Arrays.asList(new Integer[]{26, 27, 28, 29, 30, 31, 32}));

        ArrayList<Integer> route1 = new ArrayList<>(Arrays.asList(new Integer[]{22, 32, 21 , 18, 14, 7, 27}));

        ArrayList<Integer> route2 = new ArrayList<>(Arrays.asList(new Integer[]{13, 2, 16, 30}));

        ArrayList<Integer> route3 = new ArrayList<>(Arrays.asList(new Integer[]{28,25}));

        ArrayList<Integer> route4 = new ArrayList<>(Arrays.asList(new Integer[]{30, 19, 9, 10, 23, 16, 11, 26, 6, 21}));

        ArrayList<Integer> route5 = new ArrayList<>(Arrays.asList(new Integer[]{15, 29, 12, 5, 24, 4, 3, 6}));


        routes.put(1, route1);
        routes.put(2, route2);
        routes.put(3, route3);
        routes.put(4, route4);
        routes.put(5, route5);

//        System.out.println(calculateCost());

    }

    public int simulatedAnnealing(){
        Random rand = new Random();

        while(temperature > 1){
            int currentCost = calculateCost();

            int randomRoute1 = rand.nextInt((5 - 1) + 1) + 1;
            int randomRouteSize = routes.get(randomRoute1).size() - 1;
            int randomLocation1 = rand.nextInt((randomRouteSize - 0) + 1);

            int randomRoute2 = rand.nextInt((5 - 1) + 1) + 1;
            int randomRouteSize2 = routes.get(randomRoute2).size() - 1;
            int randomLocation2 = rand.nextInt((randomRouteSize2 - 0) + 1);

            int tempLocation = routes.get(randomRoute1).get(randomLocation1);
            routes.get(randomRoute1).set(randomLocation1, routes.get(randomRoute2).get(randomLocation2));
            routes.get(randomRoute2).set(randomLocation2, tempLocation);

            if(acceptance(calculateCost() - currentCost)){
                iterations++;
                if(calculateCost() < bestCost){
                    bestCost = calculateCost();
                }
            } else {
                tempLocation = routes.get(randomRoute1).get(randomLocation1);
                routes.get(randomRoute1).set(randomLocation1, routes.get(randomRoute2).get(randomLocation2));
                routes.get(randomRoute2).set(randomLocation2, tempLocation);
            }

            if(iterations == 100){
                iterations = 0;
                temperature = temperature * alpha;
            }
        }
        return calculateCost();
    }

    public boolean acceptance(int deltaCost){
        double acceptance = Math.exp(-deltaCost/temperature);
        double random = Math.random();

        return random <= acceptance;
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
