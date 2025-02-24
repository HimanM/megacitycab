package com.example.megacitycab.dao.Interfaces;
import com.example.megacitycab.model.Assignment;

public interface BookingAssignmentDAO {
    boolean insertBookingAssignment(Assignment assignment);
    boolean deleteBookingAssignment(int id);
    Assignment getAssignmentByBookingId(int bookingId);
    boolean updateDriver(int id, int newDriverId);
}
