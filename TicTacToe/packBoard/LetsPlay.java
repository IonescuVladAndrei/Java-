package packBoard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import packScoreboard.PlayerFactory;

public class LetsPlay {
    static private String checkNameIsValid() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean validCommand = false;
        String inputValue = "-1";
        while (!validCommand) {
            validCommand = true;
            try {
                inputValue = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error: couldn't read your name");
                System.out.println(e);
                System.exit(-1);
            }
            if (inputValue.contains(" ")) {
                System.out.println("No white space please. Try again");
                validCommand = false;
            }
        }
        return inputValue;
    }

    public static void main(String[] args) {
        PlayerFactory scoreB = new PlayerFactory();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        scoreB.printScoreBoard();
        Board tst = new Board();
        String inputValue = "-1", tempString;
        String[] playerNames = { "", "" };
        int[] playerScores = { 0, 0 };
        int turn = 1, boardPlace, tempInt = -1, tempInt2; // X turn
        char inputValueC = '0';
        boolean validCommand = false;
        System.out.println("\nHello there! Do you want to play agaisnt AI or a friend? (a/f)");
        try {
            inputValue = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error: couldn't read your command!");
            System.out.println(e);
            System.exit(-1);
        }
        while (!(!inputValue.equals("a") ^ !inputValue.equals("f"))) {
            System.out.println("Invalid command. Try again! (a/f)");
            try {
                inputValue = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error: couldn't read your command!");
                System.out.println(e);
                System.exit(-1);
            }
        }
        if (inputValue.equals("f")) {
            System.out.println("\nPlayer 1 please type your name (no white space)");
            playerNames[0] = LetsPlay.checkNameIsValid();
            System.out.println("\nPlayer 2 please type your name (no white space)");
            playerNames[1] = LetsPlay.checkNameIsValid();
            System.out.println("Hello there " + playerNames[0] + " and " + playerNames[1]);
            while (!inputValue.equals("y")) {
                while (tempInt == -1) {
                    tst.boardPositions();
                    tst.boardGamePos();
                    if (turn == 1)
                        System.out.println("It's " + playerNames[0] + " (X)'s turn!");
                    else
                        System.out.println("It's " + playerNames[1] + " (O)'s turn!");
                    while (!validCommand) {
                        validCommand = true;
                        try {
                            inputValue = reader.readLine();
                        } catch (IOException e) {
                            System.out.println("Error: couldn't read the next move");
                            System.out.println(e);
                            System.exit(-1);
                        }
                        if (inputValue.length() > 1) {
                            validCommand = false;
                            System.out.println("Value's length is too big! (only numbers from 1 to 9 are accepted). Try again");
                        } else {
                            inputValueC = inputValue.charAt(0);
                            if ((int) inputValueC < 49 || (int) inputValueC > 57) {
                                validCommand = false;
                                System.out.println("Value is not a number between 1 and 9! Try again");
                            } else {
                                boardPlace = tst.boardPlace(((int) inputValueC) - 48, turn);
                                if (boardPlace == 0) {
                                    validCommand = false;
                                    System.out.println("That spot is not available! Try again");
                                }
                            }
                        }
                    }
                    validCommand = false;
                    if (turn == 1)
                        turn = 0;
                    else
                        turn = 1;
                    tempInt = tst.checkWin();
                }
                tst.boardGamePos();

                if (tempInt == 0) {
                    System.out.println(playerNames[1] + " has won, congrats!");
                    playerScores[1] += tst.getScore();
                } else if (tempInt == 1) {
                    playerScores[0] += tst.getScore();
                    System.out.println(playerNames[0] + " has won, congrats! Time to switch turns");
                    tempInt2 = playerScores[0];
                    playerScores[0] = playerScores[1];
                    playerScores[1] = tempInt2;
                    tempString = playerNames[0];
                    playerNames[0] = playerNames[1];
                    playerNames[1] = tempString;
                }
                System.out.println("\nShall we stop? (y/n)");
                tst = new Board();
                tempInt = -1;
                turn = 1;
                try {
                    inputValue = reader.readLine();
                } catch (IOException e) {
                    System.out.println("Error: couldn't read the answer");
                    System.out.println(e);
                    System.exit(-1);
                }
            }
            System.out.println("\n" + playerNames[0] + " has accumulated " + playerScores[0] + " points");
            System.out.println("\n" + playerNames[1] + " has accumulated " + playerScores[1] + " points");
            scoreB.addPlayerToScoreBoard(playerNames[0], playerScores[0]);
            scoreB.addPlayerToScoreBoard(playerNames[1], playerScores[1]);
            scoreB.addPlayerWithOppToScoreBoard(playerNames[0], playerNames[1], playerScores[0]);
            scoreB.addPlayerWithOppToScoreBoard(playerNames[1], playerNames[0], playerScores[1]);
            scoreB.printScoreBoard();
            scoreB.saveScoreBoard();
        } else {
            System.out.println("\nPlease type your name (no white space)");
            playerNames[0] = LetsPlay.checkNameIsValid();
            playerNames[1] = "AI";
            Ai ai = new Ai();
            int difficulty;
            System.out.println("Choose the AI's difficulty. 1 = easy(picks random positions) 2 = not so easy (it's not gonna be a draw in best case scenario)");
            try {
                inputValue = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error: couldn't read your command!");
                System.out.println(e);
                System.exit(-1);
            }
            while (!(!inputValue.equals("1") ^ !inputValue.equals("2"))) {
                System.out.println("Invalid command. Try again! (1/2)");
                try {
                    inputValue = reader.readLine();
                } catch (IOException e) {
                    System.out.println("Error: couldn't read your command!");
                    System.out.println(e);
                    System.exit(-1);
                }
            }
            difficulty = Integer.parseInt(inputValue);
            while (!inputValue.equals("y")) {
                while (tempInt == -1) {
                    tst.boardPositions();
                    tst.boardGamePos();
                    if (turn == 1)
                        System.out.println("It's " + playerNames[0] + " (X)'s turn!");
                    else
                        System.out.println("It's " + playerNames[1] + " (O)'s turn!");
                    if (turn == 1 && !playerNames[0].equals("AI") || turn == 0 && !playerNames[1].equals("AI")) {
                        while (!validCommand) {
                            validCommand = true;
                            try {
                                inputValue = reader.readLine();
                            } catch (IOException e) {
                                System.out.println("Error: couldn't read the next move");
                                System.out.println(e);
                                System.exit(-1);
                            }
                            if (inputValue.length() > 1) {
                                validCommand = false;
                                System.out.println("Value's length is too big! (only numbers from 1 to 9 are accepted). Try again");
                            } else {
                                inputValueC = inputValue.charAt(0);
                                if ((int) inputValueC < 49 || (int) inputValueC > 57) {
                                    validCommand = false;
                                    System.out.println("Value is not a number between 1 and 9! Try again");
                                } else {

                                    boardPlace = tst.boardPlace(((int) inputValueC) - 48, turn);
                                    if (boardPlace == 0) {
                                        validCommand = false;
                                        System.out.println("That spot is not available! Try again");
                                    }
                                }
                            }
                        }
                        validCommand = false;
                        if (turn == 1)
                            turn = 0;
                        else
                            turn = 1;
                        tempInt = tst.checkWin();
                    } else {
                        if(difficulty == 1){
                            boardPlace = ai.easyMove(tst.getBoard(), turn);
                            tst.boardPlace(boardPlace, turn);
                        }
                        else{
                            boardPlace = ai.hardMove(tst.getBoard(), turn);
                            tst.boardPlace(boardPlace, turn);
                        }
                        
                        if (turn == 1)
                            turn = 0;
                        else
                            turn = 1;
                        tempInt = tst.checkWin();
                    }
                }
                tst.boardGamePos();

                if (tempInt == 0) {
                    if(playerNames[1].contains("AI"))
                        System.out.println(playerNames[1] + " has won!");
                    else
                        System.out.println(playerNames[1] + " has won, congrats!");
                    playerScores[1] += tst.getScore();
                } else if (tempInt == 1) {
                    playerScores[0] += tst.getScore();
                    if(playerNames[0].contains("AI"))
                        System.out.println(playerNames[0] + " has won!  Time to switch turns");
                    else
                        System.out.println(playerNames[0] + " has won, congrats!  Time to switch turns");
                    tempInt2 = playerScores[0];
                    playerScores[0] = playerScores[1];
                    playerScores[1] = tempInt2;
                    tempString = playerNames[0];
                    playerNames[0] = playerNames[1];
                    playerNames[1] = tempString;
                }
                System.out.println("\nShall we stop? (y/n)");
                tst = new Board();
                tempInt = -1;
                turn = 1;
                try {
                    inputValue = reader.readLine();
                } catch (IOException e) {
                    System.out.println("Error: couldn't read the answer");
                    System.out.println(e);
                    System.exit(-1);
                }
            }
            if(!playerNames[0].contains("AI")){
                System.out.println("\n" + playerNames[0] + " has accumulated " + playerScores[0] + " points");
                scoreB.addPlayerToScoreBoard(playerNames[0], playerScores[0]);
            }
            else{
                System.out.println("\n" + playerNames[1] + " has accumulated " + playerScores[1] + " points");
                scoreB.addPlayerToScoreBoard(playerNames[1], playerScores[1]);
            
            }
            scoreB.printScoreBoard();
            scoreB.saveScoreBoard();
        }
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("Error: couldn't close BufferedReader");
            System.out.println(e);
            System.exit(-1);
        }

    }
}