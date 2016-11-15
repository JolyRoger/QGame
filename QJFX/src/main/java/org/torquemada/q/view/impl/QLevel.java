package org.torquemada.q.view.impl;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.torquemada.q.model.contract.SquareType;
import org.torquemada.q.model.contract.ValidColor;
import org.torquemada.q.view.contract.ILevel;
import org.torquemada.q.view.contract.Resizable;
import org.torquemada.q.view.impl.squares.*;

@Component
public class QLevel extends Pane implements ILevel, Resizable {
    int row, col;
    private int[] levelData;
    private Square[] squares;
    private GridPane staticField;

    @Autowired
    private Dynamic dynamicField;

    @Override
    public void setDimension(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public void setLevelData(int[] levelData) {
        this.levelData = levelData;
    }

    @Override
    public void select(int id, boolean select) {
        Ball ball = ((Ball) squares[id]);
        ball.select(select);
//        ball.repaint();

    }

    @Override
    public void init() {
        setStyle("-fx-background-color: 'lightgrey';");
        staticField = new GridPane();

        getChildren().add(staticField);
        getChildren().add(dynamicField);

        squares = new Square[levelData.length];

        for (int i = 0; i < squares.length; i++) {
            squares[i] = create(i);
            int squareCol = i % col;
            int squareRow = i / col;
            staticField.add(squares[i], squareCol, squareRow);
            if (squares[i] instanceof Ball)
                dynamicField.add((Ball)squares[i]);
        }
    }
// TODO
    private Square create(int i) {
        int id = levelData[i];
        int col = i % this.col;
        int row = i / this.col;

        SquareType type = SquareType.getType(id);
        switch (type) {
            case empty : return empty().withAddress(col, row);
            case solid : return solid().withAddress(col, row);

            case redball: return ball().withColor(ValidColor.RED).withAddress(col, row);
            case whiteball: return ball().withColor(ValidColor.WHITE).withAddress(col, row);
            case yellowball: return ball().withColor(ValidColor.YELLOW).withAddress(col, row);
            case orangeball: return ball().withColor(ValidColor.ORANGE).withAddress(col, row);
            case blueball : return ball().withColor(ValidColor.BLUE).withAddress(col, row);
            case greenball: return  ball().withColor(ValidColor.GREEN).withAddress(col, row);

            case redloose : return loose().withColor(ValidColor.RED).withAddress(col, row);
            case whiteloose : return loose().withColor(ValidColor.WHITE).withAddress(col, row);
            case yellowloose : return loose().withColor(ValidColor.YELLOW).withAddress(col, row);
            case orangeloose : return loose().withColor(ValidColor.ORANGE).withAddress(col, row);
            case blueloose : return loose().withColor(ValidColor.BLUE).withAddress(col, row);
            case greenloose : return loose().withColor(ValidColor.GREEN).withAddress(col, row);
        }
        return empty();
    }

    @Lookup public Empty empty() { return null; }
    @Lookup public Solid solid() { return null; }
    @Lookup public Ball ball() { return null; }
    @Lookup public Loose loose() { return null; }


    @Override
    public void selectToMove(int selectedId) {

    }

    @Override
    public void moveBall(int from, int to) {

    }

    @Override
    public void removeBall(int from) {

    }

    @Override
    public boolean isReadyToMove(int selectedId) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void recalculateWidth(Number newValue) {
        dynamicField.recalculateWidth(newValue);
        for (Node node : staticField.getChildren()) {
            ((Resizable) node).recalculateWidth(newValue.intValue() / col);
        }
    }

    @Override
    public void recalculateHeight(Number newValue) {
        dynamicField.recalculateHeight(newValue);
        for (Node node : staticField.getChildren()) {
            ((Resizable) node).recalculateHeight(newValue.intValue() / row);
        }
    }
}
