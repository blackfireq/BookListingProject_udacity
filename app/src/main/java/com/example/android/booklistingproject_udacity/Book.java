package com.example.android.booklistingproject_udacity;

import static android.R.attr.description;

/**
 * Created by mikem on 5/31/2017.
 */

public class Book {

    private String mTitle;
    private String mDescription;
    private String mImageResourceId;
    private String mPreviewLink;

    public Book(String title, String description,String imageResourceId, String previewLink){
        mTitle = title;
        mDescription = description;
        mImageResourceId = imageResourceId;
        mPreviewLink = previewLink;
    }

    public String getTitleMain() {return mTitle;}
    public String getDescription() {return mDescription;}
    public String getmImageResourceId() {return mImageResourceId;}
    public String getPreviewLink(){return mPreviewLink;}
}
