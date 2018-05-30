package com.corelambda.touristapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.corelambda.touristapp.datamodel.WikipediaPage;

import java.util.List;

public class PlacesViewModel extends ViewModel {

    private LiveData<List<WikipediaPage>> touristSitesData;
    private PlacesRepository placesRepository;

    public PlacesViewModel() {

        placesRepository = new PlacesRepository();
        touristSitesData = placesRepository.getTouristSites();
    }

    public LiveData<List<WikipediaPage>> getTouristSites() {
        return touristSitesData;
    }


}
