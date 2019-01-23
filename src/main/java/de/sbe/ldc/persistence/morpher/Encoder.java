/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  org.apache.commons.codec.binary.Base64
 */
package de.sbe.ldc.persistence.morpher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.sbe.ldc.domain.Clopen;
import de.sbe.ldc.domain.GenderType;
import de.sbe.ldc.domain.Group;
import de.sbe.ldc.domain.GroupImpl;
import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.HostImpl;
import de.sbe.ldc.domain.Option;
import de.sbe.ldc.domain.Pykota;
import de.sbe.ldc.domain.Quota;
import de.sbe.ldc.domain.Role;
import de.sbe.ldc.domain.RoleImpl;
import de.sbe.ldc.domain.Room;
import de.sbe.ldc.domain.RoomImpl;
import de.sbe.ldc.domain.Switch;
import de.sbe.ldc.domain.User;
import de.sbe.ldc.domain.UserList;
import de.sbe.ldc.domain.ZarafaEntity;
import de.sbe.ldc.persistence.morpher.serializer.BooleanSerializer;
import de.sbe.ldc.persistence.morpher.serializer.ColumnSerializer;
import de.sbe.ldc.persistence.morpher.serializer.DateSerializer;
import de.sbe.ldc.persistence.morpher.serializer.DoubleSerializer;
import de.sbe.ldc.persistence.morpher.serializer.EnumSerializer;
import de.sbe.ldc.persistence.morpher.serializer.FloatSerializer;
import de.sbe.ldc.persistence.morpher.serializer.GroupSerializer;
import de.sbe.ldc.persistence.morpher.serializer.HostSerializer;
import de.sbe.ldc.persistence.morpher.serializer.IntegerSerializer;
import de.sbe.ldc.persistence.morpher.serializer.LongSerializer;
import de.sbe.ldc.persistence.morpher.serializer.QuotaSerializer;
import de.sbe.ldc.persistence.morpher.serializer.RoleSerializer;
import de.sbe.ldc.persistence.morpher.serializer.RoomSerializer;
import de.sbe.ldc.persistence.morpher.serializer.UserSerializer;
import de.sbe.ldc.persistence.morpher.serializer.ZarafaEntitySerializer;
import de.sbe.ldc.ui.analysis.surfing.By;
import de.sbe.ldc.ui.analysis.surfing.Epoch;
import de.sbe.ldc.ui.analysis.surfing.Mimetype;
import java.lang.reflect.Type;
import java.util.Date;
import org.apache.commons.codec.binary.Base64;

public class Encoder {
    protected GsonBuilder buildGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.registerTypeAdapter(Boolean.class, (Object)new BooleanSerializer());
        builder.registerTypeAdapter(Boolean.TYPE, (Object)new BooleanSerializer());
        builder.registerTypeAdapter(Double.class, (Object)new DoubleSerializer());
        builder.registerTypeAdapter(Double.TYPE, (Object)new DoubleSerializer());
        builder.registerTypeAdapter(Float.class, (Object)new FloatSerializer());
        builder.registerTypeAdapter(Float.TYPE, (Object)new FloatSerializer());
        builder.registerTypeAdapter(Integer.class, (Object)new IntegerSerializer());
        builder.registerTypeAdapter(Integer.TYPE, (Object)new IntegerSerializer());
        builder.registerTypeAdapter(Long.class, (Object)new LongSerializer());
        builder.registerTypeAdapter(Long.TYPE, (Object)new LongSerializer());
        builder.registerTypeAdapter(By.class, new EnumSerializer());
        builder.registerTypeAdapter(Clopen.class, new EnumSerializer());
        builder.registerTypeAdapter(UserList.Column.class, (Object)new ColumnSerializer());
        builder.registerTypeAdapter(Date.class, (Object)new DateSerializer());
        builder.registerTypeAdapter(Epoch.class, new EnumSerializer());
        builder.registerTypeAdapter(UserList.FileType.class, new EnumSerializer());
        builder.registerTypeAdapter(GenderType.class, new EnumSerializer());
        builder.registerTypeAdapter(Group.class, (Object)new GroupSerializer());
        builder.registerTypeAdapter(GroupImpl.class, (Object)new GroupSerializer());
        builder.registerTypeAdapter(Host.class, (Object)new HostSerializer());
        builder.registerTypeAdapter(HostImpl.class, (Object)new HostSerializer());
        builder.registerTypeAdapter(Host.HostType.class, new EnumSerializer());
        builder.registerTypeAdapter(Mimetype.class, new EnumSerializer());
        builder.registerTypeAdapter(Option.class, new EnumSerializer());
        builder.registerTypeAdapter(Pykota.PykotaLimitBy.class, new EnumSerializer());
        builder.registerTypeAdapter(Quota.class, (Object)new QuotaSerializer());
        builder.registerTypeAdapter(Role.class, (Object)new RoleSerializer());
        builder.registerTypeAdapter(RoleImpl.class, (Object)new RoleSerializer());
        builder.registerTypeAdapter(Room.class, (Object)new RoomSerializer());
        builder.registerTypeAdapter(RoomImpl.class, (Object)new RoomSerializer());
        builder.registerTypeAdapter(Switch.class, new EnumSerializer());
        builder.registerTypeAdapter(de.sbe.ldc.ui.analysis.surfing.Type.class, new EnumSerializer());
        builder.registerTypeAdapter(User.class, (Object)new UserSerializer());
        builder.registerTypeAdapter(ZarafaEntity.class, (Object)new ZarafaEntitySerializer());
        return builder;
    }

    public String encode(Object _obj) {
        String json = "%j:".concat(this.toJsonString(_obj));
        String base64 = "%b:".concat(this.toBase64String(json.getBytes()));
        return base64;
    }

    public String toBase64String(byte[] _bytes) {
        return new String(Base64.encodeBase64((byte[])_bytes));
    }

    public String toJsonString(Object _obj) {
        return this.buildGsonBuilder().create().toJson(_obj);
    }
}

