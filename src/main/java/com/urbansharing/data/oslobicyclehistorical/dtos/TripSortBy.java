package com.urbansharing.data.oslobicyclehistorical.dtos;

import java.util.Comparator;

import lombok.Getter;

public enum TripSortBy {
    tripsStartedAsc(
            new Comparator<TripStatisticDto>() {
                @Override
                public int compare(TripStatisticDto station1, TripStatisticDto station2) {
                    int compareTo = station1.getNumTripsStarted().compareTo(station2.getNumTripsStarted());
                    if (compareTo == 0) {
                        return station1.getStationName().compareTo(station2.getStationName());
                    }
                    return compareTo;
                }
            }),
    tripsStartedDesc(
            new Comparator<TripStatisticDto>() {
                @Override
                public int compare(TripStatisticDto station1, TripStatisticDto station2) {
                    int compareTo = station1.getNumTripsStarted().compareTo(station2.getNumTripsStarted());
                    if (compareTo == 0) {
                        return station1.getStationName().compareTo(station2.getStationName());
                    }
                    return - compareTo;
                }
            }),
    tripsEndedAsc(
            new Comparator<TripStatisticDto>() {
                @Override
                public int compare(TripStatisticDto station1, TripStatisticDto station2) {
                    int compareTo = station1.getNumTripsEnded().compareTo(station2.getNumTripsEnded());
                    if (compareTo == 0) {
                        return station1.getStationName().compareTo(station2.getStationName());
                    }
                    return compareTo;
                }
            }),
    tripsEndedDesc(
            new Comparator<TripStatisticDto>() {
                @Override
                public int compare(TripStatisticDto station1, TripStatisticDto station2) {
                    int compareTo = station1.getNumTripsEnded().compareTo(station2.getNumTripsEnded());
                    if (compareTo == 0) {
                        return station1.getStationName().compareTo(station2.getStationName());
                    }
                    return - compareTo;
                }
            }),
    averageTripLengthAsc(
            new Comparator<TripStatisticDto>() {
                @Override
                public int compare(TripStatisticDto station1, TripStatisticDto station2) {
                    int compareTo = station1.getAverageTripLength().compareTo(station2.getAverageTripLength());
                    if (compareTo == 0) {
                        return station1.getStationName().compareTo(station2.getStationName());
                    }
                    return compareTo;
                }
            }),
    averageTripLengthDesc(
            new Comparator<TripStatisticDto>() {
                @Override
                public int compare(TripStatisticDto station1, TripStatisticDto station2) {
                    int compareTo = station1.getAverageTripLength().compareTo(station2.getAverageTripLength());
                    if (compareTo == 0) {
                        return station1.getStationName().compareTo(station2.getStationName());
                    }
                    return - compareTo;
                }
            });

    @Getter
    private Comparator<TripStatisticDto> comparator;

    TripSortBy(Comparator<TripStatisticDto> comparator) {
        this.comparator = comparator;
    }
}
