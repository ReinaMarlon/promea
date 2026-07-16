package io.github.reinamarlon.promea.api;

import io.github.reinamarlon.promea.config.PromeaConfig;
import io.github.reinamarlon.promea.dashboard.DashboardBuilder;
import io.github.reinamarlon.promea.dashboard.DashboardDefinition;
import io.github.reinamarlon.promea.dashboard.DashboardRegistry;
import io.github.reinamarlon.promea.server.PromeaServer;
import io.github.reinamarlon.promea.theme.Theme;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

public final class Promea {

    private final DashboardRegistry dashboardRegistry = new DashboardRegistry();
    private PromeaConfig config = PromeaConfig.builder().build();
    private PromeaServer server;

    private Promea() {
    }

    public static Promea create() {
        return new Promea();
    }

    public Promea configure(Consumer<PromeaConfig.Builder> customizer) {
        PromeaConfig.Builder builder = PromeaConfig.builder()
                .applicationName(config.getApplicationName())
                .port(config.getPort())
                .theme(config.getTheme());

        customizer.accept(builder);
        this.config = builder.build();
        return this;
    }

    public Promea applicationName(String applicationName) {
        return configure(builder -> builder.applicationName(applicationName));
    }

    public Promea port(int port) {
        return configure(builder -> builder.port(port));
    }

    public Promea theme(Theme theme) {
        return configure(builder -> builder.theme(theme));
    }

    public DashboardBuilder dashboard(String name) {
        DashboardDefinition dashboard = new DashboardDefinition(name);
        dashboardRegistry.register(dashboard);
        return new DashboardBuilder(this, dashboard);
    }

    public Optional<DashboardDefinition> findDashboard(String name) {
        return dashboardRegistry.find(name);
    }

    public Collection<DashboardDefinition> getDashboards() {
        return dashboardRegistry.getDashboards();
    }

    public PromeaConfig getConfig() {
        return config;
    }

    public void start() {
        server = new PromeaServer(dashboardRegistry, config);
        server.start();
    }

    public void stop() {
        if (server != null) {
            server.stop();
        }
    }
}
