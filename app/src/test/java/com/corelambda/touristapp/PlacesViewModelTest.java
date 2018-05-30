package com.corelambda.touristapp;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;

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
        PlacesViewModel viewModel = new PlacesViewModel();

        LiveData<List<String>> touristSites = viewModel.getTouristSites();

        Assert.assertEquals("Statue", touristSites.getValue().get(0));
    }
}
