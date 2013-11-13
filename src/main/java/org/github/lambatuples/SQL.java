package org.github.lambatuples;

import java.sql.*;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.github.lambatuples.Pair.cons;

/**
 * Created with IntelliJ IDEA.
 * User: julian3
 * Date: 2013/11/10
 * Time: 7:14 PM
 * PROJECT: ${PROJECT}
 * DESCRIPTION:
 */
public class SQL {


    private static Map<Class<?>, Integer> MAPPINGS = new HashMap<>();
    private static Map<String, Pair<String, Integer>[]> META_DATA_CACHE = new HashMap<>();

    static {
        MAPPINGS.put(String.class, Types.VARCHAR);
        MAPPINGS.put(Integer.class, Types.NUMERIC);
    }


    public static void select(Connection connection, String sql, ResultSetProcessor processor, Object... params) {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int cnt = 0;
            for (Object param : params) {
                ps.setObject(++cnt, param, MAPPINGS.get(param.getClass()));
            }
            try (ResultSet rs = ps.executeQuery()) {
                long rowCnt = 0;
                while (rs.next()) {
                    processor.process(rs, rowCnt++);
                }
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public static void execDDL(Connection connection, String sql) {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public static int execDML(Connection connection, String sql, Object... values) {
        System.out.println(sql);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int cnt = 0;
            for (Object param : values) {
                ps.setObject(++cnt, param);
            }
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }


    }

    public static DefaultTuple rowAsTuple(String sql, ResultSet rs) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            Pair<String, Integer>[] columns = null;
            if (!META_DATA_CACHE.containsKey(sql)) {
                synchronized (META_DATA_CACHE) {
                    META_DATA_CACHE.put(sql, getColumns(metaData));
                }
            }
            columns = META_DATA_CACHE.get(sql);
            Collection result = StreamSupport.stream(Spliterators.spliterator(columns, 0), false)
                    .map((Object o) -> {
                        Pair<String, Integer> column = (Pair<String, Integer>) o;
                        try {
                            return cons(column.getCar(), Optional.of(rs.getObject(column.getCar())));
                        } catch (SQLException e) {
                            throw new DataAccessException(e);
                        }
                    }).collect( Collectors.toList());
            return new DefaultTuple(result);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }


    private static Pair<String, Integer>[] getColumns(ResultSetMetaData rs) {
        int columnCount = 0;
        try {
            columnCount = rs.getColumnCount();
            Pair<String, Integer>[] columns = new Pair[columnCount];
            for (int i = 0; i < columnCount; i++) {
                String name = rs.getColumnName(i+1);
                Integer type = rs.getColumnType(i+1);
                columns[i] = cons(name, type);

            }
            return columns;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }


    public static Stream<DefaultTuple> stream(final Connection connection, final String sql, final Object... parms) {

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new ResultSetIterator(connection, sql), 0), false);
    }


}