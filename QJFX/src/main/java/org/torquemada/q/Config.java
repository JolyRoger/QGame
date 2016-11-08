package org.torquemada.q;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.controller.impl.QEngine;
import org.torquemada.q.view.impl.QLevel;
import org.torquemada.q.view.impl.SettingsPanel;
import org.torquemada.q.view.impl.squares.Ball;
import org.torquemada.q.view.impl.squares.Empty;
import org.torquemada.q.view.impl.squares.Loose;
import org.torquemada.q.view.impl.squares.Solid;

/**
 * Created by torquemada on 6/2/16.
 */
@Configuration
@ComponentScan(basePackageClasses = QLevel.class)
public class Config {

    @Bean public IEngine engine() { return new QEngine(); }

//    @Bean public IBoard board() { return new QBoard(); }

    @Bean public SettingsPanel settingsPanel() { return new SettingsPanel(); }

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Empty empty() { return new Empty(); }

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Solid solid() { return new Solid(); }

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Ball ball() { return new Ball(); }

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Loose loose() { return new Loose(); }

}
