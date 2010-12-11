package net.pshared.sbmcounter.activity;

import java.text.SimpleDateFormat;
import java.util.List;

import net.pshared.sbmcounter.R;
import net.pshared.sbmcounter.model.CommentResult;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CommentArrayAdapter extends ArrayAdapter<CommentResult> {

    private LayoutInflater inflater;

    public CommentArrayAdapter(Context context, int textViewResourceId, List<CommentResult> comments) {
        super(context, textViewResourceId, comments);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.comment_row, null);
        }

        CommentResult comment = getItem(position);

        TextView nameView = (TextView) view.findViewById(R.id.commentRow_nameText);
        nameView.setText(comment.getName());

        TextView dateView = (TextView) view.findViewById(R.id.commentRow_dateText);
        if (comment.getDate() != null) {
            SimpleDateFormat format = new SimpleDateFormat();
            dateView.setText(format.format(comment.getDate()));
        } else {
            dateView.setText("");
        }

        TextView commentView = (TextView) view.findViewById(R.id.commentRow_commentText);
        commentView.setText(comment.getComment());

        return view;
    }
}
