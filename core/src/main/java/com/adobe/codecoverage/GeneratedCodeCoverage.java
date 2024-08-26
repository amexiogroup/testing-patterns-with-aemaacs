package com.adobe.codecoverage;

import java.lang.annotation.*;

/**
 * Annotation to use when there should be no code coverage generated for the annotated method.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface GeneratedCodeCoverage {
    String value() default "";
}
