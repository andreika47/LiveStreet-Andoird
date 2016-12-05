package com.andreika47.livestreetandroid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.andreika47.livestreetandroid.Adapters.TopicListItem;
import com.andreika47.livestreetandroid.Tasks.TopicListLoader;
import com.andreika47.livestreetandroid.Tasks.TopicListType;
import com.andreika47.livestreetandroid.Tasks.TopicLoader;

public class DataProvider
{
    private static Map<String, Topic> topics = new HashMap<>();
    private static TopicDownloadCallback topicDownloadCallback = null;
    private static TopicListDownloadCallback topicListDownloadCallback = null;
    private static TopicListLoader topicListDownloader = null;
    private static TopicLoader topicDownloader;

    public static Topic getTopic(final String topicUrl, TopicDownloadCallback _topicDownloadCallback)
    {
        assert _topicDownloadCallback != null;
        assert topicUrl != null;

        topicDownloadCallback = _topicDownloadCallback;
        Topic topic = topics.get(topicUrl);
        if (topic == null)
            return null;
        if (topic.getStatus() == TopicStatus.STATUS_INITIAL
        || topic.getStatus() == TopicStatus.STATUS_BRIEF)
        {
            if (topicDownloader != null)
                topicDownloader.cancel(true);
            topicDownloader = new TopicLoader();
            topicDownloader.download(topicUrl);
        }

        return topic;
    }

    public static void onDownloadComplete(Topic topic)
    {
        topic.setStatus(TopicStatus.STATUS_COMPLETE);
        Topic oldTopic = topics.get(topic.getTopicUrl());

        if (topic.getAuthor().length() == 0)
            topic.setAuthor(oldTopic.getAuthor());
        if (topic.getContent().length() == 0)
            topic.setContent(oldTopic.getContent());
        if (topic.getTitle().length() == 0)
            topic.setTitle(oldTopic.getTitle());
        topics.put(topic.getTopicUrl(), topic);
        topicDownloadCallback.onTopicDownloadComplete(topic);
    }

    public static List<TopicListItem> getTopicList(TopicListType topicListType, TopicListDownloadCallback _topicListDownloadCallback)
    {
        topicListDownloadCallback = _topicListDownloadCallback;
        cancelDownload();
        topics.clear();
        topicListDownloader = new TopicListLoader();
        topicListDownloader.download(topicListType);
        return getTopicList();
    }

    private static void cancelDownload()
    {
        if (topicListDownloader == null)
            return;
        topicListDownloader.cancel(true);
    }

    private static List<TopicListItem> getTopicList()
    {
        List<TopicListItem> topicList = new ArrayList<>();
        for (Map.Entry<String, Topic> entry : topics.entrySet())
            topicList.add(entry.getValue());

        Collections.sort(topicList);

        return topicList;
    }

    public static void onTopicListDownloadComplete(Topic[] topicsArr)
    {
        topics.clear();
        for (int i = 0; i < topicsArr.length; ++i)
        {
            Topic topic = topicsArr[i];
            topics.put(topic.getTopicUrl(), topic);
        }

        topicListDownloadCallback.onTopicListDownloadComplete(getTopicList());
        topicListDownloader = null;
    }
}
