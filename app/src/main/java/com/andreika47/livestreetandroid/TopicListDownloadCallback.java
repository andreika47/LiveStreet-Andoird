package com.andreika47.livestreetandroid;

import com.andreika47.livestreetandroid.Adapters.TopicListItem;

import java.util.List;

public interface TopicListDownloadCallback
{
    void onTopicListDownloadComplete(List<TopicListItem> topics);
}
