package com;

public interface FLightDaoInterface {
    public void registerCustomer(FlightCustomerPojo flightCustomerPojo);
    public void registerFLight(FlightDetailsPojo flightDetailsPojo);
    public void bookingFLight(FlightDetailsPojo flightDetailsPojo,FlightCustomerPojo flightCustomerPojo);
}
