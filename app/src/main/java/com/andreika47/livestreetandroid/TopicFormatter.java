package com.andreika47.livestreetandroid;

import android.content.Context;

import com.andreika47.livestreetandroid.Adapters.TopicListItem;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

/**
 * Created by Андрей on 26.11.2016.
 */

public class TopicFormatter
{
    private final static String templateFileName = "topic.html";
    private final Context context;
    private String template;

    public TopicFormatter(Context _context)
    {
        context = _context;
    }

    private String readString(InputStream inputStream) throws IOException
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while((length = inputStream.read(buffer)) >= 0)
            outputStream.write(buffer, 0, length);
        return outputStream.toString("UTF-8");
    }

    private String getTemplate(final String fileName)
    {
        try
        {
            template = readString(context.getAssets().open(fileName));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return template;
    }

    public String format(TopicListItem topicListItem)
    {
        Template tmpl = Mustache.compiler().escapeHTML(false).compile(getTemplate(templateFileName));
        return tmpl.execute(topicListItem);
    }
}
