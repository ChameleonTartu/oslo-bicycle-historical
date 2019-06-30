package com.urbansharing.data.oslobicyclehistorical.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TripInfoDto {

    @Getter
    public String start_station_id;

    @Getter
    public String start_station_name;

    @Getter
    public String end_station_id;

    @Getter
    public Integer duration;

    public String started_at;

    public String ended_at;

}
