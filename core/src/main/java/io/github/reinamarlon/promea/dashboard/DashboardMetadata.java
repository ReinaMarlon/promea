package io.github.reinamarlon.promea.dashboard;

public final class DashboardMetadata {

    private final String description;
    private final Integer refreshIntervalSeconds;

    private DashboardMetadata(Builder builder) {
        this.description = builder.description;
        this.refreshIntervalSeconds = builder.refreshIntervalSeconds;
    }

    public String getDescription() {
        return description;
    }

    public Integer getRefreshIntervalSeconds() {
        return refreshIntervalSeconds;
    }

    public static DashboardMetadata empty() {
        return builder().build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String description;
        private Integer refreshIntervalSeconds;

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder refreshIntervalSeconds(Integer refreshIntervalSeconds) {
            if (refreshIntervalSeconds != null && refreshIntervalSeconds < 1) {
                throw new IllegalArgumentException("refreshIntervalSeconds must be greater than zero");
            }

            this.refreshIntervalSeconds = refreshIntervalSeconds;
            return this;
        }

        public DashboardMetadata build() {
            return new DashboardMetadata(this);
        }
    }
}
