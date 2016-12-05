package com.andreika47.livestreetandroid;

import com.andreika47.livestreetandroid.Adapters.TopicListItem;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class representing the topic
 *
 */
public class Topic extends TopicListItem
{
    // Regular expression used to extract topic front image from topic HTML text
    private static final String REGEXP_IMAGE_URL = "<img.*src=\\\"([^\\\"]*)\\\".*>";

    private String author = "";	// Topic author username
    private String blogUrl = "";	// Topic blog URL
    private String content = "";	// Topic HTML content
    private TopicStatus status = TopicStatus.STATUS_INITIAL;	// Topic download status
    private VotingData votingData = new VotingData();	// Information needed to vote for topic

    public Topic(String _topicUrl)
    {
        topicUrl  = _topicUrl;
    }

    public String getBlogUrl()
    {
        return blogUrl;
    }

    public String getContent()
    {
        return content;
    }

    public String getAuthor()
    {
        return author;
    }

    public TopicStatus getStatus()
    {
        return status;
    }

    public String getTopicUrl()
    {
        return topicUrl;
    }

    public void setTitle(String _title)
    {
        title = _title;
    }

    public void setContent(String _content)
    {
        content = _content;
    }

    public void setAuthor(String _author)
    {
        author = _author;
    }

    public void setStatus(TopicStatus _status)
    {
        status = _status;
    }

    public void setBlog(String _blog, String _blogUrl)
    {
        blog = _blog;
        blogUrl = _blogUrl;
    }

    public void setDate(Date _date)
    {
        date = _date;
    }

    public boolean getDownloadComplete()
    {
        return status == TopicStatus.STATUS_COMPLETE;
    }

    public VotingData getVotingDetails()
    {
        return votingData;
    }

    public String getFrontImageUrl()
    {
        if (content.length() == 0)
        {
            return "";
        }

        // TODO move all this into separate class
        //
        Pattern pattern = Pattern.compile(REGEXP_IMAGE_URL, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);
        boolean matchFound = matcher.find();

        if (!matchFound || matcher.groupCount() < 1)
        {
            // not found
            return "";
        }

        return matcher.group(1);
    }
}
