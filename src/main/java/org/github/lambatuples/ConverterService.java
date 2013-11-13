package org.github.lambatuples;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.github.lambatuples.Pair.cons;

/**
 * Created with IntelliJ IDEA.
 * User: julian3
 * Date: 2013/11/13
 * Time: 9:41 AM
 * PROJECT: ${PROJECT}
 * DESCRIPTION:
 */
public class ConverterService {

    static Map<Pair<Class<?>, Class<?>>, TypeTransformer<?, ?>> CONVERSION_REGISTRY;

    static {
        registerConversion(String.class, Long.class, Long::valueOf);
        registerConversion(String.class, Long.TYPE, Long::valueOf);
        registerConversion(String.class, Integer.class, Integer::valueOf);
        registerConversion(String.class, Integer.TYPE, Integer::valueOf);
        registerConversion(String.class, Byte.class, Byte::valueOf);
        registerConversion(String.class, Byte.TYPE, Byte::valueOf);
        registerConversion(String.class, Short.class, Short::valueOf);
        registerConversion(String.class, Short.TYPE, Short::valueOf);
        registerConversion(String.class, Boolean.class, Converters::toBoolean);
        registerConversion(String.class, Boolean.TYPE, Converters::toBoolean);
        registerConversion(String.class, Float.class, Float::valueOf);
        registerConversion(String.class, Float.TYPE, Float::valueOf);
        registerConversion(String.class, Double.class, Double::valueOf);
        registerConversion(String.class, Double.TYPE, Double::valueOf);
        registerConversion(String.class, Character.class, (instance)-> (instance!= null) ? instance.charAt(0) : (char)(byte)0);
        registerConversion(String.class, Character.TYPE, (instance)-> (instance!= null) ? instance.charAt(0) : (char)(byte)0);
        registerConversion(String.class, Double.TYPE, Double::valueOf);
        registerConversion(String.class, BigDecimal.class, BigDecimal::new);
        registerConversion(Number.class, Long.class, (instance)-> Converters.toNumber(instance, Long.class));
        registerConversion(Number.class, Long.TYPE, (instance)-> Converters.toNumber(instance, Long.class));
        registerConversion(Number.class, Integer.class, (instance)-> Converters.toNumber(instance, Integer.class));
        registerConversion(Number.class, Integer.TYPE, (instance)-> Converters.toNumber(instance, Integer.class));
        registerConversion(Number.class, Byte.class, (instance)-> Converters.toNumber(instance, Byte.class));
        registerConversion(Number.class, Byte.TYPE, (instance)-> Converters.toNumber(instance, Byte.class));
        registerConversion(Number.class, Short.class, (instance)-> Converters.toNumber(instance, Short.class));
        registerConversion(Number.class, Short.TYPE, (instance)-> Converters.toNumber(instance, Short.class));
        registerConversion(Number.class, Boolean.class, Converters::toBoolean);
        registerConversion(Number.class, Boolean.TYPE, Converters::toBoolean);
        registerConversion(Number.class, Float.class, (instance)-> Converters.toNumber(instance, Float.class));
        registerConversion(Number.class, Float.TYPE, (instance)-> Converters.toNumber(instance, Float.class));
        registerConversion(Number.class, Double.class, (instance)-> Converters.toNumber(instance, Double.class));
        registerConversion(Number.class, Double.TYPE, (instance)-> Converters.toNumber(instance, Double.class));
        registerConversion(Number.class, BigDecimal.class, Converters::toBigDecimal);
        registerConversion(Object.class, String.class, Object::toString);
        registerConversion(Character.class, Boolean.class, Converters::toBoolean);
        registerConversion(Number.class, Boolean.class, Converters::toBoolean);
    }


    static Map<Pair<Class<?>, Class<?>>, TypeTransformer<?, ?>> getConversionRegistry() {
        if (CONVERSION_REGISTRY == null) {
            CONVERSION_REGISTRY = new HashMap<>();
        }
        return CONVERSION_REGISTRY;
    }

    public static <T, K> void registerConversion(Class<T> source, Class<K> target, TypeTransformer<T, K> transformer) {
        getConversionRegistry().put(cons((Class<?>) source, (Class<?>) target), transformer);
    }

    static TypeTransformer resolveTransformer(boolean isNull, Class<?> source, Class<?> target) {


        TypeTransformer<?, ?> typeTransformer = findWideningTransformer(source, target);
        if (typeTransformer == null) {
            return new TypeTransformer() {
                @Override
                public Object transform(Object instance) {
                    if (target.isPrimitive()) {
                        try {
                            return target.getField("TYPE").get(null);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else if (isNull) {
                        return null;
                    } else {
                        throw new IllegalArgumentException("No Type Transformer registered for " + source.getName() + "->" + target.getName());
                    }
                }
            };
        }
        return typeTransformer;
    }

    private static TypeTransformer<?, ?> findWideningTransformer(Class<?> source, Class<?> target) {
        TypeTransformer<?, ?> typeTransformer = getConversionRegistry().get(cons(source, target));
        if (typeTransformer != null) {
            return typeTransformer;
        }

        //do a widening search
        Pair<Class<?>, Class<?>> transformerKey = getConversionRegistry()
                .keySet()
                .stream()
                .filter(key -> {
                    boolean sourceAssignable = key.getCar().isAssignableFrom(source);
                    boolean targetAssignable = key.getCdr().isAssignableFrom(target);
                    boolean fits = sourceAssignable && targetAssignable;
                    return fits;
                })
                .findFirst().get();
        return getConversionRegistry().get(transformerKey);
    }


    public static <T> T convert(Object instance, Class<T> target) {
        boolean isNull = instance == null;
        if (!isNull && (target.equals(instance.getClass()) || target.isAssignableFrom(instance.getClass()))) {
            return (T) instance;
        }

        return (T) resolveTransformer(isNull, (isNull) ? null : instance.getClass(), target).transform(instance);
    }

}
