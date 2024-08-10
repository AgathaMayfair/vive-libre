package com.vivelibre.tokenretriever.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@JsonPropertyOrder({"auth-vivelibre-token", "date"})
public class ResponseObjectDto {

    @JsonProperty("date")
    private String date;
    @JsonProperty("auth-vivelibre-token")
    private String token;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDate() {
        if(date != null){
            return  date;
        }

        date = LocalDate.now().format(formatter);
        return date;
    }

    @Override
    public String toString() {
        return "ResponseObjectDto{" +
                "token='" + token + '\'' +
                ", date=" + getDate() +
                '}';
    }
}
