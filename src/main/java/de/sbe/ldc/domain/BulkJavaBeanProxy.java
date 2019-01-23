/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.JavaBean;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.List;

public class BulkJavaBeanProxy
extends AbstractBean
implements InvocationHandler {
    public static final String GETTER_PATTERN = "^(get|is).+$";
    public static final String PROPERTY_CHANGE_LISTENER_PATTERN = "^(add|remove)PropertyChangeListener$";
    private static final long serialVersionUID = 1708924101352627039L;
    public static final String SETTER_PATTERN = "^set.+$";
    private final List<? extends JavaBean> beans;

    public static <T extends JavaBean> T getProxy(Class<T> _interface, List<? extends T> _beans) {
        return (T)((JavaBean)Proxy.newProxyInstance(_interface.getClassLoader(), new Class[]{_interface}, new BulkJavaBeanProxy(_beans)));
    }

    private BulkJavaBeanProxy(List<? extends JavaBean> _beans) {
        this.beans = _beans;
    }

    void forwardChange(PropertyChangeEvent _evt) {
        this.firePropertyChange(_evt);
    }

    @Override
    public Object invoke(Object _proxy, Method _method, Object[] _args) throws Throwable {
        if (_method.getName().matches(GETTER_PATTERN)) {
            Class<?> returnType;
            Object referenceValue;
            if (this.beans.size() == 1) {
                return _method.invoke(this.beans.get(0), _args);
            }
            Iterator<? extends JavaBean> it = this.beans.iterator();
            if (it.hasNext() && (referenceValue = _method.invoke(it.next(), _args)) != null) {
                boolean equals = true;
                while (it.hasNext() && (equals &= referenceValue.equals(_method.invoke(it.next(), _args)))) {
                }
                if (equals) {
                    return referenceValue;
                }
            }
            if ((returnType = _method.getReturnType()).isPrimitive()) {
                if (Boolean.TYPE.equals(returnType)) {
                    return Boolean.FALSE;
                }
                if (Byte.TYPE.equals(returnType)) {
                    return (byte)-128;
                }
                if (Character.TYPE.equals(returnType)) {
                    return Character.valueOf('\u0000');
                }
                if (Double.TYPE.equals(returnType)) {
                    return Double.MIN_VALUE;
                }
                if (Float.TYPE.equals(returnType)) {
                    return Float.valueOf(Float.MIN_VALUE);
                }
                if (Integer.TYPE.equals(returnType)) {
                    return Integer.MIN_VALUE;
                }
                if (Long.TYPE.equals(returnType)) {
                    return Long.MIN_VALUE;
                }
                if (Short.TYPE.equals(returnType)) {
                    return (short)-32768;
                }
                if (Void.TYPE.equals(returnType)) {
                    return null;
                }
            }
            return null;
        }
        if (_method.getName().matches(SETTER_PATTERN)) {
            for (JavaBean bean : this.beans) {
                _method.invoke(bean, _args);
            }
            return null;
        }
        if (_method.getName().matches(PROPERTY_CHANGE_LISTENER_PATTERN)) {
            _method.invoke(this, _args);
            for (JavaBean bean : this.beans) {
                _method.invoke(bean, _args);
            }
            return null;
        }
        return _method.invoke(this, _args);
    }
}

