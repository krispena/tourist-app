package com.corelambda.touristapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.corelambda.touristapp.datamodel.WikipediaPage;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private PlacesViewModel placesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup RecyclerView Setup
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Handle Places ViewModel
        placesViewModel = ViewModelProviders.of(this, createViewModelFactory()).get(PlacesViewModel.class);
        LiveData<List<WikipediaPage>> placesData = placesViewModel.getTouristSites();
        placesData.observe(this, new Observer<List<WikipediaPage>>() {
            @Override
            public void onChanged(@Nullable List<WikipediaPage> touristSites) {
                recyclerAdapter = new TouristRecyclerAdapter(touristSites);
                recyclerView.setAdapter(recyclerAdapter);
            }
        });
    }

    private PlacesViewModel.PlacesViewModelFactory createViewModelFactory() {
        Retrofit retrofit = createRetrofit();
        WikipediaService wikipediaService = retrofit.create(WikipediaService.class);
        PlacesRepository placesRepository = new PlacesRepository(wikipediaService);
        PlacesViewModel.PlacesViewModelFactory factory = new PlacesViewModel.PlacesViewModelFactory(placesRepository);
        return factory;
    }

    private Retrofit createRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://en.wikipedia.org")
                .build();
        return retrofit;
    }

}
