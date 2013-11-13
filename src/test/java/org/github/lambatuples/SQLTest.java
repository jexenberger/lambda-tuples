package org.github.lambatuples;

import org.junit.Test;

import java.sql.ResultSet;
import java.util.concurrent.atomic.AtomicLong;

import static org.github.lambatuples.Pair.cons;
import static org.github.lambatuples.SQL.*;
import static org.github.lambatuples.SQLBuilder.createTable;
import static org.github.lambatuples.SQLBuilder.insertInto;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: julian3
 * Date: 2013/11/10
 * Time: 7:17 PM
 * PROJECT: ${PROJECT}
 * DESCRIPTION:
 */
public class SQLTest extends HSQLBootStrap {


    public static final String SQL = "select * from " + SCHEMA + "." + TABLE_NAME;

    @Test
    public void testExecDML() throws Exception {
        int i = execDML(connection, insertInto(SCHEMA, TABLE_NAME, ID, NAME), 999, "hello world");
        assertTrue(i > 0);
    }

    @Test
    public void testExecDDL() throws Exception {
        execDML(connection, createTable(SCHEMA, "RANDOM_TABLE", cons("one", cons("numeric(10)", true))));
    }

    @Test
    public void testSelect() throws Exception {
        final AtomicLong counter = new AtomicLong(0);
        select(connection, SQL,
                (ResultSet rs, long cnt) -> {
                    counter.incrementAndGet();
                }
        );
        assertTrue(counter.get() > 0);


    }


    @Test
    public void testStream() throws Exception {
        long result = stream(connection, SQL)
                .filter((DefaultTuple t) -> t.asInt("TEST_ID") % 2 == 0)
                .limit(100)
                .count();
        System.out.println(result);
    }

}

