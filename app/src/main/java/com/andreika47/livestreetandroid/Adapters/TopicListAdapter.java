package com.andreika47.livestreetandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andreika47.livestreetandroid.R;
import com.andreika47.livestreetandroid.Topic;

import java.util.ArrayList;
import java.util.List;

public class TopicListAdapter extends BaseAdapter
{
    Context context;
    LayoutInflater inflater;
    List<TopicListItem> topicList = new ArrayList<>();

    public TopicListAdapter(Context _context, ArrayList<TopicListItem> items)
    {
        context = _context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        topicList.addAll(items);
    }

    public void reload(List<TopicListItem> topics)
    {
        topicList.clear();
        topicList.addAll(topics);
    }

    @Override
    public int getCount()
    {
        return topicList.size();
    }

    @Override
    public TopicListItem getItem(int i)
    {
        return topicList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View resView = view;
        TopicListItem topic = topicList.get(i);

        if(resView == null)
            resView = inflater.inflate(R.layout.topic, viewGroup, false);
        ((TextView)resView.findViewById(R.id.topicName)).setText(topic.getTitle());
        ((TextView)resView.findViewById(R.id.topicBlog)).setText(topic.getBlog());
        ((TextView)resView.findViewById(R.id.topicDesc)).setText(topic.getDesc());
        return resView;
    }
}
