/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.morpher;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface RpcScope {
    public String scope() default "";
}

