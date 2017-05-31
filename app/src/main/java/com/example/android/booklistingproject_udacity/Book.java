package com.example.android.booklistingproject_udacity;

import static android.R.attr.description;

/**
 * Created by mikem on 5/31/2017.
 */

public class Book {

    private String mTitleMain;
    private String mTitleSub;
    private String mDescription;
    private String mImageResourceId;

    public Book(String title_main, String title_sub, String description,String imageResourceId){
        mTitleMain = title_main;
        mTitleSub = title_sub;
        mDescription = description;
        mImageResourceId = imageResourceId;
    }

    public String getTitleMain() {return mTitleMain;}
    public String getTitleSub() {return mTitleSub;}
    public String getDescription() {return mDescription;}
    public String getmImageResourceId() {return mImageResourceId;}
}
