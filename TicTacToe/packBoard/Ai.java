package packBoard;

import java.util.Random;

public class Ai extends Board {
    public int easyMove(int[][] board, int value) {
        int valid = 0, position = 0;
        this.setBoard(board);
        while (valid == 0) {
            Random random = new Random();
            position = random.nextInt(9) + 1;
            System.out.println("\nposition = " + position + "\n");
            valid = this.boardPlace(position, value);
        }
        System.out.println("\nReturning " + position);
        return position;
    }

    public int hardMove(int[][] board, int value) {
        int oppSum, i;
        if (value == 1)
            oppSum = 0;
        else
            oppSum = 2;
        // defense
        for (i = 0; i < 3; i++) {
            if ((board[i][0] + board[i][1] == oppSum) && board[i][2] == 10) {
                if (i == 0)
                    return 3;
                else if (i == 1)
                    return 6;
                return 9;
            }
            if ((board[i][0] + board[i][2] == oppSum) && board[i][1] == 10) {
                if (i == 0)
                    return 2;
                else if (i == 1)
                    return 5;
                return 8;
            }
            if ((board[i][1] + board[i][2] == oppSum) && board[i][0] == 10) {
                if (i == 0)
                    return 1;
                else if (i == 1)
                    return 4;
                return 7;
            }
        }
        for (i = 0; i < 3; i++) {
            if ((board[0][i] + board[1][i] == oppSum) && board[2][i] == 10) {
                if (i == 0)
                    return 7;
                else if (i == 1)
                    return 8;
                return 9;
            }
            if ((board[0][i] + board[2][i] == oppSum) && board[1][i] == 10) {
                if (i == 0)
                    return 4;
                else if (i == 1)
                    return 5;
                return 6;
            }
            if ((board[1][i] + board[2][i] == oppSum) && board[0][i] == 10) {
                if (i == 0)
                    return 1;
                else if (i == 1)
                    return 2;
                return 3;
            }
        }
        if ((board[0][0] + board[1][1] == oppSum) && board[2][2] == 10)
            return 9;
        if ((board[0][0] + board[2][2] == oppSum) && board[1][1] == 10)
            return 5;
        if ((board[1][1] + board[2][2] == oppSum) && board[0][0] == 10)
            return 1;
        if ((board[0][2] + board[1][1] == oppSum) && board[2][0] == 10)
            return 7;
        if ((board[2][0] + board[1][1] == oppSum) && board[0][2] == 10)
            return 3;
        if ((board[2][0] + board[0][2] == oppSum) && board[1][1] == 10)
            return 5;

        // offense
        if (board[0][0] == 10)
            return 1;
        if (board[0][2] == 10)
            return 3;
        if (board[2][0] == 10)
            return 7;
        if (board[2][2] == 10)
            return 9;
        return this.easyMove(board, value);
    }
}
