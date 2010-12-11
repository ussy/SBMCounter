package net.pshared.sbmcounter.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.pshared.sbmcounter.model.BookmarkResult;
import net.pshared.sbmcounter.model.CommentResult;
import net.pshared.sbmcounter.util.IOUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public abstract class AbstractBookmarkService implements BookmarkService {

    private int id;

    abstract String getCountApiUrl(String url);

    abstract String getCommentApiUrl(String url);

    abstract BookmarkResult parseCount(String response) throws Exception;

    abstract List<CommentResult> parseComments(String response) throws Exception;

    public AbstractBookmarkService(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public BookmarkResult getCount(String url) {
        InputStream is = null;
        try {
            String apiUrl = getCountApiUrl(url);
            is = getInputStream(apiUrl);
            byte[] bytes = IOUtils.read(is);
            return parseCount(new String(bytes));
        } catch (Exception e) {
            Log.e("error", e.getMessage(), e);
            return null;
        } finally {
            IOUtils.close(is);
        }
    }

    public List<CommentResult> getComments(String url) {
        InputStream is = null;
        try {
            String apiUrl = getCommentApiUrl(url);
            is = getInputStream(apiUrl);
            byte[] bytes = IOUtils.read(is);
            return parseComments(new String(bytes));
        } catch (Exception e) {
            Log.e("error", e.getMessage(), e);
            return null;
        } finally {
            IOUtils.close(is);
        }
    }

    Bitmap getIcon(Context context, String resource) {
        InputStream is = null;
        try {
            is = context.getAssets().open(resource);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            return null;
        } finally {
            IOUtils.close(is);
        }
    }

    InputStream getInputStream(String url) throws IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            return null;
        }

        return entity.getContent();
    }
}
