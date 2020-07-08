package com.example.bhojnalya.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    String FoodDiscription;
    String location;
    String transport;

    public HomeViewModel() {

    }

    public String getFoodDiscription() {
        return FoodDiscription;
    }

    public void setFoodDiscription(String foodDiscription) {
        FoodDiscription = foodDiscription;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }
}