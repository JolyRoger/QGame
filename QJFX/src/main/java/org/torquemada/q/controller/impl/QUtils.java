package org.torquemada.q.controller.impl;

import org.torquemada.q.model.contract.Direction;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by torquemada on 13.10.16.
 * Calculation of next selected ball.
 */
public class QUtils {

    private static float RIGHT_EST = 1.0f;
    private static float DIAG_EST = 0.3f;
    private static Direction theLastDirection;
    private static Stack<Integer> stack = new Stack<>();

    private static float calculateDistanceFor(int ball, int selectedId, int col, Direction action) {
        int candidateRow = ball / col;
        int candidateCol = ball % col;
        int selectedRow = selectedId / col;
        int selectedCol = selectedId % col;

        int lambdaRow = Math.abs(candidateRow - selectedRow);
        int lambdaCol = Math.abs(candidateCol - selectedCol);

        switch(action) {
            case Right : return candidateCol > selectedCol ? lambdaCol * RIGHT_EST + lambdaRow * DIAG_EST : Float.NaN;
            case Left: return candidateCol < selectedCol ? lambdaCol * RIGHT_EST + lambdaRow * DIAG_EST : Float.NaN;
            case Up : return candidateRow < selectedRow ? lambdaRow * RIGHT_EST + lambdaCol * DIAG_EST : Float.NaN;
            case Down: return candidateRow > selectedRow ? lambdaRow * RIGHT_EST + lambdaCol * DIAG_EST : Float.NaN;
        }
        return Float.NaN;
    }

    public static int calculatePreferredToSelect(ArrayList<Integer> balls, int selectedId, int col, Direction action) {
        if (theLastDirection != action) {
            stack.clear();
            theLastDirection = action;
            HashMap<Integer, Float> m = new HashMap<>();
            balls.forEach((b) -> m.put(b, calculateDistanceFor(b, selectedId, col, action)));

            stack.addAll(balls.stream().filter((b) -> !m.get(b).isNaN()).sorted(
                    (b1, b2) -> m.get(b1) > m.get(b2) ? -1 : m.get(b1) < m.get(b2) ? 1 : 0).collect(Collectors.toList()));
        }

        if (stack.isEmpty()) return selectedId;
        else return stack.pop();
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
