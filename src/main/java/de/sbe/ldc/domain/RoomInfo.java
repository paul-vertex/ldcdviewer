/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.Beam;
import de.sbe.ldc.domain.Beamer;
import de.sbe.ldc.domain.Exam;
import de.sbe.ldc.domain.HostFlags;

public class RoomInfo
extends HostFlags
implements Beamer {
    public static final String PROPERTYNAME_BEAM = "beam";
    public static final String PROPERTYNAME_EXAM = "exam";
    public static final String PROPERTYNAME_LAYOUT_MODIFICATION_IN_PROGRESS = "layoutModificationInProgress";
    private static final long serialVersionUID = 498542462100856953L;
    private Beam beam;
    private Exam exam;
    private transient boolean layoutModificationInProgress;

    @Override
    public Beam getBeam() {
        return this.beam;
    }

    public Exam getExam() {
        return this.exam;
    }

    public boolean isLayoutModificationInProgress() {
        return this.layoutModificationInProgress;
    }

    @Override
    public void setBeam(Beam _beam) {
        this.beam = _beam;
        this.firePropertyChange(PROPERTYNAME_BEAM, (Object)this.beam, (Object)this.beam);
    }

    public void setExam(Exam _exam) {
        Exam old = this.exam;
        this.exam = _exam;
        this.firePropertyChange(PROPERTYNAME_EXAM, (Object)old, (Object)this.exam);
    }

    public void setLayoutModificationInProgress(boolean _layoutChanged) {
        boolean old = this.layoutModificationInProgress;
        this.layoutModificationInProgress = _layoutChanged;
        this.firePropertyChange(PROPERTYNAME_LAYOUT_MODIFICATION_IN_PROGRESS, old, this.layoutModificationInProgress);
    }
}

