package com.opcr.safetynet_alert.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "address",
        "station"
})
public class FireStation {

    @JsonProperty("address")
    private String address;

    @JsonProperty("station")
    private int station;

    @JsonProperty("station")
    public int getStation() {
        return station;
    }

    @JsonProperty("station")
    public void setStation(int station) {
        this.station = station;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }
}
