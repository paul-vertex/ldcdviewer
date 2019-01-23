/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.repository;

import de.sbe.ldc.domain.Contact;
import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.repository.AbstractBeanRepository;
import de.sbe.ldc.domain.repository.RepositoryContext;

public final class ContactRepository
extends AbstractBeanRepository<Contact, Long> {
    private static final long serialVersionUID = 3104759382593439969L;

    ContactRepository(RepositoryContext _context) {
        super(_context, Contact.class, Long.class);
    }

    @Override
    protected Long getId(Contact _instance) {
        return _instance.getId();
    }

    @Override
    public String getIdName() {
        return "id";
    }

    @Override
    protected void setId(Contact _instance, Long _id) {
        _instance.setId(_id);
    }
}

