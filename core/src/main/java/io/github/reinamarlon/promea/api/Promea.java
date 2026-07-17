package io.github.reinamarlon.promea.api;

import io.github.reinamarlon.promea.config.PromeaConfig;
import io.github.reinamarlon.promea.dashboard.DashboardBuilder;
import io.github.reinamarlon.promea.dashboard.DashboardDefinition;
import io.github.reinamarlon.promea.dashboard.DashboardRegistry;
import io.github.reinamarlon.promea.server.Server;
import io.github.reinamarlon.promea.theme.Theme;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Main entry point for Promea.
 * Users configure the dashboard via the fluent API and then provide a {@link Server}
 * implementation (e.g., {@code JavalinPromeaServer}) to actually serve the dashboard.
 */
public final class Promea {

    private final DashboardRegistry dashboardRegistry = new DashboardRegistry();
    private PromeaConfig config = PromeaConfig.builder().build();
    private Server server;

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

    public DashboardRegistry getDashboardRegistry() {
        return dashboardRegistry;
    }

    public Collection<DashboardDefinition> getDashboards() {
        return dashboardRegistry.getDashboards();
    }

    public PromeaConfig getConfig() {
        return config;
    }

    /**
     * Sets the server implementation that will serve the dashboard.
     * This method must be called before {@link #start()}.
     *
     * @param server the server implementation
     * @return this builder for fluent chaining
     */
    public Promea server(Server server) {
        this.server = server;
        return this;
    }

    public void start() {
        if (server == null) {
            throw new IllegalStateException("Server implementation must be provided via .server(...)");
        }
        server.start();
    }

    public void stop() {
        if (server != null) {
            server.stop();
        }
    }
}