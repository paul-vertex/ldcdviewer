/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.UrlFilterEntry;
import java.util.List;

public class UrlFilterEntries
extends AbstractBean {
    private static final long serialVersionUID = -6485028343752839392L;
    private List<UrlFilterEntry> blacklist;
    private boolean filtered;
    private List<UrlFilterEntry> whitelist;

    public List<UrlFilterEntry> getBlacklist() {
        return this.blacklist;
    }

    public List<UrlFilterEntry> getWhitelist() {
        return this.whitelist;
    }

    public boolean isFiltered() {
        return this.filtered;
    }

    public void setBlacklist(List<UrlFilterEntry> _blacklist) {
        this.blacklist = _blacklist;
    }

    public void setFiltered(boolean _filtered) {
        this.filtered = _filtered;
    }

    public void setWhitelist(List<UrlFilterEntry> _whitelist) {
        this.whitelist = _whitelist;
    }
}

