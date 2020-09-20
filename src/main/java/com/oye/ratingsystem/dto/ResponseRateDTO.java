package com.oye.ratingsystem.dto;

import com.oye.ratingsystem.contract.RatingContract;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component(value = "responseRateDTO")
public class ResponseRateDTO implements RatingContract.Dto.ResponseRate {
    String status;
    String message;
}
