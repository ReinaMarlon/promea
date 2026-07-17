package io.github.reinamarlon.promea.component;


import io.github.reinamarlon.promea.table.TableDefinition;


public class Table {


    private final TableDefinition definition;



    public Table(TableDefinition definition) {

        this.definition = definition;

    }



    public String getName() {

        return definition.getName();

    }



    public Object getData() {

        return definition.getData();

    }

}