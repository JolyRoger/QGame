package org.torquemada.q.view.impl;

import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.torquemada.q.model.contract.SquareType;
import org.torquemada.q.model.contract.ValidColor;
import org.torquemada.q.view.contract.IChild;
import org.torquemada.q.view.contract.ILevel;
import org.torquemada.q.view.contract.IParent;
import org.torquemada.q.view.contract.IResizable;
import org.torquemada.q.view.impl.squares.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class QLevel extends Pane implements ILevel, IResizable {
    int rowAmount, colAmount;
    private int[] levelData;
    @Autowired
    private List<Square> squares;
    @Autowired
    private List<Marble> marbles;
    @Autowired
    private SelectingFrame frame;
    @Autowired @Qualifier("staticField")
    private IParent staticField;

    @Autowired @Qualifier("dynamicField")
    private IParent dynamicField;

    @Override
    public void setDimension(int row, int col) {
        this.rowAmount = row;
        this.colAmount = col;
    }

    @Override
    public void setLevelData(int[] levelData) {
        this.levelData = levelData;
    }

    @Override
    public void select(int id, boolean select) {
//        Ball ball = ((Ball) squares[id]);
//        ball.select(select);
//        ball.repaint();

    }

    @Override
    public void init() {
        setStyle("-fx-background-color: 'lightgrey';");

        getChildren().add(staticField.getContainer());
        getChildren().add(dynamicField.getContainer());

        for (int i = 0; i < levelData.length; i++) squares.add(create(i));

        staticField.add(squares);
        dynamicField.add(marbles);
        frame.setMarble(marbles.get(0));
    }
// TODO
    private Square create(int i) {
        int id = levelData[i];
        int col = i % this.colAmount;
        int row = i / this.colAmount;

        SquareType type = SquareType.getType(id);
        switch (type) {
            case empty : return empty().withAddress(col, row);
            case solid : return solid().withAddress(col, row);

            case redball: return createBallAndMarble(ValidColor.RED, col, row).withAddress(col, row);
            case whiteball: return createBallAndMarble(ValidColor.WHITE, col, row).withAddress(col, row);
            case yellowball: return createBallAndMarble(ValidColor.YELLOW, col, row).withAddress(col, row);
            case orangeball: return createBallAndMarble(ValidColor.ORANGE, col, row).withAddress(col, row);
            case blueball : return createBallAndMarble(ValidColor.BLUE, col, row).withAddress(col, row);
            case greenball: return createBallAndMarble(ValidColor.GREEN, col, row).withAddress(col, row);

            case redloose : return loose().withColor(ValidColor.RED).withAddress(col, row);
            case whiteloose : return loose().withColor(ValidColor.WHITE).withAddress(col, row);
            case yellowloose : return loose().withColor(ValidColor.YELLOW).withAddress(col, row);
            case orangeloose : return loose().withColor(ValidColor.ORANGE).withAddress(col, row);
            case blueloose : return loose().withColor(ValidColor.BLUE).withAddress(col, row);
            case greenloose : return loose().withColor(ValidColor.GREEN).withAddress(col, row);
        }
        return empty();
    }

    private Square createBallAndMarble(ValidColor color, int col, int row) {
        marbles.add(marble().withAddress(col,row).withColor(color));
        return ball();
    }

    @Lookup("empty") public Square empty() { return null; }
    @Lookup("ball") public Square ball() { return null; }
    @Lookup public Solid solid() { return null; }
    @Lookup public Loose loose() { return null; }
    @Lookup public Marble marble() { return null; }

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
        staticField.recalculateWidth(newValue);
    }

    @Override
    public void recalculateHeight(Number newValue) {
        dynamicField.recalculateHeight(newValue);
        staticField.recalculateHeight(newValue);
    }
}
