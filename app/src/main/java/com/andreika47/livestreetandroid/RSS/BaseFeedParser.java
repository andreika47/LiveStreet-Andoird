package com.andreika47.livestreetandroid.RSS;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Андрей on 16.11.2016.
 */

public abstract class BaseFeedParser implements FeedParser
{

    // names of the XML tags
    static final String CHANNEL = "channel";
    static final String PUB_DATE = "pubDate";
    static final  String DESCRIPTION = "description";
    static final  String LINK = "link";
    static final  String TITLE = "title";

    static final  String CREATOR_URI = "http://purl.org/dc/elements/1.1/";
    static final  String CREATOR = "creator";
    static final  String ITEM = "item";

    private final URL feedUrl;

    protected BaseFeedParser(String feedUrl)
    {
        try
        {
            this.feedUrl = new URL(feedUrl);
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException(e);
        }
    }

    protected InputStream getInputStream()
    {
        try
        {
            return feedUrl.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
