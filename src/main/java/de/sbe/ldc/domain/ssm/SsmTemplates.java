/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.ssm;

import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.ssm.SsmAction;
import de.sbe.ldc.domain.ssm.SsmEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SsmTemplates {
    private static final List<SsmAction> ACTIONS = new ArrayList<SsmAction>();
    private static final Set<SsmEntry> ALL_ENTRIES;
    private static final List<SsmEntry> HOST_ENTRIES;
    private static final List<SsmEntry> NOTEBOOK_ENTRIES;
    private static final List<SsmEntry> PRINTER_ENTRIES;

    public static SsmAction[] getActionTemplatesAsArray() {
        List<SsmAction> list = SsmTemplates.getActionTemplatesAsList();
        return list.toArray(new SsmAction[list.size()]);
    }

    public static List<SsmAction> getActionTemplatesAsList() {
        return ACTIONS;
    }


    public static List<SsmEntry> getEntryTemplatesAsList(Host.HostType _ht) {
        if (_ht == null) {
            return null;
        }
        switch (_ht) {
            case COMPUTER: {
                return HOST_ENTRIES;
            }
            case NOTEBOOK: {
                return NOTEBOOK_ENTRIES;
            }
            case PRINTER: {
                return PRINTER_ENTRIES;
            }
        }
        return null;
    }

    static {
        SsmAction sa = null;
        ACTIONS.add(sa);
        sa = new SsmAction();
        sa.setSubject("ssmAction.template.email");
        ACTIONS.add(sa);
        sa = new SsmAction();
        sa.setSubject("ssmAction.template.phone_call");
        ACTIONS.add(sa);
        sa = new SsmAction();
        sa.setSubject("ssmAction.template.working_step");
        ACTIONS.add(sa);
        sa = new SsmAction();
        sa.setSubject("ssmAction.template.misc");
        ACTIONS.add(sa);
        SsmEntry se = null;
        ALL_ENTRIES = new HashSet<SsmEntry>();
        HOST_ENTRIES = new ArrayList<SsmEntry>();
        HOST_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-1L);
        se.setPriority(SsmEntry.Priority.P1);
        se.setSubject("ssmEntry.template.host.not_booting");
        se.setSubcategory(SsmEntry.Subcategory.HOST);
        se.setCategory(SsmEntry.Category.SW);
        HOST_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-2L);
        se.setPriority(SsmEntry.Priority.P1);
        se.setSubject("ssmEntry.template.host.hangs_when_start");
        se.setSubcategory(SsmEntry.Subcategory.HOST);
        se.setCategory(SsmEntry.Category.UNKNOWN);
        HOST_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-3L);
        se.setPriority(SsmEntry.Priority.P1);
        se.setSubject("ssmEntry.template.host.bluescreen");
        se.setSubcategory(SsmEntry.Subcategory.HOST);
        se.setCategory(SsmEntry.Category.UNKNOWN);
        HOST_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-4L);
        se.setPriority(SsmEntry.Priority.P2);
        se.setSubject("ssmEntry.template.host.slow");
        se.setSubcategory(SsmEntry.Subcategory.HOST);
        se.setCategory(SsmEntry.Category.UNKNOWN);
        HOST_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-5L);
        se.setPriority(SsmEntry.Priority.P1);
        se.setSubject("ssmEntry.template.host.no_login");
        se.setSubcategory(SsmEntry.Subcategory.HOST);
        se.setCategory(SsmEntry.Category.UNKNOWN);
        HOST_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-6L);
        se.setPriority(SsmEntry.Priority.P2);
        se.setSubject("ssmEntry.template.host.not_healing");
        se.setSubcategory(SsmEntry.Subcategory.HOST);
        se.setCategory(SsmEntry.Category.UNKNOWN);
        HOST_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-7L);
        se.setPriority(SsmEntry.Priority.P3);
        se.setSubject("ssmEntry.template.host.no_screen");
        se.setSubcategory(SsmEntry.Subcategory.HOST);
        se.setCategory(SsmEntry.Category.UNKNOWN);
        HOST_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-8L);
        se.setPriority(SsmEntry.Priority.P3);
        se.setSubject("ssmEntry.template.other_error");
        se.setSubcategory(SsmEntry.Subcategory.HOST);
        se.setCategory(SsmEntry.Category.UNKNOWN);
        HOST_ENTRIES.add(se);
        NOTEBOOK_ENTRIES = new ArrayList<SsmEntry>();
        se = null;
        NOTEBOOK_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-1L);
        se.setPriority(SsmEntry.Priority.P1);
        se.setSubject("ssmEntry.template.notebook.charging_device_nonexistent");
        se.setSubcategory(SsmEntry.Subcategory.NOTEBOOK);
        se.setCategory(SsmEntry.Category.HW);
        NOTEBOOK_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-2L);
        se.setPriority(SsmEntry.Priority.P1);
        se.setSubject("ssmEntry.template.notebook.charging_cable_nonexistent");
        se.setSubcategory(SsmEntry.Subcategory.NOTEBOOK);
        se.setCategory(SsmEntry.Category.HW);
        NOTEBOOK_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-3L);
        se.setPriority(SsmEntry.Priority.P1);
        se.setSubject("ssmEntry.template.notebook.display_damaged");
        se.setSubcategory(SsmEntry.Subcategory.NOTEBOOK);
        se.setCategory(SsmEntry.Category.HW);
        NOTEBOOK_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-4L);
        se.setPriority(SsmEntry.Priority.P1);
        se.setSubject("ssmEntry.template.notebook.not_booting");
        se.setSubcategory(SsmEntry.Subcategory.NOTEBOOK);
        se.setCategory(SsmEntry.Category.UNKNOWN);
        NOTEBOOK_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-5L);
        se.setPriority(SsmEntry.Priority.P1);
        se.setSubject("ssmEntry.template.notebook.hangs_when_start");
        se.setSubcategory(SsmEntry.Subcategory.NOTEBOOK);
        se.setCategory(SsmEntry.Category.UNKNOWN);
        NOTEBOOK_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-6L);
        se.setPriority(SsmEntry.Priority.P1);
        se.setSubject("ssmEntry.template.notebook.bluescreen");
        se.setSubcategory(SsmEntry.Subcategory.NOTEBOOK);
        se.setCategory(SsmEntry.Category.UNKNOWN);
        NOTEBOOK_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-7L);
        se.setPriority(SsmEntry.Priority.P1);
        se.setSubject("ssmEntry.template.notebook.no_login");
        se.setSubcategory(SsmEntry.Subcategory.NOTEBOOK);
        se.setCategory(SsmEntry.Category.UNKNOWN);
        NOTEBOOK_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-8L);
        se.setPriority(SsmEntry.Priority.P1);
        se.setSubject("ssmEntry.template.notebook.no_login_by_wlan");
        se.setSubcategory(SsmEntry.Subcategory.NOTEBOOK);
        se.setCategory(SsmEntry.Category.UNKNOWN);
        NOTEBOOK_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-9L);
        se.setPriority(SsmEntry.Priority.P1);
        se.setSubject("ssmEntry.template.notebook.not_healing");
        se.setSubcategory(SsmEntry.Subcategory.NOTEBOOK);
        se.setCategory(SsmEntry.Category.UNKNOWN);
        NOTEBOOK_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-10L);
        se.setPriority(SsmEntry.Priority.P1);
        se.setSubject("ssmEntry.template.notebook.no_screen");
        se.setSubcategory(SsmEntry.Subcategory.NOTEBOOK);
        se.setCategory(SsmEntry.Category.UNKNOWN);
        NOTEBOOK_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-11L);
        se.setPriority(SsmEntry.Priority.P3);
        se.setSubject("ssmEntry.template.other_error");
        se.setSubcategory(SsmEntry.Subcategory.NOTEBOOK);
        se.setCategory(SsmEntry.Category.UNKNOWN);
        NOTEBOOK_ENTRIES.add(se);
        PRINTER_ENTRIES = new ArrayList<SsmEntry>();
        se = null;
        PRINTER_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-1L);
        se.setPriority(SsmEntry.Priority.P2);
        se.setSubject("ssmEntry.template.printer.jam");
        se.setSubcategory(SsmEntry.Subcategory.PRINTER);
        se.setCategory(SsmEntry.Category.HW);
        PRINTER_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-2L);
        se.setPriority(SsmEntry.Priority.P2);
        se.setSubject("ssmEntry.template.printer.not_visible");
        se.setSubcategory(SsmEntry.Subcategory.PRINTER);
        se.setCategory(SsmEntry.Category.SW);
        PRINTER_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-3L);
        se.setPriority(SsmEntry.Priority.P3);
        se.setSubject("ssmEntry.template.printer.low_on_paper");
        se.setSubcategory(SsmEntry.Subcategory.PRINTER);
        se.setCategory(SsmEntry.Category.HW);
        PRINTER_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-4L);
        se.setPriority(SsmEntry.Priority.P1);
        se.setSubject("ssmEntry.template.printer.no_paper");
        se.setSubcategory(SsmEntry.Subcategory.PRINTER);
        se.setCategory(SsmEntry.Category.HW);
        PRINTER_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-5L);
        se.setPriority(SsmEntry.Priority.P3);
        se.setSubject("ssmEntry.template.printer.low_on_dye");
        se.setSubcategory(SsmEntry.Subcategory.PRINTER);
        se.setCategory(SsmEntry.Category.HW);
        PRINTER_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-6L);
        se.setPriority(SsmEntry.Priority.P1);
        se.setSubject("ssmEntry.template.printer.no_dye");
        se.setSubcategory(SsmEntry.Subcategory.PRINTER);
        se.setCategory(SsmEntry.Category.HW);
        PRINTER_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-7L);
        se.setPriority(SsmEntry.Priority.P3);
        se.setSubject("ssmEntry.template.other_error");
        se.setSubcategory(SsmEntry.Subcategory.PRINTER);
        se.setCategory(SsmEntry.Category.UNKNOWN);
        PRINTER_ENTRIES.add(se);
        se = new SsmEntry();
        se.setId(-8L);
        se.setPriority(SsmEntry.Priority.P2);
        se.setSubject("ssmEntry.template.printer.not_working");
        se.setSubcategory(SsmEntry.Subcategory.PRINTER);
        se.setCategory(SsmEntry.Category.UNKNOWN);
        PRINTER_ENTRIES.add(se);
        ALL_ENTRIES.addAll(HOST_ENTRIES);
        ALL_ENTRIES.addAll(NOTEBOOK_ENTRIES);
        ALL_ENTRIES.addAll(PRINTER_ENTRIES);
    }

}

