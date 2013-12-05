package org.github.lambatuples;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: julian3
 * Date: 2013/12/05
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
@FunctionalInterface
public interface SQLAction<T> {


    public T exec(Connection conn) throws SQLException;
}
