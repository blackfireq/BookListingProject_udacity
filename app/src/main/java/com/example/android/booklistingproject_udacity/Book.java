package com.example.android.booklistingproject_udacity;

import static android.R.attr.description;

/**
 * Created by mikem on 5/31/2017.
 */

public class Book {

    private String mTitle;
    private String mAuthorList;
    private String mPreviewLink;

    public Book(String title, String authorList, String previewLink){
        mTitle = title;
        mAuthorList = authorList;
        mPreviewLink = previewLink;
    }

    public String getTitle() {return mTitle;}
    public String getAuthorList() {return mAuthorList;}
    public String getPreviewLink(){return mPreviewLink;}
}
