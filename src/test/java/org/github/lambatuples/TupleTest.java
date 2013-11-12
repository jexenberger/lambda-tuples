package org.github.lambatuples;

/**
 * Created with IntelliJ IDEA.
 * User: julian3
 * Date: 2013/11/10
 * Time: 7:07 PM
 * PROJECT: ${PROJECT}
 * DESCRIPTION:
 */

import org.junit.Test;

import java.util.Optional;

import static org.github.lambatuples.Pair.cons;
import static org.github.lambatuples.Tuple.tuple;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TupleTest {


    @Test
    public void testConstructor() throws Exception {
        new Tuple(cons("one","hello"), cons("two","world"));
        tuple(cons("one", "hello"), cons("two", "world"));
        //nothing to assert
    }

    @Test
    public void testValWithType() throws Exception {
        Tuple tuple = new Tuple(cons("one", "hello"), cons("two", "world"));
        Optional<String> s = tuple.val("one", String.class);
        assertTrue(s.isPresent());
        assertEquals("hello", s.get());
    }

    @Test
    public void testValWithNoType() throws Exception {
        Tuple tuple = new Tuple(cons("one", "hello"), cons("two", "world"));
        Optional<String> s = tuple.val("two");
        assertTrue(s.isPresent());
        assertEquals("world", s.get());
    }


    @Test
    public void testToString() throws Exception {
        Tuple tuple = new Tuple(cons("one", "hello"), cons("two", "world"));
        System.out.println(tuple.toString());
    }

}
