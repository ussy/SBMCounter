package net.pshared.sbmcounter.service;

import java.util.Collection;
import java.util.LinkedHashMap;

import net.pshared.sbmcounter.R;

public class BookmarkServiceFactory {

    private static LinkedHashMap<Integer, BookmarkService> map = new LinkedHashMap<Integer, BookmarkService>();

    static {
        map.put(R.string.hatebu, new HatenaBookmarkService());
        map.put(R.string.delicious, new DeliciousBookmarkService());
        map.put(R.string.tweetmeme, new TweetMemeBookmarkService());
    }

    public static Collection<BookmarkService> getBookmarkServices() {
        return map.values();
    }

    public static BookmarkService getBookmarkService(int id) {
        return map.get(id);
    }
}
