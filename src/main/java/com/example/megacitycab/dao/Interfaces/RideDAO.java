package com.example.megacitycab.dao.Interfaces;

import com.example.megacitycab.model.DTO.Ride;

public interface RideDAO {
    Ride getRideByDriverId(int driverId);
}
