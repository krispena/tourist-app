package com.corelambda.touristapp;

import com.corelambda.touristapp.datamodel.WikipediaResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WikipediaService {
    @GET("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=pageimages&generator=geosearch&pithumbsize=250&ggscoord=10.7712404%7C106.6978887&ggsradius=10000")
    Call<WikipediaResponse> getPlaces();
}
