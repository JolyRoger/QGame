package org.torquemada.q;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.torquemada.q.controller.contract.IEngine;
import org.torquemada.q.controller.impl.QEngine;
import org.torquemada.q.view.contract.IParent;
import org.torquemada.q.view.impl.Dynamic;
import org.torquemada.q.view.impl.QLevel;
import org.torquemada.q.view.impl.SettingsPanel;
import org.torquemada.q.view.impl.Static;
import org.torquemada.q.view.impl.squares.*;

/**
 * Created by torquemada on 6/2/16.
 * Java Spring configuration class.
 */
@Configuration
@ComponentScan(basePackageClasses = QLevel.class)
public class Config {

    @Bean public IEngine engine() { return new QEngine(); }

    @Bean public IParent dynamicField() { return new Dynamic(); }

    @Bean public IParent staticField() { return new Static(); }

    @Bean public SettingsPanel settingsPanel() { return new SettingsPanel(); }

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
}
