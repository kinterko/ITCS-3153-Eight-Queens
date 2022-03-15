//Kory Kinter ITCS 3153 Assignment 1 Eight Queens//
import java.util.*;

public class EightQueens {
    public static int boardDimensions = 8;

  public static void noCollision(){};
  public static void createHeuristic(){};
  public static void boardPrint(){};

//Create main method to run program
  public static void main(String[] args){
    //create arraylists for board sizes needed
    ArrayList<ArrayList<Integer>> board = new ArrayList<ArrayList<Integer>>(); 
    ArrayList<ArrayList<Integer>> hBoard = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> tBoard = new ArrayList<ArrayList<Integer>>();

    // start each arraylist at 0 for board sizes
    for(int i = 0; i < boardDimensions; i++){

      board.add(new ArrayList<Integer>());
      hBoard.add(new ArrayList<Integer>());
      tBoard.add(new ArrayList<Integer>());

      for(int j = 0; j < boardDimensions; j++){
        board.get(i).add(0);
        hBoard.get(i).add(0);
        tBoard.get(i).add(0);
      }
    }

    //add queens to columns randomly
    for(int i = 0; i < boardDimensions; i++){
      board.get((int)(Math.random()*boardDimensions)).add(i,1);
    }


    int lowerNumStates = 0;
    int changeStatesCount = 0;
    int restarts = 0;
    int currH = 0;
    int lowH;
    int firstPosition[] = new int[2]; 
    boolean restartNeeded;

    while(!noCollision(board)){

      if(lowerNumStates == 0){

        board = new ArrayList<ArrayList<Integer>>(); 
        hBoard = new ArrayList<ArrayList<Integer>>();
        tBoard = new ArrayList<ArrayList<Integer>>();

        for(int i = 0; i < boardDimensions; i++){
          board.add(new ArrayList<Integer>());
          hBoard.add(new ArrayList<Integer>());
        tBoard.add(new ArrayList<Integer>());
          for(int j = 0; j < boardDimensions; j++){
            board.get(i).add(0);
            hBoard.get(i).add(0);
            tBoard.get(i).add(0);
          }
        }

        //adds Queen to a column
        for(int i = 0; i < boardDimensions; i++){
          board.get((int)(Math.random()*boardDimensions)).add(i,1);
        }

        currH = createHeuristic(board);

      }else{
        lowerNumStates = 0;
      }

      lowH = currH; //set low H to current H

      System.out.println("Current h: " + currH);
      System.out.println("Current State");
      boardPrint(board);

      //loop to check heuristics
      for(int x = 0; x < boardDimensions; x++){
        int queenPos = 0;
        //find queen's y position
        for(int y = 0; y < boardDimensions; y++){
          if(board.get(y).get(x) == 1){
            queenPos = y;
          }
        }
        //copy of the board
        for(int i = 0 ; i < boardDimensions; i++){
          for(int j = 0 ; j < boardDimensions; j++){
            int c = board.get(i).get(j);
            tBoard.get(i).set(j,c);
          }
        }
        //removes queen on the coard copy
        tBoard.get(queenPos).set(x, 0); 
        
        for(int y = 0; y < boardDimensions ;y++){
          tBoard.get(y).set(x, 1); 

          int tempH = createHeuristic(tBoard);
          
          hBoard.get(y).set(x,tempH);
          tBoard.get(y).set(x,0);

        }
      }

      restartNeeded = true;
      

      //checks for low H is on board
      for(int x =0; x<boardDimensions; x++){
        for(int y = 0; y< boardDimensions; y++){
          if(hBoard.get(y).get(x) < currH){
            //adds to lowerNumStates based on board
            lowerNumStates += 1;
          }
          if(hBoard.get(y).get(x)<lowH){
            //change low H to current H if it lower than the lowest on the board
            lowH = hBoard.get(y).get(x);
            firstPosition[0] = y;
            firstPosition[1] = x;
            currH = lowH;
            restartNeeded = false;
          }
        }
      }

      changeStatesCount += 1;

      if(restartNeeded){
        //if restart is needed; restart
        restarts +=1;
        System.out.println("Neighbors found with lower h:" + lowerNumStates);
        System.out.println("RESTART\n");
      }else{
        //if it is a lower hueristic, move queen on the board
        System.out.println("Neighbors found with lower h:" + lowerNumStates);
        System.out.println("Setting new current state\n");

        //queen moves to row with low H
        for(int y = 0; y < boardDimensions;y++){
          board.get(y).set(firstPosition[1],0);
          if(firstPosition[0] == y ){
            board.get(y).set(firstPosition[1],1);
          }
        }

      }

    }
    //print out needed information
    System.out.println("Current State");
    boardPrint(board);

    System.out.println("Solution Found!");
    System.out.println("State changes: "+ changeStatesCount);
    System.out.println("Restarts: "+ restarts);
  }

  public static void boardPrint(ArrayList<ArrayList<Integer>> board){
    //Prints state of Board
    for(int y = 0; y < boardDimensions; y++){
      for(int x = 0; x < boardDimensions; x++){
        System.out.print(board.get(y).get(x).toString());
        if(x != boardDimensions - 1){
          System.out.print(",");
        }else{
          System.out.println("");
        }
      }
    }
  }

  public static int createHeuristic(ArrayList<ArrayList<Integer>> board){
    //Method to return a Heuristic with count of collisions on board 

    //loop to find queens on board
    int collisionCount = 0;
    for(int x = 0; x < boardDimensions ; x++){
      for(int y = 0; y < boardDimensions; y++){
        if(board.get(y).get(x) == 1){

          //loop column of queen
          for(int i = x+1;i< boardDimensions;i++){
            if(board.get(y).get(i) == 1){
              collisionCount += 1;
            }
          }
          //loop row of queen
          for(int i = y+1;i< boardDimensions;i++){
            if(board.get(i).get(x) == 1){
              collisionCount += 1;
            }
          }

        //Check diagonal from top left to bottom right for queen
          int tH = x;
          int tY = y;
          while(true){
            tH +=1;
            tY +=1;
            if(tH > boardDimensions - 1 || tY > boardDimensions - 1 ){
              break;
            }
            if(board.get(tY).get(tH) == 1){
              collisionCount += 1;
            }
          }

        //Check diagonal from top right to bottom left for queen
          tH = x;
          tY = y;

          while(true){
            tH += 1;
            tY -= 1;

            if(tY < 0 || tH > boardDimensions - 1 ){
              break;
            }
            if(board.get(tY).get(tH) == 1){
              collisionCount += 1;
            }
          }
        }
      }
    }
    return collisionCount;
  }

  public static Boolean noCollision(ArrayList<ArrayList<Integer>> board){
    //Check to see if collision is true or false
    for(int y = 0; y < boardDimensions; y++){
      for(int x = 0; x < boardDimensions; x++){
        if(board.get(y).get(x) == 1){

          //loop through columns
          for(int i = 0; i < boardDimensions; i++){
            if(x != i ){
              if(board.get(y).get(i) == 1){
                return false;
              }
            }
          }

          //loop through rows
          for(int i = 0; i < boardDimensions;i++){
            if(y != i && board.get(i).get(x) == 1){
              return false;
            }
          }

          //Check diagonal from top left to bottom right
          int tH = x;
          int tY = y;

          if(tH > tY){
            tH -= tY;
            tY = 0;
          }
          else{
            tY -= tH;
            tH = 0;
          }
          while(true){
            if(board.get(tY).get(tH) == 1){
              if(tY != y && tH != x){
                return false;
                }
              }
            tH += 1;
            tY += 1;
            if(tH < 0 || tH > boardDimensions - 1 || tY < 0  || tY > boardDimensions - 1){
              break;
            }
          }

          //Check diagonal from top right to bottom left
          tH = x;
          tY = y;

          if(tH + tY <= boardDimensions){
            tH += tY;
            tY = 0;
          }else{
            tY -= boardDimensions - 1 - tH;
            tH = boardDimensions - 1;
          }
          while(true){
            if(tH < 0 || tH > boardDimensions - 1 || tY < 0  || tY > boardDimensions - 1 ){
              break;
            }
            if(board.get(tY).get(tH) == 1){
              if(tY != y && tH != x){
                return false;
              }
            }
            tH -= 1;
            tY += 1;
          }
        }
      }
    }
    return true;
  }
}
