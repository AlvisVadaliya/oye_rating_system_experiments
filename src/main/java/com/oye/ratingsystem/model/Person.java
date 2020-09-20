package com.oye.ratingsystem.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class Person {
    @Id
    Integer id;

    String fName;

    String sName;

    String contactNumber;

    String emailId;

    @OneToOne(mappedBy = "person")
    Driver driver;

    @OneToOne(mappedBy = "person")
    Passenger passenger;
}
