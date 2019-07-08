package com.taboola.sdksample.main;

public class FakeItemModel {

    private String title;
    private String imageUrl;

    FakeItemModel(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
