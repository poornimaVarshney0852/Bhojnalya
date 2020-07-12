package com.example.bhojnalya.ui.home;

import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    String FoodDiscription;
    String location;
    String transport;
    String Cooked_UnCooked;
    String QuantityMeasurement;
    String UserType;
    String Veg_NonVeg;
    String self_d_p;
    String feedAccepted;


    String UserId;

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public HomeViewModel() {

    }

    public String getFeedAccepted() {
        return feedAccepted;
    }

    public void setFeedAccepted(String feedAccepted) {
        this.feedAccepted = feedAccepted;
    }

    public String getSelf_d_p() {
        return self_d_p;
    }

    public void setSelf_d_p(String self_d_p) {
        this.self_d_p = self_d_p;
    }

    public String getCooked_UnCooked() {
        return Cooked_UnCooked;
    }

    public void setCooked_UnCooked(String cooked_UnCooked) {
        Cooked_UnCooked = cooked_UnCooked;
    }

    public String getQuantityMeasurement() {
        return QuantityMeasurement;
    }

    public void setQuantityMeasurement(String quantityMeasurement) {
        QuantityMeasurement = quantityMeasurement;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getVeg_NonVeg() {
        return Veg_NonVeg;
    }

    public void setVeg_NonVeg(String veg_NonVeg) {
        Veg_NonVeg = veg_NonVeg;
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

    public String getUserId() { return UserId; }

}