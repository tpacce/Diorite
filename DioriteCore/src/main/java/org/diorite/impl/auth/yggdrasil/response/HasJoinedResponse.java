package org.diorite.impl.auth.yggdrasil.response;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.auth.properties.PropertyMap;

public class HasJoinedResponse extends Response
{
    private UUID        id;
    private PropertyMap properties;

    public UUID getId()
    {
        return this.id;
    }

    public void setId(final UUID id)
    {
        this.id = id;
    }

    public PropertyMap getProperties()
    {
        return this.properties;
    }

    public void setProperties(final PropertyMap properties)
    {
        this.properties = properties;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", this.id).append("properties", this.properties).toString();
    }
}
