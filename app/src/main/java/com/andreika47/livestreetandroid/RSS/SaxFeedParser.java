package com.andreika47.livestreetandroid.RSS;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 16.11.2016.
 */

public class SaxFeedParser extends BaseFeedParser
{
    static final String RSS = "rss";
    private String siteTitle = "";

    public SaxFeedParser(String feedUrl)
    {
        super(feedUrl);
    }

    public List<Message> parse()
    {
        final Message currentMessage = new Message();
        RootElement root = new RootElement(RSS);
        final List<Message> messages = new ArrayList<>();
        Element channel = root.getChild(CHANNEL);
        channel.getChild(TITLE).setEndTextElementListener(new EndTextElementListener()
        {
            public void end(String body)
            {
                siteTitle = body;
            }
        });

        Element item = channel.getChild(ITEM);
        item.setEndElementListener(new EndElementListener()
        {
            public void end()
            {
                messages.add(currentMessage.copy());
            }
        });
        item.getChild(TITLE).setEndTextElementListener(new EndTextElementListener()
        {
            public void end(String body)
            {
                currentMessage.setTitle(body);
            }
        });
        item.getChild(LINK).setEndTextElementListener(new EndTextElementListener()
        {
            public void end(String body)
            {
                currentMessage.setLink(body);
            }
        });
        item.getChild(DESCRIPTION).setEndTextElementListener(new EndTextElementListener()
        {
            public void end(String body)
            {
                currentMessage.setDescription(body);
            }
        });
        item.getChild(PUB_DATE).setEndTextElementListener(new EndTextElementListener()
        {
            public void end(String body)
            {
                currentMessage.setDate(body);
            }
        });
        item.getChild(CREATOR_URI, CREATOR).setEndTextElementListener(new EndTextElementListener()
        {
            public void end(String body)
            {
                currentMessage.setCreator(body);
            }
        });
        try
        {
            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return messages;
    }

    public String getSiteTitle()
    {
        return siteTitle;
    }
}
