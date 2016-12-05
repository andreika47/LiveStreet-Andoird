package com.andreika47.livestreetandroid.Tasks;

import android.os.AsyncTask;

import com.andreika47.livestreetandroid.DataProvider;
import com.andreika47.livestreetandroid.PageLoader;
import com.andreika47.livestreetandroid.Topic;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.SimpleHtmlSerializer;
import org.htmlcleaner.TagNode;

import java.net.MalformedURLException;

/**
 * Created by Андрей on 16.11.2016.
 */

public class TopicLoader extends AsyncTask<String, Integer, String>
{

    private static final String RSS_URL = "http://%s/rss/comments/%s/";

    private Topic topic;

    public void download(String topicUrl)
    {
        topic = new Topic(topicUrl);
        execute(topicUrl);
    }

    @Override
    protected String doInBackground(String... arg0)
    {
        String filename;
        try
        {
            HtmlCleaner cleaner = new HtmlCleaner();
            SimpleHtmlSerializer serializer = new SimpleHtmlSerializer(cleaner.getProperties());

            filename  = arg0[0];

            final String page = PageLoader.download(filename);
            final TagNode node = cleaner.clean(page);
            TagNode contentNode = getSingleElement(node, "topic-content text");

            if (null != contentNode)
            {
                String content = serializer.getAsString(contentNode, "UTF-8", true);
                topic.setContent(content);
            }

            final String topicPageText = serializer.getAsString(node);
            topic.getVotingDetails().parseVotingDetails(topicPageText);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private TagNode getSingleElement(TagNode node, String elementClass)
    {
        TagNode[] nodes = node.getElementsByAttValue("class", elementClass, true, false);

        if (null == nodes || nodes.length == 0 || null == nodes[0])
            return null;

        return nodes[0];
    }

    @Override
    protected void onPostExecute(String result)
    {
        DataProvider.onDownloadComplete(topic);
    }

    @Override
    protected void onProgressUpdate(Integer... progress)
    {
    }
}
