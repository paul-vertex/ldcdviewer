/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package de.sbe.ldc.security;

import com.google.gson.JsonObject;
import de.sbe.ldc.domain.AbstractBean;

public class Auth
extends AbstractBean {
    private static final Auth instance = new Auth();
    public static final int MAX_FAILURE = 3;
    public static final String PROPERTYNAME_PASSWORD = "password";
    public static final String PROPERTYNAME_PRIVS = "privs";
    public static final String PROPERTYNAME_USER = "user";
    private static final long serialVersionUID = 7665148877967910783L;
    private int failure;
    private char[] password;
    private JsonObject privs;
    private String session;
    private String user;

    public static Auth getInstance() {
        return instance;
    }

    public int getFailure() {
        return this.failure;
    }

    public char[] getPassword() {
        return this.password;
    }

    public JsonObject getPrivs() {
        return this.privs;
    }

    public String getSession() {
        return this.session;
    }

    public String getUser() {
        return this.user;
    }

    public void incrementFailure() {
        ++this.failure;
    }

    public boolean isFailureExceeded() {
        return 3 <= this.failure;
    }

    public void resetFailure() {
        this.failure = 0;
    }

    public void setPassword(char[] _password) {
        char[] old = this.password;
        this.password = _password;
        this.firePropertyChange(PROPERTYNAME_PASSWORD, (Object)old, (Object)this.password);
    }

    public void setPrivs(JsonObject _privs) {
        JsonObject old = this.privs;
        this.privs = _privs;
        this.firePropertyChange(PROPERTYNAME_PRIVS, (Object)old, (Object)this.privs);
    }

    public void setSession(String _session) {
        this.session = _session;
    }

    public void setUser(String _username) {
        String old = this.user;
        this.user = _username;
        this.firePropertyChange(PROPERTYNAME_USER, (Object)old, (Object)this.user);
    }
}

