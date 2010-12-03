package net.pshared.sbmcounter.activity;

import java.util.ArrayList;
import java.util.List;

import net.pshared.sbmcounter.R;
import net.pshared.sbmcounter.model.Bookmark;
import net.pshared.sbmcounter.model.BookmarkResult;
import net.pshared.sbmcounter.service.BookmarkService;
import net.pshared.sbmcounter.service.DeliciousBookmarkService;
import net.pshared.sbmcounter.service.HatenaBookmarkService;
import net.pshared.sbmcounter.service.TweetMemeBookmarkService;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SBMCounterActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmarks);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }

        String action = intent.getAction();
        if (!Intent.ACTION_SEND.equals(action)) {
            finish();
            return;
        }

        String url = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (url == null) {
            finish();
            return;
        }

        List<BookmarkService> services = new ArrayList<BookmarkService>();
        services.add(new HatenaBookmarkService());
        services.add(new DeliciousBookmarkService());
        services.add(new TweetMemeBookmarkService());

        ListView listView = (ListView) findViewById(R.id.bookmarks_listView);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                Bookmark bookmark = (Bookmark) listView.getItemAtPosition(position);
                if (bookmark.getLink() != null) {
                    Uri url = Uri.parse(bookmark.getLink());
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(url);
                    startActivity(intent);
                }
            }
        });

        List<AsyncTask<String, Integer, BookmarkResult>> tasks = new ArrayList<AsyncTask<String, Integer, BookmarkResult>>();
        final BookmarkArrayAdapter adapter = new BookmarkArrayAdapter(this, R.layout.bookmark_row);
        for (final BookmarkService service : services) {
            final Bookmark bookmark = new Bookmark(service.getId(), service.getIcon(this));
            adapter.add(bookmark);

            AsyncTask<String, Integer, BookmarkResult> task = new AsyncTask<String, Integer, BookmarkResult>() {

                @Override
                protected BookmarkResult doInBackground(String... params) {
                    return service.getCount(params[0]);
                }

                @Override
                protected void onPostExecute(BookmarkResult result) {
                    if (result == null) {
                        return;
                    }

                    bookmark.setCount(result.getCount());
                    bookmark.setLink(result.getLink());
                    adapter.notifyDataSetChanged();
                }
            };
            tasks.add(task);
        }

        listView.setAdapter(adapter);

        for (AsyncTask<String, Integer, BookmarkResult> task : tasks) {
            task.execute(url);
        }
    }
}