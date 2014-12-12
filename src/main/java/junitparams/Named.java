package junitparams;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation for naming the test parameters.
 * Use it to give your parameters name that will be displayed in a test result description
 * Example: <code>
 *     public void isAdult(@Named("age") Integer age, @Named("adult")Boolean adult)
 * </code>
 *
 * @author @milipski
 */
@Retention(RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface Named {

    /**
     * Name of parameter
     */
    String value();
}
