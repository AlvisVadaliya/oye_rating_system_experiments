package com.oye.ratingsystem.repository;

import com.oye.ratingsystem.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RideRepository extends JpaRepository<Ride, Integer> {

    @Modifying
    @Transactional
    @Query("update Ride set driver_rate = ?2 where id = ?1")
    Integer updateDriverRating(Integer rideId , Float rate);

    @Modifying
    @Transactional
    @Query("update Ride set passenger_rate = ?2 where id = ?1")
    Integer updatePassengerRating(Integer passengerId , Float rate);
}
