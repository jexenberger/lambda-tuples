package org.github.lambatuples;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: julian3
 * Date: 2013/11/10
 * Time: 7:05 PM
 * PROJECT: ${PROJECT}
 * DESCRIPTION:
 */
public class Tuple extends AbstractList<Pair<String, ?>> {


    Pair<String, ?>[] list;


    public Tuple(Pair<String, ?>... list) {
        this.list = list;
    }

    public Tuple(Collection<Pair<String, ?>> values) {
        this.list = new Pair[values.size()];
        int cnt=0;
        for (Pair<String, ?> value : values) {
            this.list[cnt++] = value;
        }
    }

    public static Tuple tuple(Pair<String, ?>... list) {
        return new Tuple(list);
    }


    public <T> Optional<T> val(String name) {
        return (Optional<T>) val(name, Object.class);
    }

    public int intVal(String name) {
        return (int) val(name).orElse(0);
    }

    public <T> Optional<T> val(String name, Class<T> type) {
        for (Pair<String, ?> stringPair : list) {
            if (name.equalsIgnoreCase(stringPair.getCar())) {
                return Optional.of((T) stringPair.getCdr());
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple)) return false;
        if (!super.equals(o)) return false;

        Tuple pairs = (Tuple) o;

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
    public Pair<String, ?> get(int index) {
        return this.list[index];
    }

    @Override
    public int size() {
        return list.length;
    }



    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("(");
        forEach((Pair<String, ?> pair) -> {
            builder.append(pair.toString()).append(',');
        });
        return builder.deleteCharAt(builder.length() - 1).append(')').toString();

    }
}

