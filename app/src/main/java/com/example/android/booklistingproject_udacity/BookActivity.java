package com.example.android.booklistingproject_udacity;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.android.booklistingproject_udacity.R.id.keyword;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    public static final String LOG_TAG = BookActivity.class.getName();

    private static final String GOOGLE_BOOK_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    /**
     * Constant value for the book loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOK_LOADER_ID = 1;

    /** Adapter for the list of books */
    private BookAdapter mAdapter;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    /** Progress bar when loading initial data*/
    private ProgressBar mProgressBarView;

    /** keyword for search*/
    private String mKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find reference to ProgressBar
        mProgressBarView = (ProgressBar) findViewById(R.id.loading_spinner);

        /** check for internet connection*/
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        mEmptyStateTextView = (TextView) findViewById(R.id.emptyList);

        // new BookTask().execute(GOOGLE_URL);
        // Get a reference to the LoaderManager, in order to interact with loaders.
        final LoaderManager loaderManager = getLoaderManager();


        if(isConnected) {

            // Find a reference to the {@link ListView} in the layout
            ListView bookListView = (ListView) findViewById(R.id.list);

            //Find a reference to the {@link keyword} in the layout
            final EditText keyword = (EditText)findViewById(R.id.keyword);

            //Find a reference to the {@link searchButton}
            TextView searchButton = (TextView)findViewById(R.id.search_button);

            // Create a new {@link ArrayAdapter} of books
            mAdapter = new BookAdapter(this, new ArrayList<Book>());

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            bookListView.setAdapter(mAdapter);
            bookListView.setEmptyView(mEmptyStateTextView);

            // set onclicklistener to send to the webpage of the books thats clicked on
            // Set a click listener to play the audio when the list item is clicked on
            bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    // Get the {@link Books} object at the given position the user clicked on
                    Book currentBook = mAdapter.getItem(position);

                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri webUrl = Uri.parse(currentBook.getPreviewLink());

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, webUrl);
                    startActivity(browserIntent);

                }
            });

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //show snapper while loading
                    mProgressBarView.setVisibility(VISIBLE);

                    //get current keyword to search
                    mKeyword = keyword.getText().toString();

                    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                    // because this activity implements the LoaderCallbacks interface).
                    loaderManager.restartLoader(BOOK_LOADER_ID, null, BookActivity.this);

                }
            });
            if (keyword != null) {
                loaderManager.initLoader(BOOK_LOADER_ID, null, BookActivity.this);
            }
        } else {
            mEmptyStateTextView.setVisibility(VISIBLE);
            mEmptyStateTextView.setText(R.string.noInternet);
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {

        // Create a new loader for the given URL
        String url = updateURL(mKeyword);


            return new BookLoader(this, url);

    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        //hide progress bar when loading is done
        mProgressBarView.setVisibility(GONE);
        // Set empty state text to display "No books found."
        mEmptyStateTextView.setText(R.string.empty_list);

        mAdapter.clear();

        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }



    private String updateURL(String searchWord){
        //searchWord = cleanUpString(searchWord);
        if(searchWord !=null) {
            String updatedURL = GOOGLE_BOOK_URL+searchWord+"&maxResults=30";
            return updatedURL;
        }
      return null;
    }

    private String cleanUpString (String searchWord){
        //trim leading and trailing spaces
        if(searchWord != null) {
            searchWord.trim();
            while(searchWord.contains(" ")){
                searchWord.replace(" ","+");
            }
            return searchWord;
            }
            return null;
        }

        // replace the spaces with plus symbol


}
