package com.example.android.booklistingproject_udacity;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static android.R.attr.author;
import static android.R.attr.description;
import static com.example.android.booklistingproject_udacity.BookActivity.LOG_TAG;

/**
 * Created by mikem on 5/31/2017.
 */

public final class QueryUtils {

    private QueryUtils() {
    }

    public static ArrayList<Book> fetchBookData(String requestUrl){

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link books} object
        ArrayList<Book> books = extractBooks(jsonResponse);

        // Return the {@link books ArrayList}
        return books;

    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the Book JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<Book> extractBooks(String jsonResponse){

        // Create an empty ArrayList that we can start adding books to
        ArrayList<Book> books = new ArrayList<>();


        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try{
            // Convert SAMPLE_JSON_RESPONSE String into a JSONObject
            JSONObject root = new JSONObject(jsonResponse);

            // Extract “items” JSONArray
            JSONArray items = root.getJSONArray("items");

            for(int i=0;i<items.length();i++) {

                JSONObject item = items.getJSONObject(i);

                //Extract volumeInfo
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                // Extract Title
                String title = volumeInfo.getString("title");

                //Extract Authors
                String authorList ="";
                if(volumeInfo.has("authors")){
                    JSONArray authors = volumeInfo.getJSONArray("authors");
                    //needs work
                    for (int a = 0; a < authors.length(); a++) {
                        String currentAuthor = authors.getString(a);
                            authorList += currentAuthor + ", ";
                    }
                } else { authorList = "No Authors Listed"; }

                //Extract previewLink
                String previewLink = volumeInfo.getString("previewLink");

                books.add(new Book(title,authorList,previewLink));
            }


        } catch (JSONException e){
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the Books JSON results", e);
        }
        return books;
    }

}
