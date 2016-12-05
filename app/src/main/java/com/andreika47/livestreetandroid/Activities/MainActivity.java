package com.andreika47.livestreetandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.andreika47.livestreetandroid.Adapters.TopicListAdapter;
import com.andreika47.livestreetandroid.Adapters.TopicListItem;
import com.andreika47.livestreetandroid.DataProvider;
import com.andreika47.livestreetandroid.R;
import com.andreika47.livestreetandroid.Tasks.TopicListType;
import com.andreika47.livestreetandroid.TopicListDownloadCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TopicListDownloadCallback
{
    private TopicListAdapter topicListAdapter;
    private TopicListType topicListType;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if(topicListAdapter != null)
            topicListAdapter.notifyDataSetChanged();
    }

    private void init()
    {
        if(topicListAdapter == null)
            topicListAdapter = new TopicListAdapter(getApplicationContext(), new ArrayList<TopicListItem>());
        listView = (ListView)findViewById(R.id.topicsLV);
        listView.setAdapter(topicListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                TopicListItem topic = topicListAdapter.getItem(i);
                if(topic != null)
                {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    bundle.putString(TopicActivity.TOPIC_URL, topic.getTopicUrl());
                    bundle.putString(TopicActivity.TOPIC_TITLE, topic.getTitle());
                    intent.putExtras(bundle);
                    intent.setClass(getBaseContext(), TopicActivity.class);
                    startActivity(intent);
                }
            }
        });
        topicListType = TopicListType.TOPIC_LIST_GOOD;
        List<TopicListItem> topics = DataProvider.getTopicList(topicListType, this);
        reloadTopics(topics);
    }

    private void reloadTopics(List<TopicListItem> topics)
    {
        topicListAdapter.reload(topics);
        listView.setAdapter(topicListAdapter);
    }

    @Override
    public void onTopicListDownloadComplete(List<TopicListItem> topics)
    {
        reloadTopics(topics);
    }
}
