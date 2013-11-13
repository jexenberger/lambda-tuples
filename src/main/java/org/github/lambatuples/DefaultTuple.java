package org.github.lambatuples;

import java.math.BigDecimal;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.github.lambatuples.ConverterService.convert;
import static org.github.lambatuples.Pair.cons;

/**
 * Created with IntelliJ IDEA.
 * User: julian3
 * Date: 2013/11/10
 * Time: 7:05 PM
 * PROJECT: ${PROJECT}
 * DESCRIPTION:
 */
public class DefaultTuple extends AbstractList<Pair<String, Optional<? extends Object>>> {

    //note the optional is untyped, this honestly makes working with tuple as a whole easier than using wildcards
    Pair<String, Optional<? extends Object>>[] list;


    public DefaultTuple(Pair<String, Optional<? extends Object>>... list) {
        this.list = list;
    }

    public DefaultTuple(Collection<Pair<String, Optional<?>>> values) {
        this.list = new Pair[values.size()];
        int cnt = 0;
        for (Pair<String, Optional<?>> value : values) {
            this.list[cnt++] = value;
        }
    }


    public static DefaultTuple tuple(Pair<String, Optional<? extends Object>>... list) {
        return new DefaultTuple(list);
    }


    public <T> Optional<T> val(String name) {
        return (Optional<T>) val(name, Object.class);
    }

    public <T> Optional<T> val(int index) {
        assert index > -1 && index < list.length;
        return (Optional<T>) val(index, Object.class);
    }

    public <T> Optional<T> val(int index, Class<T> type) {
        assert index > -1 && index < list.length;
        return (Optional<T>) list[index].getCdr();
    }


    public long asLong(String name) {
        return val(name).map((val) -> convert(val, Long.class)).orElse(0L);
    }

    public float asFloat(String name) {
        return val(name).map((val) -> convert(val, Float.class)).orElse(0.0f);
    }

    public double asDouble(String name) {
        return val(name).map((val) -> convert(val, Double.class)).orElse(0.0);
    }

    public BigDecimal asBigDecimal(String name) {
        return val(name).map((val) -> convert(val, BigDecimal.class)).orElse(null);
    }

    public short asShort(String name) {
        return val(name).map((val) -> convert(val, Short.class)).orElse((short) 0);
    }

    public byte asByte(String name) {
        return val(name).map((val) -> convert(val, Byte.class)).orElse((byte) 0);
    }

    public boolean asBoolean(String name) {
        return val(name).map((val) -> convert(val, Boolean.class)).orElse(false);
    }

    public char asChar(String name) {
        return val(name).map((val) -> convert(val, Character.class)).orElse((char) 0);
    }


    public int asInt(String name) {
        return val(name).map((val) -> convert(val, Integer.class)).orElse(0);
    }

    public int asInt(int index) {
        return val(index).map((val) -> convert(val, Integer.class)).orElse(0);
    }

    public float asFloat(int index) {
        return val(index).map((val) -> convert(val, Float.class)).orElse(0.0f);
    }

    public double asDouble(int index) {
        return val(index).map((val) -> convert(val, Double.class)).orElse(0.0);
    }

    public BigDecimal asBigDecimal(int index) {
        return val(index).map((val) -> convert(val, BigDecimal.class)).orElse(null);
    }

    public long asLong(int index) {
        return (long) val(index).map((val) -> convert(val, Long.class)).orElse(0L);
    }

    public short asShort(int index) {
        return (short) val(index).map((val) -> convert(val, Short.class)).orElse((short) 0);
    }

    public byte asByte(int index) {
        return (byte) val(index).map((val) -> convert(val, Byte.class)).orElse((byte) 0);
    }

    public boolean asBoolean(int index) {
        return (boolean) val(index).map((val) -> convert(val, Boolean.class)).orElse(false);
    }

    public char asChar(int index) {
        return (char) val(index).map((val) -> convert(val, Character.class)).orElse((char) 0);
    }

    public <T> Optional<T> val(String name, Class<T> type) {
        for (Pair<String, ?> stringPair : list) {
            if (name.equalsIgnoreCase(stringPair.getCar())) {
                return (Optional<T>) stringPair.getCdr();
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultTuple)) return false;
        if (!super.equals(o)) return false;

        DefaultTuple pairs = (DefaultTuple) o;

        if (!Arrays.equals(list, pairs.list)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (list != null ? Arrays.hashCode(list) : 0);
        return result;
    }

    @Override
    public Pair<String, Optional<? extends Object>> get(int index) {
        return this.list[index];
    }

    @Override
    public int size() {
        return list.length;
    }

    public static Pair<String, Optional<? extends Object>> tCons(String name, Object value) {
        return cons(name, (Optional<? extends Object>) Optional.of(value));
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("(");
        forEach((Pair<String, ?> pair) -> {
            builder.append(pair.toString()).append(',');
        });
        return builder.deleteCharAt(builder.length() - 1).append(')').toString();

    }

    public DefaultTuple subTuple(final String... subset) {
        return new DefaultTuple((Collection<Pair<String, Optional<?>>>)
                stream()
                        .filter((pair) -> {
                            for (String s : subset) {
                                if (s.equals(pair.getCar())) {
                                    return true;
                                }
                            }
                            return false;
                        })
                        .collect(Collectors.toList()));
    }
}

