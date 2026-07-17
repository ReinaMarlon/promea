package io.github.reinamarlon.promea.chart;


import io.github.reinamarlon.promea.datasource.DataProvider;


public class ChartDefinition {


    private final String name;

    private final ChartType type;

    private final DataProvider<?> provider;



    private ChartDefinition(Builder builder) {
        if (builder.name == null || builder.name.isBlank()) {
            throw new IllegalArgumentException("chart name cannot be blank");
        }

        if (builder.provider == null) {
            throw new IllegalArgumentException("chart provider is required");
        }

        this.name = builder.name;
        this.type = builder.type;
        this.provider = builder.provider;

    }



    public String getName() {
        return name;
    }


    public ChartType getType() {
        return type;
    }


    public Object getData() {

        return provider.get();

    }



    public static Builder builder() {

        return new Builder();

    }



    public static class Builder {


        private String name;

        private ChartType type = ChartType.BAR;

        private DataProvider<?> provider;



        public Builder name(String name) {

            this.name = name;

            return this;

        }



        public Builder type(ChartType type) {

            this.type = type;

            return this;

        }



        public Builder provider(DataProvider<?> provider) {

            this.provider = provider;

            return this;

        }



        public ChartDefinition build() {

            return new ChartDefinition(this);

        }

    }

}
