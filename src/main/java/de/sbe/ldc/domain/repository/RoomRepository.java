/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.repository;

import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.Room;
import de.sbe.ldc.domain.RoomImpl;
import de.sbe.ldc.domain.repository.AbstractBeanRepository;
import de.sbe.ldc.domain.repository.RepositoryContext;

public final class RoomRepository
extends AbstractBeanRepository<Room, String> {
    private static final long serialVersionUID = -6085171536136554615L;

    RoomRepository(RepositoryContext _context) {
        super(_context, RoomImpl.class, String.class);
    }

    @Override
    protected String getId(Room _instance) {
        return _instance.getCn();
    }

    @Override
    public String getIdName() {
        return "cn";
    }

    @Override
    protected void setId(Room _instance, String _id) {
        _instance.setCn(_id);
    }
}

