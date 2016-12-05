package com.andreika47.livestreetandroid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Андрей on 16.11.2016.
 */

public class VotingData
{
    private static final String REGEXP_SECURITY_KEY = "LIVESTREET_SECURITY_KEY.*'([0-9a-f]*)'";
    private static final String REGEXP_VOTE_TOPIC_ID = "vote\\((\\d+),this,1,'topic'\\);";
    private String securityKey = "";
    private String topicId = "";

    public void parseVotingDetails(String topicText) throws Exception
    {
        parseSecurityKey(topicText);
        parseVoteTopicId(topicText);
    }

    private void parseVoteTopicId(String topicText) throws Exception
    {
        Pattern pattern = Pattern.compile(REGEXP_VOTE_TOPIC_ID, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(topicText);
        boolean matchFound = matcher.find();

        if (!matchFound || matcher.groupCount() < 1)
            throw new Exception(String.valueOf(R.string.errorParsingPage));
        topicId  = matcher.group(1);
    }

    private void parseSecurityKey(String topicText) throws Exception
    {
        Pattern pattern = Pattern.compile(REGEXP_SECURITY_KEY, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(topicText);
        boolean matchFound = matcher.find();

        if (!matchFound || matcher.groupCount() < 1)
            throw new Exception(String.valueOf(R.string.errorParsingPage));
        securityKey = matcher.group(1);
    }
    public String getVotingTopicId()
    {
        return topicId;
    }

    public String getVotingLSSecureKey()
    {
        return securityKey;
    }
}