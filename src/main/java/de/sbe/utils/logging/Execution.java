/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.JoinPoint
 *  org.aspectj.lang.Signature
 *  org.aspectj.lang.annotation.After
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Before
 */
package de.sbe.utils.logging;

import de.sbe.utils.logging.LogUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface Execution {

    @Aspect(value="percflow(execution(@Execution * * (..)))")
    public static class Logging {
        @After(value="execution(@Execution * * (..))")
        public void after(JoinPoint _jp) {
            LogUtils.getLogger(Execution.class).exiting(_jp.getSignature().getDeclaringTypeName(), _jp.getSignature().getName());
        }

        @Before(value="execution(@Execution * * (..))")
        public void before(JoinPoint _jp) {
            LogUtils.getLogger(Execution.class).entering(_jp.getSignature().getDeclaringTypeName(), _jp.getSignature().getName());
        }
    }

}

