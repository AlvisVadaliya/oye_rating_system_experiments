package com.oye.ratingsystem.dto;

import com.oye.ratingsystem.contract.RatingContract;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component(value = "responseAvgRateDTO")
public class ResponseAvgRateDTO implements RatingContract.Dto.ResponseAvgRate {
    Float avgRating;
    String message;
}
