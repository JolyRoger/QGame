package org.torquemada.q.view.impl.squares;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.Getter;
import org.torquemada.q.model.contract.ValidColor;
import org.torquemada.q.view.contract.Resizable;

/**
 * Created by torquemada on 5/28/16.
 * This class represents a game cell.
 */
public abstract class Square extends /*ImageView*/ Pane implements Resizable {

    protected int width = 64;
    protected int height = 64;
    @Getter
    protected int row, col;

    protected ImageView image;

    public Square(String image) {
        super();
        this.image = new ImageView(image);
        getChildren().add(this.image);
    }

    public Square withAddress(int row, int col) {
        this.row = row;
        this.col = col;
        return this;
    }

    @Override
    public void recalculateHeight(Number newValue) {
//        setHeight(newValue.doubleValue());
        image.setFitHeight(newValue.doubleValue());
    }

    @Override
    public void recalculateWidth(Number newValue) {
//        setWidth(newValue.doubleValue());
        image.setFitWidth(newValue.doubleValue());
    }
}
