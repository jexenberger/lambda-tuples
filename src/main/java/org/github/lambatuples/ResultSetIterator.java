package org.github.lambatuples;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: julian3
 * Date: 2013/11/10
 * Time: 8:59 PM
 * PROJECT: ${PROJECT}
 * DESCRIPTION:
 */
public class ResultSetIterator implements Iterator<Tuple> {

    private ResultSet rs;
    private PreparedStatement ps;
    private Connection connection;
    private String sql;

    public ResultSetIterator(Connection connection, String sql) {
        assert connection != null;
        assert sql != null;
        this.connection = connection;
        this.sql = sql;
    }

    public void init() {
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public boolean hasNext() {
        if (ps == null) {
            init();
        }
        try {
            boolean hasMore = rs.next();
            if (!hasMore) {
                close();
            }
            return hasMore;
        } catch (SQLException e) {
            close();
            throw new DataAccessException(e);
        }

    }

    private void close() {
        try {
            rs.close();
            try {
                ps.close();
            } catch (SQLException e) {
                //nothing we can do here
            }
        } catch (SQLException e) {
            //nothing we can do here
        }
    }

    @Override
    public Tuple next() {
        return SQL.rowAsTuple(sql, rs);
    }
}
