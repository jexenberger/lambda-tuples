package org.github.lambatuples;

import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import java.sql.Connection;

import static org.github.lambatuples.SQL.execDDL;
import static org.github.lambatuples.SQL.execDML;
import static org.github.lambatuples.SQLBuilder.insertInto;

/**
 * Created with IntelliJ IDEA.
 * User: julian3
 * Date: 2013/11/10
 * Time: 7:12 PM
 * PROJECT: ${PROJECT}
 * DESCRIPTION:
 */
public abstract class HSQLBootStrap {


    public static final String SCHEMA = "TESTING";
    public static final String TABLE_NAME = "SAMPLE";
    public static final String ID = "test_id";
    public static final String NAME = "name";
    static JDBCDataSource DATA_SOURCE = new JDBCDataSource();
    public Connection connection;

    @BeforeClass
    public static void loadDb() throws Exception {
        DATA_SOURCE.setUrl("jdbc:hsqldb:mem:lamba_tuple_test");
        DATA_SOURCE.setUser("sa");
        DATA_SOURCE.setPassword("");

        LambdaTuplesContext.DATA_SOURCE = DATA_SOURCE;
        LambdaTuplesContext.SCHEMA = SCHEMA;

        try (Connection connection = DATA_SOURCE.getConnection()) {
            String testTable = "CREATE TABLE TESTING.SAMPLE " +
                    "(" +
                    "test_id int, " +
                    "name varchar(255)," +
                    "primary key (test_id)" +
                    ");";

            execDDL(connection, "CREATE SCHEMA TESTING;");
            execDDL(connection, testTable);
            for (int i = 1; i < 100; i++) {

                execDML(connection, insertInto(SCHEMA, TABLE_NAME, ID, NAME), i, "test"+i);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public  void beforeTest() throws Exception{
        connection = DATA_SOURCE.getConnection();
    }

    @After
    public  void afterTest() throws Exception{
        if (connection != null) {
            connection.close();
        }
    }




}