package com.andreika47.livestreetandroid.Adapters;

import com.andreika47.livestreetandroid.Topic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Андрей on 16.11.2016.
 */
public class TopicListItem  implements Comparable<TopicListItem>
{
    protected String title = "";
    protected String blog = "";
    protected String desc = "";
    protected String topicUrl = "";
    protected Date date;
    private static SimpleDateFormat RU_FORMATTER =
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm", new Locale("ru"));

    public TopicListItem() {}

    public TopicListItem(String _title, String _blog, String _desc)
    {
        title = _title;
        blog = _blog;
        desc = _desc;
    }

    public String getTitle()
    {
        return title;
    }

    public String getBlog()
    {
        return blog;
    }

    public String getDesc()
    {
        return desc;
    }

    public String getTopicUrl()
    {
        return topicUrl;
    }

    public String getDate()
    {
        return RU_FORMATTER.format(date);
    }

    @Override
    public int compareTo(TopicListItem topic)
    {
        return topic.date.compareTo(date);
    }
}
