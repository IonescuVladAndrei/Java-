# Tic Tac Toe with Flyweight Pattern for saving scores

This is my version of the very popular game Tic Tac Toe. It has 2 options, one for player vs player and another one for playing against ai (that is actually defeatable and not unfun to play against).
There are 2 main packs, packBoard and packScoreboard.

## packBoard
### Board
Board is a class with 2 private fields, board and the number of positions occupied that are used each game to keep track of the moves (this includes checking if the game has been won, if a position is empty as well as printing the board in terminal). 
### Ai
Ai extends Board and is used for choosing the next move either randomly or based on the positions that are taken/empty.
### LetsPlay
The main class that is responsible for tying everything together.
## packScoreboard
This is the interesting part of my little project that I chose to code especially because I get to learn a new Java pattern, which is the Flyweight one. I didn't want the scores to be lost whenever I close the program so they get saved in data.txt in 2 formats: first one is player name followed by their total score and the second one is player, opponent and total score against that certain opponent. If the file isn't found (because it got deleted) it gets created in PlayerFactory constructor, otherwise the available data gets read line by line and based on the number of spaces (either 1 or 2), we create a Player or PlayerWithOpponent object that is put in the playerMap hashmap. When the program is shut down from terminal, the accumulated points get saved using addPlayerToScoreBoard or addPlayerWithOppToScoreBoard. These 2 methods check the hashmap and if there are entries with identical ids, the scores get updated. printScoreBoard uses 2 linked lists and packComparator for the scores to be shown descendingly in the Terminal.
