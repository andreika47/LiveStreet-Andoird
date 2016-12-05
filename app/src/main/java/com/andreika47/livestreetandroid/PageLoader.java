package com.andreika47.livestreetandroid;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Андрей on 16.11.2016.
 */

public class PageLoader
{
    public static String download(String sURL)
    {
        String res = "";
        try
        {
            URL url = new URL(sURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();
            try
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                res = readStream(in);
            }
            finally
            {
                urlConnection.disconnect();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return res;
    }

    private static String readStream(BufferedReader stream) throws IOException
    {
        StringBuilder builder = new StringBuilder();
        String line = "";
        while((line = stream.readLine()) != null)
        {
            builder.append(line + '\n');
        }
        return builder.toString();
    }
}
