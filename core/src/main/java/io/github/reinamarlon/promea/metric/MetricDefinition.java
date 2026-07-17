package io.github.reinamarlon.promea.metric;

import io.github.reinamarlon.promea.datasource.DataProvider;


public class MetricDefinition {

    private final String name;

    private final String description;

    private final DataProvider<?> provider;


    private MetricDefinition(Builder builder) {
        if (builder.name == null || builder.name.isBlank()) {
            throw new IllegalArgumentException("metric name cannot be blank");
        }

        if (builder.provider == null) {
            throw new IllegalArgumentException("metric provider is required");
        }

        this.name = builder.name;
        this.description = builder.description;
        this.provider = builder.provider;

    }


    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }


    public Object getValue() {
        return provider.get();
    }



    public static Builder builder() {
        return new Builder();
    }



    public static class Builder {

        private String name;

        private String description;

        private DataProvider<?> provider;



        public Builder name(String name) {

            this.name = name;

            return this;
        }



        public Builder description(String description) {

            this.description = description;

            return this;
        }



        public Builder provider(DataProvider<?> provider) {

            this.provider = provider;

            return this;
        }



        public MetricDefinition build() {

            return new MetricDefinition(this);

        }

    }

}
