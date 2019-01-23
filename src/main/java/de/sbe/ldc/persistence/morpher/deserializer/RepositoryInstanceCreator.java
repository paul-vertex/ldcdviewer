/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.InstanceCreator
 */
package de.sbe.ldc.persistence.morpher.deserializer;

import com.google.gson.InstanceCreator;
import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.repository.AbstractBeanRepository;
import java.lang.reflect.Type;

public class RepositoryInstanceCreator<B extends JavaBean, I>
implements InstanceCreator<B> {
    private I nextId;
    private final AbstractBeanRepository<B, I> repo;

    public RepositoryInstanceCreator(AbstractBeanRepository<B, I> _repo) {
        this.repo = _repo;
    }

    public B createInstance(Type _type) {
        try {
            if (this.nextId == null) {
                System.out.println("RIC: " + "logging.persistence.sync.instance_creator.missing_id");
            }
            B b = this.repo.getByIdOrInstantiate(this.nextId);
            return b;
        }
        finally {
            this.setNextId(null);
        }
    }

    public void setNextId(I _nextId) {
        this.nextId = _nextId;
    }
}

