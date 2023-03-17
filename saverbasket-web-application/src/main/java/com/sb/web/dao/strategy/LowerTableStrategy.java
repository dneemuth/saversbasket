package com.sb.web.dao.strategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class LowerTableStrategy extends PhysicalNamingStrategyStandardImpl {

    /**
    *
    */
    private static final long serialVersionUID = 1L;
    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        //The table name all converted to uppercase
        String tableName = name.getText().toLowerCase();
         
        return name.toIdentifier(tableName);
    }
    
    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        String colnumName = name.getText().toLowerCase();
        return name.toIdentifier(colnumName);
    }

}
