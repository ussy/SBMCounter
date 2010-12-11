package net.pshared.sbmcounter.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.pshared.sbmcounter.R;
import net.pshared.sbmcounter.model.BookmarkResult;
import net.pshared.sbmcounter.model.CommentResult;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

public class TweetMemeBookmarkService extends AbstractBookmarkService implements BookmarkService {

    private static final String API_URL = "http://api.tweetmeme.com/url_info.json?url=%s";

    private static final String COMMENT_URL = "http://api.tweetmeme.com/v2/url/tweets.json?url=%s";

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
    String getCommentApiUrl(String url) {
        try {
            return String.format(COMMENT_URL, URLEncoder.encode(url, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    @Override
    public Bitmap getIcon(Context context) {
        return getIcon(context, "twitter.png");
    }

    @Override
    BookmarkResult parseCount(String response) throws Exception {
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

    @Override
    List<CommentResult> parseComments(String response) throws Exception {
        ArrayList<CommentResult> comments = new ArrayList<CommentResult>();
        if (response == null) {
            return comments;
        }

        JSONObject json = new JSONObject(response);
        String status = json.getString("status");
        if (!"success".equals(status)) {
            return comments;
        }

        JSONArray tweets = json.getJSONArray("tweets");
        for (int i = 0; i < tweets.length(); i++) {
            JSONObject tweet = tweets.getJSONObject(i);
            JSONObject user = tweet.getJSONObject("user");
            String name = user.getString("screen_name");
            String comment = tweet.getString("text");
            String createdAt = tweet.getString("created_at");
            if (comment != null && comment.length() > 0) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                try {
                    Date date = format.parse(createdAt);
                    comments.add(new CommentResult(name, comment, date));
                } catch (ParseException e) {
                    Log.e("error", "parse error", e);
                }
            }
        }

        return comments;
    }
}
