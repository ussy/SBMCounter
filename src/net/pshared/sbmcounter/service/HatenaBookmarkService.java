package net.pshared.sbmcounter.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.pshared.sbmcounter.R;
import net.pshared.sbmcounter.model.BookmarkResult;

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
    public Bitmap getIcon(Context context) {
        return getIcon(context, "hatebu.png");
    }

    @Override
    public BookmarkResult getCount(String url) {
        BookmarkResult result = super.getCount(url);
        return result == null ? new BookmarkResult(0, String.format(LINK_URL, url)) : result;
    };

    BookmarkResult parse(String s) throws Exception {
        if (s == null || "null".equals(s)) {
            return null;
        }

        JSONObject json = new JSONObject(s);
        int count = Integer.parseInt(json.getString("count"));
        String link = String.format(LINK_URL, json.get("url"));
        return new BookmarkResult(count, link);
    }
}
