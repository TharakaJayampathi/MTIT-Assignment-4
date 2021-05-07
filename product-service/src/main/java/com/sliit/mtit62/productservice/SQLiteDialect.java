package com.sliit.mtit62.productservice;

import java.sql.Types;

public class SQLiteDialect extends Dialect{
    registerColumnType(Types.BIT, "integer");
    registerColumnType(Types.TINYINT, "tinyint");
    registerColumnType(Types.SMALLINT, "smallint");
    registerColumnType(Types.INTEGER, "integer");
}
