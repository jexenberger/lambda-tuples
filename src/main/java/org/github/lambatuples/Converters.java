package org.github.lambatuples;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: julian3
 * Date: 2013/11/13
 * Time: 7:20 AM
 * PROJECT: ${PROJECT}
 * DESCRIPTION:
 */
public class Converters {

    static boolean convertToBoolean(String name, Optional<Object> val) {
        return (boolean) val.map(o -> {
            if ((o instanceof Boolean) || (o.getClass().isAssignableFrom(Boolean.TYPE))) {
                return o;
            } else {
                boolean isAllowedType = o instanceof Character;
                isAllowedType |= o.getClass().isAssignableFrom(Character.TYPE);
                isAllowedType |= o instanceof String;
                isAllowedType |= o instanceof Integer;
                isAllowedType |= o.getClass().isAssignableFrom(Integer.TYPE);
                isAllowedType |= o instanceof Long;
                isAllowedType |= o.getClass().isAssignableFrom(Long.TYPE);

                if (!isAllowedType) {
                    throw new IllegalArgumentException("name cannot be converted to boolean as it is a " + val.get().getClass());
                }

                return o.toString().equalsIgnoreCase("1") ||
                        o.toString().equalsIgnoreCase("yes") ||
                        o.toString().equalsIgnoreCase("true") ||
                        o.toString().equalsIgnoreCase("y");

            }
        }).orElse(false);
    }

    public static <T extends Number> T toNumber(Number number, Class<T> target) {
        if (number == null) {
            return null;
        }
        try {
            return target.getConstructor(String.class).newInstance(number.toString());
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public static BigDecimal toBigDecimal(Number number) {
        if (number == null) {
            return null;
        }
        return new BigDecimal(number.toString());
    }

    public static Boolean toBoolean(String o) {
        if (o == null) {
            return false;
        }

        return o.toString().equalsIgnoreCase("1") ||
                o.toString().equalsIgnoreCase("yes") ||
                o.toString().equalsIgnoreCase("true") ||
                o.toString().equalsIgnoreCase("y");

    }

    public static Boolean toBoolean(Character o) {
        if (o == null) {
            return false;
        }
        return o.charValue() == '1' || o.charValue() == 'y'|| o.charValue() == 'Y';
    }

    public static Boolean toBoolean(Number o) {
        if (o == null) {
            return false;
        }
        return o.intValue() == 1;
    }


    static long convertToLong(Optional<Object> value) {
        return (long) value.map(o -> {
            if (o instanceof String || o instanceof Character) {
                return Long.valueOf(o.toString());
            }
            if (o instanceof Number) {
                return ((Number) o).longValue();
            }
            return o;
        }).orElse(0L);
    }

    static long convertToInteger(Optional<Object> value) {
        return (long) value.map(o -> {
            if (o instanceof String || o instanceof Character) {
                return Integer.valueOf(o.toString());
            }
            if (o instanceof Number) {
                return ((Number) o).intValue();
            }
            return o;
        }).orElse(0L);
    }

    static byte convertToByte(Optional<Object> value) {
        return (byte) value.map(o -> {
            if (o instanceof String || o instanceof Character) {
                return Byte.valueOf(o.toString());
            }
            if (o instanceof Number) {
                return ((Number) o).byteValue();
            }
            return o;
        }).orElse(0);
    }

    static short convertToShort(Optional<Object> value) {
        return (short) value.map(o -> {
            if (o instanceof String || o instanceof Character) {
                return Short.valueOf(o.toString());
            }
            if (o instanceof Number) {
                return ((Number) o).shortValue();
            }
            return o;
        }).orElse(0);
    }


    public static Object obj(Object value) {
        return value;
    }

}
