package io.github.reinamarlon.promea.table;


import io.github.reinamarlon.promea.datasource.DataProvider;


public class TableDefinition {


    private final String name;

    private final DataProvider<?> provider;



    private TableDefinition(Builder builder) {
        if (builder.name == null || builder.name.isBlank()) {
            throw new IllegalArgumentException("table name cannot be blank");
        }

        if (builder.provider == null) {
            throw new IllegalArgumentException("table provider is required");
        }

        this.name = builder.name;
        this.provider = builder.provider;

    }



    public String getName() {

        return name;

    }



    public Object getData() {

        return provider.get();

    }



    public static Builder builder() {

        return new Builder();

    }



    public static class Builder {


        private String name;

        private DataProvider<?> provider;



        public Builder name(String name) {

            this.name = name;

            return this;

        }



        public Builder provider(DataProvider<?> provider) {

            this.provider = provider;

            return this;

        }



        public TableDefinition build() {

            return new TableDefinition(this);

        }

    }

}
