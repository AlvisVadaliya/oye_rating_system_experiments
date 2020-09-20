package com.oye.ratingsystem.controller;

import com.oye.ratingsystem.contract.RatingContract;
import com.oye.ratingsystem.dto.RequestRateDTO;
import com.oye.ratingsystem.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class RatingController {

    @Autowired
    RatingService ratingService;

    @PatchMapping("/rate/driver")
    public RatingContract.Dto.ResponseRate setRatingForDriver(@RequestBody RequestRateDTO requestRateDTO) {
        return ratingService.getResponseRate(
                ratingService.setDriverRating(requestRateDTO)
        );
    }

    @PatchMapping("/rate/passenger")
    public RatingContract.Dto.ResponseRate setRatingForPassenger(@RequestBody RequestRateDTO requestRateDTO) {
        return ratingService.getResponseRate(
                ratingService.setPassengerRating(requestRateDTO)
        );
    }

    @GetMapping("/avgRating/driver/{id}")
    public RatingContract.Dto.ResponseAvgRate getDriverAvgRating(@PathVariable("id") Integer driverId){
        return ratingService.getResponseDriverAvgRating(driverId);
    }

   @GetMapping("/avgRating/passenger/{id}")
    public RatingContract.Dto.ResponseAvgRate getPassengerAvgRating(@PathVariable("id") Integer passengerId){
        return ratingService.getResponsePassengerAvgRating(passengerId);
    }
}
