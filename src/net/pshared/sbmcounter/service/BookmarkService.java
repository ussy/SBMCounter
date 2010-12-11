package net.pshared.sbmcounter.service;

import java.util.List;

import net.pshared.sbmcounter.model.BookmarkResult;
import net.pshared.sbmcounter.model.CommentResult;
import android.content.Context;
import android.graphics.Bitmap;

public interface BookmarkService {

    int getId();

    Bitmap getIcon(Context context);

    BookmarkResult getCount(String url);
    
    List<CommentResult> getComments(String url);
}
