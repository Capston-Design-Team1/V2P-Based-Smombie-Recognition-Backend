package com.capston.v2psmombie.dto;

import com.capston.v2psmombie.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(
        title = "ResponseSmombieDto: 스몸비 조회 응답 DTO",
        description = "Smombie Search response DTO")
public class ResponseSmombieDto {

    @Schema(description = "위험도 수준", example = "0")
    private List<Integer> riskLevel;

    @Schema(description = "스몸비 위치")
    private List<LocationDto> smombiesLocation;

    public ResponseSmombieDto(List<Integer> riskLevel, List<User> users) {
        this.riskLevel = riskLevel;
        this.smombiesLocation = createLocations(users);
    }

    private List<LocationDto> createLocations(List<User> users) {
        return users.stream()
                .map(user -> new LocationDto(
                        user.getDeviceId(),
                        user.getLatitude(),
                        user.getLongitude()))
                .toList();
    }
}
