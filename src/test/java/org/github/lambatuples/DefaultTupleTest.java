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

import java.math.BigDecimal;
import java.util.Optional;

import static org.github.lambatuples.Converters.obj;
import static org.github.lambatuples.DefaultTuple.tCons;
import static org.github.lambatuples.Pair.cons;
import static org.github.lambatuples.DefaultTuple.tuple;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DefaultTupleTest {


    @Test
    public void testConstructor() throws Exception {
        new DefaultTuple(tCons("one", "hello"), tCons("two",  "world"));
        tuple(tCons("one", "hello"), tCons("two", "world"));
        //nothing to assert
    }

    @Test
    public void testValWithType() throws Exception {
        DefaultTuple tuple = new DefaultTuple(tCons("one", "hello"), tCons("two", "world"));
        Optional<String> s = tuple.val("one", String.class);
        assertTrue(s.isPresent());
        assertEquals("hello", s.get());
    }

    @Test
    public void testValWithNoType() throws Exception {
        DefaultTuple tuple = new DefaultTuple(tCons("one", "hello"), tCons("two", "world"));
        Optional<String> s = tuple.val("two");
        assertTrue(s.isPresent());
        assertEquals("world", s.get());
    }


    @Test
    public void testToString() throws Exception {
        DefaultTuple tuple = new DefaultTuple(tCons("one", "hello"), tCons("two", "world"));
        System.out.println(tuple.toString());
    }

    @Test
    public void testAsInt() throws Exception {
        DefaultTuple tuple = new DefaultTuple(tCons("one", "1"));
        assertEquals(1, tuple.asInt("one"));
        assertEquals(1, tuple.asInt(0));
    }

    @Test
    public void testAsBigDecimal() throws Exception {
        DefaultTuple tuple = new DefaultTuple(tCons("one", "1"));
        assertEquals(new BigDecimal("1"), tuple.asBigDecimal("one"));
        assertEquals(new BigDecimal("1"), tuple.asBigDecimal(0));
    }

    @Test
    public void testAsFloat() throws Exception {
        DefaultTuple tuple = new DefaultTuple(tCons("one", "1.0"));
        assertEquals(1.0, tuple.asFloat("one"),0.0);
        assertEquals(1.0, tuple.asFloat(0), 0.0);
    }

    @Test
    public void testAsDouble() throws Exception {
        DefaultTuple tuple = new DefaultTuple(tCons("one", "1.0"));
        assertEquals(1.0, tuple.asDouble("one"),0.0);
        assertEquals(1.0, tuple.asDouble(0), 0.0);
    }

    @Test
    public void testAsChar() throws Exception {
        DefaultTuple tuple = new DefaultTuple(tCons("one", "a"));
        assertEquals('a', tuple.asChar("one"));
        assertEquals('a', tuple.asChar(0));
    }

    @Test
    public void testAsShort() throws Exception {
        DefaultTuple tuple = new DefaultTuple(tCons("one", "1"));
        assertEquals(1, tuple.asShort("one"));
        assertEquals(1, tuple.asShort(0));
    }

    @Test
    public void testAsByte() throws Exception {
        DefaultTuple tuple = new DefaultTuple(tCons("one", "1"));
        assertEquals(1, tuple.asByte("one"));
        assertEquals(1, tuple.asByte(0));
    }

    @Test
    public void testAsBoolean() throws Exception {
        DefaultTuple tuple = new DefaultTuple(tCons("one", "yes"),tCons("two", 'y'), tCons("three",true));
        assertEquals(true, tuple.asBoolean("one"));
        assertEquals(true, tuple.asBoolean(0));
        assertEquals(true, tuple.asBoolean("two"));
        assertEquals(true, tuple.asBoolean(1));
        assertEquals(true, tuple.asBoolean("three"));
        assertEquals(true, tuple.asBoolean(2));
    }

    @Test
    public void testSubSet() throws Exception {
        DefaultTuple tuple = new DefaultTuple(tCons("one", "yes"),tCons("two", 'y'), tCons("three",true), tCons("four","test"));
        DefaultTuple subTuple = tuple.subTuple("two","three");
        assertTrue(subTuple.val("two").isPresent());
        assertTrue(subTuple.val("three").isPresent());
        assertFalse(subTuple.val("one").isPresent());
        assertFalse(subTuple.val("four").isPresent());
    }


}
