package org.diorite.utils.skins;

import javax.net.ssl.HttpsURLConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by Szymon on 2015-09-10 17:03.
 */
public class UUIDs
{
    public static final JSONParser            JSON_PARSER = new JSONParser();
    private static      HashMap<String, UUID> cache       = new HashMap<String, UUID>();

    public static UUID getUUID(String nickname)
    {
        if (! cache.containsKey(nickname))
        {
            URL obj = null;
            try
            {
                obj = new URL("https://api.mojang.com/profiles/minecraft");
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

            try
            {
                con.setRequestMethod("POST");
            } catch (ProtocolException e)
            {
                e.printStackTrace();
            }
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type", "application/json");

            String urlParameters = "[\"" + nickname + "\"]";

            con.setDoOutput(true);
            DataOutputStream wr = null;
            try
            {
                wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                if (con.getResponseCode() != 200)
                {
                    System.out.println("UUIDs: Limit requests. requests to playername " + nickname + ".");
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
                json = (JSONObject) ((JSONArray) JSON_PARSER.parse(response.toString())).get(0);
            } catch (ParseException e)
            {
                e.printStackTrace();
            }
            String uuid = (String) json.get("id");
            cache.put(nickname, UUID.fromString(uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20)));
        }
        return cache.get(nickname);
    }
}
