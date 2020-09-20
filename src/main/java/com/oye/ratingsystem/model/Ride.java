package com.oye.ratingsystem.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Ride {

    @Id
    Integer id;

    @ManyToOne
    Driver driver;

    @ManyToOne
    Passenger passenger;

    Float driverRate;

    Float passengerRate;
}

