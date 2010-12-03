package net.pshared.sbmcounter.model;

public class BookmarkResult {

    private int count;

    private String link;

    public BookmarkResult(int count, String link) {
        this.count = count;
        this.link = link;
    }

    public int getCount() {
        return count;
    }

    public String getLink() {
        return link;
    }

}
