package com.foody.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.foody.common.exception.GlobalAppException;
import org.springframework.http.HttpStatus;

import java.util.Objects;

public class AppUtils {

    private static final int EARTH_RADIUS_KM = 6371; // Radius of the earth in kilometers

    /**
     * Calculate the distance between two points specified by latitude/longitude using the Haversine formula.
     *
     * @param startLat the latitude of the start point
     * @param startLong the longitude of the start point
     * @param endLat the latitude of the end point
     * @param endLong the longitude of the end point
     * @return the distance between the two points in kilometers
     */
    public static double calculateDistance(double startLat, double startLong, double endLat, double endLong) {
        double dLat = Math.toRadians(endLat - startLat);
        double dLong = Math.toRadians(endLong - startLong);

        double startLatRad = Math.toRadians(startLat);
        double endLatRad = Math.toRadians(endLat);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLong / 2) * Math.sin(dLong / 2) *
                        Math.cos(startLatRad) * Math.cos(endLatRad);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }



    public static String convertToString(Object jsonPayload) {
        try {
            if(Objects.isNull(jsonPayload)){
                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(jsonPayload);
        } catch (JsonProcessingException e) {
            throw new GlobalAppException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error while converting to string");
        }
    }

    public static <T> T convertToObjectType(String jsonPayload, Class<T> t) {
        try {
            return new ObjectMapper().readValue(jsonPayload, t);
        } catch (JsonProcessingException e) {
            throw new GlobalAppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while converting to Object Type");
        }
    }

}
