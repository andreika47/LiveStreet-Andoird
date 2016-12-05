package com.andreika47.livestreetandroid;

/**
 * Created by Андрей on 16.11.2016.
 */

public enum TopicStatus
{
    STATUS_INITIAL,        // Initial status after object creation
    STATUS_BRIEF,          // Contains information from RSS (title, author, brief text)
    STATUS_DOWNLOADING,    // Downloading topic from HTML page
    STATUS_COMPLETE;       // Download complete
}
