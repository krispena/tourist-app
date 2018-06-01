package com.corelambda.touristapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.corelambda.touristapp.datamodel.WikipediaPage;

import java.util.List;

public class PlacesViewModel extends ViewModel {

    private LiveData<List<WikipediaPage>> touristSitesData;
    private PlacesRepository placesRepository;

    public PlacesViewModel(PlacesRepository repo) {

        placesRepository = repo;
        touristSitesData = placesRepository.getTouristSites();
    }

    public LiveData<List<WikipediaPage>> getTouristSites() {
        return touristSitesData;
    }

    public static class PlacesViewModelFactory implements ViewModelProvider.Factory {

        private PlacesRepository placesRepository;

        public PlacesViewModelFactory(PlacesRepository placesRepository) {
            this.placesRepository = placesRepository;
        }

        @NonNull
        @Override
        public PlacesViewModel create(@NonNull Class modelClass) {
            return new PlacesViewModel(placesRepository);
        }
    }

}
