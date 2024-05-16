package com.capston.v2psmombie.riskCalculate;

import com.capston.v2psmombie.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReRiskCalculatorTest {

    @Test
    @DisplayName("위험도 계산 로직 테스트")
    public void reRiskCalculatorLogicTest() {
        // given
        User user = User.builder("303589cb-0832-4cbd-881a-4f2c4a7c0eef", false)
                .latitude(37.4208641)
                .longitude(-122.0780300)
                .speed(6.553)
                .direction(180.0000)
                .build();

        // when
        User carData = User.builder("c93e4be3-0d6f-4af0-aed9-8267123b5250", true)
                .latitude(37.4204854)
                .longitude(-122.0780300)
                .speed(0.100)
                .direction(0.0000)
                .build();
        ReRiskCalculator calculator = new ReRiskCalculator(carData);

        // then
        assertThat(calculator.riskCalculate(user)).isEqualTo(1);

    }

}