package com.andreika47.livestreetandroid.Tasks;

/**
 * Created by Андрей on 16.11.2016.
 */

public enum TopicListType
{
    TOPIC_LIST_GOOD("http://%s/rss/index/"),
    TOPIC_LIST_NEW("http://%s/rss/new/");

    private final String feedUrl;

    TopicListType(String _feedUrl)
    {
        feedUrl = _feedUrl;
    }

    public String getFeedUrl()
    {
        return feedUrl;
    }
}
