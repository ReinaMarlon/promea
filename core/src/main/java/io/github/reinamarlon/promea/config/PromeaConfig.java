package io.github.reinamarlon.promea.config;

import io.github.reinamarlon.promea.theme.Theme;

public final class PromeaConfig {

    private final String applicationName;
    private final int port;
    private final Theme theme;

    private PromeaConfig(Builder builder) {
        this.applicationName = builder.applicationName;
        this.port = builder.port;
        this.theme = builder.theme;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public int getPort() {
        return port;
    }

    public Theme getTheme() {
        return theme;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String applicationName = "Promea";
        private int port = 7070;
        private Theme theme = Theme.system();

        public Builder applicationName(String applicationName) {
            this.applicationName = requireText(applicationName, "applicationName");
            return this;
        }

        public Builder port(int port) {
            if (port < 1 || port > 65535) {
                throw new IllegalArgumentException("port must be between 1 and 65535");
            }

            this.port = port;
            return this;
        }

        public Builder theme(Theme theme) {
            this.theme = theme == null ? Theme.system() : theme;
            return this;
        }

        public PromeaConfig build() {
            return new PromeaConfig(this);
        }

        private static String requireText(String value, String field) {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException(field + " cannot be blank");
            }

            return value;
        }
    }
}
