<p align="center">
  <img src="assets/logo.svg" alt="Promea logo" width="96" height="96">
</p>

<h1 align="center">Promea</h1>

<p align="center">
  Professional Metrics Analytics for Java applications.
</p>

<p align="center">
  Promea is a lightweight Java library for embedding analytics dashboards into applications.
</p>

---

## Why Promea?

Building dashboards usually requires:

- Frontend framework
- API layer
- Chart libraries
- Authentication
- Data transformation

Promea provides a lightweight embedded dashboard layer directly from Java code.

---

## Demo

The demo dashboard is available at:

```text
http://localhost:7070/dashboard/Ventas
```

## Installation

Promea is currently prepared as a local Maven project. Once published, it can be consumed as:

```xml
<dependency>
    <groupId>io.github.reinamarlon</groupId>
    <artifactId>promea</artifactId>
    <version>0.1.0</version>
</dependency>
```

For local development:

```bash
mvn clean install
```

## Quick Start

```java
import io.github.reinamarlon.promea.api.Promea;
import io.github.reinamarlon.promea.chart.ChartDefinition;
import io.github.reinamarlon.promea.chart.ChartType;
import io.github.reinamarlon.promea.metric.MetricDefinition;
import io.github.reinamarlon.promea.table.TableDefinition;
import io.github.reinamarlon.promea.theme.Theme;

import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        Promea.create()
                .applicationName("Acme Analytics")
                .port(7070)
                .theme(Theme.system())
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
                                        "Marzo", 48
                                ))
                                .build()
                )
                .table(
                        TableDefinition.builder()
                                .name("Clientes")
                                .provider(() -> List.of(
                                        Map.of("nombre", "Carlos", "plan", "Pro"),
                                        Map.of("nombre", "Dayana", "plan", "Business")
                                ))
                                .build()
                )
                .start();
    }
}
```

The public entry point is `io.github.reinamarlon.promea.api.Promea`.

Internally, Promea registers dashboard definitions in `DashboardRegistry`. Each dashboard owns its components and metadata. Components use `DataProvider<T>` so values are resolved dynamically when `/api/dashboard/{name}` is requested. The server layer only knows about the registry and configuration, which keeps the library API decoupled from HTTP delivery.

## Example

Run the bundled sales dashboard:

```bash
mvn clean compile exec:java
```

Then open:

```text
http://localhost:7070/dashboard/Ventas
```

The example lives at:

```text
src/main/java/io/github/reinamarlon/promea/example/SalesDashboardExample.java
```

## API

Every dashboard exposes:

GET

/api/dashboard/{name}


Example response:

{
"name":"Ventas",
"metrics":[...],
"charts":[...],
"tables":[...]
}


## Visual Identity

Promea means **Professional Metrics Analytics**.

The identity uses a geometric `P` mark combined with metric nodes. The symbol is designed to work as:

- Product logo in README and documentation
- Dashboard header mark
- Favicon
- Future package or marketplace icon

## Tech Stack

- Java 21
- Maven
- Javalin
- Jackson
- HTML
- CSS
- JavaScript
- Chart.js

## License

MIT License. See `LICENSE`.
