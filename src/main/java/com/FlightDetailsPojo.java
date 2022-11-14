package com;

import javax.persistence.*;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.List;

@Entity
public class FlightDetailsPojo {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flightNumber;
    private String flightSource;
    private String flightDestination;
    private int flightPrice;
    private Time flightTakeOff;
    private Time flightLanding;
    @OneToMany(mappedBy = "flightDetailsPojo")
    private List<FlightCustomerPojo> flightCustomerPojos;

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightSource() {
        return flightSource;
    }

    public void setFlightSource(String flightSource) {
        this.flightSource = flightSource;
    }

    public String getFlightDestination() {
        return flightDestination;
    }

    public void setFlightDestination(String flightDestination) {
        this.flightDestination = flightDestination;
    }

    public int getFlightPrice() {
        return flightPrice;
    }

    public void setFlightPrice(int flightPrice) {
        this.flightPrice = flightPrice;
    }

    public Time getFlightTakeOff() {
        return flightTakeOff;
    }

    public void setFlightTakeOff(Time flightTakeOff) {
        this.flightTakeOff = flightTakeOff;
    }

    public Time getFlightLanding() {
        return flightLanding;
    }

    public void setFlightLanding(Time flightLanding) {
        this.flightLanding = flightLanding;
    }

    public List<FlightCustomerPojo> getFlightCustomerPojos() {
        return flightCustomerPojos;
    }

    public void setFlightCustomerPojos(List<FlightCustomerPojo> flightCustomerPojos) {
        this.flightCustomerPojos = flightCustomerPojos;
    }

    @Override
    public String toString() {
        return "FlightDetailsPojo{" +
                "flightNumber=" + flightNumber +
                ", flightSource='" + flightSource + '\'' +
                ", flightDestination='" + flightDestination + '\'' +
                ", flightPrice=" + flightPrice +
                ", flightTakeOff=" + flightTakeOff +
                ", flightLanding=" + flightLanding +
                ", flightCustomerPojos=" + flightCustomerPojos +
                '}';
    }
}
