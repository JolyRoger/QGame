package org.torquemada.q.controller.impl;

import org.torquemada.q.model.contract.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by torquemada on 13.10.16.
 */
public class QUtils {

    private static float RIGHT_EST = 1.0f;
    private static float DIAG_EST = 0.3f;

    private static float calculateDistanceFor(int ball, int selectedId, int col, Direction action) {
        int candidateRow = ball / col;
        int candidateCol = ball % col;
        int selectedRow = selectedId / col;
        int selectedCol = selectedId % col;

        int lambdaRow = Math.abs(candidateRow - selectedRow);
        int lambdaCol = Math.abs(candidateCol - selectedCol);

//        boolean rowLambdaLessThanColLambda = lambdaRow <= lambdaCol;
//        boolean rowLambdaMoreThanColLambda = lambdaRow >= lambdaCol;

        switch(action) {
            case Right : return candidateCol > selectedCol /*&& rowLambdaLessThanColLambda */?
                    lambdaCol * RIGHT_EST + lambdaRow * DIAG_EST : Float.NaN;
            case Left: return candidateCol < selectedCol /*&& rowLambdaLessThanColLambda */?
                    lambdaCol * RIGHT_EST + lambdaRow * DIAG_EST : Float.NaN;
            case Up : return candidateRow < selectedRow /*&& rowLambdaMoreThanColLambda */?
                    lambdaRow * RIGHT_EST + lambdaCol * DIAG_EST : Float.NaN;
            case Down: return candidateRow > selectedRow /*&& rowLambdaMoreThanColLambda */?
                    lambdaRow * RIGHT_EST + lambdaCol * DIAG_EST : Float.NaN;
        }
        return Float.NaN;
    }

    public static int calculatePreferredToSelect(ArrayList<Integer> balls, int selectedId, int col, Direction action) {
        float minDistance = Float.MAX_VALUE;
        int selectedBall = selectedId;

        for (int ball : balls) {
            if (ball == selectedId) continue;
            float calculateDistance = calculateDistanceFor(ball, selectedId, col, action);
            if (calculateDistance < minDistance) {
                minDistance = calculateDistance;
                selectedBall = ball;
            }
        }
        return selectedBall;
    }


    public static int[][] asMatrix(int[] array, int row, int col) {
        int[][] out = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                out[i][j] = array[col*i + j];
            }
        }

        return out;
    }

    public static int[][] asMatrix(List<Integer> balls, int row, int col) {
        int[] array = new int[balls.size()];
        for(int i = 0; i < balls.size(); i++) array[i] = balls.get(i);
        return asMatrix(array, row, col);
    }





    public static void print(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.printf(" %d", array[i]);
        }
    }
    public static void print(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            System.out.println();
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf(" %2d", matrix[i][j]);
            }
        }
    }

    public static void main(String[] args) {
//        print(new int[] {1,2,3,4,5,6,7,8,9});
//        System.out.println("------------------------------");
//        print(new int[][] { {1,2,3},{4,5,6},{7,8,9} });

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println();
        System.out.println();
        System.out.println();

    }

}
