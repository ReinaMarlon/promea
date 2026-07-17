package io.github.reinamarlon.promea.dashboard;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public final class DashboardRegistry {

    private final Map<String, DashboardDefinition> dashboards = new LinkedHashMap<>();

    public void register(DashboardDefinition dashboard) {
        dashboards.put(key(dashboard.getName()), dashboard);
    }

    public Optional<DashboardDefinition> find(String name) {
        return Optional.ofNullable(dashboards.get(key(name)));
    }

    public Collection<DashboardDefinition> getDashboards() {
        return dashboards.values();
    }

    private String key(String name) {
        return name.toLowerCase(Locale.ROOT);
    }
}
