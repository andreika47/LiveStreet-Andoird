package com.andreika47.livestreetandroid.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.andreika47.livestreetandroid.Adapters.TopicListItem;
import com.andreika47.livestreetandroid.DataProvider;
import com.andreika47.livestreetandroid.R;
import com.andreika47.livestreetandroid.Topic;
import com.andreika47.livestreetandroid.TopicDownloadCallback;
import com.andreika47.livestreetandroid.TopicFormatter;

public class TopicActivity extends AppCompatActivity implements TopicDownloadCallback
{
    private WebView webView;
    private TopicListItem topicListItem;
    private TopicFormatter formatter;

    public static final String TOPIC_URL = "TOPIC_URL";
    public static final String TOPIC_TITLE = "TOPIC_TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_topic);
        formatter = new TopicFormatter(this);
        webView = (WebView)findViewById(R.id.wv);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient());
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            getSupportActionBar().setTitle(bundle.getString(TOPIC_TITLE));
            reloadTopic(bundle.getString(TOPIC_URL));
        }
    }

    @Override
    public void onTopicDownloadComplete(Topic topic)
    {
        topicListItem = topic;
        showTopic();
    }

    private void showTopic()
    {
        final String topicText = formatter.format(topicListItem);
        webView.loadDataWithBaseURL("file://android_asset", topicText, "text/html", "UTF-8", null);

        TextView titleView = (TextView)findViewById(R.id.tvTitle);
        titleView.setText(topicListItem.getTitle());
    }

    private void reloadTopic(String url)
    {
        topicListItem = DataProvider.getTopic(url, this);
    }
}
