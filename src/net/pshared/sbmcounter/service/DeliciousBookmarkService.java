package net.pshared.sbmcounter.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import net.pshared.sbmcounter.R;
import net.pshared.sbmcounter.model.BookmarkResult;
import net.pshared.sbmcounter.model.CommentResult;
import net.pshared.sbmcounter.util.MD5;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;

public class DeliciousBookmarkService extends AbstractBookmarkService implements BookmarkService {

    private static final String LINK_URL = "http://delicious.com/url/%s";

    private static final String API_URL = "http://feeds.delicious.com/v2/json/urlinfo/blogbadge?url=%s";

    private static final String COMMENT_URL = "http://feeds.delicious.com/v2/json/url/check?count=100&url=%s";

    public DeliciousBookmarkService() {
        super(R.string.delicious);
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
        return getIcon(context, "delicious.png");
    }

    @Override
    public BookmarkResult getCount(String url) {
        BookmarkResult bookmark = super.getCount(url);
        if (bookmark == null) {
            String hashedUrl = MD5.getInstance().hexdigest(url);
            return new BookmarkResult(0, String.format(LINK_URL, hashedUrl));
        }

        return bookmark;
    }

    @Override
    BookmarkResult parseCount(String response) throws Exception {
        if (response == null) {
            return null;
        }

        JSONArray json = new JSONArray(response);
        if (json.length() == 0) {
            return null;
        }

        JSONObject result = json.getJSONObject(0);
        int count = Integer.parseInt(result.getString("total_posts"));
        String link = String.format(LINK_URL, result.getString("hash"));
        return new BookmarkResult(count, link);
    }

    @Override
    List<CommentResult> parseComments(String response) throws Exception {
        ArrayList<CommentResult> comments = new ArrayList<CommentResult>();
        if (response == null) {
            return comments;
        }

        JSONArray bookmarks = new JSONArray(response);
        for (int i = 0; i < bookmarks.length(); i++) {
            JSONObject bookmark = bookmarks.getJSONObject(i);
            String name = bookmark.getString("a");
            String comment = bookmark.getString("n");

            if (comment != null && comment.length() > 0) {
                comments.add(new CommentResult(name, comment));
            }
        }

        return comments;
    }
}
