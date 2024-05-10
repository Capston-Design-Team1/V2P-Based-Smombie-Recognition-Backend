package com.capston.v2psmombie.riskCalculate;

import com.capston.v2psmombie.domain.User;

public class ReRiskCalculator {
    final double R = 6371000.0; // Earth radius in meters

    private double speed2;
    private double direction2;
    private double lat2;
    private double lon2;
    private double latGrad2;
    private double lonGrad2;

    public ReRiskCalculator(User carData) {
        this.speed2 = carData.getSpeed();
        this.direction2 = Math.toRadians(carData.getDirection());
        this.lat2 = carData.getLatitude();
        this.lon2 = carData.getLongitude();
        this.latGrad2 = Math.toDegrees((speed2 / R) * Math.cos(direction2));
        this.lonGrad2 = Math.toDegrees((speed2 / (R * Math.cos(toRadians(lat2)))) * Math.sin(direction2));
    }

    private double toRadians(double degrees) {
        return Math.toRadians(degrees);
    }

    // Haversine formula
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = toRadians(lat2 - lat1);
        double dLon = toRadians(lon2 - lon1);

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.cos(toRadians(lat1)) * Math.cos(toRadians(lat2)) *
                        Math.pow(Math.sin(dLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    // Calculate the distance between two objects with given latitude, longitude,
    // speed, and direction data over time t
    private double calculateDistance(
            double lat1, double lon1, double speed1, double direction1, double t
    ) {

        // Convert directions to radians
        direction1 = toRadians(direction1);

        // Calculate new positions after time t
        double lat1New = lat1 + Math.toDegrees((speed1 / R) * Math.cos(direction1)) * t;
        double lon1New = lon1 + Math.toDegrees((speed1 / (R * Math.cos(toRadians(lat1)))) * Math.sin(direction1)) * t;
        double lat2New = lat2 + latGrad2 * t;
        double lon2New = lon2 + lonGrad2 * t;

        // Calculate distance between new positions
        return haversine(lat1New, lon1New, lat2New, lon2New);
    }

    // Check if two objects are moving away from each other over time t
    private boolean areMovingAway(
            double lat1, double lon1, double speed1, double direction1, double t
    ) {
        double initialDistance;
        if (t < 1) {
            initialDistance = haversine(lat1, lon1, lat1, lon1);
        } else {
            initialDistance = calculateDistance(lat1, lon1, speed1, direction1, t);
        }
        double finalDistance = calculateDistance(lat1, lon1, speed1, direction1, t + 1);
        return finalDistance > initialDistance;
    }

    // Find the time at which two objects are closest to each other
    public Integer riskCalculate(User user) {
        double lat1 = user.getLatitude();
        double lon1 = user.getLongitude();
        double speed1 = user.getSpeed();
        double direction1 = user.getDirection();
        int startTime = 0;
        int endTime = 40;

        // check movingAway
        if (areMovingAway(lat1, lon1, speed1, direction1, 0)) {
            return 4;
        }

        // 가장 가까워질때의 시간
        while (startTime < endTime) {
            int midTime = (endTime + startTime) / 2;
            if (areMovingAway(lat1, lon1, speed1, direction1, midTime)) {
                endTime = midTime;
            } else {
                startTime = midTime + 1;
            }
        }
        // 가장 가까워지는 시간일때 서로간의 거리가 50m 이상이면 만나지 않는다고 판단.
        if (calculateDistance(lat1, lon1, speed1, direction1, startTime) > 50) {
            return 4;
        }

        if (startTime < 15) {
            return 1;
        } else if (startTime < 30) {
            return 2;
        } else {
            return 3;
        }
    }
}
