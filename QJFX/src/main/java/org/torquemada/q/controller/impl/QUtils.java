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
    private static Stack<Integer> ballStack = new Stack<>();

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
            ballStack.clear();
            theLastDirection = action;
            HashMap<Integer, Float> m = new HashMap<>();

            balls.forEach((b) -> m.put(b, calculateDistanceFor(b, selectedId, col, action)));
            ballStack.addAll(balls.stream().filter((b) -> !m.get(b).isNaN()).sorted(
                    (b1, b2) -> m.get(b1) > m.get(b2) ? -1 : m.get(b1) < m.get(b2) ? 1 : 0).collect(Collectors.toList()));
        }

        return ballStack.isEmpty() ? selectedId : ballStack.pop();
    }
}
