package io.github.reinamarlon.promea.component;


import io.github.reinamarlon.promea.chart.ChartDefinition;


public class Chart {


    private final ChartDefinition definition;



    public Chart(ChartDefinition definition) {

        this.definition = definition;

    }



    public String getName() {

        return definition.getName();

    }



    public Object getData() {

        return definition.getData();

    }



    public String getType() {

        return definition.getType().name();

    }

}