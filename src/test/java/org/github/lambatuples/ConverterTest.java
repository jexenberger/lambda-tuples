package org.github.lambatuples;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: julian3
 * Date: 2013/11/13
 * Time: 7:21 AM
 * PROJECT: ${PROJECT}
 * DESCRIPTION:
 */
public class ConverterTest {


    @Test
    public void testConvertToBoolean() throws Exception {
        boolean result = Converters.convertToBoolean("test", Optional.of((Object) true));
        assertTrue("boolean true failed", result);

        result = Converters.convertToBoolean("test", Optional.of((Object) 'y'));
        assertTrue("char true failed", result);
        result = Converters.convertToBoolean("test", Optional.of((Object) 'Y'));
        assertTrue("char true failed", result);
        result = Converters.convertToBoolean("test", Optional.of((Object) "yes"));
        assertTrue("string true failed", result);
        result = Converters.convertToBoolean("test", Optional.of((Object) "YES"));
        assertTrue("string true failed", result);
        result = Converters.convertToBoolean("test", Optional.of((Object) 1));
        assertTrue("integer true failed", result);
        result = Converters.convertToBoolean("test", Optional.of((Object) 1L));
        assertTrue("long true failed", result);

        result = Converters.convertToBoolean("test", Optional.of((Object) false));
        assertFalse("boolean false failed", result);
        result = Converters.convertToBoolean("test", Optional.of((Object) 'n'));
        assertFalse("char false failed", result);
        result = Converters.convertToBoolean("test", Optional.of((Object) "no"));
        assertFalse("string false failed", result);
        result = Converters.convertToBoolean("test", Optional.of((Object) 0));
        assertFalse("int false failed", result);
        result = Converters.convertToBoolean("test", Optional.of((Object) 0L));
        assertFalse("long false failed", result);


    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidType() throws Exception {
        boolean result = Converters.convertToBoolean("test", Optional.of((Object) new ArrayList<>()));
    }
    
}
