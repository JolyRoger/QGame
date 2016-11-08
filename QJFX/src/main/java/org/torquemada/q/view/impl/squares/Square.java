package org.torquemada.q.view.impl.squares;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by torquemada on 5/28/16.
 */
public abstract class Square extends ImageView {

    protected int width = 64;
    protected int height = 64;
    protected int id;

    public Square(Image image) {
        super(image);

    }
    public Square() { }

    public Square(String s) {
        super(s);
    }

    public Square withId(int id) { this.id = id; return this; }

}
