/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.repository;

import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.PropertyChangeMediator;
import de.sbe.ldc.domain.repository.AddPolicy;
import de.sbe.ldc.domain.repository.AddPolicyAdapter;
import de.sbe.ldc.domain.repository.RemovePolicy;
import de.sbe.ldc.domain.repository.RemovePolicyAdapter;
import de.sbe.ldc.domain.repository.RepositoryContext;
import de.sbe.utils.logging.LogUtils;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public abstract class AbstractBeanRepository<B extends JavaBean, I>
extends AbstractBean {
    public static final String PROPERTYNAME_BEAN_ADDED = "added";
    public static final String PROPERTYNAME_BEAN_REMOVED = "removed";
    public static final String PROPERTYNAME_CLEARED = "cleared";
    public static final String PROPERTYNAME_LOADED = "loaded";
    private static final long serialVersionUID = 1L;
    private final Class<? extends B> beanClass;
    private Set<B> beans;
    private Map<Comparator<B>, List<B>> cache;
    private final RepositoryContext context;
    private final Class<? extends I> idClass;
    private Map<I, B> idMapping;
    private boolean loaded;
    protected final Logger logger = LogUtils.getLogger(this.getClass());

    protected AbstractBeanRepository(RepositoryContext _context, Class<? extends B> _beanClass, Class<? extends I> _idClass) {
        this.context = _context;
        this.beanClass = _beanClass;
        this.idClass = _idClass;
        this.initialize();
        this.bind();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final boolean add(B _bean) {
        boolean added;
        added = false;
        this.getLock().lock();
        try {
            if (this.contains(_bean)) {
                boolean bl = false;
                return bl;
            }
            added = this.beans.add(_bean);
            if (added) {
                this.idMapping.put(this.getId(_bean), _bean);
                this.cache.clear();
                if (this.getAddPolicy() != null) {
                    this.getAddPolicy().add(_bean);
                }
                this.firePropertyChange(PROPERTYNAME_BEAN_ADDED, null, _bean);
            }
        }
        finally {
            this.getLock().unlock();
        }
        return added;
    }

    public final boolean add(List<B> _beans) {
        boolean added = true;
        for (JavaBean obj : _beans) {
            added &= this.add((B) obj);
        }
        return added;
    }

    private void bind() {
        this.addPropertyChangeListener((PropertyChangeListener)this.context.getPropertyChangeMediator());
    }

    public final void clear() {
        this.getLock().lock();
        try {
            List<B> old = this.getBeans();
            this.beans.clear();
            this.idMapping.clear();
            this.cache.clear();
            this.setLoaded(false);
            this.firePropertyChange(PROPERTYNAME_CLEARED, old, null);
        }
        finally {
            this.getLock().unlock();
        }
    }

    public final boolean contains(B _b) {
        boolean contains;
        contains = false;
        this.getLock().lock();
        try {
            contains = this.beans.contains(_b) && this.idMapping.containsKey(this.getId(_b));
        }
        finally {
            this.getLock().unlock();
        }
        return contains;
    }

    protected void finalize() throws Throwable {
        this.removePropertyChangeListener((PropertyChangeListener)this.context.getPropertyChangeMediator());
        super.finalize();
    }

    protected AddPolicy<B, I> getAddPolicy() {
        return new AddPolicyAdapter(this);
    }

    public Class<? extends B> getBeanClass() {
        return this.beanClass;
    }

    public final List<B> getBeans() {
        ArrayList<B> list;
        list = null;
        this.getLock().lock();
        try {
            list = Collections.list(Collections.enumeration(this.beans));
        }
        finally {
            this.getLock().unlock();
        }
        return list;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final List<B> getBeans(Comparator<B> _comparator) {
        List<B> list;
        list = null;
        this.getLock().lock();
        try {
            if (!this.cache.containsKey(_comparator)) {
                List<B> copy = this.getBeans();
                Collections.sort(copy, _comparator);
                this.cache.put(_comparator, Collections.unmodifiableList(copy));
            }
            list = this.cache.get(_comparator);
        }
        finally {
            this.getLock().unlock();
        }
        return list;
    }

    public final B getById(I _id) {
        this.getLock().lock();
        try {
            JavaBean javaBean = (JavaBean)this.idMapping.get(_id);
            return (B)javaBean;
        }
        finally {
            this.getLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final List<B> getById(List<I> _ids) {
        ArrayList<B> list;
        list = new ArrayList<B>(_ids.size());
        this.getLock().lock();
        try {
            for (I id : _ids) {
                B instance = this.getById(id);
                if (instance == null) continue;
                list.add(instance);
            }
        }
        finally {
            this.getLock().unlock();
        }
        return list.isEmpty() ? null : list;
    }

    public B getByIdOrInstantiate(I _id) {
        B bean;
        bean = null;
        this.getLock().lock();
        try {
            bean = this.getById(_id);
            if (bean == null) {
                bean = this.newInstance(_id);
                this.add(bean);
            }
        }
        finally {
            this.getLock().unlock();
        }
        return bean;
    }

    public RepositoryContext getContext() {
        return this.context;
    }

    protected abstract I getId(B var1);

    public Class<? extends I> getIdClass() {
        return this.idClass;
    }

    public abstract String getIdName();

    protected RemovePolicy<B, I> getRemovePolicy() {
        return new RemovePolicyAdapter<B, I>(this);
    }

    private void initialize() {
        this.beans = new HashSet<B>();
        this.cache = new HashMap<Comparator<B>, List<B>>();
        this.idMapping = new HashMap<I, B>();
    }

    public final boolean isEmpty() {
        this.getLock().lock();
        try {
            boolean bl = this.beans.isEmpty();
            return bl;
        }
        finally {
            this.getLock().unlock();
        }
    }

    public final boolean isLoaded() {
        return this.loaded;
    }

    private B newInstance(I _id) {
        JavaBean instance = null;
        try {
            instance = (JavaBean)this.beanClass.newInstance();
            this.setId((B) instance, _id);
        }
        catch (InstantiationException _ie) {
            throw new InternalError(_ie.getLocalizedMessage());
        }
        catch (IllegalAccessException _iae) {
            throw new InternalError(_iae.getLocalizedMessage());
        }
        return (B)instance;
    }

    public final boolean remove(B _bean) {
        boolean removed;
        removed = false;
        this.getLock().lock();
        try {
            removed = this.beans.remove(_bean);
            if (removed) {
                this.idMapping.remove(this.getId(_bean));
                this.cache.clear();
                if (this.getRemovePolicy() != null) {
                    this.getRemovePolicy().remove(_bean);
                }
                this.firePropertyChange("removed", _bean, null);
            }
        }
        finally {
            this.getLock().unlock();
        }
        return removed;
    }

    public final boolean remove(List<B> _beans) {
        boolean removed = true;
        for (JavaBean bean : _beans) {
            removed &= this.remove((B) bean);
        }
        return removed;
    }

    protected abstract void setId(B var1, I var2);

    public final void setLoaded(boolean _loaded) {
        boolean old = this.loaded;
        this.loaded = _loaded;
        this.firePropertyChange("loaded", old, this.loaded);
    }
}

