package net.pshared.sbmcounter.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import net.pshared.sbmcounter.R;
import net.pshared.sbmcounter.model.BookmarkResult;
import net.pshared.sbmcounter.model.CommentResult;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;

public class HatenaBookmarkService extends AbstractBookmarkService implements BookmarkService {

    private static final String LINK_URL = "http://b.hatena.ne.jp/entry/%s";

    private static final String API_URL = "http://b.hatena.ne.jp/entry/jsonlite/?url=%s";

    public HatenaBookmarkService() {
        super(R.string.hatebu);
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
        return getCountApiUrl(url);
    }

    @Override
    public Bitmap getIcon(Context context) {
        return getIcon(context, "hatebu.png");
    }

    @Override
    public BookmarkResult getCount(String url) {
        BookmarkResult result = super.getCount(url);
        return result == null ? new BookmarkResult(0, String.format(LINK_URL, url)) : result;
    }

    @Override
    BookmarkResult parseCount(String s) throws Exception {
        if (s == null || "null".equals(s)) {
            return null;
        }

        JSONObject json = new JSONObject(s);
        int count = Integer.parseInt(json.getString("count"));
        String link = String.format(LINK_URL, json.get("url"));
        return new BookmarkResult(count, link);
    }

    @Override
    List<CommentResult> parseComments(String response) throws Exception {
        ArrayList<CommentResult> comments = new ArrayList<CommentResult>();
        if (response == null || "null".equals(response)) {
            return comments;
        }

        JSONObject json = new JSONObject(response);
        JSONArray bookmarks = json.getJSONArray("bookmarks");
        for (int i = 0; i < bookmarks.length(); i++) {
            JSONObject bookmark = bookmarks.getJSONObject(i);
            String name = bookmark.getString("user");
            String comment = bookmark.getString("comment");
            if (comment != null && comment.length() > 0) {
                comments.add(new CommentResult(name, comment));
            }
        }

        return comments;
    }
}
