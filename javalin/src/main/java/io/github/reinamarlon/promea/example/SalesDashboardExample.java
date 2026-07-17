package io.github.reinamarlon.promea.example;

import io.github.reinamarlon.promea.api.Promea;
import io.github.reinamarlon.promea.chart.ChartDefinition;
import io.github.reinamarlon.promea.chart.ChartType;
import io.github.reinamarlon.promea.metric.MetricDefinition;
import io.github.reinamarlon.promea.server.PromeaServer;
import io.github.reinamarlon.promea.table.TableDefinition;
import io.github.reinamarlon.promea.theme.Theme;

import java.util.List;
import java.util.Map;

/**
 * Example demonstrating how to create and start a Promea dashboard
 * using the Javalin server implementation.
 */
public class SalesDashboardExample {

    public static void main(String[] args) {
        Promea prom = Promea.create()
                .applicationName("Promea Demo")
                .port(7070)
                .theme(Theme.system());

        PromeaServer server = new PromeaServer(prom.getDashboardRegistry(), prom.getConfig());
        prom.server(server)
                .dashboard("Ventas")
                    .description("Panel ejecutivo de metricas comerciales.")
                    .refreshIntervalSeconds(30)
                    .metric(
                            MetricDefinition.builder()
                                    .name("Usuarios")
                                    .description("Usuarios registrados")
                                    .provider(() -> 150)
                                    .build()
                    )
                    .chart(
                            ChartDefinition.builder()
                                    .name("Ventas por mes")
                                    .type(ChartType.BAR)
                                    .provider(() -> Map.of(
                                            "Enero", 15,
                                            "Febrero", 29,
                                            "Marzo", 48,
                                            "Abril", 61
                                    ))
                                    .build()
                    )
                    .chart(
                            ChartDefinition.builder()
                                    .name("Usuarios activos")
                                    .type(ChartType.LINE)
                                    .provider(() -> Map.of(
                                            "Semana 1", 18,
                                            "Semana 2", 32,
                                            "Semana 3", 27,
                                            "Semana 4", 44
                                    ))
                                    .build()
                    )
                    .table(
                            TableDefinition.builder()
                                    .name("Clientes")
                                    .provider(() -> List.of(
                                            Map.of(
                                                    "nombre", "Carlos",
                                                    "plan", "Pro",
                                                    "mrr", "$89"
                                            ),
                                            Map.of(
                                                    "nombre", "Dayana",
                                                    "plan", "Business",
                                                    "mrr", "$149"
                                            )
                                    ))
                                    .build()
                    )
                .start();
    }
}