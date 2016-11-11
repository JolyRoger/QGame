package org.torquemada.q.view.impl.squares;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.torquemada.q.view.contract.Resizable;

/**
 * Created by torquemada on 5/28/16.
 * This class represents a game cell.
 */
public abstract class Square extends /*ImageView*/ Pane implements Resizable {

    protected int width = 64;
    protected int height = 64;
    protected int id;
    protected ImageView image;

    public Square(String image) {
        super();
        this.image = new ImageView(image);
        getChildren().add(this.image);
    }
/*

//    public Square() { }

    public Square(String s) {
        super(s);
    }
*/

    public Square withId(int id) { this.id = id; return this; }

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
