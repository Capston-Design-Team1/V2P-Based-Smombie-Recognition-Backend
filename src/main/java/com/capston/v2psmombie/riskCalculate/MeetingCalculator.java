package com.capston.v2psmombie.riskCalculate;

// 자동차가 보행자에게 도달하기까지 걸리는 시간

import com.capston.v2psmombie.domain.User;

import java.lang.Math;

public class MeetingCalculator {

    // Haversine formula to calculate distance between two points
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371.0; // Earth radius in kilometers

        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double dlon = lon2Rad - lon1Rad;
        double dlat = lat2Rad - lat1Rad;

        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    /* GeographicLib dependency 필요, haversine보다 정확함 */
    // public static double vincentyDistance(double lat1, double lon1, double lat2,
    // double lon2) {
    // Geodesic geodesic = Geodesic.WGS84;
    // GeodesicData result = geodesic.Inverse(lat1, lon1, lat2, lon2);
    // return result.s12 / 1000.0; // Distance in kilometers
    // }

    /* speed: km/h, direction: degree */
    public static double timeToMeet(User userData, User carData) {
        // Initial distance between the two points
        double initialDistance = haversine(
                userData.getLatitude(),
                userData.getLongitude(),
                carData.getLatitude(),
                carData.getLongitude()
        );

        // Convert direction angles to radians
        double direction1Rad = Math.toRadians(userData.getDirection());
        double direction2Rad = Math.toRadians(carData.getDirection());

        // Calculate components of the velocity vectors
        double dx1 = userData.getSpeed() * Math.cos(direction1Rad);
        double dy1 = userData.getSpeed() * Math.sin(direction1Rad);
        double dx2 = carData.getSpeed() * Math.cos(direction2Rad);
        double dy2 = carData.getSpeed() * Math.sin(direction2Rad);

        // Calculate relative velocity vector
        double relativeDx = dx1 - dx2;
        double relativeDy = dy1 - dy2;

        // Calculate magnitude of the relative velocity vector
        double relativeDistance = Math.sqrt(Math.pow(relativeDx, 2) + Math.pow(relativeDy, 2));

        // Calculate meeting time
        double meetingTime;
        if (relativeDistance == 0) {
            return -1; // Return -1 to indicate that the two persons are already meeting or cannot meet
        } else if (relativeDistance > initialDistance) {
            return -1; // Return -1 to indicate that the two persons are moving away from each other
        } else {
            meetingTime = initialDistance / relativeDistance;
        }

        return meetingTime;
    }
}
