package org.diorite.utils.others;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Szymon on 2015-09-11 23:28.
 */
public class Premium
{
    private static HashMap<String, Boolean> cache = new HashMap<String, Boolean>();

    public static boolean hasPremium(String username)
    {
        if (! cache.containsKey(username))
        {
            try
            {
                URL url = new URL("https://minecraft.net/haspaid.jsp?user=" + username);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

                String text = "";
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                {
                    text += inputLine;
                }
                in.close();
                cache.put(username, text.equals("true"));
            } catch (MalformedURLException e)
            {
                e.printStackTrace();
                cache.put(username, false);
            } catch (IOException e)
            {
                e.printStackTrace();
                cache.put(username, false);
            }
        }
        return cache.get(username);
    }
}
