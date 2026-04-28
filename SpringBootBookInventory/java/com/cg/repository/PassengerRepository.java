package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.entity.Passenger;
import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
    @Query("SELECT p.seatNumber FROM Passenger p WHERE p.booking.schedule.scheduleId = :scheduleId")
    List<Integer> findBookedSeatsByScheduleId(@Param("scheduleId") int scheduleId);
    
    List<Passenger> findByBooking_BookingId(int bookingId);
}