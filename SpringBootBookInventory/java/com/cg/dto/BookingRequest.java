package com.cg.dto;

import java.util.List;
import com.cg.entity.Passenger;

public class BookingRequest {

    private int customerId;
    private int scheduleId;
    private List<Passenger> passengers;

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getScheduleId() { return scheduleId; }
    public void setScheduleId(int scheduleId) { this.scheduleId = scheduleId; }

    public List<Passenger> getPassengers() { return passengers; }
    public void setPassengers(List<Passenger> passengers) { this.passengers = passengers; }
}