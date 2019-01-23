/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.ssm;

import de.sbe.ldc.domain.rpc.Impersonator;
import de.sbe.ldc.domain.ssm.SsmAction;
import de.sbe.ldc.persistence.morpher.RpcScope;
import de.sbe.ldc.persistence.morpher.SerializableProperty;
import java.util.Collection;

public class SsmCloseAction
extends SsmAction {
    public static final String PROPERTYNAME_RECEIVER = "receiver";
    private static final long serialVersionUID = 1L;
    @SerializableProperty
    @RpcScope(scope="default")
    private Collection<Impersonator> receiver;

    public Collection<Impersonator> getReceiver() {
        return this.receiver;
    }

    public void setReceiver(Collection<Impersonator> _receiver) {
        this.receiver = _receiver;
        this.firePropertyChange(PROPERTYNAME_RECEIVER, this.receiver, this.receiver);
    }
}

