package net.pshared.sbmcounter.activity;

import java.util.List;

import net.pshared.sbmcounter.R;
import net.pshared.sbmcounter.model.CommentResult;
import net.pshared.sbmcounter.service.BookmarkService;
import net.pshared.sbmcounter.service.BookmarkServiceFactory;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

public class SBMCommentActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }

        final String url = intent.getStringExtra("url");
        final int id = intent.getIntExtra("id", -1);
        setTitle(id);

        AsyncTask<Object, Integer, List<CommentResult>> task = new AsyncTask<Object, Integer, List<CommentResult>>() {

            private ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                String message = getResources().getString(R.string.loading);
                dialog = ProgressDialog.show(SBMCommentActivity.this, null, message, true, true,
                    new OnCancelListener() {

                        @Override
                        public void onCancel(DialogInterface dialog) {
                            cancel(true);
                            finish();
                        }
                    });
            }

            @Override
            protected List<CommentResult> doInBackground(Object... params) {
                BookmarkService bookmarkService = BookmarkServiceFactory.getBookmarkService(id);
                return bookmarkService.getComments(url);
            }

            @Override
            protected void onPostExecute(List<CommentResult> result) {
                dialog.dismiss();

                ListView listView = (ListView) findViewById(R.id.comments_listView);
                CommentArrayAdapter adapter = new CommentArrayAdapter(SBMCommentActivity.this, R.layout.comment_row, result);
                listView.setAdapter(adapter);
            }
        };

        task.execute();
    }
}
