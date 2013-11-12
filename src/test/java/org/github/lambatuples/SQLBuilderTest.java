package org.github.lambatuples;

import org.junit.Test;

import static org.github.lambatuples.Pair.cons;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: julian3
 * Date: 2013/11/10
 * Time: 7:17 PM
 * PROJECT: ${PROJECT}
 * DESCRIPTION:
 */

public class SQLBuilderTest {


    @Test
    public void testInsert() throws Exception {
        String insert = SQLBuilder.insertInto(null, "testing", "one", "two");
        assertEquals("insert into testing (one,two) values (?,?);", insert.toLowerCase());
        insert = SQLBuilder.insertInto("test", "testing", "one", "two");
        assertEquals("insert into test.testing (one,two) values (?,?);", insert.toLowerCase());
    }


    @Test
    public void testUpdate() throws Exception {
        String update = SQLBuilder.update(null, "testing", "one", "one", "two");
        assertEquals("update testing set one=?,two=? where one=?;",update.toLowerCase());
        update = SQLBuilder.update("test", "testing", "one", "one", "two");
        assertEquals("update test.testing set one=?,two=? where one=?;",update.toLowerCase());
    }

    @Test
    public void testCreateTable() throws Exception {
        String create = SQLBuilder.createTable(null, "qwerty", cons("one", cons("varchar(5)", true)), cons("two", cons("varchar(5)", false)));
        assertEquals("create table qwerty(\none varchar(5) not null,two varchar(5));",create.toLowerCase());
    }
}
