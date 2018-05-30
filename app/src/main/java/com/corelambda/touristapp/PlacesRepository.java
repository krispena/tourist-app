package com.corelambda.touristapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.corelambda.touristapp.datamodel.QueryResponse;
import com.corelambda.touristapp.datamodel.WikipediaPage;
import com.corelambda.touristapp.datamodel.WikipediaResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlacesRepository {

    public LiveData<List<WikipediaPage>> getTouristSites() {
        final MutableLiveData<List<WikipediaPage>> liveData = new MutableLiveData<>();

        AsyncTask<Void, Void, List<WikipediaPage>> task = new AsyncTask<Void, Void, List<WikipediaPage>>() {
            @Override
            protected List<WikipediaPage> doInBackground(Void[] objects) {
                List<WikipediaPage> places = null;
                try {
                    String response = doRequest();
                    places = handleResponse(response);
                    liveData.postValue(places);
                } catch (Exception ex) {
                    Log.e("PlacesRepository", "FAILED!!! ", ex);
                    //FIXME handle errors
                }
                return places;
            }
        };
        task.execute();

        return liveData;
    }

    private String doRequest() throws IOException {
        String urlString = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=pageimages&generator=geosearch&pithumbsize=250&ggscoord=10.7712404%7C106.6978887&ggsradius=10000";

        StringBuilder response = new StringBuilder();

        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } finally {
            urlConnection.disconnect();
        }

        return response.toString();
    }

    private List<WikipediaPage> handleResponse(String response) {
        Gson gson = new Gson();
        WikipediaResponse wikipediaResponse = gson.fromJson(response, WikipediaResponse.class);
        QueryResponse queryResponse = wikipediaResponse.getQuery();
        Map<Integer, WikipediaPage> pagesMap = queryResponse.getPages();
        List<WikipediaPage> pages = new ArrayList<>(pagesMap.values());
        return pages;
    }

//    private List<String> handleResponse(String response)
//            throws JSONException {
//
//        List<String> placeNames = new ArrayList<>();
//
//        JSONObject responseObject = new JSONObject(response);
//        JSONObject queryObject = responseObject.getJSONObject("query");
//        JSONObject pagesObject = queryObject.getJSONObject("pages");
//
//        Iterator<String> keys = pagesObject.keys();
//        while (keys.hasNext()) {
//            JSONObject page = pagesObject.getJSONObject(keys.next());
//            String title = page.getString("title");
//            placeNames.add(title);
//        }
//
//        return placeNames;
//    }

}
