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
        if(rideRepository.getOne(request.getRideId()).getDriverRate() != null)
            return; // already rated before

        Driver driver = rideRepository.getOne(request.getRideId()).getDriver();
        if(driver.getAvgRating()  == null)
            driver.setAvgRating(request.getRate());
        else
            driver.setAvgRating(((driver.getAvgRating()+ request.getRate())/2));
        driverRepository.save(driver);
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

        if(rideRepository.getOne(request.getRideId()).getPassengerRate() != null)
            return; // already rated before

        Passenger passenger = rideRepository.getOne(request.getRideId()).getPassenger();
        if(passenger.getAvgRating()  == null)
            passenger.setAvgRating(request.getRate());
        else
            passenger.setAvgRating(((passenger.getAvgRating()+ request.getRate())/2));
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
