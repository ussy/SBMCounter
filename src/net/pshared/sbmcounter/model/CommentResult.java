package net.pshared.sbmcounter.model;

public class CommentResult {

    private String name;

    private String comment;

    public CommentResult(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

}
