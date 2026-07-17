package io.github.reinamarlon.promea.server;

import io.github.reinamarlon.promea.config.PromeaConfig;
import io.github.reinamarlon.promea.dashboard.DashboardRegistry;
import io.github.reinamarlon.promea.server.Server;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;


/**
 * Javalin-based implementation of the {@link Server} interface.
 * Serves Promea dashboards via HTTP.
 */
public class PromeaServer implements Server {

    private final DashboardRegistry dashboardRegistry;
    private final PromeaConfig config;

    private Javalin server;


    public PromeaServer(DashboardRegistry dashboardRegistry, PromeaConfig config) {
        this.dashboardRegistry = dashboardRegistry;
        this.config = config;
    }


    @Override
    public void start() {

        server = Javalin.create(javalinConfig -> {
                    javalinConfig.staticFiles.add(staticFiles -> {
                            staticFiles.hostedPath = "/assets";
                            staticFiles.directory = "/assets";
                            staticFiles.location = Location.CLASSPATH;
                    });
                })
                .get("/", ctx -> {
                    var firstDashboard = dashboardRegistry.getDashboards()
                            .stream()
                            .findFirst();

                    if (firstDashboard.isEmpty()) {
                        ctx.result("Promea is running. No dashboards have been registered.");
                        return;
                    }

                    ctx.redirect("/dashboard/" + firstDashboard.get().getName());
                })

                .get("/favicon.svg", ctx -> {
                    InputStream stream =
                            getClass()
                                    .getClassLoader()
                                    .getResourceAsStream(
                                            "assets/favicon.svg"
                                    );

                    if (stream == null) {
                        ctx.status(404);
                        return;
                    }

                    String svg =
                            new String(
                                    stream.readAllBytes(),
                                    StandardCharsets.UTF_8
                            );

                    ctx.contentType("image/svg+xml");
                    ctx.result(svg);
                })

                .get("/api/dashboards", ctx -> ctx.json(dashboardRegistry.getDashboards()))

                .get("/api/dashboard/{name}", ctx -> {


                    String name =
                            ctx.pathParam("name");


                    var dashboard =
                            dashboardRegistry.find(name);



                    if (dashboard.isEmpty()) {

                        ctx.status(404);

                        ctx.result(
                                "Dashboard not found"
                        );

                        return;

                    }


                    ctx.json(dashboard.get());

                })


                .get("/dashboard/{name}", ctx -> {


                    InputStream stream =
                            getClass()
                                    .getClassLoader()
                                    .getResourceAsStream(
                                            "web/dashboard.html"
                                    );


                    if (stream == null) {

                        ctx.status(404);

                        return;

                    }


                    String html =
                            new String(
                                    stream.readAllBytes(),
                                    StandardCharsets.UTF_8
                            );

                    ctx.html(html);

                })


                .start(config.getPort());



        System.out.println(
                config.getApplicationName() + " running on http://localhost:" + config.getPort()
        );

    }

    @Override
    public void stop() {
        if (server != null) {
            server.stop();
        }
    }
}
