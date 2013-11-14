package org.github.lambatuples;

import org.junit.Test;

import java.util.stream.Collectors;

import static org.github.lambatuples.SQL.select;
import static org.github.lambatuples.SQL.stream;

/**
 * Created with IntelliJ IDEA.
 * User: julian3
 * Date: 2013/11/14
 * Time: 10:25 AM
 * PROJECT: ${PROJECT}
 * DESCRIPTION:
 */
public class DefaultTupleSQLTest extends HSQLBootStrap {


    @Test
    public void testSelect() throws Exception {
        stream(connection, "select * from testing.persons persons,testing.orders orders where persons.person_id = orders.person_id")
                .forEach((row) -> System.out.println(row));
    }

}
