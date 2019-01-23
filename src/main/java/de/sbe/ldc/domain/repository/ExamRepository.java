/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.repository;

import de.sbe.ldc.domain.Exam;
import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.repository.AbstractBeanRepository;
import de.sbe.ldc.domain.repository.RepositoryContext;

public final class ExamRepository
extends AbstractBeanRepository<Exam, String> {
    private static final long serialVersionUID = 7375954021455040471L;

    ExamRepository(RepositoryContext _context) {
        super(_context, Exam.class, String.class);
    }

    @Override
    protected String getId(Exam _instance) {
        return _instance.getId();
    }

    @Override
    public String getIdName() {
        return "id";
    }

    @Override
    protected void setId(Exam _instance, String _id) {
        _instance.setId(_id);
    }
}

