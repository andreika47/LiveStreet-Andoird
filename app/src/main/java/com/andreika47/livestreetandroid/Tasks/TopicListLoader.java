package com.andreika47.livestreetandroid.Tasks;

import android.os.AsyncTask;

import com.andreika47.livestreetandroid.DataProvider;
import com.andreika47.livestreetandroid.RSS.Message;
import com.andreika47.livestreetandroid.RSS.SaxFeedParser;
import com.andreika47.livestreetandroid.Topic;
import com.andreika47.livestreetandroid.TopicStatus;

import java.util.List;


/**
 * Downloads topic list from RSS.
 *
 */
public class TopicListLoader extends AsyncTask<String, Integer, String>
{
    private Topic[] topics = null;

    public void download(TopicListType topicListType)
    {
        String url = String.format(topicListType.getFeedUrl(), "livestreet.ru");
        execute(url);
    }

    @Override
    protected String doInBackground(String... arg0)
    {
        List<Message> messages = null;
        final String url = arg0[0];
        SaxFeedParser parser = new SaxFeedParser(url);
        try
        {
            messages = parser.parse();
        }
        catch (Exception e)
        {
            messages = null;
        }

        final int messagesCount = messages == null ? 0 : messages.size();
        topics = new Topic[messagesCount];
        if (messagesCount == 0)
            return null;

        for (int i = 0; i < messages.size(); ++i) {
            Message msg = messages.get(i);
            Topic topic = new Topic(msg.getLink());
            topic.setTitle(msg.getTitle());
            topic.setAuthor(msg.getCreator());
            topic.setDate(msg.getDate());

            String description = msg.getDescription().replace("<![CDATA[", "").replace("]]>", "");
            topic.setContent(description);
            topic.setStatus(TopicStatus.STATUS_BRIEF);
            topic.setBlog("", "");
            topics[i] = topic;
        }

        return null;
    }


    /**
     * Called when downloading is done.
     */
    @Override
    protected void onPostExecute(String result)
    {
        DataProvider.onTopicListDownloadComplete(topics);
    }

}
