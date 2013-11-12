package org.github.lambatuples;

/**
 * Created with IntelliJ IDEA.
 * User: julian3
 * Date: 2013/11/10
 * Time: 7:14 PM
 * PROJECT: ${PROJECT}
 * DESCRIPTION:
 */

import java.sql.ResultSet;
import java.sql.SQLException;


import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: julian3
 * Date: 2013/09/02
 * Time: 10:06 AM
 * PROJECT: ${PROJECT}
 * DESCRIPTION:
 */
@FunctionalInterface
public interface ResultSetProcessor {

    public void process(ResultSet resultSet, long currentRow) throws SQLException;

}
