package com.example.android.booklistingproject_udacity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mikem on 5/31/2017.
 */

public class BookAdapter  extends ArrayAdapter<Book>{

    public BookAdapter(Activity context, ArrayList<Book> Book){
        super(context, 0, Book);
    }

    //override the getview to diplay the layout
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        //getters

        //get the position of the current object in the ArrayList
        Book currentBook = getItem(position);

        //get the view that will diplay the main title of the current book
        TextView title_main = (TextView)convertView.findViewById(R.id.title_view);

        //get the view that will diplay the description of the current book
        TextView description = (TextView)convertView.findViewById(R.id.description_view);

        //get the view that will diplay the image of the current book
        ImageView thumbnail = (ImageView)convertView.findViewById(R.id.thumbnail_view);

        //setters

        //set the main title of the current book
        title_main.setText(currentBook.getTitleMain());
        //set the description of the current book
        description.setText(currentBook.getDescription());
        //set the thumbnail of the current book
        thumbnail.setImageResource(R.mipmap.ic_launcher);

        return convertView;
    }
}
