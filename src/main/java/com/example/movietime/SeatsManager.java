package com.example.movietime;

import java.util.Random;

public class SeatsManager {

    public static boolean[][] generateSeating(int rows, int seatsPerRow) {
        boolean[][] seating = new boolean[rows][seatsPerRow];
        Random random = new Random();
        for (int i = 0; i < seating.length; i++) {
            for (int j = 0; j < seating[i].length; j++) {
                seating[i][j] = random.nextBoolean();
            }
        }
        return seating;
    }

    public static int[] findBestSeats(boolean[][] isSeatFree, int n) { // Algorithm generated by GPT 4.0
        int rows = isSeatFree.length;
        int cols = isSeatFree[0].length;
        int bestRow = rows / 2;

        for (int rowOffset = 0; rowOffset < rows; rowOffset++) {
            int row = (rows % 2 == 0 && rowOffset % 2 == 0) ? bestRow + rowOffset / 2 : bestRow - rowOffset / 2;
            for (int colOffset = 0; colOffset <= cols - n; colOffset++) {
                int startCol = (cols % 2 == 0 && colOffset % 2 == 0) ? cols / 2 + colOffset / 2 - n / 2 : cols / 2 - colOffset / 2 - (n - 1) / 2;
                boolean seatsAreFree = true;
                for (int i = 0; i < n; i++) {
                    if (!isSeatFree[row][startCol + i]) {
                        seatsAreFree = false;
                        break;
                    }
                }
                if (seatsAreFree) {
                    return new int[]{row, startCol};
                }
            }
        }
        return new int[]{-1, -1}; // Return {-1, -1} if no suitable seats are found
    }

}
