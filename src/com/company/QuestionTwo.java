package com.company;

/**
 * Created by Administrator on 7/6/2016.
 */
public class QuestionTwo {
    Position[][] board = new Position[4][4];

    public QuestionTwo() {
        board[0][0] = new Position(10, "black", 0, 0);
        board[3][3] = new Position(10, "white", 3, 3);

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(board[i][j].pieceCount != 10){
                    board[i][j] = new Position(0, "blank", i, j);
                }
            }
        }

    }

    public void minimax(){
        Position[][] newBoard = new Position[board.length][];

        for(int i = 0; i < board.length; i++){
            newBoard[i] = board[i].clone();
        }


    }

    public int minimaxHeuristic(Position pos){
        //heuristic: # of reachable tiles of player black - # of reachable tiles of player white
        int numMoves = 0;
        int curX = pos.x;
        int curY = pos.y;

        //check north
        while(curY >= 1){
            curY--;
            if (board[curX][curY].colour == pos.colour || board[curX][curY].colour == "blank") {
                numMoves++;
            } else {
                break;
            }
        }

        //check south
        curX = pos.x;
        curY = pos.y;

        while(curY <= 2){
            curY++;
            if (board[curX][curY].colour == pos.colour || board[curX][curY].colour == "blank") {
                numMoves++;
            } else {
                break;
            }
        }

        //check east
        curX = pos.x;
        curY = pos.y;

        while(curX <= 2){
            curX++;
            if (board[curX][curY].colour == pos.colour || board[curX][curY].colour == "blank") {
                numMoves++;
            } else {
                break;
            }
        }

        //check west
        curX = pos.x;
        curY = pos.y;

        while(curX >= 1){
            curX--;
            if (board[curX][curY].colour == pos.colour || board[curX][curY].colour == "blank") {
                numMoves++;
            } else {
                break;
            }
        }

        //check North East
        curX = pos.x;
        curY = pos.y;

        while(curX <= 2 && curY >= 1){
            curX++;
            curY--;
            if (board[curX][curY].colour == pos.colour || board[curX][curY].colour == "blank") {
                numMoves++;
            } else {
                break;
            }
        }

        //check North West
        curX = pos.x;
        curY = pos.y;

        while(curX >= 1 && curY >= 1){
            curX--;
            curY--;
            if (board[curX][curY].colour == pos.colour || board[curX][curY].colour == "blank") {
                numMoves++;
            } else {
                break;
            }
        }

        //check South East
        curX = pos.x;
        curY = pos.y;

        while(curX <= 2 && curY <= 2){
            curX++;
            curY++;
            if (board[curX][curY].colour == pos.colour || board[curX][curY].colour == "blank") {
                numMoves++;
            } else {
                break;
            }
        }

        //check South West
        curX = pos.x;
        curY = pos.y;

        while(curX >= 1 && curY <= 2){
            curX--;
            curY++;
            if (board[curX][curY].colour == pos.colour || board[curX][curY].colour == "blank") {
                numMoves++;
            } else {
                break;
            }
        }

        return numMoves;
    }
}

class Position{
    int pieceCount;
    String colour;
    int x;
    int y;

    public Position(int pieceCount, String colour, int x, int y){
        this.pieceCount = pieceCount;
        this.colour = colour;
        this.x = x;
        this.y = y;
    }
}
