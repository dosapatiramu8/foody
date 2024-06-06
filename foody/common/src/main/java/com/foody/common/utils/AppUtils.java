package com.foody.common.utils;

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

}
