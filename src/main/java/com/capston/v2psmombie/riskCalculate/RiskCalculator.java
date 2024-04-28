package com.capston.v2psmombie.riskCalculate;

import com.capston.v2psmombie.domain.User;

public class RiskCalculator {
    private User carData;

    public RiskCalculator(User carData){
        this.carData = carData;
    }


    public Integer riskCalculate(User user) {
        double minMeetingTime = MeetingCalculator.timeToMeet(user, carData);
        if (minMeetingTime < 0 || minMeetingTime > 40) {
            return 4;
        } else if (minMeetingTime > 30) {
            return 3;
        } else if (minMeetingTime > 15) {
            return 2;
        } else {
            return 1;
        }
    }
}
