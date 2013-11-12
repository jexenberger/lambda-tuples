package org.github.lambatuples;

/**
 * Created with IntelliJ IDEA.
 * User: julian3
 * Date: 2013/11/10
 * Time: 7:16 PM
 * PROJECT: ${PROJECT}
 * DESCRIPTION:
 */
public class SQLBuilder {


    public static String insertInto(String schema, String tableName, String... columns) {

        StringBuilder builder = new StringBuilder("INSERT INTO ").append(getTableName(schema, tableName)).append(" (");
        for (String column : columns) {
            builder.append(column).append(',');
        }
        builder.deleteCharAt(builder.length()-1).append(") VALUES (");
        for (String column : columns) {
            builder.append("?,");
        }
        builder.deleteCharAt(builder.length()-1).append(");");
        return builder.toString();
    }

    public static String update(String schema, String tableName, String whereColumn, String... columns) {
        StringBuilder builder = new StringBuilder("UPDATE ").append(getTableName(schema, tableName)).append(" SET ");
        for (String column : columns) {
            builder.append(column).append("=?,");
        }
        return builder.deleteCharAt(builder.length()-1).append(" WHERE ").append(whereColumn).append("=?;").toString();
    }

    public static String selectWhere(String schema, String tableName, String operator, String... columns) {
        StringBuilder builder = new StringBuilder("SELECT  * FROM ").append(getTableName(schema, tableName)).append(" WHERE ");
        for (String column : columns) {
            builder.append(column).append(" = ? ").append(operator);
        }
        return builder.toString();
    }

    public static String getTableName(String schema, String tableName) {
        StringBuilder builder = new StringBuilder();
        if (schema != null && !schema.trim().isEmpty()) {
            builder.append(schema).append('.');
        }
        return builder.append(tableName).toString();
    }


    public static String createTable(String schema, String tableName,  Pair<String, Pair<String, Boolean>> ... columns) {
        StringBuilder builder = new StringBuilder("CREATE TABLE ").append(getTableName(schema, tableName)).append("(\n");
        for (Pair<String, Pair<String, Boolean>> column : columns) {
            builder.append(column.getCar()).append(' ').append(column.getCdr().getCar());
            if (column.getCdr().getCdr()) {
                builder.append(" NOT NULL");
            }
            builder.append(',');
        }
        return builder.deleteCharAt(builder.length()-1).append(");").toString();

    }

    public static String setPrimaryKey(String schema, String tableName, String primaryKeyName,  String... columns) {
        StringBuilder builder = new StringBuilder("ALTER TABLE ")
                .append(getTableName(schema, tableName))
                .append('(')
                .append("ADD CONSTRAINT ")
                .append(primaryKeyName)
                .append(" PRIMARY KEY (");
        for (String column : columns) {
            builder.append(column).append(",");
        }

        return builder.deleteCharAt(builder.length()-1).append(");").toString();

    }


}
