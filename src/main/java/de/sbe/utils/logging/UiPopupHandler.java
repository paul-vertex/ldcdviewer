/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.utils.logging;

import de.sbe.utils.StringUtils;
import de.sbe.utils.WindowUtils;
import de.sbe.utils.logging.LogUtils;
import java.awt.Window;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.ErrorManager;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class UiPopupHandler
extends Handler {
    public UiPopupHandler() {
        this.configure();
    }

    @Override
    public void close() throws SecurityException {
    }

    private void configure() {
        String cname = this.getClass().getName();
        try {
            this.setEncoding(LogUtils.getStringProperty(cname + ".encoding", "UTF-8"));
        }
        catch (Exception _e1) {
            try {
                this.setEncoding(null);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        this.setFilter(LogUtils.getFilterProperty(cname + ".filter", null));
        this.setLevel(LogUtils.getLevelProperty(cname + ".level", Level.WARNING));
    }

    @Override
    public void flush() {
    }

    private String getMessage(LogRecord _record) {
        String text = null;
        if (_record.getThrown() == null) {
            text = _record.getMessage();
        } else {
            Throwable thrown = _record.getThrown();
            while (StringUtils.isEmptyString(thrown.getLocalizedMessage()) && thrown.getCause() != null && !thrown.equals(thrown.getCause())) {
                thrown = thrown.getCause();
            }
            text = StringUtils.isEmptyString(thrown.getLocalizedMessage()) ? thrown.getClass().getName() : thrown.getLocalizedMessage();
        }
        return text;
    }

    private int getMessageType(LogRecord _record) {
        int type = Integer.MIN_VALUE;
        type = Level.SEVERE.equals(_record.getLevel()) ? 0 : (Level.WARNING.equals(_record.getLevel()) ? 2 : 1);
        return type;
    }

    private String getTitle(LogRecord _record) {
        return _record.getLevel().getLocalizedName();
    }

    @Override
    public void publish(LogRecord _record) {
        if (this.isLoggable(_record)) {
            this.showDialogMessage(_record);
        }
    }

    private void showDialogMessage(LogRecord _record) {
        final String message = this.getMessage(_record);
        final int messageType = this.getMessageType(_record);
        final Window owner = WindowUtils.getProbableOwner();
        final String title = this.getTitle(_record);
        if (SwingUtilities.isEventDispatchThread()) {
            JOptionPane.showMessageDialog(owner, message, title, messageType);
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable(){

                    @Override
                    public void run() {
                        JOptionPane.showMessageDialog(owner, message, title, messageType);
                    }
                });
            }
            catch (InterruptedException _ie) {
                this.getErrorManager().error("", _ie, 0);
            }
            catch (InvocationTargetException _ite) {
                this.getErrorManager().error("", _ite, 0);
            }
        }
    }

}

