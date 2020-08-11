package com.example.bhojnalya.ui.Sort;

public class TransportModel {
String Description ,Pick_up_location, Pick_up_Phone_number , Delivery_location, Delivery_phone_Number, Pick_up_name, Delivery_name,From_pickup_Uid,To_deliver_Uid; ;

    public String getFrom_pickup_Uid() {
        return From_pickup_Uid;
    }

    public void setFrom_pickup_Uid(String from_pickup_Uid) {
        From_pickup_Uid = from_pickup_Uid;
    }

    public String getTo_deliver_Uid() {
        return To_deliver_Uid;
    }

    public void setTo_deliver_Uid(String to_deliver_Uid) {
        To_deliver_Uid = to_deliver_Uid;
    }

    public String getPick_up_name() {
        return Pick_up_name;
    }

    public void setPick_up_name(String pick_up_name) {
        Pick_up_name = pick_up_name;
    }

    public String getDelivery_name() {
        return Delivery_name;
    }

    public void setDelivery_name(String delivery_name) {
        Delivery_name = delivery_name;
    }

    public TransportModel(){

}

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPick_up_location() {
        return Pick_up_location;
    }

    public void setPick_up_location(String pick_up_location) {
        Pick_up_location = pick_up_location;
    }

    public String getPick_up_Phone_number() {
        return Pick_up_Phone_number;
    }

    public void setPick_up_Phone_number(String pick_up_Phone_number) {
        Pick_up_Phone_number = pick_up_Phone_number;
    }

    public String getDelivery_location() {
        return Delivery_location;
    }

    public void setDelivery_location(String delivery_location) {
        Delivery_location = delivery_location;
    }

    public String getDelivery_phone_Number() {
        return Delivery_phone_Number;
    }

    public void setDelivery_phone_Number(String delivery_phone_Number) {
        Delivery_phone_Number = delivery_phone_Number;
    }
}
