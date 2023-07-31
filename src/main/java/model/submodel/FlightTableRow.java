package model.submodel;

import controller.FlightsListPageController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FlightTableRow {
    private String flightIdCol;
    private String originAirportCol;
    private String destinationAirportCol;
    private LocalDate dateCol;
    private LocalTime timeCol;

    public FlightTableRow(String id, String originAirport, String destinationAirport, LocalDate date, LocalTime time) {
        this.flightIdCol = id;
        this.originAirportCol = originAirport;
        this.destinationAirportCol = destinationAirport;
        this.dateCol = date;
        this.timeCol = time;
    }

    public static List<FlightTableRow> filterFlightRows(FlightsListPageController flightsListPageController, List<FlightTableRow> flightRows) {
        List<FlightTableRow> filteredFlightRows = flightRows;
        if (flightsListPageController.originAirportCb.getValue() != null && !Objects.equals(flightsListPageController.originAirportCb.getValue(), ""))
            filteredFlightRows = filterByOriginAirport(flightsListPageController, filteredFlightRows);

        if (flightsListPageController.destinationAirportCb.getValue() != null && !Objects.equals(flightsListPageController.destinationAirportCb.getValue(), ""))
            filteredFlightRows = filterByDestinationAirport(flightsListPageController, filteredFlightRows);

        if (flightsListPageController.departureDp.getValue() != null && !flightsListPageController.departureDp.getValue().toString().equals(""))
            filteredFlightRows = filterByDepartureDate(flightsListPageController, filteredFlightRows);

        if (flightsListPageController.arrivalDp.getValue() != null && !flightsListPageController.arrivalDp.getValue().toString().equals(""))
            filteredFlightRows = filterByArrivalDate(flightsListPageController, filteredFlightRows);

        return filteredFlightRows;
    }

    private static List<FlightTableRow> filterByOriginAirport(FlightsListPageController flightsListPageController, List<FlightTableRow> flightRows) {
        return flightRows.stream().filter(row -> row.getOriginAirportCol().trim().equals(flightsListPageController.originAirportCb.getValue().trim())).collect(Collectors.toList());
    }

    private static List<FlightTableRow> filterByDestinationAirport(FlightsListPageController flightsListPageController, List<FlightTableRow> flightRows) {
        return flightRows.stream().filter(row -> row.getDestinationAirportCol().equals(flightsListPageController.destinationAirportCb.getValue().trim())).collect(Collectors.toList());
    }

    private static List<FlightTableRow> filterByDepartureDate(FlightsListPageController flightsListPageController, List<FlightTableRow> flightRows) {
        return flightRows.stream().filter(row -> row.getDateCol().isAfter(flightsListPageController.departureDp.getValue())).collect(Collectors.toList());
    }

    private static List<FlightTableRow> filterByArrivalDate(FlightsListPageController flightsListPageController, List<FlightTableRow> flightRows) {
        return flightRows.stream().filter(row -> row.getDateCol().isBefore(flightsListPageController.arrivalDp.getValue())).collect(Collectors.toList());
    }

    public String getFlightIdCol() {
        return flightIdCol;
    }

    public String getOriginAirportCol() {
        return originAirportCol;
    }

    public String getDestinationAirportCol() {
        return destinationAirportCol;
    }

    public LocalDate getDateCol() {
        return dateCol;
    }

    public LocalTime getTimeCol() {
        return timeCol;
    }
}
