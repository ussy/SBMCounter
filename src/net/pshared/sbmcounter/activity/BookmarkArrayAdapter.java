package net.pshared.sbmcounter.activity;

import net.pshared.sbmcounter.R;
import net.pshared.sbmcounter.model.Bookmark;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BookmarkArrayAdapter extends ArrayAdapter<Bookmark> {

    private LayoutInflater inflater;

    public BookmarkArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.bookmark_row, null);
        }

        Bookmark bookmark = getItem(position);
        ImageView iconView = (ImageView) view.findViewById(R.id.bookmarkRow_serviceIcon);
        iconView.setImageBitmap(bookmark.getIcon());

        TextView textView = (TextView) view.findViewById(R.id.bookmarkRow_nameText);
        textView.setText(bookmark.getId());

        TextView countView = (TextView) view.findViewById(R.id.bookmarkRow_countText);
        if (bookmark.getCount() > -1) {
            countView.setText(String.valueOf(bookmark.getCount()));
        } else {
            countView.setText("");
        }

        return view;
    }
}
