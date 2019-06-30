package com.urbansharing.data.oslobicyclehistorical.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class TripStatisticDto {

    public String stationId;

    @Getter
    public String stationName;

    @Getter
    @Setter
    public Long tripsLengthInSeconds;

    @Getter
    @Setter
    public Long numTripsStarted;

    @Getter
    @Setter
    public Long numTripsEnded;

    @Getter
    @Setter
    public BigDecimal averageTripLength;

    @Getter
    @Setter
    public Long longestTripLength;

    @Getter
    @Setter
    public Long numOverpaidTrips;

    @Getter
    @Setter
    public Long numTechnicalTrips;

    public TripStatisticDto(String stationId, String stationName) {
        this(stationId, stationName, 0L, 0L, 0L, BigDecimal.ZERO, 0L, 0L, 0L);
    }

}
