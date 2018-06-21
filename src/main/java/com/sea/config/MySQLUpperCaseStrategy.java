package com.sea.config;

import com.sea.util.Underline2Camel;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Created by Administrator on 2018/5/23.
 */


public class MySQLUpperCaseStrategy implements PhysicalNamingStrategy {
    private static final long serialVersionUID = 1383021413247872469L;


    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
        return name;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
        return name;
    }

    //表名大写
    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return Identifier.toIdentifier(Underline2Camel.camel2Underline(name.toString()).toUpperCase());
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
        return name;
    }

    //列名大写
    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {

        return Identifier.toIdentifier(Underline2Camel.camel2Underline(name.toString()).toUpperCase());
    }

}

