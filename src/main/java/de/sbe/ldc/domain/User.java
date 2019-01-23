/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractLdapBean;
import de.sbe.ldc.domain.DiskQuota;
import de.sbe.ldc.domain.Gender;
import de.sbe.ldc.domain.Group;
import de.sbe.ldc.domain.MailQuota;
import de.sbe.ldc.domain.Option;
import de.sbe.ldc.domain.Password;
import de.sbe.ldc.domain.Pykota;
import de.sbe.ldc.domain.Role;
import de.sbe.ldc.domain.UserInfo;
import de.sbe.ldc.domain.VPN;
import de.sbe.ldc.domain.WLAN;
import de.sbe.ldc.domain.Zarafa;
import de.sbe.ldc.domain.rpc.Impersonator;
import de.sbe.ldcd.domain.user.MailForward;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public interface User
extends AbstractLdapBean,
DiskQuota,
Gender,
MailQuota,
Password,
Pykota,
VPN,
WLAN,
Zarafa,
Impersonator,
MailForward {
    public static final List<User> EMPTY_USER_LIST = Collections.emptyList();
    public static final String PROPERTYNAME_BIRTHDAY = "birthday";
    public static final String PROPERTYNAME_CLASSES = "classes";
    public static final String PROPERTYNAME_CN = "cn";
    public static final String PROPERTYNAME_COURSES = "courses";
    public static final String PROPERTYNAME_DISPLAYNAME = "displayName";
    public static final String PROPERTYNAME_GIVENNAME = "givenname";
    public static final String PROPERTYNAME_GROUPS = "groups";
    public static final String PROPERTYNAME_MAIL = "mail";
    public static final String PROPERTYNAME_PROJECTS = "projects";
    public static final String PROPERTYNAME_ROLE = "role";
    public static final String PROPERTYNAME_SERVER_PROFILE = "serverProfile";
    public static final String PROPERTYNAME_SN = "sn";
    public static final String PROPERTYNAME_TITLE = "title";
    public static final String PROPERTYNAME_UID = "uid";
    public static final String PROPERTYNAME_UNIQUEID = "uniqueid";
    public static final String SAMBA_ACCT_FLAG_ACCOUNT_AUTO_LOCKED = "L";
    public static final String SAMBA_ACCT_FLAG_ACCOUNT_DISABLED = "D";
    public static final String SAMBA_ACCT_FLAG_PWD_NEVER_EXPIRES = "X";

    public void addGroup(Group var1);

    public Date getBirthday();

    public List<Group> getClasses();

    public List<String> getCn();

    public List<Group> getCourses();

    @Override
    public String getDisplayName();

    public String getGivenname();

    public List<Group> getGroups();

    public List<String> getMail();

    public List<Group> getProjects();

    public Role getRole();

    public Option getServerProfile();

    public String getSn();

    public String getTitle();

    public String getUid();

    public String getUniqueid();

    public UserInfo getUserInfo();

    public void removeGroup(Group var1);

    public void setBirthday(Date var1);

    public void setClasses(List<Group> var1);

    public void setCn(List<String> var1);

    public void setCourses(List<Group> var1);

    public void setDisplayName(String var1);

    public void setGivenname(String var1);

    public void setGroups(List<Group> var1);

    public void setMail(List<String> var1);

    public void setProjects(List<Group> var1);

    public void setRole(Role var1);

    public void setServerProfile(Option var1);

    public void setSn(String var1);

    public void setTitle(String var1);

    public void setUid(String var1);

    public void setUniqueid(String var1);
}

