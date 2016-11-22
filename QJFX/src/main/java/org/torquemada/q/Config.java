package org.torquemada.q;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.controller.impl.QEngine;
import org.torquemada.q.controller.impl.QEventHandler;
import org.torquemada.q.view.contract.IBoard;
import org.torquemada.q.view.contract.IParent;
import org.torquemada.q.view.contract.ISettings;
import org.torquemada.q.view.impl.*;
import org.torquemada.q.view.impl.squares.*;

/**
 * Created by torquemada on 6/2/16.
 * Java Spring configuration class.
 */
@Configuration
@ComponentScan(basePackageClasses = QLevel.class)
public class Config {

    @Bean public IEngine engine() { return new QEngine(); }

    @Bean public IBoard board() { return new QBoard(); }

    @Bean public IParent dynamicField() { return new Dynamic(); }

    @Bean public IParent staticField() { return new Static(); }

    @Bean public ISettings settingsPanel() { return new SettingsPanel(); }

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Empty empty() { return new Empty(staticField()); }

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Solid solid() { return new Solid(staticField()); }

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Empty ball() { return new Empty(staticField()); }

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Loose loose() { return new Loose(staticField()); }

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Marble marble() { return new Marble(); }

    @Bean
    public SelectingFrame frame() { return new SelectingFrame(); }

    @Bean
    public EventHandler<KeyEvent> eventHandler() { return new QEventHandler(); }

    @Bean
    public QAnimationTimer timer() { return new QAnimationTimer(); }
}
