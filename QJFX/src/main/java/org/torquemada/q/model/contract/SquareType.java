package org.torquemada.q.model.contract;

/**
 * Created by torquemada on 5/28/16.
 */
public enum SquareType {
    empty, solid,
    redball, whiteball, yellowball, orangeball, blueball, greenball,
    redloose, whiteloose, yellowloose, orangeloose, blueloose, greenloose;

    public static SquareType getType(int i) {
        switch (i) {
            case 0 : return empty;
            case 1 : return solid;

            case 2 : return redball;
            case 3 : return whiteball;
            case 4 : return yellowball;
            case 5 : return orangeball;
            case 6 : return blueball;
            case 7 : return greenball;

            case 22 : return redloose;
            case 33 : return whiteloose;
            case 44 : return yellowloose;
            case 55 : return orangeloose;
            case 66 : return blueloose;
            case 77 : return greenloose;

            default: return empty;
        }
    }
}


