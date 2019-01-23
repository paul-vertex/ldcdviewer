/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.User;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Surfing
extends AbstractBean {
    public static final Set<String> DOMAIN_DENY = new HashSet<String>();
    private static final long serialVersionUID = 7283465711035623149L;
    private String action;
    private int http_code;
    private String http_method;
    private long id;
    private String ip;
    private String mimetype;
    private long size;
    private Date time;
    private String url;
    private User user;

    public String getAction() {
        return this.action;
    }

    public int getHttp_code() {
        return this.http_code;
    }

    public String getHttp_method() {
        return this.http_method;
    }

    public long getId() {
        return this.id;
    }

    public String getIp() {
        return this.ip;
    }

    public String getMimetype() {
        return this.mimetype;
    }

    public long getSize() {
        return this.size;
    }

    public Date getTime() {
        return this.time;
    }

    public String getUrl() {
        return this.url;
    }

    public User getUser() {
        return this.user;
    }

    public void setAction(String _action) {
        this.action = _action;
    }

    public void setHttp_code(int _httpCode) {
        this.http_code = _httpCode;
    }

    public void setHttp_method(String _httpMethod) {
        this.http_method = _httpMethod;
    }

    public void setId(long _id) {
        this.id = _id;
    }

    public void setIp(String _ip) {
        this.ip = _ip;
    }

    public void setMimetype(String _mimetype) {
        this.mimetype = _mimetype;
    }

    public void setSize(long _size) {
        this.size = _size;
    }

    public void setTime(Date _time) {
        this.time = _time;
    }

    public void setUrl(String _url) {
        this.url = _url;
    }

    public void setUser(User _user) {
        this.user = _user;
    }

    static {
        DOMAIN_DENY.add("uk.com");
        DOMAIN_DENY.add("co.jp");
        DOMAIN_DENY.add("ne.jp");
        DOMAIN_DENY.add("or.jp");
        DOMAIN_DENY.add("uk.net");
        DOMAIN_DENY.add("co.uk");
        DOMAIN_DENY.add("ltd.uk");
        DOMAIN_DENY.add("me.uk");
        DOMAIN_DENY.add("net.uk");
        DOMAIN_DENY.add("org.uk");
        DOMAIN_DENY.add("plc.uk");
        DOMAIN_DENY.add("tld.uk");
    }
}

