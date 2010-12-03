package net.pshared.sbmcounter.model;

import android.graphics.Bitmap;

public class Bookmark {

    private Bitmap icon;

    private int id;

    private int count = -1;

    private String link;

    public Bookmark(int id, Bitmap icon) {
        this.id = id;
        this.icon = icon;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
