package com.example.bhojnalya.ui.Notification;

public class NotificationViewModel {
    String accepted_by,from_pickup_id,to_delivery,Description;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAccepted_by() {
        return accepted_by;
    }

    public void setAccepted_by(String accepted_by) {
        this.accepted_by = accepted_by;
    }

    public String getFrom_pickup_id() {
        return from_pickup_id;
    }

    public void setFrom_pickup_id(String from_pickup_id) {
        this.from_pickup_id = from_pickup_id;
    }

    public String getTo_delivery() {
        return to_delivery;
    }

    public void setTo_delivery(String to_delivery) {
        this.to_delivery = to_delivery;
    }
}
