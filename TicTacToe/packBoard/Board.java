package packBoard;

public class Board {
    private int board[][];
    private int spacesOcup = 0;

    public void boardPositions() {
        System.out.println("  _____\n| " + 1 + " " + 2 + " " + 3 + " |\n" + "| " + 4 + " " + 5 + " " + 6 + " |\n" + "| " + 7 + " " + 8 + " " + 9 + " |\n  -----");
    }

    public void boardGamePos() {
        int i, j;
        System.out.println("  _______________\n");
        for (i = 0; i < 3; i++) {
            System.out.print("|  ");
            for (j = 0; j < 3; j++) {
                if (this.board[i][j] == 10)
                    System.out.print("     ");
                else if (this.board[i][j] == 0)
                    System.out.print("/\\   ");
                else
                    System.out.print("\\/   ");
            }
            System.out.print("|\n");
            System.out.print("|  ");
            for (j = 0; j < 3; j++) {
                if (this.board[i][j] == 10)
                    System.out.print("     ");
                else if (this.board[i][j] == 0)
                    System.out.print("\\/   ");
                else
                    System.out.print("/\\   ");
            }
            if (i != 2) {
                System.out.print("|\n|                 |\n|                 |\n");
            } else
                System.out.print("|\n");
        }
        System.out.println("  ---------------");
    }

    public int boardPlace(int position, int value) {
        int validity = 1;
        position--;
        if (position > 5) {
            position = position - 6;
            if (this.board[2][position] == 10)
                this.board[2][position] = value;
            else
                validity = 0;
        } else if (position > 2) {
            position = position - 3;
            if (this.board[1][position] == 10)
                this.board[1][position] = value;
            else
                validity = 0;
        } else {
            if (this.board[0][position] == 10)
                this.board[0][position] = value;
            else
                validity = 0;
        }
        if (validity == 1)
            this.spacesOcup++;
        return validity;
    }

    public int checkWin() {
        int i, sum;
        for (i = 0; i < 3; i++) {
            sum = board[i][0] + board[i][1] + board[i][2]; // line i
            if (sum == 0) {
                System.out.println("O has won, line: " + (i + 1));
                return 0;
            } else if (sum == 3) {
                System.out.println("X has won, line: " + (i + 1));
                return 1;
            }
            sum = board[0][i] + board[1][i] + board[2][i]; // column i
            if (sum == 0) {
                System.out.println("O has won, column: " + (i + 1));
                return 0;
            } else if (sum == 3) {
                System.out.println("X has won, column: " + (i + 1));
                return 1;
            }
        }
        sum = board[0][0] + board[1][1] + board[2][2]; // main diagonal
        if (sum == 0) {
            System.out.println("O has won, main diagonal");
            return 0;
        } else if (sum == 3) {
            System.out.println("X has won, main diagonal");
            return 1;
        }
        sum = board[0][2] + board[1][1] + board[2][0]; // secondary diagonal
        if (sum == 0) {
            System.out.println("O has won, secondary diagonal");
            return 0;
        } else if (sum == 3) {
            System.out.println("X has won, secondary diagonal");
            return 1;
        }
        if (this.spacesOcup == 9) {
            System.out.println("It's a tie!");
            return 10;
        }
        return -1;
    }

    public int getScore() {
        int i, j, sum = 10;
        for(i = 0;i<3;i++)
            for(j=0;j<3;j++){
                if(board[i][j]!=10)
                    sum--;
                else
                    sum = sum + 10;
            }
        return sum;
    }

    public int[][] getBoard(){
        return this.board;
    }

    public void setBoard(int[][] board){
        int i, j;
        for (i = 0; i < 3; i++)
            for (j = 0; j < 3; j++)
                this.board[i][j] = board[i][j];
    }

    public Board() {
        int i, j;
        this.board = new int[3][];
        for (i = 0; i < 3; i++)
            this.board[i] = new int[3];
        for (i = 0; i < 3; i++)
            for (j = 0; j < 3; j++)
                this.board[i][j] = 10;
    }
}
