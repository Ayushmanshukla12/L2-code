package com;

import javax.persistence.*;

@Entity
public class FlightCustomerPojo {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    private String customerName;
    private String customerEmail;
    private String customerPassword;
    private int customerbookings;

    public int getCustomerbookings() {
        return customerbookings;
    }

    public void setCustomerbookings(int customerbookings) {
        this.customerbookings = customerbookings;
    }

    @ManyToOne
    private FlightDetailsPojo flightDetailsPojo;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public FlightDetailsPojo getFlightDetailsPojo() {
        return flightDetailsPojo;
    }

    public void setFlightDetailsPojo(FlightDetailsPojo flightDetailsPojo) {
        this.flightDetailsPojo = flightDetailsPojo;
    }

    @Override
    public String toString() {
        return "FlightCustomerPojo{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerPassword='" + customerPassword + '\'' +
                ", customerbookings=" + customerbookings +
                ", flightDetailsPojo=" + flightDetailsPojo +
                '}';
    }
}
