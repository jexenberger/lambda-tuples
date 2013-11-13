package org.github.lambatuples;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: julian3
 * Date: 2013/11/13
 * Time: 10:09 AM
 * PROJECT: ${PROJECT}
 * DESCRIPTION:
 */
public class ConverterServiceTest {


    @Test
    public void testTransform() throws Exception {
        assertEquals("1",ConverterService.convert(1, String.class));
        assertEquals(new Double(1.0),ConverterService.convert(1, Double.class));
        assertEquals(new Float(1.0),ConverterService.convert(1, Float.class));
        assertEquals(new Short((short)1),ConverterService.convert(1, Short.class));
        assertEquals(new Byte((byte)1),ConverterService.convert(1, Byte.class));
        assertEquals("1",ConverterService.convert(1, String.class));
        assertEquals("1.0",ConverterService.convert(1.0, String.class));
        assertEquals("1.0",ConverterService.convert(1.0f, String.class));
        assertEquals("1",ConverterService.convert(1, String.class));
        assertEquals(true,ConverterService.convert(1, Boolean.class));
        assertEquals(false,ConverterService.convert(0, Boolean.class));
        assertEquals(true,ConverterService.convert('1', Boolean.class));
        assertEquals(false,ConverterService.convert('0', Boolean.class));
        assertEquals(true,ConverterService.convert('y', Boolean.class));
        assertEquals(false,ConverterService.convert('n', Boolean.class));
        assertEquals(true,ConverterService.convert("yes", Boolean.class));
        assertEquals(false,ConverterService.convert("no", Boolean.class));
    }

}
