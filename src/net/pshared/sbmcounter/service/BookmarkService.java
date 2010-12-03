package net.pshared.sbmcounter.service;

import net.pshared.sbmcounter.model.BookmarkResult;
import android.content.Context;
import android.graphics.Bitmap;

public interface BookmarkService {

    int getId();

    Bitmap getIcon(Context context);

    BookmarkResult getCount(String url);
}
