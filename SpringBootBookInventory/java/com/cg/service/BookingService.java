package com.cg.service;

import java.time.LocalDate;
import java.util.List;

import com.cg.entity.*;

public interface BookingService {

    Customer login(String email, String password);

    List<Schedule> searchSchedule(String source, String destination, LocalDate date);

    Booking bookSeats(int customerId, int scheduleId, List<Passenger> passengers);

    List<Booking> getBookingsByCustomer(int customerId);

    List<Passenger> getPassengersByBooking(int bookingId);
    
    Schedule getScheduleById(int id);
    
    List<Integer> getBookedSeats(int scheduleId);
}
