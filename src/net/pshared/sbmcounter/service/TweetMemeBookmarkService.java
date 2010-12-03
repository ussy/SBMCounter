package net.pshared.sbmcounter.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.pshared.sbmcounter.R;
import net.pshared.sbmcounter.model.BookmarkResult;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;

public class TweetMemeBookmarkService extends AbstractBookmarkService implements BookmarkService {

    private static final String API_URL = "http://api.tweetmeme.com/url_info.json?url=%s";

    public TweetMemeBookmarkService() {
        super(R.string.tweetmeme);
    }

    @Override
    String getCountApiUrl(String url) {
        try {
            return String.format(API_URL, URLEncoder.encode(url, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    @Override
    public Bitmap getIcon(Context context) {
        return getIcon(context, "twitter.png");
    }

    BookmarkResult parse(String response) throws Exception {
        if (response == null) {
            return null;
        }

        JSONObject json = new JSONObject(response);
        String status = json.getString("status");
        if (!"success".equals(status)) {
            return null;
        }

        JSONObject story = json.getJSONObject("story");
        int count = Integer.parseInt(story.getString("url_count"));
        String link = story.getString("tm_link");
        return new BookmarkResult(count, link);
    }
}
