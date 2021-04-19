package com.composum.sling.core.usermanagement.model;

import com.composum.sling.core.usermanagement.service.Authorizables;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.jcr.RepositoryException;
import javax.jcr.Value;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class UserModel extends AuthorizableModel {

    protected final ValueMap values;

    protected final boolean admin;
    protected final boolean systemUser;
    protected final boolean disabled;
    protected final String disabledReason;

    public UserModel(@NotNull final Authorizables.Context context,
                     @NotNull final User jcrUser)
            throws RepositoryException {
        this(context, (Authorizable) jcrUser);
    }

    protected UserModel(@NotNull final Authorizables.Context context,
                        @NotNull final Authorizable authorizable)
            throws RepositoryException {
        super(context, authorizable);
        if (authorizable instanceof User) {
            User jcrUser = (User) authorizable;
            admin = jcrUser.isAdmin();
            systemUser = jcrUser.isSystemUser();
            disabled = jcrUser.isDisabled();
            disabledReason = jcrUser.getDisabledReason();
        } else {
            admin = false;
            systemUser = true;
            disabled = false;
            disabledReason = null;
        }
        Map<String, Object> properties = new TreeMap<>();
        Iterator<String> propertyNames = authorizable.getPropertyNames();
        while (propertyNames.hasNext()) {
            String name = propertyNames.next();
            Value[] property = authorizable.getProperty(name);
            String[] vs = new String[property.length];
            for (int i = 0; i < property.length; i++) {
                vs[i] = property[i].getString();
            }
            properties.put(name, vs);
        }
        values = new ValueMapDecorator(properties);
    }

    @Override
    public boolean isGroup() {
        return false;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean isSystemUser() {
        return systemUser;
    }

    public boolean isServiceUser() {
        return false;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public @Nullable String getDisabledReason() {
        return disabledReason;
    }

    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject();
        writer.name("type").value("user");
        writer.name("id").value(getId());
        writer.name("name").value(getPrincipalName());
        writer.name("path").value(getPath());
        writer.name("admin").value(isAdmin());
        writer.name("system").value(isSystemUser());
        writer.name("disabled").value(isDisabled());
        if (StringUtils.isNotBlank(getDisabledReason())) {
            writer.name("reason").value(getDisabledReason());
        }
        writer.name("declaredMemberOf").beginArray();
        for (String id : getDeclaredMemberOf()) {
            writer.value(id);
        }
        writer.endArray();
        writer.name("memberOf").beginArray();
        for (String id : getMemberOf()) {
            writer.value(id);
        }
        writer.endArray();
        writer.endObject();
    }
}
