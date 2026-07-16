package io.github.reinamarlon.promea.dashboard;


import io.github.reinamarlon.promea.component.Chart;
import io.github.reinamarlon.promea.component.Metric;
import io.github.reinamarlon.promea.component.Table;


import java.util.ArrayList;
import java.util.List;


public class DashboardDefinition {


    private final String name;
    private DashboardMetadata metadata = DashboardMetadata.empty();


    private final List<Metric> metrics =
            new ArrayList<>();


    private final List<Chart> charts =
            new ArrayList<>();


    private final List<Table> tables =
            new ArrayList<>();



    public DashboardDefinition(String name){
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("dashboard name cannot be blank");
        }

        this.name = name;

    }



    public String getName(){

        return name;

    }



    public List<Metric> getMetrics(){

        return metrics;

    }



    public List<Chart> getCharts(){

        return charts;

    }



    public List<Table> getTables(){

        return tables;

    }

    public DashboardMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(DashboardMetadata metadata) {
        this.metadata = metadata == null ? DashboardMetadata.empty() : metadata;
    }



    public void addMetric(Metric metric){

        metrics.add(metric);

    }


    public void addChart(Chart chart){

        charts.add(chart);

    }


    public void addTable(Table table){

        tables.add(table);

    }

}
