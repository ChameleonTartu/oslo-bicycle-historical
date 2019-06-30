package com.urbansharing.data.oslobicyclehistorical.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbansharing.data.oslobicyclehistorical.dtos.TripInfoDto;
import com.urbansharing.data.oslobicyclehistorical.dtos.TripSortBy;
import com.urbansharing.data.oslobicyclehistorical.dtos.TripStatisticDto;

@Controller
public class OsloBycicleController {

    private static final Logger logger = LoggerFactory.getLogger(OsloBycicleController.class);
    
    public static final long SECONDS_IN_ONE_HOUR = 3600L;

    public static final long TECHNICAL_TRIPS_IN_SECONDS = 90L;

    // XXX: Build graphics on top of this and decompose code in a small testable pieces
    @GetMapping("/stats")
    public @ResponseBody Collection<TripStatisticDto> getTripLength(@RequestParam("year") Long year, @RequestParam("month") Long month, @RequestParam("sortBy") TripSortBy sortBy) {
        RestTemplate restTemplate = new RestTemplate();
        String m = month < 10 ? "0" + month : "" + month;
        
        List list = restTemplate.getForObject("https://data.urbansharing.com/oslobysykkel.no/trips/v1/" + year + "/" + m + ".json", List.class);
        
        ObjectMapper mapper = new ObjectMapper();
        List<TripInfoDto> tripInfoDtos = (List<TripInfoDto>) list.stream().map(el -> mapper.convertValue(el, TripInfoDto.class)).collect(Collectors.toList());

        Map<String, TripStatisticDto> trips = new HashMap<>();
        tripInfoDtos.forEach(trip -> {
            trips.putIfAbsent(trip.getStart_station_id(), new TripStatisticDto(trip.getStart_station_id(), trip.getStart_station_name()));
            trips.putIfAbsent(trip.getStart_station_id(), new TripStatisticDto(trip.getEnd_station_id(), trip.getStart_station_name()));
        });

        tripInfoDtos.forEach(tripInfoDto -> {
            TripStatisticDto startStationVisitDto = trips.get(tripInfoDto.getStart_station_id());
            startStationVisitDto.setLongestTripLength(Math.max(startStationVisitDto.getLongestTripLength(), tripInfoDto.getDuration()));
            startStationVisitDto.setNumTripsStarted(startStationVisitDto.getNumTripsStarted() + 1L);
            if (tripInfoDto.getDuration() > SECONDS_IN_ONE_HOUR) {
                startStationVisitDto.setNumOverpaidTrips(startStationVisitDto.getNumOverpaidTrips() + 1L);
            } else if (tripInfoDto.getDuration() < TECHNICAL_TRIPS_IN_SECONDS && tripInfoDto.getStart_station_id().equals(tripInfoDto.getEnd_station_id())) {
                startStationVisitDto.setNumTechnicalTrips(startStationVisitDto.getNumTechnicalTrips() + 1L);
            } else {
                startStationVisitDto.setTripsLengthInSeconds(startStationVisitDto.getTripsLengthInSeconds() + tripInfoDto.getDuration());
            }

            TripStatisticDto endStationVisitDto = trips.get(tripInfoDto.getEnd_station_id());
            endStationVisitDto.setNumTripsEnded(endStationVisitDto.getNumTripsEnded() + 1L);
        });

        trips.entrySet().forEach(entry -> {
            TripStatisticDto dto = entry.getValue();
            dto.setAverageTripLength(BigDecimal.valueOf(dto.getTripsLengthInSeconds() - dto.getNumOverpaidTrips() - dto.getNumTechnicalTrips())
                    .divide(BigDecimal.valueOf(dto.getNumTripsStarted()), 2, RoundingMode.HALF_UP));
        });

        return trips.values().stream().sorted(sortBy.getComparator()).collect(Collectors.toList());
    }
}
