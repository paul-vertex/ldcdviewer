/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.Quota;

public interface DiskQuota
extends JavaBean {
    public static final String PROPERTY_DQ_F_HARD = "_dq_f_hard";
    public static final String PROPERTY_DQ_F_SOFT = "_dq_f_soft";
    public static final String PROPERTY_DQ_F_USED = "_dq_f_used";
    public static final String PROPERTY_DQ_KB_HARD = "_dq_kb_hard";
    public static final String PROPERTY_DQ_KB_SOFT = "_dq_kb_soft";
    public static final String PROPERTY_DQ_KB_USED = "_dq_kb_used";
    public static final String PROPERTYNAME_DISK_QUOTA = "diskQuota";

    public int get_dq_f_hard();

    public int get_dq_f_soft();

    public int get_dq_f_used();

    public int get_dq_kb_hard();

    public int get_dq_kb_soft();

    public int get_dq_kb_used();

    public Quota getDiskQuota();

    public void set_dq_f_hard(int var1);

    public void set_dq_f_soft(int var1);

    public void set_dq_f_used(int var1);

    public void set_dq_kb_hard(int var1);

    public void set_dq_kb_soft(int var1);

    public void set_dq_kb_used(int var1);

    public void setDiskQuota(Quota var1);
}

