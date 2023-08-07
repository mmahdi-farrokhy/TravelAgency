package dto;

import model.Airport;
import model.DBTable;
import model.Flight;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FlightDTO extends DBTable {
    private String id;
    private LocalDateTime departureTime;
    private Airport originAirport;
    private Airport destinationAirport;

    public FlightDTO() {
        this.id = "";
        this.departureTime = LocalDateTime.of(1,1,1,1,1,1);
        this.originAirport = new Airport();
        this.destinationAirport = new Airport();
    }

    public FlightDTO(String id, LocalDateTime departureTime, Airport originAirport, Airport destinationAirport) {
        this.id = id;
        this.departureTime = departureTime;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public Airport getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(Airport originAirport) {
        this.originAirport = originAirport;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public static List<Flight> flightListFromFlightDTOList(List<FlightDTO> flightDTOList){
        List<Flight> flightList = new LinkedList<>();

        for(FlightDTO flightDTO:flightDTOList)
            flightList.add(flightDTO.convertFlightDTOToFlight());

        return flightList;
    }

    public Flight convertFlightDTOToFlight() {
        return new Flight(
                getId(),
                getDepartureTime(),
                getOriginAirport(),
                getDestinationAirport());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightDTO flightDTO = (FlightDTO) o;
        return Objects.equals(departureTime, flightDTO.departureTime) && Objects.equals(originAirport, flightDTO.originAirport) && Objects.equals(destinationAirport, flightDTO.destinationAirport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departureTime, originAirport, destinationAirport);
    }
}
