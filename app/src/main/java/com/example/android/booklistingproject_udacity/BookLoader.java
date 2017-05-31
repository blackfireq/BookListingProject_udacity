package com.example.android.booklistingproject_udacity;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikem on 5/31/2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    /** Tag for log messages */
    private static final String LOG_TAG = BookLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public BookLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null){
            return null;
        }
        ArrayList<Book> books = QueryUtils.fetchBookData(mUrl);
        return books;
    }
}
