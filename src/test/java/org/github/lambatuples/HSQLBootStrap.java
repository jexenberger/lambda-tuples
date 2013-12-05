package org.github.lambatuples;

import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Date;

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
    public static final String COMPLEX_SQL = "select * from testing.persons p , testing.orders o, testing.order_items oi , testing.items i where " +
            "p.person_id = o.person_id and " +
            "o.order_id = oi.order_id and " +
            "oi.item_id = i.item_id ";
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

                execDML(connection, insertInto(SCHEMA, TABLE_NAME, ID, NAME), i, "test" + i);
            }
            String script = new String(Files.readAllBytes(Paths.get("src", "test", "resources", "test_db.sql")));
            String[] tables = script.split(";");
            for (String table : tables) {
                String trim = table.trim();
                if (!trim.equals("")) {
                    execDDL(connection, trim);
                }
            }
            execDML(connection,insertInto("testing","persons", "person_id", "last_name", "first_name", "address", "city"), 1, "Smith","John","1 Withering Heights","Nowhereton");
            execDML(connection,insertInto("testing","persons", "person_id", "last_name", "first_name", "address", "city"), 2, "Pompies","Piet","1 Wilgerood straat","PompiesVille");
            execDML(connection,insertInto("testing","items", "item_id", "item_name", "item_price"), 1, "Bricks",300.0);
            execDML(connection,insertInto("testing","items", "item_id", "item_name", "item_price"), 2, "Planks",50.0);
            execDML(connection,insertInto("testing","items", "item_id", "item_name", "item_price"), 3, "Cement",10.0);
            execDML(connection,insertInto("testing","items", "item_id", "item_name", "item_price"), 4, "Nails",0.1);
            execDML(connection,insertInto("testing","orders", "order_id", "order_no", "order_date", "shipped", "person_id"), 1,1, new Date(),false,1);
            execDML(connection,insertInto("testing","orders", "order_id", "order_no", "order_date", "shipped", "person_id"), 2,2, new Date(),false,2);
            execDML(connection,insertInto("testing","orders", "order_id", "order_no", "order_date", "shipped", "person_id"), 3,3, new Date(),false,1);
            execDML(connection,insertInto("testing","orders", "order_id", "order_no", "order_date", "shipped", "person_id"), 4,4, new Date(),false,2);
            execDML(connection,insertInto("testing","orders", "order_id", "order_no", "order_date", "shipped", "person_id"), 5,5, new Date(),false,1);
            execDML(connection,insertInto("testing","order_items", "order_id", "item_id", "qty"), 1,1,1);
            execDML(connection,insertInto("testing","order_items", "order_id", "item_id", "qty"), 1,2,1);
            execDML(connection,insertInto("testing","order_items", "order_id", "item_id", "qty"), 1,3,1);
            execDML(connection,insertInto("testing","order_items", "order_id", "item_id", "qty"), 2,1,2);
            execDML(connection,insertInto("testing","order_items", "order_id", "item_id", "qty"), 2,2,2);
            execDML(connection,insertInto("testing","order_items", "order_id", "item_id", "qty"), 2,3,2);
            execDML(connection,insertInto("testing","order_items", "order_id", "item_id", "qty"), 3,4,2);
            execDML(connection,insertInto("testing","order_items", "order_id", "item_id", "qty"), 3,3,2);
            execDML(connection,insertInto("testing","order_items", "order_id", "item_id", "qty"), 3,2,2);
            execDML(connection,insertInto("testing","order_items", "order_id", "item_id", "qty"), 4,4,2);
            execDML(connection,insertInto("testing","order_items", "order_id", "item_id", "qty"), 4,3,2);
            execDML(connection,insertInto("testing","order_items", "order_id", "item_id", "qty"), 4,2,2);
            execDML(connection,insertInto("testing","order_items", "order_id", "item_id", "qty"), 5,4,2);
            execDML(connection,insertInto("testing","order_items", "order_id", "item_id", "qty"), 5,3,2);
            execDML(connection,insertInto("testing","order_items", "order_id", "item_id", "qty"), 5,2,2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void beforeTest() throws Exception {
        connection = DATA_SOURCE.getConnection();
    }

    @After
    public void afterTest() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }


}