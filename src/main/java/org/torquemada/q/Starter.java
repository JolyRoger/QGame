package org.torquemada.q;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.Getter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.model.impl.Resources;

/**
 * Created by torquemada on 20.11.16.
 * Starter with main method for Q-game.
 */
public class Starter extends Application {

    @Getter
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setOnCloseRequest((e) -> {
            Platform.exit();
            System.exit(0);
        });

        Resources.loadResources();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Platform.runLater(context.getBean(IEngine.class)::run);

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
