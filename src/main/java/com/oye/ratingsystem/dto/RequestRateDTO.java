package com.oye.ratingsystem.dto;

import com.oye.ratingsystem.contract.RatingContract;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component(value = "requestRateDTO")
public class RequestRateDTO implements RatingContract.Dto.RequestRate {
    private Float rate;
    private Integer rideId;
}
