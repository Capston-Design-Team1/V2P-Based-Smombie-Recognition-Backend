package com.capston.v2psmombie.dto;

import com.capston.v2psmombie.domain.User;
import com.capston.v2psmombie.riskCalculate.ReRiskCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseSmombieDtoTest {

        @Test
        @DisplayName("스몸비 Response Dto 생성 테스트")
        public void smombieResponseDtoCreateTest() {
                // given
                List<User> smombies = getSmombiesData();
                User carData = User.builder("c93e4be3-0d6f-4af0-aed9-8267123b5250", true)
                                .latitude(37.4201365)
                                .longitude(-122.0780385)
                                .speed(6.520)
                                .direction(180.8181)
                                .build();

                // when
                ReRiskCalculator calculator = new ReRiskCalculator(carData);
                ResponseSmombieDto result = new ResponseSmombieDto(calculator, smombies);

                System.out.println(result.getRiskLevel1().get(0).getDeviceId());
                // then
                assertThat(result.getRiskLevel1().size()).isEqualTo(1);
                assertThat(result.getRiskLevel2().size()).isEqualTo(0);
                assertThat(result.getRiskLevel3().size()).isEqualTo(0);

        }

        private List<User> getSmombiesData() {
                List<User> smombies = new ArrayList<>();

                User user1 = User.builder("303589cb-0832-4cbd-881a-4f2c4a7c0eef", false)
                                .latitude(37.4191150)
                                .longitude(-122.0779717)
                                .speed(10.000)
                                .direction(0.0000)
                                .build();
                smombies.add(user1);

                User user2 = User.builder("46c141e8-4f3e-4355-88e5-628555d33947", false)
                                .latitude(35.856316)
                                .longitude(128.590737)
                                .speed(2.0)
                                .direction(0.0)
                                .build();
                smombies.add(user2);

                User user3 = User.builder("165374e7-e564-4d5e-954a-8f51040d1b6a", false)
                                .latitude(37.4217878)
                                .longitude(-122.0781225)
                                .speed(5.0)
                                .direction(180.0)
                                .build();
                smombies.add(user3);

                User user4 = User.builder("09fe213b-7dad-4244-92b6-ecac58d8cfcd", false)
                                .latitude(37.7749)
                                .longitude(-122.4194)
                                .speed(0.0)
                                .direction(0.0)
                                .build();
                smombies.add(user4);

                User user5 = User.builder("d433bbf1-b5fa-4ce0-b4f4-d5fbbd4f2f90", false)
                                .latitude(37.7749)
                                .longitude(-122.4194)
                                .speed(10.0)
                                .direction(180.0)
                                .build();
                smombies.add(user5);

                return smombies;
        }
}