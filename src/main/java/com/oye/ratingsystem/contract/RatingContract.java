package com.oye.ratingsystem.contract;

import com.sun.org.apache.xpath.internal.operations.Bool;

public interface RatingContract {
    interface Service{
            public Boolean setDriverRating(Dto.RequestRate requestRate);
            public Boolean setPassengerRating(Dto.RequestRate requestRate);
            public Dto.ResponseRate getResponseRate(Boolean status);
            public Dto.ResponseAvgRate getResponseDriverAvgRating(Integer driverId);
            public Dto.ResponseAvgRate getResponsePassengerAvgRating(Integer passengerId);
    }
    interface Dto{
        interface RequestRate{
            Float getRate();
            Integer getRideId();
        }
        interface ResponseRate{
            String STATUS_SUCCESSFUL = "Successful";
            String STATUS_FAILED = "Failed";
            String RATING_UPDATED = "Rating Updated";
            String RIDE_NOT_FOUND = "Ride not Found";
            void setStatus(String status);
            void setMessage(String message);
        }
        interface ResponseAvgRate{
            String USER_NOT_FOUND = "User not Found";
            String RATING_NOT_FOUND = "Rating not Found";
            String MSG_SUCCESSFUL = "Successful";
            void setAvgRating(Float avgRating);
            void setMessage(String message);
        }
    }
}
