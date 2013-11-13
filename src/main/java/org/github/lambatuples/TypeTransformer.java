package org.github.lambatuples;

/**
 * Created with IntelliJ IDEA.
 * User: julian3
 * Date: 2013/11/13
 * Time: 9:42 AM
 * PROJECT: ${PROJECT}
 * DESCRIPTION:
 */
@FunctionalInterface
public interface TypeTransformer<T,K> {

    K transform(T instance);

}
