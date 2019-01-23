/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.jgoodies.binding.beans.Model
 */
package de.sbe.ldc.domain;

import com.jgoodies.binding.beans.Model;
import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.Copyable;
import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.Option;
import de.sbe.ldc.domain.Quota;
import de.sbe.ldc.domain.Role;
import de.sbe.ldc.domain.ZarafaEntity;
import de.sbe.ldc.persistence.morpher.SerializableProperty;
import de.sbe.utils.logging.LogUtils;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public final class UserList
extends AbstractBean {
    public static final String PROPERTYNAME_ALLOW_VPN = "allowVPN";
    public static final String PROPERTYNAME_ALLOW_WLAN = "allowWLAN";
    public static final String PROPERTYNAME_CAN_CHANGE_PASSWORD = "canChangePassword";
    public static final String PROPERTYNAME_CODE_PAGE = "codePage";
    public static final String PROPERTYNAME_COLUMN = "column";
    public static final String PROPERTYNAME_DELIMITER = "delimiter";
    public static final String PROPERTYNAME_DISK_QUOTA = "diskQuota";
    public static final String PROPERTYNAME_FILE_TYPE = "fileType";
    public static final String PROPERTYNAME_ID = "id";
    public static final String PROPERTYNAME_INITIAL_PASSWORD = "initialPassword";
    public static final String PROPERTYNAME_LIST = "list";
    public static final String PROPERTYNAME_MAIL_QUOTA = "mailQuota";
    public static final String PROPERTYNAME_MUST_CHANGE_PASSWORD = "mustChangePassword";
    public static final String PROPERTYNAME_ORIGINAL_CODE_PAGE = "originalCodePage";
    public static final String PROPERTYNAME_ROLE = "role";
    public static final String PROPERTYNAME_SERVER_PROFILE = "serverProfile";
    public static final String PROPERTYNAME_SKIP_LINES = "skipLines";
    public static final String PROPERTYNAME_ZARAFA_ENTITY = "zarafaEntity";
    private static final long serialVersionUID = -2445616516871730241L;
    @SerializableProperty
    private Option allowVPN;
    @SerializableProperty
    private Option allowWLAN;
    @SerializableProperty
    private Option canChangePassword = Option.YES;
    @SerializableProperty
    private String codePage = Charset.defaultCharset().name();
    @SerializableProperty
    private List<Column> column;
    @SerializableProperty
    private String delimiter;
    @SerializableProperty
    private Quota diskQuota;
    @SerializableProperty
    private FileType fileType;
    @SerializableProperty
    private String id;
    @SerializableProperty
    private String initialPassword;
    @SerializableProperty
    private String list;
    @SerializableProperty
    private Quota mailQuota;
    @SerializableProperty
    private Option mustChangePassword = Option.YES;
    @SerializableProperty
    private String originalCodePage;
    @SerializableProperty
    private Role role;
    @SerializableProperty
    private Option serverProfile;
    @SerializableProperty
    private int skipLines;
    @SerializableProperty
    private ZarafaEntity zarafaEntity = ZarafaEntity.NONE;

    public Option getAllowVPN() {
        return this.allowVPN;
    }

    public Option getAllowWLAN() {
        return this.allowWLAN;
    }

    public Option getCanChangePassword() {
        return this.canChangePassword;
    }

    public String getCodePage() {
        return this.codePage;
    }

    public List<Column> getColumn() {
        return this.column;
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public Quota getDiskQuota() {
        if (this.diskQuota == null) {
            this.diskQuota = new Quota();
        }
        return this.diskQuota;
    }

    public FileType getFileType() {
        return this.fileType;
    }

    public String getId() {
        return this.id;
    }

    public String getInitialPassword() {
        return this.initialPassword;
    }

    public String getList() {
        return this.list;
    }

    public Quota getMailQuota() {
        if (this.mailQuota == null) {
            this.mailQuota = new Quota();
        }
        return this.mailQuota;
    }

    public Option getMustChangePassword() {
        return this.mustChangePassword;
    }

    public String getOriginalCodePage() {
        return this.originalCodePage == null ? this.codePage : this.originalCodePage;
    }

    public Role getRole() {
        return this.role;
    }

    public Option getServerProfile() {
        return this.serverProfile;
    }

    public int getSkipLines() {
        return this.skipLines;
    }

    public ZarafaEntity getZarafaEntity() {
        return this.zarafaEntity;
    }

    public void setAllowVPN(Option _allowVPN) {
        Option old = this.allowVPN;
        this.allowVPN = _allowVPN;
        this.firePropertyChange(PROPERTYNAME_ALLOW_VPN, (Object)old, (Object)this.allowVPN);
    }

    public void setAllowWLAN(Option _allowWLAN) {
        this.allowWLAN = _allowWLAN;
        this.firePropertyChange(PROPERTYNAME_ALLOW_WLAN, (Object)this.allowWLAN, (Object)this.allowWLAN);
    }

    public void setCanChangePassword(Option _canChangePassword) {
        Option old = this.canChangePassword;
        this.canChangePassword = _canChangePassword;
        this.firePropertyChange(PROPERTYNAME_CAN_CHANGE_PASSWORD, (Object)old, (Object)this.canChangePassword);
    }

    public void setCodePage(String _codePage) {
        String old = this.codePage;
        this.codePage = _codePage;
        this.firePropertyChange(PROPERTYNAME_CODE_PAGE, (Object)old, (Object)this.codePage);
    }

    public void setColumn(List<Column> _column) {
        List<Column> old = this.column;
        this.column = _column;
        this.firePropertyChange(PROPERTYNAME_COLUMN, old, this.column, true);
    }

    public void setDelimiter(String _delimiter) {
        String old = this.delimiter;
        this.delimiter = _delimiter;
        this.firePropertyChange(PROPERTYNAME_DELIMITER, (Object)old, (Object)this.delimiter);
    }

    public void setDiskQuota(Quota _diskQuota) {
        Quota old = this.diskQuota;
        this.diskQuota = _diskQuota;
        this.firePropertyChange(PROPERTYNAME_DISK_QUOTA, (Object)old, (Object)this.diskQuota);
    }

    public void setFileType(FileType _fileType) {
        FileType old = this.fileType;
        this.fileType = _fileType;
        this.firePropertyChange(PROPERTYNAME_FILE_TYPE, (Object)old, (Object)this.fileType);
    }

    public void setId(String _id) {
        String old = this.id;
        this.id = _id;
        this.firePropertyChange(PROPERTYNAME_ID, (Object)old, (Object)this.id);
    }

    public void setInitialPassword(String _initialPassword) {
        String old = this.initialPassword;
        this.initialPassword = _initialPassword;
        this.firePropertyChange(PROPERTYNAME_INITIAL_PASSWORD, (Object)old, (Object)this.initialPassword);
    }

    public void setList(String _list) {
        String old = this.list;
        this.list = _list;
        this.firePropertyChange(PROPERTYNAME_LIST, (Object)old, (Object)this.list);
    }

    public void setMailQuota(Quota _mailQuota) {
        Quota old = this.mailQuota;
        this.mailQuota = _mailQuota;
        this.firePropertyChange(PROPERTYNAME_MAIL_QUOTA, (Object)old, (Object)this.mailQuota);
    }

    public void setMustChangePassword(Option _mustChangePassword) {
        Option old = this.mustChangePassword;
        this.mustChangePassword = _mustChangePassword;
        this.firePropertyChange(PROPERTYNAME_MUST_CHANGE_PASSWORD, (Object)old, (Object)this.mustChangePassword);
    }

    public void setOriginalCodePage(String _originalCodePage) {
        this.originalCodePage = _originalCodePage;
        this.firePropertyChange(PROPERTYNAME_ORIGINAL_CODE_PAGE, (Object)this.originalCodePage, (Object)this.originalCodePage);
    }

    public void setRole(Role _role) {
        Role old = this.role;
        this.role = _role;
        this.firePropertyChange(PROPERTYNAME_ROLE, (Object)old, (Object)this.role);
        LogUtils.getLogger(this.getClass()).info("ID: " + this.getId() + ", ROLE: " + _role.getFullName());
    }

    public void setServerProfile(Option _serverProfile) {
        this.serverProfile = _serverProfile;
        this.firePropertyChange(PROPERTYNAME_SERVER_PROFILE, (Object)this.serverProfile, (Object)this.serverProfile);
    }

    public void setSkipLines(int _skipLines) {
        int old = this.skipLines;
        this.skipLines = _skipLines;
        this.firePropertyChange(PROPERTYNAME_SKIP_LINES, old, this.skipLines);
    }

    public void setZarafaEntity(ZarafaEntity _zarafaEntity) {
        this.zarafaEntity = _zarafaEntity;
        this.firePropertyChange(PROPERTYNAME_ZARAFA_ENTITY, (Object)this.zarafaEntity, (Object)this.zarafaEntity);
    }

    public static enum FileType {
        COURSES,
        CSV,
        TXT;
        
    }

    public static class Column
    extends Model
    implements Copyable,
    JavaBean {
        public static final List<Column> COURSE_COLUMNS = Collections.unmodifiableList(Arrays.asList(new Column(ColumnType.COURSE, 1), new Column(ColumnType.BASENAME, 2), new Column(ColumnType.QUANTITY, 3), new Column(ColumnType.PASSWORD, 4), new Column(ColumnType.VALIDFROM, 5), new Column(ColumnType.VALIDTILL, 6)));
        public static final String PROPERTYNAME_COLUMNTYPE = "columnType";
        public static final String PROPERTYNAME_INDEX = "index";
        public static final String PROPERTYNAME_PRIMARYKEY = "primaryKey";
        private static final long serialVersionUID = 1287282425441356056L;
        private ColumnType columnType;
        private int index;
        private boolean primaryKey;

        public Column(ColumnType _columnType) {
            this(_columnType, 1, false);
        }

        public Column(ColumnType _columnType, boolean _primaryKey) {
            this(_columnType, 1, _primaryKey);
        }

        public Column(ColumnType _columnType, int _index) {
            this(_columnType, _index, false);
        }

        public Column(ColumnType _columnType, int _index, boolean _primaryKey) {
            this.columnType = _columnType;
            this.index = _index;
            this.primaryKey = _primaryKey;
        }

        @Override
        public Column clone() {
            return this.copy();
        }

        @Override
        public Column copy() {
            return new Column(this.columnType, this.index, this.primaryKey);
        }

        public ColumnType getColumnType() {
            return this.columnType;
        }

        public int getIndex() {
            return this.index;
        }

        public boolean isPrimaryKey() {
            return this.primaryKey;
        }

        public void setColumnType(ColumnType _columnType) {
            ColumnType old = this.columnType;
            this.columnType = _columnType;
            this.firePropertyChange(PROPERTYNAME_COLUMNTYPE, (Object)old, (Object)this.columnType);
        }

        public void setIndex(int _index) {
            int old = this.index;
            this.index = _index;
            this.firePropertyChange(PROPERTYNAME_INDEX, old, this.index);
        }

        public void setPrimaryKey(boolean _primaryKey) {
            boolean old = this.primaryKey;
            this.primaryKey = _primaryKey;
            this.firePropertyChange(PROPERTYNAME_PRIMARYKEY, old, this.primaryKey);
        }

        public static enum ColumnType {
            BASENAME(false),
            BIRTHDAY(true),
            CLASS(false),
            COURSE(false),
            DISKQUOTA(false),
            EMAIL(false),
            FIRSTNAME(true),
            GENDER(true),
            LASTNAME(true),
            MAILQUOTA(false),
            PASSWORD(false),
            PRINTBALANCE(false),
            QUANTITY(false),
            SKIP_COLUMN(false),
            TITLE(true),
            UID(true),
            UNIQUEID(true),
            VALIDFROM(false),
            VALIDTILL(false);
            
            public static final Set<ColumnType> COURSES_COLUMN_TYPES;
            public static final List<ColumnType> REPLACEABLE_COLUMN_TYPES;
            private final boolean allowAsPkey;

            private ColumnType(boolean _allowAsPkey) {
                this.allowAsPkey = _allowAsPkey;
            }

            public boolean isAllowAsPkey() {
                return this.allowAsPkey;
            }

            static {
                COURSES_COLUMN_TYPES = Collections.unmodifiableSet(EnumSet.of(COURSE, new ColumnType[]{BASENAME, QUANTITY, PASSWORD, VALIDFROM, VALIDTILL}));
                REPLACEABLE_COLUMN_TYPES = Collections.unmodifiableList(Arrays.asList(new ColumnType[]{SKIP_COLUMN, BIRTHDAY, CLASS, DISKQUOTA, GENDER, EMAIL, FIRSTNAME, LASTNAME, MAILQUOTA, PASSWORD, PRINTBALANCE, TITLE, UID, UNIQUEID}));
            }
        }

    }

}

