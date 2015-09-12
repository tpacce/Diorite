package org.diorite.utils.skins;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.entity.PlayerImpl;

/**
 * Created by Szymon on 2015-09-10 16:32.
 */
public class Skin
{
    private final String EXTENSION = ".skin";
    private final PlayerImpl player;
    private       UUID       uuid;

    public Skin(PlayerImpl player)
    {
        this.player = player;
        this.uuid = player.getUniqueID();
    }

    public void setSkin(UUID uuid)
    {
        this.uuid = uuid;
        this.clear();
        this.update();
        this.save();
    }

    public void setSkin(String username)
    {
        this.setSkin(UUIDs.getUUID(username));
    }

    public void clear()
    {
        this.player.getGameProfile().getProperties().clear();
    }

    public void update()
    {
        Textures textures = new Textures(this.uuid);
        this.player.getGameProfile().getProperties().put("textures", textures.getProperty());
    }

    public void save()
    {
        File dir = new File("skins");
        if (! dir.exists())
        {
            dir.mkdir();
        }
        File cache = new File(dir, this.player.getName() + EXTENSION);
        if (cache.exists())
        {
            cache.delete();
        }
        FileWriter cfg = null;
        try
        {
            cfg = new FileWriter(cache);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            cfg.write(this.uuid.toString());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            cfg.close();
        } catch (IOException e)
        {
            System.out.println("Cache: skin for player " + this.player.getName() + " not set. Reason: IOException");
        }
    }

    public void load()
    {
        File dir = new File("skins");
        if (! dir.exists())
        {
            dir.mkdir();
        }
        File cache = new File(dir, this.player.getName() + EXTENSION);
        if (! cache.exists())
        {
            return;
        }
        BufferedReader cfg = null;
        try
        {
            cfg = new BufferedReader(new FileReader(cache));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        String line = null;
        try
        {
            line = cfg.readLine();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        this.uuid = UUID.fromString(line);
        this.clear();
        this.update();
        this.save();
        try
        {
            cfg.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public /*static*/ UUID getUUID(String nickname)
    {
        return UUIDs.getUUID(nickname);
    }

    public PlayerImpl getPlayer()
    {
        return this.player;
    }

    public UUID getUUID()
    {
        return this.uuid;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash = hash * 17 + 47;
        hash = hash * 31 + this.player.getName().hashCode();
        hash = hash * 13 + (this.uuid == null ? 0 : this.uuid.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        return ((Skin) obj).getPlayer() == this.getPlayer() && ((Skin) obj).getUUID() == this.getUUID();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.player.getName()).append("uuid", this.uuid).toString();
    }
}
