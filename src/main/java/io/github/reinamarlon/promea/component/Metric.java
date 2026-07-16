package io.github.reinamarlon.promea.component;

import io.github.reinamarlon.promea.metric.MetricDefinition;

public class Metric {

    private final MetricDefinition definition;

    public Metric(MetricDefinition definition) {

        this.definition = definition;

    }



    public String getName() {

        return definition.getName();

    }



    public Object getValue() {

        return definition.getValue();

    }



    public String getDescription() {

        return definition.getDescription();

    }
}