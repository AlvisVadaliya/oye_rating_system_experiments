package com.oye.ratingsystem.service;

import com.oye.ratingsystem.contract.RatingContract;
import com.oye.ratingsystem.model.Driver;
import com.oye.ratingsystem.model.Passenger;
import com.oye.ratingsystem.repository.DriverRepository;
import com.oye.ratingsystem.repository.PassengerRepository;
import com.oye.ratingsystem.repository.PersonRepository;
import com.oye.ratingsystem.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RatingService implements RatingContract.Service {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    @Qualifier("responseRateDTO")
    private RatingContract.Dto.ResponseRate responseRateDTO;

    @Autowired
    @Qualifier("responseAvgRateDTO")
    private RatingContract.Dto.ResponseAvgRate responseAvgRateDTO;

    @Override
    public Boolean setDriverRating(RatingContract.Dto.RequestRate request) {
        if(!rideRepository.existsById(request.getRideId()))
            return false;
        setDriverAvgRating(request);
        return  rideRepository.updateDriverRating(request.getRideId(), request.getRate()) == 1;
    }

    private void setDriverAvgRating(RatingContract.Dto.RequestRate request) {
        Driver driver = rideRepository.getOne(request.getRideId()).getDriver();
        Float oldAvgRating = driver.getAvgRating();
        Float newDriverAvgRating = request.getRate();
        Float oldDriverRating = rideRepository.getOne(request.getRideId()).getDriverRate();
        Float newDriverRating = request.getRate();

        if(oldDriverRating != null && oldAvgRating != null){
            // already rated before so remove previous rating from AvgRating and try to rate again for same Ride
            newDriverAvgRating = (findPreviousAvgRating(oldAvgRating, oldDriverRating) + newDriverRating)/2;
        }
        else if(oldAvgRating != null){
            newDriverAvgRating = (oldAvgRating + newDriverRating)/2;
        }
        driver.setAvgRating(newDriverAvgRating);
        driverRepository.save(driver);
    }

    private float findPreviousAvgRating(Float oldAvgRating, Float oldRating) {
        return (oldAvgRating * 2) - oldRating;
    }


    @Override
    public RatingContract.Dto.ResponseRate getResponseRate(Boolean status) {
            if (Boolean.TRUE == status) {
                responseRateDTO.setStatus(RatingContract.Dto.ResponseRate.STATUS_SUCCESSFUL);
                responseRateDTO.setMessage(RatingContract.Dto.ResponseRate.RATING_UPDATED);
            } else {
                responseRateDTO.setStatus(RatingContract.Dto.ResponseRate.STATUS_FAILED);
                responseRateDTO.setMessage(RatingContract.Dto.ResponseRate.RIDE_NOT_FOUND);
            }
            return responseRateDTO;
    }

    @Override
    public Boolean setPassengerRating(RatingContract.Dto.RequestRate request) {
        if(!rideRepository.existsById(request.getRideId()))
            return false;
        setPassengerAvgRating(request);
        return  rideRepository.updatePassengerRating(request.getRideId(), request.getRate()) == 1;
    }
    private void setPassengerAvgRating(RatingContract.Dto.RequestRate request) {
        Passenger passenger = rideRepository.getOne(request.getRideId()).getPassenger();
        Float oldAvgRating = passenger.getAvgRating();
        Float newPassengerAvgRating = request.getRate();
        Float oldPassengerRating = rideRepository.getOne(request.getRideId()).getPassengerRate();
        Float newPassengerRating = request.getRate();

        if(oldPassengerRating != null && oldAvgRating != null){
            // already rated before so remove previous rating from AvgRating and try to rate again for same Ride
            newPassengerAvgRating = (findPreviousAvgRating(oldAvgRating, oldPassengerRating) + newPassengerRating)/2;
        }
        else if(oldAvgRating != null){
            newPassengerAvgRating = (oldAvgRating + newPassengerRating)/2;
        }
        passenger.setAvgRating(newPassengerAvgRating);
        passengerRepository.save(passenger);
    }

    @Override
    public RatingContract.Dto.ResponseAvgRate getResponseDriverAvgRating(Integer driverId) {
        synchronized (responseAvgRateDTO){
        if(!driverRepository.existsById(driverId)){
            responseAvgRateDTO.setAvgRating(-1F);
            responseAvgRateDTO.setMessage(RatingContract.Dto.ResponseAvgRate.USER_NOT_FOUND);
            return responseAvgRateDTO;
        }
        else if(driverRepository.existsById(driverId) && driverRepository.getOne(driverId).getAvgRating() == null){
            responseAvgRateDTO.setAvgRating(-1F);
            responseAvgRateDTO.setMessage(RatingContract.Dto.ResponseAvgRate.RATING_NOT_FOUND);
            return responseAvgRateDTO;
        }

        Float avgRating = driverRepository.getOne(driverId).getAvgRating();
        responseAvgRateDTO.setAvgRating(avgRating);
        responseAvgRateDTO.setMessage(RatingContract.Dto.ResponseAvgRate.MSG_SUCCESSFUL);

        return responseAvgRateDTO;
        }
    }

    @Override
    public RatingContract.Dto.ResponseAvgRate getResponsePassengerAvgRating(Integer passengerId) {
        synchronized (responseAvgRateDTO){
        if(!passengerRepository.existsById(passengerId)){
            responseAvgRateDTO.setAvgRating(-1F);
            responseAvgRateDTO.setMessage(RatingContract.Dto.ResponseAvgRate.USER_NOT_FOUND);
            return responseAvgRateDTO;
        }
        else if(passengerRepository.existsById(passengerId) && passengerRepository.getOne(passengerId).getAvgRating() == null){
            responseAvgRateDTO.setAvgRating(-1F);
            responseAvgRateDTO.setMessage(RatingContract.Dto.ResponseAvgRate.RATING_NOT_FOUND);
            return responseAvgRateDTO;
        }

        Float avgRating = passengerRepository.getOne(passengerId).getAvgRating();
        responseAvgRateDTO.setAvgRating(avgRating);
        responseAvgRateDTO.setMessage(RatingContract.Dto.ResponseAvgRate.MSG_SUCCESSFUL);

        return responseAvgRateDTO;
        }
    }
}
