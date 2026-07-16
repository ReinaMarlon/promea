package io.github.reinamarlon.promea.dashboard;


import io.github.reinamarlon.promea.api.Promea;
import io.github.reinamarlon.promea.component.Chart;
import io.github.reinamarlon.promea.component.Metric;
import io.github.reinamarlon.promea.component.Table;


import io.github.reinamarlon.promea.metric.MetricDefinition;
import io.github.reinamarlon.promea.chart.ChartDefinition;
import io.github.reinamarlon.promea.table.TableDefinition;



public class DashboardBuilder {

    private final Promea promea;
    private final DashboardDefinition dashboard;
    private final DashboardMetadata.Builder metadataBuilder =
            DashboardMetadata.builder();



    public DashboardBuilder(
            Promea promea,
            DashboardDefinition dashboard
    ){

        this.promea = promea;
        this.dashboard = dashboard;

    }

    public DashboardBuilder description(String description) {
        metadataBuilder.description(description);
        dashboard.setMetadata(metadataBuilder.build());
        return this;
    }

    public DashboardBuilder refreshIntervalSeconds(int seconds) {
        metadataBuilder.refreshIntervalSeconds(seconds);
        dashboard.setMetadata(metadataBuilder.build());
        return this;
    }



    public DashboardBuilder metric(
            MetricDefinition definition
    ){

        dashboard.addMetric(
                new Metric(definition)
        );

        return this;

    }



    public DashboardBuilder chart(
            ChartDefinition definition
    ){

        dashboard.addChart(
                new Chart(definition)
        );

        return this;

    }



    public DashboardBuilder table(
            TableDefinition definition
    ){

        dashboard.addTable(
                new Table(definition)
        );

        return this;

    }



    public DashboardDefinition build(){

        return dashboard;

    }

    public Promea start() {
        promea.start();
        return promea;
    }

}
