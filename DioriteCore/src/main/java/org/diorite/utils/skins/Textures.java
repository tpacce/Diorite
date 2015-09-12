package org.diorite.utils.skins;

import javax.net.ssl.HttpsURLConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.diorite.impl.auth.properties.Property;

/**
 * Created by Szymon on 2015-09-10 16:56.
 */
public class Textures
{
    public static final JSONParser            JSON_PARSER     = new JSONParser();
    private static      HashMap<UUID, String> cache_texture   = new HashMap<UUID, String>();
    private static      HashMap<UUID, String> cache_signature = new HashMap<UUID, String>();
    private final UUID uuid;

    public Textures(UUID uuid)
    {
        this.uuid = uuid;
        if (! cache_texture.containsKey(uuid))
        {
            URL obj = null;
            try
            {
                obj = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replaceAll("-", "") + "?unsigned=false");
            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            }

            HttpsURLConnection con = null;
            try
            {
                con = (HttpsURLConnection) obj.openConnection();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);

            try
            {
                if (con.getResponseCode() == 429)
                {
                    System.out.println("Skins: Limit requests. Change not applied to uuid " + uuid.toString() + ".");
                    return;
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            BufferedReader in = null;
            try
            {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            String inputLine;
            StringBuffer response = new StringBuffer();

            try
            {
                while ((inputLine = in.readLine()) != null)
                {
                    response.append(inputLine);
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                in.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            JSONObject json = null;
            try
            {
                json = (JSONObject) JSON_PARSER.parse(response.toString());
            } catch (ParseException e)
            {
                e.printStackTrace();
            }
            JSONArray array = (JSONArray) json.get("properties");
            JSONObject properties = (JSONObject) array.get(0);
            cache_texture.put(uuid, (String) properties.get("value"));
            cache_signature.put(uuid, (String) properties.get("signature"));
        }
    }

    public Property getProperty()
    {
        return new Property("textures", cache_texture.get(uuid), cache_signature.get(uuid));
    }

    public UUID getUUID()
    {
        return this.uuid;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash = hash * 17 + this.uuid.toString().length();
        hash = hash * 31 + this.uuid.toString().hashCode();
        hash = hash * 13 + (this.uuid == null ? 0 : this.uuid.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        return ((Textures) obj).getUUID() == this.getUUID();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("uuid", this.uuid).toString();
    }
}
