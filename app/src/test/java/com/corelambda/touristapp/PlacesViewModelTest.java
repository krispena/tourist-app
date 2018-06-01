package com.corelambda.touristapp;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;

import com.corelambda.touristapp.datamodel.WikipediaPage;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.List;

public class PlacesViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void testGetTouristSites() {

        WikipediaService wikipediaService = new MockWikipediaService();

        PlacesRepository placesRepository = new PlacesRepository(wikipediaService);

        PlacesViewModel.PlacesViewModelFactory factory = new PlacesViewModel.PlacesViewModelFactory(placesRepository);

        PlacesViewModel viewModel = factory.create(PlacesViewModel.class);

        LiveData<List<WikipediaPage>> touristSites = viewModel.getTouristSites();

        Assert.assertEquals("Statue", touristSites.getValue().get(0).getTitle());
    }
}
