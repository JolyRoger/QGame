package org.torquemada.q.view.impl.squares;

import javafx.scene.paint.Color;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.torquemada.javafx.move.Vector2D;
import org.torquemada.q.model.contract.ValidColor;
import org.torquemada.q.view.impl.Dynamic;

public class Ball extends ColorfulSquare {

    private boolean selected = false;
    private boolean selectedToMove = false;
    @Getter
    protected Marble marble;

    @Autowired
    private Dynamic dynamic;

    public Ball() {
        super("/empty.png");
    }

    public void select(boolean select) {
        this.selected = select;
        selectedToMove = false;
    }

    public void selectToMove() {
        selectedToMove = !selectedToMove;
    }

    public boolean isReadyToMove() {
        return selectedToMove;
    }

    @Override
    public ColorfulSquare withColor(ValidColor color) {
        super.withColor(color);
        createMarble(color.getPlatformColor());
        return this;
    }

    private void createMarble(Color color) {
        // random location
        double x = getWidth();
        double y = getHeight();

        // dimensions
        double width = 50;
        double height = width / 2.0;

        // create vehicle data
        Vector2D location = new Vector2D( x,y);
        Vector2D velocity = new Vector2D( 10,10);
        Vector2D acceleration = new Vector2D( 0,0);

        marble = new Marble(dynamic, /*color.getPlatformColor()*/ color);
    }
}
