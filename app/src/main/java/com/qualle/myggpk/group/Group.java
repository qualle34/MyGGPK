package com.qualle.myggpk.group;

public class Group {

    private int id;
    private String title;
    private String url;
    private String mainUrl;

    public Group() {
    }

    public Group(int id, String title, String url, String mainUrl) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.mainUrl = mainUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    @Override
    public String toString() {
        return id + " " + title;
    }
}
