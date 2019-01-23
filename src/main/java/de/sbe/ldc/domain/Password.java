/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.JavaBean;

public interface Password
extends JavaBean {
    public static final String PROPERTYNAME_ACCOUNT_AUTO_LOCKED = "accountAutoLocked";
    public static final String PROPERTYNAME_ACCOUNT_DISABLED = "accountDisabled";
    public static final String PROPERTYNAME_INITIAL_PASSWORD = "initialPassword";
    public static final String PROPERTYNAME_PASSWORD = "password";
    public static final String PROPERTYNAME_PASSWORD_CONFIRM = "passwordConfirm";
    public static final String PROPERTYNAME_PWD_CANNOT_CHANGE = "pwdCannotChange";
    public static final String PROPERTYNAME_PWD_MUST_CHANGE = "pwdMustChange";
    public static final String PROPERTYNAME_PWD_NEVER_EXPIRES = "pwdNeverExpires";
    public static final String PROPERTYNAME_SAMBA_ACCT_FLAGS = "sambaAcctFlags";
    public static final String PROPERTYNAME_SAMBA_PWD_CAN_CHANGE = "sambaPwdCanChange";
    public static final String PROPERTYNAME_SAMBA_PWD_LAST_SET = "sambaPwdLastSet";
    public static final String PROPERTYNAME_SAMBA_PWD_MUST_CHANGE = "sambaPwdMustChange";

    public String getInitialPassword();

    public String getPassword();

    public String getPasswordConfirm();

    public String getSambaAcctFlags();

    public long getSambaPwdCanChange();

    public long getSambaPwdLastSet();

    public long getSambaPwdMustChange();

    public boolean isAccountAutoLocked();

    public boolean isAccountDisabled();

    public boolean isPwdCannotChange();

    public boolean isPwdMustChange();

    public boolean isPwdNeverExpires();

    public void setAccountAutoLocked(boolean var1);

    public void setAccountDisabled(boolean var1);

    public void setInitialPassword(String var1);

    public void setPassword(String var1);

    public void setPasswordConfirm(String var1);

    public void setPwdCannotChange(boolean var1);

    public void setPwdMustChange(boolean var1);

    public void setPwdNeverExpires(boolean var1);

    public void setSambaAcctFlags(String var1);

    public void setSambaPwdCanChange(long var1);

    public void setSambaPwdLastSet(long var1);

    public void setSambaPwdMustChange(long var1);
}

