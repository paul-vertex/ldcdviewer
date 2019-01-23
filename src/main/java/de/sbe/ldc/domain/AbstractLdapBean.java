/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.JavaBean;
import java.util.Date;

public interface AbstractLdapBean
extends JavaBean {
    public static final String CREATION_METHOD_IMPORT = "import";
    public static final String CREATION_METHOD_MANUAL = "manual";
    public static final String PROPERTYNAME_COMMENT = "comment";
    public static final String PROPERTYNAME_CREATED = "created";
    public static final String PROPERTYNAME_CREATION_METHOD = "creationMethod";
    public static final String PROPERTYNAME_CSETTINGS = "csettings";
    public static final String PROPERTYNAME_DN = "dn";
    public static final String PROPERTYNAME_LAST_MODIFIED = "lastModified";
    public static final String PROPERTYNAME_OBJECT_SUB_TYPE = "objectSubType";
    public static final String PROPERTYNAME_OBJECT_TYPE = "objectType";

    public String getComment();

    public Date getCreated();

    public String getCreationMethod();

    public String getDn();

    public Date getLastModified();

    public String getObjectType();

    public void setComment(String var1);

    public void setCreated(Date var1);

    public void setCreationMethod(String var1);

    public void setDn(String var1);

    public void setLastModified(Date var1);

    public void setObjectType(String var1);
}

