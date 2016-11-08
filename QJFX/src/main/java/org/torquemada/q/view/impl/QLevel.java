package org.torquemada.q.view.impl;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.torquemada.q.model.contract.SquareType;
import org.torquemada.q.model.contract.ValidColor;
import org.torquemada.q.model.impl.Resources;
import org.torquemada.q.view.contract.ILevel;
import org.torquemada.q.view.impl.squares.*;

@Component
public class QLevel extends Pane implements ILevel {
    int row, col;
    private int[] levelData;
    private Square[] squares;
    private GridPane field;
    private Canvas dynamic;

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
        setStyle("-fx-background-color: DAE6F3;");
        field = new GridPane();
        dynamic = new Canvas(384, 384);

        GraphicsContext g = dynamic.getGraphicsContext2D();
        g.setFill(Color.GREEN);
        g.fillOval(384-50,384-50-29,50,50);

        getChildren().add(field);
        getChildren().add(dynamic);

        squares = new Square[levelData.length];

        for (int i = 0; i < squares.length; i++) {
            squares[i] = create(i);
            field.add(squares[i], i % col, i / col);
        }
    }

    private Square create(int i) {
        int id = levelData[i];
        SquareType type = SquareType.getType(id);
        switch (type) {
            case empty : return empty().withId(i);
            case solid : return solid().withId(i);

            case redball: return ball().withColor(ValidColor.RED).withId(i);
            case whiteball: return ball().withColor(ValidColor.WHITE).withId(i);
            case yellowball: return ball().withColor(ValidColor.YELLOW).withId(i);
            case orangeball: return ball().withColor(ValidColor.ORANGE).withId(i);
            case blueball : return ball().withColor(ValidColor.BLUE).withId(i);
            case greenball: return  ball().withColor(ValidColor.GREEN).withId(i);

            case redloose : return loose().withColor(ValidColor.RED).withId(i);
            case whiteloose : return loose().withColor(ValidColor.WHITE).withId(i);
            case yellowloose : return loose().withColor(ValidColor.YELLOW).withId(i);
            case orangeloose : return loose().withColor(ValidColor.ORANGE).withId(i);
            case blueloose : return loose().withColor(ValidColor.BLUE).withId(i);
            case greenloose : return loose().withColor(ValidColor.GREEN).withId(i);
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
    public void recalculateHeight(Number newValue) {
        for (Node node : field.getChildren()) {
            if (node instanceof ImageView) {
                ((ImageView) node).setFitHeight(newValue.intValue() / row);
            }
        }
    }

    @Override
    public void recalculateWidth(Number newValue) {
        for (Node node : field.getChildren()) {
            if (node instanceof ImageView) {
                ((ImageView) node).setFitWidth(newValue.intValue() / col);
            }
        }
    }
}
